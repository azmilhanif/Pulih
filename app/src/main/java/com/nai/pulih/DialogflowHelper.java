package com.nai.pulih;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONObject;

public class DialogflowHelper {

    private static final String PROJECT_ID = "pulih-465813";
    private static final String LANGUAGE_CODE = "en-US";

    private final Context context;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final OkHttpClient client = new OkHttpClient();

    public interface DialogflowCallback {
        void onResponse(String responseText);
        void onError(Exception e);
    }

    public DialogflowHelper(Context context) {
        this.context = context;
    }

    public void detectIntent(String message, DialogflowCallback callback) {
        executorService.execute(() -> {
            Response response = null;
            try {
                String accessToken = getAccessToken();
                if (accessToken == null) throw new IOException("Failed to get access token");

                String sessionId = UUID.randomUUID().toString();

                JSONObject queryInput = new JSONObject();
                JSONObject textInput = new JSONObject();
                textInput.put("text", message);
                textInput.put("languageCode", LANGUAGE_CODE);
                queryInput.put("text", textInput);

                JSONObject requestBodyJson = new JSONObject();
                requestBodyJson.put("queryInput", queryInput);

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json"),
                        requestBodyJson.toString()
                );

                Request request = new Request.Builder()
                        .url("https://dialogflow.googleapis.com/v2/projects/" + PROJECT_ID + "/agent/sessions/" + sessionId + ":detectIntent")
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .post(body)
                        .build();

                response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "No error body";
                    throw new IOException("Unexpected code " + response.code() + ": " + errorBody);
                }

                String responseStr = response.body() != null ? response.body().string() : "";

                JSONObject jsonResponse = new JSONObject(responseStr);
                String fulfillmentText = jsonResponse
                        .getJSONObject("queryResult")
                        .getString("fulfillmentText");

                mainHandler.post(() -> callback.onResponse(fulfillmentText));

            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            } finally {
                if (response != null) response.close();
            }
        });
    }

    private String getAccessToken() throws IOException {
        InputStream stream = context.getResources().openRawResource(R.raw.service_account);
        GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                .createScoped("https://www.googleapis.com/auth/cloud-platform");
        credentials.refreshIfExpired();
        AccessToken token = credentials.getAccessToken();
        if (token == null || token.getExpirationTime() == null || token.getExpirationTime().before(new Date())) {
            credentials.refresh();
            token = credentials.getAccessToken();
        }
        return token.getTokenValue();
    }
}
