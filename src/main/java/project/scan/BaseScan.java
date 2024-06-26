package project.scan;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import project.other.*;
import okhttp3.Request;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

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
                    JSONObject jsonObject = new JSONObject(new JSONTokener(responseBody));
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject attributesObject = dataObject.getJSONObject("attributes");
                    JSONObject lastAnalysisResults = attributesObject.getJSONObject("last_analysis_results");
                    if (lastAnalysisResults.length() == 0) {
                        Thread.sleep(10000);
                        return scan();
                    }else {           
                        //push the data to a csv file
                        PushCsv csv = new PushCsv(responseBody);
                        csv.push();

                        //push the data to a pdf file
                        PushPdf pdf = new PushPdf(responseBody);
                        pdf.push();
                        
                        //sort the data
                        return new Sort(jsonObject).combineLists();
                    }               
                }
                return Collections.emptyList();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
        
	}
}
