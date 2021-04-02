package com.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.repository.lancamentoQuery.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
	

}
