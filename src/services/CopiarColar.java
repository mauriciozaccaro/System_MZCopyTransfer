package services;

public class CopiarColar {
	
	private String origem;
	private String destino;
	
	public CopiarColar(String origem, String destino) {
		this.origem = origem;
		this.destino = destino;
	}

	public CopiarColar() {
		// TODO Auto-generated constructor stub
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	
}
