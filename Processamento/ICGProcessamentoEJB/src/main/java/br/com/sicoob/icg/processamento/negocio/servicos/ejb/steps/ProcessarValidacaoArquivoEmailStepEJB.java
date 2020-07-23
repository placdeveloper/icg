package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoEmailServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoServico;
import br.com.sicoob.sws.api.servico.StepServico;

@Stateless
@Remote(StepServico.class)
public class ProcessarValidacaoArquivoEmailStepEJB extends ProcessarValidacaoArquivoStepEJB {

	@EJB
	private ValidacaoEmailServico validacaoServicoEmail;

	@Override
	protected ValidacaoServico getServicoValidacao() {
		return validacaoServicoEmail;
	}
}