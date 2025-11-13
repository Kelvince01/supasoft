//package com.supasoft.itemservice.service;
//
//import jakarta.persistence.Cacheable;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Service;
//
//// Service Example
//@Service
//@Transactional
//@Slf4j
//public class ItemServiceImpl implements ItemService {
//
//    private final ItemRepository itemRepository;
//    private final ItemMapper itemMapper;
//    private final BarcodeGenerator barcodeGenerator;
//    private final ApplicationEventPublisher eventPublisher;
//
//    @Cacheable(value = "items", key = "#itemId")
//    public ItemResponse getItemById(Long itemId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new ItemNotFoundException(itemId));
//        return itemMapper.toResponse(item);
//    }
//
//    @CacheEvict(value = "items", allEntries = true)
//    public ItemResponse createItem(CreateItemRequest request) {
//        // Validate barcode uniqueness
//        if (request.getBarcode() != null &&
//                itemRepository.findByBarcode(request.getBarcode()).isPresent()) {
//            throw new DuplicateBarcodeException(request.getBarcode());
//        }
//
//        Item item = itemMapper.toEntity(request);
//
//        // Generate barcode if not provided
//        if (item.getBarcode() == null) {
//            item.setBarcode(barcodeGenerator.generateEAN13());
//        }
//
//        Item savedItem = itemRepository.save(item);
//
//        // Publish event
//        eventPublisher.publishEvent(new ItemCreatedEvent(this, savedItem));
//
//        log.info("Created new item: {}", savedItem.getItemCode());
//
//        return itemMapper.toResponse(savedItem);
//    }
//}
