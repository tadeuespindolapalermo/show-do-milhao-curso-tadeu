package br.com.showmilhao.application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import br.com.showmilhao.util.LogUtil;
import javazoom.jl.player.Player;

public class ContinuousReprodution extends Thread {
	
	private String fileMusic;
	private boolean loop;
	
	public ContinuousReprodution(String fileMusic, boolean loop) {
		this.fileMusic = fileMusic;
		this.loop = loop;
	}
	
	@Override
	public void run() {
		try {
			do {
				new Player(new BufferedInputStream(new FileInputStream(fileMusic))).play();
			} while (loop);
		} catch (Exception e) {
			LogUtil.getLogger(ContinuousReprodution.class).error(e.getCause().toString());
		}
		
	}

}
