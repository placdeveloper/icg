package br.com.sicoob.icg.enums
{
	import flash.utils.IDataInput;
	import flash.utils.IExternalizable;
	
	import org.granite.util.Enum;
	
	[Bindable]
	[RemoteClass(alias="br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum")]
	public class TipoAtualizacaoEnum extends Enum implements IExternalizable {
		public static const PESSOAFISICA	: TipoAtualizacaoEnum	= new TipoAtualizacaoEnum("PESSOAFISICA", "1", "Pessoa Física", _); 
		public static const ENDERECO 		: TipoAtualizacaoEnum 	= new TipoAtualizacaoEnum("ENDERECO", "2", "Endereço", _); 
		public static const EMAIL 			: TipoAtualizacaoEnum 	= new TipoAtualizacaoEnum("EMAIL", "6", "Email", _); 
		public static const TELEFONE 		: TipoAtualizacaoEnum 	= new TipoAtualizacaoEnum("TELEFONE", "3", "Telefone", _);
		public static const FONTERENDA 			: TipoAtualizacaoEnum 	= new TipoAtualizacaoEnum("FONTERENDA", "4", "Fonte Renda", _);
		public static const PESSOAJURIDICA 	: TipoAtualizacaoEnum 	= new TipoAtualizacaoEnum("PESSOAJURIDICA", "5", "Pessoa Jurídica", _);
		
		private var _codigo:String;
		private var _descricao:String;
		
		function TipoAtualizacaoEnum(name:String = null, codigo:String = null, descricao:String = null, restrictor:* = null) {
			super((name || PESSOAFISICA.name), restrictor);
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
			return [ENDERECO, EMAIL, PESSOAFISICA, PESSOAJURIDICA, FONTERENDA, TELEFONE];
		}
		
		protected override function getConstants():Array {
			return constants;
		}
		
		public override function readExternal(input:IDataInput):void {
			super.readExternal(input);
			var constantObject:TipoAtualizacaoEnum = valueOf(name);
			_codigo = constantObject.codigo;
			_descricao = constantObject.descricao;
		}
		
		public static function valueOf(name:String):TipoAtualizacaoEnum {
			return TipoAtualizacaoEnum(PESSOAFISICA.constantOf(name));
		}
		
		public static function obterPorCodigo(codigo:String) : TipoAtualizacaoEnum {
			if (codigo != null) {
				for each (var enums:TipoAtualizacaoEnum in constants) 
				{
					if (enums._codigo == codigo) {
						return enums;
					}
				}
			}
			return null;
		}
		
	}
}