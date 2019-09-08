import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
	/*
	       Entrada: Dois conjuntos um dos Pontos a serem cobertos e outro das Antenas disponiveis.
	
	       P = (p1, p2, ..., pn)
	           Onde n quantidade de pontos
	           (xi, yi) e R2, com 1<=i<=n;
	       A = (a1, a2, ..., am)
	           Onde m quantidade de antenas
	           (rj, cj), rj raio de itensidade da antena j, e cj seu custo, com 1<=j<=m;
	*/
	
	ArrayList<Pontos> pontos = new ArrayList<Pontos>();
	ArrayList<Antenas> antenas = new ArrayList<Antenas>();
	
	//Area de atuacao no plano 2d
	double ini_x, fim_x, ini_y, fim_y;
	ini_x=0;
	fim_x=0;
	ini_y=0;
	fim_y=0;
	
	int aux_linha=0;
	Scanner entrada_pontos = new Scanner(new FileReader("entrada_pontos.txt"));
	Scanner entrada_antenas = new Scanner(new FileReader("entrada_antenas.txt"));
	
	while (entrada_pontos.hasNextLine()) {
	   String linha = entrada_pontos.nextLine();
	   
	   String posicao[];
	   posicao = linha.split(",");
	   //System.out.println(Double.parseDouble(posicao[0]));
	   //Criando os pontos de acordo com o arquivo de entrada
	   Pontos ponto = new Pontos( Double.parseDouble(posicao[0]), Double.parseDouble(posicao[1]) );
	   
	   //Adicionando o ponto na lista
	   pontos.add(ponto);
	   
	   if(aux_linha == 0) {
			ini_x = Double.parseDouble(posicao[0]);
			ini_y = Double.parseDouble(posicao[1]);
			fim_x = ini_x;
			fim_y = ini_y;
	   }
	   else{
		    if(Double.parseDouble(posicao[0]) < ini_x) {
		    	ini_x = Double.parseDouble(posicao[0]);
		    }
		    if(Double.parseDouble(posicao[1]) < ini_y) {
		    	ini_y = Double.parseDouble(posicao[1]);
		    }
	   
		    if(Double.parseDouble(posicao[0]) > fim_x) {
		    	fim_x = Double.parseDouble(posicao[0]);
		    }
		    if(Double.parseDouble(posicao[1]) > fim_y) {
		    	fim_y = Double.parseDouble(posicao[1]);
		    }
	   
	   }
	   
	   aux_linha++;
	   
	}
	
	double total = 0;
	
	while (entrada_antenas.hasNextLine()) {
		String linha = entrada_antenas.nextLine();
	 
		String atributo[];
		atributo = linha.split(",");
	   
		//Criando as antenas de acordo com o arquivo de entrada
		Antenas antena = new Antenas( Double.parseDouble(atributo[0]), Double.parseDouble(atributo[1]) );
		total = total + Double.parseDouble(atributo[1]);
		//Adicionando o ponto na lista
		antenas.add(antena);
	   
	}
	
	//System.out.println(total);
	
	/*
	* Ja temos os dois conjuntos: pontos e antenas
	* Criando a populacao inicial
	*/
	
	ArrayList<Cromossomo> populacao_inicial = new ArrayList<Cromossomo>();
	
	//Conjunto de Soluçoes Elite;
	ArrayList<Cromossomo> solucoes_elite = new ArrayList<Cromossomo>();
	
	//Conjunto de Soluçoes Elite + Nao Elite
	ArrayList<Cromossomo> solucoes = new ArrayList<Cromossomo>();
	
	//Conjunto de Soluçoes Nao Elite;
	ArrayList<Cromossomo> solucoes_nao_elite = new ArrayList<Cromossomo>();
	
	Cromossomo melhor_geracao = null;
	Cromossomo melhor_geral = null;
	
	int aux=0;
	
	/*
	* Variavel auxiliar para verificar se houve melhora ao longo das evolucoes
	* Se nao houver melhora entre a geracao k e a geracao k+3 REINICIALIZA
	*/
	
	int permanece = 0;
	int loop;
	
	/*
	* Variavel auxiliar para verificar a quantidade de reinicializacoes
	* O limite escolhido foram duas reinicializacao
	*/
	
	int reinicializado = 0;
	
	for(loop=0; true; loop++) {
		
		if(reinicializado > 1){
            break;
        }
		
		if(loop == 0) {
		
			melhor_geracao = null;
			
			populacao_inicial.clear();
			solucoes_elite.clear();
			solucoes.clear();
			solucoes_nao_elite.clear();
			
			
			for(int i=0; i<11;i++) {
				Cromossomo cromossomo = new Cromossomo(pontos, antenas, ini_x, fim_x, ini_y, fim_y);
				populacao_inicial.add(cromossomo);
			}
			
			//System.out.println("Estagio inicial");
			
			/*
			* Trabalhando em cima da populacao inicial
			*/
			
			//System.out.println(populacao_inicial.toString());
			
			for(int i=0; i < populacao_inicial.size(); i++) {
			
				/*
				* Seleção populacao inicial
				*/
				populacao_inicial.get(i).apto();
	            Cromossomo copia;
	            copia = populacao_inicial.get(i).clone();
	            solucoes.add(copia);
			
			}
			
			//System.out.println(solucoes.toString());
			
			if(solucoes.size() == 0 ) {
				/*
				* Reinicializacao
				* */
				permanece = 3;
			}
			else {
				Particionamento particionamento = new Particionamento(solucoes);
				
				for(int i=0; i < particionamento.solucoes_elite.size(); i++) {
					solucoes_elite.add(particionamento.solucoes_elite.get(i).clone());
				}
				
				for(int i=0; i < particionamento.solucoes_nao_elite.size(); i++) {
					solucoes_nao_elite.add(particionamento.solucoes_nao_elite.get(i).clone());
				}
				
				melhor_geracao = solucoes_elite.get(0).clone();
				
				//Executara essa condicao apenas uma vez
				if(aux == 0) {
					melhor_geral = melhor_geracao.clone();
					aux++;
				}
			}
		
		
		}
		else {
			
			//System.out.println("Evolucao: " + loop);
			solucoes.clear();
			
			for(int i=0; i < solucoes_elite.size(); i++) {
				solucoes.add(solucoes_elite.get(i).clone());
			}
			                               
			for(int i=0; i < solucoes_nao_elite.size(); i++) {
				solucoes.add(solucoes_nao_elite.get(i).clone());
			}
			
			Cruzamento cruzamento = new Cruzamento(solucoes_elite, solucoes_nao_elite);
			
			for(int i=0; i < cruzamento.cruzados().size(); i++) {
				solucoes.add(cruzamento.cruzados.get(i).clone());
			}
			
			/*
			* Criar Mutantes representam 10% do conjunto
			* */
			
			double qt_mutantes =  solucoes.size()*0.1;
			
			for(int i=0; i < qt_mutantes;i++) {
				Cromossomo mutante = new Cromossomo(pontos, antenas, ini_x, fim_x, ini_y, fim_y);
				solucoes.add(mutante);
			}
			
			/*
			* Verificar se os novos cromossomos estao aptos
			* */
			
			for(int i=0; i < solucoes.size(); i++) {
				solucoes.get(i).apto();
			}
			
			/*
			* Particionando Novamente o Conjunto de Solucoes em Elite e Nao Elite
			* */
			
			Particionamento particionamento = new Particionamento(solucoes);
			
			solucoes_elite.clear();
			solucoes_nao_elite.clear();
			
			for(int i=0; i < particionamento.solucoes_elite.size(); i++) {
				solucoes_elite.add(particionamento.solucoes_elite.get(i).clone());
			}
			
			for(int i=0; i < particionamento.solucoes_nao_elite.size(); i++) {
				solucoes_nao_elite.add(particionamento.solucoes_nao_elite.get(i).clone());
			}
		
		
		}
		
		if(solucoes.size() > 0) {
		
			if(melhor_geracao.custo == solucoes_elite.get(0).custo) {
				permanece++;
			}
			else {
				melhor_geracao = null;
				melhor_geracao = solucoes_elite.get(0).clone();
			}
			
			if(melhor_geracao.custo <= melhor_geral.custo) {
				melhor_geral = null;
				melhor_geral = melhor_geracao.clone();
			}
			
	        if(solucoes_elite.get(0).penalizado == false){
	            System.out.println("Qt Pop.: "+solucoes.size()+"\nMelhor solucao: "+ solucoes_elite.get(0).toString()+"-----------------\n");
	        }
	        else{
	            System.out.println("Qt Pop.: "+solucoes.size()+"\nNenhuma solucao foi encontrada!");
	        }
		}
		else {
			System.out.println("Qt Pop.: "+solucoes.size()+"\nNenhuma solucao foi encontrada!");
			permanece=3;
		}
		
		/*
		* Condicao de Reinicializacao
		*/
		if(solucoes.size()>=500 && solucoes_elite.get(0).penalizado) {
			//System.out.println("REINICIALIZANDO...");
			loop=-1;
			permanece = 0;
			reinicializado++;
		}
		
		/*
		* Condicao de Parada
		*/
		else if(solucoes.size() >= 500) {
			break;
		}
	
	}
	
	/*
	* Solucao Final
	* */
	               
	if(melhor_geral == null || melhor_geral.penalizado){
	    System.out.println("\n======================================\nNenhuma solucao foi encontrada!");
	}
	else{
	    Relatorio relatorio = new Relatorio();
	    System.out.println(relatorio.gerar(melhor_geral));
	    //System.out.println("\n======================================\nMelhor solucao: "+ melhor_geral.toString()+"\n");
	}


}

}