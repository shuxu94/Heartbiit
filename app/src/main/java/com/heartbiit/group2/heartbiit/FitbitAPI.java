package com.heartbiit.group2.heartbiit;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * Created by shuxu on 4/26/15.
 */

// used for the oauth
public class FitbitAPI extends DefaultApi10a{

    private static final String AUTHORIZE_URL = "https://www.fitbit.com/oauth/authorize?oauth_token=";

    @Override
    public String getAccessTokenEndpoint()
    {
        return "http://api.fitbit.com/oauth/access_token";
    }

    @Override
    public String getRequestTokenEndpoint()
    {
        return "http://api.fitbit.com/oauth/request_token";
    }

    @Override
    public String getAuthorizationUrl(Token requestToken)
    {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}
