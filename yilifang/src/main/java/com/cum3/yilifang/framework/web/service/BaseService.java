package com.cum3.yilifang.framework.web.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cum3.yilifang.framework.api.oss.CloudStorageService;
import com.cum3.yilifang.framework.common.annotations.ComCode;
import com.cum3.yilifang.framework.common.annotations.CreateTime;
import com.cum3.yilifang.framework.common.annotations.DelFlag;
import com.cum3.yilifang.framework.common.annotations.Pictures;
import com.cum3.yilifang.framework.common.annotations.UpdateTime;
import com.cum3.yilifang.framework.common.annotations.Videos;
import com.cum3.yilifang.framework.common.annotations.Voices;
import com.cum3.yilifang.framework.common.exception.ExceptionUtils;
import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.BeanUtils;
import com.cum3.yilifang.framework.common.utils.ServletUtils;
import com.cum3.yilifang.framework.common.utils.StringUtils;
import com.cum3.yilifang.framework.common.utils.ToolReflect;
import com.cum3.yilifang.framework.common.utils.ToolSqlTplXml;
import com.cum3.yilifang.framework.common.utils.sql.SqlFilter;
import com.cum3.yilifang.framework.common.utils.sql.SqlUtils;
import com.cum3.yilifang.framework.web.controller.PassParamsUtils;
import com.cum3.yilifang.framework.web.domain.BaseEntity;
import com.cum3.yilifang.framework.web.domain.Media;
import com.cum3.yilifang.framework.web.domain.PageEntity;
import com.cum3.yilifang.framework.web.mapper.BaseMapper;
import com.cum3.yilifang.framework.web.mapper.SqlMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

public abstract class BaseService<T extends BaseEntity<T>> {
    /**通用mapper**/
    @Autowired
    protected BaseMapper<T> mapper;
    /**执行sql的mapper 子类可直接调用**/
    @Autowired
    protected SqlMapper sqlMapper;
    @Autowired
    protected CloudStorageService cloudStorageService;
    /**
     * Model类型
     * @author Fandylau
     */
    protected Class<T> modelClass;
    /**
     * 绑定的bean是否含有多媒体文件注解
     */
    protected boolean isContainMediaAnnotation = false;
    
    /**
     * 有伪删除标记的列名
     */
    protected String pseudoDelColumn;

    /**
     * 构造函数
     * @author Fandylau
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BaseService() {
        //初始化参数工具
        //param = PassParamsUtils.on(ServletUtils.getRequestParameterStr());
        // 获取Model类型
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            modelClass = (Class) params[0];
            this.isContainMediaAnnotation = isContainMediaAnnotation();
            this.pseudoDelColumn = findPseudoDelColumn();//查找bean中有伪删除标记的属性名
        }
    }
    /**
     * 
     * 查找伪删除的列名
     * @return
     */
    private String findPseudoDelColumn(){
        for (Field field :this.modelClass.getDeclaredFields()) {
            DelFlag delFlag = field.getAnnotation(DelFlag.class);
            if (delFlag != null ) {
                return field.getName();
            }
        }
        return null;
    }
    
    /**
     * 描述 检测类中是否含有附件注解
     * @return
     */
    private boolean isContainMediaAnnotation(){
        for (Field field :this.modelClass.getDeclaredFields()) {
            if (field.getAnnotation(Pictures.class) != null 
                    || field.getAnnotation(Videos.class) !=null ||field.getAnnotation(Voices.class) !=null) {
                return true;
            }
        }
        return false;
    }
   
    /**
     * 获取传递参数对象
     * @author Fandy Liu
     * @created 2018年9月25日 上午3:38:35
     * @return
     */
    protected PassParamsUtils params(){
        return PassParamsUtils.on(ServletUtils.getRequestParameterStr());
    }
    /**
     * 获取分页实体
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    protected PageEntity getPageEntity(@SuppressWarnings("rawtypes") List list){
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        PageEntity pageEntity = new PageEntity();
        pageEntity.setDataList(list);
        pageEntity.setLimit(pageInfo.getEndRow());
        pageEntity.setStart(pageInfo.getStartRow());
        pageEntity.setPageCount(pageInfo.getPageNum());
        pageEntity.setTotalSize(pageInfo.getTotal());
        return pageEntity;
    }
    /**
     * 
     * 描述 获取sqlxml的命名空间
     * @author Fandy Liu
     * @created 2018年9月15日 下午3:48:07
     * @return
     */
    protected String getSqlXmlNameSpace(){
        
       return  getClass().getName();
        
    }
    
