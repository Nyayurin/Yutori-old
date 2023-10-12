package com.yurn.satori.sdk.message.element.meta;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import org.springframework.lang.Nullable;

/**
 * 作者
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public class AuthorElement extends BaseMessageElement {
    /**
     * 用户 ID
     */
    private final String userId;
    /**
     * 昵称
     */
    private final String nickname;
    /**
     * 头像 URL
     */
    private final String avatar;

    public AuthorElement(@Nullable String userId,
                         @Nullable String nickname,
                         @Nullable String avatar) {
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        String str = "<author";
        if (userId != null) {
            str += " user-id=&quot;" + userId + "&quot;";
        }
        if (nickname != null) {
            str += " nickname=&quot;" + nickname + "&quot;";
        }
        if (avatar != null) {
            str += " avatar=&quot;" + avatar + "&quot;";
        }
        str += "/>";
        return str;
    }
}
