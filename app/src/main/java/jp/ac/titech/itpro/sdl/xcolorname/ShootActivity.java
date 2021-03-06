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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Activity which previews camera and get color at the center of the camera.
 * Camera parameter EV / WB can be changed by seekBar / button.
 * When shootButton is pressed, start {@linkplain EditActivity}.
 */
public class ShootActivity extends AppCompatActivity implements Camera.PreviewCallback {
    private final static String TAG = "ShootActivity";
    private final static String KEY_SIS_EV_PROGRESS = "ShootActivity.KEY_SIS_EV_PROGRESS";
    private final static String KEY_SIS_WB_TYPE = "ShootActivity.KEY_SIS_WB_TYPE";

    private View pickedColorView;
    private int pickedColor;

    private Camera mCamera;
    private CameraView cameraView;
    private FrameLayout previewLayout;

    private FloatingActionButton shootButton;

    private TextView evTextView;
    private TextView wbTextView;
    private SeekBar evSeekBar;
    private Button wbAutoButton, wbCloudyButton, wbDaylightButton, wbFluorescentButton, wbIncandescentButton;
    private int biasEvSeekBar; // eg. if this==12, scale starts from -12 instead of 0

    private int progressEvSeekBar; //for saved instances (to init evSeekBar in onResume)
    private int selectedWbButton; //for saved instances (to init wbXXXButton in onResume)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_shoot);

        // init saved instances
        progressEvSeekBar = -1;
        selectedWbButton = -1;
        if(savedInstanceState != null){
            progressEvSeekBar = savedInstanceState.getInt(KEY_SIS_EV_PROGRESS);
            selectedWbButton = savedInstanceState.getInt(KEY_SIS_WB_TYPE);
        }

        pickedColorView = findViewById(R.id.picked_color_view);
        pickedColor = -1;

        // create cameraView(CameraView) and add it to previewLayout(FrameLayout)
        mCamera = null;
        cameraView = new CameraView(this);
        previewLayout = findViewById(R.id.preview_layout);
        previewLayout.addView(cameraView);

        // start EditActivity when shootButton is pressed
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

        // reset EV(seekBar) / WB(button) when each textView is clicked
        evTextView = findViewById(R.id.ev_textview);
        wbTextView = findViewById(R.id.wb_textview);
        evTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "evTextView.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    evSeekBar.setProgress(biasEvSeekBar);
                }
            }
        });
        wbTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "wbTextView.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    cameraView.cameraParams.setWhiteBalance(cameraView.cameraParams.MY_WB_AUTO);
                }
            }
        });

        // update camera parameter EV when seekBar is changed
        evSeekBar = findViewById(R.id.ev_seekbar);
        evSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 Log.d(TAG, "evSeekBar.onProgressChanged");
                 if(cameraView != null && cameraView.cameraParams != null) {
                     cameraView.cameraParams.setExposure(progress - biasEvSeekBar);
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

        // change camera parameter WB when each button is clicked
        wbAutoButton = findViewById(R.id.wb_auto_button);
        wbCloudyButton = findViewById(R.id.wb_cloudy_button);
        wbDaylightButton = findViewById(R.id.wb_daylight_button);
        wbFluorescentButton = findViewById(R.id.wb_fluorescent_button);
        wbIncandescentButton = findViewById(R.id.wb_incandescent_button);
        wbAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "wbAutoButton.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    selectedWbButton = cameraView.cameraParams.MY_WB_AUTO;
                    cameraView.cameraParams.setWhiteBalance(selectedWbButton);
                }
            }
        });
        wbCloudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "wbCloudyButton.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    selectedWbButton = cameraView.cameraParams.MY_WB_CLOUDY;
                    cameraView.cameraParams.setWhiteBalance(selectedWbButton);
                }
            }
        });
        wbDaylightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "wbDaylightButton.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    selectedWbButton = cameraView.cameraParams.MY_WB_DAYLIGHT;
                    cameraView.cameraParams.setWhiteBalance(selectedWbButton);
                }
            }
        });
        wbFluorescentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "wbFluorescentButton.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    selectedWbButton = cameraView.cameraParams.MY_WB_FLUORESCENT;
                    cameraView.cameraParams.setWhiteBalance(selectedWbButton);
                }
            }
        });
        wbIncandescentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "wbIncandescentButton.onClick");
                if(cameraView != null && cameraView.cameraParams != null) {
                    selectedWbButton = cameraView.cameraParams.MY_WB_INCANDESCENT;
                    cameraView.cameraParams.setWhiteBalance(selectedWbButton);
                }
            }
        });
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
                biasEvSeekBar = - cameraView.cameraParams.getMinExposure();
                evSeekBar.setMax(cameraView.cameraParams.getMaxExposure() + biasEvSeekBar);

                // init EV
                if(progressEvSeekBar == -1) {
                    evSeekBar.setProgress(biasEvSeekBar);
                }else{
                    evSeekBar.setProgress(progressEvSeekBar);
                }

                // init WB
                if(selectedWbButton == cameraView.cameraParams.MY_WB_CLOUDY){
                    wbCloudyButton.performClick();
                }else if(selectedWbButton == cameraView.cameraParams.MY_WB_DAYLIGHT){
                    wbDaylightButton.performClick();
                }else if(selectedWbButton == cameraView.cameraParams.MY_WB_FLUORESCENT){
                    wbFluorescentButton.performClick();
                }else if(selectedWbButton == cameraView.cameraParams.MY_WB_INCANDESCENT){
                    wbIncandescentButton.performClick();
                }else{// AUTO or -1
                    wbAutoButton.performClick();
                }
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged");
        int x = previewLayout.getWidth() / 2;
        int y = previewLayout.getHeight() / 2;
        AuxiliaryView auxiliaryView = new AuxiliaryView(this, x, y); //set auxiliary cross
        previewLayout.addView(auxiliaryView);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SIS_EV_PROGRESS, evSeekBar.getProgress());
        outState.putInt(KEY_SIS_WB_TYPE, selectedWbButton);
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
