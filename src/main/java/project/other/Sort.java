package project.other;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Sort {
    private JSONObject json;
    private List<List<String>> malicious = new ArrayList<>();
    private List<List<String>> suspicious = new ArrayList<>();
    private List<List<String>> undetected = new ArrayList<>();
    private List<List<String>> harmless = new ArrayList<>();
    private List<List<String>> timeout = new ArrayList<>();
    
    public Sort(JSONObject json) {
        this.json = json;
    }
    
    public List<Map<String, List<List<String>>>> combineLists() {
        classify();
        // [{Type: [[Vendor, Result],[Vendor, Result],...]}; ...]
        Map<String, List<List<String>>> combinedLists = new LinkedHashMap<>();
        combinedLists.put("Malicious", malicious);
        combinedLists.put("Suspicious", suspicious);
        combinedLists.put("Harmless", harmless);
        combinedLists.put("Undetected", undetected);
        combinedLists.put("Timeout", timeout);
        
        List<Map<String, List<List<String>>>> result = new ArrayList<>();
        result.add(combinedLists);
        
        return result;
    }
    
    public void classify() {
    	JSONObject j = ((json.getJSONObject("data").getJSONObject("attributes")).getJSONObject("last_analysis_results"));
        for (String vendor : j.keySet()) {
            String category = j.getJSONObject(vendor).getString("category");
            String result;
            if (j.getJSONObject(vendor).optString("result") == "") {
            	result = category;
            }else {
            	result = j.getJSONObject(vendor).optString("result");
            }
            List<String> a = new ArrayList<>();
            a.add(vendor);
            a.add(result);
            switch (category) {
                case "malicious":
                    malicious.add(a);
                    break;
                case "suspicious":
                    suspicious.add(a);
                    break;
                case "undetected":
                    undetected.add(a);
                    break;
                case "harmless":
                    harmless.add(a);
                    break;
                case "timeout":
                    timeout.add(a);
                    break;
                default:
                    // Handle unrecognized category
                    break;
            }
            
        }
    }
}

