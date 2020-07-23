package br.com.sicoob.icg.comum.persistencia.dao.impl;

import br.com.sicoob.icg.comum.persistencia.dao.TelefoneImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.TelefonePessoaImportacao;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class TelefoneImportacaoDAOImpl extends ICGComumCrudDao<TelefonePessoaImportacao>
		implements TelefoneImportacaoDAO {

	/**
	 * contrutor padrão
	 */
	public TelefoneImportacaoDAOImpl() {
		super(TelefonePessoaImportacao.class, "PESQUISAR_TELEFONE_ARQUIVO");
	}

}
