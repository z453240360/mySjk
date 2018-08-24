package com.dd.mylibrary.http.apiService;

import com.dd.mylibrary.http.utils.interceptor.DownloadProgressInterceptor;
import com.dd.mylibrary.http.utils.interceptor.DownloadProgressListener;
import com.dd.mylibrary.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author：fmd on 16/9/7
 * use:
 */
public class DownloadAPI extends DynamicAPI{

    private String folderPath;
    private String fileName;
    private static final String TAG = "DownloadAPI/TAG";
    public DownloadAPI(Builder builder){
        super(builder);
        this.folderPath = builder.folderPath;
        this.fileName = builder.fileName;
    }

    public Observable<File> download(){

        Observable<File> observable = retrofit.create(DynamicService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<InputStream, File>() {
                    @Override
                    public File call(InputStream inputStream) {

                        File file = new File(folderPath , fileName);
                        if(file.exists()){
                            file.delete();
                        }
                        writeFile(inputStream, folderPath , fileName);
                        return new File(folderPath , fileName);
                    }
                });
        return observable;
    }


    public static class Builder extends DynamicAPI.CommonBuilder<Builder>{

        /**
         * 下载文件的文件夹路径
         */
        private String folderPath;
        /**
         * 文件名
         */
        private String fileName;
        private DownloadProgressListener downloadListener;

        public Builder folderPath(String folderPath){

            this.folderPath = folderPath;
            return this;
        }

        public Builder fileName(String fileName){

            this.fileName = fileName;
            return this;
        }

        public Builder progressListener(DownloadProgressListener listener){

            this.downloadListener = listener;
            mClientBuilder.addInterceptor(new DownloadProgressInterceptor(downloadListener));
            return this;
        }

        public DownloadAPI build(){

            mClientBuilder.retryOnConnectionFailure(true);
            return new DownloadAPI(this);
        }
    }

    public static void writeFile(InputStream stream , String folderPath , String fileName){
        OutputStream os = null;
        try {
            File mDirectory = new File(folderPath);
            if (!mDirectory.exists())
                mDirectory.mkdirs();
            os = new FileOutputStream(folderPath + "/" + fileName);
            int bytesRead;
            byte[] buffer = new byte[2048];
            while ((bytesRead = stream.read(buffer, 0, 2048)) != -1) {
                LogUtils.d(TAG , "bytesRead ====> " + bytesRead);
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(stream != null)
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
