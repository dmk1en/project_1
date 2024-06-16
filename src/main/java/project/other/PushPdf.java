package project.other;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.json.JSONTokener;

public class PushPdf {
    private String jsonData;
	public PushPdf(String json) {
		this.jsonData = json;
	}

    public void push(){
        // JSON data as a String

        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(new JSONTokener(jsonData));
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONObject attributesObject = dataObject.getJSONObject("attributes");
        JSONObject lastAnalysisResults = attributesObject.getJSONObject("last_analysis_results");
        JSONObject lastAnalysisStats = attributesObject.getJSONObject("last_analysis_stats");

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

        String pdfFile = "result/pdf/" + type + "/" + time + ".pdf" ;

        // Create a new PDF file
        try {
            Pdf pdf = new Pdf(pdfFile);
            pdf.createPdf(lastAnalysisResults,lastAnalysisStats,name);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
