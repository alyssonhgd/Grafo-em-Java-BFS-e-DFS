import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

class Grafo {
	
	private int numVertices;
	private int numArestas;
	private String[] vertices; 						// Array  que armazena  o nome de cada vértice
	private boolean[] visitados; 					// Array  que armazena  o nome de cada vértice visitado
	private boolean[] explorados; 					// Array  que armazena  o nome de cada vértice explorado (largura)
	private String[] arestas; 						// Array  que armazena  o nome de cada arestas
	private int[][] matrizAdjacencias;				// Matriz de adjacencias numvertices x numvertices;
	public String caminho = "";
	
	public int getNumVertices() {					return this.numVertices;}
	public void setNumVertices(int n) {				this.numVertices = n;}
	public int getNumArestas() {					return this.numArestas;}
	public void setNumArestas(int n) {				this.numArestas = n;}
	public String[] getArestas() {					return this.arestas;}
	public void setArestas(String[] a){				this.arestas= a;}
	public String[] getVertices() {					return this.vertices;}
	public void setVertices(String[] v){			this.vertices= v;}
	public void setVisitados(boolean[] v){			this.visitados= v;}
	public void setExplorados(boolean[] v){			this.explorados= v;}
	public int[][] getMatrizAdjacencias(){			return this.matrizAdjacencias;}
	public void setMatrizAdjacencias(int[][] v){	this.matrizAdjacencias= v;}
	
	private Grafo() {
		this.numVertices = 0;
		this.numArestas = 0;
	}
	
	/*
	 * Método para realizar a leitura do arquivo que contém as informações do grafo
	 * Este método retorna um grafo. O método é estático e portanto não precisa ser chamado por um objeto já instanciado.
	 * "A classe grafo deve conter um método para efetuar a leitura do arquivo texto e a criacÌ§aÌƒo do objeto grafo. Repare que esse meétodo retorna um objeto do tipo Grafo e eÌ� estaÌ�tico, ou seja, pode ser invocado a partir do nome da classe."
	 */

	public static Grafo lerArquivo(String grafo) throws IOException {
		// O método deve ser estÃ¡tido e retornar um grafo. Então criamos o grafo que será retornado
		Grafo grafoNovo = new Grafo();
		// Cria um objeto do tipo FileReader para ler o arquivo contendo os grafos
		FileReader leitorDeArquivo = new FileReader(grafo);
		// Cria outro objeto para armazenar o arquivo lido
		BufferedReader arquivoLido = new BufferedReader(leitorDeArquivo);
		// Em cada momento a variável abaixo (linhaAtual) recebe um valor diferente, que é o da última linha lida
		String linhaAtual;
		
		// Essa é uma variável auxiliar utilizada, por exemplo, na linha 48, que serve para separar textos onde houver algum caractere específico
		String[] textoSeparado;

		// Enquanto o arquivoLido contiver alguma linha, vai lendo e interpretando seu conteúdo
		while((linhaAtual = arquivoLido.readLine()) !=  null) {
			// Se na linha estiver escrito "<VERTICES>", quer dizer que o que virá a seguir serão os vértices
			if(linhaAtual.equals("<VERTICES>")) {
				// Vai para a próxima linha
				linhaAtual = arquivoLido.readLine();
				// Pelo padrão passado no enunciado do trabalho, a próxima linha conterá¡ o número de vértices.
				// Para pegarmos esse valor, separamos a linha lida onde houver ":"

				textoSeparado = linhaAtual.split(":");
				// â€¦ e a segunda parte será o número de vértices do grafo
				grafoNovo.setNumVertices(Integer.parseInt(textoSeparado[1].trim()));
				// Cria um vetor com valores zerados (vértices não visitados) do tamanho da quantidade de vértices
				boolean[] visitados = new boolean[grafoNovo.getNumVertices()];
				boolean[] explorados = new boolean[grafoNovo.getNumVertices()];
				for(int i = 0; i < grafoNovo.getNumVertices(); i++) {
					visitados[i] = false;
					explorados[i] = false;
				}
				grafoNovo.setVisitados(visitados);
				grafoNovo.setExplorados(explorados);
				// Cria um vetor novo para a busca em largura

				linhaAtual = arquivoLido.readLine();
				// Os vértices serão os valores separados por hífen da próxima linha
				grafoNovo.setVertices(linhaAtual.split("-"));
				
				// Pula para a próxima linha
				linhaAtual = arquivoLido.readLine();
			}
			
			// Aqui quer dizer que a linha atual contém o texto "<ARESTAS>"
			if(linhaAtual.equals("<ARESTAS>")) {
				linhaAtual = arquivoLido.readLine();
				textoSeparado = linhaAtual.split(":");
				grafoNovo.setNumArestas(Integer.parseInt(textoSeparado[1].trim()));
				linhaAtual = arquivoLido.readLine();
				
				grafoNovo.setArestas(linhaAtual.split("-"));
				linhaAtual = arquivoLido.readLine();
			}

			// Aqui quer dizer que a linha atual contém o texto "<VERTICES>"
			if(linhaAtual.equals("<CONEXOES ENTRE VERTICES>")) {
				linhaAtual = arquivoLido.readLine();
				
				// Cria a matriz com o numero x, y de numero de vertices, e zera tornando a mesma vazia e sem arestas
				int[][] matrizTemp = new int[grafoNovo.getNumVertices()][grafoNovo.getNumVertices()];
				for(int i = 0; i < grafoNovo.getNumVertices(); i++) {
					for(int j = 0; j < grafoNovo.getNumVertices(); j++){
						matrizTemp[i][j] = 0;
					}
				}
				
				// Percorre o restante do arquivo ate o final, colocando 1 na matriz de adjacencia onde houver a aresta
				while((linhaAtual != null)) {
					String vertice1,vertice2;	//Cria 2 variaveis para os caminhos de acordo com o arquivo
					// A linha abaixo separa os textos por ":" ou "-"
					textoSeparado = linhaAtual.split(":|-");
					vertice1 = textoSeparado[1].trim();
					vertice2 = textoSeparado[2].trim();
					// Altera aquela posiÃ§Ã£o (exemplo: V1 V3) para 1, informando que existe aresta entre os dois v
					int posicaoVertice1 = Grafo.posicaoDoVertice(vertice1, grafoNovo);
					int posicaoVertice2 = Grafo.posicaoDoVertice(vertice2, grafoNovo);
					matrizTemp[posicaoVertice1][posicaoVertice2] = 1;
					matrizTemp[posicaoVertice2][posicaoVertice1] = 1;
					linhaAtual = arquivoLido.readLine();
				}
				grafoNovo.setMatrizAdjacencias(matrizTemp);
			}
		}
		
		// OK, grafo criado
		return grafoNovo;
	}
	
