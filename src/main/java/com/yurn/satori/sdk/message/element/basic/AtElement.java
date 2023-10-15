package com.yurn.satori.sdk.message.element.basic;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import com.yurn.satori.sdk.util.XmlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    public AtElement(String id, String name, String role, String type) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.type = type;
    }

    @Override
    public String toString() {
        String result = "<at";
        if (id != null) {
            result += " id=\"" + id + "\"";
        }
        if (name != null) {
            result += " name=\"" + XmlUtil.encode(name) + "\"";
        }
        if (role != null) {
            result += " role=\"" + XmlUtil.encode(role) + "\"";
        }
        if (type != null) {
            result += " type=\"" + type + "\"";
        }
        result += "/>";
        return result;
    }
}
