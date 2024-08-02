


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Title: TimeCounter.java
 * Description: This class is a timer helps count the time costed in a round.
 * @author Chenyang Li
 * @version 1.1
 */

public class TimeCounter {
    /**
     * This is a timer used to count time.
     */
    private Timer mytimer;
    /**
     * This is used to record how much time has been taken.
     */
    private int timeCost;
    /**
     * This is the initial delay for the timer..
     */
    private final int initialDelay = 1000;
    private ActionListener taskPerformer;
    /** This is the max time allowed in a game
     */
    private int maxTime=300;

    /**
     * This is the constructor of TimeCounter.
     * It also increases the timecost by one per second.
     * @param UI The User interface which needs time information from TimeCounter
     */
    public TimeCounter(EnhancedSuperHeroUI UI)
    {
        taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeCost++;
                if(timeCost<=maxTime){
                UI.getCountdownLabel().setText("You have: "+ (maxTime-timeCost) +"s");}
                else UI.getCountdownLabel().setText("You have run out of time!");
            }
        };
        this.mytimer=new Timer(initialDelay,taskPerformer);

    }

    /**
     * This method is used to get the time taken to finish this round
     * @return int the seconds taken to finish this round.
     */
    public int getTimeCost()
    {
        return this.timeCost;
    }
    /**
     * This method is used to reset the time cost to 0 at a new round.
     */
    public void resetTimecost()
    {
        this.timeCost=0;
    }

    /**
     * This method is used to stop the TimeCounter by calling a timer object to stop.
     */
    public void stop()
    {
        mytimer.stop();;
    }

    /**
     * This method is used to start the TimeCounter by calling a timer object to start.
     */
    public void start()
    {
        mytimer.start();
    }

    /**
     * This method is used to get the max allowed time for a round.
     * @return int the max time for a round
     */
    public int getMaxTime()
    {
        return this.maxTime;
    }



}
