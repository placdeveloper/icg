package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoEnderecoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoServico;
import br.com.sicoob.sws.api.servico.StepServico;

@Stateless
@Remote(StepServico.class)
public class ProcessarValidacaoArquivoEnderecoStepEJB extends ProcessarValidacaoArquivoStepEJB {

	@EJB
	private ValidacaoEnderecoServico validacaoEnderecoServico;

	@Override
	protected ValidacaoServico getServicoValidacao() {
		return validacaoEnderecoServico;
	}

}