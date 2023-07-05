package com.df4j.base.jdbc;

import java.sql.SQLException;
import java.sql.Statement;


@FunctionalInterface
public interface StatementCallback<T> {
    T onCallback(Statement statement, String sql) throws SQLException;
}
