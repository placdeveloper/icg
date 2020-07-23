package br.com.sicoob.icg.processamento.negocio.enums;

public enum ConstanteFonteRendaEnum {

	QTD_CAMPOS_OBRIGATORIOS (7),

	TAMANHO_CPFCNPJ (11),
	TAMANHO_CODTIPOFONTERENDA (5),
	TAMANHO_BOLRENDAFIXA (1),
	TAMANHO_BOLSIMPLESNACIONAL (1),
	TAMANHO_VALORRECEITABRUTAMENSAL (19),
	TAMANHO_DATA_VALIDADE (10),

	POSICAO_CAMPO_CPFCNPJ (0),
	POSICAO_CAMPO_CODTIPOPESSOA (1),
	POSICAO_CAMPO_CODTIPOFONTERENDA (2),
	POSICAO_CAMPO_BOLRENDAFIXA (3),
	POSICAO_CAMPO_BOLSIMPLESNACIONAL (4),
	POSICAO_CAMPO_DATAVALIDADERENDA (5),
	POSICAO_CAMPO_VALORRECEITABRUTAMENSAL (6);

	private Integer codigo;

	private ConstanteFonteRendaEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public static ConstanteFonteRendaEnum obterPorCodigo(Integer codigo) {
		for (ConstanteFonteRendaEnum constanteEnderecoEnum : values()) {
			if (constanteEnderecoEnum.codigo.equals(codigo)) {
				return constanteEnderecoEnum;
			}
		}
		return null;
	}

}