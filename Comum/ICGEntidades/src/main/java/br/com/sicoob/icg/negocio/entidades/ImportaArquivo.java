package br.com.sicoob.icg.negocio.entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.bancoob.persistencia.types.DateTimeDB;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Entity
@Table(name = "IMPORTAARQUIVO", schema = "ICG")
public class ImportaArquivo extends BancoobEntidade {

	private static final long serialVersionUID = 6881106918474394431L;

	@Column(name = "CODSITUACAOPROCESSAMENTO")
	private String codigoSituacaoProcessamento;

	@Column(name = "CODTIPOARQUIVOIMPORTACAO")
	private String codigoTipoArquivo;

	@Transient
	private DateTimeDB dataHoraImportacaoFiltroFim;

	@Transient
	private DateTimeDB dataHoraImportacaoFiltroInicio;

	@Column(name = "DATAHORAIMPORTACAO")
	private DateTimeDB dataImportacao;

	@Column(name = "DATAHORAPROCESSAMENTO")
	private DateTimeDB dataHoraProcessamento;

	@Column(name = "DESCERROPROCESSSAMENTO")
	private String descricaoErro;

	@Transient
	private String descricaoSituacaoProcessamento;

	@Transient
	private String descricaoTipoArquivo;

	@Column(name = "DESCDIRETORIO")
	private String diretorioArquivo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDIMPORTAARQUIVO")
	private Long idImportaArquivo;

	@Column(name = "IDINSTITUICAO")
	private Integer idInstituicao;

	@Transient
	private List<String> listaCodigoSituacaoProcessamento;

	@Column(name = "NOMEARQUIVO")
	private String nomeArquivo;

	@Column(name = "NOMEARQUIVODIRETORIO")
	private String nomeArquivoDiretorio;

	@Transient
	private String nomeInstituicao;

	@Transient
	private String nomeUsuarioImportacao;

	@Transient
	private SituacaoProcessamentoEnum situacaoProcessamentoEnum;

	@Transient
	private TipoAtualizacaoEnum tipoAtualizacaoEnum;

	@Column(name = "IDUSUARIO")
	private String usuarioResponsavel;

	public String getCodigoSituacaoProcessamento() {
		return codigoSituacaoProcessamento;
	}

	public void setCodigoSituacaoProcessamento(String codigoSituacaoProcessamento) {
		this.codigoSituacaoProcessamento = codigoSituacaoProcessamento;
	}

	public String getCodigoTipoArquivo() {
		return codigoTipoArquivo;
	}

	public void setCodigoTipoArquivo(String codigoTipoArquivo) {
		this.codigoTipoArquivo = codigoTipoArquivo;
	}

	public DateTimeDB getDataHoraImportacaoFiltroFim() {
		return dataHoraImportacaoFiltroFim;
	}

	public void setDataHoraImportacaoFiltroFim(DateTimeDB dataHoraImportacaoFiltroFim) {
		this.dataHoraImportacaoFiltroFim = dataHoraImportacaoFiltroFim;
	}

	public DateTimeDB getDataHoraImportacaoFiltroInicio() {
		return dataHoraImportacaoFiltroInicio;
	}

	public void setDataHoraImportacaoFiltroInicio(DateTimeDB dataHoraImportacaoFiltroInicio) {
		this.dataHoraImportacaoFiltroInicio = dataHoraImportacaoFiltroInicio;
	}

	public DateTimeDB getDataImportacao() {
		return dataImportacao;
	}

	public void setDataImportacao(DateTimeDB dataImportacao) {
		this.dataImportacao = dataImportacao;
	}

	public DateTimeDB getDataHoraProcessamento() {
		return dataHoraProcessamento;
	}

	public void setDataHoraProcessamento(DateTimeDB dataHoraProcessamento) {
		this.dataHoraProcessamento = dataHoraProcessamento;
	}

	public String getDescricaoErro() {
		return descricaoErro;
	}

	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = descricaoErro;
	}

	public String getDescricaoSituacaoProcessamento() {
		return descricaoSituacaoProcessamento;
	}

	public void setDescricaoSituacaoProcessamento(String descricaoSituacaoProcessamento) {
		this.descricaoSituacaoProcessamento = descricaoSituacaoProcessamento;
	}

	public String getDescricaoTipoArquivo() {
		return descricaoTipoArquivo;
	}

	public void setDescricaoTipoArquivo(String descricaoTipoArquivo) {
		this.descricaoTipoArquivo = descricaoTipoArquivo;
	}

	public String getDiretorioArquivo() {
		return diretorioArquivo;
	}

	public void setDiretorioArquivo(String diretorioArquivo) {
		this.diretorioArquivo = diretorioArquivo;
	}

	public Long getIdImportaArquivo() {
		return idImportaArquivo;
	}

	public void setIdImportaArquivo(Long id) {
		this.idImportaArquivo = id;
	}

	public Integer getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Integer idInstituicao) {
		this.idInstituicao = idInstituicao;
	}

	public List<String> getListaCodigoSituacaoProcessamento() {
		return listaCodigoSituacaoProcessamento;
	}

	public void setListaCodigoSituacaoProcessamento(List<String> listaCodigoSituacaoProcessamento) {
		this.listaCodigoSituacaoProcessamento = listaCodigoSituacaoProcessamento;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivoDiretorio() {
		return nomeArquivoDiretorio;
	}

	public void setNomeArquivoDiretorio(String nomeArquivoDiretorio) {
		this.nomeArquivoDiretorio = nomeArquivoDiretorio;
	}

	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}

	public String getNomeUsuarioImportacao() {
		return nomeUsuarioImportacao;
	}

	public void setNomeUsuarioImportacao(String nomeUsuarioImportacao) {
		this.nomeUsuarioImportacao = nomeUsuarioImportacao;
	}

	public SituacaoProcessamentoEnum getSituacaoProcessamentoEnum() {
		return situacaoProcessamentoEnum;
	}

	public void setSituacaoProcessamentoEnum(SituacaoProcessamentoEnum situacaoProcessamentoEnum) {
		this.situacaoProcessamentoEnum = situacaoProcessamentoEnum;
	}

	public TipoAtualizacaoEnum getTipoAtualizacaoEnum() {
		return tipoAtualizacaoEnum;
	}

	public void setTipoAtualizacaoEnum(TipoAtualizacaoEnum tipoAtualizacaoEnum) {
		this.tipoAtualizacaoEnum = tipoAtualizacaoEnum;
	}

	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

}
