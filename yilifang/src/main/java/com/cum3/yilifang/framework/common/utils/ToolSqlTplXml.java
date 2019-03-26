
package com.cum3.yilifang.framework.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.google.common.collect.Lists;

/**
 * 处理Sql Map
 * 说明：加载sql map中的sql到map中，并提供动态长度sql处理
 * @author Fandy Lau
 * @created 2015年5月20日 上午9:12:53
 */
public class ToolSqlTplXml {
    
    /**
     * 日志工具
     */
    private final static Logger LOG= LoggerFactory.getLogger(ToolSqlTplXml.class);

    /**
     * 缓存xml中所有的sql语句
     */
    private static final Map<String, String> SQL_MAP = new HashMap<String, String>();
    
    /**
     * 过滤掉的sql关键字
     */
    private static final List<String> BAD_KEY_WORD_LIST = new ArrayList<String>();
    
    /**
     * 加载关键字到List
     */
    static {
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";
        BAD_KEY_WORD_LIST.addAll(Arrays.asList(badStr.split("\\|")));
    }
    
    /**
     * sql查询关键字过滤效验
     * @param queryStr
     * @return
     */
    public static boolean keywordVali(String queryStr) {
        queryStr = queryStr.toLowerCase();//统一转为小写
        for (String badKeyWord : BAD_KEY_WORD_LIST) {
            if (queryStr.equals(badKeyWord)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 获取SQL，固定SQL
     * @author Fandy Liu
     * @created 2016年3月2日 上午11:33:21
     * @param sqlId
     * @return
     */
    public static String getSql(String sqlId) {
        String sql = SQL_MAP.get(sqlId);
        if(null == sql || sql.isEmpty()){
            LOG.error("sql语句不存在：sql id是{}" ,sqlId );
            return "";
        } else{
            return sql.replaceAll("[\\s]{2,}", " ");
        }
    }
    /**
     * 描述 清除加载的sql
     * @author Fandy Liu
     * @created 2016年3月2日 上午11:31:04
     */
    public static void destory() {
        SQL_MAP.clear();
    }

    /**
     * 初始化加载sql语句到map
     * @author Fandy Liu
     * @created 2016年3月2日 上午11:33:07
     * @param isInit
     */
    public static synchronized void init(boolean isInit) throws DocumentException {
        
        
        //获取容器资源解析器
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<InputStream> filesInputStreams = new ArrayList<InputStream>();
        List<String> fileNames = Lists.newArrayList();
        //获取所有匹配的文件
        try {
            Resource[] resources = resolver.getResources("classpath:com/cum3/yilifang/project/**/**/*.sql.xml");
            for(Resource resource : resources) {
              //获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
                filesInputStreams.add(resource.getInputStream());
                fileNames.add(resource.getFilename());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        SAXReader reader = new SAXReader();
        String fileName = null;
        for (int i = 0;i<filesInputStreams.size();i++) {
            InputStream xmlfileInputStream = filesInputStreams.get(i);
            fileName =fileNames.get(i);
            Document doc = null ;
            try {
                doc = reader.read(xmlfileInputStream);
            } catch (Exception e) {
            }finally{
                try {
                    if(xmlfileInputStream!=null){
                        xmlfileInputStream.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            Element root = doc.getRootElement();
            String namespace = root.attributeValue("namespace");
            if(null == namespace || namespace.trim().isEmpty()){
                LOG.error("sql xml文件" + fileName + "的命名空间不能为空");
                continue;
            }
            
            for(Iterator<?> iterTemp = root.elementIterator(); iterTemp.hasNext();) {    
                Element element = (Element) iterTemp.next();    
                if(element.getName().toLowerCase().equals("sql")){
                    String id = element.attributeValue("id");
                    if(null == id || id.trim().isEmpty()){
                        LOG.error("sql xml文件{}的存在没有id的sql语句",fileName);
                        continue;
                    }
                    
                    String sql = element.getText();
                    if(null == sql || sql.trim().isEmpty()){
                        LOG.error("sql xml文件{}存在没有内容的sql语句", fileName );
                        continue;
                    }

                    String key = namespace + "." + id;
                    if(isInit && SQL_MAP.containsKey(key)){
                        LOG.error("sql xml文件{}的sql语句{}的存在重复命名空间和ID",fileName,key);
                        continue;
                    } else if(SQL_MAP.containsKey(key)){
                        LOG.error("sql xml文件{}的sql语句{}的存在重复命名空间和ID",fileName,key);
                    }
                    
                    sql = sql.replaceAll("[\\s]{2,}", " ");
                    SQL_MAP.put(key, sql);
                    LOG.debug("sql加载, sql file = {}, sql key = {}",fileName,key);
                }
            }
        }
    }
    
}
