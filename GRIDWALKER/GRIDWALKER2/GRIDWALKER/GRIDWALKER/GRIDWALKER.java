package GRIDWALKER;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GRIDWALKER extends JFrame implements ActionListener {
    private final int numRows;
    private final int numCols;
    private final JButton[][] buttons;
    private boolean [][] visited;
    private int targetRow;
    private int targetCol;
    private int currRow;
    private int currCol;
    private boolean offGrid;
    private boolean intersect;
    private boolean found;
    private int steps;

    //constructor for GRIDWALKER class
    public GRIDWALKER(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        Random random = new Random();
        targetRow = random.nextInt(numRows);
        targetCol = random.nextInt(numCols);
        buttons = new JButton[numRows][numCols];
        visited = new boolean [numRows][numCols];

        // create the buttons and add to Jframe.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                JButton button = new JButton();
                button.addActionListener(this);
                buttons[row][col] = button;
                add(button);
            }
        }

        // set the font and text for each button
        Font font = new Font("Comic Sans", Font.PLAIN, 24);
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                JButton button = buttons[row][col];
                button.setFont(font);
                if (row == targetRow && col == targetCol) {
                    button.setText("\u25CE"); // target symbol
                } else {
                    char[] arrows = {'\u2190', '\u2191', '\u2192', '\u2193'}; // left, up, right, down
                    button.setText(Character.toString(arrows[random.nextInt(arrows.length)]));
                }
            }
        }

        // set the layout and size of the JFrame
        setLayout(new GridLayout(numRows, numCols));
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    //Main method
    public static void main(String[] args) {
        int numRows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows:"));
        int numCols = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns:"));
        GRIDWALKER gridwalker = new GRIDWALKER(numRows, numCols);
    }
    
    //Action listener for handling
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        currRow = 0;
        currCol = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (buttons[row][col] == button) {
                	currRow = row;
                    currCol = col;
                    break;
                }
            }
        }
        //keep walking until the conditions are broken
        while(!intersect && !offGrid && !found) {
            visited[currRow][currCol] = true;
            int prevRow = currRow;
            int prevCol = currCol;
            String buttonText = buttons[currRow][currCol].getText();
            buttons[currRow][currCol].setBackground(Color.YELLOW); 
            if (buttonText.equals("\u2190")) {
                currCol--;
            } else if (buttonText.equals("\u2191")) {
                currRow--;
            } else if (buttonText.equals("\u2192")) {
                currCol++;
            } else if (buttonText.equals("\u2193")) {
                currRow++;
            }

            //if statement to handle offGrid & intersect
            if (currRow < 0 || currRow >= numRows || currCol < 0 || currCol >= numCols) {    		
                
                if (currRow < 0) {
                    currRow = 0;
                } else if (currRow >= numRows) {
                    currRow = numRows - 1;
                }
                if (currCol < 0) {
                    currCol = 0;
                } else if (currCol >= numCols) {
                    currCol = numCols - 1;
                }
                buttons[currRow][currCol].setBackground(Color.RED);   
                JOptionPane.showMessageDialog(button, "You left the grid! D:");
                offGrid = true;
                this.dispose();
            } else if (steps > 1 && visited[currRow][currCol]) {
                buttons[prevRow][prevCol].setBackground(Color.RED);
                intersect = true;
                JOptionPane.showMessageDialog(button, "Your Path intersected itself! D:");
                this.dispose();
                
                
            } else {
                visited[currRow][currCol] = true;
            }
            
            steps++;
  
            // game won if the walker reaches the target
            if (currRow == targetRow && currCol == targetCol) {
                found = true;
                buttons[targetRow][targetCol].setBackground(Color.GREEN);
                JOptionPane.showMessageDialog(this, "Congratulations! You won in " + steps + " steps. :D");
                this.dispose();
                
            } 
        }
    }
}




