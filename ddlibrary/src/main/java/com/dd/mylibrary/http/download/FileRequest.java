package com.dd.mylibrary.http.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * Created by fangmingdong on 17/03/02.
 * 文件下载进度更新
 */
public class FileRequest extends BaseRequest<File> {

    public static final int UPDATE_INTERVAL = 200;
    private String mFileName, mFilePath;
    private boolean mIsCoverOldFile;
    private OnProgressUpdateListener mProgressListener;
    private int mTotalSize;
    private long mLastUpdateTimeMillis;

    public FileRequest(String requestUrl) {
        super(requestUrl);
    }

    public FileRequest(String requestUrl, Map<String, String> requestParams) {
        super(requestUrl, requestParams);
    }

    public void setFile(String path, String name) {
        setFile(path, name, true);
    }

    public void setFile(String path, String name, boolean isCoverOldFile) {
        this.mFileName = name;
        this.mFilePath = path;
        this.mIsCoverOldFile = isCoverOldFile;
    }

    @Override
    public File handleResponse(InputStream inputStream, int contentLength) {

        this.mTotalSize = contentLength;
        File file = new File(mFilePath, mFileName);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists() || mIsCoverOldFile) {
            try {
                file.createNewFile();
                return save2File(file, inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected String parseRequestParams() {
        final Map<String, String> params = getRequestParams();
        if (null == params) {
            return "";
        }
        final Map<String, String> map = params;
        Set<String> keys = map.keySet();
        StringBuilder sBuilder = new StringBuilder();
        for (String key : keys) {
            sBuilder.append(String.format("%s=%s&", key, map.get(key)));
        }
        String paramStr = sBuilder.toString();
        return paramStr.substring(0, paramStr.length() - 1);
    }

    private File save2File(File file, InputStream in) throws IOException {

        final int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(in);
        FileOutputStream fos = new FileOutputStream(file);

        int size;
        int completedSize = 0;
        while ((size = bis.read(buf)) != -1) {
            //更新进度
            completedSize += size;
            publishProgress(completedSize);
            fos.write(buf, 0, size);
        }
        fos.close();
        bis.close();
        return file;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if (null != mProgressListener) {
            //避免过度刷新，每0.2s刷新一次
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - mLastUpdateTimeMillis > UPDATE_INTERVAL) {
                mProgressListener.onProgressUpdate(values[0], mTotalSize);
                mLastUpdateTimeMillis = currentTimeMillis;
            }
        }
    }

    public FileRequest setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        this.mProgressListener = listener;
        return this;
    }

    public interface OnProgressUpdateListener {
        void onProgressUpdate(int progress, int totalSize);
    }
}
