package project.scan;


import java.util.List;
import java.util.Map;

public class IpScan {
    private String ip;
    private String url;

    public IpScan(String ip) {
        this.ip = ip;
        this.url = "https://www.virustotal.com/api/v3/ip_addresses/" + this.ip;
    }

    public List<Map<String, List<List<String>>>> scan() {
        BaseScan baseScan = new BaseScan(url);
        return baseScan.scan();
    }  
}
