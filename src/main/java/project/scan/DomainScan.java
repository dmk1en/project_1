package project.scan;

import java.util.List;
import java.util.Map;

public class DomainScan {
    private String domain;
    private String url;

    public DomainScan(String domain){
        this.domain = domain;
        this.url = "https://www.virustotal.com/api/v3/domains/" + this.domain; 
    }

    public List<Map<String, List<List<String>>>> scan() {
        BaseScan baseScan = new BaseScan(url);
        return baseScan.scan();
    }

}
