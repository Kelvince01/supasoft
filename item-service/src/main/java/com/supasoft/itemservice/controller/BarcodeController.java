package com.supasoft.itemservice.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.itemservice.service.BarcodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for Barcode operations
 */
@RestController
@RequestMapping("/api/v1/barcodes")
@RequiredArgsConstructor
@Tag(name = "Barcode Management", description = "APIs for generating and managing barcodes")
public class BarcodeController {
    
    private final BarcodeService barcodeService;
    
    @GetMapping("/generate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('CREATE_ITEMS')")
    @Operation(summary = "Generate a unique barcode")
    public ResponseEntity<ApiResponse<Map<String, String>>> generateBarcode(
            @RequestParam(defaultValue = "EAN13") String type) {
        String barcode = barcodeService.generateUniqueBarcode(type);
        return ResponseEntity.ok(ApiResponse.success("Barcode generated successfully", 
                Map.of("barcode", barcode, "type", type)));
    }
    
    @GetMapping("/generate/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('CREATE_ITEMS')")
    @Operation(summary = "Generate barcode for specific item")
    public ResponseEntity<ApiResponse<Map<String, String>>> generateBarcodeForItem(
            @PathVariable Long itemId,
            @RequestParam(defaultValue = "EAN13") String type) {
        String barcode = barcodeService.generateBarcodeForItem(itemId, type);
        return ResponseEntity.ok(ApiResponse.success("Barcode generated for item", 
                Map.of("itemId", itemId.toString(), "barcode", barcode, "type", type)));
    }
    
    @GetMapping("/validate")
    @Operation(summary = "Validate barcode format")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateBarcode(
            @RequestParam String barcode,
            @RequestParam(defaultValue = "EAN13") String type) {
        boolean isValid = barcodeService.validateBarcode(barcode, type);
        return ResponseEntity.ok(ApiResponse.success("Barcode validation result", 
                Map.of("valid", isValid, "barcode", barcode)));
    }
    
    @GetMapping("/check-unique")
    @Operation(summary = "Check if barcode is unique")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkBarcodeUniqueness(
            @RequestParam String barcode) {
        boolean isUnique = barcodeService.isBarcodeUnique(barcode);
        return ResponseEntity.ok(ApiResponse.success("Barcode uniqueness check", 
                Map.of("unique", isUnique, "barcode", barcode)));
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('UPDATE_ITEMS')")
    @Operation(summary = "Add barcode to item")
    public ResponseEntity<ApiResponse<Void>> addBarcodeToItem(@RequestBody Map<String, Object> request) {
        Long itemId = Long.parseLong(request.get("itemId").toString());
        String barcode = request.get("barcode").toString();
        String barcodeType = request.getOrDefault("barcodeType", "EAN13").toString();
        Boolean isPrimary = Boolean.parseBoolean(request.getOrDefault("isPrimary", "false").toString());
        
        barcodeService.addBarcodeToItem(itemId, barcode, barcodeType, isPrimary);
        return ResponseEntity.ok(ApiResponse.success("Barcode added to item successfully", null));
    }
}

