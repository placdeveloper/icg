package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.FonteRendaImportacaoServico;
import br.com.sicoob.icg.comum.persistencia.dao.FonteRendaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.FonteRendaImportacao;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(FonteRendaImportacaoServico.class)
public class FonteRendaImportacaoServicoEJB extends ICGCrudComumServicoEJB<FonteRendaImportacao>
		implements FonteRendaImportacaoServico {

	@Inject
	@Default
	private FonteRendaImportacaoDAO dao;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public FonteRendaImportacao incluir(FonteRendaImportacao objeto) throws BancoobException {
		return super.incluir(objeto);
	}

	public void alterar(FonteRendaImportacao objeto) throws BancoobException {
		super.alterar(objeto);
	}

	@Override
	protected FonteRendaImportacaoDAO getDAO() {
		return dao;
	}

}
