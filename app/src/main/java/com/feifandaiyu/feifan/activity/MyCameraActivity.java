package com.feifandaiyu.feifan.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.format.Time;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyCameraActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {

    private Button button;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private Button button1;
    private Button button2;

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File file = new File(Environment.getExternalStorageDirectory()+""+ SystemClock.currentThreadTimeMillis()+".png");

            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();


            } catch (Exception e) {
                e.printStackTrace();
            }


            if (file.length() > 0) {
                LogUtils.d("--------"+file.getPath());
                bitmap = BitmapFactory.decodeFile(file.getPath());
                Matrix matrix = new Matrix();
                //通过Matrix把图片旋转90度
                matrix.setRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                Drawable drawable = new BitmapDrawable(bitmap);
                mCamera.stopPreview();
                Canvas canvas = new Canvas(bitmap);
                mSurfaceView.draw(canvas);


                button.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.VISIBLE);
                button1.setEnabled(true);
                button2.setVisibility(View.VISIBLE);

            }

        }
    };
    private TextView tvTime;
    private TextView tvLg;
    private TextView tvLocation;
    private String str;

    /**
     * 当前经纬度
     */
    private LatLng mLoactionLatLng;

    private LocationClient mLocClient;

    private MyLocationListener myLocationListener = new MyLocationListener();
    private double mLatitude;
    private double mLongitude;
    private String addr;
    private ImageView image;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycamera);

        //定位的服务
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

        button = (Button) findViewById(R.id.button);
        mSurfaceView = (SurfaceView) findViewById(R.id.sf_my);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        button1 = (Button) findViewById(R.id.button2);
        button2 = (Button) findViewById(R.id.button3);
        tvTime = (TextView) findViewById(R.id.tv_time_mycamera);
        tvLg = (TextView) findViewById(R.id.tv_lg_mycamera);
        tvLocation = (TextView) findViewById(R.id.tv_location_mycamera);

        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    private void initTextView() {

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month + 1;
        int date = t.monthDay;
        int hour = t.hour + 8; // 0-23
        int minute = t.minute;
        int second = t.second;
        if (minute < 10) {
            str = "时间:" + year + "-" + month + "-" + date + "   " + hour + ":" + 0 + minute + ":" + second;

        } else {
            str = "时间:" + year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":" + second;
        }
        tvTime.setText(str);
        tvLg.setText("经度："+mLatitude+"；"+"纬度："+mLongitude+".");
        tvLocation.setText(addr);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder != null) {
                if (mCamera != null) {
                    setStartPreView(mCamera, mHolder);

                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            initTextView();
            Toast.makeText(MyCameraActivity.this, "baozha", Toast.LENGTH_SHORT).show();
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setPreviewSize(1920, 1080);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            parameters.setJpegQuality(80);
            mCamera.setParameters(parameters);
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    if (b) {
                        mCamera.takePicture(null, null, mPictureCallback);
                    }
                }
            });

        }
        if(view.getId() == R.id.button2){
            Toast.makeText(MyCameraActivity.this, "保存", Toast.LENGTH_SHORT).show();

            image = new ImageView(this);
            image.setImageBitmap(bitmap);
            drawNewBitmap1(image, str);

        }
        if (view.getId() == R.id.button3){
            Toast.makeText(MyCameraActivity.this, "取消", Toast.LENGTH_SHORT).show();
            File file = new File("/sdcard/temp5.png");
            file.delete();
            setStartPreView(mCamera,mHolder);
        }
    }

    private void drawNewBitmap1(ImageView imageView, String str) {

        int width = bitmap.getWidth();
        int hight = bitmap.getHeight();
        System.out.println("宽" + width + "高" + hight);
        Bitmap icon = Bitmap
                .createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
        Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

        Paint photoPaint = new Paint(); // 建立画笔
        photoPaint.setDither(true); // 获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);// 过滤一些

        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
        canvas.drawBitmap(bitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
        TextPaint paint = new TextPaint();
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(30.0f);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        StaticLayout layout = new StaticLayout(str + "\r\n" + "经度:" + mLongitude + "\r\n" + "纬度:" + mLatitude + "\r\n" +
                addr, paint, 600, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);

        canvas.save();
        canvas.restore();

        layout.draw(canvas);

        imageView.setImageBitmap(icon);
        saveMyBitmap(icon);


    }
    public void saveMyBitmap(Bitmap bitmap) {

        MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "水印照片", "description");

        mLocClient.stop();

        MyToast.show(this, "保存成功");

    }

    public Camera getCamera() {
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            mCamera = null;
        }


        return mCamera;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void setStartPreView(Camera camera, SurfaceHolder holder) {
        button.setVisibility(View.VISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        try {
            mCamera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setStartPreView(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mCamera.stopPreview();
        setStartPreView(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            addr = "地址:" + location.getAddrStr();


        }

    }
}
