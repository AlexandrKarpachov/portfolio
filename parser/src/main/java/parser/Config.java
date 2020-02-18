package parser;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	private final Properties values = new Properties();

	/**
	 * load all properties from the file
	 */
	public Config init() {
		try (InputStream in = parser.Config.class.getClassLoader().getResourceAsStream("parser.properties")) {
			//noinspection all
			values.load(in);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return this;
	}

	/**
	 * returns properties by key.
	 * !<b>Attention</b> works only after using {@link this#init()}
	 */
	public String get(String key) {
		return this.values.getProperty(key);
	}
}
