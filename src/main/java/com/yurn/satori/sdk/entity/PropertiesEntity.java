package com.yurn.satori.sdk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yurn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesEntity {
    /**
     * SDK 的地址
     */
    private String address;

    /**
     * Token
     */
    private String token;
}
