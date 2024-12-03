package com.consoleApp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class RentalManagerImpl implements RentalManager {
    private final Map<String, RentalAgreement> rentalAgreements;
    private final Map<String, Host> hosts;
    private final Map<String, Owner> owners;
    private final Map<String, Tenant> tenants;
    private final Map<String, Payment> payments;

    private final String rentalAgreementsFilePath = "src/main/java/com/consoleApp/data/rentalAgreements.txt";
    private final String hostsFilePath = "src/main/java/com/consoleApp/data/hosts.txt";
    private final String ownersFilePath = "src/main/java/com/consoleApp/data/owners.txt";
    private final String tenantsFilePath = "src/main/java/com/consoleApp/data/tenants.txt";
    private final String paymentsFilePath = "src/main/java/com/consoleApp/data/payments.txt";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public RentalManagerImpl(Map<String, RentalAgreement> rentalAgreements, Map<String, Host> hosts, Map<String, Owner> owners, Map<String, Tenant> tenants, Map<String, Payment> payments) {
        this.rentalAgreements = new LinkedHashMap<>(rentalAgreements);
        this.hosts = new LinkedHashMap<>(hosts);
        this.owners = new LinkedHashMap<>(owners);
        this.tenants = new LinkedHashMap<>(tenants);
        this.payments = new LinkedHashMap<>(payments);
    }

    public void saveRentalAgreementToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rentalAgreementsFilePath))) {
            for (RentalAgreement agreement : rentalAgreements.values()) {
                StringBuilder subTenants = new StringBuilder();
                for (Tenant tenant : agreement.getSubTenants()) {
                    if (subTenants.length() > 0) {
                        subTenants.append(" ");
                    }
                    subTenants.append(tenant.getId());
                }
                String subTenantsString = subTenants.toString();
                StringBuilder hosts = new StringBuilder();
                for (Host host : agreement.getHosts()) {
                    if (hosts.length() > 0) {
                        hosts.append(" ");
                    }
                    hosts.append(host.getId());
                }
                String hostsString = hosts.toString();
                bw.write(String.join(",",
                        agreement.getAgreementId(),
                        agreement.getProperty().getPropertyId(),
                        agreement.getMainTenant().getId(),
                        subTenantsString,
                        hostsString,
                        agreement.getOwner().getId(),
                        agreement.getPeriod(),
                        dateFormat.format(agreement.getStartContractDate()),
                        dateFormat.format(agreement.getEndContractDate()),
                        String.valueOf(agreement.getRentingFee()),
                        agreement.getStatus()
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRentalAgreement(RentalAgreement rentalAgreement) {
        rentalAgreements.put(rentalAgreement.getAgreementId(), rentalAgreement);
        saveRentalAgreementToFile();
    }

    @Override
    public void updateRentalAgreement(String agreementId, RentalAgreement updatedRentalAgreement) {
        if(rentalAgreements.containsKey(agreementId)) {
            rentalAgreements.put(agreementId, updatedRentalAgreement);
            saveRentalAgreementToFile();
        } else {
            System.err.println("Rental agreement with ID " + agreementId + " not found.");
        }
    }

    @Override
    public void deleteRentalAgreement(String agreementId) {
        if (rentalAgreements.containsKey(agreementId)) {
            rentalAgreements.remove(agreementId);
            saveRentalAgreementToFile();
        } else {
            System.err.println("Rental agreement with ID " + agreementId + " not found.");
        }
    }

    public void saveHostToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(hostsFilePath))) {
            for(Host host : hosts.values()) {
                StringBuilder managedPropertiesList = new StringBuilder();
                if(host.getManagedProperties().size() == 0){
                    managedPropertiesList.append(" ");
                } else {
                    for(Property property : host.getManagedProperties()){
                        if(managedPropertiesList.length() > 0) {
                            managedPropertiesList.append(" ");
                        }
                        managedPropertiesList.append(property.getPropertyId());
                    }
                }
                String managedPropertiesListString = managedPropertiesList.toString();

                StringBuilder cooperatingOwnersList = new StringBuilder();
                if(host.getCooperatingOwners().size() == 0){
                    cooperatingOwnersList.append(" ");
                } else {
                    for(Owner owner : host.getCooperatingOwners()){
                        if(cooperatingOwnersList.length() > 0) {
                            cooperatingOwnersList.append(" ");
                        }
                        cooperatingOwnersList.append(owner.getId());
                    }
                }
                String cooperatingOwnersListString = cooperatingOwnersList.toString();

                StringBuilder rentalAgreementsList = new StringBuilder();
                if(host.getRentalAgreements().size() == 0){
                    rentalAgreementsList.append(" ");
                    rentalAgreementsList.append(",");
                } else {
                    for(RentalAgreement rentalAgreement : host.getRentalAgreements()) {
                        if(rentalAgreementsList.length() > 0) {
                            rentalAgreementsList.append(" ");
                        }
                        rentalAgreementsList.append(rentalAgreement.getAgreementId());
                    }
                }
                String rentalAgreementsListString = rentalAgreementsList.toString();

                bw.write(String.join(",",
                        host.getId(),
                        host.getFullName(),
                        dateFormat.format(host.getDateOfBirth()),
                        host.getContactInfo(),
                        managedPropertiesListString,
                        cooperatingOwnersListString,
                        rentalAgreementsListString
                        ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveOwnerToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ownersFilePath))) {
            for(Owner owner : owners.values()) {
                StringBuilder ownedPropertiesList = new StringBuilder();
                if(owner.getOwnedProperties().size() == 0){
                    ownedPropertiesList.append(" ");
                } else {
                    for(Property property : owner.getOwnedProperties()){
                        if(ownedPropertiesList.length() > 0) {
                            ownedPropertiesList.append(" ");
                        }
                        ownedPropertiesList.append(property.getPropertyId());
                    }
                }
                String ownedPropertiesListString = ownedPropertiesList.toString();

                StringBuilder cooperatingHostsList = new StringBuilder();
                if(owner.getCooperatingHosts().size() == 0){
                    cooperatingHostsList.append(" ");
                } else {
                    for(Host host : owner.getCooperatingHosts()){
                        if(cooperatingHostsList.length() > 0) {
                            cooperatingHostsList.append(" ");
                        }
                        cooperatingHostsList.append(host.getId());
                    }
                }
                String cooperatingHostsListString = cooperatingHostsList.toString();

                StringBuilder rentalAgreementsList = new StringBuilder();
                if(owner.getRentalAgreements().size() == 0){
                    rentalAgreementsList.append(" ");
                    rentalAgreementsList.append(",");
                } else {
                    for(RentalAgreement rentalAgreement : owner.getRentalAgreements()) {
                        if(rentalAgreementsList.length() > 0) {
                            rentalAgreementsList.append(" ");
                        }
                        rentalAgreementsList.append(rentalAgreement.getAgreementId());
                    }
                }
                String rentalAgreementsListString = rentalAgreementsList.toString();

                bw.write(String.join(",",
                        owner.getId(),
                        owner.getFullName(),
                        dateFormat.format(owner.getDateOfBirth()),
                        owner.getContactInfo(),
                        ownedPropertiesListString,
                        cooperatingHostsListString,
                        rentalAgreementsListString
                        ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTenantToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenantsFilePath))) {
            for(Tenant tenant : tenants.values()) {
                StringBuilder rentalAgreementList = new StringBuilder();
                if(tenant.getRentalAgreements().size() == 0){
                    rentalAgreementList.append(" ");
                } else {
                    for(RentalAgreement rentalAgreement : tenant.getRentalAgreements()) {
                        if(rentalAgreementList.length() > 0) {
                            rentalAgreementList.append(" ");
                        }
                        rentalAgreementList.append(rentalAgreement.getAgreementId());
                    }
                }
                String rentalAgreementListString = rentalAgreementList.toString();

                StringBuilder paymentList = new StringBuilder();
                if(tenant.getPaymentRecords().size() == 0){
                    paymentList.append(" ");
                    paymentList.append(",");
                } else {
                    for(Payment payment : tenant.getPaymentRecords()){
                        if(paymentList.length() > 0) {
                            paymentList.append(" ");
                        }
                        paymentList.append(payment.getPaymentId());
                    }
                }
                String paymentListString = paymentList.toString();

                bw.write(String.join(",",
                        tenant.getId(),
                        tenant.getFullName(),
                        dateFormat.format(tenant.getDateOfBirth()),
                        tenant.getContactInfo(),
                        rentalAgreementListString,
                        paymentListString
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePaymentToFile(Map<String, Payment> payments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(paymentsFilePath))) {
            for(Payment payment : payments.values()) {
                bw.write(String.join(",",
                        payment.getPaymentId(),
                        String.valueOf(payment.getAmount()),
                        payment.getPaymentMethod(),
                        dateFormat.format(payment.getDate())
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<RentalAgreement> getAllRentalAgreements() {
        return new ArrayList<>(rentalAgreements.values());
    }
}
