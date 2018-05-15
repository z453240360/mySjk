package com.dd.mylibrary.utils.photo;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


import com.dd.mylibrary.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;
//import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * SimpleFragment extends TakePhotoFragment {
 * private CustomHelper customHelper;
 *
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * <p>
 * }
 * @Nullable
 * @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
 * View view=inflater.inflate(R.layout.common_layout,null);
 * customHelper = new CustomHelper(this, getTakePhoto(), CustomHelper.rgCrop, CustomHelper.rgCompress,
 * CustomHelper.rgFrom, CustomHelper.rgCropSize, CustomHelper.rgCropTool, CustomHelper.rgShowProgressBar,
 * CustomHelper.etCropHeight, CustomHelper.etCropWidth, 3, CustomHelper.etSize,
 * CustomHelper.etPx);
 * return view;
 * }
 * <p>
 * public void onClick(View view) {
 * customHelper.shoePOP();
 * }
 * <p>
 * SimpleTakePhotoActivity extends MyTakePhotoActivity {
 * private CustomHelper customHelper;
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * customHelper = new CustomHelper(this, getTakePhoto(), CustomHelper.rgCrop, CustomHelper.rgCompress,
 * CustomHelper.rgFrom, CustomHelper.rgCropSize, CustomHelper.rgCropTool, CustomHelper.rgShowProgressBar,
 * CustomHelper.etCropHeight, CustomHelper.etCropWidth, 3, CustomHelper.etSize,
 * CustomHelper.etPx);
 * }
 * <p>
 * public void MyClick(View view) {
 * customHelper.shoePOP();
 * }
 */
public class CustomHelper {

    /**
     * 底部弹窗
     */
    PopupWindow pw = null;

    /**
     * 底部弹窗View
     */
    View pickView;
    /**
     * 全局的LayoutInflater对象，已经完成初始化.
     */
    public LayoutInflater mInflater;

    Context mContext;


