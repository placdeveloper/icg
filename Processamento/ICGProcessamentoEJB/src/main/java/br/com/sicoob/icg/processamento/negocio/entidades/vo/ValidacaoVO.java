package br.com.sicoob.icg.processamento.negocio.entidades.vo;

public class ValidacaoVO {

	private StringBuilder erroValidacao = new StringBuilder();
	private Boolean houveInclusao = Boolean.FALSE;
	private Boolean registroValido = Boolean.TRUE;

	public StringBuilder getErroValidacao() {
		return erroValidacao;
	}

	public void setErroValidacao(StringBuilder erroValidacao) {
		this.erroValidacao = erroValidacao;
	}

	public Boolean getHouveInclusao() {
		return houveInclusao;
	}

	public void setHouveInclusao(Boolean houveInclusao) {
		this.houveInclusao = houveInclusao;
	}

	public Boolean getRegistroValido() {
		return registroValido;
	}

	public void setRegistroValido(Boolean registroValido) {
		this.registroValido = registroValido;
	}
}
