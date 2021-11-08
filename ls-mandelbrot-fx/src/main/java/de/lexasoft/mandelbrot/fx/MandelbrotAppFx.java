package de.lexasoft.mandelbrot.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MandelbrotAppFx extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ls-mandelbrot-fx.fxml"));
			root = (Parent) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lexasoft Mandelbrot");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
