package br.com.sicoob.icg.negocio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.bancoob.persistencia.types.DateTimeDB;

/**
 * 
 * @author Pablo.Andrade
 *
 * @param <ID>
 */
@MappedSuperclass
public abstract class ComumImportacao<ID extends Serializable> extends BancoobEntidade {

	private static final long serialVersionUID = 4148391073417176322L;

	@JoinColumn(name = "idImportaArquivo")
	@ManyToOne
	private ImportaArquivo importaArquivo;

	@Column(name = "NUMCPFCNPJ")
	private String numeroCpfCnpj;

	@Column(name = "CODTIPOPESSOA")
	private String codigoTipoPessoa;

	@Column(name = "IDINSTITUICAO")
	private Integer idInstituicao;

	@Column(name = "CODSITUACAOPROCESSAMENTO")
	private String codSituacaoProcessamento;

	@Column(name = "DATAHORAPROCESSADO")
	private DateTimeDB dataHoraProcessamento;

	@Column(name = "DESCERROPROCESSAMENTO")
	private String descErroProcessamento;

	@Column(name = "DESCLINHAARQUIVO")
	private String descLinhaArquivo;
	
	public abstract ID getId();

	public String getNumeroCpfCnpj() {
		return numeroCpfCnpj;
	}

	public void setNumeroCpfCnpj(String numeroCpfCnpj) {
		this.numeroCpfCnpj = numeroCpfCnpj;
	}

	public String getCodigoTipoPessoa() {
		return codigoTipoPessoa;
	}

	public void setCodigoTipoPessoa(String codigoTipoPessoa) {
		this.codigoTipoPessoa = codigoTipoPessoa;
	}

	public Integer getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Integer idInstituicao) {
		this.idInstituicao = idInstituicao;
	}

	public String getCodSituacaoProcessamento() {
		return codSituacaoProcessamento;
	}

	public void setCodSituacaoProcessamento(String codSituacaoProcessamento) {
		this.codSituacaoProcessamento = codSituacaoProcessamento;
	}

	public DateTimeDB getDataHoraProcessamento() {
		return dataHoraProcessamento;
	}

	public void setDataHoraProcessamento(DateTimeDB dataHoraProcessamento) {
		this.dataHoraProcessamento = dataHoraProcessamento;
	}

	public String getDescErroProcessamento() {
		return descErroProcessamento;
	}

	public void setDescErroProcessamento(String descErroProcessamento) {
		this.descErroProcessamento = descErroProcessamento;
	}

	public String getDescLinhaArquivo() {
		return descLinhaArquivo;
	}

	public void setDescLinhaArquivo(String descLinhaArquivo) {
		this.descLinhaArquivo = descLinhaArquivo;
	}

	public ImportaArquivo getImportaArquivo() {
		return importaArquivo;
	}

	public void setImportaArquivo(ImportaArquivo importaArquivo) {
		this.importaArquivo = importaArquivo;
	}

}
