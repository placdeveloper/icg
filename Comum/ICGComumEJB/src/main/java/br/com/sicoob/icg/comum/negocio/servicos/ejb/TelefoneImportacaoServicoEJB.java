package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.TelefoneImportacaoServico;
import br.com.sicoob.icg.comum.persistencia.dao.TelefoneImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.TelefonePessoaImportacao;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(TelefoneImportacaoServico.class)
public class TelefoneImportacaoServicoEJB extends ICGCrudComumServicoEJB<TelefonePessoaImportacao>
		implements TelefoneImportacaoServico {

	@Inject
	@Default
	private TelefoneImportacaoDAO dao;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TelefonePessoaImportacao incluir(TelefonePessoaImportacao objeto) throws BancoobException {
		return super.incluir(objeto);
	}

	public void alterar(TelefonePessoaImportacao objeto) throws BancoobException {
		super.alterar(objeto);
	}

	@Override
	protected TelefoneImportacaoDAO getDAO() {
		return dao;
	}
}
