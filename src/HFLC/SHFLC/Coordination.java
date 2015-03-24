package HFLC.SHFLC;

import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
import tools.JMathPlotter;
import type1.sets.T1MF_Trapezoidal;
import generic.Input;
import generic.Tuple;


/**
 * Coordination
 * 
 * The upper layer of the hierarchy. Controls which behaviour to choose
 * @author vxj11u
 *
 */
public class Coordination {
	
	//Context of activation inputs
	private Input leftWallContext;
	private Input rightWallContext;
	private Input obstacleContext;
	private Input goalSeekingContext;
	
	//Context of activation membership functions
	private T1MF_Trapezoidal obstacleLowerMF;
	private T1MF_Trapezoidal obstacleUpperMF;
	private IntervalT2MF_Trapezoidal obstacleMF;
	
	private T1MF_Trapezoidal leftWallLowerMF;
	private T1MF_Trapezoidal leftWallUpperMF;
	private IntervalT2MF_Trapezoidal leftWallMF;
	
	private T1MF_Trapezoidal rightWallLowerMF;
	private T1MF_Trapezoidal rightWallUpperMF;
	private IntervalT2MF_Trapezoidal rightWallMF;	
	
	private T1MF_Trapezoidal goalLowerMF;
	private T1MF_Trapezoidal goalUpperMF;
	private IntervalT2MF_Trapezoidal goalMF;

    
    //Context of activation antecedents
	private IT2_Antecedent obstacleLow;
	private IT2_Antecedent leftWallLow;
	private IT2_Antecedent rightWallLow; 
	private IT2_Antecedent goalHigh;
	
    private IT2_Rulebase rulebase;
    
    /**
     * Coordination
     * 
     * The coordination layer constructor
     */
    public Coordination(double maxObstacle, double maxWallFollow, double maxGoalSeeking, double obstacleLowerLeft, double obstacleLowerRight, double obstacleUpperLeft, double obstacleUpperRight, double leftLowerLeft, double leftLowerRight, double leftUpperLeft, double leftUpperRight, double rightLowerLeft, double rightLowerRight, double rightUpperLeft, double rightUpperRight, double goalLowerLeft, double goalLowerRight, double goalUpperLeft, double goalUpperRight) {
    	createInputs(maxObstacle, maxWallFollow, maxGoalSeeking);
    	createObstacleMF(obstacleLowerLeft, obstacleLowerRight, obstacleUpperLeft, obstacleUpperRight);
    	createLeftMF(leftLowerLeft, leftLowerRight, leftUpperLeft, leftUpperRight);
    	createRightMF(rightLowerLeft, rightLowerRight, rightUpperLeft, rightUpperRight);
    	createGoalMF(maxGoalSeeking, goalLowerLeft, goalLowerRight, goalUpperLeft, goalUpperRight);
    	createAntecedents();
		plotMFs("goal", new IntervalT2MF_Interface[] {goalMF}, 100);
		plotMFs("obstacle", new IntervalT2MF_Interface[] {obstacleMF}, 100);
		plotMFs("left", new IntervalT2MF_Interface[] {leftWallMF}, 100);
		plotMFs("right", new IntervalT2MF_Interface[] {rightWallMF}, 100);

    }
    
    /**
     * createInputs() 
     * 
     * Creates Input fuzzy sets
     */
    
    private void createInputs(double maxObstacle, double maxWallFollow, double maxGoalSeeking) {
		leftWallContext = new Input("Left wall following", new Tuple(0,maxWallFollow));
		rightWallContext = new Input("Right wall following", new Tuple(0,maxWallFollow));
		obstacleContext = new Input("Obstacle avoidance", new Tuple(0,maxObstacle));
		goalSeekingContext = new Input("Goal Seeking", new Tuple(0, maxGoalSeeking));
    }
    
	private void createGoalMF(double maxGoalSeeking, double goalLowerLeft, double goalLowerRight,
			double goalUpperLeft, double goalUpperRight) {
		// Membership function - goal. Definition
		goalLowerMF = new T1MF_Trapezoidal("Lower MF Goal", new double[] {
				goalLowerLeft, goalLowerRight, maxGoalSeeking, maxGoalSeeking });
		goalUpperMF = new T1MF_Trapezoidal("Upper MF Goal", new double[] {
				goalUpperLeft, goalUpperRight, maxGoalSeeking, maxGoalSeeking  });
		goalMF = new IntervalT2MF_Trapezoidal("IT2MF Goal", goalUpperMF,
				goalLowerMF);
	}
	
