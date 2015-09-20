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

import android.os.AsyncTask;
import android.util.Pair;

public abstract class HttpPostRequest extends AsyncTask<Void, Void, String> {
	/**
	 * Connection timeout 10,000 milliseconds.
	 */
	public static final int connectionTimeoutMilliseconds = 10000;
	
	private String url;
	private boolean success = false;
	private List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	
	public HttpPostRequest(String url) {
		this.url = url;
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
	
	public void send() {
		this.executeOnExecutor(THREAD_POOL_EXECUTOR);
	}
	
	/**
	 * On status code not HTTP_OK
	 * @param responseText
	 */
	public abstract void onFail(String responseText);
	
	/**
	 * On status code HTTP_OK
	 * @param responseText
	 */
	public abstract void onSuccess(String responseText);
	
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
			if(httpResponse.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				success = true;
				responseText = httpResponse.toString();
			} else {
				responseText = httpResponse.getStatusLine().getReasonPhrase();
			}
		} catch (ClientProtocolException e) {
			// Just printing exception
			e.printStackTrace();
		} catch (IOException e) {
			// Just printing exception
			e.printStackTrace();
		}
		
		return responseText;
	}
	
	protected void onPostExecute(String result) {
		if(success) {
			this.onSuccess(result);
		} else {
			this.onFail(result);
		}
	}
}