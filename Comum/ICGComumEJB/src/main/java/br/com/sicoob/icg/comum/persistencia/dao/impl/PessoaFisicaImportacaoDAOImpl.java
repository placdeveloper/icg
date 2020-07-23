package br.com.sicoob.icg.comum.persistencia.dao.impl;

import br.com.sicoob.icg.comum.persistencia.dao.PessoaFisicaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.PessoaFisicaImportacao;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class PessoaFisicaImportacaoDAOImpl extends ICGComumCrudDao<PessoaFisicaImportacao>
		implements PessoaFisicaImportacaoDAO {

	/**
	 * contrutor padrão
	 */
	public PessoaFisicaImportacaoDAOImpl() {
		super(PessoaFisicaImportacao.class, "PESQUISAR_PESSOA_FISICA_ARQUIVO");
	}

}
