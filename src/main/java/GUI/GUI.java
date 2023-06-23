package GUI;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import GUI.ImagePanel;

public class GUI extends JFrame implements ActionListener {
    private ImagePanel panelOryg=null;
    private ImagePanel panelEdyt=null;
    public static BufferedImage obrazEdyt=null;
    public static BufferedImage obrazOryg=null;
    public static BufferedImage obrazBackup=null;

    public GUI(){
        this.createGui();
        this.createMenu();
        this.createFrame();
    }
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getActionCommand().equals("Otworz")){
            JFileChooser filechooser=new JFileChooser(".");
            if (filechooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                final File file=filechooser.getSelectedFile();
                try{
                    obrazOryg=ImageIO.read(file);
                    obrazBackup=ImageIO.read(file);
                    setImageOryg(obrazOryg);
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(this,"Blad wczytywania " + file.getName(),"Blad",JOptionPane.INFORMATION_MESSAGE);
                }

            }
        }
        else if (evt.getActionCommand().equals("Zapisz")){
            JFileChooser filechooser=new JFileChooser(".");
            if (filechooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
                try {
                    // retrieve image
                    final File file=filechooser.getSelectedFile();
                    ImageIO.write(obrazEdyt, "png", file);

                } catch (IOException e) {
                }
            }
        }
        else if (evt.getActionCommand().equals("Wyjscie")){
            this.dispose();
        }
        else if (evt.getActionCommand().equals("Kontrast")){

        }
        else if (evt.getActionCommand().equals("Jasność")){

        }
        else if (evt.getActionCommand().equals("Progowanie")){

        }
        else if (evt.getActionCommand().equals("Filtr medianowy")){

        }
    }

    //Metoda wyswietlajaca obrazek
    private void setImageOryg(BufferedImage image){
        panelOryg.setImage(image);
        this.pack();
    }
    private void setImageEdyt(BufferedImage image){
        panelEdyt.setImage(image);
        this.pack();
    }


    // Metoda tworzaca GUI
    private void createGui(){
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.getContentPane().add(mainPanel);
        this.panelOryg=new ImagePanel(400,400);
        this.panelEdyt=new ImagePanel(400,400);
        mainPanel.add(BorderLayout.WEST,new JScrollPane(this.panelOryg));
        mainPanel.add(BorderLayout.EAST,new JScrollPane(this.panelEdyt));

    }
    //Metoda tworzaca menu
    private void createMenu(){

        JMenuBar menuBar=new JMenuBar();
        this.setJMenuBar(menuBar);
        menuBar.setVisible(true);

        JMenu menu=new JMenu("Plik");
        menuBar.add(menu);

        JMenuItem menuItem=new JMenuItem("Otworz");
        menu.add(menuItem);
        menuItem.addActionListener(this);

        menuItem=new JMenuItem("Zapisz");
        menu.add(menuItem);
        menuItem.addActionListener(this);

        //Dodanie separatora
        menu.addSeparator();

        //Utworzenie elementu "Wyjście"
        menuItem=new JMenuItem("Wyjscie");
        menu.add(menuItem);
        menuItem.addActionListener(this);

        //Utworzenie menu "Filtry"
        menu=new JMenu("Modyfikacje");
        menuBar.add(menu);

        //Utworzenie przycisków konkretnych filtrów
        menuItem=new JMenuItem("Filtr medianowy");
        menu.add(menuItem);
        menuItem.addActionListener(this);
        menuItem=new JMenuItem("Jasność");
        menu.add(menuItem);
        menuItem.addActionListener(this);
        menuItem=new JMenuItem("Progowanie");
        menu.add(menuItem);
        menuItem.addActionListener(this);
        menuItem=new JMenuItem("Kontrast");
        menu.add(menuItem);
        menuItem.addActionListener(this);
    }


    //Metoda ustawiajaca wlasciwosci okna
    private void createFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Przetwarzanie obrazy typu DICOM");
        this.pack();
    }


}