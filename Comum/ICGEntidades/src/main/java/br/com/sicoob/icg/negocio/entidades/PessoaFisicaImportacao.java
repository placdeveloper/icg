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
@Table(name = "PESSOAFISICAIMPORTACAO", schema = "ICG")
public class PessoaFisicaImportacao extends ComumImportacao<Long> implements Serializable {

	private static final long serialVersionUID = -8338310293156545303L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPESSOAFISICA")
	private Long idPessoaFisica;

	@Column(name = "NOMEPESSOA")
	private String nomePessoa;

	@Column(name = "NOMECOMPLETO")
	private String nomeCompleto;

	@Column(name = "CODATIVIDADEECONOMICA")
	private String codigoAtividadeEconomica;

	@Column(name = "DATANASCIMENTO")
	private DateTimeDB dataNascimento;

	@Column(name = "BOLMENOREMANCIPADO")
	private Long menorEmancipado;

	@Column(name = "NOMEPAI")
	private String nomepai;

	@Column(name = "NOMEMAE")
	private String nomeMae;

	@Column(name = "CODTIPODOCUMENTOIDENTIFICACAO")
	private String codigoTipoDocumentoIdentificacao;

	@Column(name = "NUMDOCUMENTOIDENTIFICACAO")
	private String numeroDocumentoIdentificacao;

	@Column(name = "DESCORGAOEXPDOCUMENTOIDENTIFICACAO")
	private String descricaoOrgaoExpedidorDocumentoIdentificacao;

	@Column(name = "SIGLAUFORGEXPDOCUMENTOIDENTIFICACAO")
	private String siglaUfOrgaoExpedidorDocumentoIdentificacao;

	@Column(name = "DATAEMISSAODOCUMENTOIDENTIFICACAO")
	private DateTimeDB dataEmissaoDocumentoIdentificacao;

	@Column(name = "CODTIPOSEXO")
	private String codigpTipoSexo;

	@Column(name = "IDOCUPACAOPROFISSIONAL")
	private Long codigoOcupacaoProfissional;

	@Column(name = "CODESTADOCIVIL")
	private String codigoEstadoCivil;

	@Column(name = "BOLUNIAOESTAVEL")
	private Long uniaoEestavel;

	@Column(name = "CODGRAUINSTRUCAO")
	private String codigoGrauInstrucao;

	@Column(name = "IDNATURALIDADE")
	private Long codigoNaturalidade;

	@Column(name = "DESCNATURALIDADE")
	private String descricaoNaturalidade;

	@Column(name = "CODVINCULOEMPREGATICIO")
	private String codigoVinculoEmpregaticio;

	@Column(name = "CODNACIONALIDADE")
	private String codigoNacionalidade;

	@Column(name = "NUMCPFCONJUGE")
	private String numeroCpfConjuge;

	@Column(name = "QTDDEPENDENTE")
	private Long quantidadeDependente;

	@Column(name = "CODREGIMECASAMENTO")
	private String codigoRegimeCasamento;

	@Column(name = "IDINSTITUICAOCONJUGE")
	private Long idInstituicaoConjuge;

	public Long getIdPessoaFisica() {
		return idPessoaFisica;
	}

