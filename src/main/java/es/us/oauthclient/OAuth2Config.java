package es.us.oauthclient;


abstract class OAuth2Config implements OAuth2ConfigInterface {

	private final String KEY = "GET KEY FROM    https://console.developers.google.com/apis/credentials";
	private final String SECRET = "GET SECRET FROM    https://console.developers.google.com/apis/credentials";


	/** Port in the "Callback URL". */
	private final int PORT = 8080;

	/** Domain name in the "Callback URL". */
	private final String DOMAIN = "127.0.0.1";


	public String domainCallback() {
		return DOMAIN;
	}

	public int portCallback() {
		return PORT;
	}

	public String getKey() {
		return KEY;
	}
	
	public String getSecret() {
		return SECRET;
	}

}
