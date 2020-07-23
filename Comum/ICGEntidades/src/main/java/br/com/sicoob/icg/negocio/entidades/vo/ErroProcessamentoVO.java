package br.com.sicoob.icg.negocio.entidades.vo;

import br.com.bancoob.persistencia.types.DateTimeDB;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class ErroProcessamentoVO {

	private String pessoa;
	private String situacaoProcessamento;
	private DateTimeDB dataProcessamento;
	private String descricaoErro;
	private String linhaArquivo;

	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}
	public String getSituacaoProcessamento() {
		return situacaoProcessamento;
	}
	public void setSituacaoProcessamento(String situacaoProcessamento) {
		this.situacaoProcessamento = situacaoProcessamento;
	}
	public DateTimeDB getDataProcessamento() {
		return dataProcessamento;
	}
	public void setDataProcessamento(DateTimeDB dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}
	public String getDescricaoErro() {
		return descricaoErro;
	}
	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = descricaoErro;
	}
	public String getLinhaArquivo() {
		return linhaArquivo;
	}
	public void setLinhaArquivo(String linhaArquivo) {
		this.linhaArquivo = linhaArquivo;
	}
}
