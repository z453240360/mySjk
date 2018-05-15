package com.dd.mylibrary.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fangmingdong on 2016/12/29.
 */

public class FileUtils {

    public static final String ROOT_STORAGE = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/QuickenLoan";
    public static final String IMAGE_ROOT = ROOT_STORAGE + "/image";
    public static final String VIDEO_ROOT = ROOT_STORAGE + "/video";
    public static final String LOG_SAVE_PATH = ROOT_STORAGE + "/logs/error";
    public static final String APK_SAVE_PATH = ROOT_STORAGE + "/apk";

    /**
     * 创建文件夹
     *
     * @return 文件夹绝对路径
     */
    public static String createSDCardDir(String fileName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {

            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            // 得到一个路径，内容是sdcard的文件夹路径和名字
            String path = sdcardDir.getPath() + File.separator + fileName;
            File path1 = new File(path);
            if (!path1.exists()) {
                // 若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs();
            }
            return path1.getAbsolutePath();
        } else {
            File mFile = new File(Environment.getExternalStorageDirectory()
                    .getAbsoluteFile() + File.separator + fileName);
            if (!mFile.exists()) {
                mFile.mkdir();
            }
            return mFile.getAbsolutePath();
        }
    }

    /**
     * 需要覆盖原有值
     *
     * @param parentPath
     * @param fileName
     * @param value
     */
    public static boolean writeToFile(String parentPath, String fileName, String value) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File mDirectory = new File(parentPath);
                if (!mDirectory.exists())
                    mDirectory.mkdirs();
                FileOutputStream mFileOutputStream = new FileOutputStream(mDirectory + "/"
                        + fileName, false);
                mFileOutputStream.write(value.getBytes());
                mFileOutputStream.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param file
     * @return
     */
    public static String file2String(File file) {
        String result = "";
        if (file == null || !file.exists())
            return result;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                result = result + s;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取文件流
     *
     * @return
     */
    public static String File2String(InputStream in) {
        String content = ""; // 结果字符串
        if (in == null)
            return content;
        try {
            int ch = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream(); // 实现了一个输出流
            while ((ch = in.read()) != -1)
                out.write(ch); // 将指定的字节写入此 byte 数组输出流
            byte[] buff = out.toByteArray();// 以 byte 数组的形式返回此输出流的当前内容
            out.close(); // 关闭流
            in.close(); // 关闭流
            content = new String(buff, "UTF-8"); // 设置字符串编码
        } catch (Exception e) {
        }
        return content;
    }


    public static File byte2File(byte[] data, String fileName) {
        FileOutputStream fileOut = null;

        String parentPath = IMAGE_ROOT;
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        File file = new File(parentPath + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            fileOut = new FileOutputStream(file);
            fileOut.write(data, 0, data.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void writeRawToFile(InputStream is, String path, String name) {

        File parentFile = new File(path);
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        File file = new File(path + "/" + name);
        if (file.exists()) {
            return;
        }
        FileOutputStream fos = null;
        try {
            byte[] data = new byte[is.available()];
            int nbread = 0;
            fos = new FileOutputStream(file);
            while ((nbread = is.read(data)) > -1) {
                fos.write(data, 0, nbread);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static File byte2File(byte[] data, String filePath, String fileName) {
        FileOutputStream fileOut = null;

        String parentPath = filePath;
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        File file = new File(parentPath + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            fileOut = new FileOutputStream(file);
            fileOut.write(data, 0, data.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static void checkFilePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * 根据uri获取文件的路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static File getFileFromBytes(byte[] b, String outputFile) {
        FileOutputStream fstream = null;
        File file = null;
        try {
            file = new File(outputFile);
            fstream = new FileOutputStream(file);
            fstream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 创建临时文件
     */
    public static File createTempImageFile(String path) {
        File folder = new File(path);
        if (!folder.exists()) {

            folder.mkdir();
        }
        return new File(folder, createFileName(null));
    }

    /**
     * 创建临时文件
     */
    public static File createTempImageFile(String path, String fileName) {
        File folder = new File(path);
        if (!folder.exists()) {

            folder.mkdir();
        }
        return new File(folder, createFileName(fileName));
    }

    public static String createFileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = new SimpleDateFormat("yyyyMMdd_HHmmssSSSS")
                    .format(new Date());
        }
        fileName = "img_" + fileName + ".jpg";
        return fileName;
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void deleteFileFromPath(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
    }

    /**
     * android N 之后文件获取uri权限需要兼容
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriWithN(Context context, File file) {
        String filePath = file.getAbsolutePath();
        Uri uri;
        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(file);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
        return uri;
    }

    public static String readAssert(Context context, String fileName) {
        String jsonString = "";
        String resultString = "";
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return resultString;
    }

    public static String getFileSize(long size) {
        DecimalFormat format = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbSize = size / 1024f;
            return format.format(kbSize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbSize = size / 1024f / 1024f;
            return format.format(mbSize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbSize = size / 1024f / 1024f / 1024f;
            return format.format(gbSize) + "GB";
        } else {
            return "";
        }
    }

    /**
     * 获取sdk路径
     *
     * @return
     */
    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 检测判断SD卡是否存在
     *
     * @param mActivity 上下文
     */
    public static boolean checkSD(Activity mActivity) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            Toast.makeText(mActivity, "SD卡不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
