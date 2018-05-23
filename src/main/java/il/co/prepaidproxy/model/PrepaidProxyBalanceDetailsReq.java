package il.co.prepaidproxy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PrepaidProxyBalanceDetailsReq")
public class PrepaidProxyBalanceDetailsReq {

    private String clientNumber;
    private String institutionNumber;
    private String expiryDate;
    private String currency;
    private String retrievalReference;

    @XmlElement(name="expiryDate")
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @XmlElement(name="currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name="institutionNumber")
    public String getInstitutionNumber() {
        return institutionNumber;
    }

    public void setInstitutionNumber(String institutionNumber) {
        this.institutionNumber = institutionNumber;
    }


    @XmlElement(name="clientNumber")
    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getRetrievalReference() {
        return retrievalReference;
    }

    public void setRetrievalReference(String retrievalReference) {
        this.retrievalReference = retrievalReference;
    }
}
