package il.co.prepaidproxy.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by tomerpaz on 3/6/17.
 */
public class BalanceDetail {


    String accountNumber;
    String availableAmount;
    String accountStatus;
    String currencyCode;
    String currentAmount;
    String limitAmount;
    String pendingAuthsAmount;


    @XmlElement(name="accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @XmlElement(name="availableAmount")
    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    @XmlElement(name="accountStatus")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @XmlElement(name="currencyCode")
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @XmlElement(name="currentAmount")
    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    @XmlElement(name="limitAmount")
    public String getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
    }

    @XmlElement(name="pendingAuthsAmount")
    public String getPendingAuthsAmount() {
        return pendingAuthsAmount;
    }

    public void setPendingAuthsAmount(String pendingAuthsAmount) {
        this.pendingAuthsAmount = pendingAuthsAmount;
    }
}


