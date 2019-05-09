package es.us.oauthclient;


interface OAuth2ConfigInterface {

  String getKey();
  String getSecret();
  String domainCallback();
  int portCallback();

  String tokenServerUrl();
  String authorizationServerUrl();

}
