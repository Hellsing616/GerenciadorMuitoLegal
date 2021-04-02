package com.algamoney.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.LancamentoRepository;
import com.algamoney.api.repository.PessoaRepository;
import com.algamoney.api.service.exception.PessoaInexistenteOuInativaexception;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvar(@Valid Lancamento lancamnento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamnento.getPessoa().getCodigo());
		if (pessoa == null ||pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaexception();
		}
		
		return lancamentoRepository.save(lancamnento);
	}

}
