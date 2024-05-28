package project.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import project.scan.FileScan;

public class FileController extends BaseController {
    
    final FileChooser fc = new FileChooser();
    @FXML
	public void handleFileSubmitButtonAction(ActionEvent event) throws IOException{
		fc.setTitle("Open Resource File");
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));

		File file = fc.showOpenDialog(null);

		if (file != null) {
			FileScan fileScan = new FileScan(file);
				new Thread(() -> {
					List<Map<String, List<List<String>>>> s = fileScan.scan();
					Platform.runLater(() -> {
						listResult(s);
					});
				}).start();
			}

		}
}
