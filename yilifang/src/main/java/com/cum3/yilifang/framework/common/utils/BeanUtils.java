package com.cum3.yilifang.framework.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Table;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ClassUtils;

import com.cum3.yilifang.framework.common.annotations.Wrapper;
import com.cum3.yilifang.framework.common.exception.ExceptionUtils;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.cum3.yilifang.framework.web.warpper.BaseWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

/**
 * Bean 工具类
 * 
 * @author Fandy Lau
 */
public class BeanUtils {
    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法。
     * 
     * @param dest
     *            目标对象
     * @param src
     *            源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        List<Method> destSetters = getSetterMethods(dest);
        List<Method> srcGetters = getGetterMethods(src);
        try {
            for (Method setter : destSetters) {
                for (Method getter : srcGetters) {
                    if (isMethodPropEquals(setter.getName(), getter.getName())
                            && setter.getParameterTypes()[0].equals(getter.getReturnType())) {
                        setter.invoke(dest, getter.invoke(src));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     * 
     * @param obj
     *            对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj) {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     * 
     * @param obj
     *            对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj) {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     * 
     * @param m1
     *            方法名1
     * @param m2
     *            方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
    
          
    
    /**
     * 获取主键列名
     * @param cls
     * @return
     */
    public static String getPkColumn( Class<?> cls ){
        Set<EntityColumn> pkColumns =  EntityHelper.getPKColumns(cls);
        EntityTable tableInfo = EntityHelper.getEntityTable(cls);
        if (null != tableInfo && StringUtils.isNotEmpty(pkColumns)){
            String idColumn = null;
            for(EntityColumn pkColumn:pkColumns){
                idColumn = pkColumn.getProperty();
                break;
            }
            return idColumn;
        } else {
            throw ExceptionUtils.exp("Error:  Can not execute. Could not find @Id.");
        }
       
    }
    /**
     * 
     * 获取主键值
     * @param record
     * @return
     */
    public static Object getPkValue(BaseEntity<?> record){
        String pkColumn =  getPkColumn(record.getClass());
        return ToolReflect.on(record).call("get"+StringUtils.convertToCamelCase(pkColumn)).get();
    }
    
    /**
     * 
     * 获取modelName的Class
     * @param modelName
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Class<? extends BaseEntity> findModelClass(String modelName){
        //查询所有继承Baseentiy的class
        Set<Class> entityClasses =  ClassScaner.scan("com.cum3", Table.class);
        for (Class entity : entityClasses) {
          if(ClassUtils.getShortName(entity).equals(modelName)){
              return entity;
          }
        }
        throw ExceptionUtils.exp("there is no class searched that named "+modelName+" make sure it extends 'BaseEntity'!");
    }
    
    /**
     * 
     * 获取modelName的Class
     * @param modelName
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Class<? extends BaseWrapper> findWrapperClass(String wrapperName){
        //查询所有继承Baseentiy的class
        Set<Class> entityClasses =  ClassScaner.scan("com.cum3", Wrapper.class);
        for (Class entity : entityClasses) {
          if(ClassUtils.getShortName(entity).equals(wrapperName)){
              return entity;
          }
        }
        return null;
    }
    
    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }
 
    /**
     * 将map装换为javabean对象
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map,T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
 
    /**
     * 将List<T>转换为List<Map<String, Object>>
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }
 
    /**
     * 将List<Map<String,Object>>转换为List<T>
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps,Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }
    
}
