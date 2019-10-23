package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	//Esta Classe � o 'cora��o' do sistema de xadrez.
	//Aqui ser�o colocadas as regras do jogo.
	
	private int mudarJogador;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
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
		trocarDeJogador(); //Chama o m�todo, definido abaixo, para trocar de 'player'.
		return (PecaDeXadrez)pecaCapturada; //Downcasting para 'PecaDeXadrez', porque a peca capturada era do tipo Peca.
	}
	
	//M�todo/opera��o 'fazerMovimento', usada acima.
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem); //Colocando a pe�a a ser movida na vari�vel 'p'.
		Peca pecaCapturada = tabuleiro.removerPeca(destino); //Remover a poss�vel pe�a que esteja na posi��o de destino.
		tabuleiro.colocarPeca(p, destino); //Colocando a pe�a 'p' em seu destino.
		return pecaCapturada;
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
	
	//M�todo que usa as 'coordenadas do Xadrez' (coluna e linha), e N�O as 'matriciais' (linha e coluna).
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	//M�todo Setup Inicial. � respons�vel por iniciar a partida de xadrez, colocando as pe�as no tabuleiro.
	private void setupInicial() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCAS));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETAS));
	}
}
