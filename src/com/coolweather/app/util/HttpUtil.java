package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection httpURLConnection = null;
				try {
					URL url = new URL(address);
					httpURLConnection = (HttpURLConnection)url.openConnection();
					httpURLConnection.setRequestMethod("GET");
					httpURLConnection.setConnectTimeout(8000);
					httpURLConnection.setReadTimeout(8000);
					InputStream is = httpURLConnection.getInputStream();
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					StringBuffer response = new StringBuffer();
					String line;
					while((line = reader.readLine())!=null){
						response.append(line);
					}
					if(listener != null){
						listener.onFinish(response.toString());
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(listener != null){
						listener.onError(e);
					}
				}finally{
					if(httpURLConnection != null){
						httpURLConnection.disconnect();
					}
				}
			}
		}).start();
		
	}
}
