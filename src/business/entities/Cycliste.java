package business.entities;

public class Cycliste {
	private long id;
	private String name;
	private Equipe e;
	private int nbVelos;
	
	

	@Override
	public String toString() {
		return "Cycliste [id=" + id + ", name=" + name + ", e=" + e + ", nbVelos=" + nbVelos + "]";
	}

	public Cycliste() {
		super();
	}

	public Cycliste(long id, String name, Equipe e, int nbVelos) {
		super();
		this.id = id;
		this.name = name;
		this.e = e;
		this.nbVelos = nbVelos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Equipe getE() {
		return e;
	}

	public void setE(Equipe e) {
		this.e = e;
	}

	public int getNbVelos() {
		return nbVelos;
	}

	public void setNbVelos(int nbVelos) {
		this.nbVelos = nbVelos;
	}

}
