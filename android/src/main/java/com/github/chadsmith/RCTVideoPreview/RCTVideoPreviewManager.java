package com.github.chadsmith.RCTVideoPreview;

import android.content.Context;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.GlobalImageLoadListener;
import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.image.ReactImageView;

import javax.annotation.Nullable;

public class RCTVideoPreviewManager extends ReactImageManager {

    public static final String REACT_CLASS = "RCTVideoPreview";
    private ReactContext reactContext;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public RCTVideoPreviewManager(ReactContext context) {
        this.reactContext = context;
    }

    @Override
    @ReactProp(name = "src")
    public void setSource(ReactImageView view, @Nullable ReadableArray sources) {
        new DownloadPreviewTask(view).execute(sources);
    }

}
