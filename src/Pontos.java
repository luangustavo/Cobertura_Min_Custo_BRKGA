import java.text.DecimalFormat;

public class Pontos {
	double x;
	double y;
	
	public Pontos(double x, double y) {
	
		DecimalFormat df = new DecimalFormat("###,##0.00");
		String aux1  = df.format(x);
		String aux2  = df.format(y);
		
		String[] aux3= aux1.split(",");
		String[] aux4= aux2.split(",");
		
		this.x = new Double(aux3[0]+"."+aux3[1]);
		this.y = new Double(aux4[0]+"."+aux4[1]);
	
	}

}