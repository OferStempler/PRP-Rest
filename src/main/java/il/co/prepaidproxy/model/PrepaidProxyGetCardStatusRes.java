package il.co.prepaidproxy.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomerpaz on 1/30/17.
 */
@XmlRootElement(name="PrepaidProxyGetCardStatusRes")
public class PrepaidProxyGetCardStatusRes
{
    private String errorCode = "0";
    private String errorMessage = "OK";
    private List<Card> cards;

    public PrepaidProxyGetCardStatusRes(){
        cards = new ArrayList<>();
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

    @XmlElementWrapper(name="cards")
    @XmlElement(name="cards")
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
