package com.toplyh.android.scheduler.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toplyh.android.scheduler.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NettyActivity extends AppCompatActivity {

    private Button start;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netty);

        start=(Button)findViewById(R.id.start_netty);
        text=(TextView)findViewById(R.id.text_netty);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
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
