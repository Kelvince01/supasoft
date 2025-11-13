# Supasoft - API Contracts Documentation

## 1. API OVERVIEW

### 1.1 Base URLs

| Environment | Base URL |
|------------|----------|
| Development | `http://localhost:8080/api/v1` |
| Staging | `https://staging-api.supasoft.com/api/v1` |
| Production | `https://api.supasoft.com/api/v1` |

### 1.2 API Standards

- **Protocol**: REST over HTTPS
- **Data Format**: JSON
- **Character Encoding**: UTF-8
- **Date Format**: ISO 8601 (`yyyy-MM-dd`)
- **DateTime Format**: ISO 8601 (`yyyy-MM-dd'T'HH:mm:ss.SSS'Z'`)
- **Timezone**: UTC (converted from Africa/Nairobi)
- **API Version**: v1 (URL-based versioning)

### 1.3 Authentication

All API requests (except login) require JWT Bearer token:

```http
Authorization: Bearer <JWT_TOKEN>
```

### 1.4 Standard Response Format

#### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "timestamp": "2025-11-13T10:30:00.000Z"
}
```

#### Error Response
```json
{
  "success": false,
  "error": {
    "code": "ITEM_NOT_FOUND",
    "message": "Item with ID 12345 not found",
    "details": []
  },
  "timestamp": "2025-11-13T10:30:00.000Z"
}
```

#### Paginated Response
```json
{
  "success": true,
  "data": {
    "content": [ ... ],
    "page": 0,
    "size": 20,
    "totalElements": 150,
    "totalPages": 8,
    "first": true,
    "last": false
  }
}
```

### 1.5 HTTP Status Codes

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful GET, PUT, PATCH |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Validation error, malformed request |
| 401 | Unauthorized | Missing or invalid token |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Duplicate resource, business rule violation |
| 422 | Unprocessable Entity | Business logic error |
| 429 | Too Many Requests | Rate limit exceeded |
| 500 | Internal Server Error | Server error |
| 503 | Service Unavailable | Service temporarily down |

---

## 2. AUTHENTICATION SERVICE

### 2.1 User Login

**Endpoint**: `POST /auth/login`

**Request**:
```json
{
  "username": "john.doe",
  "password": "SecurePass123!"
}
```

**Response** (200):
```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4...",
    "tokenType": "Bearer",
    "expiresIn": 1800,
    "user": {
      "userId": 1,
      "username": "john.doe",
      "email": "john.doe@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "roles": ["MANAGER"],
      "branch": {
        "branchId": 1,
        "branchName": "Main Branch"
      }
    }
  }
}
```

### 2.2 Refresh Token

**Endpoint**: `POST /auth/refresh`

**Request**:
```json
{
  "refreshToken": "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4..."
}
```

**Response** (200):
```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "bmV3IHJlZnJlc2ggdG9rZW4...",
    "tokenType": "Bearer",
    "expiresIn": 1800
  }
}
```

### 2.3 Logout

**Endpoint**: `POST /auth/logout`

**Headers**: `Authorization: Bearer <token>`

**Response** (204): No content

### 2.4 Change Password

**Endpoint**: `POST /auth/change-password`

**Request**:
```json
{
  "currentPassword": "OldPass123!",
  "newPassword": "NewSecurePass456!",
  "confirmPassword": "NewSecurePass456!"
}
```

**Response** (200):
```json
{
  "success": true,
  "message": "Password changed successfully"
}
```

---

## 3. ITEM SERVICE

### 3.1 Get All Items

**Endpoint**: `GET /items`

**Query Parameters**:
- `page` (int, default: 0)
- `size` (int, default: 20)
- `search` (string, optional)
- `categoryId` (long, optional)
- `brandId` (long, optional)
- `isActive` (boolean, optional)
- `sort` (string, default: "itemName,asc")

**Response** (200):
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "itemId": 1,
        "itemCode": "ITM-001",
        "itemName": "Coca Cola 500ml",
        "barcode": "5449000000996",
        "category": {
          "categoryId": 5,
          "categoryName": "Beverages"
        },
        "brand": {
          "brandId": 2,
          "brandName": "Coca Cola"
        },
        "baseUom": {
          "uomId": 1,
          "uomName": "Piece",
          "uomCode": "PCS"
        },
        "vatRate": 16.00,
        "isActive": true,
        "reorderLevel": 50.00,
        "createdAt": "2025-01-15T10:00:00Z"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 1500,
    "totalPages": 75
  }
}
```

