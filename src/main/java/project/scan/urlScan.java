package project.scan;

import java.util.Base64;
import java.util.List;
import java.util.Map;


public class UrlScan{
	private String url;
	
	public UrlScan(String url) {
		this.url = "https://www.virustotal.com/api/v3/urls/" + Base64.getEncoder().withoutPadding().encodeToString(url.getBytes());
	}
	
	public List<Map<String, List<List<String>>>> scan() {
		BaseScan baseScan = new BaseScan(url);
        return baseScan.scan();
	}
	
}

