package com.cum3.yilifang.framework.web.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import com.cum3.yilifang.framework.common.annotations.TreeId;
import com.cum3.yilifang.framework.common.annotations.TreeParentId;
import com.cum3.yilifang.framework.common.annotations.TreeSort;
import com.cum3.yilifang.framework.common.annotations.TreeText;
import com.cum3.yilifang.framework.common.exception.SysException;
import com.cum3.yilifang.framework.common.support.StrFormatter;
import com.cum3.yilifang.framework.common.utils.StringUtils;
import com.cum3.yilifang.framework.common.utils.ToolReflect;
import com.cum3.yilifang.framework.common.utils.TreeUtil;
import com.cum3.yilifang.framework.common.utils.sql.SqlUtils;
import com.cum3.yilifang.framework.web.domain.TreeEntity;
import com.google.common.collect.Lists;

import tk.mybatis.mapper.entity.Example;

/**
 * 树 基础服务类 重写BaseService的 crud 进行树的内码和层级的维护
 * 
 * @author Fandy Liu
 * @created 2018年9月2日 上午9:15:31
 */
public abstract class TreeService<M extends TreeEntity<M>> extends BaseService<M> {

    protected String treeIdColumn;// 树Id 数据库列名
    protected String treeIdProperty;// javabean 属性名称
    protected String parentIdColumn; // parentId 的数据库列名
    protected String parentIdProperty;// parentId javabean 属性名
    protected String treeTextColumn;// 树名称 数据库列名
    protected String treeTextProperty;// 树名称 javabean 属性名
    protected String treeSortColumn;// 树排序 数据库列名
    protected String treeSortProperty;// 树排序 javabean 属性名

    /**
     * 构造函数
     */
    public TreeService() {
        super();//调用父类构造方法
        for (Field field :this.modelClass.getDeclaredFields()) {// 获取所有属性
            // treeId的数据库列名
            TreeId treeId = field.getAnnotation(TreeId.class);
            if (treeId != null) {
                this.treeIdProperty = field.getName();
                Column column = field.getAnnotation(Column.class);
                if (StringUtils.isNotNull(column) && column.name() != null) {
                    this.treeIdColumn = column.name();
                } else {
                    this.treeIdColumn = field.getName();
                }
            }
            // parenId 列名设置
            TreeParentId parentId = field.getAnnotation(TreeParentId.class);
            if (parentId != null) {
                this.parentIdProperty = field.getName();
                Column column = field.getAnnotation(Column.class);
                if (column != null && StringUtils.isNotNull(column.name())) {
                    this.parentIdColumn = column.name();
                } else {
                    this.parentIdColumn = field.getName();
                }
            }
            // treeText 列名设置
            TreeText treeText = field.getAnnotation(TreeText.class);
            if (treeText != null) {
                this.treeTextProperty = field.getName();
                Column column = field.getAnnotation(Column.class);
                if (column != null && StringUtils.isNotNull(column.name())) {
                    this.treeTextColumn = column.name();
                } else {
                    this.treeTextColumn = field.getName();
                }
            }
            // treeSort 列名设置
            TreeSort treeSort = field.getAnnotation(TreeSort.class);
            if (treeSort != null) {
                this.treeSortProperty = field.getName();
                Column column = field.getAnnotation(Column.class);
                if (column != null && StringUtils.isNotNull(column.name())) {
                    this.treeSortColumn = column.name();
                } else {
                    this.treeSortColumn = field.getName();
                }
            }
        }
    }

    /**
     * 描述 获取继承子类的表名称
     * 
     * @author Fandy Liu
     * @created 2018年9月2日 上午2:47:34
     * @return
     */
    private String getTableName() {
        Table anTb = modelClass.getAnnotation(Table.class);
        if (anTb == null) {
            throw (new SysException("通用树TreeService", "继承的实体类没有配置@Table标签！"));
        }
        return anTb.name();
    }

    /**
     * 新增
     * 
     * @param item
     *            对象
     * @return
     */
    public Integer save(M item) {
        // 获取父亲
        Map<String, Object> parent = getParent(item);
        // 设置树型结构相关数据
        if (parent == null) {
            item.setIsn(UUID.randomUUID().toString().replace("-", ""));
        } else {
            item.setIsn(parent.get("isn").toString() + "." + UUID.randomUUID().toString().replace("-", ""));
        }
        item.setRank(TreeUtil.getIsnRank(item.getIsn()));
        item.setIsleaf("1");
        // 新增
        int ret = super.save(item);
        // 更新父节点
        setParentToBranch(item);
        return ret;
    }

