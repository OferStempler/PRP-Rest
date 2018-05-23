package il.co.prepaidproxy.model;

import lombok.Data;
import lombok.ToString;

/**
 * Created by ofer on 07/12/17.
 */
@Data
@ToString
public class PrepaidProxyPerformTransferRs2Req {

    String sourceAccount;
    String amount;
    String destinationAccount;
    String retrievalReference;
    String institutionNumber;



}
