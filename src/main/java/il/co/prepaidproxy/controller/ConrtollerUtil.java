package il.co.prepaidproxy.controller;

import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tomerpaz on 5/30/17.
 */
@Log4j
public class ConrtollerUtil {
    public static Object getEsbParamsFromRequest(HttpServletRequest request, Class clazz) {
        boolean errFlag = false;
        Exception exp = null;
        Object inputParams = null;
        try {

            inputParams = (Object) unmarshall(clazz, request.getInputStream());
            log.debug(clazz.getSimpleName() + ".getEsbParamsFromRequest() - AFTER parsing");
        } catch (JAXBException e) {
            log.error(clazz.getSimpleName() +".getEsbParamsFromRequest() - Got JAXBException", e);
            errFlag = true;
        } catch (IOException e) {
            log.error(clazz.getSimpleName() +".getEsbParamsFromRequest() - Got JAXBException", e);
            exp = e;
            errFlag = true;
        }
        if (errFlag == true) {
            log.error(clazz.getSimpleName() +".getEsbParamsFromRequest() - FAILED trying to parse ESB input. got[" + exp.getClass().getSimpleName() +"]", exp);
            return null;
        } else {
            log.debug(clazz.getSimpleName() +".getEsbParamsFromRequest() - returning inputParams=[" + inputParams + "]");
            return inputParams;
        }
    }


    public static Object unmarshall(Class<?> clazz, InputStream stream) throws JAXBException {
        Object object = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
        object = jaxbUnMarshaller.unmarshal(stream);
        return object;
    }
}
