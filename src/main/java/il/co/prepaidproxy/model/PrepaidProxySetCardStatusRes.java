package il.co.prepaidproxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PrepaidProxyRes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepaidProxySetCardStatusRes
{
    private String errorCode = "0";
    private String errorMessage = "OK";
    private String result;

    @XmlElement(name="errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    @XmlElement(name="errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "PrepaidProxySetCardStatusRes [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", result="
                + result + "]";
    }
}