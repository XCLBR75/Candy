package Candy;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class gamerule {
    private int row;
	private int col;
	private int emty = 0;
	private int[][] map;

    public gamerule(int row, int col){
        this.row = row;
        this.col = col;
        createmap();
    }

    private void createmap(){
		//create map for a game
        map = new int[row][col];

        for (int i = 0; i < col; i++) {
			map[0][i] = map[row - 1][i] = 0;
		}
		for (int i = 0; i < row; i++) {
			map[i][0] = map[i][col - 1] = 0;
		}

		Random rand = new Random();
		int imgNumber = 25;
		int maxDouble = imgNumber / 4;
		int imgArr[] = new int[imgNumber + 1];
		ArrayList<Point> listPoint = new ArrayList<Point>();

		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				listPoint.add(new Point(i, j));
			}
		}

		int i = 0;
		
		//create random candy for a map
		do {
			int imgIndex = rand.nextInt(imgNumber) + 1;
			if (imgArr[imgIndex] < maxDouble) {
				imgArr[imgIndex] += 2;
				for (int j = 0; j < 2; j++) {
					try {
						int size = listPoint.size();
						int pointIndex = rand.nextInt(size);
						map[listPoint.get(pointIndex).x][listPoint.get(pointIndex).y] = imgIndex;
						listPoint.remove(pointIndex);
					} catch (Exception e) {
					}
				}
				i++;
			}
		}
		
		while (i < row * col / 2);
	}

    private boolean checkLineX(int y1, int y2, int x) {
		//check 1 line with line x
		int min = Math.min(y1, y2);
		int max = Math.max(y1, y2);
		
		for (int y = min + 1; y < max; y++) {
			if (map[x][y] > emty) { 
				return false;
			}
		}
		return true;
	}

    private boolean checkLineY(int x1, int x2, int y) {
		//check 1 line with line y
		int min = Math.min(x1, x2);
		int max = Math.max(x1, x2);

		for (int x = min + 1; x < max; x++) {
			if (map[x][y] > emty) {
				return false;
			}
		}
		return true;
	}

    private int checkLine2X(Point p1, Point p2) {
		//check 2 line with line x
		Point pMinY = p1, pMaxY = p2;
        
		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		for (int y = pMinY.y; y <= pMaxY.y; y++) {

			if (y > pMinY.y && map[pMinY.x][y] > emty) {
				return -1;
			}

			if ((map[pMaxY.x][y] == emty || y == pMaxY.y)
					&& checkLineY(pMinY.x, pMaxY.x, y)
					&& checkLineX(y, pMaxY.y, pMaxY.x)) {

				return y;
			}
		}
		return -1;
	}

    private int checkLine2Y(Point p1, Point p2) {
		//check 2 line with line y
		Point pMinX = p1, pMaxX = p2;

		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}

		for (int x = pMinX.x; x <= pMaxX.x; x++) {

			if (x > pMinX.x && map[x][pMinX.y] > emty) {
				return -1;
			}

			if ((map[x][pMaxX.y] == emty || x == pMaxX.x)
					&& checkLineX(pMinX.y, pMaxX.y, x)
					&& checkLineY(x, pMaxX.x, pMaxX.y)) {
				return x;
			}
		}

		return -1;
	}

    private int checkLine3X(Point p1, Point p2, int type) {
		//check 3 line with line x
		Point pMinY = p1, pMaxY = p2;

		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}

		int y = pMaxY.y + type;
		int row = pMinY.x;
		int colFinish = pMaxY.y;

		if (type == -1) {
			colFinish = pMinY.y;
			y = pMinY.y + type;
			row = pMaxY.x;
		}

		if ((map[row][colFinish] == emty || pMinY.y == pMaxY.y)
				&& checkLineX(pMinY.y, pMaxY.y, row)) {

			while (map[pMinY.x][y] == emty
					&& map[pMaxY.x][y] == emty) {

				if (checkLineY(pMinY.x, pMaxY.x, y)) {
					return y;
				}

				y += type;
			}
		}
		return -1;
	}
    
    private int checkLine3Y(Point p1, Point p2, int type) {
		//check 3 line with line y
		Point pMinX = p1, pMaxX = p2;

		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}

		int x = pMaxX.x + type;
		int col = pMinX.y;
		int rowFinish = pMaxX.x;

		if (type == -1) {
			rowFinish = pMinX.x;
			x = pMinX.x + type;
			col = pMaxX.y;
		}

		if ((map[rowFinish][col] == emty || pMinX.x == pMaxX.x)
				&& checkLineY(pMinX.x, pMaxX.x, col)) {

			while (map[x][pMinX.y] == emty
					&& map[x][pMaxX.y] == emty) {

				if (checkLineX(pMinX.y, pMaxX.y, x)) {
					return x;
				}

				x += type;
			}
		}
		return -1;
	}

    public line checkTwoPoint(Point p1, Point p2) {
		//Check 2 point not over 3 lines
		if (!p1.equals(p2) && map[p1.x][p1.y] == map[p2.x][p2.y]) {//if 2 points are the same
			
			if (p1.x == p2.x) {//check first line x
				if (checkLineX(p1.y, p2.y, p1.x)) {
					return new line(p1, p2);
				}
			}

			if (p1.y == p2.y) {//check first line y
				if (checkLineY(p1.x, p2.x, p1.y)) {
					return new line(p1, p2);
				}
			}

			int t = -1; 

			if ((t = checkLine2X(p1, p2)) != -1) {//check second line x
				return new line(new Point(p1.x, t), new Point(p2.x, t));
			}

			if ((t = checkLine2Y(p1, p2)) != -1) {//check second line y
				return new line(new Point(t, p1.y), new Point(t, p2.y));
			}

			if ((t = checkLine3X(p1, p2, 1)) != -1) {//check third line x in right
				return new line(new Point(p1.x, t), new Point(p2.x, t));
			}

			if ((t = checkLine3X(p1, p2, -1)) != -1) {//check third line x in left
				return new line(new Point(p1.x, t), new Point(p2.x, t));
			}

			if ((t = checkLine3Y(p1, p2, 1)) != -1) {//check third line y in down
				return new line(new Point(t, p1.y), new Point(t, p2.y));
			}

			if ((t = checkLine3Y(p1, p2, -1)) != -1) {//check third line y in up
				return new line(new Point(t, p1.y), new Point(t, p2.y));
			}
		}
		return null;
	}

    public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
}
