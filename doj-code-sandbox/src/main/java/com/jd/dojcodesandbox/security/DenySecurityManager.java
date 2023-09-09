package com.jd.dojcodesandbox.security;

import java.security.Permission;

/**
 * 禁止所有权限安全管理器
 */
public class DenySecurityManager extends SecurityManager{

    /**
     * 检查所有的权限
     * @param perm
     */
    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限异常：" + perm.toString());
    }
}
