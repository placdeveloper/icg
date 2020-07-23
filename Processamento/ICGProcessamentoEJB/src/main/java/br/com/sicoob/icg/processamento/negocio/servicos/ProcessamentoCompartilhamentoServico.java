package br.com.sicoob.icg.processamento.negocio.servicos;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.servicos.BancoobServico;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public interface ProcessamentoCompartilhamentoServico extends BancoobServico {

	void compartilharPessoa(PessoaBasicaVO pessoaBasicaVO) throws BancoobException;

}
