package com.consoleApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class Tenant extends Person{
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();
    private List<Payment> paymentRecords = new ArrayList<>();

    //constructor
    public Tenant(String id, String fullName, Date dateOfBirth, String contactInformation) {
        super(id, fullName, dateOfBirth, contactInformation);
    }

    //getters and setters
    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    public List<Payment> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(List<Payment> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    // Add methods
    public void addRentalAgreement(RentalAgreement agreement) {
        if (!rentalAgreements.contains(agreement)) {
            rentalAgreements.add(agreement);
        }
    }
    public void addPayment(Payment payment) {
        if (!paymentRecords.contains(payment)) {
            paymentRecords.add(payment);
        }
    }

    // Remove methods
    public void removeRentalAgreement(RentalAgreement agreement) {
        rentalAgreements.remove(agreement);
    }
    public void removePayment(Payment payment) {
        paymentRecords.remove(payment);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", rentalAgreements=" + rentalAgreements.stream().map(RentalAgreement::getAgreementId).toList() +
                ", paymentRecords=" + paymentRecords.stream().map(Payment::getPaymentId).toList() +
                '}';
    }
}