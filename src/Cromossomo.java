import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Cromossomo implements Cloneable {

	ArrayList<Pontos> localizacao = new ArrayList<Pontos>();
	ArrayList<Pontos> pontos = new ArrayList<Pontos>();
	ArrayList<Antenas> antenas = new ArrayList<Antenas>();
	double ini_x, fim_x, ini_y, fim_y;
	double custo;
	ArrayList<Double> chaves = new ArrayList<Double>();
	boolean penalizado;
	
	public Cromossomo(ArrayList<Pontos> p, ArrayList<Antenas> a, double ini_x, double fim_x, double ini_y, double fim_y) {
		this.penalizado = false;
		this.antenas.addAll(a);
		this.pontos.addAll(p);
		Random gerador = new Random();
		double ch;
		this.custo = 0;
		
		this.ini_x = ini_x;
		this.fim_x = fim_x;
		this.ini_y = ini_y;
		this.fim_y = fim_y;
		
		int aux=0;
		Decodificador decodificador = new Decodificador();
		
		for(int i=0;i<this.antenas.size(); i++)
		{
			ch = gerador.nextDouble();
			
			
			
			double pos_x = this.ini_x + (new Random().nextDouble() * (this.fim_x - this.ini_x));
			double pos_y = this.ini_y + (new Random().nextDouble() * (this.fim_y - this.ini_y));
			
			this.localizacao.add(new Pontos(pos_x,pos_y));
			aux++;
			
			
			/*
			* Chaves baseada no BRKGA (0<=ch<=1)
			* */
			this.chaves.add(ch);
			
			
			
			if(decodificador.verificaUtilizacao(ch)) {
				this.custo = this.custo + this.antenas.get(i).custo;
			}
		
		}
		//Cromossomo com as localizacoes todas nulas
		if(aux == 0) {
			this.custo = -1;
		}
	
	}
	
	public boolean apto() {
	
		Decodificador decodificador = new Decodificador();
		
		int qt_pontos_cobertos = 0;
		//System.out.println(this.pontos.size());
		for(int i=0;i<this.pontos.size(); i++)
		{
			for(int j=0;j<this.localizacao.size(); j++)
			{
				/*
				* Calcular a distancia de cada ponto para as antenas
				* D(P1, P2) = {(x1-x2)^2+SQRT(y1-y2)^2}^1/2
				*
				* Decodificando a chave:
				* chave > 0.5 representa que a antena esta sendo utilizada
				* chave <= 0.5 representa que a antena nao esta sendo utilizada
				*/
				
				if(decodificador.verificaUtilizacao(this.chaves.get(j))) {
					double distancia, df_x, df_y;
					
					df_x = pontos.get(i).x - this.localizacao.get(j).x;
					df_x = Math.pow(df_x, 2);
					
					df_y = pontos.get(i).y - this.localizacao.get(j).y;
					df_y = Math.pow(df_y, 2);
					
					distancia = df_x + df_y;
					distancia = Math.sqrt(distancia);
					
					//Verificando se a itensidade da antena cobre o ponto
					if(distancia < this.antenas.get(j).raio) {
						//O ponto i esta coberto pela antena j
						qt_pontos_cobertos++;
						//System.out.println("P "+i+" - A "+j);
						break;
					}
				}
			
			}
		}
		
		if(qt_pontos_cobertos < this.pontos.size()) {
		                   
			this.custo = 0;
			//atualiza custo.

			for(int k=0; k<this.antenas.size();k++) {
				this.custo = this.custo + this.antenas.get(k).custo;
			}
		    this.custo = this.custo*2;
		   
		    this.penalizado = true;
		   
		    return false;
		}
        else{
		    this.custo = 0;
		    //atualiza custo.
		
		    for(int k=0; k<this.antenas.size();k++) {
		            if(decodificador.verificaUtilizacao(this.chaves.get(k))) {
		                    this.custo = this.custo + this.antenas.get(k).custo;
		            }
		    }
		    this.penalizado = false;
		    //Se todos os pontos foram cobertos
		    return true;
        }
		
		
		
	}
	
	public float GetCusto(){
		return (float)this.custo;
	}
	
	public void SetCusto(double c) {
		this.custo = c;
	}
	
	public String toString() {
	
		Decodificador decodificador = new Decodificador();
		
		String cromossomo = "";
		int aux=0;
		for(int i=0;i<this.localizacao.size(); i++)
		{
			if(decodificador.verificaUtilizacao(this.chaves.get(i))) {
				aux=i+1;
				//this.custo = this.custo+this.antenas.get(i).custo;
				cromossomo = cromossomo + "A"+aux+": ("+this.localizacao.get(i).x +","+ this.localizacao.get(i).y + "),";
			}
		}
		
		DecimalFormat df = new DecimalFormat("###,##0.000");
		String aux1  = df.format(this.custo);
		
		String[] aux3= aux1.split(",");
		cromossomo = cromossomo + "R$" + aux3[0]+","+aux3[1] + "\n";
		return cromossomo;
	}
	
	@Override
	public Cromossomo clone()  {
		Cromossomo copia = new Cromossomo(this.pontos,this.antenas, this.ini_x, this.fim_x, this.ini_y, this.fim_y);
		copia.penalizado = this.penalizado;
		copia.localizacao.clear();
		copia.localizacao.addAll(this.localizacao);
		copia.custo=0;
		copia.custo=this.custo;
		copia.chaves.clear();
		copia.chaves.addAll(this.chaves);
		   
		return copia;
		//return (Cromossomo) super.clone();
	}

}