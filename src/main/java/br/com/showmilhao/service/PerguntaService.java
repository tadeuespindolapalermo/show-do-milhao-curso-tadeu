package br.com.showmilhao.service;

import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.com.showmilhao.dao.PerguntaDAO;
import br.com.showmilhao.model.Pergunta;

public class PerguntaService {
	
	private PerguntaDAO dao;
	
	private static final String OK = "Processo concluído!";
	private static final int MESSAGE_TYPE = JOptionPane.INFORMATION_MESSAGE;
	
	public PerguntaService() {
		dao = new PerguntaDAO();
	}
	
	public void adicionar(Pergunta pergunta) {
		Pergunta perguntaRetornada = dao.adicionar(pergunta);
		if (Objects.nonNull(perguntaRetornada)) {
			JOptionPane.showMessageDialog(new JFrame(), "Pergunta adicionada com sucesso!", OK, MESSAGE_TYPE);
		}
	}
	
	public void atualizar(Pergunta pergunta) {
		dao.atualizar(pergunta);
		JOptionPane.showMessageDialog(new JFrame(), "Alterações realizadas com sucesso!", OK, MESSAGE_TYPE);
	}
	
	public void remover(Integer idPergunta) {
		boolean perguntaRemovida = dao.remover(idPergunta);
		if (perguntaRemovida)
			JOptionPane.showMessageDialog(new JFrame(), "Pergunta removida com sucesso!", OK, MESSAGE_TYPE);
	}
	
	public List<Pergunta> listar() {
		return dao.listar();
	}
	
	public List<Pergunta> listar(String nivel) {
		return dao.listar(nivel);
	}
	
	public List<Pergunta> listar(String idsPerguntasFeitas, String nivel) {
		return dao.listar(idsPerguntasFeitas, nivel);
	}

}
