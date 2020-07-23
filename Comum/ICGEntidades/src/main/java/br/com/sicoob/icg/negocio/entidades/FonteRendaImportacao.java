package br.com.sicoob.icg.negocio.entidades;

import java.math.BigDecimal;

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
@Table(name = "FONTERENDAIMPORTACAO", schema = "ICG")
public class FonteRendaImportacao extends ComumImportacao<Long> {

	private static final long serialVersionUID = 6331036558901432602L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDFONTERENDA")
	private Long idFonteRenda;

	@Column(name = "CODTIPOFONTERENDA")
	private String codigoTipoFonteRenda;

	@Column(name = "BOLRENDAFIXA")
	private Long rendaFixa;

	@Column(name = "VALORRECEITABRUTAMENSAL")
	private BigDecimal valorReceitaBrutaMensal;

	@Column(name = "BOLSIMPLESNACIONAL")
	private Long simplesNacional;

	@Column(name = "DATAVALIDADERENDA")
	private DateTimeDB dataValidadeRenda;

	public Long getIdFonteRenda() {
		return idFonteRenda;
	}

	public void setIdFonteRenda(Long idFonteRenda) {
		this.idFonteRenda = idFonteRenda;
	}

	public String getCodigoTipoFonteRenda() {
		return codigoTipoFonteRenda;
	}

	public void setCodigoTipoFonteRenda(String codigoTipoFonteRenda) {
		this.codigoTipoFonteRenda = codigoTipoFonteRenda;
	}

	public Long getRendaFixa() {
		return rendaFixa;
	}

	public void setRendaFixa(Long rendaFixa) {
		this.rendaFixa = rendaFixa;
	}

	public BigDecimal getValorReceitaBrutaMensal() {
		return valorReceitaBrutaMensal;
	}

	public void setValorReceitaBrutaMensal(BigDecimal valorReceitaBrutaMensal) {
		this.valorReceitaBrutaMensal = valorReceitaBrutaMensal;
	}

	public Long getSimplesNacional() {
		return simplesNacional;
	}

	public void setSimplesNacional(Long simplesNacional) {
		this.simplesNacional = simplesNacional;
	}

	public DateTimeDB getDataValidadeRenda() {
		return dataValidadeRenda;
	}

	public void setDataValidadeRenda(DateTimeDB dataValidadeRenda) {
		this.dataValidadeRenda = dataValidadeRenda;
	}

	@Override
	public Long getId() {
		return getIdFonteRenda();
	}
}
