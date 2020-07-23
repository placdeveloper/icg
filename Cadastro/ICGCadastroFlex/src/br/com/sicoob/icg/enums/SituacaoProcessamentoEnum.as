package br.com.sicoob.icg.enums
{
	import flash.utils.IDataInput;
	import flash.utils.IExternalizable;
	
	import org.granite.util.Enum;
	
	[Bindable]
	[RemoteClass(alias="br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum")]
	public class SituacaoProcessamentoEnum extends Enum implements IExternalizable {
		public static const A_INICIAR : SituacaoProcessamentoEnum 				= new SituacaoProcessamentoEnum("A_INICIAR", "1", "A iniciar", _); 
		public static const EM_PROCESSAMENTO : SituacaoProcessamentoEnum 		= new SituacaoProcessamentoEnum("EM_PROCESSAMENTO", "2", "Em processamento", _); 
		public static const PROCESSADO : SituacaoProcessamentoEnum 				= new SituacaoProcessamentoEnum("PROCESSADO", "3", "Processado", _);
		public static const PROCESSADO_PARCIALMENTE : SituacaoProcessamentoEnum	= new SituacaoProcessamentoEnum("PROCESSADO_PARCIALMENTE", "4", "Processado parcialmente", _);
		public static const ERRO_VALIDACAO : SituacaoProcessamentoEnum 			= new SituacaoProcessamentoEnum("ERRO_VALIDACAO", "5", "Erro de validação", _);
		public static const VALIDADO_PARCIALMENTE : SituacaoProcessamentoEnum   = new SituacaoProcessamentoEnum("VALIDADO_PARCIALMENTE", "6", "Validado parcialmente", _);
		public static const ARQUIVO_VALIDADO : SituacaoProcessamentoEnum   		= new SituacaoProcessamentoEnum("ARQUIVO_VALIDADO", "7", "Arquivo Validado", _);
		public static const ERRO_PROCESSAMENTO : SituacaoProcessamentoEnum   	= new SituacaoProcessamentoEnum("ERRO_PROCESSAMENTO", "8", "Erro de processamento", _);
		
		private var _codigo:String;
		private var _descricao:String;
		
		function SituacaoProcessamentoEnum(name:String = null, codigo:String = null, descricao:String = null, restrictor:* = null) {
			super((name || A_INICIAR.name), restrictor);
			if (restrictor != null) {
				this._codigo = codigo;
				this._descricao = descricao;
			}
		}
		
		public function get codigo():String {
			return _codigo;
		}
		
		public function get descricao():String {
			return _descricao;
		}
		
		public static function get constants():Array {
			return [A_INICIAR, EM_PROCESSAMENTO, PROCESSADO, PROCESSADO_PARCIALMENTE, ERRO_VALIDACAO, VALIDADO_PARCIALMENTE, ARQUIVO_VALIDADO, ERRO_PROCESSAMENTO];
		}
		
		protected override function getConstants():Array {
			return constants;
		}
		
		public override function readExternal(input:IDataInput):void {
			super.readExternal(input);
			var constantObject:SituacaoProcessamentoEnum = valueOf(name);
			_codigo = constantObject.codigo;
			_descricao = constantObject.descricao;
		}
		
		public static function valueOf(name:String):SituacaoProcessamentoEnum {
			return SituacaoProcessamentoEnum(A_INICIAR.constantOf(name));
		}
	}
}