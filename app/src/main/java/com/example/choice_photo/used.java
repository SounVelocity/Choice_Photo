package com.example.choice_photo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class used extends AppCompatActivity {

    Button reset, save;
    DrawView drawView;
    Bitmap bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used);
        setTitle("입술에 발라보세요");

//        Intent cu = getIntent();
//        byte[] arr = getIntent().getByteArrayExtra("image");
//        bb = BitmapFactory.decodeByteArray(arr,0,arr.length);
//        bb = BitmapFactory.decodeResource(getResources(),R.drawable.dada);


        LinearLayout picture = (LinearLayout) findViewById(R.id.picture);
        drawView = (DrawView) new DrawView(this);
        picture.addView(drawView);

        clickIcons();

        Resources r = Resources.getSystem();
        Configuration config = r.getConfiguration();
        onConfigurationChanged(config);
    }
        private void clickIcons() {


            reset = (Button) findViewById(R.id.reset);
            reset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent re = getIntent();
                    finish();
                    startActivity(re);
                }
            });

            save = (Button) findViewById(R.id.save);
            save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    View rootView = getWindow().getDecorView();

                    File screenShot = ScreenShot(rootView);
                    if (screenShot != null) {
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
                    }

                }
            });
        }
        public File ScreenShot(View view) {
            drawView.setDrawingCacheEnabled(true);

            Bitmap facesave = drawView.getDrawingCache();

            String filename = "saveface.png";
            File file = new File(Environment.getExternalStorageDirectory() + "Pictures",filename);
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(file);
                facesave.compress(Bitmap.CompressFormat.PNG,90,os);
                os.close();
                Toast.makeText(this,"저장 완료!", Toast.LENGTH_SHORT).show();
            }catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"파일 에러!", Toast.LENGTH_SHORT).show();
                return null;
            }
            drawView.setDrawingCacheEnabled(false);
            return file;

        }


    public class DrawView extends View {

        private Paint paint = new Paint();
        private Path path = new Path();
        private int x, y;
        String imagePath = null;

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            bb = BitmapFactory.decodeResource(getResources(),R.drawable.dada);

            int picX = (this.getWidth() - bb.getWidth()) / 2;
            int picY = (this.getHeight() - bb.getHeight()) / 2;

            canvas.drawBitmap(bb,picX,picY,null);

            bb.recycle();

            paint.setARGB(80, 250, 30, 55);

            paint.setStyle(Paint.Style.STROKE);

            paint.setStrokeWidth(8);

            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            x = (int) event.getX();
            y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    x = (int) event.getX();
                    y = (int) event.getY();

                    path.lineTo(x, y);
                    break;
            }
            invalidate();

            return true;
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                Toast.makeText(getApplicationContext(), "가로", Toast.LENGTH_SHORT).show();
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                Toast.makeText(getApplicationContext(), "세로", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
