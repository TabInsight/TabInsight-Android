package com.tabinsights.rest;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by biphilip on 10/3/15.
 */


public class RestClient extends AsyncTask<String, String, String> {
    public enum HTTPMethod{
        GET,
        POST
    }
    protected String doInBackground(String... params) {
        HTTPMethod method = HTTPMethod.valueOf(params[0]);
        try {
            URL endpointURL = new URL(params[1]);
            URLConnection conn = endpointURL.openConnection();

            switch (method) {
                case POST:
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    JSONObject payload = createPostPayload(params);
                    wr.write(payload.toString());
                    System.out.println(payload.toString());
                    wr.flush();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        System.out.println(output);
                    }

                default:
                    return null;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    private JSONObject createPostPayload(String... params){
        JSONObject payload = new JSONObject();
        try {
            for (int i = 2; i < params.length; i++) {
                String[] args = params[i].split("=");
                payload.put(args[0], args[1]);
            }
        }
        catch(Exception e){

        }
        return payload;
    }
}
