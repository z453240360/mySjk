package com.dd.mylibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fangmingdong on 2017/1/13.
 */

public class BitmapUtils {

    /**
     * 图片保存文件
     *
     * @param filePath
     * @param bitmap
     */
    public static void saveBitmapFile(Bitmap bitmap, String filePath, String fileName) {
        try {
            File dir = new File(filePath);
            File file = new File(filePath, fileName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩原始图片
     *
     * @param srcFile
     * @return
     */
    public static File getCompressImageFile(File srcFile) {
        String fileName = srcFile.getName();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        File desFile = new File(FileUtils.getSdCardPath() + "/path/");
        if (!desFile.exists()) {
            desFile.mkdirs();
        }
        desFile = new File(desFile.getPath() + "/scale." + prefix);
        try {

            //获取压缩过的图片
            Bitmap compressedImage = getCompressedBmp(srcFile.getAbsolutePath());
            saveBitmapFile(compressedImage, desFile.getPath(), "");
        } catch (Exception e) {
            // TODO: handle exception
        }


        return desFile;
    }

    /**
     * 压缩图片大小
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        //图片允许最大空间   单位：KB
        double maxSize = 1000d;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            image = zoomImage(image, image.getWidth() / Math.sqrt(i), image.getHeight() / Math.sqrt(i));
        }
        return image;
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


    /**
     * 获取图片压缩后的bitmap
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getCompressedBmp(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是1920*1080分辨率，所以高和宽我们设置为
        float hh = 1920f;//这里设置高度为1920f
        float ww = 1080f;//这里设置宽度为1080f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 获取压缩后图片。
     *
     * @param path 原图路径
     * @return Bitmap
     */
    public static Bitmap getBitmapFromFilePath(String path, int baseSize) {
        if (TextUtils.isEmpty(path)) return null;
        Bitmap bitmap;
        // 图片参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只计算几何尺寸，不返回Bitmap ，不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 宽和高
        int w = options.outWidth;
        int h = options.outHeight;
        // 最小边
        int min = w < h ? w : h;
        // 压缩比
        int rate = min / baseSize;
        if (rate <= 0) {
            rate = 1;
        }
        // 设置压缩参数
        options.inSampleSize = rate;
        options.inJustDecodeBounds = false;
        // 压缩
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        if (bitmap == null) {
            return bitmap;
        }

        Matrix matrix = new Matrix();
        // 向左旋转45度，参数为正则向右旋转
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public static Bitmap decodeFromFile(String filePath,
                                        int defaultWidth, int defaultHeight, boolean isEnlarge) {
        File file = new File(filePath);
        if (!file.exists()) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSize;
        if (outHeight < defaultHeight && outWidth < defaultWidth && isEnlarge) {
            float scale = (float) defaultHeight / (float) outHeight > (float) defaultWidth
                    / (float) outWidth ? (float) defaultHeight / (float) outHeight
                    : (float) defaultWidth / (float) outWidth;

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale); // 长和宽放大缩小的比例
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            return Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else {
            int scaleWidth = Math
                    .round((float) outWidth / (float) defaultWidth);
            int scaleHeight = Math.round((float) outHeight
                    / (float) defaultHeight);
            sampleSize = scaleHeight < scaleWidth ? scaleHeight : scaleWidth;
            if (sampleSize <= 0)
                sampleSize = 1;
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options);
        }
    }

    public static Bitmap createVideoThumb(String videoPath, String thumbDirPath, String fileName) {

        if (TextUtils.isEmpty(videoPath)) return null;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MICRO_KIND);
        if (bitmap != null) {
            BitmapUtils.saveBitmapFile(bitmap, thumbDirPath, fileName);
        }
        return bitmap;
    }

    /**
     * 把一个view转化成bitmap对象
     */
    public static Bitmap getViewBitmap(View v) {
        int w = v.getWidth();

        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bmp);

        c.drawColor(Color.TRANSPARENT);

        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);

        v.draw(c);

        return bmp;
    }

    /**
     * 去除多余白框
     *
     * @return
     */
    public static Bitmap deleteNoUseWhiteSpace(Bitmap bitmap) {
//        int[] imgThePixels = new int[originBitmap.getWidth() * originBitmap.getHeight()];
//        originBitmap.getPixels(
//                imgThePixels,
//                0,
//                originBitmap.getWidth(),
//                0,
//                0,
//                originBitmap.getWidth(),
//                originBitmap.getHeight());
//
//        // 灰度化 bitmap
//        Bitmap bitmap = getGrayImg(
//                originBitmap.getWidth(),
//                originBitmap.getHeight(),
//                imgThePixels);

        int top = 0;  // 上边框白色高度
        int left = 0; // 左边框白色高度
        int right = 0; // 右边框白色高度
        int bottom = 0; // 底边框白色高度

        for (int h = 0; h < bitmap.getHeight(); h++) {
            boolean holdBlackPix = false;
            for (int w = 0; w < bitmap.getWidth(); w++) {
                if (bitmap.getPixel(w, h) != -1) { // -1 是白色
                    holdBlackPix = true; // 如果不是-1 则是其他颜色
                    break;
                }
            }

            if (holdBlackPix) {
                break;
            }
            top++;
        }

        for (int w = 0; w < bitmap.getWidth(); w++) {
            boolean holdBlackPix = false;
            for (int h = 0; h < bitmap.getHeight(); h++) {
                if (bitmap.getPixel(w, h) != -1) {
                    holdBlackPix = true;
                    break;
                }
            }
            if (holdBlackPix) {
                break;
            }
            left++;
        }

        for (int w = bitmap.getWidth() - 1; w >= 0; w--) {
            boolean holdBlackPix = false;
            for (int h = 0; h < bitmap.getHeight(); h++) {
                if (bitmap.getPixel(w, h) != -1) {
                    holdBlackPix = true;
                    break;
                }
            }
            if (holdBlackPix) {
                break;
            }
            right++;
        }

        for (int h = bitmap.getHeight() - 1; h >= 0; h--) {
            boolean holdBlackPix = false;
            for (int w = 0; w < bitmap.getWidth(); w++) {
                if (bitmap.getPixel(w, h) != -1) {
                    holdBlackPix = true;
                    break;
                }
            }
            if (holdBlackPix) {
                break;
            }
            bottom++;
        }

        // 获取内容区域的宽高
        int cropHeight = bitmap.getHeight() - bottom - top;
        int cropWidth = bitmap.getWidth() - left - right;

        // 获取内容区域的像素点
        int[] newPix = new int[cropWidth * cropHeight];

        int i = 0;
        for (int h = top; h < top + cropHeight; h++) {
            for (int w = left; w < left + cropWidth; w++) {
                newPix[i++] = bitmap.getPixel(w, h);
            }
        }

        // 创建切割后的 bitmap， 针对彩色图，把 newPix 替换为 originBitmap 的 pixs
        return Bitmap.createBitmap(newPix, cropWidth, cropHeight, Bitmap.Config.ARGB_8888);

    }
}
