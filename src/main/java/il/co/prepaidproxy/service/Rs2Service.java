package il.co.prepaidproxy.service;

import il.co.prepaidproxy.model.*;

import java.io.IOException;

public interface Rs2Service {

    PrepaidProxySetCardStatusRes PRPSetCardStatus(PrepaidProxySetCardStatusReq req);

    PrepaidProxyBalanceDetailsRes PRPGetBalanceDetails(PrepaidProxyBalanceDetailsReq req);

    PrepaidAccountActivityReportRes PRPGetAccountActivityReport(PrepaidProxyAccountActivityReportReq req);

    String getToken() throws IOException;

    PrepaidProxyGetCardStatusRes PRPGetCardStatus(PrepaidProxyGetCardStatusReq prepaidProxyReq);

    PrepaidProxyPerformTransferRes PRPTransfer(PrepaidProxyPerformTransferRs2Req prepaidProxyReq);

    boolean validatePrpTranferRequest(PrepaidProxyPerformTransferReq prepaidProxyPerformTransferReq);

    PrepaidProxyPerformTransferRes createRs2Request(PrepaidProxyPerformTransferReq req);

    PrepaidProxyPerformTransferRes fromException(Exception e);

}
