package br.com.sicoob.icg.negocio.entidades.vo;

import br.com.bancoob.persistencia.types.DateTimeDB;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class PessoaBasicaVO {

	private Integer idPessoa;

	private Integer idPessoaCompartilhamento;

	private String cpfCnpj;

	private String nomePessoa;

	private String nomeCompleto;
	
	private Integer instituicao;
	
	private String codigoAtividadeEconomica;

	private String codigoTipoPessoa;
	
	private String situacaoProcessamento;
	
	private DateTimeDB dataHoraProcessamento;

	private String erroProcessamento;	
	
	private String linhaArquivo;
	
	private ImportaArquivo importaArquivo;
	
	private Boolean verificarAutorizacao;
	
	private Integer idUnidadeInst;
	
	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Integer getIdPessoaCompartilhamento() {
		return idPessoaCompartilhamento;
	}
	
	public void setIdPessoaCompartilhamento(Integer idPessoaCompartilhamento) {
		this.idPessoaCompartilhamento = idPessoaCompartilhamento;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
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

	public void setNomeCompleto(String nome) {
		this.nomeCompleto = nome;
	}
	
	public Integer getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Integer instituicao) {
		this.instituicao = instituicao;
	}

	public String getCodigoAtividadeEconomica() {
		return codigoAtividadeEconomica;
	}

	public void setCodigoAtividadeEconomica(String codigoAtividadeEconomica) {
		this.codigoAtividadeEconomica = codigoAtividadeEconomica;
	}

	public String getCodigoTipoPessoa() {
		return codigoTipoPessoa;
	}

	public void setCodigoTipoPessoa(String codigoTipoPessoa) {
		this.codigoTipoPessoa = codigoTipoPessoa;
	}

	public String getSituacaoProcessamento() {
		return situacaoProcessamento;
	}

	public void setSituacaoProcessamento(String situacaoProcessamento) {
		this.situacaoProcessamento = situacaoProcessamento;
	}

	public DateTimeDB getDataHoraProcessamento() {
		return dataHoraProcessamento;
	}

	public void setDataHoraProcessamento(DateTimeDB dataHoraProcessamento) {
		this.dataHoraProcessamento = dataHoraProcessamento;
	}

	public String getErroProcessamento() {
		return erroProcessamento;
	}

	public void setErroProcessamento(String erroProcessamento) {
		this.erroProcessamento = erroProcessamento;
	}

	public String getLinhaArquivo() {
		return linhaArquivo;
	}

	public void setLinhaArquivo(String linhaArquivo) {
		this.linhaArquivo = linhaArquivo;
	}

	public ImportaArquivo getImportaArquivo() {
		return importaArquivo;
	}

	public void setImportaArquivo(ImportaArquivo importaArquivo) {
		this.importaArquivo = importaArquivo;
	}

	public Boolean getVerificarAutorizacao() {
		return verificarAutorizacao;
	}

	public void setVerificarAutorizacao(Boolean verificarAutorizacao) {
		this.verificarAutorizacao = verificarAutorizacao;
	}

	public Integer getIdUnidadeInst() {
		return idUnidadeInst;
	}

	public void setIdUnidadeInst(Integer idUnidadeInst) {
		this.idUnidadeInst = idUnidadeInst;
	}
	
}
