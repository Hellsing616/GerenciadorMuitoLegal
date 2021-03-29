package com.algamoney.api.exceotionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgaMoneyExcepitionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private MessageSource menssageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String  mensagenUsuario = menssageSource.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
		String  mensagemDEV = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagenUsuario, mensagemDEV));
		return handleExceptionInternal(ex,erros,headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = criarListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex,erros,headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	
	@ExceptionHandler({EmptyResultDataAccessException.class})	
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {		
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado",null, LocaleContextHolder.getLocale());
		String msmDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, msmDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NO_CONTENT, request);
		
	}
	
	
	
	
	private List<Erro> criarListaErros(BindingResult binding){
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError fieldError: binding.getFieldErrors()) {
			String  mensagenUsuario = menssageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String  mensagemDEV = fieldError.toString();
			erros.add(new Erro(mensagenUsuario,mensagemDEV));
			
		}
		
		return erros;
	}
	
	
	public static class Erro{
		String  mensagenUsuario = "";
		String  mensagemDEV = "";
		
		public Erro(String memUser, String msmDev) {
			super();
			this.mensagenUsuario = memUser;
			this.mensagemDEV = msmDev;
		}

		public String getMensagenUsuario() {
			return mensagenUsuario;
		}

		public String getMensagemDEV() {
			return mensagemDEV;
		}
		
		
		
	}

}
