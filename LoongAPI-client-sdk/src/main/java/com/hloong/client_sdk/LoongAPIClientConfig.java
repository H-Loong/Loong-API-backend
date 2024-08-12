package com.hloong.client_sdk;

import com.hloong.client_sdk.client.LoongAPIClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("loong-api.client")
@Data
@ComponentScan
public class LoongAPIClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public LoongAPIClient loongAPIClient(){
        return new LoongAPIClient(accessKey,secretKey);
    }
}
