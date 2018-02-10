package com.github.chadsmith.RCTVideoPreview;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.util.Base64;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.views.image.ReactImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by chadsmith on 2/9/18.
 */

public class DownloadPreviewTask extends AsyncTask<ReadableArray, Void, Bitmap> {

    private ReactImageView image;

    DownloadPreviewTask(ReactImageView image) {
        this.image = image;
    }

    protected Bitmap doInBackground(ReadableArray... args) {
        ReadableArray sources = args[0];
        if(sources != null && sources.size() != 0) {
            if (sources.size() == 1) {
                ReadableMap source = sources.getMap(0);
                String uri = source.getString("uri");
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                try {
                    retriever.setDataSource(uri, new HashMap<String, String>());
                    bitmap = retriever.getFrameAtTime(3000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                }
                catch(Exception ignored) {}
                retriever.release();
                return bitmap;
            }
        }
        return null;
    }

    protected void onPostExecute(Bitmap result) {
        /*
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        WritableNativeMap source = new WritableNativeMap();
        source.putString("uri", "data:image/png;base64," + encoded);
        source.putDouble("width", result.getWidth());
        source.putDouble("height", result.getHeight());
        WritableNativeArray sources = new WritableNativeArray();
        sources.pushMap(source);
        image.setSource(sources);
        */
        // TODO - switch to base64 source or something else
        image.setImageBitmap(result);
    }

}
