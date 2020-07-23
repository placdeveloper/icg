package br.com.sicoob.icg.negocio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.bancoob.persistencia.types.DateTimeDB;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Entity
@Table(name = "PESSOAJURIDICAIMPORTACAO", schema = "ICG")
public class PessoaJuridicaImportacao extends ComumImportacao<Long> implements Serializable {

	private static final long serialVersionUID = 1291282108066418757L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPESSOAJURIDICA")
	private Long idPessoaJuridica;

	@Column(name = "NOMERAZAOSOCIAL")
	private String nomeRazaoSocial;

	@Column(name = "NOMERAZAOSOCIALCOMPLETO")
	private String nomeRazaoSocialCompleto;

	@Column(name = "CODATIVIDADEECONOMICA")
	private String codigoAtividadeEconomica;

	@Column(name = "DATACONSTITUICAO")
	private DateTimeDB dataConstituicao;

	@Column(name = "NUMREGISTROJUNTACOMERCIAL")
	private String numeroRegistroJuntaComercial;

	@Column(name = "DATAREGISTROJUNTACOMERCIAL")
	private DateTimeDB dataRegistroJuntaComercial;

	@Column(name = "CODESFERAADMINISTRATIVA")
	private String codigoEsferaAdministrativa;

	@Column(name = "CODTIPOFORMACONSTITUICAO")
	private String codigoTipoFormaConstituicao;
	
	public Long getIdPessoaJuridica() {
		return idPessoaJuridica;
	}

	public void setIdPessoaJuridica(Long idPessoaJuridica) {
		this.idPessoaJuridica = idPessoaJuridica;
	}

	public String getNomeRazaoSocial() {
		return nomeRazaoSocial;
	}

	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}

	public String getNomeRazaoSocialCompleto() {
		return nomeRazaoSocialCompleto;
	}

	public void setNomeRazaoSocialCompleto(String nomeRazaoSocialCompleto) {
		this.nomeRazaoSocialCompleto = nomeRazaoSocialCompleto;
	}

	public String getCodigoAtividadeEconomica() {
		return codigoAtividadeEconomica;
	}

	public void setCodigoAtividadeEconomica(String codigoAtividadeEconomica) {
		this.codigoAtividadeEconomica = codigoAtividadeEconomica;
	}

	public DateTimeDB getDataConstituicao() {
		return dataConstituicao;
	}

	public void setDataConstituicao(DateTimeDB dataConstituicao) {
		this.dataConstituicao = dataConstituicao;
	}

	public String getNumeroRegistroJuntaComercial() {
		return numeroRegistroJuntaComercial;
	}

	public void setNumeroRegistroJuntaComercial(String numeroRegistroJuntaComercial) {
		this.numeroRegistroJuntaComercial = numeroRegistroJuntaComercial;
	}

	public DateTimeDB getDataRegistroJuntaComercial() {
		return dataRegistroJuntaComercial;
	}

	public void setDataRegistroJuntaComercial(DateTimeDB dataRegistroJuntaComercial) {
		this.dataRegistroJuntaComercial = dataRegistroJuntaComercial;
	}

	public String getCodigoEsferaAdministrativa() {
		return codigoEsferaAdministrativa;
	}

	public void setCodigoEsferaAdministrativa(String codigoEsferaAdministrativa) {
		this.codigoEsferaAdministrativa = codigoEsferaAdministrativa;
	}

	public String getCodigoTipoFormaConstituicao() {
		return codigoTipoFormaConstituicao;
	}

	public void setCodigoTipoFormaConstituicao(String codigoTipoFormaConstituicao) {
		this.codigoTipoFormaConstituicao = codigoTipoFormaConstituicao;
	}

	@Override
	public Long getId() {
		return getIdPessoaJuridica();
	}
}
