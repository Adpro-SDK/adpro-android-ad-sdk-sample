package cn.pro.sdk.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.GregorianCalendar;

import cn.pro.sdk.AdRequest;
import cn.pro.sdk.AdSize;
import cn.pro.sdk.AdView;
import cn.pro.sdk.InterstitialAd;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        linearLayout = (LinearLayout) findViewById(R.id.ad);

        findViewById(R.id.popup).setOnClickListener(this);
        findViewById(R.id.popup_auto_show).setOnClickListener(this);
        findViewById(R.id.banner).setOnClickListener(this);
    }

    AdView adView;

    public void addView() {
        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE).setAddress("Tiananmen square in Beijing").setLongitude(116.46).setLatitude(39.92)
                .setBirthday(new GregorianCalendar(2016, 1, 26).getTime())
                .build();

        adView = new AdView(this, getString(R.string.app_key), getString(R.string.app_id), "377082c1637093841d99");
        adView.setAdListener(new AdView.AdListener() {

            @Override
            public void onAdFailedToLoad(int code, String error) {
                super.onAdFailedToLoad(code, error);
                mToast("Banner FailedToLoad Info:" + code + " error:" + error);
                Log.e(TAG, "Banner FailedToLoad Info:" + code + " error:" + error);
                if (adView != null && adView.getParent() != null && adView.getParent() instanceof ViewGroup)
                    ((ViewGroup) adView.getParent()).removeView(adView);
            }

            @Override
            public void onWillLeaveApplication() {
                super.onWillLeaveApplication();
                mToast("Banner Left Application");
                Log.e(TAG, "Banner Left Application");
            }

            @Override
            public void onWillPresentFullScreen() {
                super.onWillPresentFullScreen();
                mToast("Banner onWillPresentFullScreen");
                Log.e(TAG, "Banner onWillPresentFullScreen");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mToast("Banner onAdLoaded");
                Log.e(TAG, "Banner onAdLoaded");
            }
        });
        adView.setAdSize(new AdSize(-1, 50));
//        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(adRequest);

        //插入到布局
        linearLayout.addView(adView);
    }

    InterstitialAd interstitialAd;

    public void showInterstitialAd() {
        interstitialAd = new InterstitialAd(this, getString(R.string.app_key), getString(R.string.app_id), "2a8233af10137213f26b");

        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE).setAddress("Tiananmen square in Beijing").setLongitude(116.46).setLatitude(39.92)
                .setBirthday(new GregorianCalendar(2016, 1, 26).getTime())
                .build();

        interstitialAd.setAdListener(new InterstitialAd.AdListener() {
            @Override
            public void onAdClosed() {
                mToast("Interstitial  Close");
                Log.e(TAG, "Interstitial  Close");
            }

            @Override
            public void onAdFailedToLoad(int code, String error) {
                mToast("Interstitial Loaded Failed Info:" + code + " error:" + error);
                Log.e(TAG, "Interstitial Loaded Failed Info:" + code + " error:" + error);
            }

            @Override
            public void onAdLoaded() {
                mToast("Interstitial Loaded");
                Log.e(TAG, "Interstitial Loaded");
                if (interstitialAd != null) //显示插屏广告
                    interstitialAd.show();
            }

            @Override
            public void onWillLeaveApplication() { //将要离开应用
                mToast("Interstitial onWillLeaveApplication");
                Log.e(TAG, "Interstitial onWillLeaveApplication");
            }

            @Override
            public void onWillPresentFullScreen() { //将要展示全屏广告
                mToast("Interstitial onWillPresentFullScreen");
                Log.e(TAG, "Interstitial onWillPresentFullScreen");
            }
        });
        interstitialAd.loadAd(adRequest);
    }

    public void showInterstitialAdAutoShow() {

        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE).setAddress("Tiananmen square in Beijing").setLongitude(116.46).setLatitude(39.92)
                .setBirthday(new GregorianCalendar(2016, 1, 26).getTime())
                .build();
        interstitialAd = new InterstitialAd(this, getString(R.string.app_key), getString(R.string.app_id), "2a8233af10137213f26b");

        interstitialAd.setAdListener(new InterstitialAd.AdListener() {
            @Override
            public void onAdClosed() {
                mToast("Interstitial(AutoShow)  Close");
                Log.e(TAG, "Interstitial(AutoShow)  Close");
            }

            @Override
            public void onAdFailedToLoad(int code, String error) {
                mToast("Interstitial(AutoShow) Loaded Failed Info:" + code + " error:" + error);
                Log.e(TAG, "Interstitial(AutoShow) Loaded Failed Info:" + code + " error:" + error);
            }

            @Override
            public void onWillPresentFullScreen() {
                mToast("Interstitial(AutoShow)  onWillPresentFullScreen");
                Log.e(TAG, "Interstitial(AutoShow)  onWillPresentFullScreen");
            }

            @Override
            public void onAdLoaded() {
                mToast("Interstitial(AutoShow) Loaded");
                Log.e(TAG, "Interstitial(AutoShow) Loaded");
            }

            @Override
            public void onWillLeaveApplication() {
                mToast("Interstitial(AutoShow) onWillLeaveApplication");
                Log.e(TAG, "Interstitial(AutoShow) onWillLeaveApplication");
            }
        });
        interstitialAd.loadAdAutoShow(adRequest);
    }

    private void mToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup:
                showInterstitialAd();
                break;
            case R.id.popup_auto_show:
                showInterstitialAdAutoShow();
                break;
            case R.id.banner:
                addView();
                break;
        }
    }

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
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
    }
}
