
package com.cum3.yilifang.framework.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 * @author Fandy Lau
 * @created 2018年9月6日 下午5:07:01
 */
public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
  
    /**
     * @param <T>
     *            -> DepartmentBean
     * @param jsontext
     *            -> {"department":[{"id":"1","name":"生产部"}],"password":"admin",
     *            "username":"admin"}
     * @param obj_str
     *            -> department
     * @param clazz
     *            -> DepartmentBean
     * @return ->List<T>
     */
    
    public static final <T> List<T> getList(String jsontext, String listStr, Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(listStr);
        if (obj == null) {
            return null;
        }
        // if(obj instanceof JSONObject){}
        if (obj instanceof JSONArray) {
            JSONArray jsonarr = (JSONArray) obj;
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < jsonarr.size(); i++) {
                list.add(jsonarr.getObject(i, clazz));
            }
            return list;
        }
        return null;
    }

    /**
     * @param <T>
     *            -> DepartmentBean
     * @param jsontext
     *            -> {"department":{"id":"1","name":"生产部"},"password":"admin",
     *            "username":"admin"}
     * @param obj_str
     *            -> department
     * @param clazz
     *            -> DepartmentBean
     * @return -> T
     */
    public static final <T> T getObject(String jsontext, String objStr, Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }

        Object obj = jsonobj.get(objStr);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            return jsonobj.getObject(objStr, clazz);
        } else {
            logger.info(obj.getClass() + "");
        }

        return null;
    }

    /**
     * @param <T>
     * @param jsontext
     *            ->{"department":{"id":"1","name":"生产部"},"password":"admin",
     *            "username":"admin"}
     * @param clazz
     *            -> UserBean.class
     * @return -> UserBean
     */
    // 注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
    public static final <T> T getObject(String jsontext, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsontext, clazz);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsontext, e);
        }
        return t;
    }

    /**
     * 描述
     * @param object
     * @param prettyFormat
     * @return
     */
    public static final String toJSONString(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }

    /**
     * @Description： json字符串转成为List
     * @param jsonStr
     *            json字符串
     * @param clazz
     *            class名称
     * @return
     */
    public static <T> List<T> getList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            logger.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }

    /**
     * 
     * @Description： json字符串转换成list<Map>
     * @param jsonString
     *            json字符串
     * @return
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString, new com.alibaba.fastjson.TypeReference<List<Map<String, Object>>>() { });
        } catch (Exception e) {
            logger.error("json字符串转map失败", e);
        }
        return list;
    }

    /**
     * @Description： json字符串转换为Map
     * @param jsonStr
     *            json字符串
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }
    

    /**
     * 获取请求中的json对象
     * @param request
     * @return
     * @throws Exception
     */
    public static JSONObject getJsonData(HttpServletRequest request) throws Exception {
        String resStr = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb = new StringBuffer("");
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        resStr = sb.toString();
        JSONObject json = JSONObject.parseObject(resStr);
        return json;

    }
    
    /**
     * 描述
     * @param body
     * @param field
     * @return
     */
    public static String parseString(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if(leaf != null)
                return leaf.asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 描述
     * @author Fandy Liu
     * @param field
     * @return
     */
    public static Integer parseInteger(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if(leaf != null)
                return leaf.asInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> parseStringList(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if(leaf != null)
                return mapper.convertValue(leaf, new TypeReference<List<String>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param body
     * @param field
     * @return
     */
    public static Boolean parseBoolean(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if(leaf != null)
                return leaf.asBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Short parseShort(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if(leaf != null) {
                Integer value = leaf.asInt();
                return value.shortValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 描述 json字符转field
     * @param body
     * @param field
     * @return
     */
    public static Byte parseByte(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if(leaf != null) {
                Integer value = leaf.asInt();
                return value.byteValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 描述 获取json字符串中的对象
     * @param body
     * @param field
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String body, String field, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            node = node.get(field);
            return mapper.treeToValue(node, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 描述
     * @param json
     * @return
     */
    public static Object toNode(String json) {
        if(json == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(json);
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}