import java.util.ArrayList;

public class Decodificador {
	
	
	public Decodificador() {
	}
	
	public boolean verificaUtilizacao(double chave) {
		if(chave > 0.5) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
