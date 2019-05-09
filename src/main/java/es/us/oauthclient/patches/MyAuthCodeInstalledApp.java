package es.us.oauthclient.patches;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.http.HttpResponse;

/**
 * @author resinas
 *
 */
public class MyAuthCodeInstalledApp extends AuthorizationCodeInstalledApp {

	public MyAuthCodeInstalledApp(AuthorizationCodeFlow flow, VerificationCodeReceiver receiver) {
		super( flow, receiver );
	}


	@Override
	public Credential authorize(String userId) throws IOException {
		try {
			Credential credential = getFlow().loadCredential(userId);
			if (credential != null
					&& (credential.getRefreshToken() != null
							|| credential.getExpiresInSeconds() == null || credential
							.getExpiresInSeconds() > 60)) {
				return credential;
			}
			// open in browser
			String redirectUri = getReceiver().getRedirectUri();
			AuthorizationCodeRequestUrl authorizationUrl = getFlow()
					.newAuthorizationUrl().setRedirectUri(redirectUri);
			onAuthorization(authorizationUrl);
			// receive authorization code and exchange it for an access token
			String code = getReceiver().waitForCode();
			HttpResponse httpresponse = getFlow().newTokenRequest(code)
					.setRedirectUri(redirectUri).executeUnparsed();
			String resp = httpresponse.parseAsString();
			TokenResponse response;
			try {
				response = getFlow().getJsonFactory().createJsonParser(resp)
						.parse(TokenResponse.class);
			} catch (Exception e) {
				response = tryAltResponseParsing(resp);
			}

			// store credential and return it
			return getFlow().createAndStoreCredential(response, userId);
		} finally {
			getReceiver().stop();
		}
	}


	private TokenResponse tryAltResponseParsing(String resp) {
		TokenResponse tokenResponse = new TokenResponse();

		String TOKEN_REGEX = "access_token=([^&]+).*expires=([^&]+)";
		String CHARSET = "UTF-8";

		Matcher matcher = Pattern.compile(TOKEN_REGEX).matcher(resp);
		try {
			if (matcher.find()) {
				String token;
				token = URLDecoder.decode(matcher.group(1), CHARSET);
				String expires = URLDecoder.decode(matcher.group(2), CHARSET);
				tokenResponse.setAccessToken(token);
				tokenResponse.setExpiresInSeconds(Long.parseLong(expires));
			} else {
				throw new RuntimeException("Invalid response: " + resp);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Invalid response: " + resp, e);
		}

		return tokenResponse;

	}

}