### 3.2 Get Item by ID

**Endpoint**: `GET /items/{id}`

**Response** (200):
```json
{
  "success": true,
  "data": {
    "itemId": 1,
    "itemCode": "ITM-001",
    "itemName": "Coca Cola 500ml",
    "description": "Coca Cola Soft Drink 500ml Bottle",
    "barcode": "5449000000996",
    "sku": "SKU-CC-500",
    "category": {
      "categoryId": 5,
      "categoryName": "Beverages",
      "categoryCode": "BEV"
    },
    "brand": {
      "brandId": 2,
      "brandName": "Coca Cola",
      "brandCode": "CC"
    },
    "baseUom": {
      "uomId": 1,
      "uomName": "Piece",
      "uomCode": "PCS"
    },
    "alternativeUoms": [
      {
        "uomId": 5,
        "uomName": "Carton",
        "uomCode": "CTN",
        "conversionFactor": 24.00,
        "barcode": "5449000001245"
      }
    ],
    "isTaxable": true,
    "vatRate": 16.00,
    "taxCategory": "STANDARD",
    "isSerialized": false,
    "isBatchTracked": false,
    "isExpiryTracked": true,
    "shelfLifeDays": 365,
    "reorderLevel": 50.00,
    "reorderQuantity": 200.00,
    "weight": 0.550,
    "isActive": true,
    "isDiscontinued": false,
    "createdAt": "2025-01-15T10:00:00Z",
    "updatedAt": "2025-11-13T08:00:00Z"
  }
}
```

### 3.3 Create Item

**Endpoint**: `POST /items`

**Request**:
```json
{
  "itemCode": "ITM-002",
  "itemName": "Fanta Orange 500ml",
  "description": "Fanta Orange Soft Drink",
  "categoryId": 5,
  "brandId": 2,
  "baseUomId": 1,
  "barcode": "5449000051943",
  "isTaxable": true,
  "vatRate": 16.00,
  "isExpiryTracked": true,
  "shelfLifeDays": 365,
  "reorderLevel": 50.00,
  "reorderQuantity": 200.00
}
```

**Response** (201):
```json
{
  "success": true,
  "message": "Item created successfully",
  "data": {
    "itemId": 2,
    "itemCode": "ITM-002",
    "itemName": "Fanta Orange 500ml",
    ...
  }
}
```

### 3.4 Update Item

**Endpoint**: `PUT /items/{id}`

**Request**: Same structure as Create Item

**Response** (200): Updated item details

### 3.5 Delete Item

**Endpoint**: `DELETE /items/{id}`

**Response** (204): No content

### 3.6 Get Item by Barcode

**Endpoint**: `GET /items/barcode/{barcode}`

**Response** (200): Item details (same as Get Item by ID)

### 3.7 Search Items

**Endpoint**: `GET /items/search`

**Query Parameters**:
- `q` (string, required): Search term
- `page`, `size`, `sort`

**Response** (200): Paginated item list

---

## 4. PRICING SERVICE

### 4.1 Get Item Price

**Endpoint**: `GET /prices/item/{itemId}`

**Query Parameters**:
- `uomId` (long, required)
- `branchId` (long, optional)
- `priceTypeCode` (string, default: "RETAIL")
- `customerId` (long, optional)
- `date` (date, optional, default: today)

**Response** (200):
```json
{
  "success": true,
  "data": {
    "priceId": 101,
    "itemId": 1,
    "itemName": "Coca Cola 500ml",
    "uomId": 1,
    "uomCode": "PCS",
    "branchId": 1,
    "priceType": {
      "priceTypeId": 1,
      "priceTypeName": "Retail Price",
      "priceTypeCode": "RETAIL"
    },
    "costPrice": 45.00,
    "sellingPrice": 60.00,
    "markupPercentage": 33.33,
    "profitMargin": 15.00,
    "effectiveFrom": "2025-01-01",
    "effectiveTo": null,
    "isActive": true
  }
}
```

### 4.2 Create/Update Price

**Endpoint**: `POST /prices`

**Request**:
```json
{
  "itemId": 1,
  "uomId": 1,
  "branchId": 1,
  "priceTypeId": 1,
  "costPrice": 45.00,
  "sellingPrice": 60.00,
  "effectiveFrom": "2025-01-01"
}
```

