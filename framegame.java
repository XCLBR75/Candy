package Candy;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class framegame extends JFrame implements ActionListener, Runnable {
    private int row = 8;
	private int col = 12;
	private int width = 750;
	private int height = 650;
    private JButton btnNewGame;
	private JButton Quitgame;
	private graphicgame graphicsPanel;
	private JPanel mainPanel;
	ImageIcon icon;

    public framegame() {
		//game frame
		add(mainPanel = createMainPanel());
        setTitle("Candy Crack");
        setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
        icon = new ImageIcon("1888859.png");
		
        setIconImage(icon.getImage());
		setLocationRelativeTo(null);
		setVisible(true);
    }

    private JPanel createMainPanel() {
		//game panel

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createGraphicsPanel(), BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.NORTH);
		return panel;
	}

	private JPanel createGraphicsPanel() {
		//design gameplay background

		graphicsPanel = new graphicgame(this, row, col);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.black);
		panel.add(graphicsPanel);
		
		return panel;
	}

    private JPanel createControlPanel() {
		//design button area
		JPanel panelControl = new JPanel(new BorderLayout(10, 10));
		panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
		panelControl.add(btnNewGame = createButton("New Game"),BorderLayout.PAGE_START);
		panelControl.add(Quitgame = createButton("Quit Game"),BorderLayout.PAGE_END);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(panelControl, BorderLayout.PAGE_START);
		return panel;
	}

    private JButton createButton(String buttonName) {
		//create button
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}
    public void newGame() {
		//create a new game
		graphicsPanel.removeAll();
		mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
		mainPanel.validate();
		mainPanel.setVisible(true);
		
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


    @Override
    public void actionPerformed(ActionEvent e) {
		//action after u click buttons
        if (e.getSource() == btnNewGame) {
			newGame();
		}
		if (e.getSource() == Quitgame){
			System.exit(0);
		}
        
    }

    
	public void showDialogNewGame(String message, String title) {
		//Dialog when u finish the game
		int select = JOptionPane.showOptionDialog(null,
		 message, title,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon,
			null, null);

		if (select == 0) {
			newGame();
		} else {
			System.exit(0);
		}
	}

    
    
}

