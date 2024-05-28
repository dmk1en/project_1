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
    private String xApikey = "50f7f83dad42f5faf6775e773ce419e430a6d37f9a32edfecf4011a2fa26aafa";

    public BaseScan(String url) {
        this.client = new OkHttpClient();
        this.url = url;
    }

    public List<Map<String, List<List<String>>>> scan() {
        

        //Create Request object with the URL
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-apikey", xApikey)
                .build();

        try {
            // Execute the request and get the response
            Response response = client.newCall(request).execute();
            
            // Check if the request was successful (status code 200)
            if (response.isSuccessful()) {
                // Get response body as string
                String responseBody = response.body().string();
                if (responseBody != null) {
                	String json = responseBody;
                    
                    // //push the data to a csv file
                    // PushCsv get = new PushCsv(json);
                    // get.push();

                	JSONObject jsonObject = new JSONObject(json);
                	return new Sort(jsonObject).combineLists();
                    
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
