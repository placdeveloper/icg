package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.infraestrutura.log.ISicoobLogger;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import br.com.sicoob.sws.api.contexto.ContextoExecucao;
import br.com.sicoob.sws.api.execucao.RetornoExecucao;
import br.com.sicoob.sws.api.execucao.StepSicoobServico;

public abstract class ICGStepBase extends StepSicoobServico {
	
	protected ISicoobLogger getLogger() {
		return SicoobLoggerPadrao.getInstance(getClass());
	}

	@Override
	public final RetornoExecucao executar(ContextoExecucao contexto) {
		try {
			return executarStep(contexto);
		} catch (BancoobException e) {
			return erro(e.getMessage());
		}
	}

	protected abstract RetornoExecucao executarStep(ContextoExecucao contexto) throws BancoobException;

}
