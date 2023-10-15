package com.yurn.satori.sdk.message.element.meta;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 作者
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AuthorElement extends BaseMessageElement {
    /**
     * 用户 ID
     */
    protected String userId;

    /**
     * 昵称
     */
    protected String nickname;

    /**
     * 头像 URL
     */
    protected String avatar;

    public AuthorElement( String userId,  String nickname,  String avatar) {
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("author");
        if (userId != null) {
            element.addAttribute("user-id", userId);
        }
        if (nickname != null) {
            element.addAttribute("nickname", nickname);
        }
        if (avatar != null) {
            element.addAttribute("avatar", avatar);
        }
        return element.asXML();
    }
}
