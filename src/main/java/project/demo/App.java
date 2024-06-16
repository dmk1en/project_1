package project.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("/project/resources/fxml/url.fxml"));
        primaryStage.setTitle("test");
        primaryStage.setScene(new Scene(loader,1300,700));
        primaryStage.show();
    }
	
    public static void main( String[] args )  
    {
    	launch();
    }
}