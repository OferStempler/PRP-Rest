package il.co.prepaidproxy.controller;
import il.co.prepaidproxy.config.PrepaidproxySettings;
import il.co.prepaidproxy.model.*;
import il.co.prepaidproxy.service.Rs2Service;
import il.co.prepaidproxy.util.ValidationUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by tomerpaz on 6/19/17.
 */


@Controller
@Log4j
public class TestController {


    @Autowired
    Rs2Service rs2Service;

    @Autowired
    PrepaidproxySettings props;
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/setcardstatus/{institutionNumber}/{clientNumber}/{status}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxySetCardStatusRes PRPSetCardStatus(
            @PathVariable(value = "institutionNumber") String institutionNumber,
            @PathVariable(value = "clientNumber") String clientNumber,
            @PathVariable(value = "status") String status
    ) {
        if(props.isEnableTest()) {
            log.debug("====TEST-Controller=====setCardStutus=========START=======");
            PrepaidProxySetCardStatusReq prepaidProxyReq = new PrepaidProxySetCardStatusReq();
            prepaidProxyReq.setClientNumber(clientNumber);
            prepaidProxyReq.setInstitutionNumber(institutionNumber);
            prepaidProxyReq.setStatus(status);
            PrepaidProxySetCardStatusRes res = rs2Service.PRPSetCardStatus(prepaidProxyReq);
            log.debug("Returning response: [" +res+"]");
            log.debug("====TEST-Controller=====setCardStutus=========END=======");
            return res;
        }
        else{
            PrepaidProxySetCardStatusRes res = new PrepaidProxySetCardStatusRes();
            res.setErrorMessage("Tests not enabled");
            log.debug("Returning response: [" +res+"]");
            log.debug("====TEST-Controller=====setCardStutus=========END=======");
            return res;
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/getcardstatus/{institutionNumber}/{clientNumber}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxyGetCardStatusRes PRPGetCardStatus(
            @PathVariable(value = "institutionNumber") String institutionNumber,
            @PathVariable(value = "clientNumber") String clientNumber
    ) {
        if(props.isEnableTest()) {
            log.debug("====TEST-Controller=====getCardStutus=========START=======");
            PrepaidProxyGetCardStatusReq prepaidProxyReq = new PrepaidProxyGetCardStatusReq();
            prepaidProxyReq.setClientNumber(clientNumber);
            prepaidProxyReq.setInstitutionNumber(institutionNumber);
            PrepaidProxyGetCardStatusRes res = rs2Service.PRPGetCardStatus(prepaidProxyReq);
            log.debug("Returning response: [" +res+"]");
            log.debug("====TEST-Controller=====getCardStutus=========END=======");
            return res;
        }
        else{
            PrepaidProxyGetCardStatusRes res = new PrepaidProxyGetCardStatusRes();
            res.setErrorMessage("Tests not enabled");
            log.debug("Returning response: [" +res+"]");
            log.debug("====TEST-Controller=====getCardStutus=========END=======");
            return res;
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/transfer", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxyPerformTransferRes prpTransfer() {
        if(props.isEnableTest()) {
            log.debug("====TEST-Controller=====Transfer=========START=======");
            PrepaidProxyPerformTransferRs2Req rs2Req= new PrepaidProxyPerformTransferRs2Req();
            rs2Req.setAmount("50");
            rs2Req.setDestinationAccount("10000135002");
            rs2Req.setSourceAccount("10000136002");
            rs2Req.setInstitutionNumber("00000007");
            rs2Req.setRetrievalReference(ValidationUtil.stringGenerator());

            PrepaidProxyPerformTransferRes res = rs2Service.PRPTransfer(rs2Req);
            log.debug("Returning response from posttransfer TestController: [" +"ResponseCode: [" + res.getResponseCode() +"] ResponseDescription: ["+res.getResponseDescription() + "] ErrorMessage:[" + res.getErrorMessage() + "] AuthCode: [" + res.getAuthCode() + "] errorCode: [" +res.getErrorCode()+"]");
            log.debug("====TEST-Controller=====Transfer=========END=======");
            return res;
        }
        else{
            PrepaidProxyPerformTransferRes res = new PrepaidProxyPerformTransferRes();
            res.setErrorMessage("Tests not enabled");
            log.debug("Returning response: [" +res+"]");
            log.debug("====TEST-Controller=====Transfer=========END=======");
            return res;
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping (method = RequestMethod.POST , value="/testToken")
    public @ResponseBody String testToken () throws IOException{
        String loginResponse = "test in not enabled";
        if(props.isEnableTest()) {
            loginResponse = rs2Service.getToken();
        }
        return loginResponse;
    }
}