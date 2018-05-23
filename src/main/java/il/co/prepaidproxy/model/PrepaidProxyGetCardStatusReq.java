package il.co.prepaidproxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PrepaidProxyGetCardStatusReq")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepaidProxyGetCardStatusReq {

    private String clientNumber;
    private String institutionNumber;

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
}