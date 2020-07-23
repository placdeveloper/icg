package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.PessoaJuridicaImportacaoServico;
import br.com.sicoob.icg.comum.persistencia.dao.PessoaJuridicaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.PessoaJuridicaImportacao;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(PessoaJuridicaImportacaoServico.class)
public class PessoaJuridicaImportacaoServicoEJB extends ICGCrudComumServicoEJB<PessoaJuridicaImportacao>
		implements PessoaJuridicaImportacaoServico {

	@Inject
	@Default
	private PessoaJuridicaImportacaoDAO dao;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PessoaJuridicaImportacao incluir(PessoaJuridicaImportacao objeto) throws BancoobException {
		return super.incluir(objeto);
	}

	public void alterar(PessoaJuridicaImportacao objeto) throws BancoobException {
		super.alterar(objeto);
	}

	@Override
	protected PessoaJuridicaImportacaoDAO getDAO() {
		return dao;
	}
}
