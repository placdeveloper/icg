package br.com.sicoob.icg.processamento.negocio.enums;

public enum ConstanteEnderecoEnum {

	QTD_CAMPOS_OBRIGATORIOS (10), 
	
	TAMANHO_CPF_CNPJ  (11),
	TAMANHO_CODTIPOENDERECO  (5),
	TAMANHO_CODLOCALIDADE  (10),
	TAMANHO_CODTIPOLOGRADOURO  (3),
	TAMANHO_CODCEP  (8),
	TAMANHO_DESCLOGRADOURO  (40),
	TAMANHO_DESCCOMPLEMENTO  (20),
	TAMANHO_NOMEBAIRRO  (30),
	TAMANHO_DESCNUMERO  (5),

	POSICAO_CAMPO_CPFCNPJ  (0),
	POSICAO_CAMPO_CODTIPOPESSOA  (1),
	POSICAO_CAMPO_CODCEP  (2),
	POSICAO_CAMPO_CODTIPOENDERECO  (3),
	POSICAO_CAMPO_CODTIPOLOCALIDADE  (4),
	POSICAO_CAMPO_CODTIPOLOGRADOURO  (5),
	POSICAO_CAMPO_NOMEBAIRRO  (6),
	POSICAO_CAMPO_DESCLOGRADOURO  (7),
	POSICAO_CAMPO_DESCNUMERO  (8),
	POSICAO_CAMPO_DESCCOMPLEMENTO  (9);

	private Integer codigo;

	private ConstanteEnderecoEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public static ConstanteEnderecoEnum obterPorCodigo(Integer codigo) {
		for (ConstanteEnderecoEnum constanteEnderecoEnum : values()) {
			if (constanteEnderecoEnum.codigo.equals(codigo)) {
				return constanteEnderecoEnum;
			}
		}
		return null;
	}

}