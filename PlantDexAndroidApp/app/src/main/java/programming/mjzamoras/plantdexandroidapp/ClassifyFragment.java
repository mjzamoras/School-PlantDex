package programming.mjzamoras.plantdexandroidapp;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends Fragment {

    private TextureView textureView;
    private Button btnCapture, btnOpen, btnReset;

    public ClassifyFragment() {
        // Required empty public constructor
    }

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static{
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private String cID;
    private CameraDevice device;
    private CameraCaptureSession captureSession;
    private CaptureRequest.Builder builder;
    private Size imgSize;
    private ImageReader reader;

    private File file;
    private static final int REQUEST_PERMISSION_CODE = 200;
    private boolean hasFlash;
    private Handler bgHandler;
    private HandlerThread thread;
    private boolean resetPreview = false;

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    CameraDevice.StateCallback stateCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            device = camera;
            createImagePreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            device.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            device.close();
            device = null;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_classify, container, false);

        textureView = (TextureView) v.findViewById(R.id.textureView);
        btnCapture = (Button) v.findViewById(R.id.btnCapture);
        btnOpen = (Button) v.findViewById(R.id.btnOpen);
        btnReset = (Button) v.findViewById(R.id.btnReset);

        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snapShot();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createImagePreview();
                btnReset.setEnabled(false);
                btnCapture.setEnabled(true);
            }
        });

        return v;
    }

    private void snapShot(){
        if(device == null){
            return;
        }

        try{
            CameraManager cameraManager = (CameraManager)getContext().getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(device.getId());
            Size[] sizes = null;

            if(characteristics != null){
                sizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.JPEG);
            }

            int width = 640;
            int height = 480;
            if(sizes != null & sizes.length > 0){
                width = sizes[0].getWidth();
                height = sizes[0].getHeight();
            }
            final ImageReader r = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurface = new ArrayList<>(2);
            outputSurface.add(r.getSurface());
            outputSurface.add(new Surface(textureView.getSurfaceTexture()));

            CaptureRequest.Builder b = device.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            b.addTarget(r.getSurface());
            b.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            int rotation = 0;
            b.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));

            file = new File(Environment.getExternalStorageDirectory() + "/pdex" + UUID.randomUUID().toString() + ".jpg");
            ImageReader.OnImageAvailableListener rListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    try{
                        image = r.acquireLatestImage();
                        ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[byteBuffer.capacity()];
                        byteBuffer.get(bytes);
                        save(bytes);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        {
                            if(image != null){
                                image.close();
                            }
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException{
                    OutputStream oStream = null;
                    try {
                        oStream =new FileOutputStream(file);
                        oStream.write(bytes);
                    }finally {
                        if(oStream != null){
                            oStream.close();
                        }
                    }

                }
            };

            r.setOnImageAvailableListener(rListener, bgHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Toast.makeText(getContext(), "Saved " + file, Toast.LENGTH_LONG).show();

                    //------------------------------------------
                    btnReset.setEnabled(true);
                    btnCapture.setEnabled(false);

                }
            };

            device.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try{
                        session.capture(builder.build(), captureListener, bgHandler);
                    }catch (CameraAccessException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, bgHandler);
        }catch (NullPointerException e){
            Toast.makeText(getContext(), "Please let the page load", Toast.LENGTH_LONG).show();
            return;
        }catch (CameraAccessException e2){
            e2.printStackTrace();
            return;
        }


    }

    private void createImagePreview(){
        try{
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert  texture != null;
            texture.setDefaultBufferSize(imgSize.getWidth(), imgSize.getHeight());
            Surface surface = new Surface(texture);
            builder = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.addTarget(surface);
            device.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if(device == null){
                        return;

                    }
                    captureSession = session;
                    changePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(getContext(), "Changed", Toast.LENGTH_LONG).show();
                }
            }, null );
        }catch(CameraAccessException ex){
            ex.printStackTrace();
        }
    }

    private void openCamera(){
        CameraManager manager = (CameraManager)getContext().getSystemService(Context.CAMERA_SERVICE);
        try{
            cID = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cID);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imgSize = map.getOutputSizes(SurfaceTexture.class)[0];

            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_PERMISSION_CODE);
                return;
            }
            manager.openCamera(cID, stateCallBack, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void changePreview(){
        if(device == null){
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
        builder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        try{
            captureSession.setRepeatingRequest(builder.build(), null, bgHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CODE){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Camera Access Restricted Without Proper Authorization", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startBgThread();
        if(textureView.isAvailable()){
            openCamera();
        }else{
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    public void onPause() {
        stopBgThread();
        super.onPause();
    }

    private void startBgThread(){
        thread  = new HandlerThread("Camera Background");
        thread.start();
        bgHandler = new Handler(thread.getLooper());
    }

    private void stopBgThread(){
        thread.quitSafely();
        try{
            thread.join();
            thread = null;
            bgHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
