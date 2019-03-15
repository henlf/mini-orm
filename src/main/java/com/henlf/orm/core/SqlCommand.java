package com.henlf.orm.core;

import com.henlf.orm.constants.SqlType;

public class SqlCommand {
    private String statementId;
    private SqlType sqlType;
    private String sql;

    public SqlCommand(String statementId, SqlType sqlType, String sql) {
        this.statementId = statementId;
        this.sqlType = sqlType;
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }
}
