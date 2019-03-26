package com.cum3.yilifang.framework.web.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.BeanUtils;
import com.cum3.yilifang.framework.common.utils.SpringUtils;
import com.cum3.yilifang.framework.common.utils.ToolReflect;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.cum3.yilifang.framework.web.domain.PageEntity;
import com.cum3.yilifang.framework.web.warpper.BaseWrapper;
import com.google.common.collect.Lists;
/**
 * <p> 直接以前端传递来的modelName+Serivce名+方法名去调用Service层的同名方法; Controller层不再需要写任何代码
 * <p> 例子
 * <pre>
 *      前端: /Task/testService/queryOne
 *      Service层相应的方法签名:  Object queryOne(Map<String, Object> parameterMap)
 *      相应的Service注册到Spring容器中的id : testService
 * </pre>
 * <p>这个适用于绝大部分业务请求 如果有复杂的跳转页面的业务  请自行编写controller实现</p>
 * @author Fandy Lau
 * @created 2018年9月2日 下午4:10:45
 */
@RestController
@RequestMapping("/yilifang")
public class CommonController  extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CommonController.class);
    //同时支持post和get请求
    @RequestMapping(value = "/{modelName}/{serviceName}/{serviceMethodName}",method = {RequestMethod.POST, RequestMethod.GET})
    public Object common(
            @PathVariable final String modelName,
            @PathVariable final String serviceName, 
            @PathVariable final String serviceMethodName,
            @RequestBody(required = false)  final String jsonStr,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 获取本次的调度服务名和相应的方法名
        String fullServiceMethodName = StrFormatter.format("{}.{}", serviceName, serviceMethodName);
        // 输出日志
        LOG.debug("### current request method is [ {} ] ,  parameters is [ {} ]", fullServiceMethodName,jsonStr);
        // 获取Spring中注册的Service Bean
        Object serviceBean = SpringUtils.getBean(serviceName);
        //获取父类包含的所有方法
        Method[] methods = serviceBean.getClass().getSuperclass().getMethods();
        ToolReflect reflet = ToolReflect.on(serviceBean);//反射到目标service
        PassParamsUtils param= PassParamsUtils.on(jsonStr);//传递参数
        Class<?> modelCalss = BeanUtils.findModelClass(modelName);//查找映射的实体类
        for(Method method : methods){
            String methodName = method.getName();
            if(methodName.equals(serviceMethodName)){
               switch(methodName) {
                   case "queryById"://根据id查询一个bean
                       Object ret = reflet.call(serviceMethodName,param.pkValue()).get();
                       doLog(fullServiceMethodName);
                       return success( warpObject(modelName,ret));
                   case "queryAll"://查询所有bean
                       Object ret1 = reflet.call(serviceMethodName).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret1));
                   case "queryOne"://查询一个bean  
                       Object ret2 = reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       warpObject(modelName,warpObject(modelName,ret2));
                       return success(ret2);
                   case "queryListByWhere":
                       Object ret3 = reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret3));
                   case "queryPageListByWhere":
                       Object ret4 = reflet.call(serviceMethodName, param.page(),param.pageSize(), param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret4));
                   case "queryPageListBySqlFilter": 
                       Object ret5 = reflet.call(serviceMethodName, param.page(),param.pageSize(),initSqlFilter(param.passdataJson(),param.sortBy(),param.sortType()),param.sqlId()).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret5));
                   case "queryListBySqlFilter":
                       Object ret6 = reflet.call(serviceMethodName,initSqlFilter(param.passdataJson(),param.sortBy(),param.sortType()),param.sqlId()).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret6));
                   case "selectOneBySqlFilter":
                       Object ret22 = reflet.call(serviceMethodName,initSqlFilter(param.passdataJson(),param.sortBy(),param.sortType()),param.sqlId()).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret22));
                   case "queryListBySqlTemplate":
                       List<String> params = param.params();
                       Object ret7 = reflet.call(serviceMethodName,param.sqlId(),params==null?Lists.newArrayList().toArray():params.toArray()).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret7));
                   case "selectOneBySqlId":
                       List<String> lt = param.params();
                       Object ret20 = reflet.call(serviceMethodName,param.sqlId(),lt==null?Lists.newArrayList().toArray():lt.toArray()).get();
                       doLog(fullServiceMethodName);
                       return success(warpObject(modelName,ret20));
                   case "save":
                       Object ret8 =reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret8);
                   case "saveByMutipart":
                       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                       MultipartFile file = multipartRequest.getFile("file");
                       Object ret21 =reflet.call(serviceMethodName,file).get();
                       doLog(fullServiceMethodName);
                       return success(ret21);
                   case "saveOrUpdate":
                       Object ret9 = reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret9);
                   case "batchInsert":
                       Object ret10 = reflet.call(serviceMethodName,param.listEntity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret10);
                   case "saveOrUpdateList":
                       Object ret11 = reflet.call(serviceMethodName,param.listEntity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret11);
                   case "saveSelective":
                       Object ret12 =reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret12);
                   case "update":
                       Object ret13 =reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret13);
                   case "updateSelective":
                       Object ret14 = reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret14);
                   case "deleteById":
                       Object ret15 = reflet.call(serviceMethodName,param.pkValue()).get();
                       doLog(fullServiceMethodName);
                       return success(ret15);
                   case "deleteByIds":
                       String pkColumNname = BeanUtils.getPkColumn(modelCalss);
                       Object ret16 = reflet.call(serviceMethodName, modelCalss, pkColumNname,param.pkValues()).get();
                       doLog(fullServiceMethodName);
                       return success(ret16);
                   case "deleteByWhere":
                       Object ret17 = reflet.call(serviceMethodName,param.entity(modelCalss)).get();
                       doLog(fullServiceMethodName);
                       return success(ret17);
                   case "synTree"://同步树
                       Object ret18 = reflet.call(serviceMethodName).get();
                       doLog(fullServiceMethodName);
                       return ret18;
                   case "asyTree"://异步树
                       String pid = jsonStr==null?request.getParameter("pid"):param.pid();
                       Object ret19 = reflet.call(serviceMethodName,pid).get();
                       doLog(fullServiceMethodName);
                       return ret19;
               }
            }
        }
        //以上都不满足 调用自定义的方法
        Object ret20 = reflet.call(serviceMethodName).get();
        doLog(fullServiceMethodName);
        return success(ret20); 
    }
    
    
    /**
     * 装饰一个对象返回字典中文
     * @param warpper
     * @return
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private Object warpObject(String modelName,Object obj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
       if(obj == null){
           return null;
       }
       String warpperName = modelName+"Wrapper";
       Class<? extends BaseWrapper> wrapper = BeanUtils.findWrapperClass(warpperName);//查找是否有装饰类
       if(wrapper == null){
           return obj;
       }
       //判断 object 类型
       if(obj instanceof List &&!((List<?>)obj).isEmpty() ){
          Constructor<? extends BaseWrapper> c=wrapper.getConstructor(List.class);//获取有参构造
          if(((List<?>)obj).get(0) instanceof BaseEntity){//list中的类型是 entity
              BaseWrapper baseWrapper = (BaseWrapper)c.newInstance(BeanUtils.objectsToMaps((List<?>)obj));//通过有参构造创建对象
              return baseWrapper.wrap();
          }
          if(((List<?>)obj).get(0) instanceof Map){//list中的类型是map
               BaseWrapper baseWrapper = (BaseWrapper)c.newInstance((List<?>)obj);//通过有参构造创建对象
               return baseWrapper.wrap();
          }
       }
       if(obj instanceof BaseEntity){
           Constructor<? extends BaseWrapper> c=wrapper.getConstructor(Map.class);//获取有参构造map类型
           BaseWrapper baseWrapper = (BaseWrapper)c.newInstance(BeanUtils.beanToMap(obj));//bean转为map后再构造装饰类
           return baseWrapper.wrap();
       }
       
       if(obj instanceof Map){
           Constructor<? extends BaseWrapper> c=wrapper.getConstructor(Map.class);//获取有参构
           BaseWrapper baseWrapper = (BaseWrapper)c.newInstance(obj);//通过有参构造创建对象
           return baseWrapper.wrap();
       }
       
       if(obj instanceof PageEntity){
           Constructor<? extends BaseWrapper> c=wrapper.getConstructor(PageEntity.class);//获取有参构造
           PageEntity page = (PageEntity)obj;
           List<?> datas = page.getDataList();
           if(!datas.isEmpty()){
              if (datas.get(0) instanceof Map){
                  BaseWrapper baseWrapper = (BaseWrapper)c.newInstance(page);//通过有参构造创建对象
                  return baseWrapper.wrap();
              }
              if (datas.get(0) instanceof BaseEntity){
                  datas = BeanUtils.objectsToMaps(datas);
                  page.setDataList(datas);
                  BaseWrapper baseWrapper = (BaseWrapper)c.newInstance(page);//通过有参构造创建对象
                  return baseWrapper.wrap();
              }
           }
       }
       return obj;  
    }
    
    /**
     * 打印controller层调用日志
     * @param fullServiceMethodName
     */
    private void doLog(String fullServiceMethodName){
        LOG.debug("### current comstomer method [ {} ] has dealed ###", fullServiceMethodName);
        //..保存用户操作记录到数据库
    }
   
    
   
}