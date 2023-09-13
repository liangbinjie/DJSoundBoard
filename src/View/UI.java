package View;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import Model.Sonido;

public class UI extends JFrame {
	public boolean editBool = false;
    public UI() {
    	// Create the JFrame
        JFrame frame = new JFrame("Soundboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

     // Create a JToolBar
        JToolBar toolBar = new JToolBar();
        JButton edit = new JButton("Edit");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBool = !editBool;
                System.out.print("Edit: ");
                System.out.println(editBool);
            }
        });
        toolBar.add(edit);
        // Create a JPanel with a GridLayout to hold the buttons
        JPanel panel = new JPanel(new GridLayout(2, 3)); // 2 rows and 3 columns

        // Create six individual buttons with different labels
        JButton button1 = new JButton("Sonido 1");
        JButton button2 = new JButton("Sonido 2");
        JButton button3 = new JButton("Sonido 3");
        JButton button4 = new JButton("Sonido 4");
        JButton button5 = new JButton("Sonido 5");
        JButton button6 = new JButton("Sonido 6");
        Sonido sound1 = new Sonido("Sound1", System.getProperty("user.dir") + "\\sounds\\5.wav");
        Sonido sound2 = new Sonido("Sound2", System.getProperty("user.dir") + "\\sounds\\5.wav");
        Sonido sound3 = new Sonido("Sound3", System.getProperty("user.dir") + "\\sounds\\5.wav");
        Sonido sound4 = new Sonido("Sound4", System.getProperty("user.dir") + "\\sounds\\5.wav");
        Sonido sound5 = new Sonido("Sound5", System.getProperty("user.dir") + "\\sounds\\5.wav");
        Sonido sound6 = new Sonido("Sound6", System.getProperty("user.dir") + "\\sounds\\5.wav");
        
        button1.addActionListener(e -> play(e, sound1));
        button2.addActionListener(e -> play(e, sound2));
        button3.addActionListener(e -> play(e, sound3));
        button4.addActionListener(e -> play(e, sound4));
        button5.addActionListener(e -> play(e, sound5));
        button6.addActionListener(e -> play(e, sound6));
        

        // Add the buttons to the panel
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);

        // Create a container to hold the toolbar and the panel
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(toolBar, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);

        // Set the frame to be visible
        frame.setVisible(true);
    }
    
    public void play(ActionEvent pEvent, Sonido sound) {
    	if (editBool) {
    		JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File file) {
                    return file.getName().toLowerCase().endsWith(".wav") || file.isDirectory();
                }

                public String getDescription() {
                    return "WAV Files (*.wav)";
                }
            });

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                JOptionPane.showMessageDialog(null, "Ruta del sonido seleccionado: " + filePath);
                sound.setRuta(filePath);
            }
    	}
    	if (!editBool) {
    		
	    	try {
	            // Carga el archivo WAV que deseas reproducir
	            File wavFile = new File(sound.getRuta());
	
	            // Crea un objeto AudioInputStream para el archivo WAV
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
	
	            // Obtiene el formato de audio del archivo
	            AudioFormat audioFormat = audioInputStream.getFormat();
	
	            // Crea una línea de datos de audio (SourceDataLine)
	            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
	            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
	
	            // Abre la línea de datos de audio
	            sourceDataLine.open(audioFormat);
	            sourceDataLine.start(); // Inicia la reproducción
	
	            // Lee y reproduce el archivo WAV
	            int bufferSize = 4096;
	            byte[] buffer = new byte[bufferSize];
	            int bytesRead;
	
	            while ((bytesRead = audioInputStream.read(buffer, 0, bufferSize)) != -1) {
	                sourceDataLine.write(buffer, 0, bytesRead);
	            }
	
	            // Espera a que se complete la reproducción
	            sourceDataLine.drain();
	            sourceDataLine.close();
	            audioInputStream.close();
	        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
}