package br.com.sicoob.icg.processamento.negocio.servicos;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.servicos.BancoobServico;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public interface ProcessamentoServico extends BancoobServico {

	/**
	 * 
	 * @param arquivo
	 * @throws BancoobException
	 */
	void iniciaAtualizacao(ImportaArquivo arquivo) throws BancoobException;
	
}
