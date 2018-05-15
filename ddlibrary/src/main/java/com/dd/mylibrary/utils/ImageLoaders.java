package com.dd.mylibrary.utils;


import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dd.mylibrary.R;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


/**
 * Created by 29435 on 2017/5/25.
 */

public class ImageLoaders {
    public static final int HOME_LOGO = 1;
    public static final int VISIT_LOGO = 2;

    private static final String TAG = ImageLoaders.class.getSimpleName();

    public static void displayImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            Glide.with(imageView.getContext()).load(Integer.valueOf(url)).apply(options).into(imageView);
        } catch (Exception e) {
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }
    }

    public static void displayImage2(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher)
//                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            Glide.with(imageView.getContext()).load(Integer.valueOf(url)).apply(options).into(imageView);
        } catch (Exception e) {
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }
    }

    public static void displayImageWithDefault(ImageView imageView, String url, int defaultResource) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(defaultResource)
                .error(defaultResource)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            Glide.with(imageView.getContext()).load(Integer.valueOf(url)).apply(options).into(imageView);
        } catch (Exception e) {
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }
    }

    /**
     * @param type 1(ImageLoaders.HOME_LOGO)：主队   2(ImageLoaders.VISIT_LOGO)：客队
     */
    public static void displayImage(ImageView imageView, String url, int type) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (type == ImageLoaders.HOME_LOGO) {
            options.error(R.mipmap.ic_launcher);
        } else if (type == ImageLoaders.VISIT_LOGO) {
            options.error(R.mipmap.ic_launcher);
        }

        try {
            Glide.with(imageView.getContext()).load(Integer.valueOf(url)).apply(options).into(imageView);
        } catch (Exception e) {
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }
    }


    public static void displayImage(ImageView imageView, Integer url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }

    public static void displayCircleImage(final ImageView imageView, String url) {
        Log.d(TAG, "displayCircleImage: ");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            Glide.with(imageView.getContext()).load(Integer.valueOf(url)).apply(options).into(imageView);
        } catch (Exception e) {
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }

    }

    public static void displayCircleImage(final ImageView imageView, int resId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            Glide.with(imageView.getContext()).load(resId).apply(options).into(imageView);
        } catch (Exception e) {
            Glide.with(imageView.getContext()).load(resId).apply(options).into(imageView);
        }

    }

    public static void displayGif(ImageView imageView, String url) {

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        try {
            Glide.with(imageView.getContext()).load(Integer.valueOf(url)).apply(options).into(imageView);
        } catch (Exception e) {
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }
    }

    static long time;

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

}


