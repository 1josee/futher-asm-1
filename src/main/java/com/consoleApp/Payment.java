package com.consoleApp;

import java.util.Date;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public class Payment {
    private String paymentId;
    private double amount;
    private Date date;
    private String paymentMethod;

    public Payment(String paymentId, double amount, String paymentMethod, Date date) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
