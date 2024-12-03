package com.consoleApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class Owner extends Person {
    private List<Property> ownedProperties = new ArrayList<>();
    private List<Host> cooperatingHosts = new ArrayList<>();
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();

    public Owner(String id, String fullName, Date dateOfBirth, String contactInformation) {
        super(id, fullName, dateOfBirth, contactInformation);
    }

    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(List<Property> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public List<Host> getCooperatingHosts() {
        return cooperatingHosts;
    }

    public void setCooperatingHosts(List<Host> cooperatingHosts) {
        this.cooperatingHosts = cooperatingHosts;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }
    // Add methods
    public void addRentalAgreement(RentalAgreement agreement) {
        if (!rentalAgreements.contains(agreement)) {
            rentalAgreements.add(agreement);
        }
    }

    public void addHost(List<Host> hostList) {
        for (Host host : hostList) {
            if (!cooperatingHosts.contains(host)) {
                cooperatingHosts.add(host);
            }
        }
    }

    public void addProperty(Property property) {
        if(!ownedProperties.contains(property)) {
            ownedProperties.add(property);
        }
    }
    // Remove methods
    public void removeRentalAgreement(RentalAgreement agreement) {
        rentalAgreements.remove(agreement);
    }

    public void removeHost(Host host) {
        cooperatingHosts.remove(host);
    }

    public void removeProperty(Property property) {
        ownedProperties.remove(property);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", ownedProperties=" + ownedProperties.stream().map(Property::getPropertyId).toList() +
                ", cooperatingHosts=" + cooperatingHosts.stream().map(Host::getId).toList() +
                ", rentalAgreements=" + rentalAgreements.stream().map(RentalAgreement::getAgreementId).toList() +
                '}';
    }
}
