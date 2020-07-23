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
@Table(name = "SITUACAOPROCESSAMENTO", schema = "ICG")
public class SituacaoProcessamento  extends BancoobEntidade implements Serializable {

	private static final long serialVersionUID = 4735143456843251885L;

	@Id
	@Column(name = "CODSITUACAOPROCESSAMENTO")
	private Long codigosituacaoProcessamento;

	@Column(name = "DESCSITUACAOPROCESSAMENTO")
	private String descricaoSituacaoProcessamento;

	public Long getCodigosituacaoProcessamento() {
		return codigosituacaoProcessamento;
	}

	public void setCodigosituacaoProcessamento(Long codigosituacaoProcessamento) {
		this.codigosituacaoProcessamento = codigosituacaoProcessamento;
	}

	public String getDescricaoSituacaoProcessamento() {
		return descricaoSituacaoProcessamento;
	}

	public void setDescricaoSituacaoProcessamento(String descricaoSituacaoProcessamento) {
		this.descricaoSituacaoProcessamento = descricaoSituacaoProcessamento;
	}

}
