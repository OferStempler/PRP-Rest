

        package il.co.prepaidproxy.service.impl;

        import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.PRPCheckAAACardAuthClientIfc;
import il.co.prepaidproxy.config.PrepaidproxySettings;
import il.co.prepaidproxy.model.*;
import il.co.prepaidproxy.service.Rs2Service;
import il.co.prepaidproxy.util.MyErrorHanler;
import il.co.prepaidproxy.util.RestUtil;
import il.co.prepaidproxy.util.SSLUtilities;
import il.co.prepaidproxy.util.ValidationUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;


@Service
@Log4j
public class Rs2ServiceImpl implements Rs2Service {

    static {
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();
    }

    @Autowired
    PrepaidproxySettings props;

    @Autowired
    PRPCheckAAACardAuthClientIfc prpCheckAAACardAuthClientIfc;

//    final String INSTITUTION_NUMBER = "00000007";

    String refreshToken = null;

    @Override
    public PrepaidProxyPerformTransferRes createRs2Request(PrepaidProxyPerformTransferReq req) {
        String cjNumber = null;
        String aaaSourceAccount = null;
        String aaaDestinationAccount = null;
        PrepaidProxyPerformTransferRes res = new PrepaidProxyPerformTransferRes();

        try {
            if(validatePrpTranferRequest(req)) {
                if (ValidationUtil.validateNumeric(req.getTransferAmount()) && ValidationUtil.validateNumeric(req.getTargetCjNumber())) {


                    String transactionId = req.getTransactionId();
                    String sessionId = req.getSessionId();
                    String accountNumber = "";// should remain empty?;
                    String accountBranchNumber = ""; // should remain empty?;
                    String operationDetails = req.getOperationDetails();
                    String OTP = req.getOtp();
                    String contactId = req.getContactId();
                    String sourceCjNumber = req.getSourceCjNumber();
                    String targetCjNumber = req.getTargetCjNumber();
                    String targetPhoneNumber = req.getTargetPhoneNumber();
                    String transferAmount = req.getTransferAmount();
                    String sourceCurrency = req.getSourceCurrency();
                    String targetCurrency = req.getTargetCurrency();

                    log.debug("Before calling ESB PRPCheckAAACardAuth with req params: "
                            + "sessionId:[" + sessionId + "], transactionId: ["+transactionId+" ],accountNumber:[" + accountNumber + "]  accountBranchNumber:[" + accountBranchNumber + "]  operationDetails:[" + operationDetails + "]  OTP:[" + OTP + "] "
                            + "contactId:[" + contactId + "] sourceCjNumber:[" + sourceCjNumber + "] targetCjNumber:[" + targetCjNumber + "]  targetPhoneNumber:[" + targetPhoneNumber + "]  transferAmount:[" + transferAmount + "] sourceCurrency:[" + sourceCurrency + "] targetCurrency:[" + targetCurrency + "] ");
                    PRPCheckAAACardAuthClientIfc.PRPCheckAAACardAuthResponse aaaRes = prpCheckAAACardAuthClientIfc.execute(
                            operationDetails,
                            OTP,
                            contactId,

                            sessionId,
                            transactionId,

                            sourceCjNumber,
                            targetCjNumber,
                            targetPhoneNumber,
                            transferAmount,
                            sourceCurrency,
                            targetCurrency


                    );

                    log.debug("After calling ESB PRPCheckAAACardAuth. responseCode:" + aaaRes.responseCode + " ,responseDesc:  " + aaaRes.responseDesc);

                    //1. find aaaSourceAccount by cardData.cardAccounts:
                    if (aaaRes != null && aaaRes.responseCode == 0) {
                        if (aaaRes.responseObject != null) {
                            log.debug("looking for cjNumber and source account number from sourceCardData.cardDataArray");
                            PRPCheckAAACardAuthClientIfc.CardData sourceCardDataArray[] = aaaRes.responseObject.sourceCardData.cardsDataArray;
                            if (sourceCardDataArray != null && sourceCardDataArray.length > 0) {
                                //Find aaaSourceAccount
                                for (PRPCheckAAACardAuthClientIfc.CardData cardData : sourceCardDataArray) {
                                    //find CardData by its source CJnumber
                                    if (StringUtils.isEmpty(cjNumber) && cardData.CJNumber.equals(sourceCjNumber)) {
                                        //find aaaSourceAccount by source currency code
                                        for (PRPCheckAAACardAuthClientIfc.CardAccount cardsAccounts : cardData.cardAccountsArray) {
                                            if (StringUtils.isEmpty(aaaSourceAccount) && cardsAccounts.currencyCode.equals(sourceCurrency)) {
                                                aaaSourceAccount = cardsAccounts.accountNumber;
                                            }
                                        }
                                    }
                                }
                                //Find aaaDestinationAccount
                                PRPCheckAAACardAuthClientIfc.CardData targetCardDataArray[] = aaaRes.responseObject.targetCardData.cardsDataArray;
                                for (PRPCheckAAACardAuthClientIfc.CardData cardData : targetCardDataArray) {
                                    //find CardData by its target CJnumber
                                    if (StringUtils.isEmpty(cjNumber) && cardData.CJNumber.equals(targetCjNumber)) {
                                        //find aaaTargetAccount by Target currency code
                                        for (PRPCheckAAACardAuthClientIfc.CardAccount cardsAccounts : cardData.cardAccountsArray) {
                                            if (StringUtils.isEmpty(aaaDestinationAccount) && cardsAccounts.currencyCode.equals(targetCurrency)) {
                                                aaaDestinationAccount = cardsAccounts.accountNumber;
                                            }
                                        }
                                    }
                                }
                            } else {
                                log.error("AAA result CreditCardData is null");
                                res.setErrorMessage("AAA result CreditCardData is null");
                                res.setResponseCode("400");
                                return res;
                            }
                            if (!StringUtils.isEmpty(aaaSourceAccount) && !StringUtils.isEmpty(aaaDestinationAccount)) {
                                PrepaidProxyPerformTransferRs2Req rs2Req = new PrepaidProxyPerformTransferRs2Req();
                                rs2Req.setAmount(req.getTransferAmount());
                                rs2Req.setDestinationAccount(aaaDestinationAccount);
                                rs2Req.setSourceAccount(aaaSourceAccount);
                                rs2Req.setInstitutionNumber(req.getInstitutionNumber());
                                rs2Req.setRetrievalReference(ValidationUtil.stringGenerator());

                                res = PRPTransfer(rs2Req);

                            } else {
                                log.error("  aaaSourceAccount or aaaDestinationAccount are empty or null. aaaSourceAccount : [" + aaaSourceAccount + "] aaaDestinationAccount: [" + aaaDestinationAccount + "]");
                                res.setErrorMessage(" AAA result  - cardNumber or cjNumber empty or null. ");
                                res.setResponseCode("400");
                                return res;
                            }
                        } else {
                            log.error("PRPCheckAAACardAuthClientIfc - responseObject is null");
                            res.setResponseCode("" + aaaRes.responseCode);
                            res.setErrorMessage(aaaRes.responseDesc);


                        }
                        return res;
                    } else {
                        log.error("PRPCheckAAACardAuthClientIfc -response code is not 0: [" + aaaRes.responseCode + "] responseDesc: [" +aaaRes.responseDesc + "]");
                        res.setResponseCode("" + aaaRes.responseCode);
                        res.setErrorMessage(aaaRes.responseDesc);

                    }
                } else {

                    res.setErrorMessage("Input validation failure");
                    res.setResponseCode("400");
                    return res;
                }
            } else {
                res.setResponseCode("100");
                res.setErrorMessage("Input validation failure");
            }
        } catch (Exception e) {

            return fromException(e);
        }
        return res;
    }
    @Override
    public PrepaidProxyPerformTransferRes fromException(Exception e){
        e.printStackTrace();
        log.error(e);
        PrepaidProxyPerformTransferRes res = new PrepaidProxyPerformTransferRes();
        res.setResponseCode("1");
        res.setErrorMessage(e.getMessage());
        return res;
    }

