package es.us.oauthclient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import com.google.api.client.http.HttpResponse;


/**
 * A sample application that demonstrates how the library can be used to authenticate
 *
 */
class OAuthSample {

    public static void main(String[] args) {

        final API api = new API( new GoogleConfigInterface() );

        try {

            api.authorize( Arrays.asList("email") );

            //todo  Change this address.
            HttpResponse resp = api.get( "https://www.googleapis.com/userinfo/v2/me" );


            System.out.println( resp.parseAsString() );

            // Success!
            return;

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        System.exit(1);

    }

}
