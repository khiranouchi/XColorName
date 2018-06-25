package jp.ac.titech.itpro.sdl.xcolorname;

import java.io.ByteArrayOutputStream;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.SeekBar;
import android.widget.TextView;

public class ShootActivity extends AppCompatActivity implements Camera.PreviewCallback {
    private final static String TAG = "ShootActivity";

    private View pickedColorView;
    private int pickedColor;

    private Camera mCamera;
    private CameraView cameraView;
    private FrameLayout previewLayout;

    private FloatingActionButton shootButton;

    private TextView evTextView;
    private TextView wbTextView;
    private SeekBar evSeekBar;
    private SeekBar wbSeekBar;
    private int vaisEvSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_shoot);

        pickedColorView = findViewById(R.id.picked_color_view);
        pickedColor = -1;

        mCamera = null;
        cameraView = new CameraView(this);
        previewLayout = findViewById(R.id.preview_layout);
        previewLayout.addView(cameraView);

        shootButton = findViewById(R.id.shoot_button);
        shootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "cameraView.onClick");
                Intent intent = new Intent(ShootActivity.this, EditActivity.class);
                intent.putExtra(getString(R.string.key_picked_color), pickedColor);
                startActivity(intent);
            }
        });

        evTextView = findViewById(R.id.ev_textview);
        wbTextView = findViewById(R.id.wb_textview);
        evTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "evTextView.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    evSeekBar.setProgress(vaisEvSeekBar);
                }
            }
        });
        wbTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "wbTextView.onClick");
                // reset value of wb_seekbar TODO
            }
        });

        evSeekBar = findViewById(R.id.ev_seekbar);
        wbSeekBar = findViewById(R.id.wb_seekbar);
        evSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 Log.d(TAG, "evSeekBar.onProgressChanged");
                 if(cameraView != null && cameraView.cameraParams != null) {
                     cameraView.cameraParams.setExposure(progress - vaisEvSeekBar);
                 }
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {
             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {
             }
         }
        );




    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if(cameraView != null){
            mCamera = Camera.open();
            mCamera.setPreviewCallback(this);
            cameraView.setCamera(mCamera);

            if(cameraView.cameraParams != null){
                vaisEvSeekBar = - cameraView.cameraParams.getMinExposure();
                evSeekBar.setMax(cameraView.cameraParams.getMaxExposure() + vaisEvSeekBar);
                evSeekBar.setProgress(vaisEvSeekBar);



            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if(mCamera != null){
            cameraView.setCamera(null);
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }



    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d(TAG, "onPreviewFrame");

        // trans yuv to bitmap
        int width = camera.getParameters().getPreviewSize().width;
        int height = camera.getParameters().getPreviewSize().height;
        YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 80, baoStream);
        byte[] bArray = baoStream.toByteArray();
        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        bitmapFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeByteArray(bArray, 0, bArray.length, bitmapFactoryOptions);

        pickedColor = bmp.getPixel(width / 2, height / 2);
        pickedColorView.setBackgroundColor(pickedColor);
    }



}
