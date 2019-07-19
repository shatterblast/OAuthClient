# OAuthClient #

(I WILL EVENTUALLY NEED TO USE THIS OR THE GOOGLE OAUTH2 CLIENT ITSELF WITH SPRING MVC.  @RequestMapping WORKS WITH CONTROLLERS.  THE PLAYER'S TOKEN WILL NEED TO BE LOGGED WITH THE DATABASE OF CHOICE FOR ACCOUNT CREATION, PROBABLY MONGODB, AND THEN VERIFIED WITH GOOGLE TO SOLIDIFY THE EXPERIENCE.  THERE SHOULD NOT BE A NEED FOR SPRING'S OAUTH2 CLIENT, SINCE A CONTROLLER CAN QUALIFY AS A BEAN.)

 LINKS FOR REFERENCE:
1. https://developers.google.com/identity/sign-in/android/backend-auth
2. https://developers.google.com/identity/sign-in/web/backend-auth

#

OAuthClient is a wrapper of the [Google OAuth2 API Client Library for Java](https://developers.google.com/api-client-library/java/apis/oauth2/v1) 
to provide an easier API and a structured way of adding support to new services. 
It also fixes a couple of issues of the original library with some services
 (specifically Dropbox and Facebook).

# How to use it #

The library has been designed to hide all of the complexities of the original library by making assumptions and
reducing many customization options. An example of how to use the library in your code is as follows 
(a full example can be found at [OAuthSample](src/test/java/es/us/oauthclient/OAuthSample.java)):

```java
API api = new API(new GithubConfig()));
api.authorize(Arrays.asList("user", "user:follow"));
HttpResponse resp = api.get("https://api.github.com/user");
System.out.println(resp.parseAsString());
```

The response is Google's `HttpResponse`. From it, you can parse the response as a string (like in the example) or
you can parse its JSON to a Java class directly using method `parseAs(classname)`. 

Besides `get`, you can also use methods `post`, `put`, and `delete` directly. For other HTTP verbs, you can have access
to the `HttpRequestFactory` of Google's library by means of method `getHttpRequestlFactory()`. 

In addition, before running this code, you need to include the client id and the client secret in a configuration
file that by default resides in `$HOME/.store/keys.properties`. This is how this file looks like (obviously, keys and
secret should be changed by your own):

```
dropbox_key=xxxxxxxxxxxx
dropbox_secret=yyyyyyyyyyyyyyyyy
google_key=aaaaaaaakssssssssss
google_secret=dasfffffffffffdaaaaaaa
spotify_key=dasfdasdsfffffffff
spotify_secret=dsfa89a87fsadg870as87fg
```

## How to add support for new services ##

In the current version, there is support for Dailymotion, Dropbox, Facebbok, Github, Google and Spotify. However,
support to other APIs can be added very easily. You just need to create a new class that extends `AbstractOAuth2Config`
and implement the required methods. In addition, you can use the utilities provided by that class to load keys, secret and 
`DataStoreFactory`. Next, you can find an example of how the spotify service configuration looks like.

```java
public class SpotifyConfig extends AbstractOAuth2Config implements OAuth2Config {

  private static final String CLIENTNAME = "spotify";
  
  private static DataStoreFactory DATA_STORE_FACTORY = buildFactory(CLIENTNAME);
  
  private static final String AUTHORIZATION_SERVER_URL = "https://accounts.spotify.com/authorize";
  private static final String TOKEN_SERVER_URL = "https://accounts.spotify.com/api/token";

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
```

If you add support to a new service, please create a pull request so that I can include it in the repository.