**Response** (201): Created price details

### 4.3 Get Active Promotions

**Endpoint**: `GET /promotions/active`

**Query Parameters**:
- `branchId` (long, optional)
- `date` (date, optional, default: today)

**Response** (200):
```json
{
  "success": true,
  "data": [
    {
      "promotionId": 10,
      "promotionCode": "PROMO-NOV-2025",
      "promotionName": "November Mega Sale",
      "description": "Get 20% off on all beverages",
      "promotionType": "DISCOUNT_PERCENTAGE",
      "discountPercentage": 20.00,
      "startDate": "2025-11-01",
      "endDate": "2025-11-30",
      "applicableItems": [
        {
          "itemId": 1,
          "itemName": "Coca Cola 500ml"
        }
      ],
      "isActive": true
    }
  ]
}
```

---

## 5. STOCK SERVICE

### 5.1 Get Stock Balance

**Endpoint**: `GET /stock/balance`

**Query Parameters**:
- `itemId` (long, required)
- `branchId` (long, required)
- `batchNumber` (string, optional)

**Response** (200):
```json
{
  "success": true,
  "data": {
    "balanceId": 501,
    "item": {
      "itemId": 1,
      "itemName": "Coca Cola 500ml",
      "itemCode": "ITM-001"
    },
    "branch": {
      "branchId": 1,
      "branchName": "Main Branch"
    },
    "batchNumber": "BATCH-2025-001",
    "quantityOnHand": 500.00,
    "quantityReserved": 50.00,
    "quantityAvailable": 450.00,
    "averageCost": 45.50,
    "totalValue": 22750.00,
    "manufactureDate": "2025-01-15",
    "expiryDate": "2026-01-15",
    "warehouseLocation": "A-12-03",
    "lastMovementAt": "2025-11-13T14:30:00Z"
  }
}
```

### 5.2 Get Stock Movements

**Endpoint**: `GET /stock/movements`

**Query Parameters**:
- `itemId` (long, optional)
- `branchId` (long, optional)
- `movementType` (string, optional)
- `fromDate` (date, optional)
- `toDate` (date, optional)
- `page`, `size`, `sort`

