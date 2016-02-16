package es.us.oauthclient.apis;

import com.google.api.client.util.store.DataStoreFactory;

import es.us.oauthclient.AbstractOAuth2Config;
import es.us.oauthclient.OAuth2Config;

/**
 * @author resinas
 *
 */
public class GithubConfig extends AbstractOAuth2Config implements OAuth2Config {

  private static final String CLIENTNAME = "github";
  
  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static DataStoreFactory DATA_STORE_FACTORY = buildFactory(CLIENTNAME);
  
  private static final String TOKEN_SERVER_URL = "https://github.com/login/oauth/access_token";
  private static final String AUTHORIZATION_SERVER_URL = "https://github.com/login/oauth/authorize";

  public String getApiKey() {
    return getKeyInProperties(CLIENTNAME);
  }

  public String getApiSecret() {
    return getSecretInProperties(CLIENTNAME);
  }

  public String tokenServerUrl() {
    return TOKEN_SERVER_URL;
  }

  public String authorizationServerUrl() {
    return AUTHORIZATION_SERVER_URL;
  }

  public DataStoreFactory store() {
    return DATA_STORE_FACTORY;
  }

}
