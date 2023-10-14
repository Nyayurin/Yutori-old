package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.lang.Nullable;

/**
 * 提及用户
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AtElement extends BaseMessageElement {
    /**
     * 目标用户的 ID
     */
    protected String id;

    /**
     * 目标用户的名称
     */
    protected String name;

    /**
     * 目标角色
     */
    protected String role;

    /**
     * 特殊操作, 例如 all 表示 @全体成员, here 表示 @在线成员
     */
    protected String type;

    public AtElement(@Nullable String id, @Nullable String name, @Nullable String role, @Nullable String type) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.type = type;
    }

    @Override
    public String toString() {
        Element element = DocumentHelper.createElement("at");
        if (id != null) {
            element.addAttribute("id", id);
        }
        if (name != null) {
            element.addAttribute("name", name);
        }
        if (role != null) {
            element.addAttribute("role", role);
        }
        if (type != null) {
            element.addAttribute("type", type);
        }
        return element.asXML();
    }
}
