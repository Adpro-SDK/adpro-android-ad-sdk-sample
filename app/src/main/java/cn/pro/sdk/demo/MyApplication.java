package cn.pro.sdk.demo;

import android.app.Application;

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
    }
}
