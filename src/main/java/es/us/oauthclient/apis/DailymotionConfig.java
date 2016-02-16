package es.us.oauthclient.apis;

import com.google.api.client.util.store.DataStoreFactory;

import es.us.oauthclient.AbstractOAuth2Config;
import es.us.oauthclient.OAuth2Config;

/**
 * @author resinas
 *
 */
public class DailymotionConfig extends AbstractOAuth2Config implements OAuth2Config {
	
  public static final String DAILYMOTION = "dailymotion";

  private static DataStoreFactory DATA_STORE_FACTORY = buildFactory(DAILYMOTION);

  private static final String TOKEN_SERVER_URL = "https://api.dailymotion.com/oauth/token";
  private static final String AUTHORIZATION_SERVER_URL =
      "https://api.dailymotion.com/oauth/authorize";


  public String getApiKey() {
    return getKeyInProperties(DAILYMOTION);
  }

  public String getApiSecret() {
    return getSecretInProperties(DAILYMOTION);
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
