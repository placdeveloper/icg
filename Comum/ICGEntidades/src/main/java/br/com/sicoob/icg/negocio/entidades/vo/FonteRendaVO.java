package br.com.sicoob.icg.negocio.entidades.vo;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class FonteRendaVO extends PessoaBasicaVO {

	private Long id;

	private String rendaFixa;
	
	private String dataValidadeRenda;
	
	private String valorReceitaBrutaMensal;
	
	private String descricao;
	
	private String codigoTipoFonteRenda;
	
	private String descricaoTipoFonteRenda;
	
	private String idPessoaEmpregador;

	private String simplesNacional;

	private String bolPossuiAtivo;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRendaFixa() {
		return rendaFixa;
	}

	public void setRendaFixa(String rendaFixa) {
		this.rendaFixa = rendaFixa;
	}

	public String getDataValidadeRenda() {
		return dataValidadeRenda;
	}

	public void setDataValidadeRenda(String dataValidadeRenda) {
		this.dataValidadeRenda = dataValidadeRenda;
	}

	public String getValorReceitaBrutaMensal() {
		return valorReceitaBrutaMensal;
	}

	public void setValorReceitaBrutaMensal(String valorReceitaBrutaMensal) {
		this.valorReceitaBrutaMensal = valorReceitaBrutaMensal;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoTipoFonteRenda() {
		return codigoTipoFonteRenda;
	}

	public void setCodigoTipoFonteRenda(String codigoTipoFonteRenda) {
		this.codigoTipoFonteRenda = codigoTipoFonteRenda;
	}

	public String getDescricaoTipoFonteRenda() {
		return descricaoTipoFonteRenda;
	}

	public void setDescricaoTipoFonteRenda(String descricaoTipoFonteRenda) {
		this.descricaoTipoFonteRenda = descricaoTipoFonteRenda;
	}

	public String getIdPessoaEmpregador() {
		return idPessoaEmpregador;
	}

	public void setIdPessoaEmpregador(String idPessoaEmpregador) {
		this.idPessoaEmpregador = idPessoaEmpregador;
	}

	public String getBolSimplesNacional() {
		return simplesNacional;
	}

	public void setBolSimplesNacional(String bolSimplesNacional) {
		this.simplesNacional = bolSimplesNacional;
	}

	public String getBolPossuiAtivo() {
		return bolPossuiAtivo;
	}

	public void setBolPossuiAtivo(String bolPossuiAtivo) {
		this.bolPossuiAtivo = bolPossuiAtivo;
	}
}
