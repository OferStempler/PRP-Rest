package il.co.prepaidproxy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    String access_token;
    String refresh_token;
    String expires_in;
}
