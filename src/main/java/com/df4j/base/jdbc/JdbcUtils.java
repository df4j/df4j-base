package com.df4j.base.jdbc;

import com.df4j.base.exception.BaseException;
import com.df4j.base.util.CloseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class JdbcUtils {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);


    // executeStatement

    public static <R> R executeStatement(Connection connection, String sql, StatementCallback<R> callback) {
        try (Statement statement = connection.createStatement()) {
            return callback.onCallback(statement, sql);
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }

    public static ResultSet executeQuery(Connection connection, String sql) {
        return executeStatement(connection, sql, (statement, s) -> statement.executeQuery(s));

    }

    public static int executeUpdate(Connection connection, String sql) {
        return executeStatement(connection, sql, (stmt, s) -> stmt.executeUpdate(s));
    }

    public static Boolean execute(Connection connection, String sql) {
        return executeStatement(connection, sql, (stmt, s) -> stmt.execute(s));
    }

    // executePreparedStatement
    public static <R> R executePreparedStatement(Connection connection, String sql, Object[] args, PreparedStatementCallback<R> callback) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            return callback.onCallback(ps);
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }

    public static ResultSet executeQuery(Connection connection, String sql, Object[] args) {
        return executePreparedStatement(connection, sql, args, ps -> ps.executeQuery());
    }

    public static int executeUpdate(Connection connection, String sql, Object[] args) {
        return executePreparedStatement(connection, sql, args, ps -> ps.executeUpdate());
    }

    public static Boolean execute(Connection connection, String sql, Object[] args) {
        return executePreparedStatement(connection, sql, args, ps -> ps.execute());
    }

    // executeCall
    public static <R> R executeCall(Connection connection, String sql, Object[] args, CallableStatementCallback<R> callback) {
        try (CallableStatement cs = connection.prepareCall(sql);) {
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    cs.setObject(i + 1, args[i]);
                }
            }
            return callback.onCallback(cs);
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }

    public static ResultSet executeCallQuery(Connection connection, String sql, Object[] args) {
        return executeCall(connection, sql, args, cs -> cs.executeQuery());
    }

    public static int executeCallUpdate(Connection connection, String sql, Object[] args) {
        return executeCall(connection, sql, args, cs -> cs.executeUpdate());
    }

    public static Boolean executeCall(Connection connection, String sql, Object[] args) {
        return executeCall(connection, sql, args, cs -> cs.execute());
    }

    public static int insert(Connection connection, String tableName, Map<String, Object> data) {
        List args = new ArrayList();
        StringBuffer columns = new StringBuffer();
        StringBuffer values = new StringBuffer();
        boolean first = true;
        if (data != null) {
            for (String key : data.keySet()) {
                if (first) {
                    first = false;
                } else {
                    columns.append(",");
                    values.append(",");
                }
                columns.append(key);
                values.append("?");
                args.add(data.get(key));
            }
        }
        String sql = String.format("insert into %s(%s) values (%s)", tableName, columns.toString(), values.toString());
        return executeUpdate(connection, sql, args.toArray());
    }

    public static int update(Connection connection, String tableName, Map<String, Object> data, String[] keyNames, Object[] keyValues) {
        List args = new ArrayList();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update ");
        stringBuffer.append(tableName);
        stringBuffer.append(" set ");
        boolean first = true;
        if (data != null) {
            for (String key : data.keySet()) {
                if (first) {
                    first = false;
                } else {
                    stringBuffer.append(",");
                }
                stringBuffer.append(key);
                stringBuffer.append("=");
                stringBuffer.append("?");
                args.add(data.get(key));
            }
        }
        for (int i = 0; i < keyNames.length; i++) {
            if (i == 0) {
                stringBuffer.append(" where ");
            } else {
                stringBuffer.append(" and ");
            }
            stringBuffer.append(keyNames[i]);
            stringBuffer.append("=");
            stringBuffer.append("?");
            args.add(keyValues[i]);
        }
        return executeUpdate(connection, stringBuffer.toString(), args.toArray());
    }

    public static String[] getResultKeys(ResultSet rs) {
        try {
            ResultSetMetaData md = rs.getMetaData();
            int count = md.getColumnCount();
            String[] keys = new String[count];
            if (count > 0) {
                // 解析出包含的列
                for (int i = 1; i <= count; i++) {
                    keys[i - 1] = md.getColumnName(i);
                }
            }
            return keys;
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }

    /**
     * 在一个连接中执行多个sql
     *
     * @param dataSource
     * @param ignoreException
     * @param consumers
     */
    public static void execute(DataSource dataSource, boolean ignoreException, Consumer<Connection>... consumers) {
        if (consumers == null || consumers.length == 0) {
            return;
        }
        Connection connection = null;
        try {
            connection = ConnectionUtils.getConnection(dataSource);
            connection.setAutoCommit(false);
            for (int i = 0; i < consumers.length; i++) {
                try {
                    consumers[i].accept(connection);
                } catch (Exception e) {
                    if (ignoreException) {
                        logger.warn("batch execute Jdbc operation occur exception, ignore the exception, index: " + i, e);
                    } else {
                        throw BaseException.repack(e);
                    }
                }
            }
            ConnectionUtils.commit(connection);
        } catch (Exception e) {
            ConnectionUtils.rollback(connection);
            throw BaseException.repack(e);
        } finally {
            ConnectionUtils.closeConnection(connection);
        }
    }

    public static <T> List<T> extract(ResultSet rs, RowMapper<T> rowMapper) {
        BaseException t = null;
        try {
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            return list;
        } catch (Exception e) {
            t = BaseException.repack(e);
            throw t;
        } finally {
            CloseUtils.close(rs, t);
        }
    }

    public static List<Map> extract(ResultSet rs) {
        try {
            String[] keys = getResultKeys(rs);
            return extract(rs, (resultSet, n) -> {
                Map map = new LinkedCaseInsensitiveMap();
                for (int i = 0; i < keys.length; i++) {
                    map.put(keys[i], resultSet.getObject(keys[i]));
                }
                return map;
            });
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }
}
