package il.co.prepaidproxy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomerpaz on 1/30/17.
 */
@XmlRootElement(name="PrepaidProxyBalanceDetailsRes")
public class PrepaidProxyBalanceDetailsRes
{
    private String errorCode = "0";
    private String errorMessage = "OK";

    private List<BalanceDetail> balanceDetails;

    public PrepaidProxyBalanceDetailsRes(){
        balanceDetails = new ArrayList<>();
    }

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

    @XmlElementWrapper(name="balanceDetails")
    @XmlElement(name="balanceDetails")
    public List<BalanceDetail> getBalanceDetails() {
        return balanceDetails;
    }

    public void setBalanceDetails(List<BalanceDetail> balanceDetails) {
        this.balanceDetails = balanceDetails;
    }
    @Override
    public String toString() {
        return "PrepaidProxyBalanceDetailsRes [errorCode=" + errorCode + ", errorMessage=" + errorMessage
                + ", balanceDetails=" + balanceDetails + "]";
    }
}
