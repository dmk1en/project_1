package project.demo.controller;

import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Platform;
import project.scan.UrlScan;


public class UrlController extends BaseController{
	
	@FXML
	private TextField url;

	@FXML
	public void handleUrlSubmitButtonAction(ActionEvent event) {
		String SubmittedUrl = url.getText(); 
		if (SubmittedUrl != null) {
			new Thread(() -> {
				// malicious url : https://133806.com/?home=casino&a=x
				List<Map<String, List<List<String>>>> s = new UrlScan(SubmittedUrl).scan();
				Platform.runLater(() -> {
					listResult(s);
					}	
				);
			}
		).start();
		}
	}

}