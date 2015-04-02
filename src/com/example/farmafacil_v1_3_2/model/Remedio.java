package com.example.farmafacil_v1_3_2.model;

public class Remedio {
	private long id;
	private String nome;
	private String dosagem;
	private String uso;
	
	public Remedio(){
		
	}
	public Remedio(String nome, String dosagem, String uso){
		this.nome = nome;
		this.dosagem = dosagem;
		this.uso = uso;
	}
	
	public void setId (long id){
		this.id = id;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	public void setDosagem(String dosagem){
		this.dosagem = dosagem;
	}
	public void setUso(String uso){
		this.uso = uso;
	}
	
	public String getNome (){
		return this.nome;
	}
	public String getDosagem (){
		return this.dosagem;
	}
	public String getUso (){
		return this.uso;
	}
	public long getId() {
		return this.id;
	}
}
