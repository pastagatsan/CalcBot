package cb;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



public class CommandHandler {
	static String user;
	static String line;
	static Random rand = new Random();
	public static void sendAccordingToMSG(String send) {
		if (line.toLowerCase().contains("privmsg calcbot")) {
			Calcbot.pm(user, send);
		} else {
			Calcbot.send(send);
		}
	}

	public static void userJoinEvent(String user) {
		
	}

	public static void handleCommand(String _user, String _line, String cmd) {
		user = _user;
		line = _line;
		String[] args = cmd.split(" ");
		String command = args[0];
		
		switch (command) {
		case "help":
			ArrayList<String> help = IOTools.readFile(new File("help/help.txt"));
			for (String s : help) {
				Calcbot.pm(user, s);
			}
			break;
		case "conv":
			if (notEnoughParameters(args, 3)) {
				return;
			}
			
			String answer = CalcTools.convert(args[1], args[2], args[3]);
			sendAccordingToMSG(answer);
			break;
		case "rand":
			if (args.length == 2) {
				sendAccordingToMSG(CalcTools.rand(args[1]));
			} else if (args.length == 3) {
				sendAccordingToMSG(CalcTools.rand(args[1], args[2]));
			} else {
				sendAccordingToMSG("Incorrect amount of parameters!");
			}
			break;
		case "mm":
			if (notEnoughParameters(args, 1)) {
				return;
			}
			String s = "";
			for (String i : args) {
				s = s.concat(i + " ");
			}
			sendAccordingToMSG(CalcTools.guessEmotion(s));
			break;
		case "java":
			if (notEnoughParameters(args, 1)) {
				return;
			}
			String s2 = "";
			for (int i = 1; i < args.length; i++) {
				s2 = s2.concat(args[i] + " ");
			}
			try {
				CalcTools.runJava(s2);
			} catch (IOException e) {
				e.printStackTrace();
				CommandHandler.sendAccordingToMSG("Error");
			}
			
			
			break;
		case "javaimp":
			if (notEnoughParameters(args, 1)) {
				return;
			}
			CalcTools.javaImp(args[1]);
			break;
		case "javafunc":
			if (notEnoughParameters(args, 3)) {
				return;
			}
			String methodDecl = cmd.substring(cmd.indexOf(args[2]), cmd.indexOf("|"));
			CalcTools.newFunc(args[1], methodDecl, cmd.substring(cmd.indexOf("|") + 1, cmd.length()));
			break;
		}
	}

	private static boolean notEnoughParameters(String[] args, int i) {
		if (args.length-1 < i) {
			sendAccordingToMSG("Not enough parameters!");
			return true;
		} else {
			return false;
		}
	}
}
