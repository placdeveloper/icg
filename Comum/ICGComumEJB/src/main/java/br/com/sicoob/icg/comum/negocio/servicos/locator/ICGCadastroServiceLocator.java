package br.com.sicoob.icg.comum.negocio.servicos.locator;

import br.com.bancoob.negocio.servicos.locator.BancoobServiceLocator;
import br.com.sicoob.icg.comum.negocio.servicos.DownloadDominioServico;
import br.com.sicoob.icg.comum.negocio.servicos.ImportaArquivoServico;

public final class ICGCadastroServiceLocator extends BancoobServiceLocator {

	private static ICGCadastroServiceLocator locator;

	public synchronized static ICGCadastroServiceLocator getInstance() {
		if (locator == null) {
			locator = new ICGCadastroServiceLocator();
		}

		return locator;
	}

	private ICGCadastroServiceLocator() {
		super("icg.cadastro");
	}

	public ImportaArquivoServico localizarImportaArquivoServico() {
		return (ImportaArquivoServico) localizar("locator.icg.ImportaArquivoServico");
	}

	public DownloadDominioServico localizarDownloadDominioServico() {
		return (DownloadDominioServico) localizar("locator.icg.DownloadDominioServico");
	}

}