	public void setIdPessoaFisica(Long idPessoaFisica) {
		this.idPessoaFisica = idPessoaFisica;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCodigoAtividadeEconomica() {
		return codigoAtividadeEconomica;
	}

	public void setCodigoAtividadeEconomica(String codigoAtividadeEconomica) {
		this.codigoAtividadeEconomica = codigoAtividadeEconomica;
	}

	public DateTimeDB getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(DateTimeDB dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getMenorEmancipado() {
		return menorEmancipado;
	}

	public void setMenorEmancipado(Long menorEmancipado) {
		this.menorEmancipado = menorEmancipado;
	}

	public String getNomepai() {
		return nomepai;
	}

	public void setNomepai(String nomepai) {
		this.nomepai = nomepai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getCodigoTipoDocumentoIdentificacao() {
		return codigoTipoDocumentoIdentificacao;
	}

	public void setCodigoTipoDocumentoIdentificacao(String codigoTipoDocumentoIdentificacao) {
		this.codigoTipoDocumentoIdentificacao = codigoTipoDocumentoIdentificacao;
	}

	public String getNumeroDocumentoIdentificacao() {
		return numeroDocumentoIdentificacao;
	}

	public void setNumeroDocumentoIdentificacao(String numeroDocumentoIdentificacao) {
		this.numeroDocumentoIdentificacao = numeroDocumentoIdentificacao;
	}

	public String getDescricaoOrgaoExpedidorDocumentoIdentificacao() {
		return descricaoOrgaoExpedidorDocumentoIdentificacao;
	}

	public void setDescricaoOrgaoExpedidorDocumentoIdentificacao(String descricaoOrgaoExpedidorDocumentoIdentificacao) {
		this.descricaoOrgaoExpedidorDocumentoIdentificacao = descricaoOrgaoExpedidorDocumentoIdentificacao;
	}

	public String getSiglaUfOrgaoExpedidorDocumentoIdentificacao() {
		return siglaUfOrgaoExpedidorDocumentoIdentificacao;
	}

	public void setSiglaUfOrgaoExpedidorDocumentoIdentificacao(String siglaUfOrgaoExpedidorDocumentoIdentificacao) {
		this.siglaUfOrgaoExpedidorDocumentoIdentificacao = siglaUfOrgaoExpedidorDocumentoIdentificacao;
	}

	public DateTimeDB getDataEmissaoDocumentoIdentificacao() {
		return dataEmissaoDocumentoIdentificacao;
	}

	public void setDataEmissaoDocumentoIdentificacao(DateTimeDB dataEmissaoDocumentoIdentificacao) {
		this.dataEmissaoDocumentoIdentificacao = dataEmissaoDocumentoIdentificacao;
	}

	public String getCodigpTipoSexo() {
		return codigpTipoSexo;
	}

	public void setCodigpTipoSexo(String codigpTipoSexo) {
		this.codigpTipoSexo = codigpTipoSexo;
	}

	public Long getCodigoOcupacaoProfissional() {
		return codigoOcupacaoProfissional;
	}

	public void setCodigoOcupacaoProfissional(Long codigoOcupacaoProfissional) {
		this.codigoOcupacaoProfissional = codigoOcupacaoProfissional;
	}

	public String getCodigoEstadoCivil() {
		return codigoEstadoCivil;
	}

	public void setCodigoEstadoCivil(String codigoEstadoCivil) {
		this.codigoEstadoCivil = codigoEstadoCivil;
	}

	public Long getUniaoEestavel() {
		return uniaoEestavel;
	}

	public void setUniaoEestavel(Long uniaoEestavel) {
		this.uniaoEestavel = uniaoEestavel;
	}

	public String getCodigoGrauInstrucao() {
		return codigoGrauInstrucao;
	}

	public void setCodigoGrauInstrucao(String codigoGrauInstrucao) {
		this.codigoGrauInstrucao = codigoGrauInstrucao;
	}

	public Long getCodigoNaturalidade() {
		return codigoNaturalidade;
	}

	public void setCodigoNaturalidade(Long codigoNaturalidade) {
		this.codigoNaturalidade = codigoNaturalidade;
	}

	public String getDescricaoNaturalidade() {
		return descricaoNaturalidade;
	}

	public void setDescricaoNaturalidade(String descricaoNaturalidade) {
		this.descricaoNaturalidade = descricaoNaturalidade;
	}

	public String getCodigoVinculoEmpregaticio() {
		return codigoVinculoEmpregaticio;
	}

	public void setCodigoVinculoEmpregaticio(String codigoVinculoEmpregaticio) {
		this.codigoVinculoEmpregaticio = codigoVinculoEmpregaticio;
	}

	public String getCodigoNacionalidade() {
		return codigoNacionalidade;
	}

	public void setCodigoNacionalidade(String codigoNacionalidade) {
		this.codigoNacionalidade = codigoNacionalidade;
	}

	public String getNumeroCpfConjuge() {
		return numeroCpfConjuge;
	}

	public void setNumeroCpfConjuge(String numeroCpfConjuge) {
		this.numeroCpfConjuge = numeroCpfConjuge;
	}

	public Long getQuantidadeDependente() {
		return quantidadeDependente;
	}

	public void setQuantidadeDependente(Long quantidadeDependente) {
		this.quantidadeDependente = quantidadeDependente;
	}

	public String getCodigoRegimeCasamento() {
		return codigoRegimeCasamento;
	}

	public void setCodigoRegimeCasamento(String codigoRegimeCasamento) {
		this.codigoRegimeCasamento = codigoRegimeCasamento;
	}

	public Long getIdInstituicaoConjuge() {
		return idInstituicaoConjuge;
	}

	public void setIdInstituicaoConjuge(Long idInstituicaoConjuge) {
		this.idInstituicaoConjuge = idInstituicaoConjuge;
	}

	@Override
	public Long getId() {
		return getIdPessoaFisica();
	}
}
