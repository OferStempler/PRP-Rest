package il.co.prepaidproxy.model;


import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;

@ToString
public class Card {

    String expiryDate;
    String cardStatus;

    @XmlElement(name="expiryDate")
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @XmlElement(name="cardStatus")
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
}
