package br.com.sicoob.icg.processamento.negocio.delegates;

import br.com.bancoob.negocio.delegates.BancoobFabricaDelegates;

public final class ICGProcessamentoFabricaDelegates extends BancoobFabricaDelegates {

	private static ICGProcessamentoFabricaDelegates fabrica;

	public synchronized static ICGProcessamentoFabricaDelegates getInstance() {
		if (fabrica == null) {
			fabrica = new ICGProcessamentoFabricaDelegates();
		}
		return fabrica;
	}

	private ICGProcessamentoFabricaDelegates() {
		super();
	}

	public ProcessamentoArquivoDelegate criarProcessamentoArquivoDelegate() {
		return new ProcessamentoArquivoDelegate();
	}
}