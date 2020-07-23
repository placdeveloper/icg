package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.EnderecoPessoaImportacaoServico;
import br.com.sicoob.icg.comum.persistencia.dao.EnderecoPessoaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.EnderecoPessoaImportacao;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(EnderecoPessoaImportacaoServico.class)
public class EnderecoPessoaImportacaoServicoEJB extends ICGCrudComumServicoEJB<EnderecoPessoaImportacao>
		implements EnderecoPessoaImportacaoServico {

	@Inject
	@Default
	private EnderecoPessoaImportacaoDAO dao;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public EnderecoPessoaImportacao incluir(EnderecoPessoaImportacao objeto) throws BancoobException {
		return super.incluir(objeto);
	}

	public void alterar(EnderecoPessoaImportacao objeto) throws BancoobException {
		super.alterar(objeto);
	}

	@Override
	protected EnderecoPessoaImportacaoDAO getDAO() {
		return dao;
	}

}
