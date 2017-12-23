package com.toplyh.android.scheduler.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class CheatActivity extends AppCompatActivity {

    private static final String TAG="cheat";
    private EditText cheat;
    private Button send;
    private LinearLayout message;
    private StompClient mStompClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        bindView();
        createStompClient();
        registerStompTopic();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStompClient.send("/app/cheat","{\"userId\":\"lincoln\",\"message\":\""+cheat.getText()+"\"}")
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
    }
    private void bindView() {
        cheat = (EditText) findViewById(R.id.cheat_text);
        send = (Button) findViewById(R.id.cheat_send);
        message = (LinearLayout) findViewById(R.id.cheat_message);
    }

    private void createStompClient(){
        mStompClient= Stomp.over(WebSocket.class,"ws://192.168.1.102:8081/hello/websocket");
        mStompClient.connect();
        Toast.makeText(CheatActivity.this, "开始连接192.168.1.102：8081", Toast.LENGTH_SHORT).show();
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
        mStompClient.topic("/user/xiaoli/message").subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.e(TAG, "call: " +stompMessage.getPayload() );
                showMessage(stompMessage);
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
                        TextView text = new TextView(CheatActivity.this);
                        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        text.setText(System.currentTimeMillis() +" body is --->"+stompMessage.getPayload());
                        message.addView(text);
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
                        Toast.makeText(CheatActivity.this,message,Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
