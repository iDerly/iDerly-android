package com.iderly.control;

public abstract class HttpPostRequestListener {
	protected Object[] mixed;
	
	public HttpPostRequestListener(Object... mixed) {
		this.mixed = mixed;
	}
	
	public abstract void onFinish(int statusCode, String responseText);
}
