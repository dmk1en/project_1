package project.other;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.json.JSONTokener;

public class PushCsv {
	private String jsonData;
	public PushCsv(String json) {
		this.jsonData = json;
	}

    public void push() {
        // JSON data as a String

        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(new JSONTokener(jsonData));
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONObject attributesObject = dataObject.getJSONObject("attributes");
        JSONObject lastAnalysisResults = attributesObject.getJSONObject("last_analysis_results");

        // Define the output CSV file
        String type = dataObject.getString("type");

        String name ;

        switch (type) {
            case "file":
                if (attributesObject.getJSONArray("names").length() == 1) {
                    name = "file";
                }else{
                    name = attributesObject.getJSONArray("names").get(0).toString();
                }
                break;
            case "url":
                name = attributesObject.getString("url");
                break;
            case "ip_address":
                name = dataObject.getString("id");
                break;
            case "domain":
                name = dataObject.getString("id");
                break;
            default:
            name = "";
            break;
        }


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String time = now.format(formatter);

        String csvFile = "result/csv/" + type + "/" + time + ".csv" ;

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8)) {
            // Write CSV header
            writer.append(name + "\n");

            writer.append("Engine Name,Method,Category,Result\n");

            // Iterate through last_analysis_results and write to CSV
            for (String key : lastAnalysisResults.keySet()) {
                JSONObject resultObject = lastAnalysisResults.getJSONObject(key);
                String engineName = resultObject.getString("engine_name");
                String method = resultObject.getString("method");
                String category = resultObject.getString("category");
                String result = resultObject.optString("result");

                // Write data to CSV
                writer.append(engineName)
                      .append(',')
                      .append(method)
                      .append(',')
                      .append(category)
                      .append(',')
                      .append(result)
                      .append('\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // JSON data as a String
        String filePath = "src/main/java/project/scan/response.json";
        
        String json;
        try {
            Path path = Paths.get(filePath);
            json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            PushCsv get = new PushCsv(json);
            get.push();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}