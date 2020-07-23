package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoPessoaFisicaServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoServico;
import br.com.sicoob.sws.api.servico.StepServico;

@Stateless
@Remote(StepServico.class)
public class ProcessarValidacaoArquivoPessoaFisicaStepEJB extends ProcessarValidacaoArquivoStepEJB {

	@EJB
	private ValidacaoPessoaFisicaServico validacaoPessoaFisicaServico;

	@Override
	protected ValidacaoServico getServicoValidacao() {
		return validacaoPessoaFisicaServico;
	}
}