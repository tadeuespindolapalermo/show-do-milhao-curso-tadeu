package br.com.showmilhao.service;

import java.util.List;

import br.com.showmilhao.dao.JogadorDAO;
import br.com.showmilhao.model.Jogador;

public class JogadorService {
	
	private JogadorDAO dao;
	
	public JogadorService() {
		dao = new JogadorDAO();
	}
	
	public boolean adicionar(Jogador jogador) {		
		return dao.adicionar(jogador);
	}
	
	public void atualizar(Jogador jogador) {
		dao.atualizar(jogador);
	}
	
	public List<Jogador> listar() {
		return dao.listar();
	}
	
	public List<Jogador> listarRanking() {
		return dao.listarRanking();
	}
	
	public void zerarRanking() {
		dao.zerarRanking();
	}
	

}
