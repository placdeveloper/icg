package br.com.sicoob.icg.processamento.negocio.enums;

public enum ConstanteEmailEnum {

	QTD_CAMPOS_OBRIGARIOS (4),
	
	TAMANHO_CPFCNPJ (14),
	TAMANHO_CODTIPOEMAIL (5),
	TAMANHO_DESCEMAIL (100),

	POSICAO_CAMPO_CPFCNPJ (0),
	POSICAO_CAMPO_CODTIPOPESSOA (1),
	POSICAO_CAMPO_CODTIPOEMAIL (2),
	POSICAO_CAMPO_DESCEMAIL (3);

	private Integer codigo;

	private ConstanteEmailEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public static ConstanteEmailEnum obterPorCodigo(Integer codigo) {
		for (ConstanteEmailEnum constanteEnderecoEnum : values()) {
			if (constanteEnderecoEnum.codigo.equals(codigo)) {
				return constanteEnderecoEnum;
			}
		}
		return null;
	}

}