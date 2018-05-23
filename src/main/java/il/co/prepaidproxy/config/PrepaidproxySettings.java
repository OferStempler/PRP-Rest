package il.co.prepaidproxy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "prepaidproxy")
@Data
public class PrepaidproxySettings {
    String grantType;
    String authorization;
    String rs2Url;
    boolean enableTest;
    String PRPCheckAAACardAuthClient;
    String esbServerIP;
    String esbServerPort;
    String esbProtocol;

    String cardStatusUri;
    String oauth2TokenUri;
    String TransferInternalUri;
    String BalanceDetailsUri;
    String AccountsHistoryUri;
    String GetTokenRetryTimes;
    String refreshGrantType;
}
