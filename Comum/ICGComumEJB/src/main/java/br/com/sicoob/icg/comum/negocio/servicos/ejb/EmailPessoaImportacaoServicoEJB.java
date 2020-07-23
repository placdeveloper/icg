package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.icg.comum.negocio.servicos.EmailPessoaImportacaoServico;
import br.com.sicoob.icg.comum.persistencia.dao.EmailPessoaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.EmailPessoaImportacao;

@Stateless
@Local(EmailPessoaImportacaoServico.class)
public class EmailPessoaImportacaoServicoEJB extends ICGCrudComumServicoEJB<EmailPessoaImportacao>
		implements EmailPessoaImportacaoServico {

	@Inject
	@Default
	private EmailPessoaImportacaoDAO dao;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public EmailPessoaImportacao incluir(EmailPessoaImportacao objeto) throws BancoobException {
		return super.incluir(objeto);
	}

	public void alterar(EmailPessoaImportacao objeto) throws BancoobException {
		super.alterar(objeto);
	}

	@Override
	protected EmailPessoaImportacaoDAO getDAO() {
		return dao;
	}

}
