package project.scan;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UrlScan{
	private String url;
	private String xApikey = "50f7f83dad42f5faf6775e773ce419e430a6d37f9a32edfecf4011a2fa26aafa";
	
	public UrlScan(String url) {
		this.url = "https://www.virustotal.com/api/v3/urls/";
	}
	
	public List<Map<String, List<List<String>>>> scan() {
		try{
			if (scanUrl(url)){
				BaseScan baseScan = new BaseScan(this.url);
				return baseScan.scan();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public boolean scanUrl(String url) {
		OkHttpClient client = new OkHttpClient();
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("url", url)
				.build();
		Request request = new Request.Builder()
				.url("https://www.virustotal.com/api/v3/urls")
				.post(requestBody)
				.addHeader("accept", "application/json")
				.addHeader("content-type", "application/x-www-form-urlencoded")
				.addHeader("x-apikey", xApikey)
				.build();
		try {
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			JSONObject jsonObject = new JSONObject(responseString);
			String links = jsonObject.getJSONObject("data").getJSONObject("links").getString("self");
			System.out.println(links);
			request = new Request.Builder()
					.url(links)
					.get()
					.addHeader("x-apikey", xApikey)
					.build();
			response = client.newCall(request).execute();
			responseString = response.body().string();
			jsonObject = new JSONObject(responseString);
			
			links = jsonObject.getJSONObject("data").getJSONObject("links").getString("item");
			this.url = links;
			response.close();
			return response.isSuccessful();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
}

