package il.co.prepaidproxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PrepaidProxySetCardStatusReq")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepaidProxySetCardStatusReq {

    private String clientNumber;
    private String institutionNumber;
    private String status;

    @XmlElement(name="institutionNumber")
    public String getInstitutionNumber() {
        return institutionNumber;
    }

    public void setInstitutionNumber(String institutionNumber) {
        this.institutionNumber = institutionNumber;
    }

    @XmlElement(name="status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name="clientNumber")
    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }
}