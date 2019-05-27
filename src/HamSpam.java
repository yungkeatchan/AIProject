import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class HamSpam {
	public static void main(String[] args) throws FileNotFoundException {
		Collection<File> all = new ArrayList<File>();
		addTree(new File("trainFolder"), all);
		Scanner scan = null;
		ArrayList<String> hamWords = new ArrayList<String>();
		ArrayList<Integer> hamNumber = new ArrayList<Integer>();
		ArrayList<String> spamWords = new ArrayList<String>();
		ArrayList<Integer> spamNumber = new ArrayList<Integer>();
		ArrayList<String> allWords = new ArrayList<String>();
		for (File file : all) {
			if (file.isFile()) {
				scan = new Scanner(file);
				if (file.getName().startsWith("spmsg")) {
					while (scan.hasNext()) {
						String word = scan.next();
						if (spamWords.contains(word)) {
							int index = spamWords.indexOf(word);
							int num = spamNumber.get(index);
							spamNumber.add(index, ++num);
							spamNumber.remove(++index);
						} else {
							spamWords.add(word);
							spamNumber.add(new Integer("1"));
						}
						if (!allWords.contains(word)) {
							allWords.add(word);
						}
					}
				} else {
					while (scan.hasNext()) {
						String word = scan.next();
						if (hamWords.contains(word)) {
							int index = hamWords.indexOf(word);
							int num = hamNumber.get(index);
							hamNumber.add(index, ++num);
							hamNumber.remove(++index);
						} else {
							hamWords.add(word);
							hamNumber.add(new Integer("1"));
						}
						if (!allWords.contains(word)) {
							allWords.add(word);
						}
					}
				}
			}
		}
		System.out.println(allWords);

		// Scanner input = new Scanner(System.in);
		// String path = input.next();

		File testFile = new File("src/bare/part10/spmsgc99.txt");
		scan = new Scanner(testFile);

		// ArrayList<String> testWords = new ArrayList<String>();
		// ArrayList<Integer> testNumber = new ArrayList<Integer>();
		// while (scan.hasNext()) {
		// String word = scan.next();
		// if (testWords.contains(word)) {
		// int index = testWords.indexOf(word);
		// int num = testNumber.get(index);
		// testNumber.add(index, ++num);
		// testNumber.remove(++index);
		// } else {
		// testWords.add(word);
		// testNumber.add(new Integer("1"));
		// }
		// }

		System.out.println(hamWords);
		System.out.println(hamWords.size());
		// System.out.println(spamWords);
		// System.out.println(spamWords.size());

		double probSpam = 1;
		double probHam = 1;
		while (scan.hasNext()) {
			String testWord = scan.next();
			probSpam += Math.log(((double) (spamWords.contains(testWord) ? spamNumber.get(spamWords.indexOf(testWord)) : 0))
					/ (double) (spamWords.size() + allWords.size()));
			probHam += Math.log(((double) (hamWords.contains(testWord) ? hamNumber.get(hamWords.indexOf(testWord)) : 0))
					/ (double) (hamWords.size() + allWords.size()));
			System.out.println(probSpam+" "+probHam);

		}
		System.out.println(probSpam);
		System.out.println(probHam);
		if (probSpam > probHam) {
			System.out.println("Spam");
		} else if (probSpam < probHam) {
			System.out.println("Ham");
		} else {
			System.out.println("Not sure");
		}

		// input.close();
	}

	static void addTree(File file, Collection<File> all) {
		File[] children = file.listFiles();
		if (children != null) {
			for (File child : children) {
				all.add(child);
				addTree(child, all);
			}
		}
	}
}
