import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.jpl7.Query;
import java.util.Scanner;

//  How to run this code:
// Call the solve/5 predicate and pass to it the inputs
//  solve(M, N, Bomb1, Bomb2, Dominos)
//  M = Rows, N = Columns
//  Bomb1/Bomb2 = set of (x, y) for its placement 
//  and Dominos is the ganerated output.

//  Possible testcases: 
//  solve(3,3,(1,3),(2,1),Dominos).
//  solve(2,4,(2,2),(2,3),Dominos).
// ==========================================================================
public class App implements ActionListener {
    static JCheckBox uninformed = new JCheckBox("uninformed");
    static JCheckBox informed = new JCheckBox("informed");
    static JTextArea input = new JTextArea(1, 1);

    public App() {
        JFrame screenFrame = new JFrame("inputs");
        screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screenFrame.setSize(600, 200);
        JPanel panel = new JPanel();
        input.setSize(300, 100);
        JButton button = new JButton("Start");
        button.addActionListener(this);
        input.setBackground(Color.CYAN);
        input.setLineWrap(true);
        input.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panel.add(input);
        panel.add(uninformed);
        panel.add(informed);
        panel.add(button, BorderLayout.SOUTH);
        screenFrame.add(panel, BorderLayout.NORTH);
        screenFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (informed.isSelected()) {
            Query query = new Query("consult('Q2.pl')");
            query.hasSolution();
            String Z = input.getText();
            Z = Z.substring(0, Z.length() - 1);
            query = new Query(
                    "tell('output.txt')," + Z + ",maplist(writeln, Dominos),told.");
            query.allSolutions();
            Queue<String> strings = new LinkedList<>();
            File myObj = new File("output.txt");
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    strings.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            int size = strings.size();
            for (int round = 0; round < size; round++) {
                String first = strings.poll();
                first = first.substring(1, first.length() - 1);
                System.out.println(first);
                String[] tokens = first.split("\\),\\(");
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] = tokens[i].replaceAll("\\(|\\)", "");
                }
                char[][] board = new char[Z.charAt(6) - '0'][Z.charAt(8) - '0'];
                for (int i = 0; i < tokens.length; i++) {
                    int i1 = tokens[i].charAt(0) - '0';
                    int j1 = tokens[i].charAt(2) - '0';
                    int i2 = tokens[i].charAt(4) - '0';
                    int j2 = tokens[i].charAt(6) - '0';
                    char d = tokens[i].charAt(8);
                    board[i1 - 1][j1 - 1] = d;
                    board[i2 - 1][j2 - 1] = d;
                }
                board[Z.charAt(11) - '0' - 1][Z.charAt(13) - '0' - 1] = 'b';
                board[Z.charAt(17) - '0' - 1][Z.charAt(19) - '0' - 1] = 'b';
                JPanel pJPanel = new JPanel(new GridLayout(Z.charAt(6) - '0', Z.charAt(8) - '0'));
                try {
                    BufferedImage bomb = ImageIO.read(new File("bomb.png"));
                    Image scaledBomb = bomb.getScaledInstance(100, 100, Image.SCALE_SMOOTH);                    Image domino_up = ImageIO.read(new File("Up.png"));
                    Image domino_down = ImageIO.read(new File("Down.png"));
                    Image domino_left = ImageIO.read(new File("Left.png"));
                    Image domino_right = ImageIO.read(new File("right.png"));
                    JLabel LL;
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length; j++) {
                            switch (board[i][j]) {
                                case 'v':
                                    LL = new JLabel(new ImageIcon(domino_up));
                                    board[i + 1][j] = 'd';
                                    pJPanel.add(LL);
                                    break;
                                case 'd':
                                    LL = new JLabel(new ImageIcon(domino_down));
                                    pJPanel.add(LL);
                                    break;
                                case 'h':
                                    LL = new JLabel(new ImageIcon(domino_left));
                                    pJPanel.add(LL);
                                    board[i][j + 1] = 'r';
                                    break;
                                case 'r':
                                    LL = new JLabel(new ImageIcon(domino_right));
                                    pJPanel.add(LL);
                                    break;
                                case 'b':
                                    LL = new JLabel(new ImageIcon(scaledBomb));
                                    pJPanel.add(LL);
                                    break;
                                default:
                                    LL = new JLabel(new ImageIcon());
                                    pJPanel.add(LL);
                                    break;
                            }
                        }
                    }
                    JFrame m = new JFrame("solutions");
                    m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    m.setSize(500, 500);
                    m.getContentPane().add(pJPanel);
                    m.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (uninformed.isSelected()) {
            Query query = new Query("consult('Q1.pl')");
            query.hasSolution();
            String Z = input.getText();
            Z = Z.substring(0, Z.length() - 1);
            query = new Query(
                    "tell('output.txt')," + Z + ",maplist(writeln, Dominos),told.");
            query.allSolutions();
            Queue<String> strings = new LinkedList<>();
            File myObj = new File("output.txt");
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    strings.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            int size = strings.size();
            for (int round = 0; round < size; round++) {
                String first = strings.poll();
                first = first.substring(1, first.length() - 1);
                System.out.println(first);
                String[] tokens = first.split("\\),\\(");
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] = tokens[i].replaceAll("\\(|\\)", "");
                }
                char[][] board = new char[Z.charAt(6) - '0'][Z.charAt(8) - '0'];
                for (int i = 0; i < tokens.length; i++) {
                    int i1 = tokens[i].charAt(0) - '0';
                    int j1 = tokens[i].charAt(2) - '0';
                    int i2 = tokens[i].charAt(4) - '0';
                    int j2 = tokens[i].charAt(6) - '0';
                    char d = tokens[i].charAt(8);
                    board[i1 - 1][j1 - 1] = d;
                    board[i2 - 1][j2 - 1] = d;
                }
                board[Z.charAt(11) - '0' - 1][Z.charAt(13) - '0' - 1] = 'b';
                board[Z.charAt(17) - '0' - 1][Z.charAt(19) - '0' - 1] = 'b';
                JPanel pJPanel = new JPanel(new GridLayout(Z.charAt(6) - '0', Z.charAt(8) - '0'));
                try {
                    BufferedImage bomb = ImageIO.read(new File("bomb.png"));
                    Image scaledBomb = bomb.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    Image domino_up = ImageIO.read(new File("Up.png"));
                    Image domino_down = ImageIO.read(new File("Down.png"));
                    Image domino_left = ImageIO.read(new File("Left.png"));
                    Image domino_right = ImageIO.read(new File("right.png"));
                    JLabel LL;
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board[i].length; j++) {
                            switch (board[i][j]) {
                                case 'v':
                                    LL = new JLabel(new ImageIcon(domino_up));
                                    board[i + 1][j] = 'd';
                                    pJPanel.add(LL);
                                    break;
                                case 'd':
                                    LL = new JLabel(new ImageIcon(domino_down));
                                    pJPanel.add(LL);
                                    break;
                                case 'h':
                                    LL = new JLabel(new ImageIcon(domino_left));
                                    pJPanel.add(LL);
                                    board[i][j + 1] = 'r';
                                    break;
                                case 'r':
                                    LL = new JLabel(new ImageIcon(domino_right));
                                    pJPanel.add(LL);
                                    break;
                                case 'b':
                                    LL = new JLabel(new ImageIcon(scaledBomb));
                                    pJPanel.add(LL);
                                    break;
                                default:
                                    LL = new JLabel(new ImageIcon());
                                    pJPanel.add(LL);
                                    break;
                            }
                        }
                    }
                    JFrame m = new JFrame("solutions");
                    m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    m.setSize(500, 500);
                    m.getContentPane().add(pJPanel);
                    m.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}