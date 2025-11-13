//package com.supasoft.itemservice.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//// Controller Example
//@RestController
//@RequestMapping("/api/v1/items")
//@RequiredArgsConstructor
//@Validated
//public class ItemController {
//
//    private final ItemService itemService;
//
//    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CASHIER')")
//    public ResponseEntity<PagedResponse<ItemResponse>> getAllItems(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(required = false) String search,
//            @RequestParam(required = false) Long categoryId) {
//
//        return ResponseEntity.ok(itemService.getAllItems(page, size, search, categoryId));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
//        return ResponseEntity.ok(itemService.getItemById(id));
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
//    public ResponseEntity<ItemResponse> createItem(
//            @Valid @RequestBody CreateItemRequest request) {
//        ItemResponse response = itemService.createItem(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
//    public ResponseEntity<ItemResponse> updateItem(
//            @PathVariable Long id,
//            @Valid @RequestBody UpdateItemRequest request) {
//        return ResponseEntity.ok(itemService.updateItem(id, request));
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//        itemService.deleteItem(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/barcode/{barcode}")
//    public ResponseEntity<ItemResponse> getItemByBarcode(@PathVariable String barcode) {
//        return ResponseEntity.ok(itemService.getItemByBarcode(barcode));
//    }
//}
