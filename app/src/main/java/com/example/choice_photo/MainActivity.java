package com.example.choice_photo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private static final String TAG = "";
    private Instant TedPermission;
    private ImageView img;
    private Uri imui;
    private Bitmap imgp;


    private File SaveFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.FaceView);

    }



    public void btnG(View view){
        GG();
    }

    public void btnC(View view){
        takePhoto();
    }

    private void GG() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(SaveFile != null) {
                if (SaveFile.exists()) {
                    if (SaveFile.delete()) {
                        Log.e(TAG, SaveFile.getAbsolutePath() + " 삭제 성공");
                        SaveFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {



            Cursor cursor = null;

            try {


                InputStream in = getContentResolver().openInputStream(data.getData());

                imgp = BitmapFactory.decodeStream(in);
                in.close();

                img.setImageBitmap(imgp);



            }catch (Exception e)
            {

            }


        } else if (requestCode == PICK_FROM_CAMERA) {
            setImage();
        }
    }

    private void setImage() {



        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap originalBm = BitmapFactory.decodeFile(SaveFile.getAbsolutePath(), options);

        img.setImageBitmap(originalBm);



    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            SaveFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (SaveFile != null) {

            Uri photoUri = Uri.fromFile(SaveFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException {


        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

//    public static void saveBitmaptoJpeg(Bitmap bitmap, String name) {
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream("/storage/emulated/0/" + name + ".png");
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }

    private void saveBit(Bitmap bitmap, String name) {
        File storage = getCacheDir();

        String fileName = name + ".jpg";

        File tempFile = new File(storage, fileName);

        try {
            tempFile.createNewFile();

            FileOutputStream out = new FileOutputStream(tempFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            out.close();
        }catch (FileNotFoundException e) {
            Log.e("MyTag", "FileNotFoundException : " + e.getMessage());
        }catch (IOException e) {
            Log.e("MyTag", "IOException : " + e.getMessage());
        }
    }

    public void CU (View v) {

        saveBit(imgp, "face");
        String filename = "faceimage";
        File file = new File(Environment.getExternalStorageDirectory() + "/Pictures", filename);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            imgp.compress(Bitmap.CompressFormat.PNG, 90, os);
            os.close();

            Toast.makeText(this,"파일 생성 완료!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,"파일 에러!", Toast.LENGTH_SHORT).show();
        }

        Intent cu = new Intent(this,used.class);

//        ByteArrayOutputStream faceimg = new ByteArrayOutputStream();
//        imgp.compress(Bitmap.CompressFormat.JPEG,100,faceimg);
//        byte[] byteArray = faceimg.toByteArray();
//        cu.putExtra("image", byteArray);

        startActivity(cu);
    }
}

