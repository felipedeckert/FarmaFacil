package com.example.farmafacil_v1_3_2.model;

import java.util.ArrayList;

public class Receita {
	
	private long id;
	private String data;
	//private ArrayList <Integer> remedios;
	private String nomeMedico;
	private String crm;
	
	//contrutor
	public Receita(String data, String nomeMedico, String crm){
		this.data = data;
		this.nomeMedico = nomeMedico;
		this.crm = crm;
	}
	
	public Receita() {
		
	}

	//setters
	public void setData(String data){
		this.data = data;
	}
	
	public void setNomeMedico (String nomeMedico){
		this.nomeMedico = nomeMedico;
	}
	public void setCrm (String crm){
		this.crm = crm;
	}
	
	//getters
	public String getData (){
		return this.data;
	}
	
	public String getNomeMedico (){
		return this.nomeMedico;
	}
	public String getCrm (){
		return this.crm;
	}

	public void setId(long id) {
		this.id = id;
	}
}
