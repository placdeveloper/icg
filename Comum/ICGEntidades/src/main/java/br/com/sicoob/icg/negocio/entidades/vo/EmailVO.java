package br.com.sicoob.icg.negocio.entidades.vo;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class EmailVO extends PessoaBasicaVO {

	private Long id;

	private String codigoTipoEmail;

	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoTipoEmail() {
		return codigoTipoEmail;
	}

	public void setCodigoTipoEmail(String codigoTipoEmail) {
		this.codigoTipoEmail = codigoTipoEmail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
