package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.entity.Permission;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface PermissionService {
    /**
     * 创建权限
     *
     * @param permission
     * @return
     */
    public Permission createPermission(Permission permission);

    /**
     * 删除权限
     *
     * @param permissionId
     */
    public void deletePermission(Long permissionId);
}
