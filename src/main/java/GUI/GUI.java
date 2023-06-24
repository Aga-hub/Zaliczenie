package GUI;

import Program.Program;

import java.util.Hashtable;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dcm4che2.data.*;
import org.dcm4che2.io.*;
import org.dcm4che2.util.*;

/**Klasa od interfejsu graficznego */
public class GUI extends JFrame implements ActionListener {
    private ImagePanel panelOryg=null;
    private ImagePanel panelEdyt=null; //panele na obrazki
    private JPanel menu=null; //menu
    private JPanel panelSuwakow=null; //panel suwaków
    private JButton Otwórz, Zapisz, Filtr_medianowy, Progowanie, Jasność, Kontrast, Reset; //przyciski


    public static BufferedImage obrazEdyt=null;
    public static BufferedImage obrazOryg=null;
    public static BufferedImage obrazBackup=null; //obrazki
    public static int suwak_jasnosc = 0;
    public static int suwak_kontrast = 0;
    public static int suwak_progowanie = 0;
    public static int suwak_filtr = 1; //wartości pobierane z suwaków, przekazywwane do metod przetwarzania

    private File file; //ścieżka do pliku

    /**metoda tworząca całą aplikację, z GUI, menu i ramką*/
    public GUI(){
        this.createGui();
        this.createMenu();
        this.createPanelSuwakow();
        this.createFrame();
    }


