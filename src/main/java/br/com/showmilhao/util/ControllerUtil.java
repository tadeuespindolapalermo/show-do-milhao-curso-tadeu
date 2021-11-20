package br.com.showmilhao.util;

import java.io.File;

import br.com.showmilhao.application.ApplicationShowMilhao;
import br.com.showmilhao.application.JLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class ControllerUtil {
	
	private ControllerUtil() { }
	
	public static void exit() {
		System.exit(0);
	}
	
	public static void changeVoice(String voice) {
		JLayer jlayer = new JLayer();
		jlayer.tocar(new File(voice));
		jlayer.start();
	}
	
	public static void changeLayout(Class<?> classe, String layout, String css) {
		try {
			AnchorPane pane = FXMLLoader.load(classe.getResource(layout));
			pane.getStylesheets().add(classe.getResource(css).toExternalForm());
			ApplicationShowMilhao.changeScene(new Scene(pane, 800, 600));
		} catch (Exception e) {
			LogUtil.getLogger(ControllerUtil.class).error(e.getCause().toString());	
		}		
	}

}
