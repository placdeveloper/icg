package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoTelefoneServico;
import br.com.sicoob.sws.api.servico.StepServico;

@Stateless
@Remote(StepServico.class)
public class ProcessarValidacaoArquivoTelefoneStepEJB extends ProcessarValidacaoArquivoStepEJB {

	@EJB
	private ValidacaoTelefoneServico validacaoTelefoneServico;

	@Override
	protected ValidacaoServico getServicoValidacao() {
		return validacaoTelefoneServico;
	}
}