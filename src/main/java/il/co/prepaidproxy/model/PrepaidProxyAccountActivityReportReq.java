package il.co.prepaidproxy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PrepaidProxyAccountActivityReportReq")
public class PrepaidProxyAccountActivityReportReq {

    private String institutionNumber;
    private String accountNumber;
    private String retrievalReference;
    private String records;
    private String Days;
    private String dateFrom;
    private String dateTo;


    @XmlElement(name="institutionNumber")
    public String getInstitutionNumber() {
        return institutionNumber;
    }

    public void setInstitutionNumber(String institutionNumber) {
        this.institutionNumber = institutionNumber;
    }


    @XmlElement(name="retrievalReference")
    public String getRetrievalReference() {
        return retrievalReference;
    }

    public void setRetrievalReference(String retrievalReference) {
        this.retrievalReference = retrievalReference;
    }

    @XmlElement(name="accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @XmlElement(name="records")
    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    @XmlElement(name="Days")
    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    @XmlElement(name="dateFrom")
    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    @XmlElement(name="dateTo")
    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}