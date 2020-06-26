//package com.example.choice_photo;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
////public class DrawView extends AppCompatActivity {
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////    }
//
//    public class DrawView extends View {
//
//
//        private Paint paint = new Paint();
//        private Path path = new Path();
//        private int x, y;
//        private Bitmap bbd;
//        String imagePath = null;
//
//        public DrawView(Context context, @Nullable AttributeSet attrs) {
//            super(context, attrs);
//
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//
////        Resources res = getResources();
////        BitmapDrawable dd = null;
////        dd = (BitmapDrawable)res.getDrawable(bbd,null);
//            Bitmap bb = BitmapFactory.decodeFile(imagePath);
//
//            canvas.drawBitmap(bb,0,0,null);
//
//            paint.setARGB(80, 250, 30, 55);
//
//            paint.setStyle(Paint.Style.STROKE);
//
//            paint.setStrokeWidth(8);
//
//            canvas.drawPath(path, paint);
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//            x = (int) event.getX();
//            y = (int) event.getY();
//
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    path.moveTo(x, y);
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    x = (int) event.getX();
//                    y = (int) event.getY();
//
//                    path.lineTo(x, y);
//                    break;
//            }
//            invalidate();
//
//            return true;
//        }
//    }
//
