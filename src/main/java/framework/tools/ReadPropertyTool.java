package framework.tools;

import aquality.selenium.browser.AqualityServices;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyTool {
    private String resourcesPath;
    private String name;
    private static Properties properties;

    public ReadPropertyTool(String resourcesPath, String name) {
        this.resourcesPath = resourcesPath;
        this.name = name;
    }

    private void readProperties() {
        if (properties == null) {
            properties = new Properties();
        }
        try {
            properties.load(new FileReader(new File(resourcesPath, name)));
        } catch (IOException ex) {
            AqualityServices.getLogger().error(ex.getMessage());
        }
    }

    public String getProperty(String data) {
        initializeProperties();
        return properties.getProperty(data);
    }

    private void initializeProperties() {
        if (properties == null) {
            readProperties();
        }
    }
}