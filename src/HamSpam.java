import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class HamSpam {
	public static int[] checkFile(Collection<File> testFile) {
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
				try {
					scan = new Scanner(file);
				} catch (FileNotFoundException e) {
					continue;
				}
				while (scan.hasNext()) {
					String word = scan.next();
					if (word.trim().length() <= 1) {
						continue;
					}
					if (file.getName().startsWith("spmsg")) {

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

					} else {
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

		// File testFile = new File(testFile);

		int correct = 0;
		int wrong = 0;
		int fileNotFound = 0;

		double probSpam = 1;
		double probHam = 1;

		for (File file : testFile) {
			try {
				scan = new Scanner(file);
				while (scan.hasNext()) {
					String testWord = scan.next();
					probSpam += Math.log(
							(((double) (spamWords.contains(testWord) ? spamNumber.get(spamWords.indexOf(testWord)) : 0))
									+ 1) / (double) (spamWords.size() + allWords.size()));
					probHam += Math.log(
							(((double) (hamWords.contains(testWord) ? hamNumber.get(hamWords.indexOf(testWord)) : 0))
									+ 1) / (double) (hamWords.size() + allWords.size()));

				}
				System.out.println(probSpam + " " + probHam);

			} catch (FileNotFoundException e) {
				fileNotFound++;
				continue;
			}
			if (file.getName().startsWith("spmsg")) {
				if (probSpam > probHam)
					correct++;
				else
					wrong++;

			} else {
				if (probSpam < probHam)
					correct++;
				else
					wrong++;
			}
		}

		scan.close();

		int result[] = { correct, wrong, fileNotFound };
		return result;
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
