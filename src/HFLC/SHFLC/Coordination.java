package HFLC.SHFLC;

import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
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

    
    //Context of activation antecedents
	private IT2_Antecedent obstacleLow;
	private IT2_Antecedent leftWallLow;
	private IT2_Antecedent rightWallLow; 
	
    private IT2_Rulebase rulebase;
    
    /**
     * Coordination
     * 
     * The coordination layer constructor
     */
    public Coordination(double maxObstacle, double maxWallFollow, double obstacleLowerLeft, double obstacleLowerRight, double obstacleUpperLeft, double obstacleUpperRight, double leftLowerLeft, double leftLowerRight, double leftUpperLeft, double leftUpperRight, double rightLowerLeft, double rightLowerRight, double rightUpperLeft, double rightUpperRight) {
    	createInputs(maxObstacle, maxWallFollow);
    	createObstacleMF(obstacleLowerLeft, obstacleLowerRight, obstacleUpperLeft, obstacleUpperRight);
    	createLeftMF(leftLowerLeft, leftLowerRight, leftUpperLeft, leftUpperRight);
    	createRightMF(rightLowerLeft, rightLowerRight, rightUpperLeft, rightUpperRight);
    	createAntecedents();

    }
    
    /**
     * createInputs() 
     * 
     * Creates Input fuzzy sets
     */
    
    private void createInputs(double maxObstacle, double maxWallFollow) {
		leftWallContext = new Input("Left wall following", new Tuple(0,maxWallFollow));
		rightWallContext = new Input("Right wall following", new Tuple(0,maxWallFollow));
		obstacleContext = new Input("Obstacle avoidance", new Tuple(0,maxObstacle));
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
    }

	/**
	 * createRulebase()
	 * 
	 * Creates the coordination rulebase.
	 * @param leftWallFollow
	 * @param rightWallFollow
	 * @param obstacleAvoidance
	 */
	protected void createRulebase(IT2_Consequent leftWallFollowLeft,IT2_Consequent leftWallFollowRight, IT2_Consequent rightWallFollowLeft, IT2_Consequent rightWallFollowRight,IT2_Consequent obstacleAvoidanceLeft, IT2_Consequent obstacleAvoidanceRight) {
		rulebase = new IT2_Rulebase(3);
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{leftWallLow}, new IT2_Consequent[]{leftWallFollowLeft, leftWallFollowRight}));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{rightWallLow}, new IT2_Consequent[]{rightWallFollowLeft, rightWallFollowRight}));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{obstacleLow}, new IT2_Consequent[]{obstacleAvoidanceLeft, obstacleAvoidanceRight}));
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
}
