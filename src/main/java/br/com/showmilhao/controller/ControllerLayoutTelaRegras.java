package br.com.showmilhao.controller;

import static br.com.showmilhao.util.ControllerUtil.changeLayout;
import static br.com.showmilhao.util.ControllerUtil.exit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.showmilhao.util.RegrasUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControllerLayoutTelaRegras implements Initializable {	
	
	@FXML
	private Label labelRegras;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelRegras.setText(RegrasUtil.getRegras());
	}
	
	@FXML
	private void voltar(ActionEvent event) throws IOException {
		changeLayout(getClass(), "/view/LayoutTelaInicial.fxml", "/css/buttonStyle.css");		
	}	
	
	@FXML
	private void fechar() {
		exit();
	}	

}
