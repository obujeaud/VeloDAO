package business.entities;

public class Equipe {
	private long id;
	private String name;
	private int money;
	private String couleur;

	@Override
	public String toString() {
		return "Equipe [id=" + id + ", name=" + name + ", money=" + money + ", couleur=" + couleur + "]";
	}

	public Equipe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Equipe(long id, String name, int money, String couleur) {
		super();
		this.id = id;
		this.name = name;
		this.money = money;
		this.couleur = couleur;
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

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

}
