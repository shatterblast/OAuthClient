package es.us.oauthclient;

import com.google.api.client.util.store.DataStoreFactory;

/**
 * @author resinas
 *
 */
public interface OAuth2Config {
  String getApiKey();
  String getApiSecret();
  String domainCallback();
  int portCallback();

  String tokenServerUrl();
  String authorizationServerUrl();

  DataStoreFactory store();

}
