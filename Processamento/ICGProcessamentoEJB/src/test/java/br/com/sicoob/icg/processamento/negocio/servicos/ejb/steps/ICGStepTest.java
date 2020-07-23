package br.com.sicoob.icg.processamento.negocio.servicos.ejb.steps;

import java.util.HashMap;

import org.junit.Ignore;
import org.mockito.Mockito;

import br.com.sicoob.sws.api.contexto.ContextoExecucao;
import br.com.sicoob.sws.api.dominio.TipoParametro;
import br.com.sicoob.sws.api.parametro.Parametro;


/**
 * Classe com m�todos utilit�rios para o teste
 * @author Pablo.Andrade
 *
 */
@Ignore
public abstract class ICGStepTest extends Mockito {
	
	protected void adicionarParametroStep(ContextoExecucao contexto, Parametro parametro) {
		if(contexto.getParametrosStep() == null) {
			contexto.setParametrosStep(new HashMap<String, Parametro>());
		}
		contexto.getParametrosStep().put(parametro.getChave(), parametro);
	}
	
	protected void adicionarParametroJob(ContextoExecucao contexto, Parametro parametro) {
		if(contexto.getParametrosJob() == null) {
			contexto.setParametrosJob(new HashMap<String, Parametro>());
		}
		contexto.getParametrosJob().put(parametro.getChave(), parametro);
	}
	
	protected void adicionarParametroFluxo(ContextoExecucao contexto, Parametro parametro) {
		if(contexto.getParametrosFluxo() == null) {
			contexto.setParametrosFluxo(new HashMap<String, Parametro>());
		}
		contexto.getParametrosFluxo().put(parametro.getChave(), parametro);
	}
	
	protected ContextoExecucao criarContextoExecucao() {
		ContextoExecucao contexto = new ContextoExecucao();
		contexto.setParametroDinamico(new Parametro("dinamico", "", TipoParametro.TEXTO));
		contexto.setParametrosStep(new HashMap<String, Parametro>());
		contexto.setParametrosJob(new HashMap<String, Parametro>());
		contexto.setParametrosFluxo(new HashMap<String, Parametro>());
		return contexto;
	}
	
}
