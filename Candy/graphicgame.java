package Candy;
import java.awt.Image;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class graphicgame extends JPanel implements ActionListener {
    private int row;
	private int col;
	private int bound = 2;
	private int size = 50;
    private JButton[][] btn;
	private Point p1 = null;
	private Point p2 = null;
	private gamerule gamerule;
	private line line;
	private framegame frame;
    private Color backGroundColor = Color.green;
	private int item;
    

    public graphicgame(framegame frame, int row, int col){
        this.frame = frame;
		this.row = row + 2;
		this.col = col + 2;
		item = row * col / 2;
		//set display for a map
        setLayout(new GridLayout(row, col, bound, bound));
		setBackground(backGroundColor);
		setPreferredSize(new Dimension((size + bound) * col, (size + bound)* row));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setAlignmentY(JPanel.CENTER_ALIGNMENT);

		newGame();
	}

	public void newGame() {
		//create newgame
		gamerule = new gamerule(this.row, this.col);
		addArrayButton();
	}

    private void addArrayButton() {
		//gain image to buttons
		btn = new JButton[row][col];
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {

				btn[i][j] = createButton(i + "," + j);
				Icon icon = getIcon(gamerule.getMap()[i][j]);
				btn[i][j].setIcon(icon);
				add(btn[i][j]);
			}
		}
	}
	
    private Icon getIcon(int i) {
		//get icons in file "icon"
		int width = 48, height = 48;

		Image image = new ImageIcon(getClass().getResource("/Candy/icon/iconcandy" + i + ".jpg")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height,image.SCALE_SMOOTH));
		return icon;

	}

	private JButton createButton(String action) {
		//create buttons for a map
		JButton btn = new JButton();
		btn.setActionCommand(action);
		btn.setBorder(null);
		btn.addActionListener(this);
		return btn;
	}

    public void execute(Point p1, Point p2) {
		//execute disable button in map
		setDisable(btn[p1.x][p1.y]);
		setDisable(btn[p2.x][p2.y]);
	}

	private void setDisable(JButton btn) {
		//disable buttons
		btn.setIcon(null);
		btn.setBackground(backGroundColor);
		btn.setEnabled(false);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnI = e.getActionCommand();
		int iDot = btnI.lastIndexOf(",");
		int x = Integer.parseInt(btnI.substring(0, iDot));
		int y = Integer.parseInt(btnI.substring(iDot + 1,btnI.length()));

		//when u click the first button, line border of button become red
		if (p1 == null) {
			p1 = new Point(x, y);
			btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
		} 
        else {//when u click the second button, check 2 points
			p2 = new Point(x, y);
			line = gamerule.checkTwoPoint(p1, p2);

			//if 2 points are correct, off that buttons
			if (line != null) {
				gamerule.getMap()[p1.x][p1.y] = 0;
				gamerule.getMap()[p2.x][p2.y] = 0;
				execute(p1, p2);
				line = null;
				item--;//the number of buttons

			}
            //if 2p are not correct, off the red border
			btn[p1.x][p1.y].setBorder(null);
			p1 = null;
			p2 = null;

			//if finish the game, yes no option
			if (item == 0) {
				frame.showDialogNewGame(
						"Congratulations!\nDo you want play again?", "Winner!!!");
			}
    	}
}
}
