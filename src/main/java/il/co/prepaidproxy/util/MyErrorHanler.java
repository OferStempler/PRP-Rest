package il.co.prepaidproxy.util;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import lombok.extern.log4j.Log4j;
@Log4j
public class MyErrorHanler implements ResponseErrorHandler  {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        log.error("MyErrorHanler: ResponseStatusCode: [" +  response.getStatusCode() +"] ResponseStatusText: [" + response.getStatusText() +" ]");

    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }




}
