package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading application configuration.
 *
 * <p>Configuration values are loaded from two property files:
 * <ul>
 *     <li><b>config-bank.properties</b> - Default project configuration.</li>
 *     <li><b>generated-test-data.properties</b> - Runtime-generated test data
 *     created by UserSetupTest.</li>
 * </ul>
 *
 * <p>Runtime properties take precedence over default properties.
 */
public final class ConfigReader {

    // =========================================================
    // FIELDS
    // =========================================================

    private static final Properties configProperties = new Properties();
    private static final Properties generatedProperties = new Properties();

    static {
        loadProperties("config-bank.properties", configProperties);
        loadProperties("generated-test-data.properties", generatedProperties);
    }

    private ConfigReader() {
        // Prevent instantiation
    }

    // =========================================================
    // PUBLIC METHODS
    // =========================================================

    /**
     * Get a configuration value by key.
     *
     * <p>The method first checks the runtime-generated properties.
     * If the key is not found, it falls back to the default
     * configuration file.
     *
     * @param key Property key.
     * @return Property value.
     * @throws IllegalStateException if the property does not exist
     *                          in either configuration file.
     */
    public static String getProperty(String key) {

        String generatedValue = generatedProperties.getProperty(key);

        if (generatedValue != null && !generatedValue.isBlank()) {
            return generatedValue;
        }

        String configValue = configProperties.getProperty(key);

        if (configValue == null || configValue.isBlank()) {
            throw new IllegalStateException(
                    "Missing required config property: " + key
            );
        }

        return configValue;
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    /**
     * Load properties from a file in the classpath.
     *
     * @param fileName   Properties file name.
     * @param properties Target Properties object.
     */
    private static void loadProperties(
            String fileName,
            Properties properties
    ) {
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load properties file: " + fileName,
                    e
            );
        }
    }
}