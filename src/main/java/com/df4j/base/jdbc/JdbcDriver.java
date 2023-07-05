package com.df4j.base.jdbc;

import com.df4j.base.exception.BaseException;

/**
 * 收集各类型数据的驱动类名称
 */
public class JdbcDriver {
    // mysql
    public static final String MYSQL = "com.mysql.jdbc.Driver";
    // mysql6+
    public static final String MYSQL_CJ = "com.mysql.cj.jdbc.Driver";
    // oracle
    public static final String ORACLE = "oracle.jdbc.OracleDriver";
    // h2
    public static final String H2 = "org.h2.Driver";
    // postgresql
    public static final String POSTGRESQL = "org.postgresql.Driver";
    // sqlite
    public static final String SQLITE = "org.sqlite.JDBC";
    // mssql
    public static final String MSSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // hive
    public static final String HIVE = "org.apache.hive.jdbc.HiveDriver";
    // db2
    public static final String DB2 = "com.ibm.db2.jcc.DB2Driver";
    // `MARIADB`
    public static final String MARIADB = "org.mariadb.jdbc.Driver";


    public void forName(String driverName) {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new BaseException("load jdbc driver fail. driverClass: " + driverName, e);
        }
    }
}
