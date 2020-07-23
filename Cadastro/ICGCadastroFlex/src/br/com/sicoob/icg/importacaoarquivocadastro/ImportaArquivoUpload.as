package br.com.sicoob.icg.importacaoarquivocadastro
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.FileFilter;
	import flash.net.registerClassAlias;
	
	import mx.containers.VBox;
	import mx.events.FlexEvent;
	import mx.rpc.events.ResultEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.Botao;
	import br.com.bancoob.componentes.containers.HBoxBancoob;
	import br.com.bancoob.componentes.containers.Janela;
	import br.com.bancoob.componentes.util.IUploadMultiplosArquivos;
	import br.com.bancoob.componentes.util.UploadMultiplosArquivosFactory;
	import br.com.bancoob.dto.RequisicaoReqDTO;
	import br.com.bancoob.sisbr.DadosLogin;
	import br.com.bancoob.sisbr.portal.PortalModel;
	import br.com.bancoob.util.StringUtils;
	import br.com.bancoob.util.servico.ServicoJava;
	import br.com.bancoob.vo.DestinoVO;
	import br.com.sicoob.BCD.util.der.Integer;
	import br.com.sicoob.icg.enums.TipoAtualizacaoEnum;
	import br.com.sicoob.icg.enums.TipoExtensaoArquivoEnum;
	import br.com.sicoob.icg.util.ServicoJavaUtil;
	import br.com.sicoob.icg.vo.ImportacaoArquivoVO;
	
	public class ImportaArquivoUpload extends ImportaArquivoUploadView { 
		
		registerClassAlias("br.com.sicoob.icg.enums.TipoAtualizacaoEnum", TipoAtualizacaoEnum);
		
		public var janelaPai: Janela;
		protected var servicoImportaArquivo : ServicoJava;
		public var _listaTipoArquivos:Object = new Object();
		public var importaArquivoCadastro: ImportaArquivoCadastro;
		private var coopUsuarioLogado:String = DadosLogin.coop;
		private var usrUsuarioLogado:String = DadosLogin.usr;
		private const DIRETORIO_UPLOAD : String = "/mnt/ICG";
		private const OPERACAO_IMPORTA_ARQUIVO: String = "importarArquivo";
		private static const DESTINO_ICG:String = "servicosJavaICG";
		public static const CLASSE_SERVICO: String = "br.com.sicoob.icg.cadastro.fachada.ImportaArquivoFachada";
		private var _uploadContigencia : IUploadMultiplosArquivos = UploadMultiplosArquivosFactory.getInstance().createComponent();
		private var caminhoArquivo : String;
		private var nomeArquivo : String
		private var nomeArquivoDiretorio : String
		private var descExtensao : String
		private var fecharPosUpload : Boolean = true;
		private var botAdicionar : Botao;
		private var destinoVO:DestinoVO;
		private var limiteNomeArquivo: int = 30;
		
		public function ImportaArquivoUpload() {
			super();
			this.servicoImportaArquivo = ServicoJavaUtil.getServico(CLASSE_SERVICO, "Arquivo sendo gravado...", ResultEvent.RESULT, retornoExecutarUploadArquivo);
			addEventListener(FlexEvent.CREATION_COMPLETE, preInicializacao);		
		}
 
		private function preInicializacao(evento:FlexEvent) : void {
			adicionarComponenteUpload();
			addEventListener(FlexEvent.CREATION_COMPLETE, this.init);
			this.comboTipoArquivo.addEventListener(Event.CHANGE, habititaUpload);
		} 		
		
		private function adicionarComponenteUpload():void {
			VBox(_uploadContigencia).height = 250;			
			_uploadContigencia.tiposArquivos = [new FileFilter("Arquivo texto (*.csv)","*.csv"),
											    new FileFilter("Arquivo Json (*.json)","*.json")];
			_uploadContigencia.destination = geraDiretorioDestino();
			_uploadContigencia.nameConflict = 2;
			_uploadContigencia.maxfileselect = 1;
			_uploadContigencia.stopOnError = true;
			idBoxComponenteUpload.visible = false;
			redimencionarJanela(200);
		}
		
		private function geraDiretorioDestino():String {
			return DIRETORIO_UPLOAD+"/"+coopUsuarioLogado+"/"+usrUsuarioLogado+"/";
		}
		
		private function sucessoEnvioArquivo(evento:Event) : void {
				removerBotoes();
				Alerta.show("Deseja realmente enviar o arquivo?","Confirmação", Alerta.ALERTA_SIM, null, confirmacaoEnvioArquivoAtualizacao, cancelarEnvio);
		}
		
		private function habititaUpload(evento:Event) : void {
		
			if (comboTipoArquivo.selectedItem == null) {
				idBoxComponenteUpload.visible = false;	
				redimencionarJanela(200);
			} else {
				idBoxComponenteUpload.visible = true;
				redimencionarJanela(470);
			}
		}
		
		private function redimencionarJanela(valor:int) : void {
			this.pegaJanela().height = valor;
		}
		
		private function confirmacaoEnvioArquivoAtualizacao(evento:MouseEvent) : void {
			try {

				nomeArquivoDiretorio = "";
				if (this._uploadContigencia["gridArquivos"].selectedItem.serverFileName == null || this._uploadContigencia["gridArquivos"].selectedItem.serverFileName == "") {
					nomeArquivoDiretorio = this._uploadContigencia["gridArquivos"].selectedItem.descName;
				} else {
					nomeArquivoDiretorio = this._uploadContigencia["gridArquivos"].selectedItem.serverFileName;
				}
				
				var importacaoArquivoVO: ImportacaoArquivoVO = new ImportacaoArquivoVO();
				importacaoArquivoVO.codigoTipoArquivo = this.comboTipoArquivo.selectedItem.codigo;
				importacaoArquivoVO.nomeArquivo = this._uploadContigencia["gridArquivos"].selectedItem.descName;
				importacaoArquivoVO.nomeArquivoDiretorio = nomeArquivoDiretorio;
				importacaoArquivoVO.diretorioArquivo = geraDiretorioDestino();
				importacaoArquivoVO.idInstituicao = Number(coopUsuarioLogado);
				importacaoArquivoVO.usuarioResponsavel = usrUsuarioLogado;				
				var dto:RequisicaoReqDTO = new RequisicaoReqDTO();
				dto.dados.importacaoArquivoVO = importacaoArquivoVO;
				servicoImportaArquivo.getOperation(OPERACAO_IMPORTA_ARQUIVO).send(dto);
				
				if (fecharPosUpload) {
					this.fecharJanela();
				}
				
				removerBotoes();
				_uploadContigencia.limpar();
				comboTipoArquivo.selectedItem = null;
			} catch (e:Error) {
				Alerta.show(e.message, "ERRO", Alerta.ALERTA_ERRO);
			}		
		}
		
		private function cancelarEnvio(evento:MouseEvent) : void {
			comboTipoArquivo.setFocus();
		}

		private function removerBotoes() : void {
			var hboxBotoesUpload : HBoxBancoob = HBoxBancoob(_uploadContigencia["containerBotsPadroes"]);
			hboxBotoesUpload.percentWidth = 10;
			var botDigi : Botao = Botao(_uploadContigencia["botDigitalizar"]);
			var botRetirar : Botao = Botao(_uploadContigencia["botRetirar"]);
			botDigi.enabled = botRetirar.enabled = false;
			botDigi.visible = botDigi.includeInLayout =	botRetirar.visible = botRetirar.includeInLayout = false;
		}
								
		private function gerarNomeArquivo(nome:String, descExtensao:String): String {
			var data:Date = new Date();
			var data2:String = data.date.toString();
			var ano:String = data.fullYear.toString(); 
			var mes:String = (data.month.toString().length == 1) ? "0"+data.month.toString() : data.month.toString();
			var dia:String = (data.day.toString().length == 1) ? "0"+data.day.toString() : data.day.toString();
			nome = StringUtils.replace(nome, descExtensao,"");
			
			return nome + ano + mes + dia + descExtensao;
		}
		
		protected override function init(event:FlexEvent): void {
			inicializarServicos();
			
			this.botaoVoltar.addEventListener(MouseEvent.CLICK, voltarJanelaEvento);
			this.txtIdInstituicao.text = coopUsuarioLogado;
			preencherComboTipoArquivo();
			
			this.pegaJanela().barraBotoes.btFechar.addEventListener(MouseEvent.CLICK, voltarJanelaEvento);
		}

		private function inicializarServicos():void {
			PortalModel.portal.obterDefinicoesDestino(DESTINO_ICG, onDestinoRecuperado);	
		}
		
		private function onDestinoRecuperado(destino:DestinoVO):void {
			destinoVO = destino;
			_uploadContigencia.destinoVO = destinoVO;
			servicoImportaArquivo.configurarDestino(destinoVO);
			configurarComponente();
		}
		
		private function configurarComponente() : void {
			idBoxComponenteUpload.addChild(_uploadContigencia as DisplayObject);
			botAdicionar = Botao(_uploadContigencia["botAdicionar"]);
			botAdicionar.enabled = true;
			var botEnviar : Botao = Botao(_uploadContigencia["botEnviar"]);
			var botLimpar : Botao = Botao(_uploadContigencia["botLimpar"]);
			botEnviar.addEventListener(MouseEvent.CLICK, validarCamposObrigatorios);
			botLimpar.addEventListener(MouseEvent.CLICK, limparCampos);
			removerBotoes();
		}
		
		private function limparCampos(Evento: MouseEvent): void {
			comboTipoArquivo.selectedItem = null;
			habititaUpload(Evento);
		}
		
		private function visualizarJanelaPai(): void {
			this.janelaPai.visible = true;
		}
		
		public override function fecharJanela(): void {
			pegaJanela().visible = false;
			super.fecharJanela();
			visualizarJanelaPai();
			this.importaArquivoCadastro.flagConsultarClicado = false;
			this.importaArquivoCadastro.consultar();
		}
		
		private function voltarJanelaEvento(Evento: MouseEvent): void {
			this.fecharJanela();
		}

		private function preencherComboTipoArquivo():void {
			this.comboTipoArquivo.dataProvider = this._listaTipoArquivos;
		}

		private function retornoExecutarUploadArquivo(aEvento: ResultEvent): void {
			var importacaoArquivoVO : ImportacaoArquivoVO = aEvento.result.dados.importaArquivo;
			var mensagemRetorno: String = aEvento.result.dados.tipoMensagem;
			
			if("SUCESSO" == mensagemRetorno) {
				Alerta.show("Arquivo importado com sucesso e enviado para processamento!", "Importação de Arquivo", 
					Alerta.ALERTA_INFORMACAO);	
				fecharJanela();
			}	
		}
		

		private function validarCamposObrigatorios(evento:Event): Boolean {
			if(this.txtIdInstituicao.text == "") {
				Alerta.show("Falha na importação do arquivo. Instituição não localizada.", "Campo obrigatório", Alerta.ALERTA_ERRO);
				_uploadContigencia.stopOnError = true;
				return false;
			} else if((this.comboTipoArquivo.selectedItem == 0) || (this.comboTipoArquivo.selectedItem == null)) {
				Alerta.show("Falha na importação do arquivo. Selecione um tipo de arquivo da lista.", "Campo obrigatório", Alerta.ALERTA_ERRO);
				_uploadContigencia.stopOnError = true;
				return false;
			} else if (this._uploadContigencia["gridArquivos"].selectedItem.descName.length > this.limiteNomeArquivo) {
					Alerta.show("Falha na importação do arquivo. Nome do arquivo deve contar até " + this.limiteNomeArquivo + " caracteres.", "Campo obrigatório", Alerta.ALERTA_ERRO);
					return false;
			} else if ((this._uploadContigencia["gridArquivos"].selectedItem.descExtensao != TipoExtensaoArquivoEnum.CSV.descricao) && (this._uploadContigencia["gridArquivos"].selectedItem.descExtensao != TipoExtensaoArquivoEnum.JSON.descricao)) {
					Alerta.show("Falha na importação do arquivo. Extensão do arquivo não permitida.", "Campo obrigatório", Alerta.ALERTA_ERRO);
					return false;
			} else {				
				sucessoEnvioArquivo(evento);
				return true;
			}
			return true;
		}
	}
}