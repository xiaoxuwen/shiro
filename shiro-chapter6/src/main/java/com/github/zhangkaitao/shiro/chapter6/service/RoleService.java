package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.entity.Role;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface RoleService {


    /**
     * 创建角色
     *
     * @param role
     * @return
     */
    public Role createRole(Role role);

    /**
     * 删除角色
     *
     * @param roleId
     */
    public void deleteRole(Long roleId);

    /**
     * 添加角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, Long... permissionIds);

    /**
     * 移除角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(Long roleId, Long... permissionIds);

}