    @Override
    public PrepaidProxySetCardStatusRes PRPSetCardStatus(PrepaidProxySetCardStatusReq req) {



        String token = getToken();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<PrepaidProxySetCardStatusReq> request = new HttpEntity(req, headers);
        RestTemplate rt = new RestTemplate();

        String url = props.getRs2Url() + props.getCardStatusUri();
        log.debug("About to call: " + url + " :" + req.getInstitutionNumber() + " " + req.getClientNumber() + " " + req.getStatus());

        try {
            ResponseEntity<String> PRPSetCardStatusRes = rt.exchange(url, HttpMethod.PUT, request, String.class);
            log.debug("PRPSetCardStatusRes response=  " + PRPSetCardStatusRes);
            String responseString = PRPSetCardStatusRes.getBody();
            log.debug("PRPSetCardStatusRes responseString=  " + responseString);
            String httpCode = PRPSetCardStatusRes.getStatusCode() != null ? PRPSetCardStatusRes.getStatusCode().toString() : "";

            if (!StringUtils.isEmpty(responseString)) {
                return parseResponseBody(responseString, httpCode);
            }

            PrepaidProxySetCardStatusRes okRes = new PrepaidProxySetCardStatusRes();
            okRes.setErrorMessage("OK");
            okRes.setErrorCode(httpCode);
            return okRes;
        } catch (HttpStatusCodeException e) {
            log.error("Got Exception: " + e);
            String responseString = e.getResponseBodyAsString();
            log.error("Error response String: [" +responseString+"]");
            String httpCode = e.getStatusCode() != null ? e.getStatusCode().toString() : null;
            log.error("Error httpCode: [" +httpCode+"]");
            if (!StringUtils.isEmpty(responseString)) {
                try {
                    PrepaidProxySetCardStatusRes knownApplicativeError = parseResponseBody(responseString, httpCode);
                    return knownApplicativeError;
                } catch (IOException io) {//empry
                }
            }
            log.error("Got Exception: " + e);
            PrepaidProxySetCardStatusRes nonapplicative = new PrepaidProxySetCardStatusRes();
            nonapplicative.setErrorMessage(e.getMessage());
            nonapplicative.setErrorCode(httpCode);
            return nonapplicative;

        } catch (IOException e) {
            log.error("Got Exception: " + e);
            PrepaidProxySetCardStatusRes nonapplicative = new PrepaidProxySetCardStatusRes();
            nonapplicative.setErrorMessage(e.getMessage());
            nonapplicative.setErrorCode(e.toString());
            return nonapplicative;
        }
    }


