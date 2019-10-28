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
	//Esta Classe é o 'coração' do sistema de xadrez.
	//Aqui serão colocadas as regras do jogo.
	
	private int mudarJogador;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque; //Criando a propriedade 'xeque'. Variáveis 'boolean', por padrão, nascem com o status 'false'.
	private boolean xequeMate; //Criando a propriedade 'Xeque-Mate'.
	
	//Declaração das listas de peças 'capturadas' e peças 'mantidas' no tabuleiro.
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	//Construtor padrão
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		mudarJogador = 1;
		jogadorAtual = Cor.BRANCAS;
		//Chamando o Setup Inicial, para começar a partida, colocando as peças no tabuleiro.
		setupInicial();
	}
	
	//GETs
	public int getMudarJogador() {
		return mudarJogador;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	//Criando um método 'Get Xeque' para o método 'xeque' poder expor a sua propriedade na classe Programa.
	public boolean getXeque() {
		return xeque;
	}
	
	//Criando um método 'Get Xeque-Mate' para o método 'xeque' poder expor a sua propriedade na classe Programa.
		public boolean getXequeMate() {
			return xequeMate;
		}
	
	//Método
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j); //Há um 'downcast'aqui.
			}
		}
		return matriz;
	}
	
	//Método para identificar movimentos possíveis a partir de uma posição de origem.
	public boolean[][] movimentosPossiveis (PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.paraPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	//Método para executar movimento de Xadrez.
	public PecaDeXadrez executarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		//Convertendo posições recebidas para posição da matriz.
		Posicao origem = origemPosicao.paraPosicao();
		Posicao destino = destinoPosicao.paraPosicao();
		validarOrigemPosicao(origem); //Operação para verificar se há peça na origem definida.
		validarDestinoPosicao(origem, destino); //Operação para verificar se a posição de destino é válida.
		Peca pecaCapturada = fazerMovimento(origem, destino);
		
		//Procedimento para verificar se, após a jogada, o próprio 'player' se colocou em xeque, o que implicará em anulação da jogada.
		if (testeXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcecao("Tu não podes te colocar em xeque!");
		}
		
		//Procedimento para verificar se o 'oponente' ficou em xeque após a jogada, mudando para 'true' o status da variável 'xeque'.
		//Usar-se-á uma 'expressão condicional ternária'.
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		
		//Testando o status de 'xeque-mate'.
		if (testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
		trocarDeJogador(); //Chama o método, definido abaixo, para trocar de 'player'.
		}
		return (PecaDeXadrez)pecaCapturada; //Downcasting para 'PecaDeXadrez', porque a peca capturada era do tipo Peca.
	}
	
	//Método/operação 'fazerMovimento', usada acima.
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(origem); //Colocando a peça a ser movida na variável 'p'.
		p.incrementarContadorDeMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino); //Remover a possível peça que esteja na posição de destino.
		tabuleiro.colocarPeca(p, destino); //Colocando a peça 'p' em seu destino.
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada); //Remover a peça capturada da lista de peças no tabuleiro.
			pecasCapturadas.add(pecaCapturada); //Adicionar a peça capturada na respectiva lista.
		}
		return pecaCapturada;
	}
	
	//Método para 'desfazer movimento'. Por exemplo, o Rei 'se colocou em xeque', o que é uma jogada irregular e precisa ser desfeita.
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		//A lógica do método consiste em 'desfazer' o método acima, 'fazerMovimento'.
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(destino);
		p.decrementarContadorDeMovimentos();
		tabuleiro.colocarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	//Método/operação para 'validar' a origem da posição, usada no método acima, bem como verificar se há movimentos possíveis
	//para uma peça.
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezExcecao("Não há peça na posição de origem.");
		}
		//Verificar se o jogador 'atual' é o mesmo da 'peça escolhida' para ser movimentada.
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peça escolhida não é sua.");
		}
		//Verificando se há movimentos possíveis para uma determinada peça.
		if (!tabuleiro.peca(posicao).haAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Não há movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peça escolhida não pode ser movida para a posição de destino.");
		}
	}
	
	//Método para 'trocar de jogador'.
	private void trocarDeJogador() {
		mudarJogador++;
		jogadorAtual = (jogadorAtual == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS; //Expressão condicional 'ternária'.
	}
	
	//Método para devolver o jogo ao oponente.
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS;
	}
	
	//Método para localizar o Rei de uma determinada cor.
	//No método há o uso da 'função lambda', com 'downcast'da classe 'Peca' para a Classe 'PecaDeXadrez'.
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) { //Verificar se a peça 'p' é uma instância de Rei.
				return (PecaDeXadrez)p; //'Downcast'.
			}
		}
		//Caso a lista seja 'exaurida' e nenhum Rei seja encontrado, lança-se a 'exceção' abaixo.
		//Essa exceção não devará ocorrer nunca! Se acontecer, é sinal de erro no programa e o mesmo precisará ser encerrado.
		//Logo, 'não haverá' tratamento da exceção.
		throw new IllegalStateException("Não existe o Rei da cor " + cor + " no tabuleiro."); //Exceção nativa do Java.
	}
	
	//Método para verificar se o Rei está em xeque.
	//O algoritmo verificará todas as peças dos adversário, examinando a possibilidade que cada uma tem para vir a ocupar
	//a casa do Rei, o que evidencia o 'xeque'.
	private boolean testeXeque(Cor cor) {
		//Identificando a posição do Rei no formato de 'matriz'.
		Posicao posicaoDoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		//Criando uma lista das peças do 'oponente' desta cor. Nesta lista, chama-se o método 'oponente', criado acima.
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		//Para cada peça da lista acima, haverá a verificação de possibilidade de ameaça à posição do Rei.
		for (Peca p : pecasDoOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	//Método, complexo, para testar se o Rei está em 'Xeque-Mate'.
	private boolean testeXequeMate(Cor cor) {
		//Em primeiro lugar, ratificar que, pelo menos, o Rei está em xeque.
		if (!testeXeque(cor)) {
			return false;
		}
		//Criação de lista contendo todas as peças que há no tabuleiro, do Rei que está em cheque.
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		//Percorrendo a lista criada acima.
		for (Peca p : lista) {
			//Lógica para averiguar se algum movimento de peça pode retirar o jogador ameaçado, da posição de 'xeque'.
			boolean[][] matriz = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					//Teste: esta posição da matriz é um movimento possível?
					if (matriz[i][j]) {
						/*
						 * No código abaixo, é preciso identificar a posição de uma peça, o que não é possível usando-se
						 * 'p.posicao', visto que o atributo 'posicao' é do tipo 'protected' e está fora deste pacote. A solução é
						 * fazer um 'downcasting' para a classe 'PecaDeXadrez', e a partir disso, é possível usar um 'get'
						 * para identificar a posição na forma de coordenadas do xadrez e, após, converter para as coordenadas matriciais.
						 */
						Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						//Verificando se a posição de 'xeque' persiste após o movimento.
						boolean testeXeque = testeXeque(cor);
						//Como o movimento acima foi somente para 'testar' a saída do xeque, é preciso desfazê-lo, conforme abaixo.
						desfazerMovimento(origem, destino, pecaCapturada);
						//Se não estava em xeque, este movimento tiraria o Rei da posição de perigo, e o programa deveria registrar
						//que o jogador estaria fora do 'xeque' após a jogada.
						if (!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	//Método que usa as 'coordenadas do Xadrez' (coluna e linha), e NÃO as 'matriciais' (linha e coluna).
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca); //Além de colocar a peça no tabuleiro (acima), coloca-se também na lista de peças no tabuleiro.
	}
	
	//Método Setup Inicial. É responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro.
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
