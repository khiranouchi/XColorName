package jp.ac.titech.itpro.sdl.xcolorname;

import java.io.ByteArrayOutputStream;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;//deprecated
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ShootActivity extends AppCompatActivity implements Camera.PreviewCallback {
    private final static String TAG = "ShootActivity";

    private Camera mCamera;
    private CameraView cameraView;
    private FrameLayout previewLayout;

    private FloatingActionButton shootButton;

    private TextView hueTextview;
    private TextView saturationTextview;
    private TextView valueTextview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_shoot);


        cameraView = new CameraView(this);
        previewLayout = findViewById(R.id.preview_layout);
        previewLayout.addView(cameraView);



        shootButton = findViewById(R.id.shoot_button);
        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "cameraView.onClick");
                startActivity(new Intent(ShootActivity.this, EditActivity.class));
            }
        });


        hueTextview = findViewById(R.id.hue_textview);
        saturationTextview = findViewById(R.id.saturation_textview);
        valueTextview = findViewById(R.id.value_textview);
        hueTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "hueTextview.onClick");
                // reset value of hue_seekbar
            }
        });
        saturationTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "saturationTextview.onClick");
                // reset value of saturation_seekbar
            }
        });
        valueTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "valueTextview.onClick");
                // reset value of value_seekbar
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if(cameraView != null){
            mCamera = Camera.open();
            //mCamera.setPreviewCallback(this);//TODO need to be fixed<<<
            cameraView.SetCamera(mCamera);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if(mCamera != null){
            cameraView.SetCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }



    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d(TAG, "onPreviewFrame");

        int previewWidth = camera.getParameters().getPreviewSize().width;
        int previewHeight = camera.getParameters().getPreviewSize().height;

        YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, previewWidth, previewHeight, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, previewWidth, previewHeight), 80, baos);



    }



}
