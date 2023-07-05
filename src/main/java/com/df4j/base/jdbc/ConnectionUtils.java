package com.df4j.base.jdbc;

import com.df4j.base.exception.BaseException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection getConnection(String url, String user, String pass) {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new BaseException("get jdbc connection fail. url: " + url, e);
        }
    }

    public static Connection getConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new BaseException("get jdbc connection from datasource fail", e);
        }
    }


    public static void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new BaseException("close connection fail.", e);
        }
    }

    public static void commit(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new BaseException("commit connection fail.", e);
        }
    }

    public static void rollback(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new BaseException("rollback connection fail.", e);
        }
    }

    public static void setAutoCommit(Connection connection, boolean autoCommit) {
        if (connection == null) {
            return;
        }
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new BaseException("setAutoCommit connection fail.", e);
        }
    }

    public static void setSavepoint(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.setSavepoint();
        } catch (SQLException e) {
            throw new BaseException("setSavepoint connection fail.", e);
        }
    }

    public static void setSavepoint(Connection connection, String name) {
        if (connection == null) {
            return;
        }
        try {
            connection.setSavepoint(name);
        } catch (SQLException e) {
            throw new BaseException("setSavepoint connection fail.", e);
        }
    }
}
