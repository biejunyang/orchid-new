package com.orchid.mybatis.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.orchid.core.ResultCodeEnum;
import com.orchid.core.exception.BaseException;

public class AssertUtils extends com.orchid.core.util.AssertUtils {
    public static <T, C> C columnNotUsed(BaseMapper<T> baseMapper, T entity, String label, SFunction<T, C> function, boolean nullable, T oldEntity) {
        C value = function.apply(entity);
        if (nullable && null == value) {
            return null;
        }
        AssertUtils.notBlank(value, label);
        if (null != oldEntity && value.equals(function.apply(oldEntity))) {
            return null;
        }
        int count = baseMapper.selectCount(Wrappers.<T>lambdaQuery()//
            .eq(function, value));
        if (count > 0) {
            throw new BaseException(ResultCodeEnum.BUSINESS_PARAM_ERROR.code(), label + value + "已存在");
        }
        return value;
    }

    public static <T, C> C columnNotUsed(BaseMapper<T> baseMapper, T entity, String label, SFunction<T, C> function, T oldEntity) {
        return AssertUtils.columnNotUsed(baseMapper, entity, label, function, true, oldEntity);
    }

    public static <T, C> C columnNotUsed(BaseMapper<T> baseMapper, T entity, String label, SFunction<T, C> function, boolean nullable) {
        return AssertUtils.columnNotUsed(baseMapper, entity, label, function, nullable, null);
    }

    public static <T, C> C columnNotUsed(BaseMapper<T> baseMapper, T entity, String label, SFunction<T, C> function) {
        return AssertUtils.columnNotUsed(baseMapper, entity, label, function, true, null);
    }

    public static <T, C> C columnEffective(BaseMapper<C> baseMapper, T entity, String label, SFunction<T, C> function, boolean nullable) {
        C value = function.apply(entity);

        if (nullable && null == value) {
            return null;
        }
        AssertUtils.notBlank(value, label);
        int count = baseMapper.selectCount(Wrappers.<C>lambdaQuery(value));
        if (count < 1) {
            throw new BaseException(ResultCodeEnum.BUSINESS_PARAM_ERROR.code(),label + "不存在");
        }
        return value;
    }

    public static <T, C> C columnEffective(BaseMapper<C> baseMapper, T entity, String label, SFunction<T, C> function) {
        return AssertUtils.columnEffective(baseMapper, entity, label, function, true);
    }


}


