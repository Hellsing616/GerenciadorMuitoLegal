package com.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.api.event.RecursoCriadoEvento;
import com.algamoney.api.exceotionhandler.AlgaMoneyExcepitionHandler.Erro;
import com.algamoney.api.model.Lancamento;
import com.algamoney.api.repository.LancamentoRepository;
import com.algamoney.api.repository.filter.LancamentoFilter;
import com.algamoney.api.service.LancamentoService;
import com.algamoney.api.service.exception.PessoaInexistenteOuInativaexception;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository lancamenteRepository;

	@Autowired
	private MessageSource msmSources;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, org.springframework.data.domain.Pageable pageable) {
		return lancamenteRepository.filtrar(lancamentoFilter, pageable);
	}
	

	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Lancamento>> buscarPorCodigo(@PathVariable Long codigo) {
		Optional<Lancamento> lancamento = lancamenteRepository.findById(codigo);
		if (lancamento != null)
			return ResponseEntity.ok(lancamento);
		else
			return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Lancamento> criar(@RequestBody @Valid Lancamento lancamnento, HttpServletResponse response) {
		Lancamento lancamentoSalva = lancamentoService.salvar(lancamnento);
		publisher.publishEvent(new RecursoCriadoEvento(this, response, lancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);

	}

	@DeleteMapping("/{codigo}")
	public void deletarLancamento(@PathVariable Long codigo) {
		lancamenteRepository.deleteById(codigo);
	}

	/*
	 * @RequestMapping(value = "/{codigo}", method = RequestMethod.PUT) public
	 * ResponseEntity<Lancamento> atualizar(@RequestBody Lancamento
	 * lancamento, @PathVariable("codigo") Long codigo){
	 * lancamento.setCodigo(codigo);
	 * lancamentoService.atualizarLancamento(lancamento); return
	 * ResponseEntity.noContent().build(); }
	 */

	@ExceptionHandler({ PessoaInexistenteOuInativaexception.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativadaException(PessoaInexistenteOuInativaexception ex) {
		String mensagemUsuario = msmSources.getMessage("pessoa.inexistente-inativa", null,
				LocaleContextHolder.getLocale());
		String msmDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, msmDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);

	}

}
