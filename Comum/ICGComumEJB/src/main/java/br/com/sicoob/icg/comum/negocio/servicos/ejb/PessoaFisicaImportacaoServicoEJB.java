package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.PessoaFisicaImportacaoServico;
import br.com.sicoob.icg.comum.persistencia.dao.PessoaFisicaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.PessoaFisicaImportacao;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(PessoaFisicaImportacaoServico.class)
public class PessoaFisicaImportacaoServicoEJB extends ICGCrudComumServicoEJB<PessoaFisicaImportacao>
		implements PessoaFisicaImportacaoServico {

	@Inject
	@Default
	private PessoaFisicaImportacaoDAO dao;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PessoaFisicaImportacao incluir(PessoaFisicaImportacao objeto) throws BancoobException {
		return super.incluir(objeto);
	}

	public void alterar(PessoaFisicaImportacao objeto) throws BancoobException {
		super.alterar(objeto);
	}

	@Override
	protected PessoaFisicaImportacaoDAO getDAO() {
		return dao;
	}
}
