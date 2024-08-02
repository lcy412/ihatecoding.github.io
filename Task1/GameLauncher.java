/**
 * Title: GameLauncher.java
 * Description: This class is used to launch the internal logic setting of SuperHero TT
 * @author Chenyang Li
 * @version 1.1
 */
public class GameLauncher {
    /**
     * This is the multiplicand user choosed from the list
     */
    private int multiplicand;
    /**
     * This is the random multiplicand
     */
    private int random;
    /**
     * This is the current number of given question.
     */
    private int questionNum;
    /**
     * This is the number of User's correct answer.
     */
    private int correctNum;
    /**
     * This is the correct answer of question.
     */
    private int product;
    /**
     * This is the max number of questions can be given in a round
     */
    private int maxQuestion=5;


    /**
     * This method is used to reset the game by setting the number of questions
     * and the number of correct user's answers to 0;
     */
    public void resetGame() {
        questionNum = 0;
        correctNum = 0;
    }
    /**
     * This method is used to generate a new question by getting a random number
     * Math class. It also increases the number of questions by 1.
     * @param multiplicand the multiplicand chosen by user from the list.
     */
    public void newQuestion(int multiplicand) {
        this.multiplicand = multiplicand;
        random = (int) (Math.random() * 12 + 1);
        product=multiplicand*random;
        questionNum++;
    }

    /**
     * This method is used to check whether user's input
     * is a correct answer to the question.
     * @param answer the answer that user has given
     * @return boolean if user is correct, return ture, or return false;
     */

    public boolean checkAnswer(int answer) {
        if (answer == product) {
            correctNum++;
            return true;
        }
        return false;
    }

    /**
     * This method is used to compare the maximum of questions number in a round
     * with the number of given question to decide whether to end this round.
     * @return boolean true for end the round and false for not end.
     */
    public boolean gameEnd() {

        if(this.questionNum==this.maxQuestion)
        {
            return true;
        }else return false;

    }

    /**
     * This method is used to get how many correct answers user has given in the round.
     * @return int the current number of correct answers given in this round.
     */

    public int getCorrectNum() {
        return correctNum;
    }
    /**
     * This method is used to get the current multiplicand chosen by user.
     * @return int the current multiplicand chosen by user.
     */

    public int getMultiplicand() {
        return multiplicand;
    }
    /**
     * This method is used to get the currently used random .
     * @return int the currently used random factor.
     */
    public int getRandom() {
        return random;
    }
    /**
     * This method is used to get the product(correct answer) of current time question.
     * @return int the product(correct answer) of current time question .
     */
    public int getProduct()
    {
        return product;
    }
}


