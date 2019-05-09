package es.us.oauthclient.patches;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.http.GenericUrl;

import java.util.Collection;


public class MyAuthCodeFlow extends AuthorizationCodeFlow {

  public MyAuthCodeFlow(Builder builder) {
    super(builder);
  }


  @Override
  public AuthorizationCodeTokenRequest newTokenRequest(String authorizationCode) {
    Collection<String> scopes = getScopes().isEmpty() ? null : getScopes();

    return new AuthorizationCodeTokenRequest(getTransport(), getJsonFactory(),
        new GenericUrl(getTokenServerEncodedUrl()), authorizationCode).setClientAuthentication(
        getClientAuthentication()).setRequestInitializer(getRequestInitializer()).setScopes(scopes);
  }

}
