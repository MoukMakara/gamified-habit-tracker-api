package org.hrd.kps_group_01_spring_mini_project.beanConfig;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;


@MappedTypes(UUID.class)
@Component
public class UUIDTypeHandler extends BaseTypeHandler<UUID> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName, UUID.class);
    }

    @Override
    public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getObject(columnIndex, UUID.class);
    }

    @Override
    public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getObject(columnIndex, UUID.class);
    }
}


//public class UUIDTypeHandler extends BaseTypeHandler<UUID> {
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
//        ps.setObject(i, parameter.toString(), jdbcType.TYPE_CODE); // Store UUID as string
//    }
//
//    @Override
//    public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        String uuidStr = rs.getString(columnName);
//        return uuidStr != null ? UUID.fromString(uuidStr) : null;
//    }
//
//    @Override
//    public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        String uuidStr = rs.getString(columnIndex);
//        return uuidStr != null ? UUID.fromString(uuidStr) : null;
//    }
//
//    @Override
//    public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        String uuidStr = cs.getString(columnIndex);
//        return uuidStr != null ? UUID.fromString(uuidStr) : null;
//    }
//}