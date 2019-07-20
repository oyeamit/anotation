package de.uni_passau.dpss.annotation.View.MainMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import de.uni_passau.dpss.annotation.R;
import de.uni_passau.dpss.annotation.View.Others.CropForOcr;

public class FullScreenShot extends Activity {

    MediaProjectionManager mediaProjectionManager;
    MediaProjection mediaProjection;
    private Runnable timeoutRunnable;
    public final static int ERROR_CODE_TIMEOUT = 1;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(null);
        mediaProjectionManager = (MediaProjectionManager)getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);


                WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                final DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                Point size = new Point();
                display.getRealSize(size);
                final int mWidth = size.x;
                final int mHeight = size.y;
                int mDensity = metrics.densityDpi;
                final boolean isPortrait = mHeight > mWidth;

                //Create a imageReader for catch result
                final ImageReader mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGBA_8888, 2);

                final Handler handler = new Handler();

                //Take a screenshot
                int flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY | DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
                mediaProjection.createVirtualDisplay("screen-mirror", mWidth, mHeight, mDensity, flags, mImageReader.getSurface(), null, handler);

                //convert result into image
                timeoutRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mImageReader.setOnImageAvailableListener(null, handler);
                        mImageReader.close();
                        mediaProjection.stop();
                    }
                };

                mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader reader) {
                        reader.setOnImageAvailableListener(null, handler);

                        Image image = reader.acquireLatestImage();

                        final Image.Plane[] planes = image.getPlanes();
                        final ByteBuffer buffer = planes[0].getBuffer();

                        int pixelStride = planes[0].getPixelStride();
                        int rowStride = planes[0].getRowStride();
                        int rowPadding = rowStride - pixelStride * metrics.widthPixels;
                        // create bitmap
                        Bitmap bmp = Bitmap.createBitmap(metrics.widthPixels + (int) ((float) rowPadding / (float) pixelStride), metrics.heightPixels, Bitmap.Config.ARGB_8888);
                        bmp.copyPixelsFromBuffer(buffer);

                        image.close();
                        reader.close();

                        Bitmap realSizeBitmap = Bitmap.createBitmap(bmp, 0, 0, metrics.widthPixels, bmp.getHeight());
                        bmp.recycle();

                        /* do something with [realSizeBitmap] */
                        Intent intent = new Intent(FullScreenShot.this, CropForOcr.class);
                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        realSizeBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                        intent.putExtra("byteArray", bs.toByteArray());
                        startActivity(intent);
                    }
                }, handler);



                mediaProjection.stop();
                this.finish();
            }
        }
    }
}
