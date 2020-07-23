package br.com.sicoob.icg.processamento.negocio.excecao;

import br.com.bancoob.negocio.excecao.NegocioException;

public class ConversaoInvalidaException extends NegocioException  {
	
	private static final long serialVersionUID = -5872546633858456864L;

	public ConversaoInvalidaException(String mensagem) {
		super(mensagem);
	}

	
	

}
