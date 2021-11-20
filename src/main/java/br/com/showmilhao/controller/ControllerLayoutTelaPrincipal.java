package br.com.showmilhao.controller;

import static br.com.showmilhao.util.ControllerUtil.changeLayout;
import static br.com.showmilhao.util.ControllerUtil.changeVoice;
import static br.com.showmilhao.util.ControllerUtil.exit;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;

import br.com.showmilhao.model.Jogador;
import br.com.showmilhao.model.Pergunta;
import br.com.showmilhao.service.JogadorService;
import br.com.showmilhao.service.PerguntaService;
import br.com.showmilhao.util.LogUtil;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ControllerLayoutTelaPrincipal implements Initializable {
	
	private JogadorService jogadorService;
	private PerguntaService perguntaService;
	private List<Integer> idsPerguntasFeitas = new ArrayList<>();
	private int contadorPerguntasRespondidas = 2;
	private int pontuacaoErro;
	private int pontuacaoAcerto;
	private int pontuacaoParar;
	private int pulos = 3;
	private String respostaBtn1;
	private String respostaBtn2;
	private String respostaBtn3;
	private String respostaBtn4;
	
	private static final String NIVEL_FACIL = "facil";
	private static final String NIVEL_NORMAL = "normal";
	private static final String NIVEL_DIFICIL = "dificil";
	
	@FXML
	private Label labelNomeJogador;
	
	@FXML
	private Label labelNumeroRodada;
	
	@FXML
	private Label labelNumeroPergunta;
	
	@FXML
	private Label labelNivel;
	
	@FXML
	private Label labelErrar;
	
	@FXML
	private Label labelParar;
	
	@FXML
	private Label labelAcertar;
	
	@FXML
	private Button btnEnunciado;
	
	@FXML
	private Button btnAlternativa1;
	
	@FXML
	private Button btnAlternativa2;
	
	@FXML
	private Button btnAlternativa3;
	
	@FXML
	private Button btnAlternativa4;
	
	@FXML
	private JFXButton btnParar;
	
	@FXML
	private JFXButton btnPulo;
	
	@FXML
	private Label lblPulos;	
	
	@FXML
	private Label lblUniversitario1;
	
	@FXML
	private Label lblUniversitario2;
	
	@FXML
	private Label lblUniversitario3;
	
	@FXML
	private Button btnUniversitario1;
	
	@FXML
	private Button btnUniversitario2;
	
	@FXML
	private Button btnUniversitario3;
	
	@FXML
	private JFXButton btnUniversitarios;
	
	@FXML
	private JFXButton btnCartas;
	
	@FXML
	private Button btnCarta1;
	
	@FXML
	private Button btnCarta2;
	
	@FXML
	private Button btnCarta3;
	
	@FXML
	private Button btnCarta4;
	
	@FXML
	private void fechar() {
		exit();
	}
	
	public ControllerLayoutTelaPrincipal() {
		jogadorService = new JogadorService();
		perguntaService = new PerguntaService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initLabels();
		changeVoice("src/main/resources/songs/1-mil-reais-voice.mp3");
		processarPerguntas(NIVEL_FACIL);
		aplicarEventoCliqueBotaoParar();
		aplicarEventoCliqueBotaoPular();
		aplicarEventoCliqueBotaoUniversitarios();
		aplicarEventoCliqueBotaoCartas();
	}
	
	private Jogador getJogador() {
		return jogadorService.listar().stream().reduce((jogador1, jogador2) -> jogador2).orElseThrow();		
	}
	
	private void initLabels() {
		labelNomeJogador.setText(getJogador().getNome());
		labelNumeroRodada.setText("1");
		labelNumeroPergunta.setText("1");
		labelNivel.setText("FÁCIL");
		labelAcertar.setText("1.000,00");
		labelErrar.setText("0,00");
		labelParar.setText("0,00");
	}
	
	private String getIdsPerguntasFeitasFormatado() {
		String ids = "";
		if (!idsPerguntasFeitas.isEmpty()) {
			ids += "(";
			for (Integer id : idsPerguntasFeitas) {
				ids += id.toString() + ",";
			}
			ids = ids.substring(0, ids.length() - 1);
			ids += ")";
		}
		return ids;		
	}
	
	private void processarPerguntas(String nivel) {
		
		setVisibleUniversitarios(Boolean.FALSE);
		setVisibleAlternativas(Boolean.TRUE);
		
		List<Pergunta> perguntas = perguntaService.listar(getIdsPerguntasFeitasFormatado(), nivel);
		
		perguntas.forEach(p -> {
			
			idsPerguntasFeitas.add(p.getId());
			
			btnEnunciado.setText(p.getEnunciado());
			
			List<Button> botoesRandomizados = Arrays.asList(btnAlternativa1, btnAlternativa2, btnAlternativa3, btnAlternativa4);
			
			Collections.shuffle(botoesRandomizados);
			
			botoesRandomizados.get(0).setText(p.getAlternativa1());
			botoesRandomizados.get(1).setText(p.getAlternativa2());
			botoesRandomizados.get(2).setText(p.getAlternativa3());
			botoesRandomizados.get(3).setText(p.getResposta());
			
			if (btnAlternativa1.getText().equals(p.getResposta())) {
				
				setFlagRespostaAuxilio("respostaBtn1");
				
				btnAlternativa1.setOnMouseClicked(evento -> {
					tratarAlternativaCorreta(btnAlternativa1);
					contadorPerguntasRespondidas ++;
				});
				
				aplicarEventoAlternativaErrada(Arrays.asList(btnAlternativa2, btnAlternativa3, btnAlternativa4), btnAlternativa1);
				
			} else if (btnAlternativa2.getText().equals(p.getResposta())) {
				
				setFlagRespostaAuxilio("respostaBtn2");
				
				btnAlternativa2.setOnMouseClicked(evento -> {
					tratarAlternativaCorreta(btnAlternativa2);
					contadorPerguntasRespondidas ++;
				});
				
				aplicarEventoAlternativaErrada(Arrays.asList(btnAlternativa1, btnAlternativa3, btnAlternativa4), btnAlternativa2);
				
			} else if (btnAlternativa3.getText().equals(p.getResposta())) {
				
				setFlagRespostaAuxilio("respostaBtn3");
				
				btnAlternativa3.setOnMouseClicked(evento -> {
					tratarAlternativaCorreta(btnAlternativa3);
					contadorPerguntasRespondidas ++;
				});
				
				aplicarEventoAlternativaErrada(Arrays.asList(btnAlternativa1, btnAlternativa2, btnAlternativa4), btnAlternativa3);
				
			} else if (btnAlternativa4.getText().equals(p.getResposta())) {
				
				setFlagRespostaAuxilio("respostaBtn4");
				
				btnAlternativa4.setOnMouseClicked(evento -> {
					tratarAlternativaCorreta(btnAlternativa4);
					contadorPerguntasRespondidas ++;
				});
				
				aplicarEventoAlternativaErrada(Arrays.asList(btnAlternativa1, btnAlternativa2, btnAlternativa3), btnAlternativa4);
			}
		});
	}
	
	private void setFlagRespostaAuxilio(String respostaCerta) {
		if (respostaCerta.equals("respostaBtn1")) {
			respostaBtn1 = "certa";
			respostaBtn2 = "errada";
			respostaBtn3 = "errada";
			respostaBtn4 = "errada";			
		}
		if (respostaCerta.equals("respostaBtn2")) {
			respostaBtn2 = "certa";
			respostaBtn1 = "errada";
			respostaBtn3 = "errada";
			respostaBtn4 = "errada";
					
		}
		if (respostaCerta.equals("respostaBtn3")) {
			respostaBtn3 = "certa";
			respostaBtn1 = "errada";
			respostaBtn2 = "errada";
			respostaBtn4 = "errada";
		}
		if (respostaCerta.equals("respostaBtn4")) {
			respostaBtn4 = "certa";
			respostaBtn1 = "errada";
			respostaBtn2 = "errada";
			respostaBtn3 = "errada";
		}
	}
	
	private void aplicarEventoAlternativaErrada(List<Button> buttons, Button buttonAlternativaCorreta) {
		buttons.forEach(b -> b.setOnMouseClicked(evento -> tratarAlternativaErrada(buttonAlternativaCorreta)));
	}
	
	private void tratarAlternativaCorreta(Button button) {		
		changeVoice("src/main/resources/songs/esta-certo-disso-voice.mp3");
		int confirma = JOptionPane.showConfirmDialog(null, "Você está certo disso?", "Atenção", JOptionPane.YES_NO_OPTION);		
		if (confirma == JOptionPane.YES_OPTION) {			
			changeVoice("src/main/resources/songs/qual-e-a-resposta-certa-voice.mp3");
			sleep(2000);			
			makeTransitionAlternativaCorreta(button);
			changeVoice("src/main/resources/songs/certa-resposta.mp3");
			sleep(500);
			
			// PRIMEIRA RODADA DE PERGUNTAS => NIVEL FACIL
			if (isNivelFacil()) {
				if (contadorPerguntasRespondidas == 2) {
					atualizarLabels("2", "1", "2.000,00", "500,00", "1.000,00", "FACIL");
					changeVoice("src/main/resources/songs/2-mil-reais-voice.mp3");				
					atualizarPontuacao(1000, 500, 1000);
				}
				
				if (contadorPerguntasRespondidas == 3) {
					atualizarLabels("3", "1", "3.000,00", "1.000,00", "2.000,00", "FACIL");
					changeVoice("src/main/resources/songs/3-mil-reais-voice.mp3");	
					atualizarPontuacao(1000, 1000, 2000);
				}
				
				if (contadorPerguntasRespondidas == 4) {
					atualizarLabels("4", "1", "4.000,00", "1.500,00", "3.000,00", "FACIL");
					changeVoice("src/main/resources/songs/4-mil-reais-voice.mp3");
					atualizarPontuacao(1000, 1500, 3000);
				}
				
				if (contadorPerguntasRespondidas == 5) {
					atualizarLabels("5", "1", "5.000,00", "2.000,00", "4.000,00", "FACIL");
					changeVoice("src/main/resources/songs/5-mil-reais-voice.mp3");
					atualizarPontuacao(1000, 2000, 4000);	
				}			
			}
			
			// SEGUNDA RODADA DE PERGUNTAS => NIVEL NORMAL
			if (isNivelNormal()) {
				if (contadorPerguntasRespondidas == 6) {
					atualizarLabels("6", "2", "10.000,00", "2.500,00", "5.000,00", "NORMAL");
					changeVoice("src/main/resources/songs/10-mil-reais-voice.mp3");
					atualizarPontuacao(1000, 2500, 5000);	
				}
				
				if (contadorPerguntasRespondidas == 7) {
					atualizarLabels("7", "2", "20.000,00", "5.000,00", "10.000,00", "NORMAL");
					changeVoice("src/main/resources/songs/20-mil-reais-voice.mp3");	
					atualizarPontuacao(5000, 5000, 10000);	
				}
				
				if (contadorPerguntasRespondidas == 8) {
					atualizarLabels("8", "2", "30.000,00", "10.000,00", "20.000,00", "NORMAL");
					changeVoice("src/main/resources/songs/30-mil-reais-voice.mp3");
					atualizarPontuacao(10000, 10000, 20000);	
				}
				
				if (contadorPerguntasRespondidas == 9) {
					atualizarLabels("9", "2", "40.000,00", "15.000,00", "30.000,00", "NORMAL");
					changeVoice("src/main/resources/songs/40-mil-reais-voice.mp3");
					atualizarPontuacao(10000, 15000, 30000);	
				}	
				
				if (contadorPerguntasRespondidas == 10) {
					atualizarLabels("10", "2", "50.000,00", "20.000,00", "40.000,00", "NORMAL");
					changeVoice("src/main/resources/songs/50-mil-reais-voice.mp3");
					atualizarPontuacao(10000, 20000, 40000);	
				}	
			}
			
			// TERCEIRA RODADA DE PERGUNTAS => NIVEL DIFICIL
			if (isNivelDificil()) {
				if (contadorPerguntasRespondidas == 11) {
					atualizarLabels("11", "3", "100.000,00", "25.000,00", "50.000,00", "DIFICIL");
					changeVoice("src/main/resources/songs/100-mil-reais-voice.mp3");
					atualizarPontuacao(10000, 25000, 50000);		
				}
				
				if (contadorPerguntasRespondidas == 12) {
					atualizarLabels("12", "3", "200.000,00", "50.000,00", "100.000,00", "DIFICIL");
					changeVoice("src/main/resources/songs/200-mil-reais-voice.mp3");	
					atualizarPontuacao(50000, 50000, 100000);	
				}
				
				if (contadorPerguntasRespondidas == 13) {
					atualizarLabels("13", "3", "300.000,00", "100.000,00", "200.000,00", "DIFICIL");
					changeVoice("src/main/resources/songs/300-mil-reais-voice.mp3");
					atualizarPontuacao(100000, 100000, 200000);	
				}
				
				if (contadorPerguntasRespondidas == 14) {
					atualizarLabels("14", "3", "400.000,00", "150.000,00", "300.000,00", "DIFICIL");
					changeVoice("src/main/resources/songs/400-mil-reais-voice.mp3");
					atualizarPontuacao(100000, 150000, 300000);	
				}	
				
				if (contadorPerguntasRespondidas == 15) {
					atualizarLabels("15", "3", "500.000,00", "200.000,00", "400.000,00", "DIFICIL");
					changeVoice("src/main/resources/songs/500-mil-reais-voice.mp3");
					atualizarPontuacao(100000, 200000, 400000);	
				}	
				
				if (contadorPerguntasRespondidas == 16) {
					atualizarLabels("16", "3", "1.000.000,00", "0,00", "500.000,00", "DIFICIL");
					changeVoice("src/main/resources/songs/1-milhao-reais-voice.mp3");	
					atualizarPontuacao(600000, 0, 500000);	
				}	
				
				if (contadorPerguntasRespondidas == 17) {
					atualizarPontuacaoJogador(pontuacaoAcerto);
					changeLayout(getClass(), "/view/LayoutTelaInicial.fxml", "/css/buttonStyle.css");
				}	
			}
		}
	}
	
	private void tratarAlternativaErrada(Button buttonAlternativaCorreta) {
		contadorPerguntasRespondidas = -1;
		changeVoice("src/main/resources/songs/esta-certo-disso-voice.mp3");
		int confirma = JOptionPane.showConfirmDialog(null, "Você está certo disso?", "Atenção", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			atualizarPontuacaoJogador(pontuacaoErro);
			changeVoice("src/main/resources/songs/qual-e-a-resposta-certa-voice.mp3");
			sleep(2000);
			makeTransitionAlternativaErrada(buttonAlternativaCorreta);
		}
	}	
	
	private void atualizarPontuacaoJogador(int pontuacao) {
		Jogador jogador = getJogador();
		jogador.setPontuacao(pontuacao);
		jogadorService.atualizar(jogador);
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			LogUtil.getLogger(ControllerLayoutTelaPrincipal.class).error(e.getCause().toString());		
			Thread.currentThread().interrupt();
		}
	}
	
	private FadeTransition getTransitionDefault() {
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(250));
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setCycleCount(8);
		return fadeTransition;
	}
	
	private void makeTransitionAlternativaErrada(Button button) {
		FadeTransition fadeTransition = getTransitionDefault();
		fadeTransition.setNode(button);
		fadeTransition.setOnFinished(f -> {
			changeVoice("src/main/resources/songs/que-pena-voce-errou-voice.mp3");
			sleep(2000);
			changeLayout(getClass(), "/view/LayoutTelaInicial.fxml", "/css/buttonStyle.css");
		});
		fadeTransition.play();
	}
	
	private void makeTransitionAlternativaCorreta(Button button) {
		FadeTransition fadeTransition = getTransitionDefault();
		fadeTransition.setNode(button);
		fadeTransition.setOnFinished(f -> processarProximaPergunta(contadorPerguntasRespondidas));
		fadeTransition.play();
	}
	
	private void processarProximaPergunta(int perguntaContador) {
		int sleep = 1000;
		perguntaContador = perguntaContador - 1;
		if (perguntaContador >= 1 && perguntaContador <= 5) {
			sleep(sleep);
			processarPerguntas(NIVEL_FACIL);
		} else if (perguntaContador >= 6 && perguntaContador <= 10) {
			if (perguntaContador == 6) {
				sleep = 3000;
			}
			sleep(sleep);
			processarPerguntas(NIVEL_NORMAL);
		} else if (perguntaContador >= 11 && perguntaContador <= 17) {
			if (perguntaContador == 16 || perguntaContador == 11) {
				sleep = 5000;
			}
			sleep(sleep);
			processarPerguntas(NIVEL_DIFICIL);
		}
		sleep(sleep);
	}
	
	private boolean isNivelFacil() {
		return contadorPerguntasRespondidas >= 1 && contadorPerguntasRespondidas <= 5;
	}
	
	private boolean isNivelNormal() {
		return contadorPerguntasRespondidas >= 6 && contadorPerguntasRespondidas <= 10;
	}
	
	private boolean isNivelDificil() {
		return contadorPerguntasRespondidas >= 11 && contadorPerguntasRespondidas <= 17;
	}
	
	private void atualizarLabels(String... informacoes) {
		labelNumeroPergunta.setText(informacoes[0]);
		labelNumeroRodada.setText(informacoes[1]);
		labelAcertar.setText(informacoes[2]);
		labelErrar.setText(informacoes[3]);
		labelParar.setText(informacoes[4]);
		labelNivel.setText(informacoes[5]);
	}
	
	private void atualizarPontuacao(int... informacoes) {
		pontuacaoAcerto += informacoes[0];
		pontuacaoErro = informacoes[1];
		pontuacaoParar = informacoes[2];		
	}
	
	private void aplicarEventoCliqueBotaoParar() {
		btnParar.setOnMouseClicked(evento -> parar());
	}
	
	private void aplicarEventoCliqueBotaoPular() {
		btnPulo.setOnMouseClicked(evento -> pular());
	}
	
	private void parar() {
		changeVoice("src/main/resources/songs/vai-parar-esta-certo-disso-voice.mp3");
		int confirma = JOptionPane.showConfirmDialog(new JFrame(), "Você realmente deseja parar?", "Atenção!", JOptionPane.YES_NO_OPTION);	
		if (confirma == JOptionPane.YES_OPTION) {
			if (contadorPerguntasRespondidas == 16) {
				changeVoice("src/main/resources/songs/parou-500-mil-reais-voice.mp3");
			} else {
				changeVoice("src/main/resources/songs/ok-parou-voice.mp3");
			}
			JOptionPane.showMessageDialog(new JFrame(), "Você parou!");
			atualizarPontuacaoJogador(pontuacaoParar);
			changeLayout(getClass(), "/view/LayoutTelaInicial.fxml", "/css/buttonStyle.css");
		}
	}
	
	private void pular() {
		changeVoice("src/main/resources/songs/voce-tem-certeza-voice.mp3");
		int confirma = JOptionPane.showConfirmDialog(new JFrame(), "Você realmente deseja pular?", "Atenção!", JOptionPane.YES_NO_OPTION);	
		if (confirma == JOptionPane.YES_OPTION) {
			pulos -= 1;
			lblPulos.setText(String.valueOf(pulos));
			if (pulos == 0) {
				btnPulo.setVisible(Boolean.FALSE);
			}
			JOptionPane.showMessageDialog(new JFrame(), "Próxima pergunta...!");
			if (isNivelFacil()) {
				processarPerguntas(NIVEL_FACIL);
			}
			if (isNivelNormal()) {
				processarPerguntas(NIVEL_NORMAL);			
			}
			if (isNivelDificil()) {
				processarPerguntas(NIVEL_DIFICIL);
			}
		}
	}
	
	private void setVisibleUniversitarios(boolean visible) {
		lblUniversitario1.setVisible(visible);
		lblUniversitario2.setVisible(visible);
		lblUniversitario3.setVisible(visible);
		btnUniversitario1.setVisible(visible);
		btnUniversitario2.setVisible(visible);
		btnUniversitario3.setVisible(visible);
	}
	
	private void setNameUniversitarios() {
		lblUniversitario1.setText("RICARDO");
		lblUniversitario2.setText("JOSÉ");
		lblUniversitario3.setText("ANA PAULA");
	}
	
	private void aplicarEventoCliqueBotaoUniversitarios() {
		btnUniversitarios.setOnMouseClicked(evento -> processarAjudaUniversitarios());
	}
	
	public void processarAjudaUniversitarios() {	
		
		setNameUniversitarios();
		setVisibleUniversitarios(Boolean.TRUE);		
		
		String alternativaCorreta = "";
		
		if (respostaBtn1.equals("certa")) {
			alternativaCorreta = btnAlternativa1.getText();
		} else if (respostaBtn2.equals("certa")) {
			alternativaCorreta = btnAlternativa2.getText();
		} else if (respostaBtn3.equals("certa")) {
			alternativaCorreta = btnAlternativa3.getText();
		} else if (respostaBtn4.equals("certa")) {
			alternativaCorreta = btnAlternativa4.getText();
		}
		
		List<String> alternativas = new ArrayList<>();
		alternativas.add(btnAlternativa1.getText());
		alternativas.add(btnAlternativa2.getText());
		alternativas.add(btnAlternativa3.getText());
		alternativas.add(btnAlternativa4.getText());
		Collections.shuffle(alternativas);
		
		List<Button> universitarios = new ArrayList<>();
		universitarios.add(btnUniversitario1);
		universitarios.add(btnUniversitario2);
		universitarios.add(btnUniversitario3);
		Collections.shuffle(universitarios);
		
		universitarios.get(0).setText(alternativaCorreta);
		universitarios.get(1).setText(alternativas.get(1));
		universitarios.get(2).setText(alternativas.get(2));
		
		btnUniversitarios.setVisible(Boolean.FALSE);
	}
	
	private void aplicarEventoCliqueBotaoCartas() {
		btnCartas.setOnMouseClicked(evento -> processarAjudaCartas());
	}
	
	private void processarAjudaCartas() {		
		changeVoice("src/main/resources/songs/tire-a-carta-do-baralho-voice.mp3");
		setVisibleCartas(Boolean.TRUE);
		
		List<Integer> opcoesEliminacaoRespostasErradas = Arrays.asList(0, 1, 2, 3);
		Collections.shuffle(opcoesEliminacaoRespostasErradas);
		
		btnCarta1.setText(opcoesEliminacaoRespostasErradas.get(0).toString());
		btnCarta2.setText(opcoesEliminacaoRespostasErradas.get(1).toString());
		btnCarta3.setText(opcoesEliminacaoRespostasErradas.get(2).toString());
		btnCarta4.setText(opcoesEliminacaoRespostasErradas.get(3).toString());
		
		btnCarta1.setOnMouseClicked(evento -> eliminarAlternativasErradas(btnCarta1));
		btnCarta2.setOnMouseClicked(evento -> eliminarAlternativasErradas(btnCarta2));
		btnCarta3.setOnMouseClicked(evento -> eliminarAlternativasErradas(btnCarta3));
		btnCarta4.setOnMouseClicked(evento -> eliminarAlternativasErradas(btnCarta4));
		
		btnCartas.setVisible(Boolean.FALSE);
	}
	
	private void setVisibleCartas(boolean visible) {
		btnCarta1.setVisible(visible);
		btnCarta2.setVisible(visible);
		btnCarta3.setVisible(visible);
		btnCarta4.setVisible(visible);
	}
	
	private void eliminarAlternativasErradas(Button button) {
		changeVoice("src/main/resources/songs/o-que-vao-dizer-as-cartas-voice.mp3");
		sleep(2000);
		
		if (button.getText().equals("0")) {
			JOptionPane.showMessageDialog(new JFrame(), "QUE PENA! NENHUMA ALTERNATIVA ERRADA SERÁ ELIMINADA!");
		} else if (button.getText().equals("1")) {
			JOptionPane.showMessageDialog(new JFrame(), "APENAS 1 ALTERNATIVA ERRADA SERÁ ELIMINADA!");
			getAlternativaRespostaBtn(getRespostaBtnErradas().get(new Random().nextInt(3))).setVisible(Boolean.FALSE);
		} else if (button.getText().equals("2")) {
			JOptionPane.showMessageDialog(new JFrame(), "APENAS 2 ALTERNATIVAS ERRADAS SERÃO ELIMINADAS!");
			getAlternativaRespostaBtn(getRespostaBtnErradas().get(0)).setVisible(Boolean.FALSE);
			getAlternativaRespostaBtn(getRespostaBtnErradas().get(1)).setVisible(Boolean.FALSE);
		} else if (button.getText().equals("3")) {
			JOptionPane.showMessageDialog(new JFrame(), "OBA! TODAS AS ALTERNATIVAS ERRADAS SERÃO ELIMINADAS!");
			setVisibleAlternativas(Boolean.FALSE);
			getRespostaBtn().entrySet().forEach(respostaBtn -> {
				if (respostaBtn.getValue().equals("certa")) 
					getAlternativaRespostaBtn(respostaBtn.getKey()).setVisible(Boolean.TRUE);
			});
		}
		
		setVisibleCartas(Boolean.FALSE);
	}
	
	private Button getAlternativaRespostaBtn(String respostaBtn) {
		Button button = null;
		if (respostaBtn.equals("respostaBtn1")) {
			button = btnAlternativa1;
		}
		if (respostaBtn.equals("respostaBtn2")) {
			button = btnAlternativa2;
		}
		if (respostaBtn.equals("respostaBtn3")) {
			button = btnAlternativa3;
		}
		if (respostaBtn.equals("respostaBtn4")) {
			button = btnAlternativa4;
		}
		return button;
	}
	
	private Map<String, String> getRespostaBtn() {
		Map<String, String> respostaBtnMap = new HashMap<>();
		respostaBtnMap.put("respostaBtn1", respostaBtn1);
		respostaBtnMap.put("respostaBtn2", respostaBtn2);
		respostaBtnMap.put("respostaBtn3", respostaBtn3);
		respostaBtnMap.put("respostaBtn4", respostaBtn4);
		return respostaBtnMap;
	}
	
	private List<String> getRespostaBtnErradas() {
		return getRespostaBtn().entrySet().stream().filter(value -> value.getValue().equals("errada")).map(key -> key.getKey()).collect(Collectors.toList());
	}
	
	private void setVisibleAlternativas(boolean visible) {
		Arrays.asList(btnAlternativa1, btnAlternativa2, btnAlternativa3, btnAlternativa4).forEach(btn -> btn.setVisible(visible));
	}
	
}
