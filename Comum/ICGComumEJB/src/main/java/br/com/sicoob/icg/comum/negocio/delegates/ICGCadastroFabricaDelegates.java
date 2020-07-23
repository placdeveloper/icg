package br.com.sicoob.icg.comum.negocio.delegates;

import br.com.bancoob.negocio.delegates.BancoobFabricaDelegates;

public final class ICGCadastroFabricaDelegates extends BancoobFabricaDelegates {

	private static ICGCadastroFabricaDelegates fabrica;

	public synchronized static ICGCadastroFabricaDelegates getInstance() {
		if (fabrica == null) {
			fabrica = new ICGCadastroFabricaDelegates();
		}
		return fabrica;
	}

	private ICGCadastroFabricaDelegates() {
		super();
	}

	public ImportaArquivoDelegate criarImportaArquivoDelegate() {
		return new ImportaArquivoDelegate();
	}

	public DownloadDominioDelegate criarDownloadDominioDelegate() {
		return new DownloadDominioDelegate();
	}
}