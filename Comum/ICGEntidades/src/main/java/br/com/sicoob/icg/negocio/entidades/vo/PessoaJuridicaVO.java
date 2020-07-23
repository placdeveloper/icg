package br.com.sicoob.icg.negocio.entidades.vo;

import br.com.sicoob.icg.negocio.enums.TipoPessoaEnum;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class PessoaJuridicaVO extends PessoaBasicaVO {

	private Integer id;

	private String dataConstituicao;

	private String valorCapitalSocial;

	private String inscricaoEstadual;
	
	private String inscricaoMunicipal;

	private String codigoEsferaAdministrativa;
	
	private String numeroRegistroJuntaComercial;

	private String dataRegistroJuntaComercial;

	private String numeroUltimaAlteracaoContratoSocial;

	private String dataUltimaAlteracaoContratoSocial;

	private String numeroRegistroRepresentacao;

	private String dataRegistroRepresentacao;

	private String codigoTipoEmpresa;
	
	private String descricaoTipoEmpresa;

	private String codigoTipoFormaConstituicao;
	
	private String descricaoTipoFormaConstituicao;
	
	private String importador;
	
	private String exportador;
	
	public String getCodigoTipoPessoaJuridica() {
		return TipoPessoaEnum.PESSOA_JURIDICA.getCodigo().toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataConstituicao() {
		return dataConstituicao;
	}

	public void setDataConstituicao(String dataConstituicao) {
		this.dataConstituicao = dataConstituicao;
	}

	public String getValorCapitalSocial() {
		return valorCapitalSocial;
	}

	public void setValorCapitalSocial(String valorCapitalSocial) {
		this.valorCapitalSocial = valorCapitalSocial;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getCodigoEsferaAdministrativa() {
		return codigoEsferaAdministrativa;
	}

	public void setCodigoEsferaAdministrativa(String codigoEsferaAdministrativa) {
		this.codigoEsferaAdministrativa = codigoEsferaAdministrativa;
	}

	public String getNumeroRegistroJuntaComercial() {
		return numeroRegistroJuntaComercial;
	}

	public void setNumeroRegistroJuntaComercial(String numeroRegistroJuntaComercial) {
		this.numeroRegistroJuntaComercial = numeroRegistroJuntaComercial;
	}

	public String getDataRegistroJuntaComercial() {
		return dataRegistroJuntaComercial;
	}

	public void setDataRegistroJuntaComercial(String dataRegistroJuntaComercial) {
		this.dataRegistroJuntaComercial = dataRegistroJuntaComercial;
	}

	public String getNumeroUltimaAlteracaoContratoSocial() {
		return numeroUltimaAlteracaoContratoSocial;
	}

	public void setNumeroUltimaAlteracaoContratoSocial(String numeroUltimaAlteracaoContratoSocial) {
		this.numeroUltimaAlteracaoContratoSocial = numeroUltimaAlteracaoContratoSocial;
	}

	public String getDataUltimaAlteracaoContratoSocial() {
		return dataUltimaAlteracaoContratoSocial;
	}

	public void setDataUltimaAlteracaoContratoSocial(String dataUltimaAlteracaoContratoSocial) {
		this.dataUltimaAlteracaoContratoSocial = dataUltimaAlteracaoContratoSocial;
	}

	public String getNumeroRegistroRepresentacao() {
		return numeroRegistroRepresentacao;
	}

	public void setNumeroRegistroRepresentacao(String numeroRegistroRepresentacao) {
		this.numeroRegistroRepresentacao = numeroRegistroRepresentacao;
	}

	public String getDataRegistroRepresentacao() {
		return dataRegistroRepresentacao;
	}

	public void setDataRegistroRepresentacao(String dataRegistroRepresentacao) {
		this.dataRegistroRepresentacao = dataRegistroRepresentacao;
	}

	public String getCodigoTipoEmpresa() {
		return codigoTipoEmpresa;
	}

	public void setCodigoTipoEmpresa(String codigoTipoEmpresa) {
		this.codigoTipoEmpresa = codigoTipoEmpresa;
	}

	public String getDescricaoTipoEmpresa() {
		return descricaoTipoEmpresa;
	}

	public void setDescricaoTipoEmpresa(String descricaoTipoEmpresa) {
		this.descricaoTipoEmpresa = descricaoTipoEmpresa;
	}

	public String getCodigoTipoFormaConstituicao() {
		return codigoTipoFormaConstituicao;
	}

	public void setCodigoTipoFormaConstituicao(String codigoTipoFormaConstituicao) {
		this.codigoTipoFormaConstituicao = codigoTipoFormaConstituicao;
	}

	public String getDescricaoTipoFormaConstituicao() {
		return descricaoTipoFormaConstituicao;
	}

	public void setDescricaoTipoFormaConstituicao(String descricaoTipoFormaConstituicao) {
		this.descricaoTipoFormaConstituicao = descricaoTipoFormaConstituicao;
	}

	public String getImportador() {
		return importador;
	}

	public void setImportador(String importador) {
		this.importador = importador;
	}

	public String getExportador() {
		return exportador;
	}

	public void setExportador(String exportador) {
		this.exportador = exportador;
	}
	
}
