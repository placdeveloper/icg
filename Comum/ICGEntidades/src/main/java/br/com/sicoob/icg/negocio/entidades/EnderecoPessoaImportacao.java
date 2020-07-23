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
@Table(name = "ENDERECOPESSOAIMPORTACAO", schema = "ICG")
public class EnderecoPessoaImportacao extends ComumImportacao<Long> {

	private static final long serialVersionUID = 4158566858520586083L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDENDERECO")
	private Long idEndereco;

	@Column(name = "CODTIPOENDERECO")
	private String codigoTipoEndereco;

	@Column(name = "CODCEP")
	private String codigoCep;

	@Column(name = "NOMEBAIRRO")
	private String nomeBairro;

	@Column(name = "DESCLOGRADOURO")
	private String descricaoLogradouro;

	@Column(name = "DESCNUMERO")
	private String numero;

	@Column(name = "DESCCOMPLEMENTO")
	private String descricaoComplemento;

	@Column(name = "IDLOCALIDADE")
	private Long codigoLocalidade;

	@Column(name = "IDTIPOLOGRADOURO")
	private Long coodigoTipoLogradouro;

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getCodigoTipoEndereco() {
		return codigoTipoEndereco;
	}

	public void setCodigoTipoEndereco(String codigoTipoEndereco) {
		this.codigoTipoEndereco = codigoTipoEndereco;
	}

	public String getCodigoCep() {
		return codigoCep;
	}

	public void setCodigoCep(String codigoCep) {
		this.codigoCep = codigoCep;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	public String getDescricaoLogradouro() {
		return descricaoLogradouro;
	}

	public void setDescricaoLogradouro(String descricaoLogradouro) {
		this.descricaoLogradouro = descricaoLogradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricaoComplemento() {
		return descricaoComplemento;
	}

	public void setDescricaoComplemento(String descricaoComplemento) {
		this.descricaoComplemento = descricaoComplemento;
	}

	public Long getCodigoLocalidade() {
		return codigoLocalidade;
	}

	public void setCodigoLocalidade(Long codigoLocalidade) {
		this.codigoLocalidade = codigoLocalidade;
	}

	public Long getCoodigoTipoLogradouro() {
		return coodigoTipoLogradouro;
	}

	public void setCoodigoTipoLogradouro(Long coodigoTipoLogradouro) {
		this.coodigoTipoLogradouro = coodigoTipoLogradouro;
	}

	@Override
	public Long getId() {
		return getIdEndereco();
	}
}
