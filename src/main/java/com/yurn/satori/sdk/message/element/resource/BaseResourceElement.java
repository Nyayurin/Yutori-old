package com.yurn.satori.sdk.message.element.resource;

import com.yurn.satori.sdk.message.element.BaseMessageElement;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class BaseResourceElement extends BaseMessageElement {
    /**
     * 资源的 URL
     */
    protected String src;

    /**
     * 是否使用已缓存的文件
     */
    protected Boolean cache;

    /**
     * 下载文件的最长时间 (毫秒)
     */
    protected String timeout;

    public BaseResourceElement(@NonNull String src) {
        this(src, null, null);
    }

    public BaseResourceElement(@NonNull String src, @Nullable Boolean cache, @Nullable String timeout) {
        this.src = src;
        this.cache = cache;
        this.timeout = timeout;
    }
}
