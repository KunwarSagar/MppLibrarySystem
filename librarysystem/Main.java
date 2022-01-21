package librarysystem;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			//readMembers("MEMBERS");
			LoginScreen.INSTANCE.setTitle(ConstantConfig.APP_NAME);
			LoginScreen.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			LoginScreen.INSTANCE.init();
			centerFrameOnDesktop(LoginScreen.INSTANCE);
			LoginScreen.INSTANCE.setVisible(true);
		});
	}

	public static void centerFrameOnDesktop(Component f) {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
		
	}

	public static void readMembers(String type) {

		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir") + "/src/dataaccess/storage",
					"USERS");
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
			//System.out.println(retVal.toString());
			//writeMembers(type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

	}

	public static void writeMembers(String type) {

		ObjectOutputStream out = null;
		
		try {
				Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir") + "/src/dataaccess/storage",
				"CHECKOUT");
				out = new ObjectOutputStream(Files.newOutputStream(path));
			out.reset();;
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}	
			}
		}

	}
}
