package br.com.sicoob.icg.comum.negocio.delegates;

import java.util.List;

import br.com.bancoob.negocio.delegates.BancoobDelegate;
import br.com.sicoob.capes.api.negocio.vo.DominioVO;
import br.com.sicoob.icg.comum.negocio.servicos.DownloadDominioServico;
import br.com.sicoob.icg.comum.negocio.servicos.locator.ICGCadastroServiceLocator;
import br.com.sicoob.icg.negocio.entidades.vo.DownloadDominioVO;

public class DownloadDominioDelegate extends BancoobDelegate<DownloadDominioServico> {

	public DownloadDominioVO obterArquivoDominio(List<DominioVO> lista, String tipoArquivo) {
		return getServico().obterArquivoDominio(lista, tipoArquivo);
	}

	@Override
	protected DownloadDominioServico localizarServico() {
		return ICGCadastroServiceLocator.getInstance().localizarDownloadDominioServico();
	}

}
