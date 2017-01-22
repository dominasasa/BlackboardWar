package com.mySampleApplication.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by abcd on 2017-01-22.
 */

public class SendBrushCallback implements AsyncCallback<Void> {
    Brush result;

    @Override
    public void onFailure(Throwable caught) {
        Window.alert("can't send brush!");
    }

    @Override
    public void onSuccess(Void result) {

    }
}
