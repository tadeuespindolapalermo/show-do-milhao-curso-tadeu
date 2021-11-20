package br.com.showmilhao.application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ApplicationShowMilhao extends Application {
	
	private static Stage stage;
	private static final String FILE_MUSIC = "src/main/resources/songs/som-abertura-2.mp3";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			primaryStage.setTitle("Show do Milhão");
						
			Pane telaInicial = FXMLLoader.load(getClass().getResource("/view/LayoutTelaInicial.fxml"));
			Scene scene = new Scene(telaInicial, 800, 600);
			
			telaInicial.getStylesheets().add(getClass().getResource("/css/buttonStyle.css").toExternalForm());			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			ContinuousReprodution reprodution = new ContinuousReprodution(FILE_MUSIC, true);
			reprodution.start();			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeScene(Scene scene) {
		stage.setScene(scene);
	}	
	
}
