package com.cum3.yilifang.framework.web.controller;

import java.util.List;

import com.cum3.yilifang.framework.common.utils.JSONUtils;
import com.cum3.yilifang.framework.common.utils.ServletUtils;
import com.cum3.yilifang.framework.web.domain.Media;
import com.google.common.base.Preconditions;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class PassParamsUtils {
    /**
     * 前端传递的json对象
     */
    private final String passJsonStr;
    /**passdata对象json路径**/
    private  static final String PATH_MODEL_PRE_NAME = "$.passdata";
    /**entity对象json路径**/
    private  static final String PATH_MODEL_PRE_ENTITY = "$.passdata.entity";
    /**图片对象json路径**/
    private static final String PATH_MODEL_PICTURES = "$.passdata.oPictures";
    /**视频对象json路径**/
    private static final String PATH_MODEL_VIDEOS = "$.passdata.oVideos";
    
    private static final String PATH_MODEL_VOICE = "$.passdata.oVoices";
    
    public static PassParamsUtils on(String passJsonStr){
        return new PassParamsUtils(passJsonStr);
    }
    /**
     * 构造函数
     * @param passJsonStr
     */
    private PassParamsUtils(String passJsonStr){
        this.passJsonStr = passJsonStr;
    }
    /**
     * 
     * 获取passdata中的参数
     * @return
     */
    public  String passdataJson(){
        ReadContext context = JsonPath.parse(passJsonStr);
        return JSONUtils.toJSONString(context.read(PATH_MODEL_PRE_NAME), false);
    }
    /**
     * 获取实体类jonson对象
     * @return
     */
    public String entityJson(){
        try {
            ReadContext context = JsonPath.parse(passJsonStr);
            return JSONUtils.toJSONString(context.read(PATH_MODEL_PRE_ENTITY), false); 
        } catch (Exception e) {
            return null;
        }
       
    }
    /**
     * 获取传递的实体对象
     * @param clazz
     * @return
     */
    public  final <T> T entity( Class<T> clazz){
        return (T)JSONUtils.getObject(entityJson()==null?"{}":entityJson(), clazz);
    }
    /**
     * 
     * 获取entity列表
     * @return
     */
    public <T> List<T> listEntity(Class<T> clazz){
        return JSONUtils.getList(entityJson(), clazz);
    } 
    /**
     * openId的值
     * @return
     */
    public String openId(){
        String ret = JSONUtils.parseString(passJsonStr, PassParams.OPENID.getName());
       // Preconditions.checkNotNull(ret, "openId 不能为空!");
        return  ret;
    }
    /**
     * comcode的值
     * @return
     */
    public String comCode(){
        String ret =null;
        try {
            ret = JSONUtils.parseString(passJsonStr, PassParams.COMCODE.getName());
        } catch (Exception e) {
            ret = ServletUtils.getParameter(PassParams.COMCODE.getName());
        }
        //Preconditions.checkNotNull(ret, "comCode 不能为空!");
        return ret;
    }
    /**
     * 
     * pkvalue的值
     * @return
     */
    public String pkValue(){
        String ret = JSONUtils.parseString(passdataJson(),PassParams.PKVALUE.getName());
        return ret;
    }
    /**
     * 
     * pkValues的值
     * @return
     */
    public List<String> pkValues(){
        List<String> lt = JSONUtils.parseStringList(passdataJson(),PassParams.PKVALUES.getName());
        Preconditions.checkNotNull(lt,"pkValues 不能为空!");
        return lt;
    }
    /**
     * 父节点pid的值
     * @return
     */
    public String pid(){
        String ret = JSONUtils.parseString(passdataJson(),PassParams.PID.getName());
        Preconditions.checkNotNull(ret,"pkValues 不能为空!");
        return ret;
    }
    /**
     * 
     * sqlId的值
     * @return
     */
    public String sqlId(){
        String ret = JSONUtils.parseString(passdataJson(),PassParams.SQLID.getName());
        Preconditions.checkNotNull(ret,"sqlId 不能为空!");
        return ret; 
    }
    /**
     * 获取Params的值
     * @return
     */
    public List<String> params(){
        List<String> params = JSONUtils.parseStringList(passdataJson(),PassParams.PARAMS.getName());
       // Preconditions.checkNotNull(params,"params 不能为空!");
        return params;
    }
    /**
     * 获取页数
     * @return
     */
    public Integer page(){
        Integer page = JSONUtils.parseInteger(passdataJson(),PassParams.PAGE.getName()); 
        if(page == null){
            page = (start()/limit())+1;
        }
        return page;
    }
    
    /**
     * 获取每页条数
     * @return
     */
    public Integer pageSize(){
        Integer pageSize = JSONUtils.parseInteger(passdataJson(),PassParams.ROWS.getName()); 
        if(pageSize == null){
            pageSize =limit();
        }
        Preconditions.checkNotNull(pageSize, "每页显示条数ROWS不能为空!");
        return pageSize;
    }
    /**
     * 
     * start 开始索引
     * @return
     */
    public Integer start(){
        Integer start = JSONUtils.parseInteger(passdataJson(),PassParams.START.getName()); 
        Preconditions.checkNotNull(start, "开始索引start不能为空!");
        return start;
    }
    /**
     * 
     * limit
     * @return
     */
    public Integer limit(){
        Integer limit = JSONUtils.parseInteger(passdataJson(),PassParams.LIMIT.getName()); 
        Preconditions.checkNotNull(limit, "limit不能为空!");
        return limit;
    }
    /**
     * 按哪个字段排序
     * @return
     */
    public String sortBy(){
        return JSONUtils.parseString(passdataJson(),  PassParams.SORTBY.getName()); 
    }
    /**
     * 排序方式  asc 或 desc
     * @return
     */
    public String sortType(){
        return JSONUtils.parseString(passdataJson(),  PassParams.SORTTYPE.getName()); 
    }
    /**
     * 获取级联的model名称 list
     * @return
     */
    public  List<String> casEntitys(){
        List<String> params = JSONUtils.parseStringList(passdataJson(),PassParams.CASENTITYS.getName());
        // Preconditions.checkNotNull(params,"params 不能为空!");
        return params;
    }
    /**
     * 获取级联外键的list
     * @return
     */
    public List<String> casFkColumns(){
        List<String> params = JSONUtils.parseStringList(passdataJson(),PassParams.CASFKCOLUMNS.getName());
        // Preconditions.checkNotNull(params,"params 不能为空!");
        return params;
    }
    /**
     * 图片参数值
     * @author Fandy Liu
     * @created 2018年9月26日 上午12:31:17
     * @return
     */
    public List<Media> oPictures(){
        ReadContext context = JsonPath.parse(passJsonStr);
        try {
            String picJson = JSONUtils.toJSONString(context.read(PATH_MODEL_PICTURES), false);
            return JSONUtils.getList(picJson, Media.class);
        } catch (Exception e) {
           return null;
        }
    }
    /**
     * 
     * 视频参数值
     * @author Fandy Liu
     * @created 2018年9月26日 上午12:31:40
     * @return
     */
    public List<Media> oVideos(){
        try {
            ReadContext context = JsonPath.parse(passJsonStr);
            String picJson = JSONUtils.toJSONString(context.read(PATH_MODEL_VIDEOS), false);
            return JSONUtils.getList(picJson, Media.class);
        } catch (Exception e) {
            return null;
        } 
       
       
    }
    
    /**
     * 
     * 声音参数值
     * @author Fandy Liu
     * @created 2018年9月26日 上午12:31:40
     * @return
     */
    public List<Media> oVoices(){
        try {
            ReadContext context = JsonPath.parse(passJsonStr);
            String picJson = JSONUtils.toJSONString(context.read(PATH_MODEL_VOICE), false);
            return JSONUtils.getList(picJson, Media.class);
        } catch (Exception e) {
            return null;
        } 
    }
}
