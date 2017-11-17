package com.chenli.pay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WXPay.getInstance() != null) {
            WXPay.getInstance().getApi().handleIntent(getIntent(), this);
        } else {
            finish();
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (WXPay.getInstance() != null) {
            WXPay.getInstance().getApi().handleIntent(getIntent(), this);
        }
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(WXPay.getInstance() != null) {
                WXPay.getInstance().onResp(resp.errCode);
                finish();
            }
        }
    }
}