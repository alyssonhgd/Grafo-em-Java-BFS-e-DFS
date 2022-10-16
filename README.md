# Grafo-em-Java-BFS-e-DFS

Algoritmo feito em Java para Leitura de um arquivo simples que contenha um grafo com os seguinte padrões:

<h2>Grafo.txt</h2>

\<VERTICES><BR>
NUMERO DE VERTICES: 6<BR>
A-B-C-D-E-F<BR>
\<ARESTAS><BR>
NUMERO DE ARESTAS: 8<BR>
A1-A2-A3-A4-A5-A6-A7-A8<BR>
\<CONEXOES ENTRE VERTICES><BR>
A1: A-B<BR>
A2: A-F<BR>
A3: B-C<BR>
A4: B-F<BR>
A5: C-F<BR>
A6: C-E<BR>
A7: E-E<BR>
A8: E-F<BR>
  
  <h2>O Algoritmo tem os metodos a seguir:</h2>
    <p>
  ehEulariano(); // Verifica se o grafo repassado é eulariano ou não.
  <p>
  percorreLargura(); //Faz a busca em largura no grafo retornando o caminho.
      <p>
  percorreProfundidade(); //Faz a busca em profundida no grafo retornando o caminho.
          <p>
  
  
<H1>SAIDA</H1>
Matriz de Adjacencias<BR>

 0 1 0 0 0 1<BR>
 1 0 1 0 0 1<BR>
 0 1 0 0 1 1<BR>
 0 0 0 0 0 0<BR>
 0 0 1 0 1 1<BR>
 1 1 1 0 1 0<BR>
            
 Grafo não Euleriano!<BR>

Busca Profundidade: A-B-C-E-F-<BR>
Busca em Largura:   A-B-F-C-E-<BR>
            
            
