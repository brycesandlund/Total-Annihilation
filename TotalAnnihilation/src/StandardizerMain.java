import java.io.FileNotFoundException;


public class StandardizerMain {

	public static void main(String[] args) throws FileNotFoundException {
		ExplosionStandardizer explos = new ExplosionStandardizer();
		explos.addExplosion(new Explosion("atomicboom", 1200, 420));
		explos.addExplosion(new Explosion("atomicboomsml", 1200, 210));
		explos.addExplosion(new Explosion("liteboom", 1200, 100));
		explos.addExplosion(new Explosion("lrpcboom", 600, 128));
		explos.addExplosion(new Explosion("mineboom", 300, 128));
		explos.addExplosion(new Explosion("ringboom", 2400, 420));
		explos.addExplosion(new Explosion("ringboomsml", 2400, 210));
		explos.addExplosion(new Explosion("riotboom", 175, 70));
		explos.addExplosion(new Explosion("fx", "explode3", 38, 40));
		explos.addExplosion(new Explosion("fx", "explode2", 300, 60));
		explos.addExplosion(new Explosion("fx", "explode4", 600, 20));
		explos.addExplosion(new Explosion("fx", "nuke1", 500, 70));
		explos.addExplosion(new Explosion("blodboom", 600, 32));
		explos.addExplosion(new Explosion("favboom", 1200, 32));
		explos.addExplosion(new Explosion("tawp_fx", "xpl2", 125, 45));
		explos.addExplosion(new Explosion("tawp_fx", "xpl4", 90, 75));
		explos.addExplosion(new Explosion("tawp_fx", "xpl5", 150, 65));
		explos.addExplosion(new Explosion("tawp_fx", "xpl6", 425, 90));
		explos.addExplosion(new Explosion("tawp_fx", "xpl7", 700, 75));
		explos.addExplosion(new Explosion("tawp_fx", "xpl8", 775, 100));
		explos.addExplosion(new Explosion("juggboom", 2000, 200));
		
		explos.standardize();
		
		//lasers
		LaserLightningStandardizer laserLightnings = new LaserLightningStandardizer();
		laserLightnings.addLaserLightning(new LaserLightning(0, 208, 0, 38, 1000, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(0, 232, 234, 75, 1000, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(0, 96, 98, 150, 1000, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(0, 117, 119, 300, 1000, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(0, 114, 217, 600, 1400, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(0, 114, 217, 600, 1400, 32, "blodboom", "blodboom"));
		laserLightnings.addLaserLightning(new LaserLightning(0, 70, 72, 1200, 1400, 32, "favboom", "favboom"));
		//needs to be updated for white boom
	//	laserLightnings.addLaserLightning(new LaserLightning(0, 16, 18, 2400, 1400, 32, "blodboom", "blodboom"));
		
		//lightning
		laserLightnings.addLaserLightning(new LaserLightning(7, 117, 119, 350, 900, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(7, 114, 217, 700, 900, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(7, 70, 72, 1400, 900, 15, "fx", "explode5"));
		laserLightnings.addLaserLightning(new LaserLightning(7, 15, 17, 2800, 900, 15, "fx", "explode5"));
		
		laserLightnings.standardize();
		
		WeaponsStandardizer weaponsStandardizer = new WeaponsStandardizer();
		weaponsStandardizer.standardize();
	}
}
