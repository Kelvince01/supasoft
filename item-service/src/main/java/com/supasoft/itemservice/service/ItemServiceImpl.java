package com.supasoft.itemservice.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supasoft.common.exception.BusinessException;
import com.supasoft.common.exception.ResourceNotFoundException;
import com.supasoft.itemservice.dto.request.CreateItemRequest;
import com.supasoft.itemservice.dto.request.UpdateItemRequest;
import com.supasoft.itemservice.dto.response.ItemDetailResponse;
import com.supasoft.itemservice.dto.response.ItemListResponse;
import com.supasoft.itemservice.dto.response.ItemResponse;
import com.supasoft.itemservice.entity.Brand;
import com.supasoft.itemservice.entity.Category;
import com.supasoft.itemservice.entity.Item;
import com.supasoft.itemservice.entity.Supplier;
import com.supasoft.itemservice.entity.UnitOfMeasure;
import com.supasoft.itemservice.event.ItemEventPublisher;
import com.supasoft.itemservice.mapper.ItemMapper;
import com.supasoft.itemservice.repository.BrandRepository;
import com.supasoft.itemservice.repository.CategoryRepository;
import com.supasoft.itemservice.repository.ItemRepository;
import com.supasoft.itemservice.repository.UnitOfMeasureRepository;
import com.supasoft.itemservice.specification.ItemSpecification;
import com.supasoft.itemservice.util.BarcodeGenerator;
import com.supasoft.itemservice.util.ItemCodeGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Item operations with Redis caching
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {
    
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final UnitOfMeasureRepository uomRepository;
    private final ItemMapper itemMapper;
    private final ItemCodeGenerator itemCodeGenerator;
    private final BarcodeService barcodeService;
    private final ItemEventPublisher eventPublisher;
    
    @Override
    @CacheEvict(value = "items", allEntries = true)
    public ItemDetailResponse createItem(CreateItemRequest request) {
        log.info("Creating item with code: {}", request.getItemCode());
        
        // Validate unique item code
        if (itemRepository.existsByItemCode(request.getItemCode())) {
            throw new BusinessException(String.format("Item with code '%s' already exists", request.getItemCode()));
        }
        
        // Validate unique barcode if provided
        if (request.getBarcode() != null && itemRepository.existsByBarcode(request.getBarcode())) {
            throw new BusinessException(String.format("Barcode '%s' already exists", request.getBarcode()));
        }
        
        // Generate barcode if not provided
        String barcode = request.getBarcode();
        if (barcode == null || barcode.isEmpty()) {
            barcode = barcodeService.generateUniqueBarcode("EAN13");
        }
        
        Item item = itemMapper.toEntity(request);
        item.setBarcode(barcode);
        
        // Set category
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            item.setCategory(category);
        }
        
        // Set brand
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));
            item.setBrand(brand);
        }
        
        // Set base UOM (required)
        UnitOfMeasure baseUom = uomRepository.findById(request.getBaseUomId())
                .orElseThrow(() -> new ResourceNotFoundException("UnitOfMeasure", "id", request.getBaseUomId()));
        item.setBaseUom(baseUom);
        
        // Set supplier if provided (will be implemented in Phase 2.3)
        // For now, leave it null
        
        Item savedItem = itemRepository.save(item);
        log.info("Item created successfully with ID: {}", savedItem.getItemId());
        
        // Publish item created event
        eventPublisher.publishItemCreatedEvent(savedItem);
        
        return itemMapper.toDetailResponse(savedItem);
    }
    
    @Override
    @CachePut(value = "items", key = "#itemId")
    public ItemDetailResponse updateItem(Long itemId, UpdateItemRequest request) {
        log.info("Updating item with ID: {}", itemId);
        
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        
        // Update basic fields via mapper
        itemMapper.updateEntity(request, item);
        
        // Update category if changed
        if (request.getCategoryId() != null && !request.getCategoryId().equals(item.getCategory().getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            item.setCategory(category);
        }
        
        // Update brand if changed
        if (request.getBrandId() != null && 
            (item.getBrand() == null || !request.getBrandId().equals(item.getBrand().getBrandId()))) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));
            item.setBrand(brand);
        }
        
        Item updatedItem = itemRepository.save(item);
        log.info("Item updated successfully: {}", updatedItem.getItemId());
        
        // Publish item updated event
        eventPublisher.publishItemUpdatedEvent(updatedItem);
        
        return itemMapper.toDetailResponse(updatedItem);
    }
    
    @Override
    @Cacheable(value = "items", key = "#itemId")
    @Transactional(readOnly = true)
    public ItemDetailResponse getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        return itemMapper.toDetailResponse(item);
    }
    
    @Override
    @Cacheable(value = "items", key = "#itemCode")
    @Transactional(readOnly = true)
    public ItemDetailResponse getItemByCode(String itemCode) {
        Item item = itemRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "code", itemCode));
        return itemMapper.toDetailResponse(item);
    }
    
    @Override
    @Cacheable(value = "items", key = "#barcode")
    @Transactional(readOnly = true)
    public ItemDetailResponse getItemByBarcode(String barcode) {
        Item item = itemRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "barcode", barcode));
        return itemMapper.toDetailResponse(item);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getAllActiveItems(Pageable pageable) {
        return itemRepository.findAllActive(pageable)
                .map(itemMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemListResponse> getItemsList(Pageable pageable) {
        return itemRepository.findAllActive(pageable)
                .map(itemMapper::toListResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> searchItems(
            String search,
            Long categoryId,
            Long brandId,
            Long supplierId,
            Boolean isActive,
            Boolean isForSale,
            Boolean isForPurchase,
            Pageable pageable
    ) {
        Specification<Item> spec = ItemSpecification.buildSearchSpecification(
                search, categoryId, brandId, supplierId, isActive, isForSale, isForPurchase, true);
        
        return itemRepository.findAll(spec, pageable)
                .map(itemMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getItemsByCategory(Long categoryId, Pageable pageable) {
        return itemRepository.findActiveItemsByCategory(categoryId, pageable)
                .map(itemMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getItemsByBrand(Long brandId, Pageable pageable) {
        return itemRepository.findActiveItemsByBrand(brandId, pageable)
                .map(itemMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getItemsBySupplier(Long supplierId, Pageable pageable) {
        return itemRepository.findActiveItemsBySupplier(supplierId, pageable)
                .map(itemMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getItemsForSale(Pageable pageable) {
        return itemRepository.findAllForSale(pageable)
                .map(itemMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ItemResponse> getItemsForPurchase(Pageable pageable) {
        return itemRepository.findAllForPurchase(pageable)
                .map(itemMapper::toResponse);
    }
    
    @Override
    @CacheEvict(value = "items", key = "#itemId")
    public void deleteItem(Long itemId) {
        log.info("Deleting item with ID: {}", itemId);
        
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        
        item.setIsActive(false);
        itemRepository.save(item);
        
        // Publish item deleted event
        eventPublisher.publishItemDeletedEvent(item);
        
        log.info("Item deleted successfully: {}", itemId);
    }
    
    @Override
    @CachePut(value = "items", key = "#itemId")
    public void activateItem(Long itemId) {
        log.info("Activating item with ID: {}", itemId);
        
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        
        item.setIsActive(true);
        itemRepository.save(item);
        log.info("Item activated successfully: {}", itemId);
    }
    
    @Override
    @CachePut(value = "items", key = "#itemId")
    public void deactivateItem(Long itemId) {
        log.info("Deactivating item with ID: {}", itemId);
        
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        
        item.setIsActive(false);
        itemRepository.save(item);
        log.info("Item deactivated successfully: {}", itemId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countActiveItems() {
        return itemRepository.countActive();
    }
}
