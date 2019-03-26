package com.cum3.yilifang.framework.common.utils.sql;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cum3.yilifang.framework.common.exception.ExceptionUtils;
import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.common.utils.date.DateStyle;
import com.cum3.yilifang.framework.common.utils.date.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * SQL过滤器，用于添加where条件和排序，过滤结果集
 * <p>
 * 添加规则使用addFilter方法
 * <p>
 * 举例：QUERY_t#id_S_EQ = 0 //最终连接出的SQL是 and t.id = {} id的值是0通过参数传递
 * <p>
 * 格式说明QUERY前缀就说明要添加过滤条件
 * <p>
 * t#id 就是t.id
 * <p>
 * S:String L:Long I:Integer D:Date ST:Short BD:BigDecimal FT:Float
 * <p>
 * EQ 是操作符
 * <p>
 * // EQ 相等 // NE 不等 // LT 小于 // GT 大于 // LE 小于等于 // GE 大于等于 // LK 模糊 // RLK 右模糊
 * // LLK 左模糊 TLK 树本级和下级 TCLK 树下级
 *
 * @author Fandy Liu
 * @created 2014年10月5日 下午11:36:54
 */
public class SqlFilter {
    /**
     * 为了获取request里面传过来的动态参数
     */
    private String postJson;
    
    private Example example;
    
    private Criteria criteria ;
    
    /**
     * 为了获取request里面传过来的动态参数
     */
    private List<Object> params = new ArrayList<Object>();
    
    /**
     * 为了获取request里面传过来的动态参数
     */
    private StringBuffer sql = new StringBuffer();
    /**
     * 排序字段
     */
    private String sort;// 
    /**
     * asc
     */
    private String order = "asc";

    /**
     * 默认构造
     *
     * @author Fandy Liu
     * @created 2014年10月5日 下午11:36:54
     */
    public SqlFilter() {

    }

    /**
     * 带参构造
     * @param request
     */
    public SqlFilter(String postJson) {
        this.postJson = postJson;
        addFilter(postJson);
    }
    /**
     * 带参构造
     * @param request
     */
    public SqlFilter(String postJson, Example example) {
        this.postJson = postJson;
        this.example = example;
        this.criteria = example.createCriteria();
        addFilter(postJson);
    }

    /**
     * 添加排序字段
     *
     * @param sort
     * @author Fandy Liu
     * @created 2014年10月5日 下午11:36:54
     */
    public void addSort(String sort) {
        this.sort = sort;
    }

    /**
     * 添加排序方法，默认asc升序
     * @param order
     */
    public void addOrder(String order) {
        this.order = order;
    }

    /**
     * 转换SQL操作符
     * @param operator
     * @return
     */
    private String getSqlOperator(String operator) {
        if (StringUtils.equalsIgnoreCase(operator, "EQ")) {
            return " = ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "NE")) {
            return " != ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "LT")) {
            return " < ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "GT")) {
            return " > ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "LE")) {
            return " <= ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "GE")) {
            return " >= ";
        }
        if (StringUtils.equalsIgnoreCase(operator, "LK") || StringUtils.equalsIgnoreCase(operator, "RLK")
                || StringUtils.equalsIgnoreCase(operator, "LLK")) {
            return " like ";
        }
        return "";
    }

    /**
     * 获得添加过滤字段后的SQL
     * @return
     */
    public String getWhereSql() {
        return sql.toString();
    }

