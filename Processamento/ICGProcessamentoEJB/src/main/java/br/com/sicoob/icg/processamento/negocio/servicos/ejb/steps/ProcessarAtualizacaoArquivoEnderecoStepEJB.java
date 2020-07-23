package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoEnderecoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoServico;
import br.com.sicoob.sws.api.servico.StepServico;

@Stateless
@Remote(StepServico.class)
public class ProcessarAtualizacaoArquivoEnderecoStepEJB extends ProcessarAtualizacaoArquivoStepEJB {

	@EJB
	private ProcessamentoEnderecoServico servico;

	@Override
	protected ProcessamentoServico getServicoAtualizacao() {
		return servico;
	}
}