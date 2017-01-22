package com.mySampleApplication.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by abcd on 2017-01-22.
 */

public class GetBrushCallback implements AsyncCallback<Brush> {

    Brush result;

    @Override
    public void onFailure(Throwable caught) {
        Window.alert("can't get brush!");
    }

    @Override
    public void onSuccess(Brush result) {
        this.result = result;
    }
}