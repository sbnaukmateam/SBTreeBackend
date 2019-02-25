package org.sbteam.sbtree.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.WeakHashMap;

import com.google.appengine.api.utils.SystemProperty;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SecretHolder {
    private static final String DEV_SECRETS_PATH = "src/main/webapp/WEB-INF/secrets.json";
    private static final String PROD_SECRETS_PATH = "WEB-INF/secrets.json";
    
    private static Map<String, String> cache = new WeakHashMap<>();

    public static String getSecret(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }
        try(InputStream is = new FileInputStream(getSecretsPath())) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(is, "UTF-8"));
            return (String)jsonObject.get(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getSecretsPath() {
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            return PROD_SECRETS_PATH;
        } else {
            return DEV_SECRETS_PATH;
        }
    }
}