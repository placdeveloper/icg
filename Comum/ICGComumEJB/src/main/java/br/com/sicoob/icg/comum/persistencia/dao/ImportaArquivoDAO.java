package br.com.sicoob.icg.comum.persistencia.dao;

import java.util.List;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.persistencia.dao.BancoobCrudDaoIF;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.ErroProcessamentoVO;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public interface ImportaArquivoDAO extends BancoobCrudDaoIF<ImportaArquivo> {

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

}
