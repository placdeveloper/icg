package br.com.sicoob.icg.processamento.negocio.servicos.ejb.jobs;

import java.util.List;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.excecao.BancoobRuntimeException;
import br.com.sicoob.infraestrutura.log.ISicoobLogger;
import br.com.sicoob.infraestrutura.log.SicoobLoggerPadrao;
import br.com.sicoob.sws.api.contexto.ContextoExecucao;
import br.com.sicoob.sws.api.execucao.JobSicoobServico;
import br.com.sicoob.sws.api.execucao.Step;
import br.com.sicoob.sws.api.execucao.VerificacaoDependencias;

public abstract class ICGJobBase extends JobSicoobServico {

	protected ISicoobLogger getLogger() {
		return SicoobLoggerPadrao.getInstance(getClass());
	}

	@Override
	public final VerificacaoDependencias verificarDependencias(ContextoExecucao contexto) {
		try {
			return executarVerificarDependencias(contexto);
		} catch (BancoobException e) {
			return erro(e.getMessage());
		}
	}

	@Override
	public final List<Step> obterSteps(ContextoExecucao contexto) {
		try {
			return executarObterSteps(contexto);
		} catch (BancoobException e) {
			getLogger().erro(e, e.getMessage());
			throw new BancoobRuntimeException(e.getMessage());
		}
	}

	protected abstract VerificacaoDependencias executarVerificarDependencias(ContextoExecucao contexto)
			throws BancoobException;

	protected abstract List<Step> executarObterSteps(ContextoExecucao contexto) throws BancoobException;

}
