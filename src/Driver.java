import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Driver {
	public static void main(String[] args) {
		Collection<File> all = new ArrayList<File>();
		HamSpam.addTree(new File("src\\test\\part10"), all);
		int result[] = HamSpam.checkFile(all);
		double accuracy = (double) result[0] / all.size();

		System.out.println("Correct : " + result[0] + " files");
		System.out.println("Wrong : " + result[1] + " files");
		System.out.println("Missing : " + result[2] + " files");
		System.out.println("Accuracy = " + accuracy + "%");
	}
}