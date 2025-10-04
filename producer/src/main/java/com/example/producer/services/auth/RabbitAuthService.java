package com.example.producer.services.auth;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Service
public class RabbitAuthService {

    public boolean authenticate(String username, String password) {
        try {
            URL url = new URL("http://localhost:15672/api/whoami");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String basicAuth = "Basic " + Base64.getEncoder()
                    .encodeToString((username + ":" + password).getBytes());
            conn.setRequestProperty("Authorization", basicAuth);
            conn.setRequestMethod("GET");
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}

