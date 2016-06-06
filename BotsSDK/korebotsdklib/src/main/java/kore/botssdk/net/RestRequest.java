package kore.botssdk.net;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public abstract class RestRequest<T> extends RetrofitSpiceRequest<T, RestAPI> {

    protected final String LOG_TAG = getClass().getSimpleName();
    protected String userId;
    protected String accessToken;

    public RestRequest(Class<T> clazz, String userId, String accessToken) {
        super(clazz, RestAPI.class);
        this.userId = userId;
        this.accessToken = accessToken;
    }
    
    protected String accessTokenHeader(){
    	return "bearer " + accessToken ;
    }

    public static String accessTokenHeader(String accessToken){
        return "bearer " + accessToken ;
    }

}