package es.us.oauthclient;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

public abstract class AbstractOAuth2Config implements OAuth2Config {
	
	public static final java.io.File KEYS_FILE = new java.io.File(
			System.getProperty("user.home"), ".store/keys.properties");

	public static DataStoreFactory buildFactory(String clientName) {
		try {
			java.io.File dataStoreDir = new java.io.File(
					System.getProperty("user.home"), ".store/" + clientName);

			return new FileDataStoreFactory(dataStoreDir);
		} catch (IOException exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}

	/** Port in the "Callback URL". */
	public static final int PORT = 8080;

	/** Domain name in the "Callback URL". */
	public static final String DOMAIN = "127.0.0.1";

	public String domainCallback() {
		return DOMAIN;
	}

	public int portCallback() {
		return PORT;
	}
	
	private Properties props;
	
	public AbstractOAuth2Config()  {
		props = new Properties();
		try {
			props.load(new FileReader(KEYS_FILE));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Keys file not found", e);
		} 
	}
	
	protected String getKeyInProperties(String clientName) {
		return props.getProperty(clientName+"_key");
	}
	
	protected String getSecretInProperties(String clientName) {
		return props.getProperty(clientName+"_secret");
	}
	

}