    private PrepaidProxySetCardStatusRes parseResponseBody(String responseString, String httpStatus) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        PrepaidProxySetCardStatusRes knownApplicativeError = mapper.readValue(responseString,
                PrepaidProxySetCardStatusRes.class);
        String errorCode = knownApplicativeError.getErrorCode();
        String errorMessage = knownApplicativeError.getErrorMessage();
        if (!StringUtils.isEmpty(errorCode)) {
            if (errorMessage == null) {
                errorMessage = errorCode;
            } else {
                errorMessage += errorMessage + " " + errorCode;
            }
            knownApplicativeError.setErrorMessage(errorMessage);
        }
        knownApplicativeError.setErrorCode(httpStatus);
        return knownApplicativeError;
    }

    public String getToken() {
        try {

            String accessToken = getRefreshToken();

            if (accessToken != null){
                return accessToken;
            }

            String url = props.getRs2Url() + props.getOauth2TokenUri();
            log.debug("Getting access token without refresh token from: " + url);
            HttpHeaders headers = new HttpHeaders();

            headers.add("Authorization", props.getAuthorization());
//            headers.add("grant_type", props.getGrantType());

            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("grant_type", props.getGrantType());

            log.debug("Headers: Authorization [" +props.getAuthorization() +"] ");
            log.debug("LinkedMultiValueMap form Body: grant_type: " +props.getGrantType() + "] ");
//            String body = "";
//            HttpEntity<String> request = new HttpEntity<String>(body, headers);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
            RestTemplate rt = new RestTemplate();

            String res = rt.postForObject(url, request, String.class);
            log.debug("Response: ["+res+"]");
            ObjectMapper mapper = new ObjectMapper();
            LoginResponse loginResponse = mapper.readValue(res, LoginResponse.class);

            log.debug("expires_in: " + loginResponse.getExpires_in());
            log.debug("New refresh_token:" + loginResponse.getRefresh_token());
            log.debug("Returning access_token:" + loginResponse.getAccess_token());

            refreshToken = loginResponse.getRefresh_token();
            if (loginResponse.getExpires_in().equals("0")){
                loginResponse = retryPost(url, request);
            }
            if (loginResponse.getExpires_in().equals("0")){
                log.error("Expiration time from RS2 is still 0, returning null");
                return null;
            }
            return loginResponse.getAccess_token();
        } catch (Exception e) {
            log.error("Could not get token. " + e);
            return null;
        }
    }

    private String getRefreshToken() {

        if (refreshToken == null){
            return null;
        }
        log.debug("Sending refresh token: ["+refreshToken+"]" );
        String url = props.getRs2Url() + props.getOauth2TokenUri();
        log.debug("Getting refresh Token from: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", props.getAuthorization());

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", props.getRefreshGrantType());
        form.add("refresh_token", refreshToken);

        log.debug("Headers: Authorization [" +props.getAuthorization() +"] ");
        log.debug("LinkedMultiValueMap form Body: grant_type: [" +props.getRefreshGrantType() + "] ");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        RestTemplate rt = new RestTemplate();
        LoginResponse loginResponse = null;

        try {
            String res = rt.postForObject(url, request, String.class);
            log.debug("Response: ["+res+"]");
            ObjectMapper mapper = new ObjectMapper();
            loginResponse = mapper.readValue(res, LoginResponse.class);
        } catch (Exception e) {
            log.error(" Current Refresh token has already been used from outside the app! (Postman/SoupUi)" );
            return null;
        }
        log.debug("New refresh_token: [" + loginResponse.getRefresh_token()+"]");
        log.debug("expires_in: [" + loginResponse.getExpires_in() +"]");
        log.debug("Returning access_token: [" + loginResponse.getAccess_token()+"]");

        refreshToken = loginResponse.getRefresh_token();
        String accessToken =  loginResponse.getAccess_token();
        return accessToken;
    }
    private LoginResponse retryPost(String url, HttpEntity<MultiValueMap<String, String>> request)
            throws Exception{
        log.debug("Token expiration is 0, trying to get a new unexpired token");
        int numberOfTries = 0;
        LoginResponse loginResponse = new LoginResponse();
        if (props.getGetTokenRetryTimes() == null) {
            log.debug("getGetTokenRetryTimes paramter from config file is null. Using default retry value of 3");
            numberOfTries = 3;
        }
        numberOfTries = Integer.valueOf(props.getGetTokenRetryTimes());
        log.debug("getGetTokenRetryTimes paramter from config file is: [" +numberOfTries+ "]");
        for (int i = 1; i < numberOfTries; i++) {
            log.debug("Number of tries [" + i + "] /" + numberOfTries);
            RestTemplate rt = new RestTemplate();
            String res = rt.postForObject(url, request, String.class);
            log.debug("Response: [" + res + "]");
            ObjectMapper mapper = new ObjectMapper();
            loginResponse = mapper.readValue(res, LoginResponse.class);
            log.debug("Current expires_in: " + loginResponse.getExpires_in());
            if (loginResponse.getExpires_in().equals("0")) {
                log.debug("Expiration time is still 0, retrying...");
                continue;
            } else {
                log.debug("Got new expiration time that is not 0, returning valid response.");
                return loginResponse;
            }
        }

        return loginResponse;
    }
    @Override
    public PrepaidProxyGetCardStatusRes PRPGetCardStatus(PrepaidProxyGetCardStatusReq req) {
        String token = getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<PrepaidProxySetCardStatusReq> request = new HttpEntity(req, headers);
        RestTemplate rt = new RestTemplate();

        String url = props.getRs2Url() + props.getCardStatusUri();
        url = UriComponentsBuilder.fromUriString(url)
                .queryParam("institutionNumber", req.getInstitutionNumber())
                .queryParam("clientNumber", req.getClientNumber()).toUriString();
        log.debug("About to call: " + url + " :" + req.getInstitutionNumber() + " " + req.getClientNumber());

        try {
            ResponseEntity<PrepaidProxyGetCardStatusRes> cardsListRes = rt.exchange(url, HttpMethod.GET, request, PrepaidProxyGetCardStatusRes.class);

            log.debug("PRPSetCardStatusRes response=  " + cardsListRes);
            PrepaidProxyGetCardStatusRes okRes = cardsListRes.getBody();
            log.debug("PRPSetCardStatusRes responseString=  " + okRes.getCards());
            String httpCode = cardsListRes.getStatusCode() != null ? cardsListRes.getStatusCode().toString() : "";

            okRes.setErrorMessage("OK");
            okRes.setErrorCode(httpCode);
            return okRes;
        } catch (HttpStatusCodeException e) {
            log.error("Could not call target url. " + e );
            PrepaidProxyGetCardStatusRes httpError = new PrepaidProxyGetCardStatusRes();
            String httpCode = e.getStatusCode() != null ? e.getStatusCode().toString() : null;
            httpError.setErrorCode(httpCode);
            httpError.setErrorMessage(e.getMessage());
            return httpError;
        }
    }

    @Override
    public PrepaidProxyPerformTransferRes PRPTransfer(PrepaidProxyPerformTransferRs2Req rs2Req) {
        String token = getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);

        log.debug("RS2 request from PrepaidProxyPerformTransferReq request: [" +rs2Req+ "]");


        HttpEntity<PrepaidProxyPerformTransferRs2Req> request = new HttpEntity(rs2Req, headers);
        RestTemplate rt = new RestTemplate();
        rt.setErrorHandler(new MyErrorHanler());

        String url = props.getRs2Url() + props.getTransferInternalUri();
