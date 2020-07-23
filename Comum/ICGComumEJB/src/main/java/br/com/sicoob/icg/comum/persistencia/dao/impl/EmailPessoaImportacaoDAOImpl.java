package br.com.sicoob.icg.comum.persistencia.dao.impl;

import br.com.sicoob.icg.comum.persistencia.dao.EmailPessoaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.EmailPessoaImportacao;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class EmailPessoaImportacaoDAOImpl extends ICGComumCrudDao<EmailPessoaImportacao>
		implements EmailPessoaImportacaoDAO {

	/**
	 * contrutor padrão
	 */
	public EmailPessoaImportacaoDAOImpl() {
		super(EmailPessoaImportacao.class, "PESQUISAR_EMAIL_ARQUIVO");
	}

}
