package com.yurn.satori.sdk;

import com.yurn.satori.sdk.entity.LoginEntity;
import lombok.Data;
import lombok.Getter;

/**
 * @author Yurn
 */
@Data
public final class BotContainer {
    @Getter
    private static BotContainer INSTANCE = new BotContainer();

    private LoginEntity[] logins;

    private BotContainer() {}
}