**Response** (200):
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "movementId": 1001,
        "item": {
          "itemId": 1,
          "itemName": "Coca Cola 500ml"
        },
        "branch": {
          "branchId": 1,
          "branchName": "Main Branch"
        },
        "movementType": "SALE",
        "quantity": -24.00,
        "unitCost": 45.50,
        "totalCost": -1092.00,
        "referenceType": "SALES_INVOICE",
        "referenceNumber": "INV-2025-001234",
        "movementDate": "2025-11-13",
        "createdAt": "2025-11-13T14:30:00Z"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 5000
  }
}
```

### 5.3 Create Stock Adjustment

**Endpoint**: `POST /stock/adjustments`

**Request**:
```json
{
  "adjustmentNumber": "ADJ-2025-0001",
  "branchId": 1,
  "adjustmentType": "DECREASE",
  "adjustmentReason": "DAMAGE",
  "adjustmentDate": "2025-11-13",
  "notes": "Damaged during shelf stocking",
  "items": [
    {
      "itemId": 1,
      "batchNumber": "BATCH-2025-001",
      "systemQuantity": 500.00,
      "actualQuantity": 476.00,
      "unitCost": 45.50
    }
  ]
}
```

**Response** (201):
```json
{
  "success": true,
  "message": "Stock adjustment created successfully",
  "data": {
    "adjustmentId": 201,
    "adjustmentNumber": "ADJ-2025-0001",
    "status": "PENDING_APPROVAL",
    ...
  }
}
```

### 5.4 Approve Stock Adjustment

**Endpoint**: `POST /stock/adjustments/{id}/approve`

**Response** (200):
```json
{
  "success": true,
  "message": "Stock adjustment approved and posted",
  "data": {
    "adjustmentId": 201,
    "status": "POSTED",
    "approvedBy": 5,
    "approvedAt": "2025-11-13T15:00:00Z"
  }
}
```

### 5.5 Create Repackaging

**Endpoint**: `POST /stock/repackaging`

**Request**:
```json
{
  "repackageNumber": "RPK-2025-0001",
  "branchId": 1,
  "repackageDate": "2025-11-13",
  "sourceItemId": 10,
  "sourceUomId": 5,
  "sourceQuantity": 1.00,
  "sourceBatchNumber": "BATCH-2025-010",
  "destItemId": 11,
  "destUomId": 1,
  "destQuantity": 24.00,
  "conversionFactor": 24.00,
  "notes": "Repack carton to pieces"
}
```

**Response** (201): Repackaging details

---

## 6. SALES SERVICE (POS)

### 6.1 Open Cashier Shift

**Endpoint**: `POST /cashier/shifts/open`

**Request**:
```json
{
  "branchId": 1,
  "cashierId": 10,
  "tillNumber": "TILL-01",
  "openingFloat": 5000.00
}
```

**Response** (201):
```json
{
  "success": true,
  "message": "Cashier shift opened successfully",
  "data": {
    "shiftId": 501,
    "shiftNumber": "SHIFT-2025-0501",
    "cashierId": 10,
    "branchId": 1,
    "tillNumber": "TILL-01",
    "openingFloat": 5000.00,
    "shiftStart": "2025-11-13T08:00:00Z",
    "status": "OPEN"
  }
}
```

### 6.2 Create Sale (Process Transaction)

**Endpoint**: `POST /sales/invoices`

**Request**:
```json
{
  "branchId": 1,
  "customerId": null,
  "cashierId": 10,
  "shiftId": 501,
  "items": [
    {
      "itemId": 1,
      "uomId": 1,
      "quantity": 2.00,
      "discountPercentage": 0
    },
    {
      "itemId": 5,
      "uomId": 1,
      "quantity": 1.00,
      "discountPercentage": 0
    }
  ],
  "payments": [
    {
      "paymentMethod": "CASH",
      "paymentAmount": 200.00
    }
  ],
  "notes": "Quick sale"
}
```

**Response** (201):
```json
{
  "success": true,
  "message": "Sale processed successfully",
  "data": {
    "invoiceId": 10001,
    "invoiceNumber": "INV-2025-010001",
    "invoiceDate": "2025-11-13",
    "invoiceTime": "14:30:25",
    "branch": {
      "branchId": 1,
      "branchName": "Main Branch"
    },
    "customer": null,
    "cashier": {
      "userId": 10,
      "username": "jane.cashier"
    },
    "items": [
      {
        "invoiceItemId": 50001,
        "lineNumber": 1,
        "itemName": "Coca Cola 500ml",
        "quantity": 2.00,
        "unitPrice": 60.00,
        "discountAmount": 0.00,
        "lineTotal": 120.00,
        "taxRate": 16.00,
        "taxAmount": 16.55,
        "finalAmount": 120.00
      },
      {
        "invoiceItemId": 50002,
        "lineNumber": 2,
        "itemName": "Bread Loaf",
        "quantity": 1.00,
        "unitPrice": 55.00,
        "discountAmount": 0.00,
        "lineTotal": 55.00,
        "taxRate": 16.00,
        "taxAmount": 7.59,
        "finalAmount": 55.00
      }
    ],
    "subtotal": 175.00,
    "discountAmount": 0.00,
    "taxAmount": 24.14,
    "totalAmount": 175.00,
    "payments": [
      {
        "paymentMethod": "CASH",
        "paymentAmount": 200.00
      }
    ],
    "paidAmount": 200.00,
    "changeAmount": 25.00,
    "paymentStatus": "PAID",
    "cuInvoiceNumber": "ABC123456789",
    "qrCodeUrl": "https://api.supasoft.com/invoices/qr/10001.png",
    "etimsStatus": "SUBMITTED",
    "invoiceStatus": "ACTIVE",
    "createdAt": "2025-11-13T14:30:25Z"
  }
}
```

### 6.3 Get Invoice by ID

**Endpoint**: `GET /sales/invoices/{id}`

**Response** (200): Invoice details (same structure as Create Sale response)

### 6.4 Search Invoices

**Endpoint**: `GET /sales/invoices`

**Query Parameters**:
- `branchId` (long, optional)
- `customerId` (long, optional)
- `cashierId` (long, optional)
- `fromDate` (date, optional)
- `toDate` (date, optional)
- `invoiceNumber` (string, optional)
- `cuInvoiceNumber` (string, optional)
- `page`, `size`, `sort`

**Response** (200): Paginated invoice list

### 6.5 Create Sales Return

**Endpoint**: `POST /sales/returns`

**Request**:
```json
{
  "originalInvoiceId": 10001,
  "branchId": 1,
  "customerId": null,
  "returnReason": "DEFECTIVE",
  "returnNotes": "Product damaged",
  "refundMethod": "CASH",
  "items": [
    {
      "invoiceItemId": 50001,
      "itemId": 1,
      "returnQuantity": 1.00,
      "unitPrice": 60.00
    }
  ]
}
```

**Response** (201):
```json
{
  "success": true,
  "message": "Sales return processed successfully",
  "data": {
    "returnId": 301,
    "returnNumber": "RET-2025-0301",
    "originalInvoiceNumber": "INV-2025-010001",
    "returnDate": "2025-11-13",
    "totalReturnAmount": 60.00,
    "refundMethod": "CASH",
    "creditNoteNumber": null,
    "status": "COMPLETED"
  }
}
```

### 6.6 Close Cashier Shift

**Endpoint**: `POST /cashier/shifts/{id}/close`

**Request**:
```json
{
  "closingCash": 15000.00
}
```

**Response** (200):
```json
{
  "success": true,
  "message": "Cashier shift closed successfully",
  "data": {
    "shiftId": 501,
    "shiftNumber": "SHIFT-2025-0501",
    "openingFloat": 5000.00,
    "closingCash": 15000.00,
    "expectedCash": 15200.00,
    "cashVariance": -200.00,
    "totalSales": 10200.00,
    "transactionCount": 45,
    "shiftStart": "2025-11-13T08:00:00Z",
    "shiftEnd": "2025-11-13T17:00:00Z",
    "status": "CLOSED"
  }
}
```

---

## 7. PURCHASE SERVICE

### 7.1 Create Purchase Order

**Endpoint**: `POST /purchases/orders`

**Request**:
```json
{
  "poNumber": "PO-2025-0001",
  "poDate": "2025-11-13",
  "supplierId": 5,
  "branchId": 1,
  "expectedDeliveryDate": "2025-11-20",
  "items": [
    {
      "lineNumber": 1,
      "itemId": 1,
      "uomId": 5,
      "orderedQuantity": 10.00,
      "unitPrice": 1000.00,
      "taxRate": 16.00
    }
  ],
  "notes": "Regular stock order",
  "termsAndConditions": "Payment within 30 days"
}
```

**Response** (201):
```json
{
  "success": true,
  "message": "Purchase order created successfully",
  "data": {
    "poId": 101,
    "poNumber": "PO-2025-0001",
    "poDate": "2025-11-13",
    "supplier": {
      "supplierId": 5,
      "supplierName": "ABC Distributors Ltd"
    },
    "branch": {
      "branchId": 1,
      "branchName": "Main Branch"
    },
    "items": [ ... ],
    "subtotal": 10000.00,
    "taxAmount": 1600.00,
    "totalAmount": 11600.00,
    "status": "DRAFT",
    "createdAt": "2025-11-13T10:00:00Z"
  }
}
```

### 7.2 Approve Purchase Order

**Endpoint**: `POST /purchases/orders/{id}/approve`

**Response** (200):
```json
{
  "success": true,
  "message": "Purchase order approved",
  "data": {
    "poId": 101,
    "status": "APPROVED",
    "approvedBy": 3,
    "approvedAt": "2025-11-13T11:00:00Z"
  }
}
```

### 7.3 Create GRN (Goods Received Note)

**Endpoint**: `POST /purchases/grn`

**Request**:
```json
{
  "grnNumber": "GRN-2025-0001",
  "grnDate": "2025-11-20",
  "poId": 101,
  "supplierId": 5,
  "branchId": 1,
  "supplierInvoiceNumber": "SI-12345",
  "supplierInvoiceDate": "2025-11-19",
  "items": [
    {
      "poItemId": 501,
      "itemId": 1,
      "uomId": 5,
      "orderedQuantity": 10.00,
      "receivedQuantity": 10.00,
      "acceptedQuantity": 9.00,
      "rejectedQuantity": 1.00,
      "unitCost": 1000.00,
      "batchNumber": "BATCH-2025-050",
      "manufactureDate": "2025-10-01",
      "expiryDate": "2026-10-01",
      "rejectionReason": "Damaged carton"
    }
  ]
}
```

**Response** (201): GRN details

### 7.4 Post GRN to Stock

**Endpoint**: `POST /purchases/grn/{id}/post`

**Response** (200):
```json
{
  "success": true,
  "message": "GRN posted to stock successfully",
  "data": {
    "grnId": 201,
    "status": "POSTED",
    "postedAt": "2025-11-20T15:00:00Z"
  }
}
```

---

## 8. TRANSFER SERVICE

### 8.1 Create Stock Transfer

**Endpoint**: `POST /transfers`

**Request**:
```json
{
  "transferNumber": "TRF-2025-0001",
  "transferDate": "2025-11-13",
  "fromBranchId": 1,
  "toBranchId": 2,
  "transferType": "STANDARD",
  "items": [
    {
      "lineNumber": 1,
      "itemId": 1,
      "uomId": 1,
      "batchNumber": "BATCH-2025-001",
      "requestedQuantity": 100.00
    }
  ],
  "notes": "Branch restock"
}
```

**Response** (201): Transfer details

### 8.2 Approve Transfer

**Endpoint**: `POST /transfers/{id}/approve`

**Response** (200): Approved transfer details

### 8.3 Send Transfer (Mark In-Transit)

**Endpoint**: `POST /transfers/{id}/send`

**Request**:
```json
{
  "items": [
    {
      "transferItemId": 1001,
      "sentQuantity": 100.00
    }
  ]
}
```

**Response** (200):
```json
{
  "success": true,
  "message": "Transfer marked as in-transit",
  "data": {
    "transferId": 301,
    "status": "IN_TRANSIT",
    "sentAt": "2025-11-13T10:00:00Z"
  }
}
```

### 8.4 Receive Transfer

**Endpoint**: `POST /transfers/{id}/receive`

**Request**:
```json
{
  "items": [
    {
      "transferItemId": 1001,
      "receivedQuantity": 98.00,
      "varianceReason": "2 units damaged in transit"
    }
  ]
}
```

**Response** (200):
```json
{
  "success": true,
  "message": "Transfer received successfully",
  "data": {
    "transferId": 301,
    "status": "RECEIVED",
    "receivedAt": "2025-11-14T14:00:00Z",
    "hasVariance": true
  }
}
```

---

## 9. REPORTS SERVICE

### 9.1 Generate Daily Sales Report

**Endpoint**: `GET /reports/sales/daily`

**Query Parameters**:
- `branchId` (long, required)
- `date` (date, required)
- `format` (string, optional: "JSON", "PDF", "EXCEL")

**Response** (200):
```json
{
  "success": true,
  "data": {
    "reportType": "DAILY_SALES_SUMMARY",
    "branchId": 1,
    "branchName": "Main Branch",
    "date": "2025-11-13",
    "totalSales": 125000.00,
    "totalProfit": 18500.00,
    "profitMargin": 14.80,
    "transactionCount": 145,
    "averageTransactionValue": 862.07,
    "topSellingItems": [
      {
        "itemId": 1,
        "itemName": "Coca Cola 500ml",
        "quantitySold": 250.00,
        "totalSales": 15000.00
      }
    ],
    "salesByCategory": [
      {
        "categoryName": "Beverages",
        "totalSales": 45000.00,
        "percentage": 36.00
      }
    ],
    "hourlyBreakdown": [
      {
        "hour": "08:00",
        "sales": 5000.00,
        "transactions": 8
      }
    ],
    "paymentMethodBreakdown": [
      {
        "paymentMethod": "CASH",
        "amount": 75000.00,
        "percentage": 60.00
      },
      {
        "paymentMethod": "MPESA",
        "amount": 50000.00,
        "percentage": 40.00
      }
    ]
  }
}
```

### 9.2 Generate Stock Valuation Report

**Endpoint**: `GET /reports/stock/valuation`

**Query Parameters**:
- `branchId` (long, optional)
- `categoryId` (long, optional)
- `asOfDate` (date, optional, default: today)
- `format` (string, optional)

**Response** (200):
```json
{
  "success": true,
  "data": {
    "reportType": "STOCK_VALUATION",
    "asOfDate": "2025-11-13",
    "branches": [
      {
        "branchId": 1,
        "branchName": "Main Branch",
        "totalValue": 2500000.00,
        "itemCount": 1500,
        "categories": [
          {
            "categoryName": "Beverages",
            "totalValue": 450000.00,
            "itemCount": 250
          }
        ]
      }
    ],
    "grandTotal": 2500000.00
  }
}
```

### 9.3 Generate Fast/Slow Moving Items Report

**Endpoint**: `GET /reports/stock/movement-analysis`

**Query Parameters**:
- `branchId` (long, optional)
- `fromDate` (date, required)
- `toDate` (date, required)
- `movementType` (string: "FAST", "SLOW", "ALL")

**Response** (200):
```json
{
  "success": true,
  "data": {
    "reportType": "STOCK_MOVEMENT_ANALYSIS",
    "period": {
      "from": "2025-10-01",
      "to": "2025-11-13"
    },
    "fastMovingItems": [
      {
        "itemId": 1,
        "itemName": "Coca Cola 500ml",
        "quantitySold": 2500.00,
        "totalSales": 150000.00,
        "turnoverRatio": 12.5,
        "averageDailySales": 55.56
      }
    ],
    "slowMovingItems": [
      {
        "itemId": 150,
        "itemName": "Specialty Item XYZ",
        "quantitySold": 5.00,
        "totalSales": 500.00,
        "turnoverRatio": 0.2,
        "averageDailySales": 0.11,
        "daysOfStock": 450
      }
    ]
  }
}
```

### 9.4 Generate Expiry Tracking Report

**Endpoint**: `GET /reports/stock/expiry`

**Query Parameters**:
- `branchId` (long, optional)
- `daysAhead` (int, default: 30)

**Response** (200):
```json
{
  "success": true,
  "data": {
    "reportType": "EXPIRY_TRACKING",
    "daysAhead": 30,
    "expiredItems": [
      {
        "itemName": "Milk 1L",
        "batchNumber": "BATCH-2024-100",
        "expiryDate": "2025-11-10",
        "quantityOnHand": 12.00,
        "totalValue": 840.00,
        "daysOverdue": 3
      }
    ],
    "expiringItems": [
      {
        "itemName": "Yogurt 500g",
        "batchNumber": "BATCH-2025-200",
        "expiryDate": "2025-11-25",
        "quantityOnHand": 50.00,
        "totalValue": 3000.00,
        "daysToExpiry": 12
      }
    ],
    "totalExpiredValue": 840.00,
    "totalExpiringValue": 3000.00
  }
}
```

---

## 10. INTEGRATION SERVICE

### 10.1 Submit Invoice to KRA eTIMS

**Endpoint**: `POST /integrations/etims/submit-invoice`

**Request**:
```json
{
  "invoiceId": 10001
}
```

**Response** (200):
```json
{
  "success": true,
  "message": "Invoice submitted to KRA eTIMS successfully",
  "data": {
    "cuInvoiceNumber": "ABC123456789",
    "qrCode": "https://api.supasoft.com/invoices/qr/10001.png",
    "status": "APPROVED",
    "timestamp": "2025-11-13T14:35:00Z"
  }
}
```

### 10.2 Initiate M-Pesa STK Push

**Endpoint**: `POST /integrations/mpesa/stk-push`

**Request**:
```json
{
  "invoiceId": 10001,
  "phoneNumber": "254712345678",
  "amount": 175.00,
  "accountReference": "INV-2025-010001",
  "transactionDesc": "Payment for invoice"
}
```

**Response** (200):
```json
{
  "success": true,
  "message": "STK Push initiated successfully",
  "data": {
    "merchantRequestId": "29115-34620561-1",
    "checkoutRequestId": "ws_CO_191220191020363925",
    "responseCode": "0",
    "responseDescription": "Success. Request accepted for processing",
    "customerMessage": "Success. Request accepted for processing"
  }
}
```

### 10.3 M-Pesa Callback (Webhook)

**Endpoint**: `POST /integrations/mpesa/callback`

**Note**: This endpoint receives callbacks from Safaricom M-Pesa API

**Request** (from M-Pesa):
```json
{
  "Body": {
    "stkCallback": {
      "MerchantRequestID": "29115-34620561-1",
      "CheckoutRequestID": "ws_CO_191220191020363925",
      "ResultCode": 0,
      "ResultDesc": "The service request is processed successfully.",
      "CallbackMetadata": {
        "Item": [
          {
            "Name": "Amount",
            "Value": 175.00
          },
          {
            "Name": "MpesaReceiptNumber",
            "Value": "QJK12345ABC"
          },
          {
            "Name": "TransactionDate",
            "Value": 20251113143015
          },
          {
            "Name": "PhoneNumber",
            "Value": 254712345678
          }
        ]
      }
    }
  }
}
```

**Response** (200):
```json
{
  "ResultCode": 0,
  "ResultDesc": "Accepted"
}
```

---

## 11. NOTIFICATION SERVICE

### 11.1 Send SMS Notification

**Endpoint**: `POST /notifications/sms`

**Request**:
```json
{
  "recipient": "254712345678",
  "message": "Your order #12345 is ready for pickup.",
  "priority": "NORMAL"
}
```

**Response** (201):
```json
{
  "success": true,
  "message": "SMS queued for delivery",
  "data": {
    "notificationId": 5001,
    "status": "PENDING"
  }
}
```

### 11.2 Send Email Notification

**Endpoint**: `POST /notifications/email`

**Request**:
```json
{
  "recipient": "customer@example.com",
  "subject": "Your Invoice",
  "message": "Thank you for your purchase...",
  "attachments": [
    {
      "fileName": "invoice.pdf",
      "base64Content": "JVBERi0xLjQK..."
    }
  ]
}
```

**Response** (201): Notification details

---

## 12. VALIDATION RULES

### 12.1 Item Validation
- `itemCode`: Required, unique, alphanumeric, max 50 characters
- `itemName`: Required, max 200 characters
- `barcode`: Optional, unique if provided, valid barcode format
- `vatRate`: Decimal (0-100), default 16.00

### 12.2 Price Validation
- `costPrice`: Required, > 0
- `sellingPrice`: Required, > costPrice (warning if < costPrice)
- `effectiveFrom`: Required, cannot be in past (except admin)
- `effectiveTo`: Must be > effectiveFrom if provided

### 12.3 Stock Validation
- `quantity`: Required, > 0
- `averageCost`: Required, >= 0
- `expiryDate`: Must be > today (warning if < 30 days)

### 12.4 Sales Validation
- `quantity`: Required, > 0, <= available stock
- `paymentAmount`: Sum of all payments must equal total amount
- `customerId`: Required if total > credit limit threshold

---

## 13. ERROR CODES

| Error Code | HTTP Status | Description |
|-----------|-------------|-------------|
| `AUTH_001` | 401 | Invalid credentials |
| `AUTH_002` | 401 | Token expired |
| `AUTH_003` | 403 | Insufficient permissions |
| `ITEM_001` | 404 | Item not found |
| `ITEM_002` | 409 | Duplicate item code |
| `ITEM_003` | 409 | Duplicate barcode |
| `STOCK_001` | 422 | Insufficient stock |
| `STOCK_002` | 409 | Stock already exists |
| `SALE_001` | 422 | Invalid payment amount |
| `SALE_002` | 422 | Total mismatch |
| `PO_001` | 404 | Purchase order not found |
| `PO_002` | 422 | PO already received |
| `TRANSFER_001` | 422 | Cannot transfer to same branch |
| `PRICE_001` | 404 | Price not found |
| `ETIMS_001` | 500 | KRA eTIMS API error |
| `MPESA_001` | 500 | M-Pesa API error |
| `VALIDATION_001` | 400 | Validation error |
| `SYSTEM_001` | 500 | Internal server error |

---

## 14. RATE LIMITING

- **Default**: 100 requests per minute per user
- **Authentication endpoints**: 5 requests per minute per IP
- **Report generation**: 10 requests per minute per user
- **Headers returned**:
  - `X-RateLimit-Limit`: Maximum requests allowed
  - `X-RateLimit-Remaining`: Remaining requests
  - `X-RateLimit-Reset`: Time when limit resets (Unix timestamp)

---

## 15. WEBHOOK EVENTS

### 15.1 Supported Events
- `sale.completed`: Triggered when a sale is completed
- `stock.low_level`: Triggered when stock reaches reorder level
- `stock.expired`: Triggered when stock expires
- `transfer.received`: Triggered when transfer is received
- `po.approved`: Triggered when PO is approved
- `price.changed`: Triggered when price is updated

### 15.2 Webhook Payload Format
```json
{
  "eventId": "evt_12345",
  "eventType": "sale.completed",
  "timestamp": "2025-11-13T14:30:00Z",
  "data": {
    "invoiceId": 10001,
    "invoiceNumber": "INV-2025-010001",
    "totalAmount": 175.00
  }
}
```

---

**Document Version**: 1.0  
**Last Updated**: November 2025  
**API Version**: v1  
**Contact**: api-support@supasoft.com

