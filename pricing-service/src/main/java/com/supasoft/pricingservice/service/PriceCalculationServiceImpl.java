package com.supasoft.pricingservice.service;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.dto.request.PriceCalculationRequest;
import com.supasoft.pricingservice.dto.response.PriceCalculationResponse;
import com.supasoft.pricingservice.entity.CustomerPricing;
import com.supasoft.pricingservice.entity.Discount;
import com.supasoft.pricingservice.entity.ItemPrice;
import com.supasoft.pricingservice.entity.Promotion;
import com.supasoft.pricingservice.exception.InvalidDiscountException;
import com.supasoft.pricingservice.exception.PriceNotFoundException;
import com.supasoft.pricingservice.exception.PromotionExpiredException;
import com.supasoft.pricingservice.repository.*;
import com.supasoft.pricingservice.util.PriceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for complex price calculations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PriceCalculationServiceImpl implements PriceCalculationService {
    
    private final ItemPriceRepository itemPriceRepository;
    private final CustomerPricingRepository customerPricingRepository;
    private final DiscountRepository discountRepository;
    private final PromotionRepository promotionRepository;
    private final PriceTypeRepository priceTypeRepository;
    private final PriceCalculator priceCalculator;
    
    @Override
    public PriceCalculationResponse calculatePrice(PriceCalculationRequest request) {
        log.info("Calculating price for {} items", request.getItems().size());
        
        PriceCalculationResponse.PriceCalculationResponseBuilder responseBuilder = 
                PriceCalculationResponse.builder();
        
        List<PriceCalculationResponse.ItemPriceDetail> itemDetails = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        
        for (PriceCalculationRequest.ItemCalculation item : request.getItems()) {
            ItemPrice price = getItemPrice(item.getItemId(), request.getPriceTypeId(), request.getCustomerId());
            
            BigDecimal unitPrice = price.getSellingPrice();
            BigDecimal quantity = item.getQuantity();
            BigDecimal lineTotal = priceCalculator.calculateLineTotal(quantity, unitPrice);
            BigDecimal lineTax = BigDecimal.ZERO;
            
            if (price.getIsTaxable() && price.getTaxRate() != null) {
                lineTax = priceCalculator.calculateTaxAmount(lineTotal, price.getTaxRate());
            }
            
            PriceCalculationResponse.ItemPriceDetail detail = PriceCalculationResponse.ItemPriceDetail.builder()
                    .itemId(item.getItemId())
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .originalPrice(unitPrice)
                    .discountedPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .taxAmount(lineTax)
                    .lineDiscount(BigDecimal.ZERO)
                    .linePromotion(BigDecimal.ZERO)
                    .priceSource("REGULAR")
                    .build();
            
            itemDetails.add(detail);
            subtotal = subtotal.add(lineTotal);
            totalTax = totalTax.add(lineTax);
        }
        
        return responseBuilder
                .subtotal(subtotal)
                .discountAmount(BigDecimal.ZERO)
                .promotionAmount(BigDecimal.ZERO)
                .taxAmount(totalTax)
                .totalAmount(subtotal.add(totalTax))
                .items(itemDetails)
                .appliedDiscounts(new ArrayList<>())
                .appliedPromotions(new ArrayList<>())
                .build();
    }
    
    @Override
    public PriceCalculationResponse calculatePriceWithDiscounts(PriceCalculationRequest request, String discountCode) {
        log.info("Calculating price with discount: {}", discountCode);
        
        PriceCalculationResponse baseCalculation = calculatePrice(request);
        
        if (discountCode == null || discountCode.isBlank()) {
            return baseCalculation;
        }
        
        Discount discount = discountRepository.findByCodeAndStatus(discountCode, Status.ACTIVE)
                .orElseThrow(() -> new InvalidDiscountException("Discount not found or inactive: " + discountCode));
        
        if (!discount.isActive()) {
            throw new InvalidDiscountException(discountCode, "Discount is not active or has expired");
        }
        
        if (!priceCalculator.isDiscountApplicable(discount, baseCalculation.getSubtotal())) {
            throw new InvalidDiscountException(discountCode, "Discount not applicable for this purchase amount");
        }
        
        BigDecimal discountAmount = priceCalculator.calculateDiscountAmount(baseCalculation.getSubtotal(), discount);
        BigDecimal newSubtotal = baseCalculation.getSubtotal().subtract(discountAmount);
        BigDecimal newTotal = newSubtotal.add(baseCalculation.getTaxAmount());
        
        List<PriceCalculationResponse.AppliedDiscount> appliedDiscounts = new ArrayList<>();
        appliedDiscounts.add(PriceCalculationResponse.AppliedDiscount.builder()
                .code(discount.getCode())
                .name(discount.getName())
                .amount(discountAmount)
                .applicationType(discount.getDiscountType().name())
                .build());
        
        return PriceCalculationResponse.builder()
                .subtotal(baseCalculation.getSubtotal())
                .discountAmount(discountAmount)
                .promotionAmount(BigDecimal.ZERO)
                .taxAmount(baseCalculation.getTaxAmount())
                .totalAmount(newTotal)
                .discountCode(discountCode)
                .items(baseCalculation.getItems())
                .appliedDiscounts(appliedDiscounts)
                .appliedPromotions(new ArrayList<>())
                .build();
    }
    
    @Override
    public PriceCalculationResponse calculatePriceWithPromotions(PriceCalculationRequest request, String promotionCode) {
        log.info("Calculating price with promotion: {}", promotionCode);
        
        PriceCalculationResponse baseCalculation = calculatePrice(request);
        
        if (promotionCode == null || promotionCode.isBlank()) {
            return baseCalculation;
        }
        
        Promotion promotion = promotionRepository.findByCodeAndStatus(promotionCode, Status.ACTIVE)
                .orElseThrow(() -> new PromotionExpiredException("Promotion not found or inactive: " + promotionCode));
        
        if (!promotion.isActive()) {
            throw new PromotionExpiredException(promotionCode);
        }
        
        if (!priceCalculator.isPromotionApplicable(promotion, baseCalculation.getSubtotal())) {
            throw new PromotionExpiredException("Promotion not applicable for this purchase amount");
        }
        
        BigDecimal promotionAmount = priceCalculator.calculatePromotionAmount(baseCalculation.getSubtotal(), promotion);
        BigDecimal newSubtotal = baseCalculation.getSubtotal().subtract(promotionAmount);
        BigDecimal newTotal = newSubtotal.add(baseCalculation.getTaxAmount());
        
        List<PriceCalculationResponse.AppliedPromotion> appliedPromotions = new ArrayList<>();
        appliedPromotions.add(PriceCalculationResponse.AppliedPromotion.builder()
                .code(promotion.getCode())
                .name(promotion.getName())
                .amount(promotionAmount)
                .promotionType(promotion.getPromotionType().name())
                .build());
        
        return PriceCalculationResponse.builder()
                .subtotal(baseCalculation.getSubtotal())
                .discountAmount(BigDecimal.ZERO)
                .promotionAmount(promotionAmount)
                .taxAmount(baseCalculation.getTaxAmount())
                .totalAmount(newTotal)
                .promotionCode(promotionCode)
                .items(baseCalculation.getItems())
                .appliedDiscounts(new ArrayList<>())
                .appliedPromotions(appliedPromotions)
                .build();
    }
    
    @Override
    public PriceCalculationResponse calculatePriceWithAll(PriceCalculationRequest request) {
        log.info("Calculating price with all applicable discounts and promotions");
        
        PriceCalculationResponse calculation = calculatePrice(request);
        
        // Apply discount if provided
        if (request.getDiscountCode() != null && !request.getDiscountCode().isBlank()) {
            calculation = calculatePriceWithDiscounts(request, request.getDiscountCode());
        }
        
        // Apply promotion if provided (cumulative if allowed)
        if (request.getPromotionCode() != null && !request.getPromotionCode().isBlank()) {
            PriceCalculationResponse promotionCalc = calculatePriceWithPromotions(request, request.getPromotionCode());
            
            // Combine discount and promotion
            BigDecimal totalDiscount = calculation.getDiscountAmount().add(promotionCalc.getPromotionAmount());
            BigDecimal newTotal = calculation.getSubtotal()
                    .subtract(calculation.getDiscountAmount())
                    .subtract(promotionCalc.getPromotionAmount())
                    .add(calculation.getTaxAmount());
            
            return PriceCalculationResponse.builder()
                    .subtotal(calculation.getSubtotal())
                    .discountAmount(calculation.getDiscountAmount())
                    .promotionAmount(promotionCalc.getPromotionAmount())
                    .taxAmount(calculation.getTaxAmount())
                    .totalAmount(newTotal)
                    .discountCode(request.getDiscountCode())
                    .promotionCode(request.getPromotionCode())
                    .items(calculation.getItems())
                    .appliedDiscounts(calculation.getAppliedDiscounts())
                    .appliedPromotions(promotionCalc.getAppliedPromotions())
                    .build();
        }
        
        return calculation;
    }
    
    private ItemPrice getItemPrice(Long itemId, Long priceTypeId, Long customerId) {
        // Check for customer-specific pricing first
        if (customerId != null) {
            CustomerPricing customerPricing = customerPricingRepository
                    .findActiveCustomerPricing(customerId, itemId, Status.ACTIVE, LocalDateTime.now())
                    .orElse(null);
            
            if (customerPricing != null) {
                // Create temporary ItemPrice from customer pricing
                ItemPrice tempPrice = new ItemPrice();
                tempPrice.setItemId(itemId);
                tempPrice.setSellingPrice(customerPricing.getSpecialPrice());
                tempPrice.setCostPrice(BigDecimal.ZERO); // Not relevant for customer pricing
                tempPrice.setIsTaxable(true);
                tempPrice.setTaxRate(BigDecimal.ZERO);
                return tempPrice;
            }
        }
        
        // Fall back to regular item pricing
        return itemPriceRepository.findActiveItemPriceByType(
                itemId, priceTypeId, Status.ACTIVE, LocalDateTime.now()
        ).orElseThrow(() -> new PriceNotFoundException(itemId, priceTypeId));
    }
}

