package es.us.oauthclient;


class GoogleConfigInterface extends OAuth2Config implements OAuth2ConfigInterface {

  private final String TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";

  private final String AUTHORIZATION_SERVER_URL = "https://accounts.google.com/o/oauth2/auth";


  public String tokenServerUrl() {
    return TOKEN_SERVER_URL;
  }

  public String authorizationServerUrl() {
    return AUTHORIZATION_SERVER_URL;
  }

}
