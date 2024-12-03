package com.consoleApp;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class FileManager {
    String hostsFilePath = "src/main/java/com/consoleApp/data/hosts.txt";
    String ownersFilePath = "src/main/java/com/consoleApp/data/owners.txt";
    String tenantsFilePath = "src/main/java/com/consoleApp/data/tenants.txt";
    String propertiesFilePath = "src/main/java/com/consoleApp/data/properties.txt";
    String rentalAgreementsFilePath = "src/main/java/com/consoleApp/data/rentalAgreements.txt";
    String paymentsFilePath = "src/main/java/com/consoleApp/data/payments.txt";
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Map<String, Host> loadHostsFileFirstPass(){
        Map<String, Host> hostsMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(hostsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String fullName = data[1].trim();
                String dobString = data[2].trim();
                String contactInfo = data[3].trim();
                Date dob = null;
                try {
                    dob = dateFormat.parse(dobString);
                } catch (ParseException e) {
                    System.err.println("Invalid date format for owner: " + dobString);
                }
                Host host = new Host(id, fullName, dob, contactInfo);
                hostsMap.put(id, host);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hostsMap;
    }

    public Map<String, Owner> loadOwnersFileFirstPass(){
        Map<String, Owner> ownersMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ownersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String fullName = data[1].trim();
                String dobString = data[2].trim();
                String contactInfo = data[3].trim();
                Date dob = null;
                try {
                    dob = dateFormat.parse(dobString);
                } catch (ParseException e) {
                    System.err.println("Invalid date format for owner: " + dobString);
                }
                Owner owner = new Owner(id, fullName, dob, contactInfo);
                ownersMap.put(id, owner);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ownersMap;
    }

    public Map<String, Tenant> loadTenantsFileFirstPass(){
        Map<String, Tenant> tenantsMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(tenantsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String fullName = data[1].trim();
                String dobString = data[2].trim();
                String contactInfo = data[3].trim();
                Date dob = null;
                try {
                    dob = dateFormat.parse(dobString);
                } catch (ParseException e) {
                    System.err.println("Invalid date format for owner: " + dobString);
                }
                Tenant tenant = new Tenant(id, fullName, dob, contactInfo);
                tenantsMap.put(id, tenant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tenantsMap;
    }

    public Map<String, Property> loadPropertiesFileFirstPass(){
        Map<String, Property> propertiesMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(propertiesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String propertyType = id.substring(0, 1);
                String address = data[1].trim();
                String pricingString = data[2].trim();
                String status = data[3].trim();
                double pricing = Double.parseDouble(pricingString);
                if(propertyType.equals("C")){
                    String businessType = data[4].trim();
                    String parkingSpaceString = data[5].trim();
                    String squareFootageString = data[6].trim();
                    int parkingSpaces = Integer.parseInt(parkingSpaceString);
                    double squareFootage = Double.parseDouble(squareFootageString);
                    CommercialProperty commercialProperty = new CommercialProperty(id, address, pricing, status, businessType, parkingSpaces, squareFootage);
                    propertiesMap.put(id, commercialProperty);
                } else if(propertyType.equals("R")){
                    String numberOfBedroomsString = data[4].trim();
                    boolean hasGarden = Boolean.parseBoolean(data[5].trim());
                    boolean isPetFriendly = Boolean.parseBoolean(data[6].trim());
                    int numberOfBedrooms = Integer.parseInt(numberOfBedroomsString);
                    ResidentialProperty residentialProperty = new ResidentialProperty(id, address, pricing, status, numberOfBedrooms, hasGarden, isPetFriendly);
                    propertiesMap.put(id, residentialProperty);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesMap;
    }

    public Map<String, RentalAgreement> loadRentalAgreementsFileFirstPass(){
        Map<String, RentalAgreement> agreementsMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rentalAgreementsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String period = data[6].trim();
                String startDateString = data[7].trim();
                String endDateString = data[8].trim();
                double rentingFeeString = Double.parseDouble(data[9].trim());
                String status = data[10].trim();
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = dateFormat.parse(startDateString);
                    endDate = dateFormat.parse(endDateString);
                } catch (ParseException e) {
                    System.err.println("Invalid date format for date: " + startDateString);
                }
                RentalAgreement rentalAgreement = new RentalAgreement(id, period, startDate, endDate, rentingFeeString, status);
                agreementsMap.put(id, rentalAgreement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agreementsMap;
    }


    public Map<String, Host> loadHostsFileSecondPass(Map<String, Host> hostsMap, Map<String, Owner> ownersMap, Map<String, Property> propertiesMap, Map<String, RentalAgreement> rentalAgreementMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(hostsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                Host host = hostsMap.get(id);
                if (host == null) {
                    System.err.println("Host not found for ID: " + id);
                    continue;
                }

                String[] managedPropertiesId = data[4].trim().isEmpty() ? new String[0] : data[4].trim().split(" ");
                String[] cooperatingOwnersId = data[5].trim().isEmpty() ? new String[0] : data[5].trim().split(" ");
                String[] rentalAgreementsId = data[6].trim().isEmpty() ? new String[0] : data[6].trim().split(" ");

                List<Property> propertiesList = new ArrayList<>();
                for (String propertyId : managedPropertiesId) {
                    Property property = propertiesMap.get(propertyId);
                    if (property != null) {
                        propertiesList.add(property);
                    } else {
                        System.err.println("Property not found for ID: " + propertyId);
                    }
                }

                List<Owner> ownerList = new ArrayList<>();
                for (String ownerId : cooperatingOwnersId) {
                    Owner owner = ownersMap.get(ownerId);
                    if (owner != null) {
                        ownerList.add(owner);
                    } else {
                        System.err.println("Owner not found for ID: " + ownerId);
                    }
                }

                List<RentalAgreement> rentalAgreementList = new ArrayList<>();
                for (String rentalAgreementId : rentalAgreementsId) {
                    RentalAgreement rentalAgreement = rentalAgreementMap.get(rentalAgreementId);
                    if (rentalAgreement != null) {
                        rentalAgreementList.add(rentalAgreement);
                    } else {
                        System.err.println("Rental Agreement not found for ID: " + rentalAgreementId);
                    }
                }

                host.setManagedProperties(propertiesList);
                host.setCooperatingOwners(ownerList);
                host.setRentalAgreements(rentalAgreementList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hostsMap;
    }


    public Map<String, Owner> loadOwnersFileSecondPass(Map<String, Owner> ownerMap, Map<String, Property> propertyMap, Map<String, Host> hostMap ,Map<String, RentalAgreement> rentalAgreementMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(ownersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                Owner owner = ownerMap.get(id);
                if (owner == null) {
                    System.err.println("Owner not found for ID: " + id);
                    continue;
                }

                String[] ownedPropertiesId = data[4].trim().isEmpty() ? new String[0] : data[4].trim().split(" ");
                String[] cooperatingHostsId = data[5].trim().isEmpty() ? new String[0] : data[5].trim().split(" ");
                String[] rentalAgreementsId = data[6].trim().isEmpty() ? new String[0] : data[6].trim().split(" ");

                List<Property> propertyList = new ArrayList<>();
                for (String propertyId : ownedPropertiesId) {
                    Property property = propertyMap.get(propertyId);
                    if (property != null) {
                        propertyList.add(property);
                    } else {
                        System.err.println("Property not found for ID: " + propertyId);
                    }
                }

                List<Host> hostList = new ArrayList<>();
                for (String hostId : cooperatingHostsId) {
                    Host host = hostMap.get(hostId);
                    if (host != null) {
                        hostList.add(host);
                    } else {
                        System.err.println("Host not found for ID: " + hostId);
                    }
                }

                List<RentalAgreement> rentalAgreementList = new ArrayList<>();
                for (String rentalAgreementId : rentalAgreementsId) {
                    RentalAgreement rentalAgreement = rentalAgreementMap.get(rentalAgreementId);
                    if (rentalAgreement != null) {
                        rentalAgreementList.add(rentalAgreement);
                    } else {
                        System.err.println("Rental Agreement not found for ID: " + rentalAgreementId);
                    }
                }

                owner.setOwnedProperties(propertyList);
                owner.setCooperatingHosts(hostList);
                owner.setRentalAgreements(rentalAgreementList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ownerMap;
    }

    public Map<String, Tenant> loadTenantsFileSecondPass(Map<String, Tenant> tenantMap, Map<String, RentalAgreement> rentalAgreementMap, Map<String, Payment> paymentMap){
        try (BufferedReader br = new BufferedReader(new FileReader(tenantsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                Tenant tenant = tenantMap.get(id);
                if (tenant == null) {
                    System.err.println("Tenant not found for ID: " + id);
                    continue;
                }
                String[] rentalAgreementsId = data[4].trim().isEmpty() ? new String[0] : data[4].trim().split(" ");
                String[] paymentsId = data[5].trim().isEmpty() ? new String[0] : data[5].trim().split(" ");
                List<RentalAgreement> rentalAgreementList = new ArrayList<>();
                for (String rentalAgreementId : rentalAgreementsId) {
                    RentalAgreement rentalAgreement = rentalAgreementMap.get(rentalAgreementId);
                    if (rentalAgreement != null) {
                        rentalAgreementList.add(rentalAgreement);
                    } else {
                        System.err.println("Rental Agreement not found for ID: " + rentalAgreementId);
                    }
                }
                List<Payment> paymentList = new ArrayList<>();
                for (String paymentId : paymentsId) {
                    Payment payment = paymentMap.get(paymentId);
                    if (payment != null) {
                        paymentList.add(payment);
                    } else {
                        System.err.println("Payment not found for ID: " + paymentId);
                    }
                }
                tenant.setRentalAgreements(rentalAgreementList);
                tenant.setPaymentRecords(paymentList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tenantMap;
    }

    public Map<String, RentalAgreement> loadRentalAgreementsFileSecondPass(Map<String, RentalAgreement> rentalAgreementMap, Map<String, Tenant> tenantMap,
                                                                           Map<String, Host> hostMap, Map<String, Owner> ownerMap, Map<String, Property> propertyMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(rentalAgreementsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                RentalAgreement rentalAgreement = rentalAgreementMap.get(id);
                if (rentalAgreement == null) {
                    System.err.println("Rental Agreement not found for ID: " + id);
                    continue;
                }
                String propertyId = data[1].trim();
                String mainTenantId = data[2].trim();
                String[] subTenantsId = data[3].trim().isEmpty() ? new String[0] : data[3].trim().split(" ");
                String[] hostsId = data[4].trim().isEmpty() ? new String[0] : data[4].trim().split(" ");
                String ownerId = data[5].trim();

                Property property = propertyMap.get(propertyId);
                Tenant mainTenant = tenantMap.get(mainTenantId);
                Owner owner = ownerMap.get(ownerId);

                List<Tenant> subTenants = new ArrayList<>();
                for (String tenantId : subTenantsId) {
                    Tenant tenant = tenantMap.get(tenantId);
                    if (tenant != null) {
                        subTenants.add(tenant);
                    } else {
                        System.err.println("Tenant not found for ID: " + ownerId);
                    }
                }

                List<Host> hosts = new ArrayList<>();
                for (String hostId : hostsId) {
                    Host host = hostMap.get(hostId);
                    if (host != null) {
                        hosts.add(host);
                    } else {
                        System.err.println("Host not found for ID: " + ownerId);
                    }
                }

                rentalAgreement.setOwner(owner);
                rentalAgreement.setProperty(property);
                rentalAgreement.setMainTenant(mainTenant);
                rentalAgreement.setSubTenants(subTenants);
                rentalAgreement.setHosts(hosts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentalAgreementMap;
    }

    public void generateRentalAgreementReport(List<RentalAgreement> rentalAgreements){
        String filePath = "src/main/java/com/consoleApp/data/agreementReport.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Rental Agreement Report:\n");
            writer.write("========================\n");
            for (RentalAgreement agreement : rentalAgreements) {
                int size = agreement.toString().length();
                String agreementString = agreement.toString().substring(16, size - 1);
                writer.write(agreementString);
                writer.newLine();
            }
            System.out.println("Report saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    public Map<String, Payment> loadPaymentsFile(){
        Map<String, Payment> paymentsMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(paymentsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                double amount = Double.parseDouble( data[1].trim());
                String paymentMethod = data[2].trim();
                String dateString = data[3].trim();
                Date date = null;
                try {
                    date = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    System.err.println("Invalid date format for date: " + dateString);
                }
                Payment payment = new Payment(id, amount, paymentMethod, date);
                paymentsMap.put(id, payment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paymentsMap;
    }
}
