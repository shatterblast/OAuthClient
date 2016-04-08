package es.us.oauthclient;

import java.io.IOException;
import java.util.List;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import es.us.oauthclient.patches.MyAuthCodeFlow;
import es.us.oauthclient.patches.MyAuthCodeInstalledApp;

/**
 * @author resinas
 *
 */
public class API {

  /** Global instance of the HTTP transport. */
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  /** Global instance of the JSON factory. */
  static final JsonFactory JSON_FACTORY = new JacksonFactory();

  private OAuth2Config client;

  Credential credential;

  private HttpRequestFactory requestFactory;

  public API() {
    super();
    this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
          public void initialize(HttpRequest request) throws IOException {
              request.setParser(new JsonObjectParser(JSON_FACTORY));
            }
          });
  }
  
  public API(OAuth2Config client) {
	this();
    this.client = client;
  }

  /** Authorizes the installed application to access user's protected data. */
  public void authorize(List<String> scope) throws IOException {
    if (client == null) {
	   throw new RuntimeException("No authentication config provided");  
	}
    
    AuthorizationCodeFlow flow = setUpAuthorizationCodeFlow(scope);

    // authorize
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(
        client.domainCallback()).setPort(client.portCallback()).build();

    credential = new MyAuthCodeInstalledApp(flow, receiver).authorize("user");

    requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
      public void initialize(HttpRequest request) throws IOException {
        credential.initialize(request);
        request.setParser(new JsonObjectParser(JSON_FACTORY));
      }
    });
  }

private MyAuthCodeFlow setUpAuthorizationCodeFlow(List<String> scope)
		throws IOException {
	return new MyAuthCodeFlow(new AuthorizationCodeFlow.Builder(BearerToken
      .authorizationHeaderAccessMethod(),
      HTTP_TRANSPORT,
      JSON_FACTORY,
      new GenericUrl(client.tokenServerUrl()),
      new ClientParametersAuthentication(
        client.getApiKey(), client.getApiSecret()),
      client.getApiKey(),
      client.authorizationServerUrl()).setScopes(scope)
      .setDataStoreFactory(client.store()).setRequestInitializer(
    			new HttpRequestInitializer() {
    				public void initialize(HttpRequest request) {
    					request.setHeaders(new HttpHeaders().setAccept("application/json"));
    				}
    			}
    		));
}
  
  public HttpRequestFactory getRequestFactory() {
	  return requestFactory;
  }

  public HttpResponse get(String url) throws IOException {
    if (requestFactory == null)
      throw new RuntimeException("API access must be authorized before using it");

    HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
    return request.execute();
  }

  public HttpResponse post(String url, Object data) throws IOException {
    if (requestFactory == null)
      throw new RuntimeException("API access must be authorized before using it");

    HttpContent content = data == null ? null : new JsonHttpContent(JSON_FACTORY, data);
    HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(url), content);
    return request.execute();
  }

  public HttpResponse put(String url, Object data) throws IOException {
    if (requestFactory == null)
      throw new RuntimeException("API access must be authorized before using it");

    HttpContent content = data == null ? null : new JsonHttpContent(JSON_FACTORY, data);
    HttpRequest request = requestFactory.buildPutRequest(new GenericUrl(url), content);
    return request.execute();
  }


  public HttpResponse delete(String url) throws IOException {
    if (requestFactory == null)
      throw new RuntimeException("API access must be authorized before using it");

    HttpRequest request = requestFactory.buildDeleteRequest(new GenericUrl(url));
    return request.execute();
  }
}
