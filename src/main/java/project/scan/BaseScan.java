package project.scan;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import project.sort.Sort;
import okhttp3.Request;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BaseScan {
    protected OkHttpClient client;
    private String url;
    private String x_apikey = "50f7f83dad42f5faf6775e773ce419e430a6d37f9a32edfecf4011a2fa26aafa";

    public BaseScan(String url) {
        this.client = new OkHttpClient();
        this.url = url;
    }

    public List<Map<String, List<List<String>>>> scan() {
		
		OkHttpClient client = new OkHttpClient();

        // URL to send the GET request
        String Url = this.url;

        //Create Request object with the URL
        Request request = new Request.Builder()
                .url(Url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-apikey", x_apikey)
                .build();

        try {
            // Execute the request and get the response
            Response Response = client.newCall(request).execute();
            
            // Check if the request was successful (status code 200)
            if (Response.isSuccessful()) {
                // Get response body as string
                String responseBody = Response.body().string();
                if (responseBody != null) {
                	String json = responseBody;
                	JSONObject jsonObject = new JSONObject(json);
                	Sort sort = new Sort(jsonObject);
                	List<Map<String, List<List<String>>>> combinedLists = sort.combineLists(); //sort the data
                	return combinedLists;
                }
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
	}




}
