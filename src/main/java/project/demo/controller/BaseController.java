package project.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BaseController {
	
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
	private Label result;
	
	@FXML
	private GridPane grid;

    private String redBlack = "-fx-text-fill: red;-fx-control-inner-background: black;";
    private String yellowBlack = "-fx-text-fill: yellow;-fx-control-inner-background: black;";
    private String grayBlack = "-fx-text-fill: darkgray;-fx-control-inner-background: black;";
    private String whiteBlack = "-fx-text-fill: white;-fx-control-inner-background: black;";
	
	
	@FXML
	public void listResult(List<Map<String, List<List<String>>>> s){
		if (grid.getChildren() != null) {
			grid.getChildren().clear();
		}
		
		if (s != null) {
			int rowNum = 0;
			for (Map<String, List<List<String>>> categoryMap : s) {
				for (Map.Entry<String, List<List<String>>> entry : categoryMap.entrySet()) {
					String category = entry.getKey();
					for ( List<String> resultList : entry.getValue()) {
						if (resultList != null) {
							RowConstraints row = new RowConstraints();
							row.setPercentHeight(300); // Set the height of the row (adjust as needed)
							grid.getRowConstraints().add(row);	
							
							TextArea vendorLabel = new TextArea(StringUtils.capitalize(resultList.get(0)));
							vendorLabel.setEditable(false);
							TextArea resultLabel = new TextArea(StringUtils.capitalize(resultList.get(1)));
							resultLabel.setEditable(false);
							resultLabel.setWrapText(true);
							String imageUrl;
							
							switch (category) {
								case "Malicious":
									imageUrl = "/project/resources/image/exclamation-mark.png";
									vendorLabel.setStyle(redBlack);
									resultLabel.setStyle(redBlack);
									break;
								case "Suspicious":
									imageUrl = "/project/resources/image/yellow-exclamation-mark.png";
									vendorLabel.setStyle(yellowBlack);
									resultLabel.setStyle(yellowBlack);
									break;
								case "Undetected":
									imageUrl = "/project/resources/image/question.png";
									vendorLabel.setStyle(grayBlack);
									resultLabel.setStyle(grayBlack);
									break;
								case "Harmless":
									imageUrl = "/project/resources/image/checkmark.png";
									vendorLabel.setStyle(whiteBlack);
									resultLabel.setStyle(whiteBlack);
									break;
								default:
									imageUrl = "/project/resources/image/checkmark.png";
									vendorLabel.setStyle(whiteBlack);
									resultLabel.setStyle(whiteBlack);
									break;
							}
							Image image = new Image(imageUrl);
							ImageView iv1 = new ImageView(image);
							iv1.setFitHeight(33.0);
							iv1.setFitWidth(23.0);
							iv1.setPreserveRatio(true);
							iv1.setSmooth(true);
							grid.add(vendorLabel, 0, rowNum);
							grid.add(resultLabel, 1, rowNum);
							grid.add(iv1,2,rowNum);
							rowNum++;
						}
					}
				}
			}
		}
	}

	public void changeTo(String fxml) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			Stage stage = (Stage) submitButton.getScene().getWindow();
			Scene scene = new Scene(root,1000,700);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeToFile() throws IOException {
		changeTo("/project/resources/fxml/file.fxml");
	}

	public void changeToUrl() throws IOException {
		changeTo("/project/resources/fxml/url.fxml");

	}

	public void changeToIp() throws IOException{
		changeTo("/project/resources/fxml/ip.fxml");

	}

	public void changtoDomain() throws IOException{
		changeTo("/project/resources/fxml/domain.fxml");
	}

}
