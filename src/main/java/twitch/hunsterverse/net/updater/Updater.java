package twitch.hunsterverse.net.updater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.fusesource.jansi.AnsiConsole;

import twitch.hunsterverse.net.logger.Logger;
import twitch.hunsterverse.net.logger.Logger.Level;

public class Updater {
	
	public static String cwd = System.getProperty("user.dir");
	public static String latestReleasePath = "https://github.com/EclipseKnight/hvstreambot/releases/latest/download/hvstreambot.jar";
	public static String botPath = cwd + File.separator + "hvstreambot.jar";
	public static String os = System.getProperty("os.name").toLowerCase();
	
	public static void main(String[] args) {
		// allows ANSI escape sequences to format console output. For loggers. aka PRETTY COLORS
		AnsiConsole.systemInstall();
		
		if (args.length <= 0) {
			Logger.log(Level.FATAL, "No arguments. Stopping Updater.");
			return;
		}
		
		if ("--update".equals(args[0])) {
			getLatestReleaseJar();
			update();
		}
	}
	
	/**
	 * Fetches latest release from repo
	 * and overwrites current jar
	 */
	public static void getLatestReleaseJar() {
		Logger.log(Level.INFO, "Downloading latest release...");
		try {
			ReadableByteChannel rbc = Channels.newChannel(new URL(latestReleasePath).openStream());
			
			FileOutputStream fos = new FileOutputStream(new File(cwd + File.separator + "hvstreambot.jar"));
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			fos.close();
			rbc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.log(Level.INFO, "Finished downloading.");
	}
	
	/**
	 * runs the update process which starts bot back up and closes updater.
	 */
	public static void update() {
		Thread updateHook = new Thread(() -> {
			Logger.log(Level.FATAL, "Finishing updating process.");
			ProcessBuilder processBuilder = new ProcessBuilder();
			Logger.log(Level.INFO, "Detected OS: " + os);
			
			if (os.contains("win")) {
				processBuilder.command("cmd", "/c", "start", "cmd.exe", "/c", "java", "-jar", botPath);
			}
			
			if (os.contains("linux")) {
				processBuilder.command("gnome-terminal", "--", "java", "-jar", botPath);
			}
			
			if (os.contains("mac")) {
				processBuilder.command("/bin/bash", "-c", "java", "-jar", botPath);
			}
			
			try {
				processBuilder.start();
			} catch (IOException e) {
				e.printStackTrace();
				Logger.log(Level.FATAL, "Update process execution failed...");
			}
			
		});
		
		Runtime.getRuntime().addShutdownHook(updateHook);
		System.exit(100);
	}
}
