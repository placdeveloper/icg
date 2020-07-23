package br.com.sicoob.icg.enums
{
	import flash.utils.IDataInput;
	import flash.utils.IExternalizable;
	
	import org.granite.util.Enum;
	
	[Bindable]
	[RemoteClass(alias="br.com.sicoob.icg.negocio.enums.TipoExtensaoArquivoEnum")]
	public class TipoExtensaoArquivoEnum extends Enum implements IExternalizable {
		public static const CSV	: TipoExtensaoArquivoEnum	= new TipoExtensaoArquivoEnum("CSV", ".csv", _); 
		public static const JSON : TipoExtensaoArquivoEnum 	= new TipoExtensaoArquivoEnum("JSON", ".json", _); 
		
		private var _descricao:String;
		
		function TipoExtensaoArquivoEnum(name:String = null, descricao:String = null, restrictor:* = null) {
			super((name || CSV.name), restrictor);
			if (restrictor != null) {
				this._descricao = descricao;
			}
		}
		
		public function get descricao():String {
			return _descricao;
		}
		
		public static function get constants():Array {
			return [CSV, JSON];
		}
		
		protected override function getConstants():Array {
			return constants;
		}
		
		public override function readExternal(input:IDataInput):void {
			super.readExternal(input);
			var constantObject:TipoExtensaoArquivoEnum = valueOf(name);
			_descricao = constantObject.descricao;
		}
		
		public static function valueOf(name:String):TipoExtensaoArquivoEnum {
			return TipoExtensaoArquivoEnum(CSV.constantOf(name));
		}
		
		public static function obterPorDescricao(descricao:String) : TipoExtensaoArquivoEnum {
			if (descricao != null) {
				for each (var enums:TipoExtensaoArquivoEnum in constants) 
				{
					if (enums._descricao == descricao) {
						return enums;
					}
				}
			}
			return null;
		}
		
	}
}