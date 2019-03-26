package com.cum3.yilifang.framework.common.genid;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cum3.yilifang.framework.common.utils.SpringUtils;
import com.cum3.yilifang.framework.common.utils.StringUtils;
import com.cum3.yilifang.framework.common.utils.date.DateUtil;
import com.cum3.yilifang.framework.datasource.DynamicDataSource;
import com.cum3.yilifang.framework.web.mapper.SqlMapper;

import tk.mybatis.mapper.genid.GenId;
/**
 * 全局主键 RecNo 生成策略
 * @author Fandy Liu
 * @created 2018年9月26日 下午5:03:01
 */
public class RecNoGen  implements GenId<String>{
    
   
    /**
     * 最后一条
     */
    private static final String GET_LAST_RECNO ="select {}  from {}  order by id desc   limit 0,1";
    /**
     * 
     */
    private static final String GET_BY_SCOPE = "select a.recNo,a.digit from ylf_sys_recno_attributemaster a  where a.delFlag = 0  and a.scope = '{}' order by id  limit 0,1";
    /**
     * 
     */
    private static final String GET_DATAS_BY_MASTERNO = "select a.ruleName,a.ruleNumber,a.orderIndex from ylf_sys_recno_attributedetail a where a.delFlag = 0 and a.masterNo = '{}' order by orderIndex ";
    
    public String genId(String table, String column) {
        String sql =StringUtils.format(GET_LAST_RECNO,column, table);
        SqlMapper sqlMapper = (SqlMapper)SpringUtils.getBean("sqlMapper");
        Map<String,Object> m = sqlMapper.selectOne(sql);
        String lastRecNo =m==null?null:(String)m.get(column);
        return getBaseRecNo(table, lastRecNo);
    }
    
    /**
     * 获取基本功能编码
     * @param scope
     * @param lastRecNo
     * @return
     */
    public  String getBaseRecNo(String scope,String lastRecNo){
        DynamicDataSource.setDataSourceKey("medical");//切换id生成所在的库
        SqlMapper sqlMapper = (SqlMapper)SpringUtils.getBean("sqlMapper");
        String recNo = null;
        Map<String, Object> resNosAttribute = sqlMapper.selectOne(StringUtils.format(GET_BY_SCOPE, scope));//可以换成缓存中读取
        if(resNosAttribute != null){
            String recNo0 = (String) resNosAttribute.get("recNo");
            int digit0 = (int) resNosAttribute.get("digit");
            List<Map<String, Object>> mlist0 =sqlMapper.selectList(StringUtils.format(GET_DATAS_BY_MASTERNO, recNo0));//可以换成缓存中读取
            recNo = createRecNo(mlist0, digit0,lastRecNo, null);
        }
        DynamicDataSource.setDataSourceKey("base");//切换回主库
        return recNo;
    }
    
    /**
     * 生成编码
     * @param recNoAttributeDetails ---》编码规程
     * @param digit ---》流水号位数
     * @param lastRecNo ---》最后一个编码
     * @param map0 ---》前台传值
     * @return
     */
    public  String createRecNo(List<Map<String,Object>> recNoAttributeDetails,int digit,String lastRecNo,Map<String,Object> map0){
        String str = "";
        for(int i = 1; i<= recNoAttributeDetails.size() ;i++){
            for(int j = 0; j< recNoAttributeDetails.size() ;j++){
                String ruleName = (String) recNoAttributeDetails.get(j).get("ruleName");
                int ruleNumber = (int) recNoAttributeDetails.get(j).get("ruleNumber");
                int orderIndex = (int) recNoAttributeDetails.get(j).get("orderIndex");
                if(orderIndex == i){
                    if(ruleNumber == 0){//字符串
                        str = str + ruleName;
                    }
                    if(ruleNumber == 1){//时间格式
                        str = str + DateUtil.dateToString(new Date(), ruleName);
                    }
                    if(ruleNumber == 2){//关联字段
                        str = str + map0.get(ruleName);
                    }
                }
            }
        }
        String digitStr = createStringDigit(digit, lastRecNo);
        return str + digitStr;
    }
    
