package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Created by abcd on 2017-01-21.
 */
public interface GameServerAsync {


    void sendBrushPosition(String brush, AsyncCallback<Void> async);


    void getBrushPosition(AsyncCallback<String> async);

    void createSession(String ID, AsyncCallback<Boolean> async);

    void sendBrush(Brush brush, AsyncCallback<Void> async);

    void getBrush(AsyncCallback<Brush> async);
}


