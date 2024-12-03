package com.consoleApp;

import java.util.Date;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class Person {
    private String id;
    private String fullName;
    private Date dateOfBirth;
    private String contactInfo;

    public Person(){}
    // constructor
    public Person(String id, String fullName, Date dateOfBirth, String contactInformation) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.contactInfo = contactInformation;
    }

    // method to display person information
    public void displayInfo() {
        System.out.println("ID: " + this.id);
        System.out.println("Full Name: " + this.fullName);
        System.out.println("Date of Birth: " + this.dateOfBirth);
        System.out.println("Contact Information: " + this.contactInfo);
    }

    // getters
    public String getId() {
        return id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
