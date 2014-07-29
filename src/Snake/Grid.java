
package Snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Grid implements Runnable{
    private Thread gameThread;
    private boolean gameRunning = false;
    private boolean gamePaused = false;
    private int score = 0;
    
    private GridBlock[][] gridArray;
    private LinkedList<Point> snake;
    private int[][] levelModel;

    private int snakeDirection;
    public static final int NORTH = 1;  public static final int SOUTH = 2;
    public static final int WEST = 3;   public static final int EAST = 4;
      
    private JFrame mainFrame;
    private JPanel gridPanel;
    private JLabel scoreLabel;
    
  
     @Override
    public void run() {
           tick();               
    }
    
    public void tick(){       
        while (gameRunning) {            
              paint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
            move(snakeDirection);
            update();       
        }
    }
    
    public void update(){ 
        boolean fruitExists = false;
        scoreLabel.setText("Score: " + score);
        
         for (GridBlock[] arr2 : gridArray) {
             for (GridBlock val : arr2) {
                 if(val.type == GridBlock.FRUIT){ fruitExists = true;} 
             }
         }
            
            if (!fruitExists) {
             generateFruit();
             growSnake(); 
             score += 9;
         }
                   
            updateSnake();
         
     }
   
    private void growSnake(){
        snake.addLast(new Point(snake.getLast().x, snake.getLast().y));
    }
     
     public void startGame(){
        prepareGame();
        addSnaketoGrid();
        gameThread = new Thread(this);
        gameThread.start(); 
        gameRunning = true;
     }
     
     private void addSnaketoGrid(){     
         for (int i = 0; i < snake.size() - 2; i++) {
             gridArray[snake.get(i).x][snake.get(i).y].setType(GridBlock.SNAKE);
         }
         gridArray[snake.getLast().x][snake.getLast().y].setType(GridBlock.SNAKE_HEAD);
     }
     
     private void setUpGridPanel(){
     
         levelModel = new int[][]{
             {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
             {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}    
         };
         
         for (int i = 0; i < levelModel.length; i++) {
             for (int j = 0; j < levelModel[i].length; j++) {
                 if(levelModel[i][j] == 1){ 
                     GridBlock temp = new GridBlock(GridBlock.WALL);
                     gridArray[i][j] = temp;
                     gridPanel.add(temp);
             } else {
                     GridBlock temp = new GridBlock(GridBlock.EMPTY);
                     gridArray[i][j] = temp;
                     gridPanel.add(temp);
                     
                 }}
         }     
         
     }
     
      private void initilizeSnake(){
        snake = new LinkedList<>();
         snake.add(new Point(15, 12));
         snake.add(new Point(15, 13));
         snake.add(new Point(15, 14));
         snake.add(new Point(15, 15));        
         snakeDirection = EAST;
    }
     
     public void paint(){   
         gridPanel.repaint();                            
     }
       
     
     private void updateSnake(){
         for (int i = 0; i < snake.size() - 1; i++) {
               gridArray[snake.get(i).x][snake.get(i).y].setType(GridBlock.SNAKE);
         }
            gridArray[snake.getLast().x][snake.getLast().y].setType(GridBlock.SNAKE_HEAD);
     }
     
     private void generateFruit(){
         int randX  = (int)(Math.random() * 29) + 1;
         int randY =  (int)(Math.random() * 29) + 1;  
         if (gridArray[randX][randY].isEmpty()) {
             gridArray[randX][randY].setToFruit();
         }
     }
     
      public void prepareGame(){
         
        initilizeSnake();             
        gridArray = new GridBlock[30][30];
        
        mainFrame = new JFrame("Snake");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(620, 600);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setBackground(Color.DARK_GRAY);       
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(30, 30));
        gridPanel.setPreferredSize(new Dimension(300, 300));      
        setUpGridPanel();      
        mainFrame.getContentPane().add(gridPanel);        
        mainFrame.setLocationRelativeTo(null);       
        mainFrame.setVisible(true);
        
        scoreLabel = new JLabel();
        mainFrame.getContentPane().add(BorderLayout.NORTH, scoreLabel);
        
        KeyListener keyListner = new MoveWithKeyListener();
        gridPanel.addKeyListener(keyListner);
        gridPanel.setFocusable(true);

        generateFruit();
        
        score = 0;
    }
    
     
     //Snake Movement**************************************************************
     private void loseTail(){
         gridArray[snake.get(0).x][snake.get(0).y].setType(GridBlock.EMPTY);
         snake.remove(0);
     }
     
      public void moveUp(){ 
          Point pointToAdd =  new Point(snake.get(snake.size()-1).x - 1, snake.get(snake.size()-1).y);
          
          if (gridArray[pointToAdd.x ][pointToAdd.y].isEmpty() || 
                  gridArray[pointToAdd.x][pointToAdd.y].isFruit()) {
              snake.add(snake.size(), pointToAdd);
              loseTail();             
          } else {gameRunning = false;}         
     }
      
      public void moveDown(){     
          Point pointToAdd =  new Point(snake.get(snake.size()-1).x + 1, snake.get(snake.size()-1).y);
          
          if (gridArray[pointToAdd.x][pointToAdd.y].isEmpty()||
               gridArray[pointToAdd.x][pointToAdd.y].isFruit()   ) {
              snake.add(snake.size(), pointToAdd);
              loseTail();  
          }  else {gameRunning = false;}        
     }
     
     public void moveRight(){ 
         Point pointToAdd =  new Point(snake.get(snake.size()-1).x, snake.get(snake.size()-1).y + 1);
                 
         if (gridArray[pointToAdd.x][pointToAdd.y].isEmpty()||
              gridArray[pointToAdd.x][pointToAdd.y].isFruit()   ) {
             snake.add(snake.size(), pointToAdd);
             loseTail();  
         } else {gameRunning = false;}       
     }
     
     public void moveleft(){    
         Point pointToAdd =  new Point(snake.get(snake.size()-1).x, snake.get(snake.size()-1).y - 1);
         
         if (gridArray[pointToAdd.x][pointToAdd.y].isEmpty()||
              gridArray[pointToAdd.x][pointToAdd.y].isFruit()   ) {
            snake.add(snake.size(), pointToAdd);
            loseTail();   
         } else {gameRunning = false;}
         
     }
     
  
    public void move(int direction) {
        if (!gamePaused) {
            switch (direction) {
                case 1:
                    moveUp();
                    break;
                case 2:
                    moveDown();
                    break;
                case 3:
                    moveleft();
                    break;
                case 4:
                    moveRight();
                    break;
            }
        }
    }
    

    class MoveWithKeyListener implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {
             int keyCode = e.getKeyCode();
     
        switch (keyCode) {
            case KeyEvent.VK_UP: gamePaused = false; 
                if (snakeDirection == SOUTH) {snakeDirection = SOUTH;} else snakeDirection = NORTH;  break; 
            case KeyEvent.VK_DOWN:
                gamePaused = false;
                if (snakeDirection == NORTH) {snakeDirection = NORTH;} else snakeDirection = SOUTH; break;
            case KeyEvent.VK_LEFT:
                gamePaused = false;
                if (snakeDirection == EAST) {snakeDirection = EAST;} else snakeDirection = WEST;  break;
            case KeyEvent.VK_RIGHT: 
                gamePaused = false;
                if (snakeDirection == WEST) {snakeDirection = WEST;} else snakeDirection = EAST;  break;
            case KeyEvent.VK_ENTER:
                gamePaused = true;
                if (!gameRunning) {
                    prepareGame();
                    startGame();
                }
                break;       
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}       
        @Override
        public void keyTyped(KeyEvent e) {}
    
    }
    
 
    
    
    
}
