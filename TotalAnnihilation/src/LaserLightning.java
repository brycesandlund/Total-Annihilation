
public class LaserLightning {

	public int color;
	public int color2;
	public int damage;
	public int velocity;
	public int AOE = -1;
	public String explosiongaf = "";
	public String explosionart = "";
	public int rendertype;
	
	public LaserLightning(int rendertype, int color, int color2, int damage, int velocity, int AOE, String explosiongaf, String explosionart)
	{
		this.rendertype = rendertype;
		this.color = color;
		this.color2 = color2;
		this.damage = damage;
		this.velocity = velocity;
		this.AOE = AOE;
		this.explosionart = explosionart;
		this.explosiongaf = explosiongaf;
	}
	
	public LaserLightning(int rendertype, int color, int color2, int damage, int velocity)
	{
		this.rendertype = rendertype;
		this.color = color;
		this.color2 = color2;
		this.damage = damage;
		this.velocity = velocity;
	}
}
