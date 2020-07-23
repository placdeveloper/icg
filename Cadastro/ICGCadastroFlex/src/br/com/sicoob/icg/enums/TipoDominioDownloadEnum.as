package br.com.sicoob.icg.enums
{
	import flash.utils.IDataInput;
	import flash.utils.IExternalizable;
	
	import org.granite.util.Enum;
	
	[Bindable]
	[RemoteClass(alias="br.com.sicoob.icg.negocio.enums.TipoDominioDownloadEnum")]
	public class TipoDominioDownloadEnum extends Enum implements IExternalizable {
		public static const ATIVIDADE_ECONOMICA	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("ATIVIDADE_ECONOMICA", "ATIVIDADE ECONOMICA", _);
		public static const ESTADO_CIVIL	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("ESTADO_CIVIL", "ESTADO CIVIL", _);
		public static const GRAU_INSTRUCAO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("GRAU_INSTRUCAO", "GRAU INSTRUÇÃO", _);
		public static const NACIONALIDADE	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("NACIONALIDADE", "NACIONALIDADE", _);
		public static const ORGAO_EMISSAO_CERTIDAO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("ORGAO_EMISSAO_CERTIDAO", "ORGÃO EMISSÃO CERTIDAO", _);
		public static const REGIME_CASAMENTO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("REGIME_CASAMENTO", "REGIME CASAMENTO", _);
		public static const TIPO_PESSOA	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_PESSOA", "TIPO PESSOA", _);
		public static const TIPO_FORMA_CONSTITUICAO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_FORMA_CONSTITUICAO", "TIPO FORMA CONSTITUIÇÃO", _);
		public static const TIPO_FONTE_RENDA	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_FONTE_RENDA", "TIPO FONTE RENDA", _);
		public static const TIPO_ENDERECO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_ENDERECO", "TIPO ENDEREÇO", _);
		public static const TIPO_EMPRESA	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_EMPRESA", "TIPO EMPRESA", _);
		public static const TIPO_EMAIL	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_EMAIL", "TIPO EMAIL", _);
		public static const TIPO_DOCUMENTO_IDENTIFICACAO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_DOCUMENTO_IDENTIFICACAO", "TIPO DOCUMENTO IDENTIFICAÇÃO", _);
		public static const TIPO_CERTIDAO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_CERTIDAO", "TIPO CERTIDÃO", _);
		public static const TIPO_RELACIONAMENTO	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_RELACIONAMENTO", "TIPO RELACIONAMENTO", _);
		public static const TIPO_TELEFONE	: TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("TIPO_TELEFONE", "TIPO TELEFONE", _);
		public static const VINCULO_EMPREGATICIO : TipoDominioDownloadEnum	= new TipoDominioDownloadEnum("VINCULO_EMPREGATICIO", "VINCULO EMPREGATÍCIO", _);
		
		
		private var _descricao:String;
		
		function TipoDominioDownloadEnum(name:String = null, descricao:String = null, restrictor:* = null) {
			super((name || ATIVIDADE_ECONOMICA.name), restrictor);
			if (restrictor != null) {
				this._descricao = descricao;
			}
		}
		
		public function get descricao():String {
			return _descricao;
		}
		
		public static function get constants():Array {
			return [ATIVIDADE_ECONOMICA, ESTADO_CIVIL, GRAU_INSTRUCAO, NACIONALIDADE, ORGAO_EMISSAO_CERTIDAO, REGIME_CASAMENTO, TIPO_PESSOA, TIPO_FORMA_CONSTITUICAO, TIPO_FONTE_RENDA, TIPO_ENDERECO, TIPO_EMPRESA, TIPO_EMAIL, TIPO_DOCUMENTO_IDENTIFICACAO, TIPO_CERTIDAO, TIPO_RELACIONAMENTO, TIPO_TELEFONE, VINCULO_EMPREGATICIO];
		}
		
		protected override function getConstants():Array {
			return constants;
		}
		
		public override function readExternal(input:IDataInput):void {
			super.readExternal(input);
			var constantObject:TipoDominioDownloadEnum = valueOf(name);
			_descricao = constantObject.descricao;
		}
		
		public static function valueOf(name:String):TipoDominioDownloadEnum {
			return TipoDominioDownloadEnum(ATIVIDADE_ECONOMICA.constantOf(name));
		}
		
		public static function obterPorDescricao(descricao:String) : TipoDominioDownloadEnum {
			if (descricao != null) {
				for each (var enums:TipoDominioDownloadEnum in constants) 
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