package br.com.sicoob.icg.processamento.negocio.enums;

public enum ConstanteComumEnum {

	TAMANHO_CNPJ (14),
	TAMANHO_CODIGO_TIPO_PESSOA (1),
	TAMANHO_CPF (11),
	TAMANHO_CPF_CNPJ (14),
	TAMANHO_CODATIVIDADEECONOMICA (5);
	
	private Integer codigo;

	private ConstanteComumEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public static ConstanteComumEnum obterPorCodigo(Integer codigo) {
		for (ConstanteComumEnum constanteEnderecoEnum : values()) {
			if (constanteEnderecoEnum.codigo.equals(codigo)) {
				return constanteEnderecoEnum;
			}
		}
		return null;
	}

}