package com.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algamoney.api.event.RecursoCriadoEvento;

@Component
public class RecursocriadoDoEvento implements ApplicationListener<RecursoCriadoEvento>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvento recursoCriadoEvento) {
		HttpServletResponse response = recursoCriadoEvento.getResponse();
		Long codigo = recursoCriadoEvento.getCodigo();
		
		adicionarHeaderlocation(response, codigo);	
	}

	private void adicionarHeaderlocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
			response.setHeader("Location", uri.toASCIIString());
	}
	

}
