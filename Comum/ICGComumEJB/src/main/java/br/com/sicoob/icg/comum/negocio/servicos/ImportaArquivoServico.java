package br.com.sicoob.icg.comum.negocio.servicos;

import java.util.List;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.ErroProcessamentoVO;
import br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public interface ImportaArquivoServico extends ICGCrudComumServico<ImportaArquivo> {

	/**
	 * 
	 * @param criterios
	 * @return
	 * @throws BancoobException
	 */
	List<ImportaArquivo> listaArquivosImportados(ImportaArquivo criterios) throws BancoobException;

	/**
	 * 
	 * @param criterios
	 * @return
	 * @throws BancoobException
	 */
	List<ErroProcessamentoVO> listaErroProcessamento(ImportaArquivo criterios) throws BancoobException;

	/**
	 * 
	 * @param tipoArquivo
	 * @return
	 * @throws BancoobException
	 */
	List<ImportaArquivo> listarArquivosImportadosNaoIniciados(TipoAtualizacaoEnum tipoArquivo) throws BancoobException;

	/**
	 * 
	 * @return
	 * @throws BancoobException
	 */
	List<ImportaArquivo> listarArquivosImportadosNaoIniciados() throws BancoobException;

	/**
	 * 
	 * @param situacaoProcessamento
	 * @return
	 * @throws BancoobException
	 */
	List<ImportaArquivo> listarArquivosImportadosPendentesProcessamento() throws BancoobException;

}
