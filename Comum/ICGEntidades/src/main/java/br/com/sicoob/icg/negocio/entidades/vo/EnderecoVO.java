package br.com.sicoob.icg.negocio.entidades.vo;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class EnderecoVO extends PessoaBasicaVO {

	public EnderecoVO() {
		this.complemento = "";
		this.numero = "";
	}

	private Long id;

	private String codigoTipoEndereco;

	private String cep;

	private String logradouro;

	private String complemento;

	private String bairro;

	private String numero;
	
	private String codigoLocalidade;

	private String codigoTipoLogradouro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCodigoTipoEndereco() {
		return codigoTipoEndereco;
	}

	public void setCodigoTipoEndereco(String codigoTipoEndereco) {
		this.codigoTipoEndereco = codigoTipoEndereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCodigoLocalidade() {
		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade) {
		this.codigoLocalidade = codigoLocalidade;
	}

	public String getCodigoTipoLogradouro() {
		return codigoTipoLogradouro;
	}

	public void setCodigoTipoLogradouro(String codigoTipoLogradouro) {
		this.codigoTipoLogradouro = codigoTipoLogradouro;
	}
}
