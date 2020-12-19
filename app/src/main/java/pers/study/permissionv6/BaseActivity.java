package pers.study.permissionv6;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
        initData();
    }

    /**
     * 布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    private PermissionListener mlistener;

    /**
     * 权限申请
     *
     * @param permissions 待申请的权限集合
     * @param listener    申请结果监听事件
     */
    protected void requestRunTimePermission(String[] permissions, PermissionListener listener) {
        this.mlistener = listener;

        // 用于存放未授权的权限
        List<String> permissionList = new ArrayList<>();
        // 遍历传递过来的权限集合
        for (String permission : permissions) {
            // 判断是否已经授权
            if (isHasPermission(permission)) {
                // 未授权，则加入待授权的权限集合中
                permissionList.add(permission);
            }
        }

        // 判断集合
        if (!permissionList.isEmpty()) {
            // 如果集合不为空，则需要去授权
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            // 为空，则已经全部授权
            if (listener != null) {
                listener.onGranted();
            }
        }
    }

    /**
     * 判断是否有权限
     *
     * @param permission
     * @return
     */
    protected boolean isHasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 权限申请结果
     *
     * @param requestCode  请求码
     * @param permissions  所有的权限集合
     * @param grantResults 授权结果集合
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    // 被用户拒绝的权限集合
                    List<String> deniedPermissions = new ArrayList<>();
                    // 用户通过的权限集合
                    List<String> grantedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        // 获取授权结果，这是一个int类型的值
                        int grantResult = grantResults[i];

                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            // 用户拒绝授权的权限
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        } else {
                            // 用户同意的权限
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {
                        // 用户拒绝权限为空
                        if (mlistener != null) {
                            mlistener.onGranted();
                        }
                    } else {
                        // 不为空
                        if (mlistener != null) {
                            // 回调授权成功的接口
                            mlistener.onGranted(grantedPermissions);
                            // 回调授权失败的接口
                            mlistener.onDenied(deniedPermissions);
                            mlistener.onDenied();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public interface PermissionListener {
        // 授权成功
        void onGranted();

        // 授权部分
        void onGranted(List<String> grantedPermission);

        // 拒绝授权
        void onDenied(List<String> deniedPermission);

        // 授权失败
        void onDenied();
    }
}
