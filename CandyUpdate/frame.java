package Candy;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class frame extends JFrame implements ActionListener{

    JButton button;
    JButton button2;
    
    JLabel L1;
    ImageIcon backgroundImage;
    JFrame screen = new JFrame("Candy Crack");
    framegame f;
    

    frame(){
      //Menu screen design
      backgroundImage = new ImageIcon(this.getClass().getResource("backgroundImage.png"));
      L1 = new JLabel(backgroundImage);
      L1.setSize(750, 750);

      //Start button
      button = new JButton();
      button.setBounds(520, 250, 120, 70);
      button.addActionListener(this);
      button.setBackground(new Color(10742705));
      button.setText("START");
      button.setFont(new Font("Arial", Font.BOLD, 25));
      button.setFocusable(false);

      //quit button
      button2 = new JButton();
      button2.setBounds(520, 380, 120, 70);
      button2.addActionListener(this);
      button2.setBackground(new Color(10742705));
      button2.setText("QUIT");
      button2.setFont(new Font("Arial", Font.BOLD, 25));
      button2.setFocusable(false);

      screen.setSize(760,760);
      screen.setResizable(false);
      screen.setLocationRelativeTo(null);
      screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      screen.setLayout(null);

      screen.add(L1);
      L1.add(button);
      L1.add(button2);
        
      ImageIcon icon = new ImageIcon("1888859.png");
      screen.setIconImage(icon.getImage());
      screen.setVisible(true);     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == button) {
        //Start game
        f = new framegame();
        screen.setVisible(false);
        new Thread(f).start();
      }
        
      if(e.getSource() == button2){
        //exit game
        System.exit(0);
      }   
    }
}


