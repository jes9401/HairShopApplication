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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PleaseReviseActivity extends AppCompatActivity {

    TextView writerText;
    EditText titleText;
    TextView dateText;
    EditText contentsText;
    private String writer, title, date, contents, image, image2;
    private String secret;
    AlertDialog dialog;
    private int pleaseNum;
    private int access;
    private int num;
    private String pleaseTitle, pleaseContents, reviseDate, pleaseImage= "noimage";
    private String pleaseImage2="noimage";
    private ImageView iv_view;
    private Button btn_album1;
    private ImageView iv_view1;
    // LOG
    private Log_Class LOG = new Log_Class();
    private String TAG = this.getClass().getSimpleName()+"_LOG";

    private static Bitmap bPicture = null;
    CheckBox pictureCheck;
    FrameLayout pleaseFrameLayout;
    // URL
    private String ServerIP = "http://kyu9341.cafe24.com/image_up_down/";
    private String ImageToServerURL = "ImageUploadToServer.php";

    private String sPictureUrl;
    private String sPictureUrl2;
    // 이미지넣는 뷰와 업로드하기위환 버튼
    private Button btn_album;

    public String uploadFilePath1;
    public String uploadFileName1;

    // 서버로 업로드할 파일관련 변수
    public String uploadFilePath;
    public String uploadFileName;
    private int REQ_CODE_PICK_PICTURE = 1;

    // 파일을 업로드 하기 위한 변수 선언
    private int serverResponseCode = 0;

    RadioButton open;
    RadioButton notopen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_please_revise);
        iv_view = (ImageView)findViewById(R.id.iv_view);
        writerText = (TextView) findViewById(R.id.writer);
        titleText = (EditText) findViewById(R.id.titleText);
        dateText = (TextView) findViewById(R.id.dateText);
        contentsText = (EditText)findViewById(R.id.contentsText);
        RadioGroup secretGroup = (RadioGroup)findViewById(R.id.secretGroup);
        open = (RadioButton)findViewById(R.id.open);
        notopen = (RadioButton)findViewById(R.id.notopen);
        Button completeButton = (Button)findViewById(R.id.completeButton);
        pictureCheck = (CheckBox)findViewById(R.id.pictureCheck);
        pleaseFrameLayout = (FrameLayout)findViewById(R.id.pleaseFrameLayout);

        btn_album1 = (Button)findViewById(R.id.btn_album1);
        iv_view1 = (ImageView)findViewById(R.id.iv_view1);
        // 퍼미션 적용
        tedPermission();
        btn_album = (Button)findViewById(R.id.btn_album);


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
        long now = System.currentTimeMillis();  // 현재 시간 받아오기
        Date date1 = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        reviseDate = CurDateFormat.format(date1);
        dateText.setText(reviseDate);
        reviseDate = reviseDate+" 수정됨";
        Intent intent = getIntent();
        pleaseNum = intent.getIntExtra("Num", 0);
        Log.e("pleaseNum = "+pleaseNum, "pleaseNum");
        pictureCheck.setChecked(false);

        pleaseFrameLayout.setVisibility(View.GONE);

        pictureCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pictureCheck.isChecked()){

                    pleaseFrameLayout.setVisibility(View.VISIBLE);
                }else {
                    pleaseFrameLayout.setVisibility(View.GONE);
                }
            }
        });

         new BackgroundTask().execute();

        secretGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // 성별 라디오 버튼에 대한 이벤트처리
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton secretButton = (RadioButton)findViewById(i); // 현재 선택된 라디오버튼 받아옴
                secret = secretButton.getText().toString();
                if(secret.equals("공개")){
                    access = 1;
                }else {
                    access = 0;
                }
                Log.e("access = "+ access, "access");
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pleaseTitle = titleText.getText().toString();
                pleaseContents = contentsText.getText().toString();

                if (pleaseContents.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PleaseReviseActivity.this);
                    dialog = builder.setMessage("내용을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (pleaseTitle.equals("")) {
                    pleaseTitle = "제목 없음";
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) { // 작성에 성공했을 경우 성공 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(PleaseReviseActivity.this);
                                dialog = builder.setMessage("수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();

                            } else { // 작성에 실패한 경우 실패 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(PleaseReviseActivity.this);
                                dialog = builder.setMessage("수정에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace(); // 오류 출력
                        }

                    }
                };

    //            pleaseImage = image;
     //           pleaseImage2 = image2;
                Log.e("image = "+ image, "pleaseImage" + pleaseImage);
                Log.e("image2 = "+ image2, "pleaseImage2" + pleaseImage2);
                if(pictureCheck.isChecked()  == false){
                    pleaseImage = "noimage";
                    pleaseImage2 = "noimage";
                }


                PleaseRequest reviseRequest = new PleaseRequest(num, pleaseTitle, reviseDate, pleaseContents, pleaseImage, pleaseImage2, access, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PleaseReviseActivity.this);
                queue.add(reviseRequest);
                Log.e("access = " + access, "access");

                if (pleaseImage != "noimage") {
                    PleaseReviseActivity.UploadImageToServer uploadimagetoserver = new PleaseReviseActivity.UploadImageToServer();
                    uploadimagetoserver.execute(ServerIP+ImageToServerURL);
                } else {
//                    Toast.makeText(WriteActivity.this, "You didn't insert any image", Toast.LENGTH_SHORT).show();
                }


                if (pleaseImage2 != "noimage") {
                    PleaseReviseActivity.UploadImageToServer1 uploadimagetoserver1 = new PleaseReviseActivity.UploadImageToServer1();
                    uploadimagetoserver1.execute(ServerIP+ImageToServerURL);
                } else {
//                    Toast.makeText(WriteActivity.this, "You didn't insert any image", Toast.LENGTH_SHORT).show();
                }

                finish();
            }

        });



    }




    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://kyu9341.cafe24.com/PleaseRevise.php?pleaseNum="+URLEncoder.encode(pleaseNum+""); // GET 방식으로 ID를 서버에 전송
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); // 넘어오는 결과값들을 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // 해당 inputstream에 있는 내용들을 버퍼에 담아 읽을수 있도록 함.
                String temp;
                StringBuilder stringBuilder = new StringBuilder(); // 문자열 형태로 저장
                while ((temp = bufferedReader.readLine()) != null){  // 버퍼에서 받아오는 값을 한줄씩 읽으면 temp에 저장
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){ // 해당 결과 처리
            try{
                JSONObject jsonObject = new JSONObject(result); // 응답 부분 처리
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                JSONObject object = jsonArray.getJSONObject(0); // 현재 배열의 원소값


                writer = object.getString("pleaseName");
                title = object.getString("pleaseTitle");
                date = object.getString("pleaseDate");
                contents = object.getString("pleaseContents");
                access = object.getInt("access");
                num = object.getInt("pleaseNum");
                image = object.getString("pleaseImage");
                image2 = object.getString("pleaseImage2");

                Log.e("writer = "+writer , "writer");
                Log.e("title = "+title , "title");
                Log.e("date = "+date , "date");
                Log.e("contents = "+contents , "contents");
                Log.e("image = "+image , "image");
                Log.e("image2 = "+image2 , "image2");
                writerText.setText(writer);
                titleText.setText(title);
                contentsText.setText(contents);

/*
                sPictureUrl = image;
                sPictureUrl2 = image2;


                PleaseReviseActivity.GetImageFromServer GetImageFromServer_th = new PleaseReviseActivity.GetImageFromServer();
                GetImageFromServer_th.execute(); // 서버로부터 이미지를 가져옴
                PleaseReviseActivity.GetImageFromServer2 GetImageFromServer_th2 = new PleaseReviseActivity.GetImageFromServer2();
                GetImageFromServer_th2.execute(); // 서버로부터 이미지를 가져옴
*/
                if(access == 1){
                    open.setChecked(true);
                }else{
                    notopen.setChecked(true);
                }

                /*
                if(image.equals("noimage") && image2.equals("noimage")){
                    pictureCheck.setChecked(false);
                }else{
                    pictureCheck.setChecked(true);
                    pleaseFrameLayout.setVisibility(View.VISIBLE);
                }
*/

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
    private void tedPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(PleaseReviseActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(PleaseReviseActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
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
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String path = getPath(uri);
                String name = getName(uri);

                uploadFilePath = path;
                uploadFileName = name;

                pleaseImage = uploadFileName;

                LOG.i(TAG, "[onActivityResult] uploadFilePath:" + uploadFilePath + ", uploadFileName:" + uploadFileName);

                Bitmap bit = BitmapFactory.decodeFile(path);
                iv_view.setImageBitmap(bit);

            }

        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                Uri uri = data.getData();
                String path = getPath(uri);
                String name = getName(uri);

                uploadFilePath1 = path;
                uploadFileName1 = name;

                pleaseImage2 = uploadFileName1;

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
                                Toast.makeText(PleaseReviseActivity.this, "File Upload Completed", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PleaseReviseActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    LOG.i(TAG,"[UploadImageToServer] error: " + ex.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(PleaseReviseActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(PleaseReviseActivity.this, "작성 완료", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PleaseReviseActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    LOG.i(TAG,"[UploadImageToServer] error: " + ex.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(PleaseReviseActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
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
    // =========================== 서버로부터 이미지 받아오는 스레드 ===============================
    private class GetImageFromServer extends AsyncTask<String, String, String> {
        URL Url;

        @Override
        protected void onPreExecute() {
            LOG.i(TAG,"::::: [GetImageFromServer Start] :::::");
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                if (!sPictureUrl.equals("")) {
                    Url = new URL(ServerIP+sPictureUrl);
                    HttpURLConnection conn;
                    conn = (HttpURLConnection) Url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();

                    Bitmap PictureDefault = BitmapFactory.decodeStream(is);
                    bPicture = PictureDefault != null ? PictureDefault : BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    conn.disconnect();
                    is.close();
                    return "True";
                }

            } catch (UnsupportedEncodingException e) {
                LOG.i(TAG,"[GetImageFromServer] Frist UnsupportedEncodingException");
                e.printStackTrace();
                return "UnsupportedEncodingException";
            } catch (IOException e) {
                LOG.i(TAG,"[GetImageFromServer] Frist IOException");
                e.printStackTrace();
                return "IOException";
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("True")){
                iv_view.setImageBitmap(bPicture);
//                pleaseImage = sPictureUrl;
 //               Toast.makeText(PleaseReviseActivity.this, "이미지 다운로드 성공.", Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("UnsupportedEncodingException")){

            }
            else if(s.equals("IOException")){
      //          Toast.makeText(PleaseReviseActivity.this, "서버에서 파일을 다운로드 받지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(PleaseReviseActivity.this, "실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // =============================================================================================

    // =============================================================================================
    // =========================== 서버로부터 이미지 받아오는 스레드2 ===============================
    private class GetImageFromServer2 extends AsyncTask<String, String, String> {
        URL Url;

        @Override
        protected void onPreExecute() {
            LOG.i(TAG,"::::: [GetImageFromServer Start] :::::");
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                if (!sPictureUrl2.equals("")) {
                    Url = new URL(ServerIP+sPictureUrl2);
                    HttpURLConnection conn;
                    conn = (HttpURLConnection) Url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();

                    Bitmap PictureDefault = BitmapFactory.decodeStream(is);
                    bPicture = PictureDefault != null ? PictureDefault : BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    conn.disconnect();
                    is.close();
                    return "True";
                }

            } catch (UnsupportedEncodingException e) {
                LOG.i(TAG,"[GetImageFromServer] Frist UnsupportedEncodingException");
                e.printStackTrace();
                return "UnsupportedEncodingException";
            } catch (IOException e) {
                LOG.i(TAG,"[GetImageFromServer] Frist IOException");
                e.printStackTrace();
                return "IOException";
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("True")){
                iv_view1.setImageBitmap(bPicture);
//                pleaseImage2 = sPictureUrl2;
                //         Toast.makeText(PleaseContentsActivity.this, "이미지 다운로드 성공.", Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("UnsupportedEncodingException")){

            }
            else if(s.equals("IOException")){
                //      Toast.makeText(PleaseContentsActivity.this, "서버에서 파일을 다운로드 받지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(PleaseReviseActivity.this, "실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // =============================================================================================



}
