package jp.ac.titech.itpro.sdl.xcolorname;

import java.io.IOException;
import java.util.List;
import android.content.Context;
import android.hardware.Camera;//deprecated
import android.hardware.Camera.Size;//deprecated
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";
    private SurfaceHolder holder;
    private Camera camera;

    public CameraParams cameraParams;

    public CameraView(Context context) {
        super(context);
        holder = getHolder();
        this.holder.addCallback(this);
        this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        camera = null;
        cameraParams = null;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        if(camera != null){
            cameraParams = new CameraParams();
            this.camera.setDisplayOrientation(90);//TODO kari<<<
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }catch(IOException e){
            Log.d(TAG, R.string.exception_io_camera_preview + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = camera.getParameters();
        List<Size> sizes = parameters.getSupportedPreviewSizes();
        Size optimalSize = getOptimalPreviewSize(sizes, width, height);
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        camera.setParameters(parameters);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double)w/h;
        if(sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Size size: sizes) {
            double ratio = (double)size.width/size.height;
            if(Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if(Math.abs(size.height - targetHeight) < minDiff){
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if(optimalSize == null){
            minDiff = Double.MAX_VALUE;
            for(Size size : sizes){
                if(Math.abs(size.height - targetHeight) < minDiff){
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }


    public class CameraParams {
        private int minExposure, maxExposure;
        private int exposure;


        private CameraParams() {
            minExposure = camera.getParameters().getMinExposureCompensation();
            maxExposure = camera.getParameters().getMaxExposureCompensation();


        }

        public int getMinExposure() {
            return minExposure;
        }

        public int getMaxExposure() {
            return maxExposure;
        }

        public void setExposure(int value) {
            if(value < minExposure){
                value = minExposure;
            }else if(value > maxExposure){
                value = maxExposure;
            }
            Camera.Parameters params = camera.getParameters();
            params.setExposureCompensation(value);
            camera.setParameters(params);
        }




    }
}
