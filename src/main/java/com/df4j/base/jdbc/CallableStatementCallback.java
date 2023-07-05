package com.df4j.base.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface CallableStatementCallback<T> {
    T onCallback(CallableStatement cs) throws SQLException;
}
