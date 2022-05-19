package com.utopian.weather.persistence;

import java.sql.Types;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

public class CustomPostgreSQLDialect extends PostgreSQL10Dialect {

    public CustomPostgreSQLDialect() {
        super();

        registerColumnType(Types.BLOB, "bytea");
        registerColumnType(Types.JAVA_OBJECT, "json");
        registerColumnType(Types.JAVA_OBJECT, "jsonb");
        registerFunction("ilike",
                new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "?1 ilike ?2"));
    }

    @Override
    public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        if (sqlTypeDescriptor.getSqlType() == Types.BLOB) {
            return BinaryTypeDescriptor.INSTANCE;
        }

        return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
    }
}