    /**
     * 生成编码
     * @param recNoAttributeDetails ---》编码规程
     * @param digit ---》流水号位数
     * @param lastRecNo ---》最后一个编码
     * @param parentId ----》父节点编码
     * @param map0 ---》前台传值
     * @return
     */
    public  String createRecNo(List<Map<String,Object>> recNoAttributeDetails,int digit,String lastRecNo,String parentId,Map<String,Object> map0){
        String str = "";
        if(parentId.equals("0") || parentId == null){
            parentId = "";
        }
        for(int i = 1; i<= recNoAttributeDetails.size() ;i++){
            for(int j = 0; j< recNoAttributeDetails.size() ;j++){
                String ruleName = (String) recNoAttributeDetails.get(j).get("ruleName");
                int ruleNumber = (int) recNoAttributeDetails.get(j).get("ruleNumber");
                int orderIndex = (int) recNoAttributeDetails.get(j).get("orderIndex");
                if(orderIndex == i){
                    if(ruleNumber == 0){//字符串
                        str = str + ruleName;
                    }
                    if(ruleNumber == 1){//时间格式
                        str = str + DateUtil.dateToString(new Date(), ruleName);
                    }
                    if(ruleNumber == 2){//关联字段
                        str = str + map0.get(ruleName);
                    }
                }
            }
        }
        String digitStr = createStringDigit(digit, lastRecNo);
        return parentId + str + digitStr;
    }
    
    /**
     * 生成系统编码
     * @param sysRecNosAttribute
     * @param lastRecNo
     * @return
     */
    public  String createSysRecNo(Map<String, Object> sysRecNosAttribute, String lastRecNo) {
        String recNo = "";
        String prefix = (String) sysRecNosAttribute.get("prefix");// 前缀
        int digit = 0;
        try {
            digit = Integer.parseInt(String.valueOf(sysRecNosAttribute.get("digit")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String suffix = "";// 后缀
        for (int i = 0; i < digit; i++) {
            suffix = suffix + "0";
        }
        DecimalFormat df = new DecimalFormat(suffix);
        if (lastRecNo != null && !lastRecNo.equals("")) {
            int lastSuffix = Integer.parseInt(lastRecNo.substring(lastRecNo.length() - digit));
            suffix = df.format(lastSuffix + 1);
        } else {
            suffix = df.format(1);
        }
        recNo = prefix + suffix;
        return recNo;
    }

    public  String createStringDigit(int digit, String lastRecNo) {
        String digitStr = "";
        for (int i = 0; i < digit; i++) {
            digitStr = digitStr + "0";
        }
        DecimalFormat df = new DecimalFormat(digitStr);
        if (lastRecNo != null && !lastRecNo.equals("")) {
            int lastSuffix = Integer.parseInt(lastRecNo.substring(lastRecNo.length() - digit));
            digitStr = df.format(lastSuffix + 1);
        } else {
            digitStr = df.format(1);
        }
        return digitStr;
    }
    /**
     * 生成编码
     * @param recNoAttribute
     * @param lastRecNo
     * @return
     */
    public  String createRecNo(Map<String, Object> recNoAttribute, String lastRecNo) {
        String recNo = "";
        if (recNoAttribute.get("prefix") != null && recNoAttribute.get("middle") != null
                && recNoAttribute.get("digit") != null) {
            String prefix = (String) recNoAttribute.get("prefix");// 前缀
            String middle = (String) recNoAttribute.get("middle");
            int digit = 0;
            try {
                digit = Integer.parseInt(String.valueOf(recNoAttribute.get("digit")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String suffix = "";// 后缀
            for (int i = 0; i < digit; i++) {
                suffix = suffix + "0";
            }
            DecimalFormat df = new DecimalFormat(suffix);
            if (lastRecNo != null && !lastRecNo.equals("")) {
                int lastSuffix = Integer.parseInt(lastRecNo.substring(lastRecNo.length() - digit));
                suffix = df.format(lastSuffix + 1);
            } else {
                suffix = df.format(1);
            }
            recNo = prefix + middle + suffix;
        }
        return recNo;
    }

}