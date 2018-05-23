package il.co.prepaidproxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PrepaidProxyPerformTransferReq")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class PrepaidProxyPerformTransferReq {

    String sessionId;
    String operationDetails;
    String otp;
    String contactId;
    String transactionId;

    String sourceCjNumber;
    String targetCjNumber;
    String targetPhoneNumber;
    String transferAmount;
    String targetCurrency;
    String sourceCurrency;

    String channelName;
    String operationType;
    String institutionNumber;

    @XmlElement(name="transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @XmlElement(name="sessionId")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    @XmlElement(name="contactId")
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @XmlElement(name="channelName")
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @XmlElement(name="operationType")
    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @XmlElement(name="transferAmount")
    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    @XmlElement(name="otp")
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @XmlElement(name="operationDetails")
    public String getOperationDetails() {
        return operationDetails;
    }

    public void setOperationDetails(String operationDetails) {
        this.operationDetails = operationDetails;
    }

    @XmlElement(name="targetCurrency")
    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @XmlElement(name="targetCjNumber")
    public String getTargetCjNumber() {
        return targetCjNumber;
    }

    public void setTargetCjNumber(String targetCjNumber) {
        this.targetCjNumber = targetCjNumber;
    }

    @XmlElement(name="targetPhoneNumber")
    public String getTargetPhoneNumber() {
        return targetPhoneNumber;
    }

    public void setTargetPhoneNumber(String targetPhoneNumber) {
        this.targetPhoneNumber = targetPhoneNumber;
    }

    @XmlElement(name="sourceCjNumber")
    public String getSourceCjNumber() {
        return sourceCjNumber;
    }

    public void setSourceCjNumber(String sourceCjNumber) {
        this.sourceCjNumber = sourceCjNumber;
    }

    @XmlElement(name="sourceCurrency")
    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    @XmlElement(name="institutionNumber")
    public String getInstitutionNumber() {
        return institutionNumber;
    }

    public void setInstitutionNumber(String institutionNumber) {
        this.institutionNumber = institutionNumber;
    }
}


/*

<PrepaidProxyPerformTransferReq>
<contactId></contactId>
<channelName> </channelName>
<operationType></operationType>
<transferAmount></transferAmount>
<otp></otp>
<operationDetails>"CardToCardTransferOTP"</operationDetails>
<targetCurrency> </targetCurrency>
<targetCjNumber></targetCjNumber>
<targetPhoneNumber></targetPhoneNumber>
<sourceCjNumber></sourceCjNumber>
<sourceCurrency> </sourceCurrency>
<institutionNumber></institutionNumber>
</PrepaidProxyPerformTransferReq>
 */