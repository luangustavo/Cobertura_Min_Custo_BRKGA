import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Particionamento {
	
	ArrayList<Cromossomo> solucoes_elite = new ArrayList<Cromossomo>();
	ArrayList<Cromossomo> solucoes_nao_elite = new ArrayList<Cromossomo>();

	public Particionamento(ArrayList<Cromossomo> pop) {

		ArrayList<Cromossomo> solucoes = new ArrayList<Cromossomo>();
		
		for(int i=0; i < pop.size(); i++){
			Cromossomo copia;
			copia = pop.get(i).clone();
			solucoes.add(copia);
		}
		
		int total = solucoes.size();
		
		/*
		 * Quantidade de Solucoes Elite representam 20%
		 * */
		
		int qt_elite = (int)(total*0.2);
		
		Collections.sort (solucoes, new Comparator() {
			
			public int compare(Object o1, Object o2) {
				
				Cromossomo p1 = (Cromossomo) o1;
				Cromossomo p2 = (Cromossomo) o2;
				return p1.custo < p2.custo ? -1 : (p1.custo > p2.custo ? +1 : 0);
			}
				
			});
		
		
		/*
		 * Criando o Particionamento
		 * */
		for(int i=0; i < total; i++) {
			
			if(i <= qt_elite) {
				this.solucoes_elite.add(solucoes.get(i).clone());
			}
			else {
				this.solucoes_nao_elite.add(solucoes.get(i).clone());
			}
			
		}
		
	}
	
	public ArrayList<Cromossomo> elites() {
		
		return this.solucoes_elite;
	}
	public ArrayList<Cromossomo> naoElites() {
		
		return this.solucoes_nao_elite;
	}

}
