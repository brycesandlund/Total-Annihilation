
public class Explosion {

	private String explosiongaf;
	private String explosionart;
	private int damage;
	private int AOE;
	
	public String getExplosiongaf()
	{
		return explosiongaf;
	}
	
	public String getExplosionart()
	{
		return explosionart;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public int getAOE()
	{
		return AOE;
	}
	
	public Explosion(String explosiongaf, String explosionart, int damage, int AOE)
	{
		this.explosiongaf = explosiongaf.toLowerCase();
		this.explosionart = explosionart.toLowerCase();
		this.damage = damage;
		this.AOE = AOE;
	}
	
	public Explosion(String explosiongaf, int damage, int AOE)
	{
		this.explosiongaf = explosiongaf.toLowerCase();
		this.explosionart = explosiongaf.toLowerCase();
		this.damage = damage;
		this.AOE = AOE;
	}
}
