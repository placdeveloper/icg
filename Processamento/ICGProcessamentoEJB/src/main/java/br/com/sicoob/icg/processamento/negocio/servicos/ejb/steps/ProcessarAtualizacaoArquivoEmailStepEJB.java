package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoEmailServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoServico;
import br.com.sicoob.sws.api.servico.StepServico;

@Stateless
@Remote(StepServico.class)
public class ProcessarAtualizacaoArquivoEmailStepEJB extends ProcessarAtualizacaoArquivoStepEJB {

	@EJB
	private ProcessamentoEmailServico servico;

	@Override
	protected ProcessamentoServico getServicoAtualizacao() {
		return servico;
	}
}