package br.com.sicoob.icg.comum.negocio.delegates;

import java.util.List;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.delegates.BancoobCrudDelegate;
import br.com.sicoob.icg.comum.negocio.servicos.ImportaArquivoServico;
import br.com.sicoob.icg.comum.negocio.servicos.locator.ICGCadastroServiceLocator;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.ErroProcessamentoVO;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class ImportaArquivoDelegate extends BancoobCrudDelegate<ImportaArquivo, ImportaArquivoServico> {

	/**
	 * @see ImportaArquivoServico#listaArquivosImportados(ImportaArquivo)
	 */
	public List<ImportaArquivo> listaArquivosImportados(ImportaArquivo criterios) throws BancoobException {
		return getServico().listaArquivosImportados(criterios);
	}

	/**
	 * @see ImportaArquivoServico#listaErroProcessamento(ImportaArquivo)
	 */
	public List<ErroProcessamentoVO> listaErroProcessamento(ImportaArquivo criterios) throws BancoobException {
		return getServico().listaErroProcessamento(criterios);
	}

	@Override
	protected ImportaArquivoServico localizarServico() {
		return ICGCadastroServiceLocator.getInstance().localizarImportaArquivoServico();
	}
}
