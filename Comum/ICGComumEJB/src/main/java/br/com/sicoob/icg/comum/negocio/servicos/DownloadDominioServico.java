package br.com.sicoob.icg.comum.negocio.servicos;

import java.util.List;

import br.com.sicoob.capes.api.negocio.vo.DominioVO;
import br.com.sicoob.icg.negocio.entidades.vo.DownloadDominioVO;

public interface DownloadDominioServico extends ICGComumServico {

	DownloadDominioVO obterArquivoDominio(List<DominioVO> lista, String tipoArquivo);

}
