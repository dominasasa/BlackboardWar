package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;

/**
 * Created by odomi on 11.01.2017.
 */
public class UI {
    interface UIUiBinder extends UiBinder<DivElement, UI> {
    }

    private static UIUiBinder ourUiBinder = GWT.create(UIUiBinder.class);

    public UI() {
        DivElement rootElement = ourUiBinder.createAndBindUi(this);
    }
}