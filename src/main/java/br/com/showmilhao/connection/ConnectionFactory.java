package br.com.showmilhao.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.showmilhao.util.LogUtil;

public class ConnectionFactory {
	
	private static final String URL_CONNECTION = "jdbc:sqlite:src/main/resources/data/show_milhao.db";
	
	private static Connection connection;
	
	private ConnectionFactory() { }
	
	static {
		conectar();
	}

	private static void conectar() {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection(URL_CONNECTION);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			LogUtil.getLogger(ConnectionFactory.class).error(e.getCause().toString());
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}

}
