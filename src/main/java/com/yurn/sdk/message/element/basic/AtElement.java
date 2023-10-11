package com.yurn.sdk.message.element.basic;

import com.yurn.sdk.message.element.BaseMessageElement;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 提及用户
 *
 * @author Yurn
 */
public class AtElement extends BaseMessageElement {
    /**
     * 目标用户的 ID
     */
    private final String id;
    /**
     * 目标用户的名称
     */
    private final String name;
    /**
     * 目标角色
     */
    private final String role;
    /**
     * 特殊操作, 例如 all 表示 @全体成员, here 表示 @在线成员
     */
    private final String type;

    public AtElement(@Nullable String id,
                     @Nullable String name,
                     @Nullable String role,
                     @Nullable String type) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.type = type;
    }

    public static AtElement atUserId(@NonNull String id) {
        return new AtElement(id, null, null, null);
    }

    public static AtElement atUserName(@NonNull String name) {
        return new AtElement(null, name, null, null);
    }

    public static AtElement atUserRole(@NonNull String role) {
        return new AtElement(null, null, role, null);
    }

    public static AtElement atUserType(@NonNull String type) {
        return new AtElement(null, null, null, type);
    }

    @Override
    public String toString() {
        String str = "<at";
        if (id != null) {
            str += " id=&quot;" + id + "&quot;";
        }
        if (name != null) {
            str += " name=&quot;" + name + "&quot;";
        }
        if (role != null) {
            str += " role=&quot;" + role + "&quot;";
        }
        if (type != null) {
            str += " type=&quot;" + type + "&quot;";
        }
        str += "/>";
        return str;
    }
}
