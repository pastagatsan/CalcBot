package cb;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Calcbot {
	static BufferedReader reader = null;
	static BufferedWriter writer = null;
	public static String channel;
	public static boolean doFunction = true;

	public static ArrayList<String> users = new ArrayList<String>();
	public static ArrayList<String> usersNewMail = new ArrayList<String>();

	public static void main(String[] args) {
		// The server to connect to and our details.
		String server = "irc.freenode.net";
		String nick = "CalcBot";
		String login = "CalcBot";

		// The channel which the bot will join.
		Scanner input = new Scanner(System.in);
		System.out.println("Input Channel Name (Include hashtag please)");
		channel = input.nextLine();
		System.out.println("Channel = '" + channel + "'. Thank you!");
		// Connect directly to the IRC server.
		System.out.println("Initialized vars");


		try{
			Socket socket = new Socket(server, 6667);
			writer = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream( )));
			reader = new BufferedReader(
					new InputStreamReader(socket.getInputStream( )));

			// Log on to the server.
			writer.write("NICK " + nick + "\r\n");
			writer.write("USER " + login + " 8 * : CalcBot: Doing random math stuff since 2013\r\n");
			writer.flush( );

			// Read lines from the server until it tells us we have connected.
			String line = null;
			
			while ((line = reader.readLine( )) != null) {

				if (line.startsWith("PING ")) {
					writer.write("PONG " + line.substring(5) + "\r\n");
					writer.flush();
				}

				if (line.indexOf("004") >= 0) {
					// We are now logged in.

					break;
				}
				if (line.indexOf("353") >= 0) {
					String s = line.substring(line.indexOf(channel + " :") + (channel + " :").length() );
					String[] names = s.split(" ");
					for (int i = 0; i < names.length; i++) {
						users.add(names[i]);
					}
				}
				else if (line.indexOf("433") >= 0) {
					System.out.println("Nickname is already in use.");
					return;
				}


				System.out.println("[<<]" + line);
			}


			// Join the channel.
			writer.write("JOIN " + channel + "\r\n");
			writer.flush( );

		}catch(IOException e){
			e.printStackTrace();
		}

		String line = "";
		pm("NickServ", "IDENTIFY cakeparty333");
		try{
			while ((line = reader.readLine()) != null) {
				System.out.println("[<<]" + line);
				if (line.startsWith("PING ")) {
					// We must respond to PINGs to avoid being disconnected.

					writer.write("PONG " + line.substring(5) + "\r\n");
					writer.flush( );
				}
				if (line.contains(":!c")) {

					try {
						String cmd = line.substring(line.indexOf(":!c") + 4);
						String user = line.substring(1, line.indexOf("!"));
						CommandHandler.handleCommand(user, line, cmd);

					}catch(Exception e){
						e.printStackTrace();
					}
				}

			}

			if (!line.contains("PRIVMSG")) {
				if (line.contains("JOIN :" + channel)) {

					String user = line.substring(1, line.indexOf("!"));
					users.add(user);
				}

				if (line.contains("QUIT :") || line.contains("PART :")) {
					String user = line.substring(1, line.indexOf("!"));
					if (users.contains(user)) {
						users.remove(user);
					}
				}

				if (line.contains("NICK :")) {
					String user = line.substring(1, line.indexOf("!"));
					String newNick = line.substring(line.indexOf("NICK :") + 6);
					if (users.contains(user)) {
						users.remove(user);
						users.add(newNick);
					}
				}

			}


		}catch(Exception ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void send(String msg) {
		try {
			writer.write("PRIVMSG "+channel+" :" + msg + "\r\n");
			writer.flush();
			System.out.println("[>>]" + "PRIVMSG "+channel+" :" + msg);
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public static void pm(String user, String msg) {
		try {
			writer.write("PRIVMSG "+user+" :" + msg + "\r\n");
			writer.flush();
			System.out.println("[>>]" + "PRIVMSG "+user+" :" + msg);
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static String help(String msg, String user) {
		return("Help File Link: http://m.uploadedit.com/b017/1372934998896.txt");
	}
}
