package librarysystem;


import javax.swing.*;

public class MessageBox {

   public static class InnerFrame extends JFrame {

        public void showMessageBox(String message, String messageType) {

            int error = 1;
            
            if(messageType.equals("Error")){
            	error = JOptionPane.ERROR_MESSAGE;
            }
            
            else if(messageType.equals("Info")){
            	error = JOptionPane.INFORMATION_MESSAGE;
            }
            
            else {
            	error = JOptionPane.WARNING_MESSAGE;
            }

            JOptionPane.showMessageDialog(this,
                    message,
                    messageType,
                    error);
        }
    }
}
