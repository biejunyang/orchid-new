package com.orchid.core.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

public class AssertUtils {


    public static void notNull(Object obj, String label) {
        Assert.notNull(obj, "{}不能为空", label);
    }

    public static void notBlank(Object obj, String label, boolean nullable) {
        if (nullable && null == obj) {
            return;
        }
        AssertUtils.notNull(obj, label);
        boolean isBlank = false;
        if (obj instanceof CharSequence) {
            isBlank = StrUtil.isBlank((CharSequence)obj);
        }
        Assert.isFalse(isBlank, "{}不能为空白字符", label);
    }

    public static void notBlank(Object obj, String label) {
        AssertUtils.notBlank(obj, label, false);
    }

}
