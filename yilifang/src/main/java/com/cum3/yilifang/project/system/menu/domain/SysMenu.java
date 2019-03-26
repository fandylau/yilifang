package com.cum3.yilifang.project.system.menu.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cum3.yilifang.framework.common.annotations.TreeId;
import com.cum3.yilifang.framework.common.annotations.TreeParentId;
import com.cum3.yilifang.framework.common.annotations.TreeSort;
import com.cum3.yilifang.framework.common.annotations.TreeText;
import com.cum3.yilifang.framework.common.genid.UUIdGenId;
import com.cum3.yilifang.framework.web.domain.TreeEntity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "sys_menu")
@NameStyle(Style.normal)
public class SysMenu extends TreeEntity<SysMenu> {
  

    /**
     * 描述
     */
    private static final long serialVersionUID = 6733184673275856359L;

    /**
     * 主键
     */
    @Id
    @KeySql(genId = UUIdGenId.class)
    @TreeId
    private String id;

    /**
     * 菜单名称
     */
    @TreeText
    private String name;

    /**
     * 父菜单ID
     */
    @TreeParentId
    private String parentId;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 类型:M目录,C菜单,F按钮
     */
    private String menuType;

    /**
     * 菜单状态:0显示,1隐藏
     */
    private Integer visible;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    @TreeSort
    private Integer seq;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id
     *            主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取菜单名称
     *
     * @return name - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name
     *            菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父菜单ID
     *
     * @return parentId - 父菜单ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单ID
     *
     * @param parentId
     *            父菜单ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取请求地址
     *
     * @return url - 请求地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置请求地址
     *
     * @param url
     *            请求地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取类型:M目录,C菜单,F按钮
     *
     * @return menuType - 类型:M目录,C菜单,F按钮
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * 设置类型:M目录,C菜单,F按钮
     *
     * @param menuType
     *            类型:M目录,C菜单,F按钮
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取菜单状态:0显示,1隐藏
     *
     * @return visible - 菜单状态:0显示,1隐藏
     */
    public Integer getVisible() {
        return visible;
    }

    /**
     * 设置菜单状态:0显示,1隐藏
     *
     * @param visible
     *            菜单状态:0显示,1隐藏
     */
    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    /**
     * 获取权限标识
     *
     * @return perms - 权限标识
     */
    public String getPerms() {
        return perms;
    }

    /**
     * 设置权限标识
     *
     * @param perms
     *            权限标识
     */
    public void setPerms(String perms) {
        this.perms = perms;
    }

    /**
     * 获取菜单图标
     *
     * @return icon - 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon
     *            菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

}