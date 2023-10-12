package com.yurn.satori.sdk.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yurn
 */
@Data
@Component
@ConfigurationProperties("yurn-sdk")
public class YurnSdkProperties {
    /**
     * SDK的地址
     */
    @Value("${yurn-sdk.address:127.0.0.1:5500}")
    private String address;
}
