package com.toplyh.android.scheduler.ui.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.state.Token;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.toplyh.android.scheduler.service.utils.ImageHelper.getFilePathByFileUri;


public class NettyActivity extends AppCompatActivity {
    private static final int SELECT_IMAGE_RESULT_CODE=1;

    private Button start;
    private TextView text;
    private Button upload;

    private String mImagePath;

    private RequestBody mRequestBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netty);

        start=(Button)findViewById(R.id.start_netty);
        text=(TextView)findViewById(R.id.text_netty);
        upload=(Button) findViewById(R.id.upload_file);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(mImagePath);
                RequestBody requesFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body=MultipartBody.Part.createFormData("file",file.getName(),requesFile);
                String descriptionString = "hello,这是文件描述";
                RequestBody description=RequestBody.create(MediaType.parse("multipart/form-data"),descriptionString);

                Count count=new Count();
                count.setName("george");
                count.setPassword("123");
                RetrofitHelper.getInstance(NettyActivity.this)
                        .getServer().loginUser(count)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<HttpsResult<Token>>() {
                            @Override
                            public void call(HttpsResult<Token> tokenHttpsResult) {
                                //Toast.makeText(NettyActivity.this, tokenHttpsResult.getState(), Toast.LENGTH_SHORT).show();
                                Log.e("george",tokenHttpsResult.getState()+"");
                            }
                        });
                /*Call<ResponseBody> call= RetrofitHelper.getInstance(NettyActivity.this)
                        .getServer()
                        .upload(description,body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        Log.e("george","uploadFile success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("george","uploadfile error"+t.getMessage());
                    }
                });*/
            }
        });
    }
    private void pickPhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_IMAGE_RESULT_CODE);
    }
    private void takePhoto(){
        String SDState= Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)){
            String imageFilePath=getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            String timestamp="/"+formatter.format(new Date())+".png";
            File imageFile=new File(imageFilePath,timestamp);
            mImagePath=imageFile.getAbsolutePath();
            Uri imageFileUri=Uri.fromFile(imageFile);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);
            startActivityForResult(intent,SELECT_IMAGE_RESULT_CODE);
        }else {
            Toast.makeText(this,"内存卡不存在！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SELECT_IMAGE_RESULT_CODE&&resultCode==RESULT_OK){
            String imagePath="";
            if (data!=null&&data.getData()!=null){
                imagePath=getFilePathByFileUri(this,data.getData());
            }else {
                imagePath=mImagePath;
            }
            text.setText(imagePath);
            mImagePath=imagePath;
        }
    }

    private void connect(){
        EchoWebSocketListener listener=new EchoWebSocketListener();
        Request request=new Request.Builder()
                .url("ws://47.94.12.38:8081/gs-guide-websocket/websocket")
                .build();
        OkHttpClient client=new OkHttpClient();
        client.newWebSocket(request,listener);
        client.dispatcher().executorService().shutdown();
    }

    private final class EchoWebSocketListener extends WebSocketListener{

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("hello world");
            webSocket.send("welcome");
            webSocket.send(ByteString.decodeHex("adef"));
            webSocket.close(1000,"再见");
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("onMessage:"+bytes);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("onMessage:"+text);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000,null);
            output("onClosing:"+code+"/"+reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            output("onClosed:"+code+"/"+reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("onFailure:"+t.getMessage());
        }

        private void output(final String content){
            Observable.just(content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            text.setText(text.getText().toString()+content+"\n");
                        }
                    });
        }
    }
}
