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
@Table(name = "TELEFONEPESSOAIMPORTACAO", schema = "ICG")
public class TelefonePessoaImportacao extends ComumImportacao<Long> {

	private static final long serialVersionUID = 8962050230544853599L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDTELEFONE")
	private Long idTelefone;

	@Column(name = "CODTIPOTELEFONE")
	private Long codigoTipoTelefone;

	@Column(name = "NUMDDD")
	private String numeroDdd;

	@Column(name = "NUMTELEFONE")
	private String numeroTelefone;

	@Column(name = "NUMRAMAL")
	private String numeroRamal;

	@Column(name = "DESCOBSERVACAO")
	private String descricaoObservacao;

	public Long getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(Long idTelefone) {
		this.idTelefone = idTelefone;
	}

	public Long getCodigoTipoTelefone() {
		return codigoTipoTelefone;
	}

	public void setCodigoTipoTelefone(Long codigoTipoTelefone) {
		this.codigoTipoTelefone = codigoTipoTelefone;
	}

	public String getNumeroDdd() {
		return numeroDdd;
	}

	public void setNumeroDdd(String numeroDdd) {
		this.numeroDdd = numeroDdd;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public String getNumeroRamal() {
		return numeroRamal;
	}

	public void setNumeroRamal(String numeroRamal) {
		this.numeroRamal = numeroRamal;
	}

	public String getDescricaoObservacao() {
		return descricaoObservacao;
	}

	public void setDescricaoObservacao(String descricaoObservacao) {
		this.descricaoObservacao = descricaoObservacao;
	}

	@Override
	public Long getId() {
		return getIdTelefone();
	}
}
