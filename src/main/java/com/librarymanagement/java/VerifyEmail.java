package com.librarymanagement.java;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class VerifyEmail {

    private static final String API_KEY = "your_hunter_api_key";
    private static final String API_URL = "https://api.hunter.io/v2/email-verifier?email=%s&api_key=%s";

    public boolean verifyEmail(String email) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format(API_URL, email, API_KEY);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            String status = jsonObject.getJSONObject("data").getString("status");
            return status.equals("valid") || status.equals("webmail");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
