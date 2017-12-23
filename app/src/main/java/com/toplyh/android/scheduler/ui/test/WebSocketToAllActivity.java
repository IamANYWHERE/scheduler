package com.toplyh.android.scheduler.ui.test;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;

import org.java_websocket.WebSocket;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

public class WebSocketToAllActivity extends AppCompatActivity {

    private static final String TAG="websocket";
    private TextView serverMessage;
    private Button start;
    private Button stop;
    private Button send;
    private EditText editText;
    private StompClient mStompClient;
    private Button cheat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket_to_all);

        bindView();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建client实例
                createStompClient();
                //订阅消息
                registerStompTopic();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStompClient.send("app/welcome","{\"name\":\""+editText.getText()+"\"}")
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted() {
                                toast("发送成功");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                toast("发送错误");
                            }

                            @Override
                            public void onNext(Void aVoid) {

                            }
                        });
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStompClient.disconnect();
            }
        });

        cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WebSocketToAllActivity.this,CheatActivity.class));
                if (mStompClient!=null){
                    mStompClient.disconnect();
                }
                finish();
            }
        });
    }

    private void showMessage(final StompMessage stompMessage){
        Observable.just(stompMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StompMessage>() {
                    @Override
                    public void call(StompMessage stompMessage) {
                        serverMessage.setText("stomp command is -->"+stompMessage.getStompCommand()+"body is" +
                                " -->"+stompMessage.getPayload());
                    }
                });
    }

    private void createStompClient(){
        mStompClient= Stomp.over(WebSocket.class,"ws://47.94.12.38:8081/example-endpoint/websocket");
        mStompClient.connect();
        Toast.makeText(WebSocketToAllActivity.this, "开始连接47.94.12.38:8081", Toast.LENGTH_SHORT).show();
        mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
            @Override
            public void call(LifecycleEvent lifecycleEvent) {
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        Log.d(TAG, "Stomp connection opened");
                        toast("连接已开启");
                        break;

                    case ERROR:
                        Log.e(TAG, "Stomp Error", lifecycleEvent.getException());
                        toast("连接出错");
                        break;
                    case CLOSED:
                        Log.d(TAG, "Stomp connection closed");
                        toast("连接关闭");
                        break;
                }
            }
        });

    }

    private void registerStompTopic() {
        mStompClient.topic("/topic/greetings").subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.e(TAG, "call: " +stompMessage.getPayload() );
                showMessage(stompMessage);
            }
        });

    }

    private void toast(final String message){
        Observable.just(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(WebSocketToAllActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void bindView(){
        serverMessage=(TextView)findViewById(R.id.serverMessage);
        start=(Button)findViewById(R.id.websocket_start);
        stop=(Button) findViewById(R.id.websocket_stop);
        send=(Button) findViewById(R.id.websocket_send);
        editText=(EditText)findViewById(R.id.clientMessage);
        cheat=(Button)findViewById(R.id.cheat);
    }
}
