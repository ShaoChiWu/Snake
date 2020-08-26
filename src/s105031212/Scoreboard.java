package s105031212;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Scoreboard {
	static ArrayList<String> lines;
	static ArrayList<Integer> keys;
	private static String filepath;

	// read the file to get scores
	public Scoreboard(String filepath) {
		try {
			this.filepath = filepath;
			Scanner scan = new Scanner(new File(filepath));
			lines = new ArrayList<String>();
			keys = new ArrayList<Integer>();
			String line = "";
			Integer key = 0;

			while (scan.hasNext()) {
				line = scan.nextLine();
				lines.add(line);
				key = Integer.parseInt(line);
				keys.add(key);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// sorting from small to big
	public static void bubbleSort() {
		for (int i = 0; i < keys.size(); i++) {
			for (int j = 0; j < keys.size() - i - 1; j++) {
				if (keys.get(j) > keys.get(j + 1)) {
					Integer temp1 = keys.get(j);
					keys.set(j, keys.get(j + 1));
					keys.set(j + 1, temp1);
					String temp2 = lines.get(j);
					lines.set(j, lines.get(j + 1));
					lines.set(j + 1, temp2);
				}
			}
		}
	}

	// print out the list to a file
	public static void printResult(int score) {
		try {
			File result = new File(filepath);
			PrintStream printfile = new PrintStream(result);
			System.out.println(keys.get(0));
			for (int i = 0; i < lines.size(); i++) {
				printfile.println(lines.get(i));
			}
			printfile.println(score);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
