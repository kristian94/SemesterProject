/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forwarding;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristian Nielsen
 */
public class ForwarderCallable implements Callable<String> {

    private Gson gson = new Gson();
    private String url;
    private String body;
    private String method;

    protected ForwarderCallable(String url, String body, String method) {
        this.url = url;
        this.body = body;
        this.method = method;
        Unirest.setTimeouts(1000, 1000);
    }

    @Override
    public String call() throws Exception {
        switch (method) {
            case "GET":
                return unirestGet();
            case "POST":
                return unirestPost();
        }
        return null;
    }

    public String unirestGet() {
        String result = "";
        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(url).asJson();

            if (httpResponse.getStatus() == 200) {
                result = httpResponse.getBody().toString();
            } else {
                printResponse(httpResponse);
            }

        } catch (Exception ex) {
            result = "";
        }
        System.out.println(url + " - unirestGet: " + result);
        return result;
    }

    public String unirestPost() {
        System.out.println("Posting to " + url);
        System.out.println("Body: " + body);
        Map<String, String> headers = new HashMap();
        headers.put("content-type", "application/json");
//        headers.put("method", "POST");
        headers.put("accept", "application/json");
        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .headers(headers)
                    .body(body).asJson();
            int code = response.getStatus();

            if (code < 400 && code >= 200) {

                return response.getBody().toString();
            } else {
                printResponse(response);
            }

        } catch (Exception ex) {
            System.out.println("Request Failed");
            return "";
        }
        return "";
    }

    private void printResponse(HttpResponse httpResponse) {
        System.out.println("Error with:" + url);
        System.out.println("Status: "+ httpResponse.getStatus());
        System.out.println("Status text: "+httpResponse.getStatusText());
        System.out.println("Response body: "+httpResponse.getBody().toString());
    }
}
