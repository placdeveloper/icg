package br.com.sicoob.icg.processamento.negocio.validacao.util;

public interface Validacao {

	/**
	 * 
	 * @param valor - informação a ser validada
	 * @param linhaArquivo - número da linha ou objto do arquivo
	 * @param campo - nome do campo
	 * @param tamanhoCampoPermitido - tamnaho do campo permitido
	 * @return
	 */
	
	public StringBuilder retornaValidacao(String valor, String linhaArquivo, String campo, Integer tamanhoCampoPermitido, Boolean obrigatorio);
}
