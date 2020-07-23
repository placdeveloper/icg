package br.com.sicoob.icg.util {
		
	public class ConstantesUtil
	{
				public static const TIPO_OPERACAO_INCLUSAO:int = 1;
		public static const TIPO_OPERACAO_ALTERACAO:int = 2;
		public static const TIPO_OPERACAO_EXCLUSAO:int = 3;
		
		public static var ITENS_TIPO_OPERACAO_HISTORICO:Array = [
			{label:"Inclusão", data:TIPO_OPERACAO_INCLUSAO},
			{label:"Alteração", data:TIPO_OPERACAO_ALTERACAO},
			{label:"Exclusão", data:TIPO_OPERACAO_EXCLUSAO}];
		
	}
}
