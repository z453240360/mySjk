package com.sanjianke.mysjk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sanjianke.mysjk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZxingActivity extends AppCompatActivity implements QRCodeView.Delegate{

    @BindView(R.id.zxingview)
    ZXingView zxingview;
    @BindView(R.id.mBtn_light)
    Button mBtnLight;
    @BindView(R.id.mBtn_tesst)
    Button mBtnTesst;
    @BindView(R.id.mEd_result)
    EditText mEd_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.bind(this);


        zxingview.startSpot();
        zxingview.setDelegate(this);

    }

    @OnClick({R.id.mBtn_light, R.id.mBtn_tesst,R.id.mBtn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBtn_light:
                zxingview.openFlashlight();
                break;
            case R.id.mBtn_tesst:
                zxingview.closeFlashlight();
                break;

            case R.id.mBtn_start:
                zxingview.startSpotDelay(1000);
                break;
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        mEd_result.setText(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(ZxingActivity.this, "请打开相机权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        zxingview.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zxingview.onDestroy();
    }
}
