package com.company;

import java.util.ArrayList;

public enum DefaultProperties
{ 											
	METODOLOGIA(Colour.BROWN, new ArrayList<Integer>() {{add(2);add(10);add(30);add(90);add(160);add(250);}}, "Metodologia del Aprendizaje"),
	FORMACION(Colour.BROWN, new ArrayList<Integer>() {{add(2);add(10);add(30);add(90);add(160);add(250);}}, "Formacion General"),
	QUIMICA(Colour.BROWN, new ArrayList<Integer>() {{add(4);add(20);add(60);add(180);add(320);add(450);}}, "Quimica"),	
	INTRO(Colour.LIGHT_BLUE, new ArrayList<Integer>() {{add(6);add(30);add(90);add(270);add(400);add(550);}}, "Introduccion a la Informatica"),
	XML(Colour.LIGHT_BLUE, new ArrayList<Integer>(){{add(6);add(30);add(90);add(270);add(400);add(550);}}, "Diseno y Procesamiento de Doc XML"),
	ING_SOFT(Colour.LIGHT_BLUE, new ArrayList<Integer>() {{add(8);add(40);add(100);add(300);add(450);add(600);}}, "Ingenieria de Software"),	
	ALGEBRA(Colour.PINK, new ArrayList<Integer>() {{add(10);add(50);add(150);add(450);add(625);add(750);}}, "Algebra"),
	ANALISIS_I(Colour.PINK, new ArrayList<Integer>() {{add(10);add(50);add(150);add(450);add(625);add(750);}}, "Analisis Matematico I"),
	SIST_REP(Colour.PINK, new ArrayList<Integer>() {{add(12);add(60);add(80);add(500);add(700);add(900);}}, "Sistemas de Representacion"),
	ANALISIS_II(Colour.ORANGE, new ArrayList<Integer>() {{add(14);add(70);add(200);add(550);add(750);add(950);}}, "Analisis Matematico II"),
	DISCRETA(Colour.ORANGE, new ArrayList<Integer>() {{add(14);add(70);add(200);add(550);add(750);add(950);}}, "Matematica Discreta"),
	FISICA_I(Colour.ORANGE, new ArrayList<Integer>() {{add(16);add(80);add(220);add(600);add(800);add(1000);}}, "Fisica I"),	
	FISICA_II(Colour.RED, new ArrayList<Integer>() {{add(18);add(90);add(250);add(700);add(875);add(1050);}}, "Fisica II"),
	PROBA(Colour.RED, new ArrayList<Integer>() {{add(18);add(90);add(250);add(700);add(875);add(1050);}}, "Probabilidad y Estadistica"),
	POO(Colour.RED, new ArrayList<Integer>() {{add(20);add(100);add(300);add(750);add(925);add(1100);}}, "Programacion Orientada a Objetos"),	
	METODOS(Colour.YELLOW, new ArrayList<Integer>() {{add(22);add(110);add(330);add(800);add(975);add(1150);}}, "Metodos Numericos"),
	BASE_DATOS(Colour.YELLOW, new ArrayList<Integer>() {{add(22);add(110);add(330);add(800);add(975);add(1150);}}, "Base de Datos I"),
	PROTOCOLOS(Colour.YELLOW, new ArrayList<Integer>() {{add(24);add(120);add(360);add(850);add(1025);add(1200);}}, "Protocolos de Comunicacion"),	
	PI(Colour.GREEN, new ArrayList<Integer>() {{add(26);add(130);add(390);add(900);add(1100);add(1275);}}, "Programacion Imperativa"),
	EDA(Colour.GREEN, new ArrayList<Integer>() {{add(26);add(130);add(390);add(900);add(1100);add(1275);}}, "Estructura de Datos y Algoritmos"),
	ARQUI(Colour.GREEN, new ArrayList<Integer>() {{add(28);add(150);add(450);add(1000);add(1200);add(1400);}}, "Arquitecturas de Computadoras"),
	SIST_OP(Colour.BLUE, new ArrayList<Integer>() {{add(35);add(175);add(500);add(1100);add(1300);add(1500);}}, "Sistemas Operativos"),
	FISICA_III(Colour.BLUE, new ArrayList<Integer>() {{add(35);add(175);add(500);add(1100);add(1300);add(1500);}}, "Fisica III"),
	LOGICA(Colour.BLUE, new ArrayList<Integer>() {{add(50);add(200);add(600);add(1400);add(1700);add(2000);}}, "Logica Computacional");	
	
    private String name;
    private ArrayList<Integer> prices;
    private Colour color;
    
    private DefaultProperties(Colour color, ArrayList<Integer> prices, String name)
    {
    	this.name = name;
    	this.prices = prices;
    	this.color = color;
    }
		
    public Property getProperty()
    {
    	return new Property(prices, name, color);
    }

}