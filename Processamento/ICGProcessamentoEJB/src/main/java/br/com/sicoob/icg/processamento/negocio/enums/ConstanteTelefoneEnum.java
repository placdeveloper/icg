package br.com.sicoob.icg.processamento.negocio.enums;

public enum ConstanteTelefoneEnum {

	QTD_CAMPOS_OBRIGATORIOS (7),
	TAMANHO_CPFCNPJ (14),
	TAMANHO_CODTIPOPESSOA (1),
	TAMANHO_CODTIPOTELEFONE (5),
	TAMANHO_NUMTELEFONE (12),
	TAMANHO_NUMDDD (2),
	TAMANHO_NUMRAMAL (4),
	TAMANHO_DESCOBSERVACAO (100),

	POSICAO_CAMPO_CPFCNPJ (0),
	POSICAO_CAMPO_CODTIPOPESSOA (1),
	POSICAO_CAMPO_CODTIPOTELEFONE (2),
	POSICAO_CAMPO_NUMDDD (3),
	POSICAO_CAMPO_NUMTELEFONE (4),
	POSICAO_CAMPO_NUMRAMAL (5),
	POSICAO_CAMPO_DESCOBSERVACAO (6);

	private Integer codigo;

	private ConstanteTelefoneEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public static ConstanteTelefoneEnum obterPorCodigo(Integer codigo) {
		for (ConstanteTelefoneEnum constanteEnderecoEnum : values()) {
			if (constanteEnderecoEnum.codigo.equals(codigo)) {
				return constanteEnderecoEnum;
			}
		}
		return null;
	}

}