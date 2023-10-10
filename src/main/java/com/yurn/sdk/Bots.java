package com.yurn.sdk;

import com.yurn.sdk.entity.Login;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Yurn
 */
@Data
@Component
public class Bots {
    private Login[] logins;
}
