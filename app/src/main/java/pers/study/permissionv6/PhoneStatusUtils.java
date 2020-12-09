package pers.study.permissionv6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.UUID;

public class PhoneStatusUtils {

    private static final String TAG = "PhoneStatusUtils";

    @SuppressLint({"NewApi", "MissingPermission"})
    public static String GetSerialNumber(Activity context)
    {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){

            if (readPhoneStateListener != null) {
                readPhoneStateListener.OnReadPhoneStateListener();
            }

            return "";
        }else {

            /**
             * 获取手机序列号
             *
             * @return 手机序列号
             */
            String serial = "";
            try {
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){//10.0+
                    try{
                        serial = android.os.Build.getSerial();
                    }catch (Exception e){
                        String m_szDevIDShort = "35" +
                                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                                Build.USER.length() % 10; //13 位
                        serial = "serial";
                        serial = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
                    }
                }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//9.0+
                    serial = Build.getSerial();
                } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+
                    serial = Build.SERIAL;
                } else {//8.0-
                    Class<?> c = Class.forName("android.os.SystemProperties");
                    Method get = c.getMethod("get", String.class);
                    serial = (String) get.invoke(c, "ro.serialno");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,"读取设备序列号异常：" + e.toString());
            }
            return serial;
        }
    }



    /**
     * 定义一个接口
     */
    public interface  IReadPhoneStateListener{
        void OnReadPhoneStateListener();
    }
    /**
     *定义一个变量储存数据
     */
    private static IReadPhoneStateListener readPhoneStateListener;
    /**
     *提供公共的方法,并且初始化接口类型的数据
     */
    public static void setListener( IReadPhoneStateListener listener){
        readPhoneStateListener = listener;
    }
}
