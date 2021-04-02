package com.algamoney.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {
	
	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDE;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoATE;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataVencimentoDE() {
		return dataVencimentoDE;
	}
	public void setDataVencimentoDE(LocalDate dataVencimentoDE) {
		this.dataVencimentoDE = dataVencimentoDE;
	}
	public LocalDate getDataVencimentoATE() {
		return dataVencimentoATE;
	}
	public void setDataVencimentoATE(LocalDate dataVencimentoATE) {
		this.dataVencimentoATE = dataVencimentoATE;
	}
	
	

}
