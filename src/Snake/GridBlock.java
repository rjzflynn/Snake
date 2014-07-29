
package Snake;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GridBlock extends  JPanel{
    protected int type;
    
    public static final int EMPTY = 0;
    public static final int SNAKE = 1;
    public static final int FRUIT = 2;
    public static final int WALL = 3;
    public static final int SNAKE_HEAD = 4;

    public GridBlock(int type) {
        super();
        this.type =type;
        this.setBackground(Color.white);
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public boolean isEmpty(){
        return this.type == EMPTY;
    }
    
    public boolean isFruit(){
        return this.type == FRUIT;
    }
    
     public boolean isSnake(){
        return this.type == SNAKE;
    }
    
    public void setToFruit() {
        type = FRUIT;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(type){          
            case EMPTY: g.setColor(Color.WHITE); g.fillRect(0, 0, 50, 50); break;
            case SNAKE:g.setColor(new Color(66, 27, 6)); g.fillOval(0, 0, this.getWidth(), this.getHeight()); break;
            case FRUIT: g.setColor(Color.green); g.fillOval(0, 0, this.getWidth(), this.getHeight()); break;  
            case WALL: g.setColor(Color.black); g.fillRect(0, 0, this.getWidth(), this.getHeight()); break;  
            case SNAKE_HEAD: g.setColor(Color.red); g.fillOval(0, 0, this.getWidth(), this.getHeight()); break;
        }
    }

}
