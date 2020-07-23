package br.com.sicoob.icg.comum.persistencia.dao.impl;

import br.com.sicoob.icg.comum.persistencia.dao.EnderecoPessoaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.EnderecoPessoaImportacao;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class EnderecoPessoaImportacaoDAOImpl extends ICGComumCrudDao<EnderecoPessoaImportacao>
		implements EnderecoPessoaImportacaoDAO {

	/**
	 * contrutor padrão
	 */
	public EnderecoPessoaImportacaoDAOImpl() {
		super(EnderecoPessoaImportacao.class, "PESQUISAR_ENDERECO_ARQUIVO");
	}

}
