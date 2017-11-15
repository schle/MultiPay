package com.chenli.pay;

import android.content.Context;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by chenli on 2017/10/16.
 */

public class WXPay {


    private static WXPay mInstance;
    private IWXAPI mApi;
    private WXPayResultCallBack mCallBack;

    public interface WXPayResultCallBack {
        void onSuccess(); //支付成功
        void onError(int errorCode);   //支付失败
        void onCancel();    //支付取消
    }

    private WXPay(Context context, String appId) {
        mApi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        mApi.registerApp(appId);
    }

    public static WXPay init(Context context, String appId) {
        if (mInstance == null) {
            synchronized (WXPay.class) {
                if (mInstance == null) {
                    mInstance = new WXPay(context, appId);
                }
            }
        }
        return mInstance;
    }


    public static WXPay getInstance() {
        return mInstance;
    }

    public IWXAPI getApi(){
        return mApi;
    }

    public boolean isSupportPay() {
        return mApi.isWXAppInstalled() && mApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    public void startPay(PayReq payReq, WXPayResultCallBack callBack) {
        mCallBack = callBack;
        mApi.sendReq(payReq);
    }

    public void onResp(int errCode) {
        if (mCallBack==null)return;

        switch (errCode){
            case BaseResp.ErrCode.ERR_OK:
                mCallBack.onSuccess();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                mCallBack.onCancel();
                break;
            default:
                mCallBack.onError(errCode);
                break;
        }

    }




}
