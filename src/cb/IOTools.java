package cb;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class IOTools {

	public final static long ONE_SECOND = 1000;
	public final static long SECONDS = 60;

	public final static long ONE_MINUTE = ONE_SECOND * 60;
	public final static long MINUTES = 60;

	public final static long ONE_HOUR = ONE_MINUTE * 60;
	public final static long HOURS = 24;

	public final static long ONE_DAY = ONE_HOUR * 24;

	public static ArrayList<String> readFile(File f) {
		ArrayList<String> result = new ArrayList<String>();

		Scanner x = null;
		try {
			x = new Scanner(f);
		} catch (FileNotFoundException fnfe) {
			System.err.println("[ERR]Could not find file " + f.getPath() + "!");
			return result;
		}

		while (x.hasNextLine()) {
			String line = x.nextLine();
			result.add(line);
		}

		return result;
	}

	public static void writeToFile(File f, ArrayList<String> data) {
		Formatter x = null;
		try {
			x = new Formatter(f);
		} catch (FileNotFoundException fnfe) {
			System.err.println("[ERR]Could not find file " + f.getPath() + "!");
			return;
		}

		for (String v : data) {
			x.format(v + "\n");
		}
		x.close();
	}
	
	public static String readFileFirstLine(File f) {
		String result = "";

		Scanner x = null;
		try {
			x = new Scanner(f);
		} catch (FileNotFoundException fnfe) {
			System.err.println("[ERR]Could not find file " + f.getPath() + "!");
			return result;
		}

		while (x.hasNextLine()) {
			String line = x.nextLine();
			result = line;
			break;
		}

		return result;
	}

	public static String[] flipStringArray(String[] arr) {
		String temp = "";

		for (int i = 0; i < arr.length/2; i++){
			temp = arr[i];
			arr[i] = arr[arr.length-1 - i];
			arr[arr.length-1 - i] = temp;
		}

		return arr;
	}

	public boolean userOnline(String user, String msg) {
		boolean result = false;

		if (msg.contains(user)) {
			result = true;
		}

		return result;
	}

	/**
	 * converts time (in milliseconds) to human-readable format
	 *  "<w> days, <x> hours, <y> minutes and (z) seconds"
	 */
	public static String millisToLongDHMS(long duration) {
		StringBuffer res = new StringBuffer();
		long temp = 0;
		if (duration >= ONE_SECOND) {
			temp = duration / ONE_DAY;
			if (temp > 0) {
				duration -= temp * ONE_DAY;
				res.append(temp).append(" day").append(temp > 1 ? "s" : "")
				.append(duration >= ONE_MINUTE ? ", " : "");
				return res.toString();
			}

			temp = duration / ONE_HOUR;
			if (temp > 0) {
				duration -= temp * ONE_HOUR;
				res.append(temp).append(" hour").append(temp > 1 ? "s" : "")
				.append(duration >= ONE_MINUTE ? ", " : "");
				return res.toString();
			}

			temp = duration / ONE_MINUTE;
			if (temp > 0) {
				duration -= temp * ONE_MINUTE;
				res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
				return res.toString();
			}

			if (!res.toString().equals("") && duration >= ONE_SECOND) {
				res.append(" and ");
			}

			temp = duration / ONE_SECOND;
			if (temp > 0) {
				res.append(temp).append(" second").append(temp > 1 ? "s" : "");
				return res.toString();
			}
			return res.toString();
		} else {
			return "0 second";
		}
	}

	public static void writeToSpecLineFile(File file, String data, int line) {
		Scanner sc = null;
		Formatter formatter = null;
		try {
			sc = new Scanner(file);
			formatter = new Formatter(file);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			return;
		}
		
		ArrayList<String> arrList = new ArrayList<String>();
		
		while (sc.hasNextLine()) {
			arrList.add(sc.nextLine());
		}
		sc.close();
		
		int count = 1;
		for (String s : arrList) {
			if (count == line) {
				formatter.format(data + "\n");
			} else {
				formatter.format(s + "\n");
			}
			count++;
		}
		formatter.close();
	}
	
	public static String readFromSpecificLine(File file, int line) {
		Scanner sc = null;
		String str = "";
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException fnfe) {
			return str;
		}
		
		int count = 1;
		while (sc.hasNextLine()) {
			String scLine = sc.nextLine();
			if (count == line) {
				str = scLine;
				break;
			}
			count++;
		}
		
		return str;
	}

	public static int getLargest(int[] i) {
		int tempLargest = Integer.MIN_VALUE;
		for (int v : i) {
			if (v > tempLargest) {
				tempLargest = v;
			}
		}
		return tempLargest;
	}
}
