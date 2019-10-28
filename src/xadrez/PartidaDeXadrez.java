package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	//Esta Classe � o 'cora��o' do sistema de xadrez.
	//Aqui ser�o colocadas as regras do jogo.
	
	private int mudarJogador;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque; //Criando a propriedade 'xeque'. Vari�veis 'boolean', por padr�o, nascem com o status 'false'.
	private boolean xequeMate; //Criando a propriedade 'Xeque-Mate'.
	
	//Declara��o das listas de pe�as 'capturadas' e pe�as 'mantidas' no tabuleiro.
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	//Construtor padr�o
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		mudarJogador = 1;
		jogadorAtual = Cor.BRANCAS;
		//Chamando o Setup Inicial, para come�ar a partida, colocando as pe�as no tabuleiro.
		setupInicial();
	}
	
	//GETs
	public int getMudarJogador() {
		return mudarJogador;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	//Criando um m�todo 'Get Xeque' para o m�todo 'xeque' poder expor a sua propriedade na classe Programa.
	public boolean getXeque() {
		return xeque;
	}
	
	//Criando um m�todo 'Get Xeque-Mate' para o m�todo 'xeque' poder expor a sua propriedade na classe Programa.
		public boolean getXequeMate() {
			return xequeMate;
		}
	
	//M�todo
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j); //H� um 'downcast'aqui.
			}
		}
		return matriz;
	}
	
	//M�todo para identificar movimentos poss�veis a partir de uma posi��o de origem.
	public boolean[][] movimentosPossiveis (PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.paraPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	//M�todo para executar movimento de Xadrez.
	public PecaDeXadrez executarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		//Convertendo posi��es recebidas para posi��o da matriz.
		Posicao origem = origemPosicao.paraPosicao();
		Posicao destino = destinoPosicao.paraPosicao();
		validarOrigemPosicao(origem); //Opera��o para verificar se h� pe�a na origem definida.
		validarDestinoPosicao(origem, destino); //Opera��o para verificar se a posi��o de destino � v�lida.
		Peca pecaCapturada = fazerMovimento(origem, destino);
		
		//Procedimento para verificar se, ap�s a jogada, o pr�prio 'player' se colocou em xeque, o que implicar� em anula��o da jogada.
		if (testeXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcecao("Tu n�o podes te colocar em xeque!");
		}
		
		//Procedimento para verificar se o 'oponente' ficou em xeque ap�s a jogada, mudando para 'true' o status da vari�vel 'xeque'.
		//Usar-se-� uma 'express�o condicional tern�ria'.
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		
		//Testando o status de 'xeque-mate'.
		if (testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
		trocarDeJogador(); //Chama o m�todo, definido abaixo, para trocar de 'player'.
		}
		return (PecaDeXadrez)pecaCapturada; //Downcasting para 'PecaDeXadrez', porque a peca capturada era do tipo Peca.
	}
	
	//M�todo/opera��o 'fazerMovimento', usada acima.
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(origem); //Colocando a pe�a a ser movida na vari�vel 'p'.
		p.incrementarContadorDeMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino); //Remover a poss�vel pe�a que esteja na posi��o de destino.
		tabuleiro.colocarPeca(p, destino); //Colocando a pe�a 'p' em seu destino.
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada); //Remover a pe�a capturada da lista de pe�as no tabuleiro.
			pecasCapturadas.add(pecaCapturada); //Adicionar a pe�a capturada na respectiva lista.
		}
		return pecaCapturada;
	}
	
	//M�todo para 'desfazer movimento'. Por exemplo, o Rei 'se colocou em xeque', o que � uma jogada irregular e precisa ser desfeita.
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		//A l�gica do m�todo consiste em 'desfazer' o m�todo acima, 'fazerMovimento'.
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(destino);
		p.decrementarContadorDeMovimentos();
		tabuleiro.colocarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	//M�todo/opera��o para 'validar' a origem da posi��o, usada no m�todo acima, bem como verificar se h� movimentos poss�veis
	//para uma pe�a.
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezExcecao("N�o h� pe�a na posi��o de origem.");
		}
		//Verificar se o jogador 'atual' � o mesmo da 'pe�a escolhida' para ser movimentada.
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A pe�a escolhida n�o � sua.");
		}
		//Verificando se h� movimentos poss�veis para uma determinada pe�a.
		if (!tabuleiro.peca(posicao).haAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("N�o h� movimentos poss�veis para a pe�a escolhida.");
		}
	}
	
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A pe�a escolhida n�o pode ser movida para a posi��o de destino.");
		}
	}
	
	//M�todo para 'trocar de jogador'.
	private void trocarDeJogador() {
		mudarJogador++;
		jogadorAtual = (jogadorAtual == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS; //Express�o condicional 'tern�ria'.
	}
	
	//M�todo para devolver o jogo ao oponente.
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS;
	}
	
	//M�todo para localizar o Rei de uma determinada cor.
	//No m�todo h� o uso da 'fun��o lambda', com 'downcast'da classe 'Peca' para a Classe 'PecaDeXadrez'.
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) { //Verificar se a pe�a 'p' � uma inst�ncia de Rei.
				return (PecaDeXadrez)p; //'Downcast'.
			}
		}
		//Caso a lista seja 'exaurida' e nenhum Rei seja encontrado, lan�a-se a 'exce��o' abaixo.
		//Essa exce��o n�o devar� ocorrer nunca! Se acontecer, � sinal de erro no programa e o mesmo precisar� ser encerrado.
		//Logo, 'n�o haver�' tratamento da exce��o.
		throw new IllegalStateException("N�o existe o Rei da cor " + cor + " no tabuleiro."); //Exce��o nativa do Java.
	}
	
	//M�todo para verificar se o Rei est� em xeque.
	//O algoritmo verificar� todas as pe�as dos advers�rio, examinando a possibilidade que cada uma tem para vir a ocupar
	//a casa do Rei, o que evidencia o 'xeque'.
	private boolean testeXeque(Cor cor) {
		//Identificando a posi��o do Rei no formato de 'matriz'.
		Posicao posicaoDoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		//Criando uma lista das pe�as do 'oponente' desta cor. Nesta lista, chama-se o m�todo 'oponente', criado acima.
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		//Para cada pe�a da lista acima, haver� a verifica��o de possibilidade de amea�a � posi��o do Rei.
		for (Peca p : pecasDoOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	//M�todo, complexo, para testar se o Rei est� em 'Xeque-Mate'.
	private boolean testeXequeMate(Cor cor) {
		//Em primeiro lugar, ratificar que, pelo menos, o Rei est� em xeque.
		if (!testeXeque(cor)) {
			return false;
		}
		//Cria��o de lista contendo todas as pe�as que h� no tabuleiro, do Rei que est� em cheque.
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		//Percorrendo a lista criada acima.
		for (Peca p : lista) {
			//L�gica para averiguar se algum movimento de pe�a pode retirar o jogador amea�ado, da posi��o de 'xeque'.
			boolean[][] matriz = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					//Teste: esta posi��o da matriz � um movimento poss�vel?
					if (matriz[i][j]) {
						/*
						 * No c�digo abaixo, � preciso identificar a posi��o de uma pe�a, o que n�o � poss�vel usando-se
						 * 'p.posicao', visto que o atributo 'posicao' � do tipo 'protected' e est� fora deste pacote. A solu��o �
						 * fazer um 'downcasting' para a classe 'PecaDeXadrez', e a partir disso, � poss�vel usar um 'get'
						 * para identificar a posi��o na forma de coordenadas do xadrez e, ap�s, converter para as coordenadas matriciais.
						 */
						Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						//Verificando se a posi��o de 'xeque' persiste ap�s o movimento.
						boolean testeXeque = testeXeque(cor);
						//Como o movimento acima foi somente para 'testar' a sa�da do xeque, � preciso desfaz�-lo, conforme abaixo.
						desfazerMovimento(origem, destino, pecaCapturada);
						//Se n�o estava em xeque, este movimento tiraria o Rei da posi��o de perigo, e o programa deveria registrar
						//que o jogador estaria fora do 'xeque' ap�s a jogada.
						if (!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	//M�todo que usa as 'coordenadas do Xadrez' (coluna e linha), e N�O as 'matriciais' (linha e coluna).
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca); //Al�m de colocar a pe�a no tabuleiro (acima), coloca-se tamb�m na lista de pe�as no tabuleiro.
	}
	
	//M�todo Setup Inicial. � respons�vel por iniciar a partida de xadrez, colocando as pe�as no tabuleiro.
	private void setupInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCAS));
		
		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETAS));
	}
}