//        String url = "http://localhost:8085/destination";

        log.debug("PRPTransfer post to:  [" + url + "] request body: [" + rs2Req + "]");
//            String rs2Res = rt.postForObject(url, request, String.class);
        ResponseEntity<String> exchangeResponse = rt.exchange(url, HttpMethod.POST, request, String.class);
        String responseBody = exchangeResponse.getBody();

        try {
            ObjectMapper mapper = new ObjectMapper();
            PrepaidProxyPerformTransferRes response = mapper.readValue(responseBody, PrepaidProxyPerformTransferRes.class);
            log.debug("PrepaidProxyTransferRes response:  " + response.toString());

            if(RestUtil.isError(exchangeResponse.getStatusCode())){
                log.error("Client or Server Exception");
                PrepaidProxyPerformTransferRes httpError = new PrepaidProxyPerformTransferRes();
                log.error("Original RS2 response:[" +"ResponseCode: [" + response.getResponseCode() +"] ResponseDescription: ["+response.getResponseDescription() + "] ErrorMessage:[" + response.getErrorMessage() + "] AuthCode: [" + response.getAuthCode() + "] errorCode: [" +response.getErrorCode()+"]");

                if(response.getResponseCode() == null && response.getResponseDescription() ==null ) {
                    log.debug("RS2 response Code and ResponseDesc are null - Switcing RS2 errorCode and errorMessage with ResponseCode and responseDescription fields for ESB.");
//                   httpError.setResponseCode(response.getErrorCode());
//                   httpError.setResponseDescription( response.getErrorMessage());
                    httpError.setAuthCode(response.getAuthCode());
                    httpError.setErrorMessage(response.getErrorMessage());
                    httpError.setErrorCode(response.getResponseCode());
                } else {
                    httpError.setResponseCode(response.getResponseCode());
                    httpError.setResponseDescription( response.getResponseDescription());
                    httpError.setAuthCode(response.getAuthCode());
                    httpError.setErrorCode(response.getErrorCode());
                    httpError.setErrorMessage(response.getErrorMessage());

                }

                return httpError;
            } else {

                //handle rs2 response code 000:
                if(Integer.valueOf(response.getResponseCode()) == 0){
                    response.setResponseCode("0");
                }

                return response;

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PRPTransfer generel exception: ", e);
            PrepaidProxyPerformTransferRes response = new PrepaidProxyPerformTransferRes();

            response.setResponseDescription("Exception:  "+e.getMessage());
            response.setResponseCode("500");
            response.setErrorMessage("RS2 error message: [" + response.getErrorMessage() +"]");
            response.setErrorCode("RS2 erroCode: [" + response.getErrorCode() + "]");
            return response;
        }

    }

    @Override
    public boolean validatePrpTranferRequest(PrepaidProxyPerformTransferReq prepaidProxyPerformTransferReq) {
        log.debug("Starting validation for PrepaidProxyPerformTransferReq");
        boolean isValid = true;

        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getContactId())){
            isValid = false;
            log.error("contactId is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getChannelName())){
            isValid = false;
            log.error("ChannelName is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getOperationType())){
            isValid = false;
            log.error("OperationType is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getTransferAmount())){
            isValid = false;
            log.error("TransferAmount is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getOtp())){
            isValid = false;
            log.error("OTP is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getOperationDetails())){
            isValid = false;
            log.error("OperationDetails is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getTargetCurrency())){
            isValid = false;
            log.error("TargetCurrency is null or empty");
            return isValid;
        }

        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getTargetCjNumber())){
            isValid = false;
            log.error("TargetCjNumber is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getTargetPhoneNumber())){
            isValid = false;
            log.error("TargetPhoneNumber is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getSourceCjNumber())){
            isValid = false;
            log.error("SourceCjNumber is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getSourceCurrency())){
            isValid = false;
            log.error("SourceCurrency is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getInstitutionNumber())){
            isValid = false;
            log.error("InstitutionNumber is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getTransactionId())){
            isValid = false;
            log.error("TransactionId is null or empty");
            return isValid;
        }
        if(StringUtils.isEmpty(prepaidProxyPerformTransferReq.getSessionId())){
            isValid = false;
            log.error("sessionId is null or empty");
            return isValid;
        }
        log.debug("Successfully validated all params.");
        return isValid;
    }



    @PostConstruct
    String generateRetrievalReference() {
        String retrievalReference = "" + System.currentTimeMillis();
        int startIndex = retrievalReference.length() - 12;
        retrievalReference = retrievalReference.substring(startIndex);
        log.debug("Retrieval Reference Startup test: " + retrievalReference + ", length: " + retrievalReference.length());
        return retrievalReference;
    }

    @Override
    public PrepaidProxyBalanceDetailsRes PRPGetBalanceDetails(PrepaidProxyBalanceDetailsReq req) {


        String token = getToken();

        req.setRetrievalReference(generateRetrievalReference());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity request = new HttpEntity(headers);

        log.debug("Headers:");
        log.debug("Authorization: " + "Bearer " + token);

        RestTemplate rt = new RestTemplate();
        String url = props.getRs2Url() + props.getBalanceDetailsUri();


        url = UriComponentsBuilder.fromUriString(url)
                .queryParam("institutionNumber", req.getInstitutionNumber())
                .queryParam("clientNumber", req.getClientNumber())
                .queryParam("retrievalReference", req.getRetrievalReference()).toUriString();

        log.debug("url: " + url);

        ResponseEntity<List<BalanceDetail>> rateResponse =
                rt.exchange(url,HttpMethod.GET, request, new ParameterizedTypeReference<List<BalanceDetail>>() {});
        List<BalanceDetail> balanceDetails = rateResponse.getBody();
        PrepaidProxyBalanceDetailsRes rd = new PrepaidProxyBalanceDetailsRes();
        rd.setBalanceDetails(balanceDetails);
        if(!StringUtils.isEmpty(balanceDetails)){
            log.debug("Succsessfully got balanceDetails: " + balanceDetails.toString());
        }
        return rd;
    }

    @Override
    public PrepaidAccountActivityReportRes PRPGetAccountActivityReport(PrepaidProxyAccountActivityReportReq req) {
        String token = getToken();

        req.setRetrievalReference(generateRetrievalReference());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity request = new HttpEntity(headers);

        log.debug("Headers:");
        log.debug("Authorization: " + "Bearer " + token);

        RestTemplate rt = new RestTemplate();
        String url = props.getRs2Url() + props.getAccountsHistoryUri();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("institutionNumber", req.getInstitutionNumber())
                .queryParam("accountNumber", req.getAccountNumber())
                .queryParam("retrievalReference", req.getRetrievalReference());

        if (!StringUtils.isEmpty(req.getRecords())) {
            builder.queryParam("records", req.getRecords());
        } else if (!StringUtils.isEmpty(req.getDays())) {
            builder.queryParam("Days", req.getDays());
        } else if (!StringUtils.isEmpty(req.getDateFrom()) && !StringUtils.isEmpty(req.getDateTo())) {
            builder.queryParam("dateFrom", req.getDateFrom());
            builder.queryParam("dateTo", req.getDateTo());
        }


        url = builder.toUriString();

        log.debug("url: " + url);
        HttpEntity<PrepaidAccountActivityReportRes> res = rt.exchange(url, HttpMethod.GET, request, PrepaidAccountActivityReportRes.class);

        log.debug("PrepaidAccountActivityReportRes: " + res);
        return res.getBody();
    }


}
