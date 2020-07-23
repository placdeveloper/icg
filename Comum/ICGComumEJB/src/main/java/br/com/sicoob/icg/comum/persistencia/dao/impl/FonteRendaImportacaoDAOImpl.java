package br.com.sicoob.icg.comum.persistencia.dao.impl;

import br.com.sicoob.icg.comum.persistencia.dao.FonteRendaImportacaoDAO;
import br.com.sicoob.icg.negocio.entidades.FonteRendaImportacao;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class FonteRendaImportacaoDAOImpl extends ICGComumCrudDao<FonteRendaImportacao>
		implements FonteRendaImportacaoDAO {

	/**
	 * contrutor padrão
	 */
	public FonteRendaImportacaoDAOImpl() {
		super(FonteRendaImportacao.class, "PESQUISAR_FONTE_RENDA_ARQUIVO");
	}

}
