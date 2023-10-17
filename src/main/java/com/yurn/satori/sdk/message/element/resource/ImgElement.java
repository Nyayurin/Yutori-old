/*
Copyright (c) 2023 Yurn
YurnSatoriSdk is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package com.yurn.satori.sdk.message.element.resource;

import com.yurn.satori.sdk.util.XmlUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 图片
 *
 * @author Yurn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ImgElement extends BaseResourceElement {
    /**
     * 图片的宽度
     */
    protected Long width;

    /**
     * 图片的高度
     */
    protected Long height;

    public ImgElement(String src) {
        this(src, null, null);
    }

    public ImgElement(String src, Long width, Long height) {
        super(src);
        this.width = width;
        this.height = height;
    }

    public ImgElement(String src, Boolean cache, String timeout, Long width, Long height) {
        super(src, cache, timeout);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        String result = "<img";
        if (src != null) {
            result += " src=\"" + XmlUtil.encode(src) + "\"";
        }
        if (cache != null) {
            result += " cache";
        }
        if (timeout != null) {
            result += " timeout=\"" + timeout + "\"";
        }
        if (width != null) {
            result += " width=" + width;
        }
        if (height != null) {
            result += " height=" + height;
        }
        result += "/>";
        return result;
    }
}
