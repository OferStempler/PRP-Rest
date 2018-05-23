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
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@Log4j
public class PrePaidProxyController {

    @Autowired
    Rs2Service rs2Service;

    @Autowired
    PrepaidproxySettings props;


    //--------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/prpsetcardstatus", method = RequestMethod.POST, produces = {"application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxySetCardStatusRes PRPSetCardStatus(HttpServletRequest request) {
        log.debug("===============setCardStutus=========START=======");
        log.debug("PRPsetcardstatus in. Building ESB request from params");
        try {
            PrepaidProxySetCardStatusReq prepaidProxyReq = (PrepaidProxySetCardStatusReq) ConrtollerUtil.getEsbParamsFromRequest(request, PrepaidProxySetCardStatusReq.class);
            if (prepaidProxyReq == null) {
                return null;
            }
            log.debug("Got ESB request: [" +prepaidProxyReq.toString()+"]");
            if (ValidationUtil.validateNumeric(prepaidProxyReq.getInstitutionNumber()) && ValidationUtil.validateNumeric(prepaidProxyReq.getClientNumber())) {
                PrepaidProxySetCardStatusRes res = rs2Service.PRPSetCardStatus(prepaidProxyReq);
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============setCardStutus=========END=======");
                return res;

            } else {
                PrepaidProxySetCardStatusRes res = new PrepaidProxySetCardStatusRes();
                res.setErrorCode("Input validation failure");
                res.setErrorCode("400");
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============setCardStutus=========END=======");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            PrepaidProxySetCardStatusRes res = new PrepaidProxySetCardStatusRes();
            res.setErrorCode("1");
            res.setErrorMessage(e.getMessage());
            log.debug("Retunring response to ESB: [" +res.toString()+"]");
            log.debug("===============setCardStutus=========END=======");
            return res;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/prpgetbalancedetails", method = RequestMethod.POST, produces = {"application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxyBalanceDetailsRes PRPGetBalanceDetails(HttpServletRequest request) {
        log.debug("===============balenceDetails=========START=======");
        try {
            PrepaidProxyBalanceDetailsReq prepaidProxyReq = (PrepaidProxyBalanceDetailsReq) ConrtollerUtil.getEsbParamsFromRequest(request, PrepaidProxyBalanceDetailsReq.class);
            if (prepaidProxyReq == null) {
                return null;
            }
            if (ValidationUtil.validateNumeric(prepaidProxyReq.getInstitutionNumber()) && ValidationUtil.validateNumeric(prepaidProxyReq.getClientNumber())) {
                PrepaidProxyBalanceDetailsRes res = rs2Service.PRPGetBalanceDetails(prepaidProxyReq);
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============balenceDetails=========END=======");
                return res;
            } else {
                PrepaidProxyBalanceDetailsRes res = new PrepaidProxyBalanceDetailsRes();
                res.setErrorCode("Input validation failure");
                res.setErrorCode("400");
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============balenceDetails=========END=======");
                return res;
            }

        } catch (Exception e) {
            log.error(e);
            PrepaidProxyBalanceDetailsRes res = new PrepaidProxyBalanceDetailsRes();
            res.setErrorCode("1");
            res.setErrorMessage(e.getMessage());
            log.debug("Retunring response to ESB: [" +res.toString()+"]");
            log.debug("===============balenceDetails=========END=======");
            return res;
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/prpgetaccountactivityreport", method = RequestMethod.POST, produces = {"application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidAccountActivityReportRes PRPGetAccountActivityReport(HttpServletRequest request) {
        log.debug("===============accountActivityReport=========START=======");

        try {
            PrepaidProxyAccountActivityReportReq prepaidProxyReq = (PrepaidProxyAccountActivityReportReq) ConrtollerUtil.getEsbParamsFromRequest(request, PrepaidProxyAccountActivityReportReq.class);
            if (prepaidProxyReq == null) {
                return null;
            }

            if (ValidationUtil.validateNumeric(prepaidProxyReq.getAccountNumber()) && ValidationUtil.validateNumeric(prepaidProxyReq.getInstitutionNumber())) {
                PrepaidAccountActivityReportRes res = rs2Service.PRPGetAccountActivityReport(prepaidProxyReq);
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============accountActivityReport=========END=======");
                return res;

            } else {
                PrepaidAccountActivityReportRes res = new PrepaidAccountActivityReportRes();
                res.setErrorCode("Input validation failure");
                res.setErrorCode("400");
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============accountActivityReport=========END=======");
                return res;
            }
        } catch (Exception e) {
            log.error(e);
            PrepaidAccountActivityReportRes res = new PrepaidAccountActivityReportRes();
            res.setErrorCode("1");
            res.setErrorMessage(e.getMessage());
            log.debug("Retunring response to ESB: [" +res.toString()+"]");
            log.debug("===============accountActivityReport=========END=======");
            return res;
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/prpgetcardstatus", method = RequestMethod.POST, produces = {"application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxyGetCardStatusRes PRPGetCardStatus(HttpServletRequest request) {
        log.debug("===============getCardStatus=========START=======");
        try {
            PrepaidProxyGetCardStatusReq prepaidProxyReq = (PrepaidProxyGetCardStatusReq) ConrtollerUtil.getEsbParamsFromRequest(request, PrepaidProxyGetCardStatusReq.class);
            if (prepaidProxyReq == null) {
                return null;
            }
            if (ValidationUtil.validateNumeric(prepaidProxyReq.getClientNumber()) && ValidationUtil.validateNumeric(prepaidProxyReq.getInstitutionNumber())) {
                PrepaidProxyGetCardStatusRes res = rs2Service.PRPGetCardStatus(prepaidProxyReq);
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============getCardStatus=========END=======");
                return res;
            } else {
                PrepaidProxyGetCardStatusRes res = new PrepaidProxyGetCardStatusRes();
                res.setErrorCode("Input validation failure");
                res.setErrorCode("400");
                log.debug("Retunring response to ESB: [" +res.toString()+"]");
                log.debug("===============getCardStatus=========END=======");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            PrepaidProxyGetCardStatusRes res = new PrepaidProxyGetCardStatusRes();
            res.setErrorCode("1");
            res.setErrorMessage(e.getMessage());
            log.debug("Retunring response to ESB: [" +res.toString()+"]");
            log.debug("===============getCardStatus=========END=======");
            return res;
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/prptransfer", method = RequestMethod.POST, produces = {"application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxyPerformTransferRes PRPTransfer(HttpServletRequest request) {
        log.debug("===============Transfer=========START=======");

        try {
            PrepaidProxyPerformTransferReq req = (PrepaidProxyPerformTransferReq) ConrtollerUtil.getEsbParamsFromRequest(request, PrepaidProxyPerformTransferReq.class);
            if (req == null) {
                return null;
            }
            return PRPTransferPost(req);
        } catch (Exception e) {
            return rs2Service.fromException(e);
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/posttransfer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    PrepaidProxyPerformTransferRes PRPTransferPost(@RequestBody PrepaidProxyPerformTransferReq req) {
        log.debug("===============post-Transfer=========START=======");

        PrepaidProxyPerformTransferRes res = rs2Service.createRs2Request(req);
        log.debug("Returning response from posttransfer to ESB: [" +"ResponseCode: [" + res.getResponseCode() +"] ResponseDescription: ["+res.getResponseDescription() + "] ErrorMessage:[" + res.getErrorMessage() + "] AuthCode: [" + res.getAuthCode() + "] errorCode: [" +res.getErrorCode()+"]");
        log.debug("===============post-Transfer=========END=======");

        return res;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public
    @ResponseBody
    String test() {
        return "OK " + new Date();
    }
}