    /**
     * 修改
     * 
     * @param item
     *            对象
     * @return
     */
    public Integer update(M item) {
        int ret = updateSelective(item);
        M temp = mapper.selectOne(item);
        // 获取父亲
        Map<String, Object> parent = getParent(temp);
        // 设置树型结构相关数据
        if (parent == null) {
            temp.setIsn(UUID.randomUUID().toString().replace("-", ""));
        } else {
            temp.setIsn(parent.get("isn").toString() + "." + UUID.randomUUID().toString().replace("-", ""));
        }
        temp.setRank(TreeUtil.getIsnRank(item.getIsn()));
        updateSelective(temp);
        return ret;
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @Transactional
    public Integer deleteById(Object id) {
        String tableName = getTableName();
        String findSql = StrFormatter.format(" SELECT {}, isn, parentId FROM {}  WHERE {} = {}",treeIdColumn, tableName,treeIdColumn,
                SqlUtils.toSqlSearchVal(id.toString()));
        List<Map<String, Object>> lst = sqlMapper.selectList(findSql);
        // 如果不存在删除对象，则退出
        if (lst.size() == 0) {
            return 0;
        }
        // 删除本级及所有下级
        Map<String, Object> item = lst.get(0);
        String delSql = StrFormatter.format("DELETE FROM {} WHERE isn = '{}' OR isn LIKE {} ", getTableName(),item.get("isn"),
                "concat('" + item.get("isn").toString() + "','%')");
        int ret = sqlMapper.delete(delSql);
        // 更新父节点
        setParentToLeaf(item);
        return ret;
    }

    /**
     * 获取父亲
     * 
     * @param item
     *            对象
     * @return
     */
    public Map<String, Object> getParent(M item) {
        // 如果没有父亲，则返回空
        String parentId = ToolReflect.on(item).call("get" + StringUtils.convertToCamelCase(this.parentIdProperty))
                .get();
        if (StringUtils.isEmpty(parentId)) {
            return null;
        }
        // 获取父亲
        String sql = StrFormatter.format("SELECT {}, isn FROM {} WHERE {} = {}  ", this.treeIdColumn, getTableName(), this.treeIdColumn,
                SqlUtils.toSqlSearchVal(parentId));
        List<Map<String, Object>> lst = sqlMapper.selectList(sql);
        return lst.size() > 0 ? lst.get(0) : null;
    }

    /**
     * 修改指定节点的父节点为非最细级
     * 
     * @param item
     *            对象
     */
    public void setParentToBranch(M item) {
        // 如果存在父节点，则更新父节点为非最细级
        String parentId = ToolReflect.on(item).call("get" + StringUtils.convertToCamelCase(this.parentIdProperty))
                .get();
        if (StringUtils.isNotNull(parentId)) {
            String sql = StrFormatter.format("UPDATE {} SET isLeaf = {} WHERE {} = '{}'", getTableName(), "0",
                    this.treeIdColumn, parentId);
            sqlMapper.update(sql);
        }
    }

    /**
     * 修改指定节点的父节点为最细级
     * 
     * @param item
     *            对象
     */
    public void setParentToLeaf(Map<String, Object> item) {
        // 如果存在父节点，则当父节点没有子节点时更新父节点为最细级
        if (StringUtils.isNotNull(item.get(this.parentIdColumn))) {
            String parentId = item.get(this.parentIdColumn).toString();
            String tableName = getTableName();
            String updateTplSql = "UPDATE {} SET isLeaf = '{}' WHERE {} = '{}' AND NOT EXISTS (SELECT 1 FROM (SELECT 1 AS tmp_field FROM {}  WHERE {} = '{}') tmp) ";
            String retSql = StrFormatter.format(updateTplSql, tableName,"1", this.treeIdColumn, parentId, tableName,
                    this.parentIdColumn, parentId);
            sqlMapper.update(retSql);
        }
    }

    /**
     * 描述 同步树 一次取出所有
     * 
     * @param item
     * @return
     */
    public List<M> synTree() {
        List<M> allNodes = Lists.newArrayList();
        if (StringUtils.isNull(this.treeSortColumn)) {
            allNodes = queryAll();
        } else {// 按顺序排序
            Example example = new Example(this.modelClass);
            example.orderBy(this.treeSortProperty).asc();// 按顺序排列
            allNodes = mapper.selectByExample(example);
        }
        Map<String, M> map = new HashMap<>();
        // 把所对应每个节点的放到map中,索引为自身的id
        for (M node : allNodes) {
            String id = ToolReflect.on(node).call("get" + StringUtils.convertToCamelCase(this.treeIdProperty)).get();
            map.put(id, node);
        }
        List<M> list = Lists.newArrayList();
        // 再遍历所有的节点集合,确定父子关系
        for (M node : allNodes) {
            String parentId = ToolReflect.on(node).call("get" + StringUtils.convertToCamelCase(this.parentIdProperty)).get();
            String id = ToolReflect.on(node).call("get" + StringUtils.convertToCamelCase(this.treeIdProperty)).get();
            if (StringUtils.isNotEmpty(parentId)) { // 有父亲
                // 取出父子
                M cNode = (M) map.get(id);
                M pNode = (M) map.get(parentId);
                if (pNode != null) {
                    pNode.getChildren().add(cNode);
                }
            } else {
                list.add((M) map.get(id));
            }
        }
        // System.out.println(JSONUtils.toJSONString(list, true));
        return list;
    }

    /**
     * 异步树
     * 
     * @author Fandy Liu
     * @created 2018年9月4日 上午2:00:08
     * @return
     */
    public Object asyTree(Object pid) {
        Example example = new Example(this.modelClass);
        if (StringUtils.isNotNull(this.treeSortColumn)) {
            example.orderBy(this.treeSortProperty).asc();// 按顺序排列
        }
        if (StringUtils.isNull(pid)) {
            example.createCriteria().andEqualTo(this.parentIdProperty, "");// 父节点等于空
        } else {
            example.createCriteria().andEqualTo(this.parentIdProperty, pid);// 父节点等于空
        }
        return mapper.selectByExample(example);
    }

}
