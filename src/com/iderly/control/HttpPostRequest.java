package com.iderly.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

public abstract class HttpPostRequest extends AsyncTask<Void, Void, String> {
	/**
	 * Connection timeout 10,000 milliseconds.
	 */
	public static final int connectionTimeoutMilliseconds = 10000;
	
	protected Object[] mixed;

	private List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	private String url;
	private boolean success = false;
	private int statusCode;
	
	public HttpPostRequest(String url, Object... mixed) {
		this.url = url;
		this.mixed = mixed;
	}
	
	public HttpPostRequest addParameter(String name, String value) {
		parameters.add(new BasicNameValuePair(name, value));
		return this;
	}
	
	public HttpPostRequest addParameters(Pair<String, String>... parameters) {
		for(Pair<String, String> param: parameters) {
			this.parameters.add(new BasicNameValuePair(param.first, param.second));
		}
		return this;
	}
	
	public HttpPostRequest addParameters(List<Pair<String, String>> parameters) {
		for(Pair<String, String> param: parameters) {
			this.parameters.add(new BasicNameValuePair(param.first, param.second));
		}
		return this;
	}
	
	public void send() {
		this.execute();
	}
	
	/**
	 * On AsyncTask finish
	 * @param responseText
	 */
	public abstract void onFinish(int statusCode, String responseText);
	
	@Override
	protected String doInBackground(Void... params) {
		String responseText = null;
		
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// Do nothing
		}
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeoutMilliseconds);
		
		try {
			HttpResponse httpResponse = new DefaultHttpClient(httpParams).execute(httpPost);
			
			statusCode = httpResponse.getStatusLine().getStatusCode();
			responseText = EntityUtils.toString(httpResponse.getEntity());
		} catch (ClientProtocolException e) {
			// Just printing exception
			e.printStackTrace();
		} catch (IOException e) {
			// Just printing exception
			e.printStackTrace();
		}
		
		return responseText;
	}
	
	@Override
	protected void onPostExecute(String responseText) {
		this.onFinish(statusCode, responseText);
	}
}