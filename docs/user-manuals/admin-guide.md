# Supasoft - Administrator User Guide

## Welcome to Supasoft

This guide will help you understand and effectively use the Supasoft Supermarket Management System as an Administrator or Manager.

---

## TABLE OF CONTENTS

1. [Getting Started](#1-getting-started)
2. [Dashboard Overview](#2-dashboard-overview)
3. [User Management](#3-user-management)
4. [Item Management](#4-item-management)
5. [Pricing Management](#5-pricing-management)
6. [Stock Management](#6-stock-management)
7. [Purchase Management](#7-purchase-management)
8. [Sales Management](#8-sales-management)
9. [Transfer Management](#9-transfer-management)
10. [Reports](#10-reports)
11. [System Settings](#11-system-settings)
12. [Troubleshooting](#12-troubleshooting)

---

## 1. GETTING STARTED

### 1.1 Logging In

1. Open your web browser
2. Navigate to: `https://app.supasoft.com`
3. Enter your **Username** and **Password**
4. Click **"Login"**

**First Time Login:**
- You will be prompted to change your password
- Choose a strong password (min 8 characters, including uppercase, lowercase, number, and special character)

**Forgot Password:**
1. Click **"Forgot Password?"** on the login page
2. Enter your email address
3. Check your email for password reset link
4. Follow instructions to create a new password

### 1.2 Navigating the Interface

**Main Navigation Menu** (Left sidebar):
- 📊 Dashboard
- 👥 Users
- 🏷️ Items
- 💰 Pricing
- 📦 Stock
- 🛒 Purchases
- 💵 Sales
- 🔄 Transfers
- 📈 Reports
- ⚙️ Settings

**Top Bar:**
- Branch selector (if multi-branch)
- Notifications bell
- User profile menu
- Logout button

---

## 2. DASHBOARD OVERVIEW

The dashboard provides a real-time overview of your business operations.

### 2.1 Key Metrics (Top Cards)

**Today's Sales**
- Shows total sales for the current day
- Includes number of transactions
- Compares with yesterday (% change)

**Stock Value**
- Total value of inventory on hand
- Based on weighted average cost
- Updates in real-time

**Low Stock Alerts**
- Number of items below reorder level
- Click to view details
- Red badge indicates critical items

**Pending Approvals**
- Purchase orders awaiting approval
- Stock adjustments pending review
- Transfers needing authorization

### 2.2 Sales Chart

**Graph showing:**
- Sales trends (last 7 days, 30 days, or custom range)
- Comparison with previous period
- Peak sales hours

**Actions:**
- Change date range using date picker
- Toggle between daily/weekly/monthly view
- Export data to Excel

### 2.3 Quick Actions

**Common tasks accessible from dashboard:**
- Create New Item
- New Purchase Order
- Stock Adjustment
- Generate Report

---

## 3. USER MANAGEMENT

### 3.1 Viewing Users

**Navigate to:** Users > All Users

**User List shows:**
- Username
- Full Name
- Email
- Role (Admin, Manager, Cashier, Stock Keeper)
- Branch
- Status (Active/Inactive)
- Last Login

**Search & Filter:**
- Search by name, username, or email
- Filter by role
- Filter by branch
- Filter by status (Active/Inactive)

### 3.2 Creating a New User

1. Click **"+ New User"** button
2. Fill in user details:
   - **First Name**: User's first name
   - **Last Name**: User's last name
   - **Username**: Login username (unique, lowercase, no spaces)
   - **Email**: Valid email address
   - **Phone Number**: Contact number
   - **Role**: Select appropriate role
   - **Branch**: Assign to branch (if applicable)
3. Click **"Save"**

**Auto-generated password will be sent to user's email**

**User Roles:**

| Role | Permissions | Typical Use |
|------|-------------|-------------|
| **Super Admin** | Full system access, manage all branches | System administrators |
| **Admin** | Manage single branch, create users | Branch managers |
| **Manager** | Manage inventory, view reports | Department managers |
| **Cashier** | Process sales, returns | POS operators |
| **Stock Keeper** | Stock management, adjustments | Warehouse staff |

### 3.3 Editing User Details

1. Click on user name from user list
2. Modify required fields
3. Click **"Update User"**

**Editable fields:**
- Name, email, phone
- Role (requires admin permission)
- Branch assignment
- Status (Active/Inactive)

### 3.4 Resetting User Password

**Option A: Admin Reset**
1. Go to user details
2. Click **"Reset Password"**
3. New password sent to user's email

**Option B: User Self-Reset**
1. User clicks "Forgot Password" on login
2. Follows email instructions

### 3.5 Deactivating Users

**To temporarily disable access:**
1. Go to user details
2. Change status to **"Inactive"**
3. Click **"Update"**

**To permanently delete:**
1. Go to user details
2. Click **"Delete User"**
3. Confirm deletion (cannot be undone)

**Note:** Deleted users cannot be restored. Consider deactivating instead.

---

## 4. ITEM MANAGEMENT

### 4.1 Viewing Items

**Navigate to:** Items > All Items

**Item catalog shows:**
- Item image (thumbnail)
- Item code
- Item name
- Category
- Brand
- Stock status indicator
- Status (Active/Inactive)

**Search & Filter:**
- Search by name, code, or barcode
- Filter by category
- Filter by brand
- Filter by status
- Sort by name, code, or recently added

### 4.2 Creating a New Item

1. Click **"+ Add Item"**
2. Fill in **Basic Information** tab:
   - **Item Code**: Unique code (auto-generated if left blank)
   - **Item Name**: Full product name
   - **Description**: Detailed description
   - **Category**: Select from dropdown (create new if needed)
   - **Brand**: Select brand
   - **Base UOM**: Unit of Measure (e.g., Piece, Kg, Liter)

3. Fill in **Pricing & Tax** tab:
   - **Is Taxable**: Check if VAT applies
   - **VAT Rate**: Default 16% (adjust if needed)
   - **Tax Category**: Standard, Zero-rated, Exempt

4. Fill in **Inventory** tab:
   - **Track Batches**: Enable for batch tracking
   - **Track Expiry**: Enable for expiry dates
   - **Track Serial Numbers**: Enable for serialized items
   - **Reorder Level**: Minimum quantity before alert
   - **Reorder Quantity**: Suggested order quantity

5. Click **"Save Item"**

### 4.3 Managing Barcodes

**Adding Barcode:**
1. Open item details
2. Go to **"Barcodes"** tab
3. Click **"+ Add Barcode"**
4. Enter barcode number (or click "Generate" for auto-generation)
5. Select UOM (if multiple pack sizes)
6. Mark as primary barcode (optional)
7. Click **"Save"**

**Barcode Types Supported:**
- EAN-13 (most common)
- EAN-8
- Code-128
- QR Code

**Printing Barcodes:**
1. Select items from list (checkbox)
2. Click **"Print Barcodes"**
3. Choose label format
4. Click **"Print"**

### 4.4 Managing Categories

**Creating Category:**
1. Navigate to Items > Categories
2. Click **"+ New Category"**
3. Enter category name and code
4. Select parent category (for sub-categories)
5. Upload category image (optional)
6. Click **"Save"**

**Category Hierarchy Example:**
```
Beverages (Parent)
├── Soft Drinks
├── Juices
├── Water
└── Energy Drinks
```

### 4.5 Managing Units of Measure (UOM)

**Common UOMs:**
- Piece (PCS)
- Kilogram (KG)
- Liter (L)
- Dozen (DOZ)
- Carton (CTN)
- Pack (PACK)

**Creating Custom UOM:**
1. Navigate to Items > UOM
2. Click **"+ New UOM"**
3. Enter UOM name and code
4. Set conversion factor (if derived unit)
   - Example: 1 Dozen = 12 Pieces
5. Click **"Save"**

**Item-Specific UOM:**

Example: Coca Cola 500ml can be sold as:
- 1 Piece (retail)
- 1 Pack (6 pieces)
- 1 Carton (24 pieces)

**To add alternative UOMs:**
1. Open item details
2. Go to **"UOMs"** tab
3. Click **"+ Add UOM"**
4. Select UOM
5. Enter conversion factor (e.g., 1 Carton = 24 Pieces)
6. Optionally add UOM-specific barcode
7. Click **"Save"**

### 4.6 Bulk Import Items

**To import multiple items at once:**

1. Navigate to Items > Import
2. Download Excel template
3. Fill in item details in Excel
4. Upload completed file
5. Review import preview
6. Click **"Confirm Import"**

**Excel Template Columns:**
- Item Code
- Item Name
- Description
- Category Code
- Brand Code
- Base UOM
- Barcode
- VAT Rate
- Reorder Level

**Tips:**
- Ensure category and brand codes exist before import
- Use unique barcodes
- Leave Item Code blank for auto-generation
- Check for duplicate barcodes

---

## 5. PRICING MANAGEMENT

### 5.1 Price Types

Supasoft supports multiple price types:

| Price Type | Description | Typical Use |
|-----------|-------------|-------------|
| **Retail** | Standard retail price | Regular customers |
| **Wholesale** | Discounted bulk price | B2B customers |
| **Special** | Promotional price | Sales campaigns |
| **Member** | Loyalty member price | Loyalty program |

### 5.2 Setting Item Prices

**Single Item Pricing:**

1. Navigate to Pricing > Manage Prices
2. Search for item
3. Click **"Set Price"**
4. Fill in price details:
   - **Price Type**: Retail, Wholesale, etc.
   - **UOM**: Select unit
   - **Branch**: Select branch (or All Branches)
   - **Cost Price**: Purchase cost
   - **Selling Price**: Customer price
   - **Effective From**: Start date
   - **Effective To**: End date (optional)
5. System auto-calculates:
   - Markup %
   - Profit Margin
6. Click **"Save Price"**

**Price Validation:**
- ⚠️ Warning if selling price < cost price (loss)
- ✓ Green check if profit margin is healthy

**Branch-Specific Pricing:**

Enable different prices per branch:
- Main branch: Higher rent → Higher prices
- Suburban branch: Lower rent → Competitive prices

### 5.3 Bulk Price Updates

**Scenario: Increase all prices by 10%**

1. Navigate to Pricing > Bulk Update
2. Select items (or select all)
3. Choose update method:
   - **Percentage Increase**: +10%
   - **Percentage Decrease**: -5%
   - **Fixed Amount**: +KES 10
   - **Set Margin**: Target 30% margin
4. Preview changes
5. Click **"Apply Updates"**

### 5.4 Managing Promotions

**Creating a Promotion:**

1. Navigate to Pricing > Promotions
2. Click **"+ New Promotion"**
3. Fill in promotion details:
   - **Promotion Code**: Unique code
   - **Promotion Name**: Customer-facing name
   - **Description**: Details
   - **Type**: Select promotion type
   - **Start Date**: When it begins
   - **End Date**: When it expires
4. Configure discount:
   - **Percentage Discount**: e.g., 20% off
   - **Fixed Discount**: e.g., KES 50 off
   - **Buy X Get Y**: e.g., Buy 2 Get 1 Free
5. Select applicable items or categories
6. Set restrictions (optional):
   - Minimum purchase amount
   - Maximum discount limit
   - Usage limit
7. Click **"Save Promotion"**

**Promotion Types:**

**1. Percentage Discount**
- Example: 20% off all beverages
- Applied at checkout

**2. Fixed Amount Discount**
- Example: KES 100 off purchases over KES 1000
- Conditional discount

**3. Buy X Get Y**
- Example: Buy 2 Get 1 Free
- Example: Buy 3 Pay for 2
- Automatic calculation

**4. Bundle Deal**
- Example: Bread + Milk = KES 150 (normally KES 180)
- Requires specific items

**Activating/Deactivating Promotions:**
- Toggle switch on promotion list
- Automatic activation/deactivation based on dates

### 5.5 Price History

**View price changes:**
1. Open item details
2. Go to **"Price History"** tab
3. View chronological price changes:
   - Date changed
   - Old price
   - New price
   - Changed by
   - Reason

**Export price history:**
- Click **"Export to Excel"**
- Useful for pricing analysis

---

## 6. STOCK MANAGEMENT

### 6.1 Viewing Stock Levels

**Navigate to:** Stock > Stock Balance

**Stock balance shows:**
- Item details
- Branch location
- Quantity on hand
- Quantity reserved (pending sales)
- Available quantity
- Average cost
- Total value
- Warehouse location
- Batch/Serial number (if tracked)
- Expiry date (if tracked)

**Color Indicators:**
- 🟢 Green: Adequate stock
- 🟡 Yellow: Approaching reorder level
- 🔴 Red: Below reorder level
- ⚫ Black: Out of stock

### 6.2 Stock Movements

**View transaction history:**

1. Navigate to Stock > Movements
2. Filter by:
   - Date range
   - Item
   - Branch
   - Movement type
3. View detailed movements:
   - Purchase (stock in)
   - Sale (stock out)
   - Adjustment
   - Transfer in/out
   - Return
   - Repackage

**Export movements:**
- Select date range
- Click **"Export Report"**
- Choose PDF or Excel

### 6.3 Stock Adjustments

**When to use:**
- Physical stock count results differ from system
- Damaged goods
- Expired products
- Theft/shrinkage
- System errors

**Creating Stock Adjustment:**

1. Navigate to Stock > Adjustments
2. Click **"+ New Adjustment"**
3. Fill in details:
   - **Adjustment Number**: Auto-generated
   - **Branch**: Select branch
   - **Adjustment Type**: Increase or Decrease
   - **Reason**: 
     - Damage
     - Expiry
     - Theft
     - Stock Take
     - Error Correction
     - Other
   - **Date**: Adjustment date
4. Add items:
   - Click **"+ Add Item"**
   - Select item
   - Enter **System Quantity** (from records)
   - Enter **Actual Quantity** (physical count)
   - System calculates **Variance**
   - Add notes (explain variance)
5. Upload photo evidence (optional but recommended)
6. Click **"Save as Draft"**

**Adjustment Workflow:**

```
Draft → Submit for Approval → Approved → Posted to Stock
  ↓                              ↓
Save/Edit                    Rejected → Revise
```

**Approving Adjustments:**

(Manager/Admin only)

1. Navigate to Stock > Adjustments
2. Filter by **"Pending Approval"**
3. Click on adjustment to review
4. Check:
   - Variance is reasonable
   - Notes explain the variance
   - Photo evidence (if provided)
5. Click **"Approve"** or **"Reject"**
6. If rejected, add reason for rejection

**Posting Adjustment:**

After approval:
1. Adjustment automatically updates stock balance
2. Stock movement record created
3. Notification sent to relevant users

### 6.4 Stock Taking (Physical Count)

**Full Stock Take Process:**

**Preparation (Day Before):**
1. Navigate to Stock > Stock Take
2. Click **"+ New Stock Take"**
3. Select branch
4. Select count date
5. Assign counters (users who will count)
6. Click **"Initiate Stock Take"**

**Counting Day:**
1. Counters log in to mobile app or system
2. Scan barcodes or manually enter counts
3. Enter actual quantity found
4. Take photos of problematic items
5. Submit counts

**Review & Reconciliation:**
1. Navigate to Stock > Stock Take
2. Open the stock take session
3. Review variances:
   - Green: Match (system = actual)
   - Yellow: Minor variance (< 5%)
   - Red: Major variance (> 5%)
4. For major variances:
   - Request recount
   - Investigate cause
   - Document reason
5. Generate stock adjustment
6. Submit for approval

**Cycle Counting:**

Instead of full stock take, count subsets regularly:
- Monday: Category A (High value items)
- Tuesday: Category B (Fast movers)
- Wednesday: Category C (Slow movers)
- Etc.

### 6.5 Repackaging

**Use Case:**
- Buy in bulk (cartons)
- Sell in smaller units (pieces)

**Example:**
- Purchase: 10 Cartons of Soap (1 Carton = 24 Pieces)
- Repackage: Convert 5 Cartons → 120 Pieces for retail

**Repackaging Process:**

1. Navigate to Stock > Repackaging
2. Click **"+ New Repackage"**
3. Fill in source details:
   - **Source Item**: Soap - Carton
   - **Source UOM**: Carton
   - **Source Quantity**: 5
   - **Batch Number**: BATCH-001
4. Fill in destination details:
   - **Destination Item**: Soap - Piece (or same item)
   - **Destination UOM**: Piece
   - **Destination Quantity**: 120 (auto-calculated)
   - **New Batch Number**: BATCH-001-R
5. System calculates:
   - Cost per piece = (Carton cost × 5) ÷ 120
6. Click **"Save Repackage"**

**Result:**
- Stock reduced: 5 Cartons
- Stock increased: 120 Pieces
- Cost allocated proportionally

### 6.6 Expiry Management

**Viewing Items Near Expiry:**

1. Navigate to Stock > Expiry Tracking
2. View items grouped by:
   - **Expired**: Already past expiry date
   - **Expiring Soon**: Within 30 days
   - **Expiring**: Within 90 days
3. Sort by expiry date or value

**Actions for Expiring Items:**
- Mark down prices (create promotion)
- Transfer to high-volume branch
- Return to supplier (if possible)
- Dispose (create stock adjustment)

**Disposal Process:**
1. Select expired items
2. Click **"Create Disposal Adjustment"**
3. Upload disposal evidence (photos)
4. Submit for approval
5. After approval, stock reduced

**FIFO (First In, First Out):**

System automatically uses oldest batches first during sales.

Example:
- Batch A: Expiry 2025-12-31 (100 units)
- Batch B: Expiry 2026-03-31 (150 units)
- Sale: 80 units → Taken from Batch A

### 6.7 Stock Valuation

**View total stock value:**

1. Navigate to Reports > Stock Valuation
2. Select:
   - Branch (or all branches)
   - Category (or all categories)
   - As of date
3. Report shows:
   - Item-by-item valuation
   - Quantities on hand
   - Average cost per unit
   - Total value
4. Export to Excel for analysis

**Valuation Method:**

Supasoft uses **Weighted Average Cost (WAC)** method.

**Example:**
- Purchase 1: 100 units @ KES 50 = KES 5,000
- Purchase 2: 50 units @ KES 60 = KES 3,000
- Total: 150 units = KES 8,000
- WAC = KES 8,000 ÷ 150 = KES 53.33 per unit

**Benefits:**
- Smooths out price fluctuations
- Consistent cost basis
- Accepted by tax authorities

---

## 7. PURCHASE MANAGEMENT

### 7.1 Managing Suppliers

**Adding a New Supplier:**

1. Navigate to Purchases > Suppliers
2. Click **"+ New Supplier"**
3. Fill in supplier details:
   - **Supplier Code**: Unique code (auto-generated)
   - **Supplier Name**: Company name
   - **Contact Person**: Primary contact
   - **Phone Number**: Contact number
   - **Email**: Email address
   - **Address**: Physical location
   - **Payment Terms**: Days (e.g., 30 days)
   - **Credit Limit**: Maximum credit allowed
   - **Tax PIN**: KRA PIN number
   - **Bank Details**: For payments
4. Click **"Save Supplier"**

**Supplier Performance:**

View supplier metrics:
- Total purchases (value)
- On-time delivery rate
- Quality issues
- Average lead time

### 7.2 Creating Purchase Orders

**Purchase Order Process:**

```
Create PO → Submit for Approval → Approved → Send to Supplier →
Receive Goods → Post GRN → Stock Updated
```

**Creating a Purchase Order:**

1. Navigate to Purchases > Purchase Orders
2. Click **"+ New Purchase Order"**
3. Fill in PO header:
   - **PO Number**: Auto-generated
   - **Supplier**: Select from dropdown
   - **Branch**: Receiving branch
   - **Expected Delivery Date**: Estimated arrival
   - **Delivery Address**: Shipping address
4. Add items:
   - Click **"+ Add Line Item"**
   - Select item
   - Select UOM
   - Enter **Ordered Quantity**
   - Enter **Unit Price** (negotiated price)
   - System calculates line total
   - Repeat for all items
5. Add **Notes** (special instructions)
6. Add **Terms and Conditions**
7. Review totals:
   - Subtotal
   - VAT
   - Total Amount
8. Click **"Save as Draft"** or **"Submit for Approval"**

**Approval Workflow:**

Approval required for:
- All POs (standard)
- POs above certain value (e.g., > KES 50,000)

**Approving Purchase Orders:**

(Manager/Admin only)

1. Navigate to Purchases > Pending Approvals
2. Click on PO to review
3. Check:
   - Supplier is approved
   - Prices are reasonable
   - Quantities match needs
   - Budget availability
4. Click **"Approve"** or **"Reject"**
5. If approved, PO can be sent to supplier

**Sending PO to Supplier:**

After approval:
1. Open approved PO
2. Click **"Send to Supplier"**
3. Choose delivery method:
   - Email (PDF attached)
   - Print & Fax
   - Download PDF
4. PO status changes to **"Sent"**

### 7.3 Receiving Goods (GRN)

**GRN (Goods Received Note) Process:**

When goods arrive from supplier:

1. Navigate to Purchases > Goods Received
2. Click **"+ New GRN"**
3. Select related Purchase Order (if any)
4. Fill in GRN details:
   - **GRN Number**: Auto-generated
   - **Supplier**: Auto-filled from PO
   - **Supplier Invoice Number**: Supplier's invoice #
   - **Supplier Invoice Date**: Invoice date
   - **Received Date**: Today's date
5. For each line item:
   - **Ordered Quantity**: From PO
   - **Received Quantity**: Actually received
   - **Accepted Quantity**: Good quality
   - **Rejected Quantity**: Damaged/wrong items
   - **Batch Number**: From product packaging
   - **Manufacture Date**: Manufacturing date
   - **Expiry Date**: Expiry date
   - **Notes**: Any issues
6. Upload delivery note (optional)
7. Click **"Save GRN"**

**Quality Check:**

Before accepting goods:
- ✓ Check quantities match
- ✓ Inspect for damage
- ✓ Verify expiry dates
- ✓ Confirm items match order
- ✓ Check batch numbers

**Rejected Items:**

If items are rejected:
1. Enter **Rejected Quantity**
2. Select **Rejection Reason**:
   - Damaged
   - Expired
   - Wrong item
   - Poor quality
3. Take photo evidence
4. Create **Debit Note** for supplier

**Variance Handling:**

If received quantity ≠ ordered quantity:
- **Shortage**: Contact supplier, amend PO
- **Excess**: Accept or return excess

**Posting GRN:**

After quality check:
1. Open GRN
2. Review all entries
3. Click **"Post GRN"**
4. Confirm posting
5. Stock automatically updated
6. Cost added to inventory

**GRN Status Flow:**
```
Draft → Quality Check → Approved → Posted → Stock Updated
```

### 7.4 Purchase Returns

**Returning Goods to Supplier:**

1. Navigate to Purchases > Returns
2. Click **"+ New Purchase Return"**
3. Select GRN (source of goods)
4. Select items to return
5. Enter return quantity
6. Select return reason:
   - Defective
   - Wrong item delivered
   - Damaged in transit
   - Expired
   - Other
7. Upload photos
8. Click **"Submit Return"**

**Debit Note Generated:**
- Reduces amount owed to supplier
- Reference for refund/credit

### 7.5 Supplier Payments

**Recording Payment:**

1. Navigate to Purchases > Payments
2. Click **"+ Record Payment"**
3. Select supplier
4. View outstanding invoices
5. Select invoices to pay
6. Enter payment details:
   - Payment method (Cash, Bank Transfer, Cheque)
   - Payment amount
   - Payment reference
   - Payment date
7. Click **"Save Payment"**

**Outstanding Balance:**
- View what's owed to each supplier
- Filter by overdue invoices
- Payment aging report

---

## 8. SALES MANAGEMENT

### 8.1 Viewing Sales Invoices

**Navigate to:** Sales > Invoices

**Invoice list shows:**
- Invoice number
- Date and time
- Customer name (if registered)
- Cashier
- Total amount
- Payment status
- KRA eTIMS status

**Search & Filter:**
- Search by invoice number or customer
- Filter by date range
- Filter by branch
- Filter by cashier
- Filter by payment method
- Filter by status

### 8.2 Invoice Details

**Click on invoice to view:**
- Customer information
- Itemized list:
  - Item name
  - Quantity
  - Unit price
  - Discounts applied
  - Line total
  - VAT amount
- Payment breakdown
- KRA details (CU Invoice Number, QR Code)

**Actions available:**
- View receipt
- Print receipt
- Email receipt to customer
- Process return
- Void invoice (with proper authorization)

### 8.3 Sales Returns

**Processing a Return:**

1. Navigate to Sales > Returns
2. Click **"+ New Return"**
3. Search for original invoice (by number or scan receipt)
4. Select items to return
5. Enter return quantity (can be partial)
6. Select return reason:
   - Defective product
   - Expired product
   - Wrong item sold
   - Customer changed mind
   - Other
7. Take photos if product is defective
8. Select refund method:
   - Cash refund
   - M-Pesa refund
   - Credit note (for future purchases)
9. Click **"Process Return"**

**Authorization:**
- Returns > KES 1,000 require manager approval
- Returns > 7 days old require admin approval

**Credit Note:**
- If customer chooses credit note
- Unique credit note number issued
- Can be used for future purchases
- Has expiry date (e.g., 90 days)

### 8.4 Customer Management

**Registered Customers:**

**Adding a Customer:**
1. Navigate to Sales > Customers
2. Click **"+ New Customer"**
3. Fill in details:
   - Customer code (auto-generated)
   - Name
   - Phone number
   - Email
   - Address
   - Customer type (Individual/Corporate)
   - Credit limit (if offering credit)
   - Credit days (payment terms)
4. Click **"Save Customer"**

**Customer Benefits:**
- Purchase history tracking
- Loyalty points
- Special pricing
- Credit sales
- Personalized promotions

**Loyalty Points:**
- Earn points on purchases (e.g., 1 point per KES 100)
- Redeem points for discounts
- View point balance in customer profile

### 8.5 Cashier Shift Management

**Opening a Shift:**

Cashiers must open shift before sales:
1. Log in to POS
2. Click **"Open Shift"**
3. Enter:
   - Till number
   - Opening float (starting cash)
4. Click **"Start Shift"**

**Shift Information:**
- Start time
- Cashier name
- Till location
- Opening float amount

**During Shift:**
- Process sales
- Accept payments
- Issue receipts
- Handle returns

**Closing a Shift:**

At end of day:
1. Click **"Close Shift"**
2. Count cash in till
3. Enter **Closing Cash** amount
4. System calculates:
   - Expected cash (Opening + Cash sales - Cash refunds)
   - Variance (Closing - Expected)
5. If variance:
   - Enter explanation
   - Manager approval may be required
6. Print **Shift Report**
7. Submit cash for banking

**Shift Report includes:**
- Total sales (count and value)
- Payment breakdown (Cash, M-Pesa, Card)
- Returns processed
- Opening/closing float
- Cash variance
- Top selling items

### 8.6 Sales Analytics

**Daily Sales Summary:**

View real-time sales data:
- Total sales today
- Number of transactions
- Average transaction value
- Sales by payment method
- Sales by category
- Hourly sales breakdown

**Compare Periods:**
- Today vs Yesterday
- This week vs Last week
- This month vs Last month
- Custom date ranges

---

## 9. TRANSFER MANAGEMENT

### 9.1 Inter-Branch Transfers

**When to transfer stock:**
- Branch A has excess, Branch B is low
- New branch opening (initial stock)
- Seasonal demand shifts
- Rebalancing inventory

### 9.2 Creating Stock Transfer

**Requesting Transfer:**

1. Navigate to Transfers > New Transfer
2. Fill in transfer details:
   - **Transfer Number**: Auto-generated
   - **From Branch**: Source branch
   - **To Branch**: Destination branch
   - **Transfer Date**: Planned date
   - **Transfer Type**:
     - Standard (regular rebalancing)
     - Emergency (urgent need)
     - Rebalance (planned optimization)
3. Add items:
   - Click **"+ Add Item"**
   - Select item
   - Enter requested quantity
   - Select batch (if multiple)
4. Add notes (reason for transfer)
5. Click **"Submit for Approval"**

**Transfer Workflow:**

```
Request → Approval → Dispatch → In-Transit → Receive → Complete
```

**Approval:**
- Requires manager approval at source branch
- Ensures stock availability
- Prevents unauthorized transfers

### 9.3 Sending Transfer

**After approval:**

1. Navigate to Transfers > Approved Transfers
2. Open transfer
3. Click **"Send Transfer"**
4. For each item:
   - Confirm quantity being sent
   - Adjust if needed (reasons required)
5. Pack items
6. Generate **Transfer Note** (print)
7. Click **"Mark as Dispatched"**
8. Notification sent to receiving branch

**Stock Impact (Source Branch):**
- Stock reduced immediately
- Movement recorded as "Transfer Out"

### 9.4 Receiving Transfer

**At destination branch:**

1. Navigate to Transfers > Incoming Transfers
2. Open transfer marked "In-Transit"
3. Click **"Receive Transfer"**
4. For each item:
   - Verify item received
   - Enter **Received Quantity**
   - If variance, enter reason:
     - Damaged in transit
     - Lost
     - Short delivery
   - Take photos if damaged
5. Click **"Complete Reception"**

**Stock Impact (Destination Branch):**
- Stock increased
- Movement recorded as "Transfer In"
- Cost transferred from source

**Variance Handling:**

If received ≠ sent:
- **Shortage**: Investigate, claim insurance
- **Damage**: Create stock adjustment at destination
- **Report Generated**: Variance report for management

### 9.5 Transfer Reports

**View transfer history:**
- All transfers between branches
- Transfer value
- Items transferred
- Transit time
- Variance statistics

**Useful for:**
- Audit trail
- Performance analysis
- Cost allocation
- Branch profitability

---

## 10. REPORTS

### 10.1 Sales Reports

**Daily Sales Report:**
- Total sales by day
- Transaction count
- Average sale value
- Payment method breakdown
- Hourly sales trend
- Top selling items
- Sales by category
- Cashier performance

**Sales Summary Report:**
- Customizable date range
- Branch comparison
- Period-over-period comparison
- Charts and graphs

**Customer Sales Report:**
- Sales per customer
- Top customers by value
- Purchase frequency
- Average basket size

**Profit & Loss Report:**
- Sales revenue
- Cost of goods sold (COGS)
- Gross profit
- Profit margin %
- By item, category, or branch

### 10.2 Inventory Reports

**Stock Balance Report:**
- Current stock levels
- Stock value
- Location breakdown
- Category breakdown
- Items below reorder level

**Stock Movement Report:**
- All stock transactions
- In (purchases, transfers in, adjustments)
- Out (sales, transfers out, adjustments)
- Net movement
- By date range

**Fast Moving Items:**
- Items with high turnover
- Sales quantity and frequency
- Ranked by velocity
- Suggests reorder priorities

**Slow Moving Items:**
- Items with low turnover
- Days of stock on hand
- Risk of expiry/obsolescence
- Consider markdowns or discontinuation

**Expiry Report:**
- Items expiring soon (30/60/90 days)
- Already expired items
- Value at risk
- Sorted by urgency

**Stock Valuation Report:**
- Total inventory value
- By category
- By branch
- By supplier
- For financial statements

### 10.3 Purchase Reports

**Purchase Analysis:**
- Total purchases by period
- By supplier
- By category
- Purchase order fulfillment rate

**Supplier Performance:**
- On-time delivery %
- Quality issues
- Average lead time
- Total spend per supplier

**Purchase Variance Report:**
- PO vs GRN variances
- Reasons for variances
- Financial impact

**Outstanding POs:**
- Orders not yet received
- Aging analysis
- Follow-up required

### 10.4 Financial Reports

**Daily Cash Report:**
- Cash sales
- M-Pesa collections
- Card payments
- Cash variance

**Payment Method Analysis:**
- Cash vs Cashless ratio
- Trends over time
- By branch

**Debtor Aging:**
- Outstanding customer credit
- Aging categories (0-30, 31-60, 61-90, 90+ days)
- Collection priorities

**Creditor Aging:**
- Outstanding supplier payments
- Due dates
- Overdue payments

### 10.5 Generating Reports

**Steps:**
1. Navigate to Reports
2. Select report type
3. Set parameters:
   - Date range
   - Branch (or all)
   - Category (if applicable)
   - Other filters
4. Click **"Generate Report"**
5. View on screen or export:
   - PDF (for printing/archiving)
   - Excel (for analysis)
   - CSV (for import to other systems)

**Scheduled Reports:**

Automate recurring reports:
1. Navigate to Reports > Scheduled Reports
2. Click **"+ New Schedule"**
3. Select report type
4. Set frequency (Daily, Weekly, Monthly)
5. Set time (e.g., 8:00 AM)
6. Add email recipients
7. Click **"Activate Schedule"**

Reports automatically generated and emailed.

### 10.6 Dashboard Analytics

**Management Dashboard:**

Real-time KPIs:
- Sales today (vs target)
- Transactions count
- Average transaction value
- Stock value
- Profit margin
- Low stock alerts
- Pending approvals

**Charts:**
- Sales trend (line chart)
- Sales by category (pie chart)
- Top items (bar chart)
- Branch comparison (bar chart)

**Customizable:**
- Add/remove widgets
- Set date ranges
- Set refresh interval

---

## 11. SYSTEM SETTINGS

### 11.1 Branch Management

**Adding a Branch:**
1. Navigate to Settings > Branches
2. Click **"+ New Branch"**
3. Fill in details:
   - Branch code
   - Branch name
   - Branch type (Main, Branch, Warehouse)
   - Address
   - Contact details
   - GPS coordinates (for mapping)
4. Click **"Save"**

**Branch Settings:**
- Operating hours
- Tax settings
- Receipt format
- Till configuration

### 11.2 Company Settings

**Company Information:**
- Company name
- Tax PIN
- Address
- Logo (for receipts and reports)
- Contact information

**Business Settings:**
- Default currency (KES)
- Date format
- Number format
- Fiscal year start

### 11.3 Tax Configuration

**VAT Settings:**
- Standard rate (16%)
- Zero-rated items
- Exempt items

**KRA eTIMS Integration:**
- eTIMS API credentials
- CU serial number
- Automatic submission settings

### 11.4 Payment Methods

**Configure payment methods:**
- Cash
- M-Pesa
- Card (Visa, Mastercard)
- Bank Transfer
- Cheque
- Customer Credit

**M-Pesa Configuration:**
- Paybill/Till number
- API credentials
- Callback URL
- Auto-reconciliation

### 11.5 Receipt Settings

**Customize receipts:**
- Header (company info)
- Logo placement
- Footer (thank you message)
- Include QR code
- Include eTIMS details
- Font size

**Receipt Types:**
- A4 (full page)
- 80mm (thermal)
- 58mm (compact thermal)

### 11.6 Notification Settings

**Configure alerts:**
- Low stock alerts (email/SMS)
- Expiry alerts
- Pending approval alerts
- Payment reminders
- Daily sales summary

**Recipients:**
- By role (all admins, all managers)
- Specific users
- External email addresses

### 11.7 Backup & Restore

**Manual Backup:**
1. Navigate to Settings > Backup
2. Click **"Create Backup"**
3. Wait for backup to complete
4. Download backup file

**Automatic Backups:**
- Daily at 3:00 AM
- Stored for 30 days
- Offsite backup

**Restore:**
1. Contact system administrator
2. Provide backup date
3. System restored (typically 1-2 hours)

### 11.8 Audit Log

**View system activity:**
- User logins
- Data changes
- Approvals/rejections
- Exports
- Configuration changes

**Search and filter:**
- By user
- By date
- By action type
- By module

---

## 12. TROUBLESHOOTING

### 12.1 Common Issues

**Cannot Login:**
- ✓ Check username spelling (case-sensitive)
- ✓ Check password (caps lock off)
- ✓ Clear browser cache
- ✓ Try different browser
- ✓ Contact admin for password reset

**Item Not Found:**
- Check barcode is correct
- Check item is active
- Check item assigned to branch
- Check spelling in search

**Price Not Showing:**
- Check price is set for correct UOM
- Check price is set for branch
- Check price effective dates
- Check item has active price

**Stock Not Updating:**
- Check transaction was posted (not in draft)
- Refresh page (F5)
- Check correct branch selected
- Check permissions

**Report Not Generating:**
- Check date range is valid
- Check branch has data
- Try smaller date range
- Check internet connection
- Clear browser cache

**Receipt Not Printing:**
- Check printer is on
- Check printer connected
- Check paper loaded
- Check printer selected in settings
- Restart printer

### 12.2 Error Messages

**"Insufficient Stock":**
- Item not available in quantity requested
- Check stock balance
- Reduce quantity or transfer from another branch

**"Duplicate Barcode":**
- Barcode already exists for another item
- Use different barcode or edit existing item

**"Permission Denied":**
- User doesn't have required permission
- Contact administrator to grant permission

**"Session Expired":**
- Login session timed out (30 minutes)
- Log in again
- Data in progress may be lost

**"Price Not Found":**
- No active price for item/UOM combination
- Set price before selling

### 12.3 Getting Help

**In-App Help:**
- Click **"?"** icon on any page
- Context-sensitive help displayed

**User Manual:**
- Navigate to Help > User Guide
- Searchable documentation

**Video Tutorials:**
- Navigate to Help > Video Tutorials
- Step-by-step video guides

**Support Ticket:**
1. Navigate to Help > Support
2. Click **"Create Ticket"**
3. Describe issue
4. Attach screenshots if helpful
5. Click **"Submit"**
6. Support team responds within 24 hours

**Emergency Support:**
- Phone: +254 700 000 000
- Email: support@supasoft.com
- WhatsApp: +254 700 000 000
- Available 8 AM - 8 PM EAT

---

## 13. BEST PRACTICES

### 13.1 Daily Tasks

**Morning:**
- ✓ Review overnight sales
- ✓ Check stock alerts
- ✓ Approve pending items
- ✓ Review cashier floats

**Throughout Day:**
- ✓ Monitor sales performance
- ✓ Address low stock items
- ✓ Approve purchase orders
- ✓ Respond to notifications

**Evening:**
- ✓ Review cashier shifts
- ✓ Reconcile cash
- ✓ Generate daily reports
- ✓ Plan tomorrow's activities

### 13.2 Weekly Tasks

- ✓ Review fast/slow moving items
- ✓ Check expiring products
- ✓ Analyze sales trends
- ✓ Plan promotions
- ✓ Review supplier performance
- ✓ Staff performance review

### 13.3 Monthly Tasks

- ✓ Full stock take
- ✓ Financial reports
- ✓ Budget vs actual analysis
- ✓ Profit margin review
- ✓ Supplier payment reconciliation
- ✓ System backup verification

### 13.4 Data Security

**Passwords:**
- Use strong, unique passwords
- Change every 90 days
- Never share passwords
- Log out when away from computer

**User Access:**
- Grant minimum required permissions
- Review user accounts quarterly
- Deactivate inactive users
- Monitor audit logs

**Data Backup:**
- Verify backups run daily
- Test restore process quarterly
- Keep offline backups
- Secure backup storage

---

## 14. APPENDIX

### 14.1 Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl + N` | New item/record |
| `Ctrl + S` | Save |
| `Ctrl + F` | Search |
| `Ctrl + P` | Print |
| `Esc` | Cancel/Close |
| `F5` | Refresh |
| `Alt + H` | Help |

### 14.2 System Limits

| Item | Limit |
|------|-------|
| Items per PO | 1000 |
| Items per invoice | 100 |
| Users per branch | Unlimited |
| File upload size | 10 MB |
| Report export rows | 100,000 |

### 14.3 Glossary

**Terms:**
- **UOM**: Unit of Measure
- **PO**: Purchase Order
- **GRN**: Goods Received Note
- **SKU**: Stock Keeping Unit
- **FIFO**: First In, First Out
- **WAC**: Weighted Average Cost
- **eTIMS**: Electronic Tax Invoice Management System
- **KRA**: Kenya Revenue Authority
- **VAT**: Value Added Tax

---

**Document Version**: 1.0  
**Last Updated**: November 2025  
**For Supasoft Version**: 1.0.0  
**Feedback**: admin-guide-feedback@supasoft.com

---

**Thank you for using Supasoft!**

For additional help, contact our support team at support@supasoft.com or call +254 700 000 000.

