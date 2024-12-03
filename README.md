# Rental Management System - README

Welcome to the **Rental Management System**! This application is designed to help manage rental agreements, tenants, hosts, owners, properties, and payments. It provides a streamlined way to handle various rental-related tasks such as creating, updating, deleting agreements, generating reports, and making payments.

---

## Features:
1. **Add New Rental Agreements**  
   Easily create new rental agreements by entering required details like tenants, hosts, owner, rental period, fee, and contract dates.

2. **Update Existing Rental Agreements**  
   Modify details of existing rental agreements including sub-tenants, hosts, owner, period, fees, and more.

3. **Delete Rental Agreements**  
   Remove a rental agreement and clean up associated data from owners, tenants, and hosts.

4. **Generate Agreement Reports**  
   View and export sorted reports based on Agreement ID, Start Date, Fee, or Status.

5. **Make Payments**  
   Record tenant payments with methods like Cash, Bank Transfer, or Checks.

6. **View All Records**  
   Retrieve and display records of properties, hosts, owners, tenants, and payments.

---

## Setup Instructions:
1. Ensure you have Java installed on your system.
2. Compile the program using:
   ```bash
   javac RentalManagementSystem.java
   ```
3. Run the program using:
   ```bash
   java RentalManagementSystem
   ```

---

## Program Usage:
### Main Menu:
After launching the program, you'll see the main menu with the following options:
```
1. Add a new Rental Agreement
2. View all Rental Agreements
3. Update a Rental Agreement
4. Delete a Rental Agreement
5. Generate Agreement Report
6. Make a Payment
7. View all Properties
8. View all Hosts
9. View all Owners
10. View all Tenants
11. View all Payments
12. Exit
```

### Menu Options:
#### **1. Add a New Rental Agreement**  
- Follow the prompts to enter details like Main Tenant, Sub-Tenants, Hosts, Owner, Period, Fees, and Dates.  
- The program validates the inputs and saves the agreement once all details are entered correctly.

#### **2. View All Rental Agreements**  
- Displays a list of all rental agreements in the system.

#### **3. Update a Rental Agreement**  
- Choose this option to update an agreement.  
- The program will allow you to modify:
  - Sub-Tenants
  - Hosts
  - Owner
  - Rental Period
  - Start/End Dates
  - Renting Fee
  - Status  

#### **4. Delete a Rental Agreement**  
- Enter the ID of the rental agreement you wish to delete.  
- The system will remove all associated data from the tenants, hosts, and owners.

#### **5. Generate Agreement Report**  
- Choose a sorting order (Ascending/Descending) and sorting field (Agreement ID, Start Date, Renting Fee, or Status).  
- A report will be generated and saved to a file.

#### **6. Make a Payment**  
- Enter a Tenant ID and follow the prompts to record the payment details, including method, amount, and date.

#### **7-11. View All Records**  
- Displays all records for:
  - Properties (7)
  - Hosts (8)
  - Owners (9)
  - Tenants (10)
  - Payments (11)

#### **12. Exit**  
- Save all data and close the program.

---

## Input Format:
- **Dates:** Enter in `dd/MM/yyyy` format (e.g., 15/10/2024).  
- **Fee/Amount:** Positive numbers only (e.g., 1000.00).  
- **Status:** Valid values are `New`, `Active`, or `Completed`.

---

## Validation Rules:
1. Indices for Sub-Tenants and Hosts must be valid and not conflict with the Main Tenant or Owner.
2. End dates cannot precede Start dates.
3. Payments must have valid methods (`Cash`, `Bank Transfer`, `Checks`) and positive amounts.

---

## File Management:
All data is persisted in files (e.g., agreements, tenants, hosts, owners). Ensure the program has write access to save updates.

---

## Error Handling:
- Invalid inputs prompt error messages with instructions to retry.
- Invalid menu choices display a warning and return to the main menu.
