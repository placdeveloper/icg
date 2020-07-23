package {
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.registerClassAlias;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	import mx.rpc.events.ResultEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.componentes.containers.Janela;
	import br.com.bancoob.componentes.tabelapaginada.TabelaPaginadaUtils;
	import br.com.bancoob.dto.DateTimeBase;
	import br.com.bancoob.dto.RequisicaoReqDTO;
	import br.com.bancoob.sisbr.DadosLogin;
	import br.com.bancoob.sisbr.portal.PortalModel;
	import br.com.bancoob.util.StringUtils;
	import br.com.bancoob.util.servico.ServicoJava;
	import br.com.bancoob.vo.DestinoVO;
	import br.com.sicoob.app.Application;
	import br.com.sicoob.icg.enums.SituacaoProcessamentoEnum;
	import br.com.sicoob.icg.enums.TipoAtualizacaoEnum;
	import br.com.sicoob.icg.importacaoarquivocadastro.ImportaArquivoUpload;
	import br.com.sicoob.icg.importacaoarquivocadastro.ImportaArquivoVisualizar;
	import br.com.sicoob.icg.importacaoarquivocadastro.MonitoraImportacaoArquivoCadastroView;
	import br.com.sicoob.icg.importacaoarquivocadastro.VisualizarErroProcessamento;
	import br.com.sicoob.icg.util.ServicoJavaUtil;
	import br.com.sicoob.icg.vo.ImportacaoArquivoVO;
	
	public class ImportaArquivoCadastro extends MonitoraImportacaoArquivoCadastroView {
		
		private var importaArquivoUpload: ImportaArquivoUpload;
		private var importaArquivoVisualizar: ImportaArquivoVisualizar;
		private var visualizarErroProcessamento: VisualizarErroProcessamento;
		private var _destino:DestinoVO;
		private static const CLASSE_SERVICO: String =  "br.com.sicoob.icg.cadastro.fachada.ImportaArquivoFachada";
		private static const DESTINO_ICG:String = "servicosJavaICG";
		private static const OPERACAO_OBTER_DADOS: String = "obterDefinicoes";
		private static const OPERACAO_PESQUISAR_ARQUIVO_IMPORTADO: String = "retornoListaArquivosImportados";
		private static const OPERACAO_EXCLUIR_ARQUIVO_IMPORTADO: String = "excluirArquivoImportado";
		private static const TITULO_EXCLUSAO:String = "Erro ao excluir o upload";
		private static const TEXTO_EXCLUSAO:String = "Por favor, selecione um registro na lista para a exclusão";
		private static const TEXTO_EXCLUSAO_NAO_PERMITIDO:String = "Não é possível excluir este arquivo. Exclusão permitida somente para situação de processamento 'A iniciar'";
		private static const TITULO_VISUALIZAR_VALIDACAO:String = "Visualizar erro de validação";
		private static const TITULO_VISUALIZAR_PROCESSAMENTO:String = "Visualizar erro de processamento";
		private static const TEXTO_VISUALIZAR:String = "Por favor, selecione um registro na lista para visualizar";
		private static const TEXTO_NAO_POSSUI_ERRO_VALIDACAO:String = "Arquivo não possui erro de validação.";
		private static const TEXTO_NAO_POSSUI_ERRO_PROCESSAMENTO:String = "Arquivo não possui erro de processamento.";
		protected var servico : ServicoJava;
		protected var servicoConsulta : ServicoJava;
		protected var servicoExcluir : ServicoJava;
		private var coopUsuarioLogado:String = DadosLogin.coop;
		private var _listaArquivosImportados:ArrayCollection = new ArrayCollection();
		private var _listaSituacaoProcessamento:Object = new Object();;
		private var _listaTipoAtualizacao:Object = new Object();
		public var flagConsultarClicado: Boolean = false;
		
		public function ImportaArquivoCadastro() {
			super();
			trace("criando modulo");
			registerClassAlias("br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum", SituacaoProcessamentoEnum);
			registerClassAlias("br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum", TipoAtualizacaoEnum);
			registerClassAlias("br.com.sicoob.icg.negocio.entidades.ImportaArquivo", ImportacaoArquivoVO);
			
			this.servico = ServicoJavaUtil.getServico(CLASSE_SERVICO, "Obtendo definições...", ResultEvent.RESULT, preencherDefinicoes);
			this.servicoConsulta = ServicoJavaUtil.getServico(CLASSE_SERVICO, "Realizando pesquisa inicial...", ResultEvent.RESULT, retornaListaArquivosImportados);
			this.servicoExcluir = ServicoJavaUtil.getServico(CLASSE_SERVICO, "Obtendo definições de exclusao...", ResultEvent.RESULT, retornoExclusao);
			trace("finalizando criando modulo");
			
			this.addEventListener(FlexEvent.CREATION_COMPLETE, inicializar);
		}
		
		private function inicializar(event:FlexEvent): void {
			trace("inicializando");
			
			this.painelFiltro.btConsultar.addEventListener(MouseEvent.CLICK, consultarClicado);
			this.painelFiltro.btLimpar.addEventListener(MouseEvent.CLICK, limparFiltro);

			
			PortalModel.portal.obterDefinicoesDestino(DESTINO_ICG, configurarDestino);

			this.criarBotaoVisualizarErroProcessamento();
			this.criarBotaoVisualizarErrosValidacao();
			this.criarBotaoImportar();
			this.criarBotaoExcluir();
			this.criarbotaoFecharNovo();
			this.configurarBotoesPadroes();
			configurarTabIndex();
		}
		
		private function limparFiltro(evt:Event = null): void {
			this.painelFiltro.txtNomeArquivo.text = "";
			this.painelFiltro.dataInicio.selectedDate = null;
			this.painelFiltro.dataFim.selectedDate = null;
			this.painelFiltro.comboTipoAtualizacao.selectedIndex = 0;
			this.painelFiltro.comboSituacaoProcessamento.selectedIndex = 0;
			
			consultar();
		}
		
		private function consultarClicado(evt:Event = null): void {
			this.flagConsultarClicado = true;
			
			consultar();
		}		
		
		public function consultar():void {
			if (servicoConsulta != null) {
				MostraCursor.setBusyCursor("PESQUISANDO...", Application.application, MostraCursor.CURSOR_PESQUISAR);
			}
			
			var dto:RequisicaoReqDTO = new RequisicaoReqDTO();
			preencherRequisicaoReqDTO(dto);
			servicoConsulta.getOperation(OPERACAO_PESQUISAR_ARQUIVO_IMPORTADO).send(dto);
		}
		
		private function configurarDestino(destino:DestinoVO):void{
				this._destino = destino;
				this.servico.configurarDestino(destino);
				this.servicoConsulta.configurarDestino(destino);
				this.servicoExcluir.configurarDestino(destino);
				trace("Destinos recuperados");
				
				obterDefinicoes();
		}
		
		private function voltarJanelaEvento(Evento: MouseEvent): void {
			this.fecharJanela();
		}		
		
		private function obterDefinicoes(): void{
			trace("inciando consulta")
			var dto: RequisicaoReqDTO = new RequisicaoReqDTO();
			servico.getOperation(OPERACAO_OBTER_DADOS).send(dto);
		}
		
		private function preencherDefinicoes(event : ResultEvent = null):void {
			trace("preenchendo as definições");
			painelFiltro.comboTipoAtualizacao.dataProvider = event.result.dados.listaTipoAtualizacao;
			this._listaTipoAtualizacao = event.result.dados.listaTipoAtualizacao;
			painelFiltro.comboSituacaoProcessamento.dataProvider = event.result.dados.listaSituacoProcessamento;
			this._listaSituacaoProcessamento = event.result.dados.listaSituacoProcessamento;
			
			pesquisaInicial(event);
		}

		private function retornaListaArquivosImportados(event : ResultEvent = null):void {
			this.listaArquivosImportados.list = event.result.dados.listaArquivosImportados;
			if ((listaArquivosImportados == null || listaArquivosImportados.length == 0) && (this.flagConsultarClicado == true)) {
					Alerta.show("Não existe(m) upload(s) para o filtro informado!", "Informação", Alerta.ALERTA_INFORMACAO);
			}
			this.gridListaArquivosImportados.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaArquivosImportados, gridListaArquivosImportados.tamanhoPagina));
		}
		
		public function pesquisaInicial(event : ResultEvent = null):void {
			this.listaArquivosImportados.list = event.result.dados.listaArquivosImportados;
			if(listaArquivosImportados != null &&  listaArquivosImportados.length > 0){
				this.gridListaArquivosImportados.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaArquivosImportados, gridListaArquivosImportados.tamanhoPagina));
			}
		}
		
		private function configurarTabIndex(): void {
			invalidateProperties();
			invalidateDisplayList();
		}
		
		private function configurarBotoesPadroes(): void {
			this.barraBotoesListaCadastro.visible = false;
			
			this.barraBotoesListaCadastro.exibirBotaoAjuda = false;
			this.barraBotoesListaCadastro.exibirBotaoAlterar = false;
			this.barraBotoesListaCadastro.exibirBotaoExcluir = false;
			this.barraBotoesListaCadastro.exibirBotaoIncluir = false;
			this.barraBotoesListaCadastro.exibirBotaoLimpar = false;
			this.barraBotoesListaCadastro.exibirBotaoVisualizar = false;
			this.barraBotoesListaCadastro.exibirBotaoFechar= false;
			
		}		
						
		private function criarbotaoFecharNovo(): void {
			
			this.botaoFecharNovo.label = "FECHAR";
			this.botaoFecharNovo.name = "btnFecharNovo";
			this.botaoFecharNovo.height = 22;
			this.botaoFecharNovo.width = 90;
			this.botaoFecharNovo.x = 680;
			this.botaoFecharNovo.y = 11;
			this.botaoFecharNovo.addEventListener(MouseEvent.CLICK, voltarJanelaEvento);
		}

		private function criarBotaoImportar(): void {
			this.botaoImportar.label = "REALIZAR IMPORTAÇÃO";
			this.botaoImportar.name = "btnImportar";
			
			this.botaoImportar.height = 22;
			this.botaoImportar.width = 180;
			this.botaoImportar.x = 400;
			this.botaoImportar.y = 11;
			
			this.botaoImportar.addEventListener(MouseEvent.CLICK, abrirTelaImportar);
		}

		private function criarBotaoExcluir(): void {
			this.botaoExcluir.label = "Excluir";
			this.botaoExcluir.name = "btnExcluir";
			this.botaoExcluir.height = 22;
			this.botaoExcluir.width = 90;
			this.botaoExcluir.x = 585;
			this.botaoExcluir.y = 11;
			this.botaoExcluir.addEventListener(MouseEvent.CLICK, excluirArquivoImportado);
		}
		
		private function criarBotaoVisualizarErrosValidacao(): void {
			this.botaoVisualizarErrosValidacao.label = "Erros de validação";
			this.botaoVisualizarErrosValidacao.name = "btnVisualizarErrosValidacao";
			this.botaoVisualizarErrosValidacao.height = 22;
			this.botaoVisualizarErrosValidacao.width = 180;
			this.botaoVisualizarErrosValidacao.x = 215;
			this.botaoVisualizarErrosValidacao.y = 11;
			this.botaoVisualizarErrosValidacao.addEventListener(MouseEvent.CLICK, abrirTelaVisualizarErroValidacao);
		}

		private function criarBotaoVisualizarErroProcessamento(): void {
			this.botaoVisualizarErroProcessamento.label = "Erros de Processamento";
			this.botaoVisualizarErroProcessamento.name = "btnVisualizarErroProcessamento";
			this.botaoVisualizarErroProcessamento.height = 22;
			this.botaoVisualizarErroProcessamento.width = 180;
			this.botaoVisualizarErroProcessamento.x = 30;
			this.botaoVisualizarErroProcessamento.y = 11;
			this.botaoVisualizarErroProcessamento.addEventListener(MouseEvent.CLICK, abrirTelaVisualizarErroProcessamento);
		}

		private function excluirArquivoImportado(Evento: MouseEvent): void {
			
			var objeto: ImportacaoArquivoVO = this.gridListaArquivosImportados.grdDados.selectedItem as ImportacaoArquivoVO;
			if(objeto == null){
				Alerta.show(TEXTO_EXCLUSAO, TITULO_EXCLUSAO, Alerta.ALERTA_ERRO);
			} else {
				if (objeto.situacaoProcessamentoEnum.codigo != SituacaoProcessamentoEnum.A_INICIAR.codigo) {
					Alerta.show(TEXTO_EXCLUSAO_NAO_PERMITIDO, TITULO_EXCLUSAO, Alerta.ALERTA_ERRO);
				} else {
						alertaExclusaoArquivoImportado();
					}
			}
		}		
		
		private function alertaExclusaoArquivoImportado() : void {
				Alerta.show(
					"Deseja realmente excluir o registro da lista?",
					"Confirmação", Alerta.ALERTA_SIM, null, confirmacaoExcluirArquivoImportado);	
		}
		
		private function confirmacaoExcluirArquivoImportado(evento:MouseEvent) : void {
			try {
				
				var objeto: ImportacaoArquivoVO = this.gridListaArquivosImportados.grdDados.selectedItem as ImportacaoArquivoVO;
				var importacaoArquivoVO: ImportacaoArquivoVO = new ImportacaoArquivoVO();
				importacaoArquivoVO.idImportaArquivo = objeto.idImportaArquivo;
				importacaoArquivoVO.idInstituicao = objeto.idInstituicao;
				var dto: RequisicaoReqDTO = new RequisicaoReqDTO();	
				dto.dados.importacaoArquivoVO = importacaoArquivoVO;
				this.servicoExcluir.getOperation(OPERACAO_EXCLUIR_ARQUIVO_IMPORTADO).send(dto);
			} catch (e:Error) {
				Alerta.show(e.message, "ERRO", Alerta.ALERTA_ERRO);
			}							
		}
		
		protected function retornoExclusao(event:ResultEvent):void {
			Alerta.show("Arquivo excluído com sucesso!", "Sucesso", Alerta.ALERTA_INFORMACAO);
			
			this.flagConsultarClicado = false;
			
			consultar();
		}
		
		private function abrirTelaImportar(Evento: MouseEvent): void {
			this.importaArquivoUpload = new ImportaArquivoUpload();
			this.importaArquivoUpload.destino = this.destino;
			this.importaArquivoUpload.importaArquivoCadastro = this;
			this.importaArquivoUpload.janelaPai = this.pegaJanela();
			this.importaArquivoUpload._listaTipoArquivos = this._listaTipoAtualizacao;
			this.pegaJanela().visible = false;
			
			var janela: Janela = new Janela();
			janela.title = "Importações de Arquivos";
			janela.x = 150;
			janela.y = 150;
			janela.addChild(this.importaArquivoUpload);
			janela.abrir(Application.application as DisplayObject, false, false);
		}
		
		private function abrirTelaVisualizarErroValidacao(Evento: MouseEvent): void {
			var objeto : ImportacaoArquivoVO = this.gridListaArquivosImportados.grdDados.selectedItem as ImportacaoArquivoVO;
			if(objeto == null){
				Alerta.show(TEXTO_VISUALIZAR, TITULO_VISUALIZAR_VALIDACAO, Alerta.ALERTA_ERRO);
			} else {
				if ((objeto.situacaoProcessamentoEnum.codigo != SituacaoProcessamentoEnum.ERRO_VALIDACAO.codigo) &&
					(objeto.situacaoProcessamentoEnum.codigo != SituacaoProcessamentoEnum.VALIDADO_PARCIALMENTE.codigo))
				{
					Alerta.show(TEXTO_NAO_POSSUI_ERRO_VALIDACAO, TITULO_VISUALIZAR_VALIDACAO, Alerta.ALERTA_ERRO);
				} else {
					
					this.importaArquivoVisualizar = new ImportaArquivoVisualizar();
					this.importaArquivoVisualizar.destino = this.destino;
					this.importaArquivoVisualizar.importaArquivoCadastro = this;
					this.importaArquivoVisualizar.janelaPai = this.pegaJanela();
					this.pegaJanela().visible = false;
					this.importaArquivoVisualizar.importacaoArquivoVO = objeto;
					
					var janela: Janela = new Janela();
					janela.title = "Erros de validação do arquivo";
					janela.x = 150;
					janela.y = 150;
					janela.addChild(this.importaArquivoVisualizar);
					janela.abrir(Application.application as DisplayObject, false, false);
				}
			}
		}

		private function abrirTelaVisualizarErroProcessamento(Evento: MouseEvent): void {
			var objeto : ImportacaoArquivoVO = this.gridListaArquivosImportados.grdDados.selectedItem as ImportacaoArquivoVO;
			if(objeto == null){
				Alerta.show(TEXTO_VISUALIZAR, TITULO_VISUALIZAR_PROCESSAMENTO, Alerta.ALERTA_ERRO);
			} else {
				if ((objeto.situacaoProcessamentoEnum.codigo != SituacaoProcessamentoEnum.ERRO_PROCESSAMENTO.codigo) &&
					(objeto.situacaoProcessamentoEnum.codigo != SituacaoProcessamentoEnum.PROCESSADO_PARCIALMENTE.codigo))
				{
					Alerta.show(TEXTO_NAO_POSSUI_ERRO_PROCESSAMENTO, TITULO_VISUALIZAR_PROCESSAMENTO, Alerta.ALERTA_ERRO);
				} else {
					this.visualizarErroProcessamento = new VisualizarErroProcessamento();
					this.visualizarErroProcessamento.destino = this.destino;
					this.visualizarErroProcessamento.importaArquivoCadastro = this;
					this.visualizarErroProcessamento.janelaPai = this.pegaJanela();
					this.pegaJanela().visible = false;
					this.visualizarErroProcessamento.importacaoArquivoVO = objeto;
					
					var janela: Janela = new Janela();
					janela.title = "Erros de processamento do arquivo";
					janela.x = 150;
					janela.y = 150;
					janela.addChild(this.visualizarErroProcessamento);
					janela.abrir(Application.application as DisplayObject, false, false);
				}
			}
		}

		public function preencherRequisicaoReqDTO(dto: RequisicaoReqDTO): void {		
			var importacaoArquivoVO: ImportacaoArquivoVO = new ImportacaoArquivoVO();
		
			if (StringUtils.trim(this.painelFiltro.txtNomeArquivo.text) != "") {
				importacaoArquivoVO.nomeArquivo = this.painelFiltro.txtNomeArquivo.text;
			}
			
			if ((this.painelFiltro.comboTipoAtualizacao.selectedItem != 0) && (this.painelFiltro.comboTipoAtualizacao.selectedItem != null)) {
				importacaoArquivoVO.codigoTipoArquivo = (this.painelFiltro.comboTipoAtualizacao.selectedItem as TipoAtualizacaoEnum).codigo;
			}
			
			if ((this.painelFiltro.comboSituacaoProcessamento.selectedItem != 0) && (this.painelFiltro.comboSituacaoProcessamento.selectedItem != null)){
				importacaoArquivoVO.codigoSituacaoProcessamento = (this.painelFiltro.comboSituacaoProcessamento.selectedItem as SituacaoProcessamentoEnum).codigo;
			}
			
			if ((this.painelFiltro.dataInicio.selectedDate != null) && (this.painelFiltro.dataFim.selectedDate != null)) {
				importacaoArquivoVO.dataHoraImportacaoFiltroInicio =  DateTimeBase.getDateTimeEntity(this.painelFiltro.dataInicio.selectedDate);
				importacaoArquivoVO.dataHoraImportacaoFiltroFim =  DateTimeBase.getDateTimeEntity(this.painelFiltro.dataFim.selectedDate);
			}
			
			dto.dados.importacaoArquivoVO = importacaoArquivoVO;
		}
		
		public function get listaArquivosImportados():ArrayCollection {
			return this._listaArquivosImportados;
		}
	}
}