    /**
     * 从相册选择照片或从相机拍照，调用此方法弹窗
     */
    public void shoePOP() {
        if (pw == null) {
            pw = PopupWindowHelper.createPopupWindow(pickView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pw.setAnimationStyle(R.style.slide_up_in_down_out);
        }
        pw.showAtLocation(((Activity) mContext).getWindow().getDecorView(),
                Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopupWindowHelper.backgroundAlpha((Activity) mContext, 1f);
            }
        });
        PopupWindowHelper.backgroundAlpha((Activity) mContext, 0.7f);
    }

    /**
     * 选择相册或相机的底部弹窗控件实例化
     */
    private void initPickView() {
        pickView = mInflater.inflate(R.layout.layout_select_picture, null);
        pickView.findViewById(R.id.photoFromAlbum_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        pw.dismiss();
                        PopupWindowHelper.backgroundAlpha((Activity) mContext, 1f);

                        PictureSelector.create((Activity) mContext)
                                .openCamera(PictureMimeType.ofImage())
                                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                .maxSelectNum(SMAX)// 最大图片选择数量 int
                                .minSelectNum(SMIN)// 最小选择数量 int
                                .imageSpanCount(IMAGESPANCOUNT)// 每行显示个数 int
                                .selectionMode(MORS)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                .previewImage(ISPREVIEWIMAGE)// 是否可预览图片 true or false
                                .previewVideo(ISPREVIEWVIDEO)// 是否可预览视频 true or false
                                .enablePreviewAudio(ISENABLEPREVIEWAUDIO) // 是否可播放音频 true or false
//                                .compressGrade(COMPRESSGRADE)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                                .isCamera(ISCAMERA)// 是否显示拍照按钮 true or false
                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                                .enableCrop(ENABLECROP)// 是否裁剪 true or false
                                .compress(ISZOOMANIM)// 是否压缩 true or false
//                                .compressMode(COMPRESSMODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
//                                                .glideOverride(0.5f)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                .hideBottomControls(HIDEBOTTOMCONTROLS)// 是否显示uCrop工具栏，默认不显示 true or false
                                .isGif(ISGIF)// 是否显示gif图片 true or false
                                .freeStyleCropEnabled(FREESTYLECROPENABLED)// 裁剪框是否可拖拽 true or false
                                .circleDimmedLayer(CIRCLEDIMMEDLAYER)// 是否圆形裁剪 true or false
                                .showCropFrame(SHOWCROPFRAME)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                .showCropGrid(SHOWCROPGRID)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                .openClickSound(OPENCLICKSOUND)// 是否开启点击声音 true or false
                                .selectionMedia(SELECTIONMEDIA)// 是否传入已选图片 List<LocalMedia> list
                                .previewEggs(PREVIEWEGGS)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                .cropCompressQuality(CROPCOMPRESSQUALITY)// 裁剪压缩质量 默认90 int
//                                .compressMaxKB(COMPRESSMAXKB)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
//                                .compressWH(COMPRESSWHWHIDE, COMPRESSWHHEIGHT) // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                                .cropWH(COMPRESSWHWHIDE + 100, COMPRESSWHHEIGHT + 100)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                                .rotateEnabled(ROTATEENABLED) // 裁剪是否可旋转图片 true or false
                                .minimumCompressSize(100)
                                .scaleEnabled(SCALEENABLED)// 裁剪是否可放大缩小图片 true or false
                                .videoQuality(1)// 视频录制质量 0 or 1 int
//                                .videoSecond(VIDEOSECOND)// 显示多少秒以内的视频or音频也可适用 int
                                .recordVideoSecond(RECORDVIDEOSECOND)//视频秒数录制 默认60s int
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code 

                    }
                });
        pickView.findViewById(R.id.photoFromCamera_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        pw.dismiss();
                        PopupWindowHelper.backgroundAlpha((Activity) mContext, 1f);

                        PictureSelector.create((Activity) mContext)
                                .openGallery(POA)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                .maxSelectNum(SMAX)// 最大图片选择数量 int
                                .minSelectNum(SMIN)// 最小选择数量 int
                                .imageSpanCount(IMAGESPANCOUNT)// 每行显示个数 int
                                .selectionMode(MORS)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                .previewImage(ISPREVIEWIMAGE)// 是否可预览图片 true or false
                                .previewVideo(ISPREVIEWVIDEO)// 是否可预览视频 true or false
                                .enablePreviewAudio(ISENABLEPREVIEWAUDIO) // 是否可播放音频 true or false
                                .compress(true)
//                                .compressGrade(COMPRESSGRADE)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                                .isCamera(ISCAMERA)// 是否显示拍照按钮 true or false
                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                                .enableCrop(ENABLECROP)// 是否裁剪 true or false
                                .compress(ISZOOMANIM)// 是否压缩 true or false
//                                .compressMode(COMPRESSMODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                                //                .glideOverride(0.5f)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                .hideBottomControls(HIDEBOTTOMCONTROLS)// 是否显示uCrop工具栏，默认不显示 true or false
                                .isGif(ISGIF)// 是否显示gif图片 true or false
                                .freeStyleCropEnabled(FREESTYLECROPENABLED)// 裁剪框是否可拖拽 true or false
                                .circleDimmedLayer(CIRCLEDIMMEDLAYER)// 是否圆形裁剪 true or false
                                .showCropFrame(SHOWCROPFRAME)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                .showCropGrid(SHOWCROPGRID)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                .openClickSound(OPENCLICKSOUND)// 是否开启点击声音 true or false
                                .selectionMedia(SELECTIONMEDIA)// 是否传入已选图片 List<LocalMedia> list
                                .previewEggs(PREVIEWEGGS)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                .cropCompressQuality(CROPCOMPRESSQUALITY)// 裁剪压缩质量 默认90 int
//                                .compressMaxKB(COMPRESSMAXKB)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
//                                .compressWH(COMPRESSWHWHIDE, COMPRESSWHHEIGHT) // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
//                                .cropWH(COMPRESSWHWHIDE + 100, COMPRESSWHHEIGHT + 100)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                                .rotateEnabled(ROTATEENABLED) // 裁剪是否可旋转图片 true or false
                                .scaleEnabled(SCALEENABLED)// 裁剪是否可放大缩小图片 true or false
                                .videoQuality(1)// 视频录制质量 0 or 1 int
//                                .videoSecond(VIDEOSECOND)// 显示多少秒以内的视频or音频也可适用 int
                                .recordVideoSecond(RECORDVIDEOSECOND)//视频秒数录制 默认60s int
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code   
                    }
                });
        pickView.findViewById(R.id.photoCancle_btn).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                        pw.dismiss();
                        PopupWindowHelper.backgroundAlpha((Activity) mContext, 1f);

                    }
                });
        pickView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                PopupWindowHelper.backgroundAlpha((Activity) mContext, 1f);

                pw.dismiss();
            }
        });
    }


    public CustomHelper(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        initPickView();
        init();
    }

    public static int POA = PictureMimeType.ofImage();
    public static int SMAX = 3;
    public static int SMIN = 1;
    public static int IMAGESPANCOUNT = 4;
    public static int MORS = PictureConfig.SINGLE;
