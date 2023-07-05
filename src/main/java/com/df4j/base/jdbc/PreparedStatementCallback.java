package com.df4j.base.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementCallback<T> {
    T onCallback(PreparedStatement ps) throws SQLException;
}
