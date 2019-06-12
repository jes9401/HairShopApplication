package com.example.hairapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReviewWriteActivity extends AppCompatActivity {

    private String reviewTitle;
    private String reviewName;
    private String reviewDate;
    private String reviewContents;
    private float reviewRate = 5;
    private String reviewImage = "noimage";
    private String reviewImage2 = "noimage";
    //   final TextView writer = (TextView)findViewById(R.id.writer);
    private AlertDialog dialog; // 알림창

    // LOG
    private Log_Class LOG = new Log_Class();
    private String TAG = this.getClass().getSimpleName()+"_LOG";

    // 이미지넣는 뷰와 업로드하기위환 버튼
    private Button btn_album;
    private ImageView iv_view;
    private Button btn_album1;
    private ImageView iv_view1;
    private static Bitmap bPicture = null;


    // 서버로 업로드할 파일관련 변수
    public String uploadFilePath;
    public String uploadFileName;
    private int REQ_CODE_PICK_PICTURE = 1;

    public String uploadFilePath1;
    public String uploadFileName1;
    // 파일을 업로드 하기 위한 변수 선언
    private int serverResponseCode = 0;

    // URL
    private String ServerIP = "http://kyu9341.cafe24.com/image_up_down/";
    private String ImageToServerURL = "ImageUploadToServer.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        Button completeBtn = (Button)findViewById(R.id.completeBtn);
        final EditText titleText = (EditText)findViewById(R.id.titleText);
        final EditText contentsText = (EditText)findViewById(R.id.contentsText);
        final TextView writer = (TextView)findViewById(R.id.writer);
        final TextView dateText = (TextView)findViewById(R.id.dateText);
        final CheckBox pictureCheck = (CheckBox)findViewById(R.id.pictureCheck);
        final FrameLayout reviewFrameLayout = (FrameLayout)findViewById(R.id.reviewFrameLayout);

        btn_album = (Button)findViewById(R.id.btn_album);
        iv_view = (ImageView)findViewById(R.id.iv_view);
        btn_album1 = (Button)findViewById(R.id.btn_album1);
        iv_view1 = (ImageView)findViewById(R.id.iv_view1);

        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // images on the SD card.

                // 결과를 리턴하는 Activity 호출
                startActivityForResult(i, 1);
            }
        });
        btn_album1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // images on the SD card.

                // 결과를 리턴하는 Activity 호출
                startActivityForResult(i, 2);
            }
        });

        // 퍼미션 적용
        tedPermission();

        reviewFrameLayout.setVisibility(View.GONE);

        long now = System.currentTimeMillis();  // 현재 시간 받아오기
        Date date1 = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String strCurDate = CurDateFormat.format(date1);

        reviewDate = strCurDate; // 현재 날짜 저장
        writer.setText(MainActivity.nickname);
        dateText.setText(reviewDate);

        final RatingBar reviewRating = (RatingBar)findViewById(R.id.reviewRating);

        reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                reviewRate = reviewRating.getRating();

            }
        });


        Log.e("reviewRate = "+reviewRate, "reviewRate");

        contentsText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() ==R.id.contentsText) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        pictureCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pictureCheck.isChecked()){

                    reviewFrameLayout.setVisibility(View.VISIBLE);
                }else {
                    reviewFrameLayout.setVisibility(View.GONE);
                }
            }
        });





        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reviewTitle = titleText.getText().toString();
                Log.e("reviewTitle = "+titleText, "titleText");
                reviewName = MainActivity.nickname; // MainActivity에서 데이터베이스에서 받아온 nickname
                //        reviewName = writer.getText().toString();
                //       reviewDate = dateText.getText().toString();
                reviewContents = contentsText.getText().toString();
                Log.e("reviewContents"+reviewContents, " reviewContents");

                if(reviewContents.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewWriteActivity.this);
                    dialog = builder.setMessage("내용을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }if(reviewTitle.equals("")){
                    reviewTitle = "제목 없음";
                }


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ // 작성에 성공했을 경우 성공 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewWriteActivity.this);
                                dialog = builder.setMessage("작성에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();


                            }else{ // 작성에 실패한 경우 실패 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewWriteActivity.this);
                                dialog = builder.setMessage("작성에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            }
                        }
                        catch (Exception e){
                            e.printStackTrace(); // 오류 출력
                        }

                    }
                } ;

                if(pictureCheck.isChecked()  == false){
                    reviewImage = "noimage";
                    reviewImage2 = "noimage";
                }



                ReviewRequest reviewRequest = new ReviewRequest(reviewTitle, reviewName, reviewDate, reviewContents, reviewImage, reviewImage2, reviewRate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReviewWriteActivity.this);
                queue.add(reviewRequest);
                Log.e("reviewRate = "+reviewRate, "reviewRate");


                if (reviewImage != "noimage") {
                    ReviewWriteActivity.UploadImageToServer uploadimagetoserver = new ReviewWriteActivity.UploadImageToServer();
                    uploadimagetoserver.execute(ServerIP+ImageToServerURL);
                } else {
//                    Toast.makeText(WriteActivity.this, "You didn't insert any image", Toast.LENGTH_SHORT).show();
                }
                if (reviewImage2!="noimage") {
                    ReviewWriteActivity.UploadImageToServer1 uploadimagetoserver1 = new ReviewWriteActivity.UploadImageToServer1();
                    uploadimagetoserver1.execute(ServerIP+ImageToServerURL);
                } else {
//                    Toast.makeText(WriteActivity.this, "You didn't insert any image", Toast.LENGTH_SHORT).show();
                }
                finish();
                //               Intent intent = new Intent(getApplicationContext(), ReviewFragment.class);
                //               startActivity(intent);
            }
        });

    }

    private void tedPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(ReviewWriteActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ReviewWriteActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }
    // =============================================================================================
    // ==================================== 사진을 불러오는 소스코드 ===============================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String path = getPath(uri);
                String name = getName(uri);

                uploadFilePath = path;
                uploadFileName = name;

                reviewImage = uploadFileName;

                LOG.i(TAG,"[onActivityResult] uploadFilePath:" + uploadFilePath + ", uploadFileName:" + uploadFileName);

                Bitmap bit = BitmapFactory.decodeFile(path);
                iv_view.setImageBitmap(bit);
            }
        }if(requestCode == 2){
            if (resultCode == Activity.RESULT_OK) {

                Uri uri = data.getData();
                String path = getPath(uri);
                String name = getName(uri);

                uploadFilePath1 = path;
                uploadFileName1 = name;

                reviewImage2 = uploadFileName1;

                LOG.i(TAG, "[onActivityResult] uploadFilePath:" + uploadFilePath + ", uploadFileName:" + uploadFileName);

                Bitmap bit = BitmapFactory.decodeFile(path);
                iv_view1.setImageBitmap(bit);
            }

        }
    }

    // 실제 경로 찾기
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // 파일명 찾기
    private String getName(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns.DISPLAY_NAME};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // uri 아이디 찾기
    private String getUriId(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns._ID};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // =============================================================================================


    // =============================================================================================
    // ============================== 사진을 서버에 전송하기 위한 스레드 ===========================

    private class UploadImageToServer extends AsyncTask<String, String, String> {

        String fileName = uploadFilePath;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 10240 * 10240;
        File sourceFile = new File(uploadFilePath);

        @Override
        protected void onPreExecute() {
            // Create a progressdialog
        }

        @Override
        protected String doInBackground(String... serverUrl) {
            if (!sourceFile.isFile()) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        LOG.i(TAG,"");
                    }
                });
                return null;
            } else {
                try {
                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(serverUrl[0]);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);
                    LOG.i(TAG,"fileName: " + fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    // 이미지 전송
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    LOG.i(TAG, "[UploadImageToServer] HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                    if (serverResponseCode == 200) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(ReviewWriteActivity.this, "작성 완료", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ReviewWriteActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    LOG.i(TAG,"[UploadImageToServer] error: " + ex.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ReviewWriteActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    LOG.i(TAG,"[UploadImageToServer] Upload file to server Exception Exception : " + e.getMessage());
                }
                LOG.i(TAG, "[UploadImageToServer] Finish");
                return null;
            } // End else block
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }
    // =============================================================================================


    // =============================================================================================
    // ============================== 사진을 서버에 전송하기 위한 스레드1 ===========================

    private class UploadImageToServer1 extends AsyncTask<String, String, String> {

        String fileName = uploadFilePath1;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 10240 * 10240;
        File sourceFile = new File(uploadFilePath1);

        @Override
        protected void onPreExecute() {
            // Create a progressdialog
        }

        @Override
        protected String doInBackground(String... serverUrl) {
            if (!sourceFile.isFile()) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        LOG.i(TAG,"");
                    }
                });
                return null;
            } else {
                try {
                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(serverUrl[0]);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);
                    LOG.i(TAG,"fileName: " + fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    // 이미지 전송
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    LOG.i(TAG, "[UploadImageToServer] HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                    if (serverResponseCode == 200) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(ReviewWriteActivity.this, "작성 완료", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ReviewWriteActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    LOG.i(TAG,"[UploadImageToServer] error: " + ex.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ReviewWriteActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    LOG.i(TAG,"[UploadImageToServer] Upload file to server Exception Exception : " + e.getMessage());
                }
                LOG.i(TAG, "[UploadImageToServer] Finish");
                return null;
            } // End else block
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }
    // =============================================================================================




    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}