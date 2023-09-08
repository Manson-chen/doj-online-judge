import java.security.Permission;

/**
 * 默认安全管理器
 */
public class MySecurityManager extends SecurityManager{

    /**
     * 检查所有的权限
     * @param perm
     */
    @Override
    public void checkPermission(Permission perm) {
//        super.checkPermission(perm);
    }

    // 检查程序是否可执行
    @Override
    public void checkExec(String cmd) {
        throw new SecurityException("checkExec 权限异常：" + cmd);
    }

    // 检查程序是否允许读文件
    @Override
    public void checkRead(String file, Object context) {
//        if (file.contains("sandbox")){
//            return;
//        }
//        throw new SecurityException("checkRead 权限异常：" + file);
    }

    // 检查程序是否允许写文件
    @Override
    public void checkWrite(String file) {
//        throw new SecurityException("checkWrite 权限异常：" + file);
    }

    // 检查程序是否允许删除文件
    @Override
    public void checkDelete(String file) {
//        throw new SecurityException("checkDelete 权限异常：" + file);
    }

    // 检查程序是否允许连接网络
    @Override
    public void checkConnect(String host, int port) {
//        throw new SecurityException("checkConnect 权限异常：" + host + ":" + port);
    }
}
