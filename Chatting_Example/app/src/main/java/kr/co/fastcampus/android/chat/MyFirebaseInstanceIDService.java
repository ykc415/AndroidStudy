package kr.co.fastcampus.android.chat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    public static final String TOPIC = "update";
    private static final String TAG = MyFirebaseInstanceIDService.class.getName();

    public MyFirebaseInstanceIDService() {
    }



    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);


    }
}
