package br.com.sicoob.icg.negocio.enums;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public enum TipoDominioDownloadEnum {

	ATIVIDADE_ECONOMICA("ATIVIDADE ECONOMICA"),
	ESTADO_CIVIL("ESTADO CIVIL"),
	GRAU_INSTRUCAO("GRAU INSTRUÇÃO"),
	NACIONALIDADE("NACIONALIDADE"),
	ORGAO_EMISSAO_CERTIDAO("ORGÃO EMISSÃO CERTIDAO"),
	REGIME_CASAMENTO("REGIME CASAMENTO"),
	TIPO_CERTIDAO("TIPO CERTIDÃO"),
	TIPO_DOCUMENTO_IDENTIFICACAO("TIPO DOCUMENTO IDENTIFICAÇÃO"),
	TIPO_EMAIL("TIPO EMAIL"),
	TIPO_EMPRESA("TIPO EMPRESA"),
	TIPO_ENDERECO("TIPO ENDEREÇO"),
	TIPO_FONTE_RENDA("TIPO FONTE RENDA"),
	TIPO_FORMA_CONSTITUICAO("TIPO FORMA CONSTITUIÇÃO"),
	TIPO_PESSOA("TIPO PESSOA"),
	TIPO_RELACIONAMENTO("TIPO RELACIONAMENTO"),
	TIPO_TELEFONE("TIPO TELEFONE"),
	VINCULO_EMPREGATICIO("VINCULO EMPREGATÍCIO"); 

	private String descricao;

	private TipoDominioDownloadEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=" + name();
	}

	public static TipoDominioDownloadEnum getTipoDominio(String valor) {
		for (TipoDominioDownloadEnum dominio : TipoDominioDownloadEnum.values()) {
			if (dominio.getDescricao().equals(valor))
				return dominio;
		}
		return null;
	}
}
