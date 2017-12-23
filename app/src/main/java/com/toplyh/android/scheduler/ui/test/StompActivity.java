package com.toplyh.android.scheduler.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.RetrofitHelper;
import com.toplyh.android.scheduler.service.utils.SharedPreferencesUtils;

import org.java_websocket.WebSocket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompCommand;
import ua.naiksoftware.stomp.client.StompMessage;

public class StompActivity extends AppCompatActivity {

    private static final String TAG="StompActivity";
    private SimpleAdapter mAdapter;
    private List<String> mDataSet=new ArrayList<>();
    private StompClient mStompClient;
    private CompositeSubscription mCompositeSubscription;
    private final SimpleDateFormat mTimeFormat=new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private RecyclerView mRecyclerView;
    private Gson mGson=new GsonBuilder().create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stomp);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        mAdapter=new SimpleAdapter(mDataSet);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        mCompositeSubscription=new CompositeSubscription();
        initButton();
    }

    private void initButton(){
        Button btn_disconnect=(Button) findViewById(R.id.disconnectStomp);
        Button btn_connect=(Button)findViewById(R.id.connectStomp);
        Button btn_send_stomp=(Button)findViewById(R.id.sendEchoViaStomp);
        Button btn_send_rest=(Button)findViewById(R.id.sendEchoViaRest);

        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStompClient.disconnect();
            }
        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStompClient= Stomp.over(WebSocket.class,"ws://47.94.12.38:8081"+
                "/example-endpoint/websocket");
                mStompClient.lifecycle()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<LifecycleEvent>() {
                            @Override
                            public void call(LifecycleEvent lifecycleEvent) {
                                switch (lifecycleEvent.getType()) {
                                    case OPENED:
                                        toast("Stomp connection opened");
                                        break;
                                    case ERROR:
                                        Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                                        toast("Stomp connection error");
                                        break;
                                    case CLOSED:
                                        toast("Stomp connection closed");
                                }
                            }
                        });

                mStompClient.topic("/topic/greetings")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<StompMessage>() {
                            @Override
                            public void call(StompMessage stompMessage) {
                                Log.d(TAG, "Received " + stompMessage.getPayload());
                                addItem(mGson.fromJson(stompMessage.getPayload(), EchoModel.class));
                            }
                        });

                List<StompHeader> stompHeaders=new ArrayList<StompHeader>();
                StompHeader stompHeader=new StompHeader("username",
                        (String) SharedPreferencesUtils.getParam(StompActivity.this,
                                getString(R.string.username),"usernmae"));
                stompHeaders.add(stompHeader);
                mStompClient.connect(stompHeaders);
            }
        });

        btn_send_stomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStompClient.send("/topic/hello-msg-mapping", "Echo STOMP " + mTimeFormat.format(new Date()))
                        .unsubscribeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                Log.d(TAG, "STOMP echo send successfully");
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "Error send STOMP echo", throwable);
                                toast(throwable.getMessage());
                            }
                        });
            }
        });
        btn_send_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subscription subscription=RetrofitHelper.getInstance(StompActivity.this).getServer()
                        .sendRestEcho("Echo REST " + mTimeFormat.format(new Date()))
                        .unsubscribeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                Log.d(TAG, "REST echo send successfully");
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "Error send REST echo", throwable);
                                toast(throwable.getMessage());
                            }
                        });
                mCompositeSubscription.add(subscription);
            }
        });
    }

    private void addItem(EchoModel echoModel){
        mDataSet.add(echoModel.getEcho()+"-"+mTimeFormat.format(new Date()));
        mAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(mDataSet.size()-1);
    }

    private void toast(String text){
        Log.i(TAG, text);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        mStompClient.disconnect();
        if (mCompositeSubscription!=null&&mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
