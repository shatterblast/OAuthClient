package es.us.oauthclient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import com.google.api.client.http.HttpResponse;

import es.us.oauthclient.apis.DailymotionConfig;
import es.us.oauthclient.apis.DropboxConfig;
import es.us.oauthclient.apis.FacebookConfig;
import es.us.oauthclient.apis.GithubConfig;
import es.us.oauthclient.apis.GoogleConfig;
import es.us.oauthclient.apis.SpotifyConfig;


/**
 * A sample application that demonstrates how the library can be used to authenticate
 *
 */
public class OAuthSample {




  public static void main(String[] args) {
    try {
      runDailyMotion(new API(new DailymotionConfig()));
      runGoogle(new API(new GoogleConfig()));
      runDropbox(new API(new DropboxConfig()));
      runSpotify(new API(new SpotifyConfig()));
      runFacebook(new API(new FacebookConfig()));
      runGithub(new API(new GithubConfig()));
      // Success!
      return;
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (Throwable t) {
      t.printStackTrace();
    }
    System.exit(1);
  }

  private static void runGithub(API api) throws IOException {
	api.authorize(Arrays.asList("user", "user:follow"));
	HttpResponse resp = api.get("https://api.github.com/user");
	System.out.println(resp.parseAsString());
	
}

private static void runFacebook(API api) throws IOException {
	  api.authorize(Arrays.asList("user_about_me"));
	  HttpResponse resp = api.get("https://graph.facebook.com/v2.2/me");
	  System.out.println(resp.parseAsString());
	
}

private static void runSpotify(API api) throws IOException {
	  api.authorize(Arrays.asList("user-library-read","playlist-read-private"));
	  HttpResponse resp = api.get("https://api.spotify.com/v1/me/albums");
	  System.out.println(resp.parseAsString());
	
}

/**
   * @param api
   * @throws IOException
   */
  private static void runDropbox(API api) throws IOException {
    api.authorize(Collections.<String>emptyList());
    HttpResponse resp = api.post("https://api.dropboxapi.com/2/users/get_current_account", null);
    System.out.println(resp.parseAsString());

  }

  /**
   * @param api
   * @throws IOException
   */
  private static void runGoogle(API api) throws IOException {
    api.authorize(Arrays.asList("profile"));
    HttpResponse resp = api.get("https://www.googleapis.com/plus/v1/people/me");
    System.out.println(resp.parseAsString());
  }
  

  private static void runDailyMotion(API api) throws IOException {
    String urlPath = "https://api.dailymotion.com/videos";
    api.authorize(Arrays.asList("read"));


    HttpResponse response = api.get(urlPath);
    System.out.println(response.parseAsString());
  }
}
