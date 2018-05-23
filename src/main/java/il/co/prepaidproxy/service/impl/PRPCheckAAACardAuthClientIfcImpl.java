package il.co.prepaidproxy.service.impl;

import il.co.prepaidproxy.config.PrepaidproxySettings;
import com.main.PRPCheckAAACardAuthClientIfc;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;

@Service
@Log4j
public class PRPCheckAAACardAuthClientIfcImpl implements PRPCheckAAACardAuthClientIfc {

    PRPCheckAAACardAuthClientIfc service  = null;

    @Autowired
    PrepaidproxySettings  prepaidproxySettings;

    @PostConstruct
    private void init(){

        log.info("init PRPCheckAAACardAuthClientIfcImpl");
        log.info("params:");
        log.info("PRPCheckAAACardAuthClient:" +prepaidproxySettings.getPRPCheckAAACardAuthClient());
        log.info("EsbServerIP:" +prepaidproxySettings.getEsbServerIP());
        log.info("EsbServerPort:" +prepaidproxySettings.getEsbServerPort());

        if(!StringUtils.isEmpty(prepaidproxySettings.getPRPCheckAAACardAuthClient())
                && !StringUtils.isEmpty(prepaidproxySettings.getEsbServerIP())
                && !StringUtils.isEmpty(prepaidproxySettings.getEsbServerPort())){
            try {
                log.info("init with reflection:");
                Class serviceClass = Class.forName(prepaidproxySettings.getPRPCheckAAACardAuthClient());
                Constructor constructor = serviceClass.getConstructor(new Class[]{String.class,String.class,String.class});
                service = (PRPCheckAAACardAuthClientIfc)constructor.newInstance(prepaidproxySettings.getEsbProtocol(),prepaidproxySettings.getEsbServerIP(),prepaidproxySettings.getEsbServerPort());
                log.info("init OK:");
            }catch (Exception e){
                log.error(e.getMessage(),e);
                log.error("init PRPCheckAAACardAuthClientIfcImpl failed");
                service = null;
            }
        }
    }

    @Override
    public PRPCheckAAACardAuthResponse execute(String sessionId, String transactionId, String operationDetails, String OTP, String contactId, String sourceCjNumber, String targetCjNumber, String targetPhoneNumber, String transferAmount, String sourceCurrency, String targetCurrency) {
        if(service != null) {

            return service.execute(
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
                    targetCurrency);
        }
        else{
            PRPCheckAAACardAuthResponse res = new PRPCheckAAACardAuthResponse();
            res.responseCode = 500;
            res.responseDesc = "PRPCheckAAACardAuthClientIfc failed to init, look at application.yml - PRPCheckAAACardAuthClient param";
            return res;
        }
    }
}
