package br.com.showmilhao.controller;

import static br.com.showmilhao.util.ControllerUtil.*;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerLayoutTelaInicial {
	
	@FXML
	private void jogar(ActionEvent event) throws IOException {
		changeLayout(getClass(), "/view/LayoutTelaNome.fxml", "/css/buttonStyle.css");		
	}
	
	@FXML
	private void exibirRegras(ActionEvent event) throws IOException {
		changeLayout(getClass(), "/view/LayoutTelaRegras.fxml", "/css/regrasStyle.css");		
	}
	
	@FXML
	private void fechar() {
		exit();
	}

}
