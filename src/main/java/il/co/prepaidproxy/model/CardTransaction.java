package il.co.prepaidproxy.model;


import javax.xml.bind.annotation.XmlElement;

public class CardTransaction {

    private String transactionType;
    private String transactionDate;
    private String postDate;
    private String transactionAmount;
    private String merchantName;
    private String settlementAmount;
    private String countryName;
    private String localAmount;
    private String issuingCurrency;
    private  String authCode;
    private String cardNumber;
    private String terminalCapability;
    private String localCurrency;

/*
    {
   "acctTransactions": [   {
      "transactionType": "531",
      "transactionDate": "20160802",
      "postDate": "20160802",
      "transactionAmount": "C000000000000",
      "merchantName": "One Time Subscription Fee               ",
      "settlementAmount": "D000000003000",
      "countryName": "",
      "localAmount": "C000000000000",
      "localCurrency": "376",
      "issuingCurrency": "376",
      "authCode": "",
      "terminalCapability": "999",
      "cardNumber": "540227******6675"
   }],
   "accountBalance": "C000000000000"
}
     */


    @XmlElement(name="terminalCapability")
    public String getTerminalCapability() {
        return terminalCapability;
    }

    public void setTerminalCapability(String terminalCapability) {
        this.terminalCapability = terminalCapability;
    }

    @XmlElement(name="authCode")
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    @XmlElement(name="issuingCurrency")
    public String getIssuingCurrency() {
        return issuingCurrency;
    }

    public void setIssuingCurrency(String issuingCurrency) {
        this.issuingCurrency = issuingCurrency;
    }
    @XmlElement(name="localAmount")
    public String getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(String localAmount) {
        this.localAmount = localAmount;
    }
    @XmlElement(name="countryName")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    @XmlElement(name="settlementAmount")
    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }
    @XmlElement(name="merchantName")
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    @XmlElement(name="transactionAmount")
    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    @XmlElement(name="postDate")
    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    @XmlElement(name="transactionDate")
    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    @XmlElement(name="transactionType")
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @XmlElement(name="cardNumber")
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    @XmlElement(name="localCurrency")
    public String getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }
}