	//Metodo para Imprimir a matriz de adjacencias
	public void Imprime_Matriz() {
		System.out.println("Matriz de Adjacencias");
		for(int i = 0; i < numVertices; i++) {
			System.out.println("");
			for(int j = 0; j < numVertices ; j++) {
				System.out.print(" "+matrizAdjacencias[i][j]);	
			}
		}
	}

	private static int posicaoDoVertice(String verticeBuscado, Grafo grafo) {
		int indiceDoVertice = 0;
		// Copia os vértices do grafo para esta variável temporária "vertices"
		String[] vertices = grafo.getVertices();
		// Percorre todos os vértices até descobrir qual a posição do vértice desejado
		for(int i = 0; i < grafo.getNumVertices(); i++) {
			if(vertices[i].equals(verticeBuscado)) {
				indiceDoVertice = i;
			}
		}
		if(indiceDoVertice == -1);
		return indiceDoVertice;
	}

	// MÃ©todo que realiza o percorrimento em profundidade do grafo e retorna a string com o caminho
	public String percorrerProfundidade(String verticePartida) {
		// Acha o índice do vértice a fim de verificar, na matriz, quais são os vértices adjacentes
		int indiceDoVertice = Grafo.posicaoDoVertice(verticePartida, this);
		if (this.visitados[indiceDoVertice] == true) return "";
		this.visitados[indiceDoVertice] = true;
		String caminho = this.vertices[indiceDoVertice] +"-";
		
		// Este vetor abaixo será¡, inicialmente, uma fila com os vizinhos do vértice
		int[] adjacentesPosicao = new int[this.getNumVertices()];
		int adjacentesTotal = 0;
		// Verifica na matriz quis as posições dos vértices que são adjacentes
		for(int j = 0; j<this.getNumVertices(); j++) {
			// Quando o valor na matriz que cruza o vértice e o vértice[j] for 1, é porque há adjacência
			// Além disso, se aquele vértice ainda não foi visitados, o colocamos na fila 
			if(this.getMatrizAdjacencias()[indiceDoVertice][j] == 1 && this.visitados[j] == false) {
				// Adiciona o vizinho no final da fila
				// insere-o no "caminho" junto com um hífen
				adjacentesPosicao[adjacentesTotal] = j;
				adjacentesTotal++;
			}
		}
		
		// Depois que percorre todos os vizinhos, percorre os vizinhos dos vizinhos recursivamente
		for(int j = 0; j < adjacentesTotal; j++) {
			caminho = caminho + percorrerProfundidade(this.vertices[adjacentesPosicao[j]]);
		}
		return caminho;
	}
	
