package br.com.sicoob.icg.negocio.entidades.vo;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class TelefoneVO extends PessoaBasicaVO {

	private Long id;

	private String ddd;

	private String telefone;

	private String ramal;	
	
	private String codigoTipoTelefone;

	private String descricaoTipoTelefone;

	private String descObservacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public String getCodigoTipoTelefone() {
		return codigoTipoTelefone;
	}

	public void setCodigoTipoTelefone(String codigoTipoTelefone) {
		this.codigoTipoTelefone = codigoTipoTelefone;
	}

	public String getDescricaoTipoTelefone() {
		return descricaoTipoTelefone;
	}

	public void setDescricaoTipoTelefone(String descricaoTipoTelefone) {
		this.descricaoTipoTelefone = descricaoTipoTelefone;
	}

	public String getDescObservacao() {
		return descObservacao;
	}

	public void setDescObservacao(String descObservacao) {
		this.descObservacao = descObservacao;
	}
}
