import java.io.FileNotFoundException;
import java.util.List;


public class CostUpdater {

	public static void main(String[] args) throws FileNotFoundException {
		TabaWeapons tabaW = new TabaWeapons();
		List<Unit> unitsToUpdate = tabaW.spD.classes.files.automaticCostUnit;
		for (int i = 0; i < unitsToUpdate.size(); ++i)
		{
			unitsToUpdate.get(i).updateOnDisk();
		}
	}
}
