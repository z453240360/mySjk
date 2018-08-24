package com.sanjianke.mysjk.bussiness.mine.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.base.BaseActivity;
import com.sanjianke.mysjk.bean.MusicBean;
import com.sanjianke.mysjk.bussiness.mine.adapter.MusicAdapter;
import com.sanjianke.mysjk.bussiness.mine.constract.MusicContract;
import com.sanjianke.mysjk.bussiness.mine.presenter.MusicPresenter;
import com.sanjianke.mysjk.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicActivity extends BaseActivity<MusicPresenter> implements MusicContract.View {

    @BindView(R.id.mBtn_music)
    Button mBtnMusic;
    @BindView(R.id.mRv_music)
    RecyclerView mRvMusic;
    @BindView(R.id.mSeekBar)
    SeekBar mSeekBar;
    @BindView(R.id.mTxt_musicTime)
    TextView mTxtMusicTime;
    @BindView(R.id.mBtn_start)
    RadioButton mBtnStart;
    @BindView(R.id.mBtn_stop)
    RadioButton mBtnStop;
    @BindView(R.id.mBtn_pause)
    RadioButton mBtnPause;
    @BindView(R.id.mBtn_next)
    RadioButton mBtnNext;
    @BindView(R.id.mBtn_last)
    RadioButton mBtnLast;
    private LinearLayoutManager manager;
    private MusicAdapter adapter;
    private List<MusicBean> mDatas = new ArrayList<>();
    private MediaPlayer player;
    private Intent intent;
    private String currentMps;

    @Override
    public int getLayoutId() {
        return R.layout.activity_music;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    public void loadData() {
        registerReceiver(new MyReceiver(),new IntentFilter("com.dd.music.broad"));
        intent = new Intent(mContext, MusicService.class);
        initRv();
        initSeekar();

    }

    private void initSeekar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startMusicService("stophandler");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                intent.putExtra("pos",seekBar.getProgress());
                startMusicService("seekTo");
            }
        });
    }

    private void initRv() {
        manager = new LinearLayoutManager(mContext);
        adapter = new MusicAdapter(mContext, mDatas);
        mRvMusic.setAdapter(adapter);
        mRvMusic.setLayoutManager(manager);
        adapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View view) {
                currentMps= mDatas.get(pos).getPath();
//                intent.putExtra("mp3", currentMps);
                startMusicService("start");
            }
        });

        mPresenter.main();
    }

    @Override
    public void main(List<MusicBean> stringList) {
        if (stringList.size() == 0) {
            return;
        } else {
            currentMps = stringList.get(0).getPath();
        }
        mDatas.clear();
        mDatas.addAll(stringList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }


    @OnClick(R.id.mBtn_music)
    public void onViewClicked() {
//       if (player!=null){
//           player.stop();
//       }
    }


    @OnClick({R.id.mBtn_start, R.id.mBtn_stop, R.id.mBtn_pause, R.id.mBtn_next, R.id.mBtn_last})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBtn_start:
                startMusicService("start");
                break;
            case R.id.mBtn_stop:
                startMusicService("stop");
                break;
            case R.id.mBtn_pause:
                startMusicService("pause");

                break;
            case R.id.mBtn_next:
                startMusicService("next");
                break;
            case R.id.mBtn_last:
                startMusicService("last");
                break;
        }
    }

    private void startMusicService(String states) {
        intent.putExtra("mp3", currentMps);
        intent.putExtra("music", states);
        startService(intent);
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int max = intent.getIntExtra("max",0);
            int pos = intent.getIntExtra("pos", 0);
            mSeekBar.setMax(max);
            mSeekBar.setProgress(pos);
        }
    }
}
