package com.dd.mylibrary.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

public class SdCarcUtils {



    //判断SD卡是否正常可用
    public static boolean isExist(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    //获取sd卡的跟文件对象
    public static File getSdFile(){
        return Environment.getExternalStorageDirectory();
    }

    //获取sd卡的跟路径
    public static String getSdPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }


    /**
     * 获取指定类型的文件夹的具体路径:如音乐 照片  视频等
     *
     * String directoryMusic = Environment.DIRECTORY_MUSIC;
     * Environment.DIRECTORY_MOVIES;
     * Environment.DIRECTORY_DCIM;
     *
     *
     * @param type
     * @return
     */
    public static String getDetailPath(String type){
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    public File getDetailFile(String type){
        return Environment.getExternalStoragePublicDirectory(type).getAbsoluteFile();
    }

    public Uri getUri(String absolutePath){
        return Uri.parse(absolutePath);
    }

    //获取SD卡的总大小
    public static String getTotalSize(Context context) {
        // 获取SD卡根目录
        File path = Environment.getExternalStorageDirectory();
        // 获取指定目录下的内存存储状态
        StatFs stat = new StatFs(path.getPath());
        // 获取单个扇区的大小
        long blockSize = stat.getBlockSize();
        // 获取扇区的数量
        long totalBlocks = stat.getBlockCount();

        // 总空间 = 扇区的总数 * 扇区的大小
        long totalSize = blockSize * totalBlocks;
        // 格式化文件大小的格式
        return Formatter.formatFileSize(context, totalSize);
    }


    //获取可用大小
    public static String getAvaliSize(Context context) {
        // 获取SD卡根目录
        File path = Environment.getExternalStorageDirectory();
        // 获取指定目录下的内存存储状态
        StatFs stat = new StatFs(path.getPath());
        // 获取单个扇区的大小
        long blockSize = stat.getBlockSize();

        // 获取可以使用的扇区数量
        long availableBlocks = stat.getAvailableBlocks();
        // 可用空间 = 扇区的大小 + 可用的扇区
        long availableSize = blockSize * availableBlocks;
        // 格式化文件大小的格式
        return Formatter.formatFileSize(context, availableSize);
    }






}