    /**
     * 获得添加过滤字段后加上排序字段的SQL
     * @return
     */
    public String getWhereAndOrderSql() {
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            sql.append(" order by " + sort + " " + order + " ");// 添加排序信息
        } else {
            if (postJson != null) {
                String s = JSONUtils.parseString(postJson, sort);
                String o = JSONUtils.parseString(postJson,order);
                if (!StringUtils.isBlank(s)) {
                    sort = s;
                }
                if (!StringUtils.isBlank(o)) {
                    order = o;
                }
                if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
                    sql.append(" order by " + sort + " " + order + " ");// 添加排序信息
                }
            }
        }
        return sql.toString();
    }

    /**
     * 获得过滤字段参数和值
     * @return
     */
    public List<Object> getParams() {
        return params;
    }

    /**
     * 添加过滤
     * @param request
     */
    public void addFilter(String postJson) {
        Map<String,Object> passdataMap = JSONUtils.json2Map(postJson);
        for(String name:passdataMap.keySet()){
            Object value = passdataMap.get(name);
            if (value!=null) {
                addFilter(name, value.toString().trim());
                addCriteria(criteria,name,value.toString());
            }
        }
    }
    
    /**
     * 添加过滤
     * <p>
     * 举例，name传递：QUERY_t#id_S_EQ
     * <p>
     * 举例，value传递：0
     *
     * @author Fandy Liu
     * @created 2014年10月5日 下午11:36:54
     */
    public void addCriteria (Criteria criteria,String name, String value) {
        if (criteria !=null && name != null && StringUtils.isNotEmpty(value)) {
            if (name.startsWith("QUERY_")) {// 如果有需要过滤的字段
                String[] filterParams = StringUtils.split(name, "_");
                if (filterParams.length == 4) {
                    String columnName = filterParams[1].replaceAll("#", ".");// 要过滤的字段名称
                    String columnType = filterParams[2];// 字段类型
                    String operator = filterParams[3];// SQL操作符
                    //or查询的时候val等于value本身 在下面的OREQ 进行处理
                    Object val =value.indexOf(":") ==-1?getObjValue(columnType,operator,value):value;
                    if(val instanceof String){
                        val = val.toString().replace("'", "").trim();
                    }
                    // TLK 树本级和下级
                    if (StringUtils.equalsIgnoreCase(operator, "TLK")) {
                        Criteria criteriaOr = example.createCriteria();
                        criteriaOr.andEqualTo(columnName, value);
                        criteriaOr.orLike(columnName, val.toString());
                        example.and(criteriaOr);
                    }
                     // TCLK 树下级
                     else if (StringUtils.equalsIgnoreCase(operator, "TCLK")) {
                        criteria.andLike(columnName, val.toString());
                    } else if (StringUtils.equalsIgnoreCase(operator, "EQNULL")) {
                        criteria.andIsNull(columnName);
                    } else {
                        if (StringUtils.equalsIgnoreCase(operator, "EQ")) {
                            criteria.andEqualTo(columnName, val);
                        }
                        if (StringUtils.equalsIgnoreCase(operator, "NE")) {
                            criteria.andNotEqualTo(columnName, val);
                        }
                        if (StringUtils.equalsIgnoreCase(operator, "LT")) {
                            if(val instanceof Date){
                                criteria.andLessThan(columnName,dealYYYYMMDDDateStyle(value,"L"));
                            }else{
                                criteria.andLessThan(columnName, value);
                            }
                        }
                        if (StringUtils.equalsIgnoreCase(operator, "GT")) {
                            if(val instanceof Date){
                                criteria.andGreaterThan(columnName,dealYYYYMMDDDateStyle(value,"G"));
                            }else{
                                criteria.andGreaterThan(columnName, value);
                            }
                        }
                        if (StringUtils.equalsIgnoreCase(operator, "LE")) {
                            if(val instanceof Date){
                                criteria.andLessThanOrEqualTo(columnName,dealYYYYMMDDDateStyle(value,"L"));
                            }else{
                                criteria.andLessThanOrEqualTo(columnName, value);
                            }
                        }
                        if (StringUtils.equalsIgnoreCase(operator, "GE")) {
                            if(val instanceof Date){
                                criteria.andGreaterThanOrEqualTo(columnName,dealYYYYMMDDDateStyle(value,"G"));
                            }else{
                                criteria.andGreaterThanOrEqualTo(columnName, value);
                            }
                        }
                        if (StringUtils.equalsIgnoreCase(operator, "LK")) {
                            criteria.andLike(columnName, "%"+val.toString()+"%");
                        }
                        if( StringUtils.equalsIgnoreCase(operator, "RLK")){
                            criteria.andLike(columnName, val.toString()+"%");
                        }
                        if( StringUtils.equalsIgnoreCase(operator, "LLK")){
                            criteria.andLike(columnName, "%"+val.toString());
                        }
                        if( StringUtils.equalsIgnoreCase(operator, "OREQ")){
                            String[] values = value.split(":");
                            Criteria criteriaOr = example.createCriteria();
                            for(String pv :values){
                                if(StringUtils.isNotEmpty(pv)){
                                    criteriaOr.orEqualTo(columnName,pv);
                                }
                            }
                            example.and(criteriaOr);
                        }
                        if( StringUtils.equalsIgnoreCase(operator, "ORLK")){
                            String[] values = value.split(":");
                            Criteria criteriaOr = example.createCriteria();
                            for(String pv :values){
                                if(StringUtils.isNotEmpty(pv)){
                                    criteriaOr.orLike(columnName,"%"+pv+"%");
                                }
                            }
                            example.and(criteriaOr);
                        }
                    }
                }
            }
        }
    }
    /**
     * 
     * 描述
     * @author Fandy Liu
     * @created 2018年10月18日 下午10:49:21
     * @param strDate
     * @param type  比较类型： L 小于或小于等于   G 大于或 大于等于
     * @return
     */
    private Date dealYYYYMMDDDateStyle(String strDate,String type){
        DateStyle dateStyle = DateUtil.getDateStyle(strDate);
        if(dateStyle == null){
            throw ExceptionUtils.exp("传递的时间格式非法，系统无法识别！");
        }
        if(dateStyle.equals(DateStyle.YYYY_MM_DD)||dateStyle.equals(DateStyle.YYYYMMDD)
                ||dateStyle.equals(DateStyle.YYYY_MM_DD_EN)
                ||dateStyle.equals(DateStyle.YYYY_MM_DD_CN)){//传递格式是年月日 特殊处理下 小于等于的时候 补上23:59:59 大于或大于等于补上00:00:00
            if(type.equals("L")){
                strDate +=" 23:59:59";
            } 
            if(type.equals("G")){
                strDate +=" 00:00:00";
            }
        }
        return  DateUtil.stringToDate(strDate);
    }

    /**
     * 添加过滤
     * <p>
     * 举例，name传递：QUERY_t#id_S_EQ
     * <p>
     * 举例，value传递：0
     *
     * @author Fandy Liu
     * @created 2014年10月5日 下午11:36:54
     */
    public void addFilter(String name, String value) {
        if (name != null && StringUtils.isNotEmpty(value)) {
            if (name.startsWith("QUERY_")) {// 如果有需要过滤的字段
                String[] filterParams = StringUtils.split(name, "_");
                if (filterParams.length == 4) {
                    String columnName = filterParams[1].replaceAll("#", ".");// 要过滤的字段名称
                    String columnType = filterParams[2];// 字段类型
                    String operator = filterParams[3];// SQL操作符
                    // TLK 树本级和下级
                    if (StringUtils.equalsIgnoreCase(operator, "TLK")) {
                        sql.append(" and (" + columnName + " = {} or " + columnName + " like {}) ");
                        params.add( SqlUtils.toSqlSearchVal(value));
                        params.add( SqlUtils.toSqlSearchVal(value + "%"));
                    }
                    // TCLK 树下级
                    else if (StringUtils.equalsIgnoreCase(operator, "TCLK")) {
                        sql.append(" and " + columnName + " like {} ");
                        params.add(SqlUtils.toSqlSearchVal(value + "%"));
                    } else if (StringUtils.equalsIgnoreCase(operator, "EQNULL")) {
                        sql.append(" and (" + columnName + " is null or "+columnName +"= '' ) ");
                    } else {
                        // 判断是否存在多个字段情况 如果存在 通过or连接的 （一般用于关键字查询）
                        if (columnName.contains(":")) {
                            String[] columnNames = columnName.split(":");
                            for (int i = 0; i < columnNames.length; i++) {
                                if (i == 0) {
                                    sql.append(" and ( " + columnNames[i] + " " + getSqlOperator(operator) + "{} ");
                                    params.add(getObjValue(columnType, operator,  SqlUtils.toSqlSearchVal(value)));
                                } else {
                                    sql.append(" or " + columnNames[i] + " " + getSqlOperator(operator) + "{} ");
                                    params.add(getObjValue(columnType, operator, SqlUtils.toSqlSearchVal(value)));
                                }
                                if (i == columnNames.length - 1) {
                                    sql.append(") ");
                                }
                            }

                        } else {
                            sql.append(" and " + columnName + " " + getSqlOperator(operator) + "{}");// 拼SQL
                            params.add(getObjValue(columnType, operator,value));
                        }
                    }
                }
            }
        }
    }

    /**
     * 将String值转换成Object，用于拼写HQL，替换操作符和值
     * <p>
     * S:String L:Long I:Integer D:Date ST:Short BD:BigDecimal FT:Float
     *
     * @param columnType
     * @param operator
     * @param value
     * @return
     * @throws ParseException 
     */
    private Object getObjValue(String columnType, String operator, String value)  {
        if (StringUtils.equalsIgnoreCase(columnType, "S")) {
            if (StringUtils.equalsIgnoreCase(operator, "LK")) {
                value = "%" + value + "%";
            } else if (StringUtils.equalsIgnoreCase(operator, "RLK")) {
                value = value + "%";
            } else if (StringUtils.equalsIgnoreCase(operator, "LLK")) {
                value = "%" + value;
            }
            return SqlUtils.toSqlSearchVal(value);
        }
        if (StringUtils.equalsIgnoreCase(columnType, "L")) {
            return Long.parseLong(value);
        }
        if (StringUtils.equalsIgnoreCase(columnType, "I")) {
            return Integer.parseInt(value);
        }
        if (StringUtils.equalsIgnoreCase(columnType, "D")) {
           return DateUtil.stringToDate(value.toString());
        }
        if (StringUtils.equalsIgnoreCase(columnType, "ST")) {
            return Short.parseShort(value);
        }
        if (StringUtils.equalsIgnoreCase(columnType, "BD")) {
            return new BigDecimal(value);
        }
        if (StringUtils.equalsIgnoreCase(columnType, "FT")) {
            return Float.parseFloat(value);
        }
        return null;
    }

    public String getPostJson() {
        return postJson;
    }

    public Example getExample() {
        return example;
    }

    public StringBuffer getSql() {
        return sql;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }
}
