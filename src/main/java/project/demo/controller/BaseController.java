package project.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	private PieChart pieChart;
	
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
		
		int maliciousNum = 0;
		int suspiciousNum = 0;
		int harmlessNum = 0;
		int undetectedNum = 0;
		int timeoutNum = 0;

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
									maliciousNum ++;
									break;
								case "Suspicious":
									imageUrl = "/project/resources/image/yellow-exclamation-mark.png";
									vendorLabel.setStyle(yellowBlack);
									resultLabel.setStyle(yellowBlack);
									suspiciousNum ++;
									break;
								case "Undetected":
									imageUrl = "/project/resources/image/question.png";
									vendorLabel.setStyle(grayBlack);
									resultLabel.setStyle(grayBlack);
									undetectedNum ++;
									break;
								case "Harmless":
									imageUrl = "/project/resources/image/checkmark.png";
									vendorLabel.setStyle(whiteBlack);
									resultLabel.setStyle(whiteBlack);
									harmlessNum ++;
									break;
								case "Timeout":
									imageUrl = "/project/resources/image/question.png";
									vendorLabel.setStyle(grayBlack);
									resultLabel.setStyle(grayBlack);
									timeoutNum ++;
									break;
								default:
									imageUrl = "/project/resources/image/checkmark.png";
									vendorLabel.setStyle(whiteBlack);
									resultLabel.setStyle(whiteBlack);
									harmlessNum ++;
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
			ObservableList<PieChart.Data> pieChartData = 
			FXCollections.observableArrayList(
			new PieChart.Data("Malicious",maliciousNum),
			new PieChart.Data("Suspicious",suspiciousNum),
			new PieChart.Data("Harmless",harmlessNum),
			new PieChart.Data("Undetected/Timeout",undetectedNum+timeoutNum)
			);

			pieChart.titleProperty().set("Results");
			pieChart.setPrefSize(500, 300);
			pieChart.setLabelsVisible(false);
			pieChart.setData(pieChartData);
			((Label) pieChart.lookup(".chart-title")).setTextFill(Color.web("#ffffff"));
			((Label) pieChart.lookup(".chart-title")).setStyle("-fx-font-size: 30px;");

			for (final PieChart.Data data : pieChartData) {
				data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
					new EventHandler<MouseEvent>() {
						@Override public void handle(MouseEvent e) {
							//System.out.println("enter");
							Tooltip tooltip = new Tooltip();
							tooltip.setText( (int) data.getPieValue() + "");
							Tooltip.install(data.getNode(), tooltip);
						}
					}
				);

			}
		}else {
			TextArea result = new TextArea("No results found");
			result.setEditable(false);
			grid.add(result, 0, 0);
		}
	}

	public void changeTo(String fxml) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			Stage stage = (Stage) submitButton.getScene().getWindow();
			Scene scene = new Scene(root,1300,700);
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
