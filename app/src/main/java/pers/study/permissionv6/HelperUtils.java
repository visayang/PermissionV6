package pers.study.permissionv6;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class HelperUtils {

    public static String getValues(Context c, String key) {
        return (String) SharePerfenceUtils.get(c, key, "");
    }

    public static void setValues(Context c,String key) {
        SharePerfenceUtils.put(c,key,"1");
    }

    //跳转系统设置界面  主动选择权限
    public static void toSelfSetting(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }
}
