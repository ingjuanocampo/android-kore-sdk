package kore.botssdk.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Date;

import kore.botssdk.R;
import kore.botssdk.autobahn.WebSocket;
import kore.botssdk.bot.BotClient;
import kore.botssdk.fragment.BotContentFragment;
import kore.botssdk.fragment.ComposeFooterFragment;
import kore.botssdk.listener.BotContentFragmentUpdate;
import kore.botssdk.listener.ComposeFooterUpdate;
import kore.botssdk.models.BotInfoModel;
import kore.botssdk.models.BotRequest;
import kore.botssdk.net.BotDemoRestService;
import kore.botssdk.net.JWTGrantRequest;
import kore.botssdk.net.RestResponse;
import kore.botssdk.net.SDKConfiguration;
import kore.botssdk.utils.BotSharedPreferences;
import kore.botssdk.utils.BundleUtils;
import kore.botssdk.utils.Contants;
import kore.botssdk.utils.CustomToast;
import kore.botssdk.utils.DateUtils;
import kore.botssdk.utils.SocketConnectionEventStates;
import kore.botssdk.websocket.SocketConnectionListener;

/**
 * Created by Pradeep Mahato on 31-May-16.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class BotChatActivity extends AppCompatActivity implements SocketConnectionListener, ComposeFooterFragment.ComposeFooterInterface {

    String LOG_TAG = BotChatActivity.class.getSimpleName();

    FrameLayout chatLayoutFooterContainer;
    FrameLayout chatLayoutContentContainer;
    ProgressBar taskProgressBar;

    FragmentTransaction fragmentTransaction;

    String chatBot, taskBotId;
    String loginMode = Contants.NORMAL_FLOW;

    Handler actionBarTitleUpdateHandler;

    BotClient botClient;
    BotContentFragment botContentFragment;
    ComposeFooterFragment composeFooterFragment;

    BotContentFragmentUpdate botContentFragmentUpdate;
    ComposeFooterUpdate composeFooterUpdate;
    private SpiceManager spiceManagerForJWT = new SpiceManager(BotDemoRestService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_chat_layout);
        findViews();
        getBundleInfo();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //Add Bot Content Fragment
        botContentFragment = new BotContentFragment();
        botContentFragment.setArguments(getIntent().getExtras());
        fragmentTransaction.add(R.id.chatLayoutContentContainer, botContentFragment).commit();
        setBotContentFragmentUpdate(botContentFragment);

        //Add Bot Compose Footer Fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        composeFooterFragment = new ComposeFooterFragment();
        composeFooterFragment.setArguments(getIntent().getExtras());
        composeFooterFragment.setComposeFooterInterface(this);
        fragmentTransaction.add(R.id.chatLayoutFooterContainer, composeFooterFragment).commit();
        setComposeFooterUpdate(composeFooterFragment);

        updateTitleBar();

        botClient = new BotClient(this);

//        if (loginMode.equalsIgnoreCase(Contants.NORMAL_FLOW)) {
//            connectToWebSocket();
//        } else {
            connectToWebSocketAnonymous();
//        }
    }

    @Override
    protected void onDestroy() {
        botClient.disconnect();
        super.onDestroy();
    }
    @Override
    protected void onStart() {
        super.onStart();
        spiceManagerForJWT.start(getApplicationContext());
    }

    @Override
    protected void onStop() {
        spiceManagerForJWT.shouldStop();
        super.onStop();
    }

    private void getBundleInfo() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            chatBot = bundle.getString(BundleUtils.CHATBOT, "");
            taskBotId = bundle.getString(BundleUtils.TASKBOTID, "");
            loginMode = bundle.getString(BundleUtils.LOGIN_MODE, Contants.NORMAL_FLOW);
        }
//        if(loginMode.equalsIgnoreCase(Contants.ANONYMOUS_FLOW)){
            chatBot = SDKConfiguration.Client.chatBotName;
            taskBotId = SDKConfiguration.Client.taskBotId;
//        }
    }

    private void findViews() {
        chatLayoutFooterContainer = (FrameLayout) findViewById(R.id.chatLayoutFooterContainer);
        chatLayoutContentContainer = (FrameLayout) findViewById(R.id.chatLayoutContentContainer);
        taskProgressBar = (ProgressBar) findViewById(R.id.taskProgressBar);
    }

    private void updateTitleBar() {
        String botName = (chatBot != null && !chatBot.isEmpty()) ? chatBot : ((loginMode.equalsIgnoreCase(Contants.NORMAL_FLOW)) ? chatBot : chatBot+" - anonymous");
        getSupportActionBar().setSubtitle(botName);
    }

    private void updateTitleBar(SocketConnectionEventStates socketConnectionEvents) {

        String titleMsg = "";
        switch (socketConnectionEvents) {
            case CONNECTING:
                titleMsg = getString(R.string.socket_connecting);
                taskProgressBar.setVisibility(View.VISIBLE);
                break;
            case CONNECTED:
                titleMsg = getString(R.string.socket_connected);
                taskProgressBar.setVisibility(View.GONE);
                updateActionBar();
                break;
            case DISCONNECTED:
                titleMsg = getString(R.string.socket_disconnected);
                taskProgressBar.setVisibility(View.GONE);
                updateActionBar();
                break;
            case FAILED_TO_CONNECT:
                titleMsg = getString(R.string.socket_failed);
                taskProgressBar.setVisibility(View.GONE);
                updateActionBar();
                break;
            case RECONNECTING:
                titleMsg = getString(R.string.socket_connected);
                taskProgressBar.setVisibility(View.VISIBLE);
                break;
        }

        if (!titleMsg.isEmpty()) {
            getSupportActionBar().setSubtitle(titleMsg);
        }
    }

    private void connectToWebSocket() {
        String accessToken = BotSharedPreferences.getAccessTokenFromPreferences(getApplicationContext());
        botClient.connectAsAuthenticatedUser(accessToken, chatBot,taskBotId, this);

        updateTitleBar(SocketConnectionEventStates.CONNECTING);
    }

    private void connectToWebSocketAnonymous() {
        getJWTToken();
        updateTitleBar(SocketConnectionEventStates.CONNECTING);
    }

    private void updateActionBar() {
        if (actionBarTitleUpdateHandler == null) {
            actionBarTitleUpdateHandler = new Handler();
        }

        actionBarTitleUpdateHandler.removeCallbacks(actionBarUpdateRunnable);
        actionBarTitleUpdateHandler.postDelayed(actionBarUpdateRunnable, 4000);

    }

    Runnable actionBarUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            updateTitleBar();
        }
    };

    @Override
    public void onOpen() {
        if (composeFooterUpdate != null) {
            composeFooterUpdate.enableSendButton();
            composeFooterUpdate = null;
        }
        //By sending null initiating sending which are un-delivered in pool
        botClient.sendMessage(null,null,null);
        updateTitleBar(SocketConnectionEventStates.CONNECTED);
    }

    @Override
    public void onClose(WebSocket.WebSocketConnectionObserver.WebSocketCloseNotification code, String reason) {
        CustomToast.showToast(getApplicationContext(), "onDisconnected. Reason is " + reason);

        switch (code) {
            case CONNECTION_LOST:
                updateTitleBar(SocketConnectionEventStates.DISCONNECTED);
                break;
            case CANNOT_CONNECT:
            case PROTOCOL_ERROR:
            case INTERNAL_ERROR:
            case SERVER_ERROR:
                updateTitleBar(SocketConnectionEventStates.FAILED_TO_CONNECT);
                break;
        }
        updateTitleBar();
    }

    @Override
    public void onTextMessage(String payload) {
        botContentFragment.convertToPojoAndRefreshTheList(payload);
    }

    @Override
    public void onRawTextMessage(byte[] payload) {

    }

    @Override
    public void onBinaryMessage(byte[] payload) {

    }

    @Override
    public void onSendClick(String message) {


        botClient.sendMessage(message,chatBot,taskBotId);

        if (botContentFragmentUpdate != null) {
            //Update the bot content list with the send message

            RestResponse.BotMessage botMessage = new RestResponse.BotMessage(message);

            RestResponse.BotPayLoad botPayLoad = new RestResponse.BotPayLoad();
            botPayLoad.setMessage(botMessage);
            BotInfoModel botInfo = new BotInfoModel(chatBot,taskBotId);
            botPayLoad.setBotInfo(botInfo);
            Gson gson = new Gson();
            String jsonPayload = gson.toJson(botPayLoad);

            BotRequest botRequest = gson.fromJson(jsonPayload, BotRequest.class);
            botRequest.setCreatedOn(DateUtils.isoFormatter.format(new Date()));

            botContentFragmentUpdate.updateContentListOnSend(botRequest);
        }
    }

    public void setBotContentFragmentUpdate(BotContentFragmentUpdate botContentFragmentUpdate) {
        this.botContentFragmentUpdate = botContentFragmentUpdate;
    }

    public void setComposeFooterUpdate(ComposeFooterUpdate composeFooterUpdate) {
        this.composeFooterUpdate = composeFooterUpdate;
    }

    private void getJWTToken(){
        JWTGrantRequest request = new JWTGrantRequest(SDKConfiguration.Client.demo_client_id,
                SDKConfiguration.Client.clientSecret,SDKConfiguration.Client.identity,SDKConfiguration.Server.IS_ANONYMOUS_USER);
        spiceManagerForJWT.execute(request, new RequestListener<RestResponse.JWTTokenResponse>() {
            @Override
            public void onRequestFailure(SpiceException e) {

            }

            @Override
            public void onRequestSuccess(RestResponse.JWTTokenResponse jwt) {
                botClient.connectAsAnonymousUser(jwt.getJwt(),
                        SDKConfiguration.Client.demo_client_id,chatBot,taskBotId, BotChatActivity.this);
            }
        });
    }
}
