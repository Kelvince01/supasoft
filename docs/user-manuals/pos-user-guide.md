# Supasoft POS - Cashier User Guide

## Welcome to Supasoft POS

This guide will help you use the Supasoft Point of Sale (POS) system efficiently as a cashier.

---

## TABLE OF CONTENTS

1. [Getting Started](#1-getting-started)
2. [Opening Your Shift](#2-opening-your-shift)
3. [Processing Sales](#3-processing-sales)
4. [Accepting Payments](#4-accepting-payments)
5. [Processing Returns](#5-processing-returns)
6. [Customer Management](#6-customer-management)
7. [Closing Your Shift](#7-closing-your-shift)
8. [Troubleshooting](#8-troubleshooting)
9. [Tips for Success](#9-tips-for-success)

---

## 1. GETTING STARTED

### 1.1 Logging In

1. Turn on your POS terminal/computer
2. Open the Supasoft POS application
3. Enter your **Username**
4. Enter your **Password**
5. Click **"Login"** or press `Enter`

**First Time Login:**
- You will be asked to change your password
- Choose a password you can remember
- Write it down and keep it safe

**If You Forget Your Password:**
- Ask your manager to reset it
- You will receive a new temporary password
- Change it when you log in

### 1.2 Understanding the POS Screen

**Main Screen Layout:**

```
┌─────────────────────────────────────────────────┐
│  [Logo]  Branch Name     User: John  [Logout]  │
├──────────────┬──────────────────────────────────┤
│              │  CART (Right Side)               │
│              │  ┌────────────────────────────┐  │
│  PRODUCT     │  │ Item Name    Qty  Price   │  │
│  SEARCH      │  │ Coca Cola     2   120.00  │  │
│  (Left)      │  │ Bread         1    55.00  │  │
│              │  └────────────────────────────┘  │
│  [Barcode]   │                                  │
│  [Scanner]   │  Subtotal:          175.00      │
│              │  Discount:            0.00      │
│              │  VAT (16%):          24.14      │
│              │  ─────────────────────────      │
│              │  TOTAL:            175.00       │
│              │                                  │
│  Recent      │  [PAYMENT]  [CLEAR]  [HOLD]    │
│  Items       │                                  │
└──────────────┴──────────────────────────────────┘
```

**Key Areas:**

1. **Top Bar**: Shows branch, your name, and logout button
2. **Left Panel**: Product search and scanner input
3. **Right Panel**: Shopping cart with items and total
4. **Bottom Buttons**: Actions (Payment, Clear, Hold)

### 1.3 Hardware Setup

**Check these are working:**
- ✓ Barcode scanner
- ✓ Receipt printer
- ✓ Cash drawer
- ✓ M-Pesa device (if available)
- ✓ Card machine (if available)

**Test Barcode Scanner:**
1. Scan any product barcode
2. Product should appear on screen
3. If not working, call your supervisor

**Test Receipt Printer:**
1. Press printer test button
2. Sample receipt should print
3. If not printing, check:
   - Printer is on
   - Paper is loaded
   - Paper not jammed

---

## 2. OPENING YOUR SHIFT

**You MUST open your shift before making any sales.**

### 2.1 Opening Shift Steps

1. Log in to POS
2. Click **"Open Shift"** button (big button on screen)
3. Fill in shift details:

**Shift Opening Form:**
- **Till Number**: Your assigned till (e.g., TILL-01)
- **Opening Float**: Count the starting cash in your drawer
  - Count coins carefully
  - Count notes by denomination
  - Enter total amount
- **Date**: Today's date (auto-filled)
- **Time**: Current time (auto-filled)

4. Click **"Start Shift"**

**Confirmation Message:**
```
✓ Shift opened successfully!
  Shift Number: SHIFT-2025-1234
  Cashier: John Doe
  Till: TILL-01
  Opening Float: KES 5,000.00
  Time: 08:00 AM
```

### 2.2 What Happens When You Open Shift

- ✓ Your shift starts being recorded
- ✓ All sales are tracked to your shift
- ✓ You can now process transactions
- ✓ Manager can see your shift is active

**Important:**
- Count opening float carefully
- Any shortage/excess at end of day will be on your record
- If amount is wrong, ask supervisor before starting

---

## 3. PROCESSING SALES

### 3.1 Starting a New Sale

**Option A: Using Barcode Scanner (Recommended)**

1. Scan product barcode
2. Item appears in cart (right side)
3. Quantity defaults to 1
4. Scan again to add more of same item
5. Repeat for all items

**Option B: Manual Search**

1. Click in search box (left side)
2. Type product name or code
3. Results appear below
4. Click on correct product
5. Item added to cart

**Option C: Browse Categories**

1. Click **"Categories"** button
2. Select category (e.g., Beverages)
3. View products in category
4. Click product to add to cart

### 3.2 Adjusting Quantities

**To change quantity:**
1. Click on item in cart
2. Quantity field highlights
3. Type new quantity
4. Press `Enter`

**To remove item:**
1. Click item in cart
2. Click **"Remove"** button (trash icon)
3. Item removed from cart

### 3.3 Applying Discounts

**Item-Level Discount:**
1. Click on item in cart
2. Click **"Discount"** button
3. Enter discount:
   - **Percentage**: Type "10" for 10% off
   - **Amount**: Type "KES 5" for KES 5 off
4. Discount applied to that item only

**Invoice-Level Discount:**
1. Click **"Apply Discount"** at bottom
2. Enter discount percentage or amount
3. Applies to entire purchase

**Manager Approval Required:**
- Discounts over 10% need manager code
- Manager enters their code to approve
- Sale continues after approval

### 3.4 Special Cases

**Weighable Items (Fruits, Vegetables, Meat):**
1. Weigh item on scale
2. Scale shows weight and price
3. Scan scale barcode OR
4. Enter weight manually in POS
5. Price calculated automatically

**Items Without Barcode:**
1. Search by name
2. Select item
3. Confirm price with customer
4. Proceed with sale

**Multiple Pack Sizes:**

Example: Milk available in 500ml, 1L, 2L
1. Scan correct barcode for size
2. Check UOM on screen (Piece, Liter, etc.)
3. Confirm with customer if unsure

---

## 4. ACCEPTING PAYMENTS

### 4.1 Payment Process Overview

After adding all items:
1. Review total with customer
2. Ask payment method
3. Process payment
4. Give receipt and change

### 4.2 Cash Payment

1. Click **"PAYMENT"** button
2. Click **"CASH"** tab
3. Total amount shown
4. Ask customer for payment
5. Enter amount received
6. System calculates change
7. Click **"Complete Payment"**

**Example:**
```
Total:         KES 175.00
Cash Received: KES 200.00
Change:        KES  25.00
```

8. Give customer:
   - Receipt
   - Change (count back to customer)

**Counting Change:**
- Count out loud
- "Your total is 175. Out of 200. That's 25 shillings change."
- Hand coins/notes to customer

### 4.3 M-Pesa Payment

**Option A: Customer Initiates (Paybill/Till)**

1. Tell customer your Paybill/Till number
2. Tell customer total amount
3. Tell customer reference (invoice number)
4. Customer sends M-Pesa
5. Customer shows confirmation message
6. You verify:
   - Transaction code
   - Amount
   - Your till number
7. Click **"MPESA"** tab
8. Enter M-Pesa code
9. Click **"Verify"**
10. If valid, payment complete

**Option B: STK Push (Automated)**

1. Click **"MPESA"** tab
2. Enter customer phone number (254XXXXXXXXX)
3. Click **"Send Request"**
4. Customer receives pop-up on phone
5. Customer enters M-Pesa PIN
6. Confirmation appears on POS
7. Payment complete

**If M-Pesa Fails:**
- Ask customer to check phone
- Check phone number is correct
- Try again or use alternative payment

### 4.4 Card Payment

1. Click **"CARD"** tab
2. Customer inserts/swipes/taps card
3. Customer enters PIN (if required)
4. Wait for approval
5. Print receipt
6. Give customer merchant copy

**Card Types Accepted:**
- Visa
- Mastercard
- American Express (if enabled)

**If Card Declined:**
- "Sorry, your card was declined"
- Suggest alternative payment
- Never retain customer's card

### 4.5 Split Payment

Customer pays with multiple methods:

**Example: Part Cash, Part M-Pesa**

Total: KES 1,000

1. Click **"SPLIT PAYMENT"**
2. Add first payment:
   - Cash: KES 500
   - Click **"Add Payment"**
3. Add second payment:
   - M-Pesa: KES 500
   - Click **"Add Payment"**
4. Total paid: KES 1,000 ✓
5. Click **"Complete"**

### 4.6 Credit Sales (Registered Customers Only)

1. Search for customer
2. Select customer from list
3. Check credit limit available
4. Process sale normally
5. Click **"CREDIT"** payment option
6. Customer signs receipt
7. Payment recorded as owing

**Important:**
- Only registered customers get credit
- Check credit limit before sale
- Must have manager approval for large credits

### 4.7 Completing the Sale

After payment processed:
1. Receipt prints automatically
2. Give receipt to customer
3. If cash, give change
4. Thank customer
5. Screen clears for next customer

**Receipt Includes:**
- Items purchased
- Quantities and prices
- Total amount
- Payment method
- Change (if cash)
- KRA eTIMS QR code
- Thank you message

---

## 5. PROCESSING RETURNS

### 5.1 When to Accept Returns

**Accept returns for:**
- ✓ Defective products
- ✓ Expired products
- ✓ Wrong item sold
- ✓ Damaged products
- ✓ Customer changed mind (within policy)

**Return Policy:**
- Within 7 days with receipt
- Product in resaleable condition
- Original packaging (for some items)

**Check with Manager if Unsure**

### 5.2 Processing a Return

1. Ask customer for receipt
2. Click **"RETURNS"** button
3. Scan receipt barcode OR enter invoice number
4. Original sale appears
5. Select items being returned
6. Enter quantity (if partial return)
7. Select return reason:
   - Defective
   - Expired
   - Wrong item
   - Changed mind
   - Other
8. If defective/damaged:
   - Inspect product
   - Take photo (if required)
9. Select refund method:
   - Cash
   - M-Pesa
   - Credit note
10. Click **"Process Return"**

**Manager Approval:**
- Returns over KES 1,000 need manager code
- Returns after 7 days need manager approval

### 5.3 Issuing Refunds

**Cash Refund:**
1. Count cash from drawer
2. Give customer refund amount
3. Give return receipt
4. Customer signs return receipt

**M-Pesa Refund:**
1. Enter customer M-Pesa number
2. System processes refund
3. Customer receives confirmation SMS
4. Give customer return receipt

**Credit Note:**
1. Credit note prints with unique number
2. Give to customer
3. Valid for 90 days
4. Can be used for future purchases

### 5.4 Using Credit Notes

When customer has credit note:
1. Process sale normally
2. At payment:
3. Click **"CREDIT NOTE"**
4. Scan credit note barcode OR enter number
5. System verifies:
   - Valid
   - Not expired
   - Not already used
6. Credit applied to sale
7. If balance remains:
   - Customer pays balance
8. If credit exceeds sale:
   - New credit note issued for balance

---

## 6. CUSTOMER MANAGEMENT

### 6.1 Searching for Customers

**During Sale:**
1. Click **"Customer"** button
2. Search by:
   - Phone number (recommended)
   - Name
   - Customer code
3. Select customer from results
4. Customer info appears at top of sale

**Benefits for Customer:**
- Special pricing (if applicable)
- Loyalty points earned
- Purchase history
- Credit purchases (if enabled)

### 6.2 Registering New Customers

**Quick Registration (during sale):**
1. Click **"New Customer"**
2. Enter minimum details:
   - Name
   - Phone number
3. Click **"Save"**
4. Continue with sale

**Full Registration:**
- Refer customer to service desk OR
- Give customer registration form

### 6.3 Loyalty Points

**If enabled:**
- Points earned automatically
- Displayed on receipt
- Customer can see balance

**Redeeming Points:**
1. Select customer
2. View available points
3. Click **"Redeem Points"**
4. Enter points to redeem
5. Discount applied to sale

**Example:**
- 1,000 points = KES 100 discount
- Customer has 2,500 points
- Can redeem up to 2,500 points (KES 250)

---

## 7. CLOSING YOUR SHIFT

**At end of your shift (or when asked by manager):**

### 7.1 Closing Shift Steps

1. Ensure no customers waiting
2. Click **"Close Shift"** button
3. Count cash in drawer:
   - Count all coins
   - Count all notes
   - Count carefully (double-check)
4. Enter **Closing Cash** amount
5. System shows:

**Shift Summary:**
```
Opening Float:      KES  5,000.00
Cash Sales:         KES 45,000.00
Cash Refunds:       KES    500.00
Expected Cash:      KES 49,500.00
Counted Cash:       KES 49,450.00
Variance:           KES    (50.00)  ⚠️
```

6. If variance:
   - **Shortage**: Count again carefully
   - **Excess**: Verify no errors
   - Enter explanation
7. Click **"Finalize Shift"**

### 7.2 Shift Report

Report prints automatically showing:
- Total sales (amount and count)
- Payment methods breakdown:
  - Cash: KES 45,000 (60%)
  - M-Pesa: KES 25,000 (33%)
  - Card: KES 5,000 (7%)
- Returns processed
- Average sale value
- Busiest hour
- Cash variance

### 7.3 Banking Cash

**After closing shift:**
1. Print shift report (keep one copy)
2. Put cash in banking bag:
   - Separate by denomination
   - Fill in banking slip
   - Include shift report
3. Seal bag
4. Sign banking register
5. Hand to supervisor/manager

**Never:**
- Take cash home
- Leave cash unattended
- Open sealed banking bag

### 7.4 Variance Explanation

**If short (you have less than expected):**
- Recount carefully
- Check for errors in transactions
- Check for voided/cancelled sales
- Explain what might have happened
- Accept accountability

**Common causes of shortage:**
- Giving wrong change
- Not entering sale in system
- Entering wrong amount
- Giving discount without manager code

**If over (you have more than expected):**
- Recount carefully
- Check for duplicate entries
- Still report variance
- Never keep excess

**Manager May:**
- Review your transactions
- Check CCTV footage (if serious)
- Provide coaching/training
- Disciplinary action (if fraud suspected)

**Best Practice:**
- Count change carefully
- Double-check amounts
- Ask for help if unsure

---

## 8. TROUBLESHOOTING

### 8.1 Scanner Not Working

**Problem**: Barcode doesn't scan

**Solutions:**
1. ✓ Check scanner is plugged in
2. ✓ Check scanner light is on
3. ✓ Try scanning barcode again (hold steady)
4. ✓ Try manual entry (type barcode number)
5. ✓ Call supervisor if still not working

**Temporary Workaround:**
- Type barcode number manually
- Or search by product name

### 8.2 Printer Not Printing

**Problem**: Receipt doesn't print

**Solutions:**
1. ✓ Check printer is on (power light)
2. ✓ Check paper is loaded
3. ✓ Check paper not jammed
4. ✓ Try test print
5. ✓ Restart printer
6. ✓ Call IT support if still not working

**Temporary Workaround:**
- Email receipt to customer
- Handwrite receipt (get manager approval)

### 8.3 System Slow or Frozen

**Problem**: POS not responding

**Solutions:**
1. ✓ Wait 30 seconds (may be processing)
2. ✓ Press `Esc` to cancel
3. ✓ Check internet connection
4. ✓ Restart application
5. ✓ Restart computer (last resort)
6. ✓ Call IT support

**Important:**
- Note transaction you were processing
- Manager can recover/reverse if needed

### 8.4 Wrong Item Scanned

**Problem**: Scanned wrong product

**Solutions:**
1. ✓ Click on item in cart
2. ✓ Click "Remove" button
3. ✓ Scan correct item

**If Already Paid:**
- Cannot remove after payment
- Process as return
- Resell correct item

### 8.5 Customer Wants to Cancel

**Problem**: Customer changes mind before payment

**Solutions:**
1. ✓ Click **"CLEAR"** button
2. ✓ Confirm clearance
3. ✓ Sale cancelled
4. ✓ No record created

**After Payment:**
- Cannot cancel
- Must process as return

### 8.6 Price Showing Wrong

**Problem**: Item shows different price than shelf

**Solutions:**
1. ✓ Check UOM (is it per kg, per piece, per pack?)
2. ✓ Check if promotion should apply
3. ✓ Call supervisor
4. ✓ Supervisor can override price

**Never:**
- Change price yourself without authorization
- Promise customer a price you can't honor

### 8.7 M-Pesa Not Received

**Problem**: Customer sent M-Pesa but system doesn't show

**Solutions:**
1. ✓ Ask for M-Pesa confirmation message
2. ✓ Verify transaction code
3. ✓ Click **"Check Status"** on POS
4. ✓ Wait 1-2 minutes (may be delayed)
5. ✓ Call supervisor if still not showing

**Important:**
- Get customer's contact number
- If payment confirmed but not reflecting:
  - Supervisor can manually verify
  - Let customer take goods
  - Reconciliation team will verify later

### 8.8 Customer Has Complaint

**Problem**: Customer unhappy with product/service

**Solutions:**
1. ✓ Stay calm and professional
2. ✓ Listen to customer
3. ✓ Apologize for inconvenience
4. ✓ Try to resolve within your authority
5. ✓ Call supervisor/manager if needed

**Never:**
- Argue with customer
- Make promises you can't keep
- Accept blame for company policy

**Escalate to Manager if:**
- Customer very angry
- You don't know how to help
- Customer asking for something unusual
- Situation seems serious

---

## 9. TIPS FOR SUCCESS

### 9.1 Speed & Efficiency

**Faster Service:**
- ✓ Keep work area organized
- ✓ Know common product codes
- ✓ Scan multiple items quickly
- ✓ Prepare change in advance
- ✓ Learn keyboard shortcuts

**During Quiet Times:**
- Tidy your area
- Restock bags/receipt paper
- Learn product locations
- Practice on POS

### 9.2 Accuracy

**Avoid Mistakes:**
- ✓ Count change carefully
- ✓ Verify amounts before accepting payment
- ✓ Check large bills for counterfeits
- ✓ Double-check discounts
- ✓ Ensure items scan correctly

**If You Make a Mistake:**
- Admit it immediately
- Call supervisor
- Don't try to hide it
- Learn from it

### 9.3 Customer Service

**Excellent Service:**
- ✓ Greet every customer with a smile
- ✓ Make eye contact
- ✓ Say "Please" and "Thank you"
- ✓ Handle products carefully
- ✓ Bag items properly (heavy at bottom)
- ✓ Offer help with loading (if needed)

**Professional Appearance:**
- ✓ Wear clean uniform
- ✓ Name badge visible
- ✓ Good personal hygiene
- ✓ Neat grooming

**Communication:**
- Speak clearly
- Be polite
- Listen to customer
- Answer questions honestly
- If you don't know, ask supervisor

### 9.4 Security

**Protect Cash:**
- ✓ Don't leave drawer open
- ✓ Don't count cash in front of customers
- ✓ Don't tell anyone your password
- ✓ Lock screen when leaving till
- ✓ Report suspicious behavior

**Personal Safety:**
- ✓ Follow security procedures
- ✓ Know emergency procedures
- ✓ Know where panic button is
- ✓ Never confront shoplifters (call security)

**If Robbery Occurs:**
- Stay calm
- Comply with demands
- Don't be a hero
- Note description of suspect
- Report immediately after

### 9.5 Health & Safety

**Your Responsibility:**
- ✓ Keep work area clean
- ✓ Clean up spills immediately
- ✓ Report hazards
- ✓ Take regular breaks
- ✓ Use proper lifting techniques

**Ergonomics:**
- Adjust chair height
- Keep screen at eye level
- Take short breaks (stretch)
- Report discomfort to supervisor

### 9.6 Teamwork

**Working with Team:**
- ✓ Help colleagues when possible
- ✓ Communicate clearly
- ✓ Share knowledge
- ✓ Cover breaks
- ✓ Maintain positive attitude

**During Busy Times:**
- Open additional tills if trained
- Call for backup
- Work efficiently
- Stay calm under pressure

### 9.7 Continuous Improvement

**Always Learning:**
- ✓ Ask questions
- ✓ Attend training sessions
- ✓ Learn from experienced cashiers
- ✓ Practice new features
- ✓ Suggest improvements

**Feedback:**
- Accept constructive criticism
- Ask for feedback from supervisor
- Learn from mistakes
- Celebrate successes

---

## 10. QUICK REFERENCE

### 10.1 Common Shortcuts

| Key | Action |
|-----|--------|
| `F1` | Help |
| `F2` | Search Product |
| `F3` | Search Customer |
| `F5` | Refresh |
| `F12` | Payment |
| `Esc` | Cancel/Back |
| `Ctrl+Z` | Undo |
| `Delete` | Remove Item |

### 10.2 Common Codes

**Payment Methods:**
- Cash: `C`
- M-Pesa: `M`
- Card: `D`
- Credit: `R`

**Return Reasons:**
- Defective: `DEF`
- Expired: `EXP`
- Wrong: `WRG`

### 10.3 Who to Contact

| Issue | Contact |
|-------|---------|
| POS/System Issue | IT Support: ext 100 |
| Price Dispute | Supervisor: ext 101 |
| Customer Complaint | Manager: ext 102 |
| Security Issue | Security: ext 999 |
| Emergency | Emergency: ext 911 |

### 10.4 Daily Checklist

**Morning:**
- [ ] Log in
- [ ] Check printer has paper
- [ ] Check scanner works
- [ ] Count opening float
- [ ] Open shift

**During Shift:**
- [ ] Process sales accurately
- [ ] Provide excellent service
- [ ] Keep area clean
- [ ] Report issues immediately

**End of Shift:**
- [ ] Close shift
- [ ] Count cash carefully
- [ ] Print shift report
- [ ] Bank cash
- [ ] Log out

---

## 11. EMERGENCY PROCEDURES

### 11.1 Power Failure

**If power goes out:**
1. Stay calm
2. Inform customers
3. Note last transaction
4. Wait for power restoration (usually 5-10 min)
5. If extended:
   - Close till
   - Secure cash
   - Follow manager instructions

**When Power Restored:**
1. Log back in
2. Check last transaction completed
3. Resume operations

### 11.2 System Failure

**If POS system crashes:**
1. Note transaction being processed
2. Call IT support
3. Follow manual procedures (manager will guide)
4. Continue operations if possible

**Temporary:**
- Write sales on paper
- Enter in system when restored

### 11.3 Cash Shortage in Drawer

**If you run out of change:**
1. Call supervisor immediately
2. Request change
3. Apologize to customers for delay
4. Suggest exact change if waiting

### 11.4 Fire Alarm

**If fire alarm sounds:**
1. Stop serving immediately
2. Secure cash drawer (lock)
3. Leave POS station
4. Follow evacuation procedures
5. Don't take personal belongings
6. Go to assembly point

### 11.5 Suspicious Activity

**If you notice:**
- Counterfeit money
- Suspected shoplifter
- Threatening customer
- Unusual requests

**Action:**
1. Stay calm
2. Don't confront
3. Discreetly alert security/supervisor
4. Follow their instructions

---

## 12. FREQUENTLY ASKED QUESTIONS

**Q: Can I give discounts to friends/family?**
A: No. All discounts must be authorized. Unauthorized discounts are against policy and may result in disciplinary action.

**Q: What if customer doesn't have receipt for return?**
A: Manager can look up transaction by date/amount/card number. Customer must have valid ID.

**Q: Can I accept foreign currency?**
A: Only if authorized and trained. Check with manager.

**Q: What if I'm short at end of shift?**
A: Recount carefully. If still short, explain honestly. Small variances happen. Learn from it.

**Q: Can I close my shift early?**
A: Only with manager permission. Shifts should be closed at assigned time.

**Q: Customer asking for credit without registration?**
A: Cannot give credit to unregistered customers. Offer to register them quickly.

**Q: Scanner beeping but item not adding?**
A: Item may not be in system. Search by name or call supervisor.

**Q: How to void a transaction?**
A: If before payment, click "Clear". If after payment, process as return.

---

## 13. PERFORMANCE STANDARDS

**You are expected to:**
- ✓ Accuracy: 99%+ transaction accuracy
- ✓ Speed: Average 2-3 minutes per transaction
- ✓ Variance: Less than 0.1% cash variance
- ✓ Attendance: On time, full shifts
- ✓ Customer Service: Positive feedback
- ✓ Professionalism: Follow all policies

**Performance Review:**
- Monthly meeting with supervisor
- Discuss performance metrics
- Address any issues
- Set improvement goals

**Recognition:**
- Cashier of the Month
- Performance bonuses
- Promotion opportunities

---

## 14. IMPORTANT REMINDERS

**DO:**
- ✓ Greet every customer
- ✓ Count cash carefully
- ✓ Ask for help if unsure
- ✓ Follow all procedures
- ✓ Report issues immediately
- ✓ Keep work area clean
- ✓ Be honest and trustworthy

**DON'T:**
- ✗ Share passwords
- ✗ Leave till unattended
- ✗ Give unauthorized discounts
- ✗ Accept damaged currency
- ✗ Argue with customers
- ✗ Use phone during transactions
- ✗ Eat/drink at POS station

---

## 15. CONTACT INFORMATION

**Support Contacts:**

**IT Support:**
- Phone: ext 100
- Email: it@supasoft.com
- Hours: 24/7

**Supervisor:**
- Phone: ext 101
- Available: During business hours

**Manager:**
- Phone: ext 102
- Email: manager@supasoft.com

**HR (for personal issues):**
- Phone: ext 103
- Email: hr@supasoft.com

**Emergency:**
- Security: ext 999
- Medical: ext 911
- Fire: ext 911

---

**Document Version**: 1.0  
**Last Updated**: November 2025  
**For Supasoft POS Version**: 1.0.0  
**Feedback**: pos-guide-feedback@supasoft.com

---

**Thank you for being part of the Supasoft team!**

Your role is important. Every customer interaction matters. 
Provide excellent service, stay honest, and always ask for help when needed.

---

**Training & Certification:**

To become a certified Supasoft cashier:
1. Complete this user guide
2. Pass written test (80% required)
3. Complete 4 hours supervised practice
4. Receive certification

**Questions?** Ask your supervisor or trainer.

**Good luck and welcome aboard!** 🎉

