package project.scan;

import java.io.FileWriter;
import java.io.IOException;
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
        String csvFile = "analysis_result.csv";

        try (FileWriter writer = new FileWriter(csvFile)) {
            // Write CSV header
            writer.append("Engine Name,Method,Category,Result\n");

            // Iterate through last_analysis_results and write to CSV
            for (String key : lastAnalysisResults.keySet()) {
                JSONObject resultObject = lastAnalysisResults.getJSONObject(key);
                String engineName = resultObject.getString("engine_name");
                String method = resultObject.getString("method");
                String category = resultObject.getString("category");
                String result = resultObject.getString("result");

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
}