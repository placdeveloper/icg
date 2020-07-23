package br.com.sicoob.icg.processamento.negocio.delegates;

import br.com.bancoob.negocio.delegates.BancoobDelegate;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.locator.ICGProcessamentoServiceLocator;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class ProcessamentoArquivoDelegate extends BancoobDelegate<ValidacaoServico> {

	@Override
	protected ValidacaoServico localizarServico() {
		return ICGProcessamentoServiceLocator.getInstance().localizarValidacaoServico();
	}

}
