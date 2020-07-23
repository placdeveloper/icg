package br.com.sicoob.icg.comum.persistencia.dao.impl;

import br.com.sicoob.icg.comum.persistencia.dao.PessoaJuridicaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.PessoaJuridicaImportacao;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class PessoaJuridicaImportacaoDAOImpl extends ICGComumCrudDao<PessoaJuridicaImportacao>
		implements PessoaJuridicaImportacaoDAO {

	/**
	 * contrutor padrão
	 */
	public PessoaJuridicaImportacaoDAOImpl() {
		super(PessoaJuridicaImportacao.class, "PESQUISAR_PESSOA_JURIDICA_ARQUIVO");
	}

}
