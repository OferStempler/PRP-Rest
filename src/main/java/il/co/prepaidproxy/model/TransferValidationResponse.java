package il.co.prepaidproxy.model;

import lombok.Data;

/**
 * Created by ofer on 17/12/17.
 */
@Data
public class TransferValidationResponse {

    private boolean validated;
    private String validationError;


}
