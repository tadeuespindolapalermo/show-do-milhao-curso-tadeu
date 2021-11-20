package br.com.showmilhao.controller;

import static br.com.showmilhao.util.ControllerUtil.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXTextField;

import br.com.showmilhao.model.Jogador;
import br.com.showmilhao.service.JogadorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControllerLayoutTelaNome implements Initializable {
	
	private static final String PARTICIPANTE_VOICE = "src/main/resources/songs/vamos-conhecer-agora-o-nosso-participante-voice.mp3";
	private static final String START_GAME_VOICE = "src/main/resources/songs/vai-comecar-o-show-do-milhao-voice.mp3";
	
	private JogadorService service;
	
	@FXML
	private JFXTextField nome;
	
	public ControllerLayoutTelaNome() {
		service = new JogadorService();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		changeVoice(PARTICIPANTE_VOICE);	
	}
	
	@FXML
	private void voltar(ActionEvent event) throws IOException {
		changeLayout(getClass(), "/view/LayoutTelaInicial.fxml", "/css/buttonStyle.css");		
	}
	
	@FXML
	private void startGame(ActionEvent event) throws IOException, InterruptedException {
		boolean jogadorSalvo = false;
		if (!nome.getText().isEmpty()) {
			jogadorSalvo = salvarJogador(nome.getText());
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Informe o nome do Jogador!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
		}
		if (jogadorSalvo) {
			changeVoice(START_GAME_VOICE);
			Thread.sleep(2000);
			changeLayout(getClass(), "/view/LayoutTelaPrincipal.fxml", "/css/principalStyle.css");
		}			
	}
	
	@FXML
	private void fechar() {
		exit();
	}
	
	private boolean salvarJogador(String nome) {
		Jogador jogador = new Jogador(nome);
		return service.adicionar(jogador);
	}

}
