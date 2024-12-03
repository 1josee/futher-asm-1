package com.consoleApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class Host extends Person {
    private List<Property> managedProperties = new ArrayList<>();
    private List<Owner> cooperatingOwners = new ArrayList<>();
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();

    public Host(String id, String fullName, Date dateOfBirth, String contactInformation) {
        super(id, fullName, dateOfBirth, contactInformation);
    }

    public List<Property> getManagedProperties() {
        return managedProperties;
    }

    public void setManagedProperties(List<Property> managedProperties) {
        this.managedProperties = managedProperties;
    }

    public List<Owner> getCooperatingOwners() {
        return cooperatingOwners;
    }

    public void setCooperatingOwners(List<Owner> cooperatingOwners) {
        this.cooperatingOwners = cooperatingOwners;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    // Add methods
    public void addManagedProperty(Property property) {
        if (!managedProperties.contains(property)) {
            managedProperties.add(property);
        }
    }

    public void addCooperatingOwner(Owner owner) {
        if (!cooperatingOwners.contains(owner)) {
            cooperatingOwners.add(owner);
        }
    }

    public void addRentalAgreement(RentalAgreement rentalAgreement) {
        if (!rentalAgreements.contains(rentalAgreement)) {
            rentalAgreements.add(rentalAgreement);
        }
    }

    // Remove methods
    public void removeManagedProperty(Property property) {
        managedProperties.remove(property);
    }

    public void removeCooperatingOwner(Owner owner) {
        cooperatingOwners.remove(owner);
    }

    public void removeRentalAgreement(RentalAgreement rentalAgreement) {
        rentalAgreements.remove(rentalAgreement);
    }

    @Override
    public String toString() {
        return "Host{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", managedProperties=" + managedProperties.stream().map(Property::getPropertyId).toList()  +
                ", cooperatingOwners=" + cooperatingOwners.stream().map(Owner::getId).toList() +
                ", rentalAgreements=" + rentalAgreements.stream().map(RentalAgreement::getAgreementId).toList() +
                '}';
    }
}
