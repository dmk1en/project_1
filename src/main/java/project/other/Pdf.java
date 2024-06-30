package project.other;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;

public class Pdf {
    private String pdfFile;

    public Pdf(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    public void createPdf(JSONObject lastAnalysisResults, JSONObject lastAnalysisStats, String name) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            document.open();
            document.add(new Paragraph("Scan Results for: " + name + "\n\n",FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD)));


            document.add(new Paragraph("Scan Stats:", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
            for (Iterator<String> it = lastAnalysisStats.keys(); it.hasNext(); ) {
                String key = it.next();
                Integer value = lastAnalysisStats.getInt(key);
                document.add(new Paragraph(key + ": " + value));
            }

            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph("Scan Results:", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));

            for (Iterator<String> it = lastAnalysisResults.keys(); it.hasNext(); ) {
                String key = it.next();
                JSONObject resultObject = lastAnalysisResults.getJSONObject(key);
                String engineName = resultObject.getString("engine_name");
                String method = resultObject.getString("method");
                String category = resultObject.getString("category");
                String result = resultObject.optString("result");

                document.add(new Paragraph("Engine Name: " + engineName));
                document.add(new Paragraph("Method: " + method));
                document.add(new Paragraph("Category: " + category));
                document.add(new Paragraph("Result: " + result));
                document.add(new Paragraph("\n"));
            }

            document.close();
        } catch (DocumentException | FileNotFoundException e) {
        }
    }
}