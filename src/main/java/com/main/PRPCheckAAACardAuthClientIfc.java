package com.main;

public interface PRPCheckAAACardAuthClientIfc {

    public class CardAccount   {
        public String accountNumber;
        public String currencyCode;
    }

//    public class CardAccountsArray   {
//        public CardAccount cardAccount;
//    }


    public class CardData   {
        public CardData(){;}
        public String clientNumber;
        public String expirationDate;
        public String maskedCardNumber;
        public String CJNumber;
        public String cardTypeCode;
        public CardAccount[] cardAccountsArray;
    }





    public class SourceCardData   {
        public SourceCardData(){;}
        public CardData[] cardsDataArray;
    }
    public class TargetCardData   {
        public TargetCardData(){;}
        public  CardData[] cardsDataArray;
    }

    public class Response   {
        public Response() {}
        public  String         sumForTransfer;
        public SourceCardData sourceCardData;
        public TargetCardData targetCardData;
    }

    public class PRPCheckAAACardAuthResponse{
        public int      responseCode;
        public String   responseDesc;
        public Response responseObject;
    }

    public enum HttpProtocols{
        HTTP("http"),
        HTTPS("https"),;

        private String protocolNameStr;
        private HttpProtocols(String protocolNameStr){
            this.protocolNameStr = protocolNameStr;
        }
    }

    public PRPCheckAAACardAuthResponse execute(

            String operationDetails,
            String OTP,
            String contactId,

            String sessionId,
            String transactionId,

            String sourceCjNumber,
            String targetCjNumber,
            String targetPhoneNumber,
            String transferAmount,
            String sourceCurrency,
            String targetCurrency

//            String idNumber,
//            String accountNumber,
//            String accountBranchNumber

            );




}
