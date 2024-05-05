package project.scan;

import java.util.Base64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class urlScan{
	private String url;
	private String response;
	
	public urlScan(String url) {
		this.url = "https://www.virustotal.com/api/v3/urls/" + Base64.getEncoder().withoutPadding().encodeToString(url.getBytes());
	}
	
	public void scan() {
		
		OkHttpClient client = new OkHttpClient();

        // URL to send the GET request
        String Url = this.url;

        // Create Request object with the URL
        Request request = new Request.Builder()
                .url(Url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-apikey", "50f7f83dad42f5faf6775e773ce419e430a6d37f9a32edfecf4011a2fa26aafa")
                .build();

        try {
            // Execute the request and get the response
            Response Response = client.newCall(request).execute();

            // Check if the request was successful (status code 200)
            if (Response.isSuccessful()) {
                // Get response body as string
                String responseBody = Response.body().string();
                this.response = responseBody;
                System.out.println("Response Body:");
                System.out.println(this.response);
            } else {
                System.out.println("Error: " + Response.code() + " - " + Response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}

