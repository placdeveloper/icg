package br.com.sicoob.icg.processamento.negocio.servicos.locator;

import br.com.bancoob.negocio.servicos.locator.BancoobServiceLocator;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoServico;

public final class ICGProcessamentoServiceLocator extends BancoobServiceLocator {

	private static ICGProcessamentoServiceLocator locator;

	public synchronized static ICGProcessamentoServiceLocator getInstance() {
		if (locator == null) {
			locator = new ICGProcessamentoServiceLocator();
		}

		return locator;
	}

	private ICGProcessamentoServiceLocator() {
		super("icg.processamento");
	}

	public ValidacaoServico localizarValidacaoServico() {
		return (ValidacaoServico) localizar("locator.icg.ValidacaoServico");
	}

}
