package cb;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class CalcTools {

	static Random r = new Random();
	private static String classesPathFile = "/Users/user/Desktop/PcBackup/Temp/Bots/CalcBot/classes";
	private static String outputsPathFile = "/Users/user/Desktop/PcBackup/Temp/Bots/CalcBot/help";
	public static String convert(String fromType, String toType, String num) {
		String result = "Error";
		int number = 0;
		try {
			switch (fromType) {
			case "-h": // Hexadecimal
				number = Integer.parseInt(num, 16);
				break;
			case "-d": // Decimal
				number = Integer.parseInt(num);
				break;
			case "-o": // Octary
				number = Integer.parseInt(num, 8);
				break;
			case "-b": // Binary
				number = Integer.parseInt(num, 2);
				break;
			}
		} catch (NumberFormatException nfe) {
			return result;
		}

		switch (toType) {
		case "-h": // Hexadecimal
			result = Integer.toHexString(number);
			break;
		case "-d": // Decimal
			result = Integer.toString(number);
			break;
		case "-o": // Octary
			result = Integer.toOctalString(number);
			break;
		case "-b": // Binary
			result = Integer.toBinaryString(number);
			break;
		}

		return result.toUpperCase();
	}

	public static String rand(String string, String string2) {
		String result = "Error";
		try {
			int f = Integer.parseInt(string);
			int t = Integer.parseInt(string2);
			int n = r.nextInt(t - f) + f;
			result = "Random number between " + f + " and " + t + ": " + n;
			return result;
		} catch (Exception nfe) {
			return result;
		}
	}

	public static String rand(String string2) {
		String result = "Error";
		try {
			int i = Integer.parseInt(string2);
			int n = r.nextInt(i);
			result = "Random number between 0 and " + i + ": " + n;
			return result;
		} catch (NumberFormatException nfe) {
			return result;
		}
	}

	public static String guessEmotion(String s) {
		int sad = 0;
		int happy = 0;
		int angry = 0;
		String v = s.toLowerCase().trim();
		String result = "I cant guess your emotion.";

		File file = new File("help/emo.txt");
		ArrayList<String> emoTXT = IOTools.readFile(file);
		for (String n : emoTXT) {
			String args[] = n.split(";");
			System.out.println(args[0] + "  "  + args[1]);
			if (v.contains(args[0])) {
				switch (args[1]) {
				case "s":
					sad++;
					System.out.println("sad");
					break;
				case "h":
					happy++;
					break;
				case "a":
					angry++;
					break;
				}
			}
		}

		int emos[] = {sad, happy, angry};
		int emoti = IOTools.getLargest(emos);
		if (emoti == 0) {
			return result;
		}
		if (emoti == sad) {
			result = "Sad";
		}else
			if (emoti == happy) {
				result = "Happy";
			}else
				if (emoti == angry) {
					result = "Angry";
				}

		return result;
	}

	public static void runJava(final String s2) throws IOException {
		new Thread(){
			public void run() {
				ArrayList<String> imps = IOTools.readFile(new File("help/imports.txt"));
				String tempStr = "";
				for (String s : imps) {
					tempStr = tempStr + s + " ";
				}

				ArrayList<String> funcs = IOTools.readFile(new File("help/funcs.txt"));
				String tempStr2 = "";
				for (String s : funcs) {
					tempStr2 = tempStr2 + s + " ";
				}
				String s = new String(tempStr + "\n public class Main { public static void main(String[] args) { " + s2 + " } \n " + tempStr2 + "}");
				ArrayList<String> data = new ArrayList<String>();
				data.add(s);
				IOTools.writeToFile(new File("Main.java"), data);

				if (s2.contains("Runtime")) {
					CommandHandler.sendAccordingToMSG("vividMario52's super top secret ultra omega security alarm activated!");
					CommandHandler.sendAccordingToMSG("brring brring brring");
					return;
				}
				try {
					//CommandHandler.sendAccordingToMSG("Starting Program in seperate thread...");
					runProcess("javac Main.java");
					runProcess("java Main");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}

	private static void printLines(String name, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(
				new InputStreamReader(ins));
		int l = 1;
		while ((line = in.readLine()) != null) {
			if (l == 5) {
				CommandHandler.sendAccordingToMSG("Max lines reached");
				break;
			}

			if (line.length() > 256) {
				break;
			}
			CommandHandler.sendAccordingToMSG(line);
			try { 
				Thread.sleep(1000);
			} catch (Exception e) {

			}
			l++;
		}
	}

	private static void runProcess(final String command) throws Exception {
		try {
			Process pro = Runtime.getRuntime().exec(command);
			printLines(command + " stdout:", pro.getInputStream());
			printLines(command + " stderr:", pro.getInputStream());
			pro.waitFor();
			System.out.println(command + " exitValue() " + pro.exitValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void newFunc(String type, String name, String func) {
		ArrayList<String> funcs = IOTools.readFile(new File("help/funcs.txt"));
		funcs.add("public static " + type + " " + name + " {\n " + func + "\n}");
		IOTools.writeToFile(new File("help/funcs.txt"), funcs);
	}

	public static void javaImp(String string) {
		ArrayList<String> imps = IOTools.readFile(new File("help/imports.txt"));
		if (!imps.contains(string)) {
			imps.add("import " + string + ";");

		}
		IOTools.writeToFile(new File("help/imports.txt"), imps);
	}
}
