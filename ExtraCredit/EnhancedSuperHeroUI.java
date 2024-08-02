import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
/**
 * Title: EnhancedSuperHeroUI
 * Description: This class is the User Interface of SuperHero times table
 * @author Chenyang Li
 * @version 1.2
 */
public class EnhancedSuperHeroUI extends JFrame {
    /**
     * This is the Label "Answer", on the left of interface.
     */
    private JLabel answer;
    /**
     * This is the Label "Rating", on the right of interface.
     */
    private JLabel rating;
    /**
     * This is the multiplicands list for user to select from.
     */
    private JList<Integer> list;
    /**
     * This is the button showing "Start" or "Next".
     */
    private JButton startButton;
    /**
     * This is the label used to show the question.
     */
    private JLabel question;
    /**
     * This is the answer box.
     */
    private JTextField answerField;
    /**
     * This is used to store the multiplicands selected by user
     */
    private Integer[] multiplicands;
    /**
     * This is a GameLauncher for setting game logic.
     */
    private GameSetting gameLauncher;
    /**
     * This is the name of the file used to store the picture.
     */
    private String filename="superhero.jpg";
    /**
     * This is label for count-down.
     */

    private JLabel countdown;
    /**
     * This is the time counter to count time.
     */
    private TimeCounter timeCounter;

    /** This is the constructor of class SuperHeroUI. It is used to set panels,
     * labels, etc. and add image to the user interface.
     * @param gameSetting It's a setter used to launch the internal setting of
     *                   game logic.
     */

    public EnhancedSuperHeroUI(GameSetting gameSetting)  {
        this.gameLauncher=gameSetting;
        this.timeCounter=new TimeCounter(this);
        this.setTitle("SuperHero Times Table");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 400);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout());
        JPanel middle = new JPanel(new FlowLayout());
        JPanel center = new JPanel(new BorderLayout());
        JPanel image = new JPanel();
        this.add(mainPanel);

        multiplicands = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        list = new JList<>(multiplicands);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        top.add(scrollPane);

        question = new JLabel("Product");
        middle.add(question);
        answerField = new JTextField(4);
        middle.add(answerField);
        startButton = new JButton("Start");
        middle.add(startButton);
        center.add(middle, BorderLayout.NORTH);

        answer = new JLabel("Answer");
        answer.setPreferredSize(new Dimension(150, answer.getPreferredSize().height));
        center.add(answer, BorderLayout.WEST);

        rating = new JLabel("Rating");
        rating.setPreferredSize(new Dimension(150, rating.getPreferredSize().height));
        center.add(rating, BorderLayout.EAST);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename)));
        JLabel imageLabel = new JLabel(icon);
        image.add(imageLabel);
        center.add(image, BorderLayout.CENTER);

        countdown=new JLabel();
        countdown.setText("You have: "+timeCounter.getMaxTime()+" s");
        center.add(countdown,BorderLayout.SOUTH);

        mainPanel.add(top, BorderLayout.NORTH);
        mainPanel.add(center, BorderLayout.CENTER);

        top.setPreferredSize(new Dimension(400, 100));
        center.setPreferredSize(new Dimension(400, 100));
        middle.setPreferredSize(new Dimension(400, 50));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                answerField.requestFocusInWindow();
            }
        });

        this.setVisible(true);
    }
    /**
     * This method is used to start a game and make changes to the user interface
     * after user clicking the button.
     * It calls different methods and performs differently with different state of
     * the button, text field and the number of questions.
     */

    public void startGame() {
        if (list.getSelectedValuesList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one multiplicand.");
            return;
        }

        if (this.startButton.getText().equals("Start")) {
            newRound();
        } else {
            if (answerField.getText().isEmpty()) {
                return;
            } else {
                if(answerCheck()){
                    if (gameLauncher.gameEnd()) {
                        resultShow();
                    } else {
                        newQuestion();
                    }
                }
            }
        }
    }
    /**
     * This method is called by method startGame and is used to start a new round of
     * game. It calls gameLauncher to reset game and set the text of labels.
     */
    public void newRound() {
        gameLauncher.resetGame();
        timeCounter.resetTimecost();
        startButton.setText("Next");
        answer.setText("Answer");
        rating.setText("Rating");
        timeCounter.start();
        countdown.setText("You have: "+timeCounter.getMaxTime()+" s");
        newQuestion();
    }
    /**
     * This method is used to call gameLauncher to check whether
     * the answer user input is correct and give corresponding feedback
     * to the interface by outputting "correct" or "wrong" and the correct answer.
     * @return boolean true for user entering number, and false for user entering others(such as letter)
     */
    public boolean answerCheck() {
        try {
            int answer = Integer.parseInt(this.answerField.getText());
            if (gameLauncher.checkAnswer(answer)) {
                this.answer.setText("Correct");
            } else {
                int multiplicand = gameLauncher.getMultiplicand();
                int random = gameLauncher.getRandom();
                int product = gameLauncher.getProduct();
                this.answer.setText("Wrong! " + multiplicand + " * " + random + " = " + product);
            }
            this.answerField.setText("");
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            this.answerField.setText("");
            return false;
        }
    }
    /**
     * This method is used to call gameLauncher to give a new question, and show
     * the new question on the interface.
     */
    public void newQuestion() {
        int multiplicand=this.getRandomMultiplicand();
        gameLauncher.newQuestion(multiplicand);
        question.setText(multiplicand + " * " + gameLauncher.getRandom());
    }

    /**
     * This method is to randomly get a multiplicand from all the multiplicands
     * thr user has chosen
     * @return int a multiplicand to be used in a new question
     */
    private int getRandomMultiplicand() {
        java.util.List<Integer> selectedValues = list.getSelectedValuesList();
        return selectedValues.get((int) (Math.random() * selectedValues.size()));
    }
    /**
     * This method is used to show the result of the user's performance
     * after a round of game is over and reset part of UI. It outputs how many correct answers
     * user has given.
     */
    public void resultShow() {
        timeCounter.stop();
        rating.setText("You got " + gameLauncher.getCorrectNum() + " correct!");
        startButton.setText("Start");
        question.setText("Product");
        answer.setText("You took "+ timeCounter.getTimeCost() +" seconds");
    }

    /**
     * This method is used to get the countdown lable.
     * @return JLable The lable for the countdown.
     */
    public JLabel getCountdownLabel()
    {
        return this.countdown;
    }


}
