package com.cum3.yilifang.project.system.menu.service;

import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.web.service.TreeService;
import com.cum3.yilifang.project.system.menu.domain.SysMenu;

@Service("sysMenuService")
public class SysMenuService extends TreeService<SysMenu>{}
