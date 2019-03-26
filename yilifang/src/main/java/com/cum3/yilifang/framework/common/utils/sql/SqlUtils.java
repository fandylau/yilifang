
package com.cum3.yilifang.framework.common.utils.sql;

import java.util.regex.Pattern;

/**
 * <p>
 * SqlUtils工具类
 * </p>
 * @since 2016-11-13
 */
public class SqlUtils {

    private final static SqlFormatter SQL_FORMATTER = new SqlFormatter();

    /**
     * 验证字符串是否是数据库字段
     */
    private static final Pattern P_IS_CLOMUN = Pattern.compile("^\\w\\S*[\\w\\d]*$");
    /**
     * 格式sql
     *
     * @param boundSql
     * @param format
     * @return
     */
    public static String sqlFormat(String boundSql, boolean format) {
        if (format) {
            try {
                return SQL_FORMATTER.format(boundSql);
            } catch (Exception ignored) {
            }
        }
        return boundSql;
    }

    /**
     * <p>
     * 用%连接like
     * </p>
     *
     * @param str 原字符串
     * @return
     */
    public static String concatLike(String str, SqlLike type) {
        StringBuilder builder = new StringBuilder(str.length() + 3);
        switch (type) {
            case LEFT:
                builder.append(StringPool.PERCENT).append(str);
                break;
            case RIGHT:
                builder.append(str).append(StringPool.PERCENT);
                break;
            case CUSTOM:
                builder.append(str);
                break;
            default:
                builder.append(StringPool.PERCENT).append(str).append(StringPool.PERCENT);
        }
        return builder.toString();
    }

    /**
     * <p>
     * 获取需要转义的SQL字段
     * </p>
     *
     * @param dbType   数据库类型
     * @param val      值
     * @param isColumn val 是否是数据库字段
     */
    public static String sqlWordConvert(DbType dbType, String val, boolean isColumn) {
        if (dbType == DbType.POSTGRE_SQL) {
            if (isColumn && isNotColumnName(val) || val.toLowerCase().equals(val)) {
                // 都是数据库字段的情况下
                // 1.手动加了转义符
                // 2.全小写之后和原值一样
                // 都直接返回
                return val;
            }
            return String.format("\"%s\"", val);
        }
        return val;
    }

    /**
     * <p>
     * SQL注入内容剥离
     * </p>
     *
     * @param sql 待处理 SQL 内容
     * @return this
     */
    public static String stripSqlInjection(String sql) {
        return sql.replaceAll("('.+--)|(--)|(\\|)|(%7C)", StringPool.EMPTY);
    }
    
    /**
     * <p>
     * 判断字符串是否符合数据库字段的命名
     * </p>
     *
     * @param str 字符串
     * @return 判断结果
     */
    public static boolean isNotColumnName(String str) {
        return !P_IS_CLOMUN.matcher(str).matches();
    }
    
    /**
     * 描述 查询值 加单引号
     * @param ids
     * @return
     */
    public static String toSqlSearchVal(String vals) {
        if (null == vals || vals.isEmpty()) {
            return "";
        }

        String[] idsArr = vals.split(",");
        StringBuilder sqlSb = new StringBuilder();
        int length = idsArr.length;
        for (int i = 0, size = length - 1; i < size; i++) {
            sqlSb.append(" '").append(idsArr[i]).append("', ");
        }
        if (length != 0) {
            sqlSb.append(" '").append(idsArr[length - 1]).append("' ");
        }
        return sqlSb.toString();
    }


}
