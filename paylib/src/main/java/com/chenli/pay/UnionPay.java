package com.chenli.pay;

import android.app.Activity;

import com.unionpay.UPPayAssistEx;

/**
 *
 * Created by chenli on 2017/11/14.
 */

public class UnionPay {

    /**
     * 银联支付
     * @param activity 启动activity
     * @param tn 交易流水号
     */
    public static void startPay(Activity activity, String tn){
        UPPayAssistEx.startPay(activity, null, null, tn, "00");
    }

}
