# adpro-android-ad-sdk-sample

# AdProSDK（Android）开发者手册


   --V：2.0.0

## 关于AdProSDK


本SDK支持 android 2.2 及以上版本。Java JDK1.6 及以上版本。编译时请使用 Android SDK 4.0 以上版本编译。


## 获取APP_ID、APP_KEY

1. 登录 `开发者平台`（如果没有账户，请向商务联系人索取）

2. 按照【开发者平台】提示，`添加应用方案`，在应用详情里面获取APP_ID、APP_KEY

![1](http://git.yunpro.cn/uploads/mobile-sdk/sdk-doc/7148deb69d/1.png)


## 下载SDK

   [下载SDK] 并解压缩。

   ![2](http://git.yunpro.cn/uploads/mobile-sdk/sdk-doc/92f713a642/2.png)

## 导入SDK

1. 导入Jar

    下载的压缩包中包含`ad_android_sdk.jar`,将其拷贝到工程指定libs目录中，并引入。 

2. 配置`AndroidMainfest.xml`文件


    2.1 添加权限

   
	```
    <!-- SDK INJECT -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!-- SDK INJECT [END] -->
	```
   
    2.2 注册AdActivity
    

	```
    <!-- SDK INJECT -->
    <activity
            android:name="cn.pro.sdk.AdActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
    <!-- SDK INJECT [END] -->
    ```
    


## 广告位添加

 进入【开发者平台】，添加广告位。

 ![3](http://git.yunpro.cn/uploads/mobile-sdk/sdk-doc/005f86bfd2/3.png)

### Banner广告


1. 点击`添加广告位`，选择`Banner`类型,并进行相关广告配置，配置好点击完成

2. 获取广告slot_id

    在新添加的广告一栏中，点击`查看代码`，获取slot_id（slot_id是广告控件的唯一标识）

3. 显示广告代码

   ```
   AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE).setAddress("Tiananmen square in Beijing").setLongitude(116.46).setLatitude(39.92)
                .setBirthday(new GregorianCalendar(2016, 1, 26).getTime())
                .build();

        adView = new AdView(this, getString(R.string.app_key), getString(R.string.app_id), "377082c1637093841d99");
        adView.setAdListener(new AdView.AdListener() {

            @Override
            public void onAdFailedToLoad(int code, String error) {
                Log.e(TAG, "Banner FailedToLoad Info:" + code + " error:" + error);
                if (adView != null && adView.getParent() != null && adView.getParent() instanceof ViewGroup)
                    ((ViewGroup) adView.getParent()).removeView(adView);
            }

            @Override
            public void onWillLeaveApplication() {
                Log.e(TAG, "Banner Left Application");
            }

            @Override
            public void onWillPresentFullScreen() {
                Log.e(TAG, "Banner onWillPresentFullScreen");
            }

            @Override
            public void onAdLoaded() {
                Log.e(TAG, "Banner onAdLoaded");
            }

        });
        adView.setAdSize(new AdSize(-1, 50));
        // adView.setAdSize(AdSize.BANNER);
        adView.loadAd(adRequest);

        //插入到布局
        linearLayout.addView(adView);
   ```
   
   

   ```
   /**
     * @param context
     * @param appKey 应用密钥
     * @param appId  应用ID
     * @param slotId 广告位ID
     */
    public AdView(Context context, String appKey, String appId, String slotId) {
   ```

4. 状态控制
   
   ```
   @Override
    protected void onResume() {
        super.onResume();
        if (adView != null)
            adView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView != null)
            adView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
    }
   ```
  
  <font color = red>注：AdSize中，宽不能为-2。</font>
  
  
#### Interstitial

##### 准备

1. 点击`添加广告位`，选择`Popup`类型,并进行相关广告配置，配置好点击完成

2. 获取广告slot_id

   在新添加的广告一栏中，点击`查看代码`，获取slot_id（slot_id是广告控件的唯一标识）

##### 插屏广告（一）

**特点：**便于控制

1. 显示广告

   ```
interstitialAd = new InterstitialAd(this, getString(R.string.app_key), getString(R.string.app_id), "2a8233af10137213f26b");

        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE).setAddress("Tiananmen square in Beijing").setLongitude(116.46).setLatitude(39.92)
                .setBirthday(new GregorianCalendar(2016, 1, 26).getTime())
                .build();

        interstitialAd.setAdListener(new InterstitialAd.AdListener() {
            @Override
            public void onAdClosed() {
                Log.e(TAG, "Interstitial  Close");
            }

            @Override
            public void onAdFailedToLoad(int code, String error) {
                Log.e(TAG, "Interstitial Loaded Failed Info:" + code + " error:" + error);
            }

            @Override
            public void onAdLoaded() {
                Log.e(TAG, "Interstitial Loaded");
                if (interstitialAd != null) //显示插屏广告
                    interstitialAd.show();
            }

            @Override
            public void onWillLeaveApplication() {  //将要离开应用
                Log.e(TAG, "Interstitial onWillLeaveApplication");
            }

            @Override
            public void onWillPresentFullScreen() { //将要展示全屏广告
                Log.e(TAG, "Interstitial onWillPresentFullScreen");
            }
        });
        interstitialAd.loadAd(adRequest);
   ```

2. 状态控制
   
   ```
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
    }
   ```
   
##### 插屏广告（二）

**特点：**无需调用`interstitialAd.show();`方法

1. 显示广告

   ```
   AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE).setAddress("Tiananmen square in Beijing").setLongitude(116.46).setLatitude(39.92)
                .setBirthday(new GregorianCalendar(2016, 1, 26).getTime())
                .build();
        interstitialAd = new InterstitialAd(this, getString(R.string.app_key), getString(R.string.app_id), "2a8233af10137213f26b");

        interstitialAd.setAdListener(new InterstitialAd.AdListener() {
            @Override
            public void onAdClosed() {
                Log.e(TAG, "Interstitial(AutoShow)  Close");
            }

            @Override
            public void onAdFailedToLoad(int code, String error) {
                Log.e(TAG, "Interstitial(AutoShow) Loaded Failed Info:" + code + " error:" + error);
            }

            @Override
            public void onWillPresentFullScreen() {
                Log.e(TAG, "Interstitial(AutoShow)  onWillPresentFullScreen");
            }

            @Override
            public void onAdLoaded() {
                Log.e(TAG, "Interstitial(AutoShow) Loaded");
            }

            @Override
            public void onWillLeaveApplication() {
                Log.e(TAG, "Interstitial(AutoShow) onWillLeaveApplication");
            }
        });
        interstitialAd.loadAdAutoShow(adRequest);
   ```

2. 状态控制
   
   ```
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
    }
   ```


## 问题及处理方法

### SDK使用的什么编码

SDK 使用的 `UTF-8` 编码，请尽量使用`UTF-8`编码。


### 关于广告没有展示成功，问题的排查

1. 检查设置是否可以连网。

2. 检查SDK注册信息是否正确，APP_ID、APP_KEY。

3. 检查广告位的设置是否正确(如果有广告位),`slot_id`是否正确。

4. 如果仍未能解决,请联系我们的客服,我们将很乐意为您服务。


**注:**请留意`控制台Log`,错误信息及警告会在里面显示。

## 混淆事项


  请在proguard.cfg中加入以下代码:
  
```
-dontwarn a.a.**
-dontwarn cn.pro.sdk.**
-dontwarn assets.**
-dontwarn com.android.volley.**
-keep class a.a.** { *; }
-keep class cn.pro.sdk.** { *; }
-keep class com.android.volley.** { *; }
```
