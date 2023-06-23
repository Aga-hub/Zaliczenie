package GUI;

import javax.swing.*;

class main{
    public static void main(String[] args){
            SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            GUI app=new GUI();
                            app.setVisible(true);
                         }
                    });
    }
}