    /**
     * createObstacleMF()
     * 
     * Creates obstacle avoidance membership function
     */
    private void createObstacleMF(double obstacleLowerLeft, double obstacleLowerRight, double obstacleUpperLeft, double obstacleUpperRight) {
    	obstacleLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, obstacleLowerLeft, obstacleLowerRight});
    	obstacleUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, obstacleUpperLeft, obstacleUpperRight});
    	obstacleMF = new IntervalT2MF_Trapezoidal("Low", obstacleUpperMF, obstacleLowerMF);
    }
    
    /**
     * createLeftMF()
     * 
     * Creates Left wall following membership function
     */
    private void createLeftMF(double leftLowerLeft, double leftLowerRight, double leftUpperLeft, double leftUpperRight) {
    	leftWallLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, leftLowerLeft, leftLowerRight});
    	leftWallUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, leftUpperLeft, leftUpperRight});
    	leftWallMF = new IntervalT2MF_Trapezoidal("Low", leftWallUpperMF, leftWallLowerMF);
    }
    
    /**
     * createRightMF()
     * 
     * Creates Right wall following membership function
     */
    private void createRightMF(double rightLowerLeft, double rightLowerRight, double rightUpperLeft, double rightUpperRight) {
    	rightWallLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, rightLowerLeft, rightLowerRight});
    	rightWallUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, rightUpperLeft, rightUpperRight});
    	rightWallMF = new IntervalT2MF_Trapezoidal("Low", rightWallUpperMF, rightWallLowerMF);
    }
    
    /**
     * createAntecedents()
     * 
     * Creates the antecedents
     */
    private void createAntecedents() {
        obstacleLow = new IT2_Antecedent("Obstacle Distance Low", obstacleMF, obstacleContext);
        leftWallLow = new IT2_Antecedent("Left Wall Distance Low", leftWallMF, leftWallContext);
        rightWallLow = new IT2_Antecedent("Right Wall Distance Low", rightWallMF, rightWallContext);
        goalHigh = new IT2_Antecedent("Obstacle Avoidance High", goalMF, goalSeekingContext);
    }

	/**
	 * createRulebase()
	 * 
	 * Creates the coordination rulebase.
	 * @param leftWallFollow
	 * @param rightWallFollow
	 * @param obstacleAvoidance
	 */
	protected void createRulebase(IT2_Consequent leftWallFollowLeft,IT2_Consequent leftWallFollowRight, IT2_Consequent rightWallFollowLeft, IT2_Consequent rightWallFollowRight,IT2_Consequent obstacleAvoidanceLeft, IT2_Consequent obstacleAvoidanceRight, IT2_Consequent goalSeekingLeft, IT2_Consequent goalSeekingRight) {
		rulebase = new IT2_Rulebase(4);
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{leftWallLow}, new IT2_Consequent[]{leftWallFollowLeft, leftWallFollowRight}));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{rightWallLow}, new IT2_Consequent[]{rightWallFollowLeft, rightWallFollowRight}));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{obstacleLow}, new IT2_Consequent[]{obstacleAvoidanceLeft, obstacleAvoidanceRight}));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{goalHigh}, new IT2_Consequent[]{goalSeekingLeft, goalSeekingRight}));
	}
	
	/**
	 * getRulebase()
	 * 
	 * @return rulebase
	 */
	public IT2_Rulebase getRulebase() {
		return rulebase;
	}
	
	/**
	 * setLeftInput()
	 * 
	 * sets the left wall following input value
	 * @param Left minimum sonar value
	 */
	public void setLeftInput(double leftInput) {
		leftWallContext.setInput(leftInput);
	}
	
	/**
	 * setRightInput()
	 * 
	 * sets the right wall following input value
	 * @param Right minimum sonar value
	 */
	public void setRightInput(double rightInput) {
		rightWallContext.setInput(rightInput);
	}
	
	/**
	 * setObstacleInput()
	 * 
	 * sets the sonar input value
	 * @param Obstacle avoidance minimum sonar value
	 */
	public void setObstacleInput(double obstacleInput) {
		obstacleContext.setInput(obstacleInput);
	}
	
	/**
	 * setGoalInput()
	 * 
	 * Sets the input for the goal seeking behavior as the minimum input of all the other mehaviors
	 */
	public void setGoalInput() {
		if (rightWallContext.getInput() <= leftWallContext.getInput() && rightWallContext.getInput() <= obstacleContext.getInput()) {
			goalSeekingContext.setInput(rightWallContext.getInput());
		} else if (obstacleContext.getInput() <= leftWallContext.getInput() && obstacleContext.getInput() <=rightWallContext.getInput()) {
			goalSeekingContext.setInput(obstacleContext.getInput());
		} else {
			goalSeekingContext.setInput(leftWallContext.getInput());
		}
	}
	
	//helper. Copied from Juzzy. Remove when done
    private void plotMFs(String name, IntervalT2MF_Interface[] sets, int discretizationLevel)
    {
        JMathPlotter plotter = new JMathPlotter();
        plotter.plotMF(sets[0].getName(), sets[0], discretizationLevel, null, false);
       
        for (int i=1;i<sets.length;i++)
        {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, null, false);
        }
        plotter.show(name);
    }
}
