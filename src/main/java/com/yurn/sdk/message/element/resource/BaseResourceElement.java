package com.yurn.sdk.message.element.resource;

import com.yurn.sdk.message.element.BaseMessageElement;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author Yurn
 */
public abstract class BaseResourceElement extends BaseMessageElement {
    /**
     * 资源的 URL
     */
    protected final String src;

    /**
     * 是否使用已缓存的文件
     */
    protected final Boolean cache;

    /**
     * 下载文件的最长时间 (毫秒)
     */
    protected final String timeout;

    public BaseResourceElement(@NonNull String src,
                               @Nullable Boolean cache,
                               @Nullable String timeout) {
        this.src = src;
        this.cache = cache;
        this.timeout = timeout;
    }

    public BaseResourceElement(@NonNull String src) {
        this(src, null, null);
    }

    @Override
    public String toXmlString() {
        String str = " src=&quot;" + src + "&quot;";
        if (cache != null) {
            str += " cache=&quot;" + cache + "&quot;";
        }
        if (timeout != null) {
            str += " timeout=&quot;" + timeout + "&quot;";
        }
        return str;
    }
}