	// Método que realiza o percorrimento em largura do grafo e retorna a string com o caminho
	public String percorrerLargura(String verticePartida) {
		// Acha o índice do vértice a fim de verificar, na matriz, quais são os vértices adjacentes
		int indiceDoVertice = Grafo.posicaoDoVertice(verticePartida, this);
		if (this.explorados[indiceDoVertice] == true) return "";
		if (this.visitados[indiceDoVertice] == false) {
			this.caminho += this.vertices[indiceDoVertice] + "-";
			this.visitados[indiceDoVertice] = true;
		}
		this.explorados[indiceDoVertice] = true;

		String caminho = this.vertices[indiceDoVertice] +"-";
		
		// Este vetor abaixo será, inicialmente, uma fila com os vizinhos do vértice
		int[] adjacentesPosicao = new int[this.getNumVertices()];
		int adjacentesTotal = 0;
		// Verifica na matriz quis as posições dos vértices que são adjacentes
		for(int j = 0; j<this.getNumVertices(); j++) {
			// Quando o valor na matriz que cruza o vértice e o vértice[j] for 1, é porque há¡ adjacência
			// Além disso, se aquele vértice ainda não foi visitados, o colocamos na fila 
			if(this.getMatrizAdjacencias()[indiceDoVertice][j] == 1 && this.visitados[j] == false) {
				// Adiciona o vizinho no final da fila
				// insere-o no "caminho" junto com um hífen
				this.visitados[j] = true;
				this.caminho += this.vertices[j] + "-";
				adjacentesPosicao[adjacentesTotal] = j;
				adjacentesTotal++;
			}
		}
		
		// Depois que percorre todos os vizinhos, percorre os vizinhos dos vizinhos recursivamente
		for(int j = 0; j < adjacentesTotal; j++) {
			caminho = caminho + percorrerLargura(this.vertices[adjacentesPosicao[j]]);
		}
		return this.caminho;
	}
	
	
	public boolean ehEulariano()
	{	//numIncidencias guardara o num de adjacencias que o vertice percorrido possui
		int numIncidencias = 0;
		//Variavel criada para retornar false caso algum vertice não tenha grau par, ou seja, sua incidencia será impar, logo o grafo nao pode ser euleriano
		boolean retorno = true;
		for(int i = 0; i < this.getNumVertices(); i++)
		{
			for(int j = 0; j < this.getNumVertices(); j++)
			{
				if(this.getMatrizAdjacencias()[i][j] == 1)
				{
					numIncidencias++;
				}
				if(j+1 == this.getNumVertices())
				{
					if(numIncidencias%2 != 0)
					{
						retorno = false;
						j = this.getNumVertices();
						i = this.getNumVertices();;
					}
				}
			}
		}
	return retorno;
	}
	
	
	
	public String arestasEuleriano()
	{
		String saida = "";
		if(this.ehEulariano() == false)
		{saida = "Grafo não Euleriano!";}
		else
		{
			int[][] matriz_Temp = new int[numVertices][numVertices];
			matriz_Temp =  this.getMatrizAdjacencias();
			int VerticePartida = 0;
			//Loop para encontrar o primeiro vertice que é conectado a uma aresta
			for(int j = 0; j < numVertices; j++)
			{
				if(this.getMatrizAdjacencias()[0][j] == 1)
					VerticePartida = j;
					saida = this.vertices[j];
			}
			int i = 0;
			//percorre toda a matriz temporaria ate acabar seus vertices
			while(verifica_vazio(matriz_Temp) == false)
			{
				if(matriz_Temp[i][VerticePartida] == 1)
				{
					matriz_Temp[0][j] = 0;
					matriz_Temp[j][0] = 0;
				}
			}
			
		}
		
		return saida;
	}
	
	
	private boolean verifica_vazio(int[][] matriz_Temp) {
		boolean saida = true;
		for(int i = 0; i < this.getNumVertices();i++)
		{
			for(int j = 0; j < this.getNumVertices();j++)
				if(matriz_Temp[i][j] == 1)
					saida = false;
		}
		return saida;
	}
	public static void main(String args[]) throws IOException {
		Grafo grafo = Grafo.lerArquivo("grafo.txt");
		grafo.Imprime_Matriz();
		if(grafo.ehEulariano() == true)
		{	
			System.out.println("");
			System.out.println("O grafo é Euleriano!");
		}
		else
			System.out.println("Grafo não Euleriano!");
		System.out.println("");
		System.out.println("Busca Profundidade: "+grafo.percorrerProfundidade("X"));
		Grafo grafo2 = Grafo.lerArquivo("grafo.txt");
		System.out.println("Busca em Largura:   "+grafo2.percorrerLargura("X"));
	}
	
}
