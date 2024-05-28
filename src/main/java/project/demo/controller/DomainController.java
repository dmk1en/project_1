package project.demo.controller;

import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.scan.DomainScan;

public class DomainController extends BaseController {
    @FXML
	private TextField domain;
    
    @FXML
	public void handleDomainSubmitButtonAction(ActionEvent event) {
		String SubmittedUrl = domain.getText();	
		if (SubmittedUrl != null) {
			new Thread(() -> {
				List<Map<String, List<List<String>>>> s = new DomainScan(SubmittedUrl).scan();
				Platform.runLater(() -> {
					listResult(s);
				});
			}).start();
		}
	}
}
