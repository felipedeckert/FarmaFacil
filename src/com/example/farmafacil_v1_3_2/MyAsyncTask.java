package com.example.farmafacil_v1_3_2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.farmafacil_v1_3_2.HomeActivity;

public class MyAsyncTask extends AsyncTask<String, String, String[]> {
	public HomeActivity activity;
	public String url;
	public MyAsyncTask(HomeActivity x, String url){
		 activity = x;
		this.url = url;
	}

    private ProgressDialog progressDialog = new ProgressDialog(activity);
    InputStream inputStream = null;
    String result = ""; 

    protected void onPreExecute() {
        progressDialog.setMessage("Downloading your data...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
    @Override
    protected String[] doInBackground(String... params) {

    	        String url_select = url;

                ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

    	        try {
    	            // Set up HTTP post

    	            // HttpClient is more then less deprecated. Need to change to URLConnection
    	            HttpClient httpClient = new DefaultHttpClient();

    	            HttpPost httpPost = new HttpPost(url_select);
    	            httpPost.setEntity(new UrlEncodedFormEntity(param));
    	            HttpResponse httpResponse = httpClient.execute(httpPost);
    	            HttpEntity httpEntity = httpResponse.getEntity();

    	            // Read content & Log
    	            inputStream = httpEntity.getContent();
    	        } catch (UnsupportedEncodingException e1) {
    	            Log.e("UnsupportedEncodingException", e1.toString());
    	            e1.printStackTrace();
    	        } catch (ClientProtocolException e2) {
    	            Log.e("ClientProtocolException", e2.toString());
    	            e2.printStackTrace();
    	        } catch (IllegalStateException e3) {
    	            Log.e("IllegalStateException", e3.toString());
    	            e3.printStackTrace();
    	        } catch (IOException e4) {
    	            Log.e("IOException", e4.toString());
    	            e4.printStackTrace();
    	        }
    	        // Convert response to string using String Builder
    	        try {
    	            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
    	            StringBuilder sBuilder = new StringBuilder();

    	            String line = null;
    	            while ((line = bReader.readLine()) != null) {
    	                sBuilder.append(line + "\n");
    	            }

    	            inputStream.close();
    	            result = sBuilder.toString();
    	            
    	        } catch (Exception e) {
    	            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
    	        }
    	        String [] list = new String[2];
    	        
    	        return list;
    	 } // protected Void doInBackground(String... params)
    	 protected void onPostExecute(Void v){
    	        //parse JSON data
    	        try {
    	            JSONArray jArray = new JSONArray(result);    
    	            for(int i=0; i < jArray.length(); i++) {

    	                JSONObject jObject = jArray.getJSONObject(i);

    	                String name = jObject.getString("name");
    	                String tab1_text = jObject.getString("tab1_text");
    	                int active = jObject.getInt("active");

    	            } // End Loop
    	            this.progressDialog.dismiss();
    	        } catch (JSONException e) {
    	            Log.e("JSONException", "Error: " + e.toString());
    	        } // catch (JSONException e)
    	 } // protected void onPostExecute(Void v)
 
} 