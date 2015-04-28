package com.heartbiit.group2.heartbiit;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Created by shuxu on 4/26/15.
 */
public class FitbitData {

    private static String apiKey = "41564a134c484dd6806c561c8a966759";
    private static String apiSecret = "b05c137e6cb44a1e91fb30eaa9968a36";

    public void run() {
        OAuthService service = new ServiceBuilder()
                .provider(FitbitAPI.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
        Token requestToken = service.getRequestToken();
        String authUrl = service.getAuthorizationUrl(requestToken);
        Verifier v = new Verifier("Verifier from user");
        Token accessToken = service.getAccessToken(requestToken, v);

        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.fitbit.com/1/user/-/activities/steps/date/2015-04-20/1d.json");

        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
    }
    public FitbitData() {


    }


}