    @Override
    /**"Słuchacze" wyborów użytkownika - przyciski*/
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getActionCommand().equals("Otwórz")){
            JFileChooser filechooser=new JFileChooser(".");
            if (filechooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                file=filechooser.getSelectedFile(); //zczytywanie wybranego pliku
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
        else if (evt.getActionCommand().equals("Zapisz")){ //zapis pliku
            JFileChooser filechooser=new JFileChooser(".");
            if (filechooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
                try {
                    // retrieve image
                    final File file=filechooser.getSelectedFile();
                    ImageIO.write(obrazEdyt, "png", file);

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this,"Blad zapisu ","Blad",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else if (evt.getActionCommand().equals("Reset")){ //zresetowanie modyfikacji
            try {
                obrazOryg=ImageIO.read(file);
            }
            catch (IOException e) {
            }

            this.obrazEdyt = null;
            setImageEdyt(obrazEdyt);
            revalidate();
            repaint();

        }
        else if (evt.getActionCommand().equals("Kontrast")){ //wywołanie zmiany kontrastu
            if(suwak_kontrast!=0) {
                obrazEdyt = Program.kontrast(obrazOryg, suwak_kontrast);
                setImageEdyt(obrazEdyt);
                obrazOryg = obrazEdyt;
                setImageOryg(obrazBackup);
                repaint();
            }
        }
        else if (evt.getActionCommand().equals("Jasność")){ //wywołanie zmiany jasności
            obrazEdyt=Program.jasnosc(obrazOryg, suwak_jasnosc);
            setImageEdyt(obrazEdyt);
            obrazOryg=obrazEdyt;
            setImageOryg(obrazBackup);
            repaint();
        }
        else if (evt.getActionCommand().equals("Progowanie")){ //wywołanie zmiany progowania
            obrazEdyt=Program.progowanie(obrazOryg, suwak_progowanie);
            setImageEdyt(obrazEdyt);
            obrazOryg=obrazEdyt;
            setImageOryg(obrazBackup);
            repaint();
        }
        else if (evt.getActionCommand().equals("Filtr_medianowy")){ //wywołanie filtracji

            obrazEdyt=Program.filtrMedianowy(obrazOryg, suwak_filtr);
            setImageEdyt(obrazEdyt);
            obrazOryg=obrazEdyt;
            setImageOryg(obrazBackup);
            repaint();
        }
    }

    /**Metoda wyswietlajaca obrazek*/
    private void setImageOryg(BufferedImage image){
        panelOryg.setImage(image);
        this.pack();
    }
    private void setImageEdyt(BufferedImage image){
        panelEdyt.setImage(image);
        this.pack();
    }


    /** Metoda tworzaca GUI*/
    private void createGui(){
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.getContentPane().add(mainPanel);
        this.panelOryg=new ImagePanel(400,400);
        this.panelEdyt=new ImagePanel(400,400);
        this.menu=new JPanel();
        this.panelSuwakow=new JPanel();
        mainPanel.add(BorderLayout.WEST,new JScrollPane(this.panelOryg));
        mainPanel.add(BorderLayout.CENTER,new JScrollPane(this.panelEdyt));
        mainPanel.add(BorderLayout.EAST, this.menu);
        mainPanel.add(BorderLayout.SOUTH, this.panelSuwakow);


    }
    /**Metoda tworzaca menu i przyciski*/
    private void createMenu(){
        Otwórz = new JButton("Otwórz");
        Zapisz = new JButton("Zapisz");
        Filtr_medianowy = new JButton("Filtr_medianowy");
        Progowanie = new JButton("Progowanie");
        Jasność = new JButton("Jasność");
        Kontrast = new JButton("Kontrast");
        Reset = new JButton("Reset");


        Otwórz.setBounds(270,100, 125,25); //ustalenie granic przycisków
        Zapisz.setBounds(270,100, 125,25);
        Filtr_medianowy.setBounds(270,100, 125,25);
        Progowanie.setBounds(270,100, 125,25);
        Jasność.setBounds(270,100, 125,25);
        Kontrast.setBounds(270,100, 125,25);
        GridLayout GridLayout = new GridLayout(0,1); //tworzenie layoutu menu
        menu.setLayout(GridLayout);//ustawienie layoutu menu
        menu.add(Otwórz);
        menu.add(Zapisz);
        menu.add(Filtr_medianowy);
        menu.add(Progowanie);
        menu.add(Jasność);
        menu.add(Kontrast);
        menu.add(Reset); //dodanie przycisków do menu

        Otwórz.addActionListener(this);
        Zapisz.addActionListener(this);
        Filtr_medianowy.addActionListener(this);
        Progowanie.addActionListener(this);
        Jasność.addActionListener(this);
        Kontrast.addActionListener(this);
        Reset.addActionListener(this); //dodanie actionListenerów


    }
    /**tworzenie panelu suwaków*/
    private void createPanelSuwakow(){
        JSlider kontrast = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        JSlider jasnosc = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        JSlider okno_filtracji = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
        JSlider progowanie = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);  //stworzenie suwaków

        //Ustawienia indeksów suwaka
        jasnosc.setMajorTickSpacing(25);
        jasnosc.setPaintTicks(true);
        jasnosc.setPaintLabels(true);

        kontrast.setMajorTickSpacing(5);
        kontrast.setPaintTicks(true);
        kontrast.setPaintLabels(true);

        okno_filtracji.setMajorTickSpacing(1);
        okno_filtracji.setPaintTicks(true);
        okno_filtracji.setPaintLabels(true);

        progowanie.setMajorTickSpacing(50);
        progowanie.setPaintTicks(true);
        progowanie.setPaintLabels(true);

        panelSuwakow.add(okno_filtracji);
        panelSuwakow.add(progowanie);
        panelSuwakow.add(jasnosc);
        panelSuwakow.add(kontrast); //dodanie suwaków do panelu

        //"słuchacze" suwaków
        jasnosc.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                suwak_jasnosc = jasnosc.getValue();
            }
        });
        kontrast.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                suwak_kontrast = kontrast.getValue();
            }
        });
        okno_filtracji.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                suwak_filtr = okno_filtracji.getValue();
            }
        });
        progowanie.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                suwak_progowanie= progowanie.getValue();
            }
        });
    }



    /**Metoda ustawiajaca wlasciwosci okna*/
    private void createFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Przetwarzanie obrazów typu DICOM");
        this.pack();
    }


}