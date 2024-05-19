package project.sort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Sort {
    private JSONObject json;
    private List<List<String>> Malicious = new ArrayList<>();
    private List<List<String>> Suspicious = new ArrayList<>();
    private List<List<String>> Undetected = new ArrayList<>();
    private List<List<String>> Harmless = new ArrayList<>();
    private List<List<String>> Timeout = new ArrayList<>();
    
    public Sort(JSONObject json) {
        this.json = json;
    }
    
    public List<Map<String, List<List<String>>>> combineLists() {
        classify();
        // [{Type: [[Vendor, Result],[Vendor, Result],...]}; ...]
        Map<String, List<List<String>>> combinedLists = new LinkedHashMap<>();
        combinedLists.put("Malicious", Malicious);
        combinedLists.put("Suspicious", Suspicious);
        combinedLists.put("Harmless", Harmless);
        combinedLists.put("Undetected", Undetected);
        combinedLists.put("Timeout", Timeout);
        
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
                    Malicious.add(a);
                    break;
                case "suspicious":
                    Suspicious.add(a);
                    break;
                case "undetected":
                    Undetected.add(a);
                    break;
                case "harmless":
                    Harmless.add(a);
                    break;
                case "timeout":
                    Timeout.add(a);
                    break;
                default:
                    // Handle unrecognized category
                    break;
            }
            
        }
    }
    

     //test
//    public static void main(String[] args) {
//        JSONObject jo = new JSONObject();
//        jo.put("data", new JSONObject().put("attributes", new JSONObject().put("last_analysis_results", new JSONObject().put("Artists Against 419", new JSONObject().put("category", "Malicious")))));
//
//        
//        Sort sort = new Sort(jo);
//        List<Map<String, List<String>>> combinedLists = sort.combineLists();
//        System.out.println(combinedLists);
//    }
}

