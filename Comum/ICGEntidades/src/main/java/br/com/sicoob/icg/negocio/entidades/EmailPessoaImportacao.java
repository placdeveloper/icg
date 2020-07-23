package br.com.sicoob.icg.negocio.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Entity
@Table(name = "EMAILPESSOAIMPORTACAO", schema = "ICG")
public class EmailPessoaImportacao extends ComumImportacao<Long> {

	private static final long serialVersionUID = -78068500067165997L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDEMAIL")
	private Long idEmail;

	@Column(name = "CODTIPOEMAIL")
	private String codigoTipoEmail;

	@Column(name = "DESCEMAIL")
	private String descricaoEmail;
	
	@Override
	public Long getId() {
		return getIdEmail();
	}

	public Long getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(Long idEmail) {
		this.idEmail = idEmail;
	}

	public String getCodigoTipoEmail() {
		return codigoTipoEmail;
	}

	public void setCodigoTipoEmail(String codigoTipoEmail) {
		this.codigoTipoEmail = codigoTipoEmail;
	}

	public String getDescricaoEmail() {
		return descricaoEmail;
	}

	public void setDescricaoEmail(String descricaoEmail) {
		this.descricaoEmail = descricaoEmail;
	}

}
