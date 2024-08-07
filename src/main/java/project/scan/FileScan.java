package project.scan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FileScan {
    private File file;
    private String url;
    private String xApikey = "50f7f83dad42f5faf6775e773ce419e430a6d37f9a32edfecf4011a2fa26aafa";

    public FileScan(File file) throws IOException {
        this.file = file;
        this.url = "https://www.virustotal.com/api/v3/files/" + getFileHash(file.getAbsolutePath());
    }

    public String getFileHash(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        String sha256Hex = DigestUtils.sha256Hex(fis);
        fis.close();
        return sha256Hex;
    }

	public List<Map<String, List<List<String>>>> scan(){
        if (file.length() >= 33554432) {
            return largeFileScan();
        } else {
            return smallFileScan();
        }
	}

    public List<Map<String, List<List<String>>>> smallFileScan(){
        OkHttpClient client = new OkHttpClient();

        // Create a multipart request body
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "filename", RequestBody.create(file, MediaType.parse("application/octet-stream")))
                .build();

        // Construct the request
        Request request = new Request.Builder()
                .url("https://www.virustotal.com/api/v3/files")
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("x-apikey", xApikey)
                .build();
        try {
            // Execute the request
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                BaseScan baseScan = new BaseScan(this.url);
                return baseScan.scan();	  
            }

            response.close();
            return Collections.emptyList();
            
        }catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, List<List<String>>>> largeFileScan(){
        
        OkHttpClient client = new OkHttpClient();

        //get the upload url for large files
        Request request = new Request.Builder()
            .url("https://www.virustotal.com/api/v3/files/upload_url")
            .addHeader("x-apikey", xApikey)
            .build();
    
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            String urlPost = jsonObject.getString("data");
            response.close();

            //upload the file
            RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "filename", RequestBody.create(file, MediaType.parse("application/octet-stream")))
                .build();

            Request uploadRequest = new Request.Builder()
                .url(urlPost)
                .addHeader("x-apikey", xApikey)
                .post(requestBody)
                .build();

            Response uploadResponse = client.newCall(uploadRequest).execute();
        
            if (uploadResponse.isSuccessful()) {
                uploadResponse.close();
                BaseScan baseScan = new BaseScan(this.url);
                return baseScan.scan();	  
            }else{
                    uploadResponse.close();
                    return Collections.emptyList();
                }
            }catch (Exception e) {
            }
        return Collections.emptyList();
    }

}