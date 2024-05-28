package project.demo.controller;

import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.scan.IpScan;

public class IpController extends BaseController {
    @FXML
	private TextField ip;
    
    @FXML
	public void handleIpSubmitButtonAction(ActionEvent event) {
		String SubmittedUrl = ip.getText();	
		if (SubmittedUrl != null) {
			new Thread(() -> {
				List<Map<String, List<List<String>>>> s = new IpScan(SubmittedUrl).scan();
				Platform.runLater(() -> {
					listResult(s);
				});
			}).start();
		}
	}
}
