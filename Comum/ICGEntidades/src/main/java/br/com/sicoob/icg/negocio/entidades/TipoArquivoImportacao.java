package br.com.sicoob.icg.negocio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Entity
@Table(name = "TIPOARQUIVOIMPORTACAO", schema = "ICG")
public class TipoArquivoImportacao extends BancoobEntidade implements Serializable {

	private static final long serialVersionUID = -3394562307561208426L;

	@Id
	@Column(name = "CODSITUACAOPROCESSAMENTO")
	private String codigoSituacaoProcessamento;

	@Column(name = "DESCSITUACAOPROCESSAMENTO")
	private String descricaoSituacaoProcessamento;

	public String getCodigoSituacaoProcessamento() {
		return codigoSituacaoProcessamento;
	}

	public void setCodigoSituacaoProcessamento(String codigoSituacaoProcessamento) {
		this.codigoSituacaoProcessamento = codigoSituacaoProcessamento;
	}

	public String getDescricaoSituacaoProcessamento() {
		return descricaoSituacaoProcessamento;
	}

	public void setDescricaoSituacaoProcessamento(String descricaoSituacaoProcessamento) {
		this.descricaoSituacaoProcessamento = descricaoSituacaoProcessamento;
	}
}