    /**
     * 根据id查询数据
     * 
     * @param id
     * @return
     */
    public T queryById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     * 
     * @return
     */
    public List<T> queryAll() {
        return mapper.select(null);
    }

    /**
     * 根据条件查询一条数据，如果有多条数据会抛出异常
     * 
     * @param record
     * @return
     */
    public T queryOne(T record) {
        if(this.pseudoDelColumn != null){//有伪删除列 执行更新 
            ToolReflect reflect = ToolReflect.on(record);
            reflect.call("set"+StringUtils.convertToCamelCase(pseudoDelColumn), 0);
        }
        return mapper.selectOne(record);
    }

    

    /**
     * 根据条件查询数据列表
     * 
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record) {
       Example example = new Example(record.getClass());
       if(params().entityJson() !=null){
           ToolReflect reflect = ToolReflect.on(record);
           Criteria criteria = example.createCriteria();
           for(EntityColumn column :  EntityHelper.getColumns(this.modelClass)){
               String columnName = column.getEntityField().getName();
               Object columValue = reflect.call("get"+StringUtils.convertToCamelCase(columnName)).get();
               if(columValue!=null){
                   criteria.andEqualTo(columnName, columValue);
               }
           }
        }
        new SqlFilter(params().passdataJson(),example);//拼接多条件查询语句
        if(this.pseudoDelColumn != null){
           example.createCriteria().andEqualTo(pseudoDelColumn, 0);
        }
        if(params().sortType()!=null&&params().sortBy()!=null){
            if(params().sortType().toString().toLowerCase().equals("asc")){
                example.orderBy(params().sortBy().toString()).asc();
            }else{
                example.orderBy(params().sortBy().toString()).desc();
            }
        }
        if(this.pseudoDelColumn != null){//有伪删除标志
            Criteria criteria =example.createCriteria();
            criteria.andEqualTo(pseudoDelColumn, 0);
            example.and(criteria);
        }
        return  mapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param record
     * @return
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public PageEntity queryPageListByWhere(Integer page, Integer rows,T record) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        return getPageEntity(queryListByWhere(record));
    }
    
    /**
     * 分页查询 返回Map
     * @param page
     * @param rows
     * @param record
     * @return
     */
    public PageEntity queryPageListBySqlFilter(Integer page, Integer rows,SqlFilter sqlFilter,String sqlId) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        List<Map<String,Object>> list = queryListBySqlFilter(sqlFilter,sqlId);
        return this.getPageEntity(list);
    }
    /**
     * 描述 根据sqlFilter返回查询结果List<Map<String,Object>>
     * @param sqlFilter
     * @param sqlTemplateId
     * @return
     */
    public List<Map<String,Object>> queryListBySqlFilter(SqlFilter sqlFilter,String sqlId){
        String sql = ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+sqlId)+sqlFilter.getWhereAndOrderSql();
        sql = StrFormatter.format(sql, sqlFilter.getParams().toArray()); 
        return sqlMapper.selectList(sql);
    }
    /**
     * 根据sql模板id返回list<map>
     * @return
     */
    public List<Map<String,Object>> queryListBySqlTemplate(String sqlId,Object... params){
        String  sql =ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+sqlId);
        if(params!=null&&params.length>0){
            sql =  StrFormatter.format(sql,params); 
        }
        return sqlMapper.selectList(sql);
    }
    /**
     * 描述 根据sqlFilter返回查询结果Map<String,Object>
     * @param sqlFilter
     * @param sqlTemplateId
     * @return
     */
    public Map<String,Object> selectOneBySqlFilter(SqlFilter sqlFilter,String sqlId){
        String sql = ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+sqlId)+sqlFilter.getWhereAndOrderSql();
        sql = StrFormatter.format(sql, sqlFilter.getParams().toArray()); 
        return sqlMapper.selectOne(sql);
    }
    /**
     * 根据sqlId查询一条记录
     * @return
     */
    public Map<String,Object> selectOneBySqlId(String sqlId,Object... params){
        String  sql =ToolSqlTplXml.getSql(getSqlXmlNameSpace()+"."+sqlId);
        if(params!=null&&params.length>0){
            sql =  StringUtils.format(sql,params); 
        }
        return sqlMapper.selectOne(sql);
    }
    /**
     * 新增数据，返回成功的条数
     * 
     * @param record
     * @return
     */
    public Integer save(T record) {
        setCreateTime(record);//设置创建时间
        setUpateTime(record);//设置更新时间
        setComcode(record);//设置comCode的值
        setDelFlag(record);//设置未删除列的值为 0->有效
        setMedia(record);//设置多媒体文件的url地址
        return mapper.insert(record);
    }
    
    /**
     * 
     * 多附件带表单参数提交保存
     * @author Fandy Liu
     * @created 2018年10月5日 下午5:40:31
     * @return
     * @throws Exception 
     */
    @Transactional
    public Object saveByMutipart(Object file) throws Exception {
        T record = ServletUtils.getRequestBean(this.modelClass);
        setComcode(record);
        Object pkValue = BeanUtils.getPkValue(record);
        String pkColumn = BeanUtils.getPkColumn(this.modelClass);
        String filePath = "";
        String uploadedUrls = "";
        if ((file != null) && (((MultipartFile) file).getBytes().length > 0)) {
            Integer isVideo = ServletUtils.getParameterToInt("isVideo", Integer.valueOf(0));
            if (isVideo.intValue() == 1){
                filePath = cloudStorageService.uploadVideo((MultipartFile) file, 1);
            }else{
                filePath = cloudStorageService.upload((MultipartFile) file, 1); 
            }
            uploadedUrls = setBeanAttachField(record, filePath);
        }
        if ((pkValue == null) || (StringUtils.isEmpty((String) pkValue))) {
            setCreateTime(record);
            mapper.insert(record);
            pkValue = BeanUtils.getPkValue(record);
        } else {
            mapper.updateByPrimaryKeySelective(record);
        }
        return ImmutableMap.of("pkColumn", pkColumn, "pkValue", pkValue, "uploadedUrls", uploadedUrls);
    }
    
    private String setBeanAttachField(T record, String uploadPath) {
        String attField = ServletUtils.getParameter("attField");
        if (StringUtils.isEmpty(attField)){
            throw ExceptionUtils.exp("attField的值为空！");
        }
        ToolReflect ref = ToolReflect.on(record);
        String recordPath = (String) ref.call("get" + StringUtils.convertToCamelCase(attField)).get();
        if (StringUtils.isEmpty(recordPath)) {
            ref.call("set" + StringUtils.convertToCamelCase(attField), new Object[] { uploadPath });
        } else {
            recordPath = recordPath + "," + uploadPath;
            ref.call("set" + StringUtils.convertToCamelCase(attField), new Object[] { recordPath });
        }
        return ((String) ref.call("get" + StringUtils.convertToCamelCase(attField)).get());
    }
   
   
    
    /**
     * 
     * 多附件带表单参数提交保存
     * @author Fandy Liu
     * @created 2018年10月5日 下午5:40:31
     * @return
     * @throws Exception 
     */
    /*@Transactional
    public Object saveByMutipart(Object file) throws Exception{
        T record = ServletUtils.getRequestBean(modelClass);
        setComcode(record);
        Object pkValue = BeanUtils.getPkValue(record);
        String pkColumn = BeanUtils.getPkColumn(modelClass);
        String attField = ServletUtils.getParameter("attField");
        String signal =  ServletUtils.getParameter("signal");//开始上传的信号 upload
        if(StringUtils.isEmpty(attField)){
            throw ExceptionUtils.exp("attField的值为空！");
        }
        if(pkValue==null || StringUtils.isEmpty((String)pkValue)){//先插入数据
            mapper.insert(record);
            pkValue = BeanUtils.getPkValue(record);
        } 
        if(file !=null&&((MultipartFile)file).getBytes().length>0){//存在附件 
            UploadFileCache.getInstance().add((String)pkValue, attField, (MultipartFile)file);
        }
        StringBuffer uploadedPath = new StringBuffer();
        ToolReflect ref = ToolReflect.on(record);
        if(StringUtils.isNotEmpty(signal)&&"upload".equals(signal)){//开始上传附件
            Map<String,List<MultipartFile>> map =  UploadFileCache.getInstance().getTempFilePool().get((String)pkValue);
            if(map !=null){
                for(String filed :map.keySet()){
                    uploadedPath.append(",");
                    try {
                        String okPath = cloudStorageService.upload(map.get(filed), CloudStorageService.ATT_DB_EFFECT);//上传一批
                        uploadedPath.append(okPath);
                        ref.call("set"+StringUtils.convertToCamelCase(filed),okPath);
                        mapper.updateByPrimaryKeySelective(record);
                    } catch (SystemException e) {
                        cloudStorageService.delete( uploadedPath.deleteCharAt(0).toString().split(","));
                        UploadFileCache.getInstance().remove(pkValue);//清除附件缓存
                        mapper.delete(record);//删除入库记录
                        ExceptionUtils.exp("附件上传失败了，请稍后重试！");
                    }
                    
                }
                //清除本次上传附件的缓存
                UploadFileCache.getInstance().remove(pkValue);
            }
        }
        String uploadedUrls =  ref.call("get"+StringUtils.convertToCamelCase(attField)).get();
        return ImmutableMap.of("pkColumn",pkColumn,"pkValue",pkValue, "uploadedUrls", uploadedUrls==null?"":uploadedUrls);
    }*/
   
    /**
     * 新增或更新
     * @param entity
     * @return
     * @throws Exception 
     */
    @Transactional
    public boolean saveOrUpdate(T record) throws Exception {
        if (null != record) {
            Object idVal  =BeanUtils.getPkValue(record);
            if (StringUtils.isNull(idVal)||StringUtils.isEmpty((String)idVal)) {
                return save(record)>0;
            } else {
                return updateSelective(record)>0;
            }
        }
        return false;
    }
    /**
     * 批量插入
     * @param T
     * @return
     * @throws Exception 
     */
    @Transactional
    public Integer batchInsert(List<T> list) throws Exception{
        //return mapper.insertList(list); //这个方法不行 暂时循环插入 
        for(T record:list){
            save(record);
        }
        return list.size();
    }
    
    /**
     * 批量 新增或插入List
     * @param entityList
     * @param batchSize
     * @return
     */
    @Transactional
    public boolean saveOrUpdateList(List<T> entityList) {
        if (StringUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try{
            for (T anEntity : entityList) {
                saveOrUpdate(anEntity);
            }
        } catch (Throwable e) {
            throw ExceptionUtils.exp("Error: Cannot execute saveOrUpdateBatch Method. Cause",e);
        }
        return true;
    }

    /**
     * 新增数据，使用不为null的字段，返回成功的条数
     * 
     * @param record
     * @return
     */
    @Transactional
    public Integer saveSelective(T record) {
        setCreateTime(record);
        setComcode(record);
        setMedia( record);
        return mapper.insertSelective(record);
    }

    /**
     * 修改数据，返回成功的条数
     * 
     * @param record
     * @return
     */
    public Integer update(T record) {
        setUpateTime(record);
        setMedia( record);
        return mapper.updateByPrimaryKey(record);
    }

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     * 
     * @param record
     * @return
     */
    @Transactional
    public Integer updateSelective(T record) {
        setUpateTime(record);
        setMedia( record);
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据id删除数据
     * 
     * @param id
     * @return
     * @throws Exception 
     */
    @Transactional
    public Integer deleteById(Object id) throws Exception {
        if(this.pseudoDelColumn != null){//伪删除
           T record = mapper.selectByPrimaryKey(id);
           ToolReflect reflect = ToolReflect.on(record);
           reflect.call("set"+StringUtils.convertToCamelCase(pseudoDelColumn), 1);
           return mapper.updateByPrimaryKeySelective(record);
        }
        
        if(isContainMediaAnnotation)//包含附件标签 先删除云服务器上的附件
           deleteAttachByRecord(mapper.selectByPrimaryKey(id));
        return  mapper.deleteByPrimaryKey(id);//物理删除
    }

    /**
     * 批量删除
     * @param clazz
     * @param property
     * @param values
     * @return
     * @throws Exception 
     */
    @Transactional
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) throws Exception {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
        if(this.pseudoDelColumn != null){//有伪删除列 执行更新 
            ToolReflect reflect = ToolReflect.on(modelClass.newInstance());
            reflect.call("set"+StringUtils.convertToCamelCase(pseudoDelColumn), 1);
            return mapper.updateByExampleSelective(reflect.get(), example);
        }
        if(isContainMediaAnnotation){//bean中包含附件标签
            List<T>  delDatas = mapper.selectByExample(example);
            for(T record:delDatas){
                deleteAttachByRecord(record);//删除附件
            }
        }
        return mapper.deleteByExample(example);
    }

    /**
     * 根据条件做删除
     * 
     * @param record
     * @return
     * @throws Exception 
     */
    @Transactional
    public Integer deleteByWhere(T record) throws Exception {
        if(this.pseudoDelColumn != null){//有伪删除列 执行更新 
            ToolReflect reflect = ToolReflect.on(record);
            reflect.call("set"+StringUtils.convertToCamelCase(pseudoDelColumn), 1);//设置伪删除的值
            return mapper.updateByPrimaryKeySelective(record);
        }
        
        if(isContainMediaAnnotation){//bean中包含附件标签
            List<T>  delDatas = mapper.select(record);
            for(T r:delDatas){
                deleteAttachByRecord(r);//删除附件
            }
        }
        return mapper.delete(record);
    }
    /**
     * 级联删除
     * @return
     * @throws Exception 
     */
    @Transactional
    public boolean deleteCascade() throws Exception{
        List<String> casEntitys = params().casEntitys();//要级联删除的entity名字 
        List<String> casFkColumns = params().casFkColumns();//级联表的外键名称
        String pkValue = params().pkValue();//主键值
        for(int i = 0;i<casEntitys.size();i++){
            String modelName = casEntitys.get(i);
            EntityTable tableInfo = EntityHelper.getEntityTable(BeanUtils.findModelClass(modelName));//根据类名称找到class
            String tableName = tableInfo.getName();
            String fkColumn = casFkColumns.get(i);
            String delSql = StringUtils.format("delete from {} where {} = '{}'", tableName,fkColumn,pkValue);
            sqlMapper.delete(delSql);
        }
        deleteById(params().pkValue());//主表删除
        return true;
    }

    /**
     * 伪删除
     * @return
     */
    public Integer deletePseudo(T record){
       String tableName =  EntityHelper.getEntityTable(record.getClass()).getName();
       String  pseudoSql = StringUtils.format("update {} set {} = 1",tableName,this.pseudoDelColumn);
       return sqlMapper.update(pseudoSql);
    }
    
    
    /**
     * 
     * 描述
     * @param record
     */
    private void setComcode(T record){
        for (Field field :this.modelClass.getDeclaredFields()) {
            ComCode anComcode = field.getAnnotation(ComCode.class);
            if (anComcode != null ) {
                String comcode = params().comCode();
                if(StringUtils.isNotBlank(comcode)){
                    ToolReflect.on(record).call("set"+StringUtils.convertToCamelCase(field.getName()),comcode);
                }
                break;
            }
        }  
    }
    
    /**
     * 
     * 创建时间设置
     * @param record
     */
    private void setCreateTime(T record){
        for (Field field :this.modelClass.getDeclaredFields()) {
            CreateTime createTime = field.getAnnotation(CreateTime.class);
            if (createTime != null ) {
                ToolReflect.on(record).call("set"+StringUtils.convertToCamelCase(field.getName()),new Date());
               // break;
            }
        }
    }
    /**
     * 
     * 修改时间和修改人 
     * @param record
     */
    private void setUpateTime(T record){
        // 获取所有属性
        for (Field field :this.modelClass.getDeclaredFields()) {
            UpdateTime updateTime = field.getAnnotation(UpdateTime.class);
            if (updateTime != null ) {
                ToolReflect.on(record).call("set"+StringUtils.convertToCamelCase(field.getName()),new Date());
               // break;
            }
        }
    }
    
    /**
     * 
     * 设置伪删除列的值
     * @param record
     */
    private void setDelFlag(T record){
        if(this.pseudoDelColumn ==null) return ;//无伪删除的列 不执行 直接返回
        // 获取所有属性
        for (Field field :this.modelClass.getDeclaredFields()) {
            DelFlag delFlag = field.getAnnotation(DelFlag.class);
            if (delFlag != null ) {
                ToolReflect.on(record).call("set"+StringUtils.convertToCamelCase(field.getName()),0);
                break;
            }
        }
    }
    
    
    
    /**
     * 
     * 处理文件信息 图片 视频 音频，拼接url地址 同时更新数据库附件信息为有效
     * @param medias
     * @return
     */
    private String dealMedias(List<Media> medias){
        StringBuffer mediaUrls = new StringBuffer();
        for(Media m:medias){
            mediaUrls.append(",");
            mediaUrls.append(m.getUrl());
        }
        if(mediaUrls.length()>0){
            mediaUrls.deleteCharAt(0);
            String updateSql = " update sys_attachment set status = 1 where filePath in({})";
            updateSql = StringUtils.format(updateSql,  SqlUtils.toSqlSearchVal(mediaUrls.toString()));
            sqlMapper.update(updateSql);
        } 
        return  mediaUrls.toString();
    }
   
    /**
     * 
     * 设置上传附件url 地址  更新附件状态为有效
     * @param record
     */
    private void setMedia(T record){
        if(!isContainMediaAnnotation) return;
        for (Field field :this.modelClass.getDeclaredFields()) {
            Pictures pictures = field.getAnnotation(Pictures.class);
            if (pictures != null && params().oPictures() !=null) {
                String urls = dealMedias( params().oPictures());//处理图片url地址
                ToolReflect.on(record).call("set"+StringUtils.convertToCamelCase(field.getName()),urls);
            }
            Videos videos = field.getAnnotation(Videos.class);
            if (videos != null &&params().oVideos()!=null) {
                String urls = dealMedias( params().oVideos());//处理视频url地址
                ToolReflect.on(record).call("set"+StringUtils.convertToCamelCase(field.getName()),urls);
            }
            Voices voices = field.getAnnotation(Voices.class);
            if (voices != null &&params().oVoices()!=null) {
                String urls = dealMedias( params().oVoices());//处理音频url地址
                ToolReflect.on(record).call("set"+StringUtils.convertToCamelCase(field.getName()),urls);
            }
        }
    }
    /**
     * 
     * 描述 删除 bean中的所有附件
     * @author Fandy Liu
     * @created 2018年10月17日 下午3:01:56
     * @param record
     * @throws Exception
     */
    protected void deleteAttachByRecord(T record) throws Exception{
        StringBuffer urls = new StringBuffer();
        for (Field field :this.modelClass.getDeclaredFields()) {
            Pictures pictures = field.getAnnotation(Pictures.class);
            Videos videos = field.getAnnotation(Videos.class);
            Voices voices = field.getAnnotation(Voices.class);
            if(pictures!=null||videos!=null||voices!=null){
                String attachUrl = ToolReflect.on(record).call("get"+StringUtils.convertToCamelCase(field.getName())).get();
                if(StringUtils.isNotEmpty(attachUrl)){
                    urls.append(",");
                    urls.append(attachUrl);
                }
            }
        }
        if(urls.length()>0){
            urls.deleteCharAt(0);
            if(StringUtils.isNotBlank(urls)){
                cloudStorageService.delete(urls.toString().split(","));
            }
        }
    }

}
