package com.sanjianke.mysjk.bussiness.mine.presenter;


import android.net.Uri;

import com.dd.mylibrary.utils.FileUtils;
import com.dd.mylibrary.utils.SdCarcUtils;
import com.sanjianke.mysjk.base.BasePresenter;
import com.sanjianke.mysjk.bean.MusicBean;
import com.sanjianke.mysjk.bussiness.mine.activity.MusicActivity;
import com.sanjianke.mysjk.bussiness.mine.activity.SdCardActivity;
import com.sanjianke.mysjk.bussiness.mine.constract.MineContract;
import com.sanjianke.mysjk.bussiness.mine.constract.MusicContract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicPresenter extends BasePresenter<MusicActivity> implements MusicContract.Presenter {

    private List<MusicBean> stringList;

    @Override
    public void main() {
        stringList = new ArrayList<>();

        File[] sdFile = SdCarcUtils.getSdFile().listFiles();
        testLoopOutAllFileName(SdCarcUtils.getSdFile().getAbsolutePath());
        mView.main(stringList);
    }


    /**
     * 递归读取文件夹下的 所有文件
     *
     * @param testFileDir 文件名或目录名
     */
    private void testLoopOutAllFileName(String testFileDir) {
        if (testFileDir == null) {
            //因为new File(null)会空指针异常,所以要判断下
            return;
        }
        File[] testFile = new File(testFileDir).listFiles();
        if (testFile == null) {
            return;
        }
        for (File file : testFile) {
            if (file.isFile()) {
                String name = file.getName();
                if (name.endsWith(".mp3") || name.endsWith(".MP3")) {

                    MusicBean musicBean = new MusicBean();
                    musicBean.setCheck(false);
                    musicBean.setName(file.getName());
                    musicBean.setPath(file.getAbsolutePath());
                    musicBean.setSelect(false);
                    musicBean.setSize(file.length() + "");
//                    Uri uriWithN = FileUtils.getUriWithN(mContext, file);

                    Uri parse = Uri.parse(file.getAbsolutePath());
                    musicBean.setUri(parse);
                    String ringDuring = getRingDuring(file.getAbsolutePath());
                    musicBean.setTime(ringDuring);
                    stringList.add(musicBean);
                }
            } else if (file.isDirectory()) {
                testLoopOutAllFileName(file.getPath());
            } else {
                System.out.println("文件读入有误！");
            }
        }
    }

    public static String getRingDuring(String mUri) {
        String duration = null;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            }
            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        return duration;
    }

}
