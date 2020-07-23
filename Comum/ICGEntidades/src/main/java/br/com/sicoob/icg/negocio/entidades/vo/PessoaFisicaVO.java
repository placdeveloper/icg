package br.com.sicoob.icg.negocio.entidades.vo;

import br.com.sicoob.icg.negocio.enums.TipoPessoaEnum;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class PessoaFisicaVO extends PessoaBasicaVO {

	private Integer id;

	private String menorEmancipado;

	private String dataNascimento;

	private String nomePai;

	private String nomeMae;

	private String codigoTipoDocumento;

	private String numeroDocumento;

	private String orgaoExpedidorDocumento;

	private String ufOrgaoExpedidorDocumento;

	private String dataEmissaoDocumento;

	private String tipoSexo;

	private String codigoOcupacaoProfissional;

	private String codigoEstadoCivil;

	private String codigoRegimeCasamento;

	private String quantidadeDependentes;

	private String uniaoEstavel;

	private String codigoGrauInstrucao;

	private String descNaturalidade;

	private String codigoNaturalidade;

	private String codigoVinculoEmpregaticio;

	private String codigoNacionalidade;

	private Integer idPessoaConjuge;

	private Integer idInstituicaoConjuge;

	private String cpfConjuge;

	private String simplesNacional;
	
	private String valorReceitaBrutaMensal;


	public String getCodigoTipoPessoaFisica() {
		return TipoPessoaEnum.PESSOA_FISICA.getCodigo().toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenorEmancipado() {
		return menorEmancipado;
	}

	public void setMenorEmancipado(String menorEmancipado) {
		this.menorEmancipado = menorEmancipado;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}

	public void setCodigoTipoDocumento(String codigoTipoDocumento) {
		this.codigoTipoDocumento = codigoTipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getOrgaoExpedidorDocumento() {
		return orgaoExpedidorDocumento;
	}

	public void setOrgaoExpedidorDocumento(String orgaoExpedidorDocumento) {
		this.orgaoExpedidorDocumento = orgaoExpedidorDocumento;
	}

	public String getUfOrgaoExpedidorDocumento() {
		return ufOrgaoExpedidorDocumento;
	}

	public void setUfOrgaoExpedidorDocumento(String ufOrgaoExpedidorDocumento) {
		this.ufOrgaoExpedidorDocumento = ufOrgaoExpedidorDocumento;
	}

	public String getDataEmissaoDocumento() {
		return dataEmissaoDocumento;
	}

	public void setDataEmissaoDocumento(String dataEmissaoDocumento) {
		this.dataEmissaoDocumento = dataEmissaoDocumento;
	}

	public String getTipoSexo() {
		return tipoSexo;
	}

	public void setTipoSexo(String tipoSexo) {
		this.tipoSexo = tipoSexo;
	}

	public String getCodigoOcupacaoProfissional() {
		return codigoOcupacaoProfissional;
	}

	public void setCodigoOcupacaoProfissional(String codigoOcupacaoProfissional) {
		this.codigoOcupacaoProfissional = codigoOcupacaoProfissional;
	}

	public String getCodigoEstadoCivil() {
		return codigoEstadoCivil;
	}

	public void setCodigoEstadoCivil(String codigoEstadoCivil) {
		this.codigoEstadoCivil = codigoEstadoCivil;
	}

	public String getCodigoRegimeCasamento() {
		return codigoRegimeCasamento;
	}

	public void setCodigoRegimeCasamento(String codigoRegimeCasamento) {
		this.codigoRegimeCasamento = codigoRegimeCasamento;
	}

	public String getQuantidadeDependentes() {
		return quantidadeDependentes;
	}

	public void setQuantidadeDependentes(String quantidadeDependentes) {
		this.quantidadeDependentes = quantidadeDependentes;
	}

	public String getUniaoEstavel() {
		return uniaoEstavel;
	}

	public void setUniaoEstavel(String uniaoEstavel) {
		this.uniaoEstavel = uniaoEstavel;
	}

	public String getCodigoGrauInstrucao() {
		return codigoGrauInstrucao;
	}

	public void setCodigoGrauInstrucao(String codigoGrauInstrucao) {
		this.codigoGrauInstrucao = codigoGrauInstrucao;
	}

	public String getDescNaturalidade() {
		return descNaturalidade;
	}

	public void setDescNaturalidade(String descNaturalidade) {
		this.descNaturalidade = descNaturalidade;
	}

	public String getCodigoNaturalidade() {
		return codigoNaturalidade;
	}

	public void setCodigoNaturalidade(String codigoNaturalidade) {
		this.codigoNaturalidade = codigoNaturalidade;
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

	public Integer getIdPessoaConjuge() {
		return idPessoaConjuge;
	}

	public void setIdPessoaConjuge(Integer idPessoaConjuge) {
		this.idPessoaConjuge = idPessoaConjuge;
	}

	public Integer getIdInstituicaoConjuge() {
		return idInstituicaoConjuge;
	}

	public void setIdInstituicaoConjuge(Integer idInstituicaoConjuge) {
		this.idInstituicaoConjuge = idInstituicaoConjuge;
	}

	public String getCpfConjuge() {
		return cpfConjuge;
	}

	public void setCpfConjuge(String cpfConjuge) {
		this.cpfConjuge = cpfConjuge;
	}

	public String getSimplesNacional() {
		return simplesNacional;
	}

	public void setSimplesNacional(String simplesNacional) {
		this.simplesNacional = simplesNacional;
	}

	public String getValorReceitaBrutaMensal() {
		return valorReceitaBrutaMensal;
	}

	public void setValorReceitaBrutaMensal(String valorReceitaBrutaMensal) {
		this.valorReceitaBrutaMensal = valorReceitaBrutaMensal;
	}
	
}
