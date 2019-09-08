public class Relatorio {
	   
	Cromossomo cromossomo;
	   
    public Relatorio(){
    }
	   
	public String gerar(Cromossomo crm) {
		this.cromossomo = crm.clone();
		Decodificador decodificador = new Decodificador();
		
        String relatorio = "\n=======================\nRELATORIO DE COBERTURA\n=======================";
       
        relatorio = relatorio+"\nMelhor Solucao: "+this.cromossomo.toString();
        int p,a;
        
		int qt_pontos_cobertos = 0;
		
		//System.out.println(this.pontos.size());
		for(int i=0;i<this.cromossomo.pontos.size(); i++)
		{
			for(int j=0;j<this.cromossomo.localizacao.size(); j++)
			{
				/*
				* Calcular a distancia de cada ponto para as antenas
				* D(P1, P2) = {(x1-x2)^2+SQRT(y1-y2)^2}^1/2
				*
				* Decodificando a chave:
				* chave > 0.5 representa que a antena esta sendo utilizada
				* chave <= 0.5 representa que a antena nao esta sendo utilizada
				*/
				
				if(decodificador.verificaUtilizacao(this.cromossomo.chaves.get(j))) {
					double distancia, df_x, df_y;
					
					df_x = this.cromossomo.pontos.get(i).x - this.cromossomo.localizacao.get(j).x;
					df_x = Math.pow(df_x, 2);
					
					df_y = this.cromossomo.pontos.get(i).y - this.cromossomo.localizacao.get(j).y;
					df_y = Math.pow(df_y, 2);
					
					distancia = df_x + df_y;
					distancia = Math.sqrt(distancia);
					
					//Verificando se a itensidade da antena cobre o ponto
					if(distancia < this.cromossomo.antenas.get(j).raio) {
						//O ponto i esta coberto pela antena j
						qt_pontos_cobertos++;
                        p=i+1;
                        a=j+1;
                        relatorio = relatorio+"\nP"+p+" coberto por A"+a;
						//System.out.println("P "+i+" - A "+j);
						break;
					}
				}
			
			}
		}
		
		return relatorio;
		
	}
   
}