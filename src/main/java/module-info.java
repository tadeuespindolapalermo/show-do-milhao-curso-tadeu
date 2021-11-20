module showMilhao {
	
	opens br.com.showmilhao.controller;
	
	exports br.com.showmilhao.application;
	
	requires transitive javafx.controls;
	requires log4j;
	requires jlayer;
	requires java.sql;
	requires java.desktop;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	requires javafx.base;
}