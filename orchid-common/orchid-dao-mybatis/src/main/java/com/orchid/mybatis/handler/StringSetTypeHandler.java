package com.orchid.mybatis.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class StringSetTypeHandler extends BaseTypeHandler<Set<String>> {

    private final static char SEPARATOR = ',';
    private final static String SEPARATOR_STR = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, CollUtil.join(parameter, SEPARATOR_STR));
    }

    @Override
    public Set<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return CollUtil.newHashSet(StrUtil.split(rs.getString(columnName), SEPARATOR));
    }

    @Override
    public Set<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return CollUtil.newHashSet(StrUtil.split(rs.getString(columnIndex), SEPARATOR));
    }

    @Override
    public Set<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CollUtil.newHashSet(StrUtil.split(cs.getString(columnIndex), SEPARATOR));
    }
}
