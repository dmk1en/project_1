package project.demo.controller;

import java.util.List;
import java.util.Map;


import java.io.File;
import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; 
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Platform;
import project.scan.FileScan;
import project.scan.UrlScan;
import project.scan.IpScan;
import project.scan.DomainScan;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import org.apache.commons.lang3.StringUtils;

public class UrlController {

	private Stage stage;
	private Scene scene;
	
	@FXML
	private ScrollPane scrollPane;

	@FXML
	private VBox mainBox;
	
	@FXML
	private Label urLabel;
	
	@FXML
	private Label fileLabel;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private TextField url;
	
	@FXML
	private Label result;
	
	@FXML
	private GridPane grid;
	
	@FXML
	public void onButtonClicked(ActionEvent event) {
		System.out.println("clicked");
	}
	
	@FXML
	public void listResult(List<Map<String, List<List<String>>>> s){
		if (s != null) {
			int row = 0;
			for (Map<String, List<List<String>>> categoryMap : s) {
				for (Map.Entry<String, List<List<String>>> entry : categoryMap.entrySet()) {
					String category = entry.getKey();
					for ( List<String> resultList : entry.getValue()) {
						if (resultList != null) {
							RowConstraints Row = new RowConstraints();
							Row.setPercentHeight(300); // Set the height of the row (adjust as needed)
							grid.getRowConstraints().add(Row);	
							
							TextArea vendorLabel = new TextArea(StringUtils.capitalize(resultList.get(0)));
							vendorLabel.setEditable(false);
							TextArea resultLabel = new TextArea(StringUtils.capitalize(resultList.get(1)));
							resultLabel.setEditable(false);
							resultLabel.setWrapText(true);
							String imageUrl;
							
							switch (category) {
								case "Malicious":
									imageUrl = "/project/resources/image/exclamation-mark.png";
									vendorLabel.setStyle("-fx-text-fill: red;-fx-control-inner-background: black;");
									resultLabel.setStyle("-fx-text-fill: red;-fx-control-inner-background: black;");
									break;
								case "Suspicious":
									imageUrl = "/project/resources/image/yellow-exclamation-mark.png";
									vendorLabel.setStyle("-fx-text-fill: yellow;-fx-control-inner-background: black;");
									resultLabel.setStyle("-fx-text-fill: yellow;-fx-control-inner-background: black;");
									break;
								case "Undetected":
									imageUrl = "/project/resources/image/question.png";
									vendorLabel.setStyle("-fx-text-fill: darkgray;-fx-control-inner-background: black;");
									resultLabel.setStyle("-fx-text-fill: darkgray;-fx-control-inner-background: black;");
									break;
								case "Harmless":
									imageUrl = "/project/resources/image/checkmark.png";
									vendorLabel.setStyle("-fx-text-fill: white;-fx-control-inner-background: black;");
									resultLabel.setStyle("-fx-text-fill: white;-fx-control-inner-background: black;");
									break;
								default:
									imageUrl = "/project/resources/image/checkmark.png";
									vendorLabel.setStyle("-fx-text-fill: white;-fx-control-inner-background: black;");
									resultLabel.setStyle("-fx-text-fill: white;-fx-control-inner-background: black;");
									break;
							}
							Image image = new Image(imageUrl);
							ImageView iv1 = new ImageView(image);
							iv1.setFitHeight(33.0);
							iv1.setFitWidth(23.0);
							iv1.setPreserveRatio(true);
							iv1.setSmooth(true);
							grid.add(vendorLabel, 0, row);
							grid.add(resultLabel, 1, row);
							grid.add(iv1,2,row);
							row++;
						}
					}
				}
			}
		}
	};


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
	
	@FXML
	public void handleIpSubmitButtonAction(ActionEvent event) {
		String SubmittedUrl = url.getText();	
		if (SubmittedUrl != null) {
			new Thread(() -> {
				List<Map<String, List<List<String>>>> s = new IpScan(SubmittedUrl).scan();
				Platform.runLater(() -> {
					listResult(s);
				});
			}).start();
		}
	}

	@FXML
	public void handleDomainSubmitButtonAction(ActionEvent event) {
		String SubmittedUrl = url.getText();	
		if (SubmittedUrl != null) {
			new Thread(() -> {
				List<Map<String, List<List<String>>>> s = new DomainScan(SubmittedUrl).scan();
				Platform.runLater(() -> {
					listResult(s);
				});
			}).start();
		}
	}


	public void changeTo(String fxml) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			stage = (Stage) submitButton.getScene().getWindow();
			scene = new Scene(root,1000,700);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeToFile(MouseEvent event) throws IOException {
		changeTo("/project/resources/fxml/file.fxml");
	}

	public void changeToUrl(MouseEvent event) throws IOException {
		changeTo("/project/resources/fxml/url.fxml");

	}

	public void changeToIp(MouseEvent event) throws IOException{
		changeTo("/project/resources/fxml/ip.fxml");

	}

	public void changtoDomain(MouseEvent event) throws IOException{
		changeTo("/project/resources/fxml/domain.fxml");
	}


}