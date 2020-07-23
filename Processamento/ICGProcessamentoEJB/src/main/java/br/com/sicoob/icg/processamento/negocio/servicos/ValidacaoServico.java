package br.com.sicoob.icg.processamento.negocio.servicos;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.servicos.BancoobServico;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public interface ValidacaoServico extends BancoobServico {

	/**
	 * 
	 * @param arquivo
	 * @throws BancoobException
	 */
	void iniciaValidacaoArquivo(ImportaArquivo arquivo) throws BancoobException;
	
}
