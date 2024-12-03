package com.consoleApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class RentalAgreement {
    private String agreementId;
    private Property property;
    private Tenant mainTenant;
    private List<Tenant> subTenants;
    private List<Host> hosts;
    private Owner owner;
    private String period;
    private Date startContractDate;
    private Date endContractDate;
    private double rentingFee;
    private String status;

    public RentalAgreement(String agreementId, Property property, Tenant mainTenant, List<Tenant> subTenants, List<Host> hosts, Owner owner, String period, Date startContractDate, Date endContractDate, double rentingFee, String status) {
        this.agreementId = agreementId;
        this.property = property;
        this.mainTenant = mainTenant;
        this.subTenants = subTenants;
        this.hosts = hosts;
        this.period = period;
        this.owner = owner;
        this.status = status;
        this.startContractDate = startContractDate;
        this.endContractDate = endContractDate;
        this.rentingFee = rentingFee;
    }

    public RentalAgreement(String agreementId, String period, Date startContractDate, Date endContractDate, double rentingFee, String status) {
        this.agreementId = agreementId;
        this.period = period;
        this.startContractDate = startContractDate;
        this.endContractDate = endContractDate;
        this.rentingFee = rentingFee;
        this.status = status;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Tenant getMainTenant() {
        return mainTenant;
    }

    public void setMainTenant(Tenant mainTenant) {
        this.mainTenant = mainTenant;
    }

    public List<Tenant> getSubTenants() {
        return subTenants;
    }

    public void setSubTenants(List<Tenant> subTenants) {
        this.subTenants = subTenants;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getStartContractDate() {
        return startContractDate;
    }

    public void setStartContractDate(Date startContractDate) {
        this.startContractDate = startContractDate;
    }

    public Date getEndContractDate() {
        return endContractDate;
    }

    public void setEndContractDate(Date endContractDate) {
        this.endContractDate = endContractDate;
    }

    public double getRentingFee() {
        return rentingFee;
    }

    public void setRentingFee(double rentingFee) {
        this.rentingFee = rentingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "RentalAgreement{" +
                "agreementId='" + agreementId + '\'' +
                ", property=" + (property != null ? property.getPropertyId() : "null") +
                ", mainTenant=" + (mainTenant != null ? mainTenant.getId() : "null") +
                ", subTenants=" + (subTenants != null ? subTenants.stream().map(Tenant::getId).toList() : "null") +
                ", hosts=" + (hosts != null ? hosts.stream().map(Host::getId).toList() : "null") +
                ", owner=" + (owner != null ? owner.getId() : "null") +
                ", period='" + period + '\'' +
                ", startContractDate=" + startContractDate +
                ", endContractDate=" + endContractDate +
                ", rentingFee=" + rentingFee +
                ", status='" + status + '\'' +
                '}';
    }
}
