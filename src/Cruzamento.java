import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Cruzamento {

	ArrayList<Cromossomo> elite  = new ArrayList<Cromossomo>();
	ArrayList<Cromossomo> nao_elite  = new ArrayList<Cromossomo>();
	ArrayList<Cromossomo> cruzados  = new ArrayList<Cromossomo>();
	
	public Cruzamento(ArrayList<Cromossomo> cj_elite, ArrayList<Cromossomo> cj_nao_elite) throws CloneNotSupportedException {
	
		//ArrayList<Cromossomo> cromossomos  = new ArrayList<Cromossomo>();
		
		for(int i=0; i < cj_elite.size(); i++){
			this.elite.add(cj_elite.get(i).clone());
		}
		
		for(int i=0; i < cj_nao_elite.size(); i++){
			this.nao_elite.add(cj_nao_elite.get(i).clone());
		}
		
		//Collections.copy(pop, this.cromossomos);
	}
	
	public ArrayList<Cromossomo> cruzados (){
		this.cruzados.clear();
		
		int aux1, aux2;
		
		for(int i=0; i<this.elite.size(); i++){
		
			aux1 = 0;
			                       
			int j = new Random().nextInt(this.nao_elite.size());
			
			Cromossomo cruzado = this.elite.get(i).clone();
			
			for(int k=0; k<this.nao_elite.get(j).localizacao.size();k++) {
				/*
				* Probabilidade de Heranca:
				* do elite > 0.5
				*/
				double prob_heranca_nao_elite = (new Random().nextInt(5))*0.1;
				double prob_heranca_elite = 1 - prob_heranca_nao_elite;
				
				double n_sorteado = new Random().nextDouble();
				
				if(n_sorteado <= prob_heranca_elite) {
					/*
					 * Herda do Elite
					 * */
					//this.cromossomos.get(i).localizacao.set(j, this.cromossomos.get(i).localizacao.get(j));
					cruzado.localizacao.set(k, elite.get(i).localizacao.get(k));
					cruzado.chaves.set(k, elite.get(i).chaves.get(k));
				
				}
				else {
					/*
					 * Herda do Nao Elite
					 * */
					cruzado.localizacao.set(k, nao_elite.get(i).localizacao.get(k));
					cruzado.chaves.set(k, nao_elite.get(i).chaves.get(k));
					
				}
			}
			
			this.cruzados.add(cruzado);
			
			
		}
		
		return this.cruzados;
	}

}