//    public static int COMPRESSGRADE = Luban.CUSTOM_GEAR;
    public static int ASPECT_RATIO_X = 4;
    public static int ASPECT_RATIO_Y = 3;
    public static int CROPCOMPRESSQUALITY = 30;
    public static int COMPRESSMAXKB = 50;
    public static int COMPRESSWHWHIDE = 500;
    public static int COMPRESSWHHEIGHT = 500;
    public static int VIDEOSECOND = 10;
    public static int RECORDVIDEOSECOND = 60;
    public static boolean ISPREVIEWIMAGE = true;
    public static boolean ISPREVIEWVIDEO = false;
    public static boolean ISENABLEPREVIEWAUDIO = false;
    public static boolean ISCAMERA = false;
    public static boolean ISZOOMANIM = true;
    public static boolean ENABLECROP = true;
    public static boolean ISGIF = true;
    public static boolean FREESTYLECROPENABLED = false;
    public static boolean SHOWCROPFRAME = true;
    public static boolean SHOWCROPGRID = false;
    public static boolean CIRCLEDIMMEDLAYER = false;
    public static boolean HIDEBOTTOMCONTROLS = false;
    public static boolean OPENCLICKSOUND = false;
    public static List<LocalMedia> SELECTIONMEDIA = null;
    public static boolean PREVIEWEGGS = true;
    public static boolean ROTATEENABLED = true;
    public static boolean SCALEENABLED = true;
//    public static int COMPRESSMODE = LUBAN_COMPRESS_MODE;

    private void init() {
        POA = PictureMimeType.ofImage();
        SMAX = 1;
        IMAGESPANCOUNT = 4;
        SMIN = 1;
        MORS = PictureConfig.SINGLE;
//        COMPRESSGRADE = Luban.CUSTOM_GEAR;
        ASPECT_RATIO_X = 4;
        ASPECT_RATIO_Y = 3;
        CROPCOMPRESSQUALITY = 90;
        COMPRESSMAXKB = 50;
        COMPRESSWHWHIDE = 500;
        COMPRESSWHHEIGHT = 500;
        VIDEOSECOND = 10;
        RECORDVIDEOSECOND = 60;
        ISPREVIEWIMAGE = true;
        ISPREVIEWVIDEO = false;
        ISENABLEPREVIEWAUDIO = false;
        ISCAMERA = false;
        ISZOOMANIM = true;
        ENABLECROP = true;
        ISGIF = true;
        FREESTYLECROPENABLED = false;
        SHOWCROPFRAME = true;
        SHOWCROPGRID = false;
        CIRCLEDIMMEDLAYER = false;
        HIDEBOTTOMCONTROLS = false;
        OPENCLICKSOUND = false;
        SELECTIONMEDIA = null;
        PREVIEWEGGS = true;
        ROTATEENABLED = true;
        SCALEENABLED = true;
//        COMPRESSMODE = LUBAN_COMPRESS_MODE;
    }


}
