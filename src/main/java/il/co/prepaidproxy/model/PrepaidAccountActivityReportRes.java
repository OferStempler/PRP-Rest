package il.co.prepaidproxy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="PrepaidAccountActivityReportRes")
public class PrepaidAccountActivityReportRes
{
    private String errorCode = "0";
    private String errorMessage = "OK";


    List<CardTransaction> acctTransactions;
    private String accountBalance;

    @XmlElement(name="errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PrepaidAccountActivityReportRes(){
        acctTransactions = new ArrayList<>();
    }

    @XmlElement(name="errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @XmlElementWrapper(name="acctTransactions")
    @XmlElement(name="acctTransaction")
    public List<CardTransaction> getAcctTransactions() {
        return acctTransactions;
    }

    public void setAcctTransactions(List<CardTransaction> acctTransactions) {
        this.acctTransactions = acctTransactions;
    }

    @XmlElement(name="accountBalance")
    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

}
