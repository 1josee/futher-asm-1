package com.consoleApp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class RentalManagementSystem {
    static Map<String, Host> hostMap;
    static Map<String, Owner> ownerMap;
    static Map<String, Tenant> tenantMap;
    static Map<String, Property> propertyMap;
    static Map<String, RentalAgreement> rentalAgreementMap;
    static Map<String, Payment> paymentMap;
    public static void main(String[] args){
        loadDataFromFiles();
        RentalManagerImpl rentalManager = new RentalManagerImpl(rentalAgreementMap, hostMap, ownerMap, tenantMap, paymentMap);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String[] validPeriods = {"daily", "weekly", "fortnightly", "monthly"};
        while (true){
            System.out.println("Main menu:");
            System.out.println("1. Get All Rental Agreements");
            System.out.println("2. Add New Rental Agreement");
            System.out.println("3. Update Rental Agreement");
            System.out.println("4. Delete Rental Agreement");
            System.out.println("5. Generate Rental Agreement Report");
            System.out.println("6. Make new payment");
            System.out.println("7. Get All Properties");
            System.out.println("8. Get All Hosts");
            System.out.println("9. Get All Owners");
            System.out.println("10. Get All Tenants");
            System.out.println("11. Get All Payments");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Get all rental agreements
                    System.out.println("Rental Agreements List: ");
                    rentalManager.getAllRentalAgreements().forEach(System.out::println);
                    break;

                case 2:
                    // Get Property
                    List<Property> propertyList = getProperty();
                    System.out.print("Enter Property Index: ");
                    int propertyIndex;
                    Property property;
                    while (true) {
                        propertyIndex = getValidIndex(scanner, propertyList.size());
                        if (propertyIndex > 0 && propertyIndex <= propertyList.size()) {
                            property = propertyList.get(propertyIndex-1);
                            break;
                        } else {
                            System.out.println("Invalid choice. Please select a valid index.");
                        }
                    }
                    // Get Main Tenant
                    List<Tenant> tenantList = getAllTenants();
                    System.out.print("Enter Main Tenant index: ");
                    int mainTenantIndex;
                    Tenant mainTenant;
                    while (true) {
                        mainTenantIndex = getValidIndex(scanner, tenantList.size());
                        if (mainTenantIndex > 0 && mainTenantIndex <= tenantList.size()) {
                            mainTenant = tenantList.get(mainTenantIndex-1);
                            System.out.println(mainTenant.toString());
                            break;
                        } else {
                            System.out.println("Invalid choice. Please select a valid index.");
                        }
                    }
                    // Get Sub Tenants
                    System.out.println("Enter Sub Tenant indices separated by spaces (exclude Main Tenant index): ");
                    List<Integer> subTenantIndices = new ArrayList<>();
                    List<Tenant> subTenants = new ArrayList<>();
                    while (true) {
                        String input = scanner.nextLine();
                        subTenantIndices = parseSubTenantIndices(input, mainTenantIndex, tenantList.size());
                        if (!subTenantIndices.isEmpty()) {
                            for (Integer tenantIndex : subTenantIndices) {
                                Tenant tenant = tenantList.get(tenantIndex - 1);
                                subTenants.add(tenant);
                            }
                            break;
                        } else {
                            System.out.println("Invalid input. Ensure sub-tenant indices are valid and do not include the main tenant.");
                        }
                    }
                    //Get Host
                    List<Host> hostList = getAllHosts();
                    System.out.println("Enter Hosts indices separated by spaces: ");
                    List<Integer> hostIndices = new ArrayList<>();
                    List<Host> hosts = new ArrayList<>();
                    while (true) {
                        String input = scanner.nextLine();
                        hostIndices = parseHostIndices(input, hostList.size());
                        if (!hostIndices.isEmpty()) {
                            for (Integer hostIndex : hostIndices) {
                                Host host = hostList.get(hostIndex - 1);
                                hosts.add(host);
                            }
                            break;
                        } else {
                            System.out.println("Invalid input. Ensure host indices are valid and separated by spaces.");
                        }
                    }
                    // Get Owner
                    List<Owner> ownerList = getAllOwners();
                    System.out.print("Enter Owner Index: ");
                    int ownerIndex;
                    Owner owner;
                    while (true) {
                        ownerIndex = getValidIndex(scanner, ownerList.size());
                        if (ownerIndex > 0 && ownerIndex <= ownerList.size()) {
                            owner = ownerList.get(ownerIndex-1);
                            break;
                        } else {
                            System.out.println("Invalid choice. Please select a valid index.");
                        }
                    }
                    // Get Period
                    String period;
                    while (true) {
                        System.out.println("Enter period of the rental agreement (daily, weekly, fortnightly, monthly): ");
                        period = scanner.nextLine().trim().toLowerCase();
                        // Validate the input
                        boolean isValid = false;
                        for (String validPeriod : validPeriods) {
                            if (validPeriod.equals(period)) {
                                isValid = true;
                                break;
                            }
                        }
                        if (isValid) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter one of the following: daily, weekly, fortnightly, monthly.");
                        }
                    }
                    // Get Start date and End date
                    dateFormat.setLenient(false);
                    Date startContractDate = null;
                    Date endContractDate = null;
                    while (startContractDate == null) {
                        System.out.println("Enter the start contract date (dd/MM/yyyy): ");
                        String startDateInput = scanner.nextLine().trim();

                        try {
                            startContractDate = dateFormat.parse(startDateInput);
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }
                    while (endContractDate == null) {
                        System.out.println("Enter the end contract date (dd/MM/yyyy): ");
                        String endDateInput = scanner.nextLine().trim();

                        try {
                            endContractDate = dateFormat.parse(endDateInput);

                            // Ensure end date is after start date
                            if (endContractDate.before(startContractDate)) {
                                System.out.println("End date must be after the start date. Please try again.");
                                endContractDate = null;
                            }
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }
                    // Get Renting Fee
                    double rentingFee = 0.0;
                    while (true) {
                        System.out.println("Enter Renting Fee: ");
                        String input = scanner.nextLine().trim();
                        try {
                            rentingFee = Double.parseDouble(input);
                            if (rentingFee <= 0) {
                                System.out.println("Renting fee must be a positive number. Please try again.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number for the renting fee.");
                        }
                    }
                    // Set status of rental agreement
                    String status = "New";
                    // Generate Rental Agreement Id
                    String prefix = "R-";
                    Random random = new Random();
                    String rentalAgreementId;
                    do{
                        int randomNumber = 10000000 + random.nextInt(90000000);
                        rentalAgreementId = prefix + randomNumber;
                    } while(rentalAgreementMap.containsKey(rentalAgreementId));
                    RentalAgreement rentalAgreement = new RentalAgreement(rentalAgreementId, property, mainTenant, subTenants, hosts,owner,
                            period, startContractDate, endContractDate, rentingFee, status);
                    // Update Host
                    for(Host host : hosts) {
                        host.addManagedProperty(property);
                        host.addCooperatingOwner(owner);
                        host.addRentalAgreement(rentalAgreement);
                    }
                    // Update Owner
                    if(owner != null) {
                        owner.addRentalAgreement(rentalAgreement);
                        owner.addHost(hosts);
                        owner.addProperty(property);
                    }
                    // Update Tenant
                    mainTenant.addRentalAgreement(rentalAgreement);
                    for(Tenant tenant : subTenants) {
                        tenant.addRentalAgreement(rentalAgreement);
                    }
                    // Save data to file
                    rentalAgreementMap.put(rentalAgreement.getAgreementId(),rentalAgreement);
                    rentalManager.addRentalAgreement(rentalAgreement);
                    rentalManager.saveHostToFile();
                    rentalManager.saveOwnerToFile();
                    rentalManager.saveTenantToFile();
                    System.out.println("New Rental Agreement: " + rentalAgreement.toString() + "\n");
                    break;

                case 3:
                    // Update a rental agreement
                    System.out.print("Enter Rental Agreement ID to update (or type 'exit' to cancel): ");
                    String updateId = scanner.nextLine();
                    if (updateId.equalsIgnoreCase("exit")) {
                        break;
                    }
                    RentalAgreement agreementToUpdate = rentalAgreementMap.get(updateId);
                    if (agreementToUpdate == null) {
                        System.out.println("Rental Agreement ID not found.");
                        break;
                    }
                    System.out.println("Updating Rental Agreement: " + agreementToUpdate);
                    while (true) {
                        System.out.println("Choose an option to update:");
                        System.out.println("1. Property");
                        System.out.println("2. Main Tenant");
                        System.out.println("3. Sub Tenants");
                        System.out.println("4. Hosts");
                        System.out.println("5. Owner");
                        System.out.println("6. Period");
                        System.out.println("7. Start Contract Date");
                        System.out.println("8. End Contract Date");
                        System.out.println("9. Renting Fee");
                        System.out.println("10. Status");
                        System.out.println("11. Cancel Update");
                        System.out.print("Enter your choice: ");
                        int updateChoice = getValidIndex(scanner, 11);
                        if(updateChoice == 11) {
                            System.out.println("Cancel Update");
                            break;
                        }
                        switch (updateChoice) {
                            case 1: // Update Property
                                List<Property> propertyExistList = getProperty();
                                System.out.print("Enter Property ID: ");
                                int propertyUpdatedIndex = getValidIndex(scanner, propertyExistList.size());
                                Property oldProperty = agreementToUpdate.getProperty();
                                Property newProperty = propertyExistList.get(propertyUpdatedIndex - 1);
                                agreementToUpdate.setProperty(newProperty);
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                for(Host host : agreementToUpdate.getHosts()) {
                                    host.removeManagedProperty(oldProperty);
                                    host.addManagedProperty(newProperty);
                                }
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                rentalManager.saveHostToFile();
                                System.out.println("Rental Agreement successfully updated.");
                                break;

                            case 2: // Update Main Tenant
                                List<Tenant> tenantExistList = getAllTenants();
                                System.out.print("Enter Main Tenant index: ");
                                int mainTenantUpdatedIndex = getValidIndex(scanner, tenantExistList.size());
                                Tenant oldMainTenant = agreementToUpdate.getMainTenant();
                                Tenant newMainTenant = tenantExistList.get(mainTenantUpdatedIndex - 1);
                                agreementToUpdate.setMainTenant(newMainTenant);
                                newMainTenant.addRentalAgreement(agreementToUpdate);
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                oldMainTenant.removeRentalAgreement(agreementToUpdate);
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                rentalManager.saveTenantToFile();
                                System.out.println("Rental Agreement successfully updated.");
                                break;

                            case 3: // Update Sub Tenants
                                tenantExistList = getAllTenants();
                                Integer mainTenantAgreementIndex = null;
                                for(int i = 0; i < tenantExistList.size(); i++) {
                                    if(tenantExistList.get(i).getId() == agreementToUpdate.getMainTenant().getId()) {
                                        mainTenantAgreementIndex = i + 1;
                                    }
                                }
                                System.out.println("Enter Sub Tenant indices separated by spaces: ");
                                List<Tenant> oldSubTenants = agreementToUpdate.getSubTenants();
                                List<Tenant> newSubTenants = new ArrayList<>();
                                List<Integer> subTenantUpdatedIndices = new ArrayList<>();
                                while (true) {
                                    String input = scanner.nextLine();
                                    subTenantUpdatedIndices = parseSubTenantIndices(input, mainTenantAgreementIndex, tenantExistList.size());
                                    if (!subTenantUpdatedIndices.isEmpty()) {
                                        for (Integer index : subTenantUpdatedIndices) {
                                            newSubTenants.add(tenantExistList.get(index - 1));
                                        }
                                        break;
                                    } else {
                                        System.out.println("Invalid input. Ensure sub-tenant indices are valid and do not include the main tenant.");
                                    }
                                }
                                agreementToUpdate.setSubTenants(newSubTenants);
                                for(Tenant tenant : newSubTenants) {
                                    tenant.addRentalAgreement(agreementToUpdate);
                                }
                                for(Tenant tenant : oldSubTenants) {
                                    if(!newSubTenants.contains(tenant)) {
                                        tenant.removeRentalAgreement(agreementToUpdate);
                                    }
                                }
                                rentalManager.saveTenantToFile();
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                System.out.println("Rental Agreement successfully updated.");
                                break;

                            case 4: // Update Hosts
                                List<Host> hostExistList = getAllHosts();
                                System.out.println("Enter Host indices separated by spaces: ");
                                List<Integer> hostUpdatedIndices = new ArrayList<>();
                                List<Host> oldHosts = agreementToUpdate.getHosts();
                                List<Host> newHosts = new ArrayList<>();
                                while (true) {
                                    String input = scanner.nextLine();
                                    hostUpdatedIndices = parseHostIndices(input, hostExistList.size());
                                    if (!hostUpdatedIndices.isEmpty()) {
                                        for (Integer index : hostUpdatedIndices) {
                                            newHosts.add(hostExistList.get(index - 1));
                                        }
                                        break;
                                    } else {
                                        System.out.println("Invalid input. Ensure host indices are valid and  separated by spaces.");
                                    }
                                }
                                agreementToUpdate.setHosts(newHosts);
                                for(Host host : oldHosts) {
                                    if(!newHosts.contains(host)) {
                                        host.removeRentalAgreement(agreementToUpdate);
                                        host.removeManagedProperty(agreementToUpdate.getProperty());
                                        host.removeCooperatingOwner(agreementToUpdate.getOwner());
                                        agreementToUpdate.getOwner().removeHost(host);
                                    }
                                }
                                for(Host host : newHosts) {
                                    host.addRentalAgreement(agreementToUpdate);
                                    host.addCooperatingOwner(agreementToUpdate.getOwner());
                                    host.addManagedProperty(agreementToUpdate.getProperty());
                                }
                                agreementToUpdate.getOwner().addHost(newHosts);
                                rentalManager.saveHostToFile();
                                rentalManager.saveOwnerToFile();
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                System.out.println("Rental Agreement successfully updated.");
                                break;

                            case 5: // Update Owner
                                List<Owner> ownerExistList = getAllOwners();
                                System.out.print("Enter Owner Index: ");
                                int ownerUpdatedIndex = getValidIndex(scanner, ownerExistList.size());
                                Owner oldOwner = agreementToUpdate.getOwner();
                                Owner newOwner = ownerExistList.get(ownerUpdatedIndex - 1);
                                agreementToUpdate.setOwner(newOwner);
                                List<Host> currentHost = agreementToUpdate.getHosts();
                                oldOwner.removeRentalAgreement(agreementToUpdate);
                                oldOwner.removeProperty(agreementToUpdate.getProperty());
                                for(Host host : currentHost) {
                                    host.removeCooperatingOwner(oldOwner);
                                    oldOwner.removeHost(host);
                                }
                                for(Host host : currentHost) {
                                    host.addCooperatingOwner(newOwner);
                                }
                                newOwner.addHost(currentHost);
                                newOwner.addProperty(agreementToUpdate.getProperty());
                                newOwner.addRentalAgreement(agreementToUpdate);
                                rentalManager.saveHostToFile();
                                rentalManager.saveOwnerToFile();
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                System.out.println("Rental Agreement successfully updated.");
                                break;

                            case 6: // Update Period
                                System.out.println("Enter new period (daily, weekly, fortnightly, monthly): ");
                                String newPeriod = scanner.nextLine().trim().toLowerCase();
                                boolean isValidPeriod = Arrays.asList(validPeriods).contains(newPeriod);
                                if (isValidPeriod) {
                                    agreementToUpdate.setPeriod(newPeriod);
                                    System.out.println("Rental Agreement successfully updated.");
                                } else {
                                    System.out.println("Invalid period. No changes made.");
                                }
                                rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                break;

                            case 7: // Update Start Contract Date
                                System.out.println("Enter new start date (dd/MM/yyyy): ");
                                try {
                                    Date newStartDate = dateFormat.parse(scanner.nextLine());
                                    agreementToUpdate.setStartContractDate(newStartDate);
                                    rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                    System.out.println("Rental Agreement successfully updated.");
                                } catch (ParseException e) {
                                    System.out.println("Invalid date format. No changes made.");
                                }
                                break;

                            case 8: // Update End Contract Date
                                System.out.println("Enter new end date (dd/MM/yyyy): ");
                                try {
                                    Date newEndDate = dateFormat.parse(scanner.nextLine());
                                    if (agreementToUpdate.getStartContractDate() != null && newEndDate.before(agreementToUpdate.getStartContractDate())) {
                                        System.out.println("End date cannot be before start date. No changes made.");
                                    } else {
                                        agreementToUpdate.setEndContractDate(newEndDate);
                                        rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                        System.out.println("Rental Agreement successfully updated.");
                                    }
                                } catch (ParseException e) {
                                    System.out.println("Invalid date format. No changes made.");
                                }
                                break;

                            case 9: // Update Renting Fee
                                System.out.println("Enter new renting fee: ");
                                try {
                                    double newFee = Double.parseDouble(scanner.nextLine());
                                    if (newFee > 0) {
                                        agreementToUpdate.setRentingFee(newFee);
                                        rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                        System.out.println("Rental Agreement successfully updated.");
                                    } else {
                                        System.out.println("Fee must be positive. No changes made.");
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid fee format. No changes made.");
                                }
                                break;

                            case 10: // Update Status
                                System.out.println("Enter new status (New, Active, Completed): ");
                                String newStatus = scanner.nextLine().trim();
                                if (newStatus.equalsIgnoreCase("New") || newStatus.equalsIgnoreCase("Active") || newStatus.equalsIgnoreCase("Completed")) {
                                    agreementToUpdate.setStatus(newStatus);
                                    rentalManager.updateRentalAgreement(agreementToUpdate.getAgreementId(), agreementToUpdate);
                                    System.out.println("Rental Agreement successfully updated.");
                                } else {
                                    System.out.println("Invalid status. Status must be in [New,Active,Completed]. No changes made.");
                                }
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    break;
                case 4:
                    // Delete a rental agreement
                    getAllRentalAgreements();
                    System.out.print("Enter Rental Agreement ID to delete (or type 'exit' to cancel): ");
                    String deleteId = scanner.nextLine();
                    if (deleteId.equalsIgnoreCase("exit")) {
                        break;
                    }
                    RentalAgreement agreementToDelete = rentalAgreementMap.get(deleteId);
                    if (agreementToDelete == null) {
                        System.out.println("Rental Agreement ID not found.");
                        break;
                    } else {
                        Owner tempOwner = agreementToDelete.getOwner();
                        tempOwner.removeProperty(agreementToDelete.getProperty());
                        for(Host host : agreementToDelete.getHosts()){
                            tempOwner.removeHost(host);
                        }
                        tempOwner.removeRentalAgreement(agreementToDelete);

                        List<Host> tempHostList = agreementToDelete.getHosts();
                        for(Host host : tempHostList){
                            host.removeCooperatingOwner(tempOwner);
                            host.removeManagedProperty(agreementToDelete.getProperty());
                            host.removeRentalAgreement(agreementToDelete);
                        }

                        Tenant tempMainTenant = agreementToDelete.getMainTenant();
                        tempMainTenant.removeRentalAgreement(agreementToDelete);

                        List<Tenant> tempSubTenantList = agreementToDelete.getSubTenants();
                        for(Tenant tenant : tempSubTenantList){
                            tenant.removeRentalAgreement(agreementToDelete);
                        }
                        rentalAgreementMap.remove(deleteId);
                        rentalManager.deleteRentalAgreement(deleteId);
                        rentalManager.saveOwnerToFile();
                        rentalManager.saveTenantToFile();
                        rentalManager.saveHostToFile();
                        System.out.println("Deleting Rental Agreement: " + agreementToDelete.toString());
                        System.out.println("Rental agreement deleted successfully.");
                    }
                    break;
                case 5:
                    // Generate Agreement Report
                    Boolean reverse = null;
                    System.out.println("Choose the sorting order:");
                    System.out.println("1. Ascending");
                    System.out.println("2. Descending");

                    System.out.print("Enter your sorting order choice: ");
                    int orderChoice = scanner.nextInt();
                    if(orderChoice == 1){
                        reverse = false;
                    } else if (orderChoice == 2) {
                        reverse = true;
                    } else {
                        System.out.println("Invalid choice for sorting order. Defaulting to Ascending.");
                        reverse = false;
                    }

                    System.out.println("Choose a sorting option:");
                    System.out.println("1. Sort by Agreement ID");
                    System.out.println("2. Sort by Start Contract Date");
                    System.out.println("3. Sort by Renting Fee");
                    System.out.println("4. Sort by Status");

                    System.out.print("Enter your choice: ");
                    int sortingChoice = scanner.nextInt();

                    List<RentalAgreement> rentalAgreements = new ArrayList<>(rentalAgreementMap.values());
                    switch (sortingChoice) {
                        case 1:
                            Collections.sort(rentalAgreements, Comparator.comparing(RentalAgreement::getAgreementId));
                            System.out.println("Sorted by Agreement ID.");
                            break;
                        case 2:
                            Collections.sort(rentalAgreements, Comparator.comparing(RentalAgreement::getStartContractDate));
                            System.out.println("Sorted by Start Contract Date.");
                            break;
                        case 3:
                            Collections.sort(rentalAgreements, Comparator.comparingDouble(RentalAgreement::getRentingFee));
                            System.out.println("Sorted by Renting Fee.");
                            break;
                        case 4:
                            Collections.sort(rentalAgreements, Comparator.comparing(RentalAgreement::getStatus));
                            System.out.println("Sorted by Status.");
                            break;
                        default:
                            System.out.println("Invalid choice. No sorting applied.");
                            break;
                    }
                    if (Boolean.TRUE.equals(reverse)) {
                        Collections.reverse(rentalAgreements);
                        System.out.println("Sorting applied in Descending order.");
                    } else {
                        System.out.println("Sorting applied in Ascending order.");
                    }
                    writeAgreementReportToFile(rentalAgreements);
                    System.out.println("Rental Agreement generated successfully.");
                    break;
                case 6: // Make payment
                    while (true){
                        System.out.println("Enter Tenant ID (or type 'exit' to cancel): ");
                        String tenantId = scanner.nextLine();
                        if (tenantId.equalsIgnoreCase("exit")) {
                            break;
                        }
                        String paymentMethod = null;
                        double amount = 0.0;
                        Date date = null;
                        Tenant tenant = tenantMap.get(tenantId);
                        if(tenant != null && !tenantId.equalsIgnoreCase("exit")){
                            while(true){
                                System.out.println("Enter Payment method (Cash, Bank Transfer, Checks): ");
                                paymentMethod = scanner.nextLine().trim();
                                if(paymentMethod.equals("Cash") || paymentMethod.equals("Bank Transfer") || paymentMethod.equals("Checks")){
                                    break;
                                } else {
                                    System.out.println("Invalid payment method. Please enter valid payment method.");
                                }
                            }
                            while (true) {
                                System.out.println("Enter amount: ");
                                String input = scanner.nextLine().trim();
                                try {
                                    amount = Double.parseDouble(input);
                                    if (amount <= 0) {
                                        System.out.println("Renting fee must be a positive number. Please try again.");
                                    } else {
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid number for the renting fee.");
                                }
                            }
                            while (date == null) {
                                System.out.println("Enter the payment date (dd/MM/yyyy): ");
                                String dateInput = scanner.nextLine().trim();
                                try {
                                    date = dateFormat.parse(dateInput);
                                } catch (ParseException e) {
                                    System.out.println("Invalid date format. Please try again.");
                                }
                            }
                            // Generate payment ID
                            Random randomGenerate = new Random();
                            String paymentId;
                            do {
                                int randomNum = 10000000 + randomGenerate.nextInt(90000000);
                                paymentId = "P-" + randomNum;
                            } while (paymentMap.containsKey(paymentId));
                            Payment newPayment = new Payment(paymentId, amount, paymentMethod, date);
                            paymentMap.put(paymentId, newPayment);
                            tenant.addPayment(newPayment);
                            // Save payment
                            rentalManager.savePaymentToFile(paymentMap);
                            rentalManager.saveTenantToFile();
                            break;
                        } else {
                            System.out.println("Invalid tenant ID. Please enter a valid tenant ID.");
                        }
                    }
                    break;
                case 7:
                    getAllProperties();
                    break;
                case 8:
                    getAllHosts();
                    break;
                case 9:
                    getAllOwners();
                    break;
                case 10:
                    getAllTenants();
                    break;
                case 11:
                    getAllPayments();
                    break;
                case 12:
                    // Exit the program
                    System.out.println("Exiting the program.");
                    scanner.close(); // Close the scanner
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public static void loadDataFromFiles(){
        FileManager fileManager = new FileManager();
        hostMap = fileManager.loadHostsFileFirstPass();
        ownerMap = fileManager.loadOwnersFileFirstPass();
        tenantMap = fileManager.loadTenantsFileFirstPass();
        propertyMap = fileManager.loadPropertiesFileFirstPass();
        rentalAgreementMap = fileManager.loadRentalAgreementsFileFirstPass();
        paymentMap = fileManager.loadPaymentsFile();

        hostMap = fileManager.loadHostsFileSecondPass(hostMap, ownerMap, propertyMap, rentalAgreementMap);
        ownerMap = fileManager.loadOwnersFileSecondPass(ownerMap, propertyMap, hostMap, rentalAgreementMap);
        tenantMap = fileManager.loadTenantsFileSecondPass(tenantMap, rentalAgreementMap, paymentMap);
        rentalAgreementMap = fileManager.loadRentalAgreementsFileSecondPass(rentalAgreementMap, tenantMap, hostMap, ownerMap, propertyMap);
    }

    public static void writeAgreementReportToFile(List<RentalAgreement> rentalAgreements){
        FileManager fileManager = new FileManager();
        fileManager.generateRentalAgreementReport(rentalAgreements);
    }

    public static List<Property> getProperty(){
        List<Property> properties = new ArrayList<>(propertyMap.values());
        for (int i = 0; i < properties.size(); i++) {
            System.out.println((i + 1) + ". " + properties.get(i).toString());
        }
        return properties;
    }

    public static void getAllRentalAgreements(){
        List<RentalAgreement> rentalAgreements = new ArrayList<>(rentalAgreementMap.values());
        for (int i = 0; i < rentalAgreements.size(); i++) {
            System.out.println((i + 1) + ". " + rentalAgreements.get(i).toString());
        }
    }

    public static void getAllProperties() {
        boolean running = true;
        List<Property> properties = new ArrayList<>(propertyMap.values());
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. Get All Commercial Properties");
            System.out.println("2. Get All Residential Properties");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        List<Property> commercialProperties = new ArrayList<>();
                        for (Property property : properties) {
                            if (property instanceof CommercialProperty) {
                                commercialProperties.add(property);
                            }
                        }
                        System.out.println("\nCommercial Properties:");
                        for (int i = 0; i < commercialProperties.size(); i++) {
                            System.out.println((i + 1) + ". " + commercialProperties.get(i).toString());
                        }
                        running = false;
                        break;

                    case 2:
                        List<Property> residentialProperties = new ArrayList<>();
                        for (Property property : properties) {
                            if (property instanceof ResidentialProperty) {
                                residentialProperties.add(property);
                            }
                        }
                        System.out.println("\nResidential Properties:");
                        for (int i = 0; i < residentialProperties.size(); i++) {
                            System.out.println((i + 1) + ". " + residentialProperties.get(i).toString());
                        }
                        running = false;
                        break;

                    case 3:
                        System.out.println("Back to main menu...\n");
                        running = false;
                        return;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.\n");
                scanner.nextLine();
            }
        }
    }

    public static List<Host> getAllHosts() {
        List<Host> hosts = new ArrayList<>(hostMap.values());
        for(int i = 0; i < hosts.size(); i++){
            System.out.println((i + 1) + ". " + hosts.get(i).toString());
        }
        return hosts;
    }

    public static List<Owner> getAllOwners() {
        List<Owner> owners = new ArrayList<>(ownerMap.values());
        for(int i = 0; i < owners.size(); i++){
            System.out.println((i + 1) + ". " + owners.get(i).toString());
        }
        return owners;
    }

    public static List<Tenant> getAllTenants() {
        List<Tenant> tenants = new ArrayList<>(tenantMap.values());
        for(int i = 0; i < tenants.size(); i++){
            System.out.println((i + 1) + ". " + tenants.get(i).toString());
        }
        return tenants;
    }

    public static void getAllPayments() {
        List<Payment> payments = new ArrayList<>(paymentMap.values());
        for(int i = 0; i < payments.size(); i++){
            System.out.println((i + 1) + ". " + payments.get(i).toString());
        }
    }

    private static int getValidIndex(Scanner scanner, int size) {
        while (true) {
            try {
                int index = Integer.parseInt(scanner.nextLine());
                if (index > 0 && index <= size) {
                    return index;
                } else {
                    System.out.println("Please enter a valid index between 1 and " + size + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static List<Integer> parseSubTenantIndices(String input, int mainTenantIndex, int size) {
        List<Integer> subTenantIndices = new ArrayList<>();
        try {
            String[] tokens = input.split("\\s+");
            for (String token : tokens) {
                int index = Integer.parseInt(token);
                if (index > 0 && index <= size && index != mainTenantIndex) {
                    subTenantIndices.add(index);
                } else {
                    return Collections.emptyList(); // Invalid input
                }
            }
        } catch (NumberFormatException e) {
            return Collections.emptyList(); // Invalid input
        }
        return subTenantIndices;
    }

    private static List<Integer> parseHostIndices(String input, int size) {
        List<Integer> hostIndices = new ArrayList<>();
        try {
            String[] tokens = input.split("\\s+");
            for (String token : tokens) {
                int index = Integer.parseInt(token);
                if (index > 0 && index <= size) {
                    hostIndices.add(index);
                } else {
                    return Collections.emptyList(); // Invalid input
                }
            }
        } catch (NumberFormatException e) {
            return Collections.emptyList(); // Invalid input
        }
        return hostIndices;
    }

}
