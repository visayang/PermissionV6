package pers.study.permissionv6;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends BaseActivity implements PhoneStatusUtils.IReadPhoneStateListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PhoneStatusUtils.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void addPermission(View view) {
//        accessPermissionStoarg();
        PhoneStatusUtils.GetSerialNumber(this);
    }

    //存储权限
    public void accessPermissionStoarg() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    SharePerfenceUtils.clear(this, "REQUEST_CODE_STORAGE");
                } else if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && HelperUtils.getValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP).equals("1")) {
                    //用户拒绝权限 并勾选了 不再提示  （这里 可以自定义 Dialog 让用户跳转 系统设置 去选择权限  跳转的方法在 HelperUtils 类中）
                    if(HelperUtils.getValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP_SHOWED).equals("1")){
                        // 用户拒绝权限 并勾选了 不再提示 后 已经提示过一次
                        return;
                    }
                    // 用户拒绝权限 并勾选了 不再提示   下一次获取权限 弹土司提示
                    Toast.makeText(this, "未取得存储权限，请在应用设置中打开权限", Toast.LENGTH_SHORT).show();

                    HelperUtils.setValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP_SHOWED);
                    return;
                } else {

                }
            }

            // 权限还没有授予，进行申请
            requestRunTimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new SimplePermissionListener() {
                @Override
                public void onGranted() {
                    Log.i(TAG, "存储权限 已经授权");
                }

                @Override
                public void onDenied() {
                    super.onDenied();Log.i(TAG, "用户拒绝授予 存储 权限");
                    HelperUtils.setValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP);
                }
            });
        }
    }


    //设备权限
    public void accessPermissionPhone() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    SharePerfenceUtils.clear(this, "REQUEST_CODE_STORAGE");
                } else if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && HelperUtils.getValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP).equals("1")) {
                    //用户拒绝权限 并勾选了 不再提示  （这里 可以自定义 Dialog 让用户跳转 系统设置 去选择权限  跳转的方法在 HelperUtils 类中）
                    if(HelperUtils.getValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP_SHOWED).equals("1")){
                        // 用户拒绝权限 并勾选了 不再提示 后 已经提示过一次
                        return;
                    }
                    // 用户拒绝权限 并勾选了 不再提示   下一次获取权限 弹土司提示
                    Toast.makeText(this, "未取得存储权限，请在应用设置中打开权限", Toast.LENGTH_SHORT).show();

                    HelperUtils.setValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP_SHOWED);
                    return;
                } else {

                }
            }

            // 权限还没有授予，进行申请
            requestRunTimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new SimplePermissionListener() {
                @Override
                public void onGranted() {
                    Log.i(TAG, "存储权限 已经授权");
                }

                @Override
                public void onDenied() {
                    super.onDenied();Log.i(TAG, "用户拒绝授予 存储 权限");
                    HelperUtils.setValues(MainActivity.this, SharePerfenceUtils.KEY_STORAGE_NOTIP);
                }
            });
        }
    }

    @Override
    public void OnReadPhoneStateListener() {
        Log.i(TAG, "OnReadPhoneStateListener===========");

    }

    private static abstract class SimplePermissionListener implements PermissionListener {

        @Override
        public void onGranted() {

        }

        @Override
        public void onGranted(List<String> grantedPermission) {

        }

        @Override
        public void onDenied(List<String> deniedPermission) {

        }

        @Override
        public void onDenied() {

        }
    }


}
