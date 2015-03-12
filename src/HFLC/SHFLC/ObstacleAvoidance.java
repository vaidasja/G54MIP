package HFLC.SHFLC;

import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
import generic.Input;
import generic.Tuple;

/**
 * ObstacleAvoidance
 * 
 * Obstacle avoidance behaviour for singleton fuzzy system.
 * 
 * @author vxj11u
 *
 */

public class ObstacleAvoidance extends SonarInputBehaviour {

	// Sonar sensor input
	private Input middle;
	private Input left;
	private Input right;

	// Antecedents
	private IT2_Antecedent closeLeft;
	private IT2_Antecedent farLeft;
	private IT2_Antecedent closeMiddle;
	private IT2_Antecedent farMiddle;
	private IT2_Antecedent closeRight;
	private IT2_Antecedent farRight;
	
	
	private IT2_Rulebase rulebase;

	/**
	 * ObstacleAvoidance()
	 * 
	 * Obstacle avoidance constructor
	 * 
	 * @param maxInput
	 * @param closeLowerLeft
	 * @param closeLowerRight
	 * @param closeUpperLeft
	 * @param closeUpperRight
	 * @param farLowerLeft
	 * @param farLowerRight
	 * @param farUpperLeft
	 * @param farUpperRight
	 */
	public ObstacleAvoidance(double maxInput, double closeLowerLeft,
			double closeLowerRight, double closeUpperLeft,
			double closeUpperRight, double farLowerLeft, double farLowerRight,
			double farUpperLeft, double farUpperRight, IT2_Consequent leftWheelLow, IT2_Consequent leftWheelMedium,IT2_Consequent leftWheelHigh,IT2_Consequent rightWheelLow,IT2_Consequent rightWheelMedium,IT2_Consequent rightWheelHigh) {
		// super constructor creates the membership functions
		super(maxInput, closeLowerLeft, closeLowerRight, closeUpperLeft, closeUpperRight,
				farLowerLeft, farLowerRight, farUpperLeft, farUpperRight);
		createInputs(maxInput);
		createAntecedents();
		createRulebase(leftWheelLow, leftWheelMedium, leftWheelHigh, rightWheelLow, rightWheelMedium, rightWheelHigh);
	}

	/**
	 * createInputs()
	 * 
	 * Creates the inputs
	 * 
	 * @param maxInput
	 */
	protected void createInputs(double maxInput) {
		middle = new Input("Middle sonar", new Tuple(0, maxInput));
		left = new Input("Left sonar", new Tuple(0, maxInput));
		right = new Input("Right sonar", new Tuple(0, maxInput));
	}

	/**
	 * createAntecedents()
	 * 
	 * Creates the antecedents with the previously created membership functions
	 */
	protected void createAntecedents() {
		closeLeft = new IT2_Antecedent("Close Left", closeMF, left);
		farLeft = new IT2_Antecedent("Far Left", farMF, left);
		closeMiddle = new IT2_Antecedent("Close Middle", closeMF, middle);
		farMiddle = new IT2_Antecedent("Far Middle", farMF, middle);
		closeRight = new IT2_Antecedent("Close Right", closeMF, right);
		farRight = new IT2_Antecedent("Far Right", farMF, right);
	}
	
	/**
	 * createRulebase()
	 * 
	 * Creates the obstacle avoidance rulebase.
	 * 
	 * @param leftWheelLow
	 * @param leftWheelMedium
	 * @param leftWheelHigh
	 * @param rightWheelLow
	 * @param rightWheelMedium
	 * @param rightWheelHigh
	 */
	protected void createRulebase(IT2_Consequent leftWheelLow, IT2_Consequent leftWheelMedium,IT2_Consequent leftWheelHigh,IT2_Consequent rightWheelLow,IT2_Consequent rightWheelMedium,IT2_Consequent rightWheelHigh) {
		rulebase = new IT2_Rulebase(8);
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, closeMiddle, closeRight}, new IT2_Consequent[]{leftWheelLow, rightWheelLow}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, closeMiddle, farRight}, new IT2_Consequent[]{leftWheelMedium, rightWheelLow}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, farMiddle, closeRight}, new IT2_Consequent[]{leftWheelMedium, rightWheelMedium}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, farMiddle, farRight}, new IT2_Consequent[]{leftWheelHigh, rightWheelLow}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, closeMiddle, closeRight}, new IT2_Consequent[]{leftWheelLow, rightWheelMedium}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, closeMiddle, farRight}, new IT2_Consequent[]{leftWheelLow, rightWheelLow}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, farMiddle, closeRight}, new IT2_Consequent[]{leftWheelLow, rightWheelMedium}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, farMiddle, farRight}, new IT2_Consequent[]{leftWheelHigh, rightWheelHigh}));
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
	 * sets the sonar input value
	 * @param Left sonar value
	 */
	public void setLeftInput(double leftInput) {
		left.setInput(leftInput);
	}
	
	/**
	 * setRightInput()
	 * 
	 * sets the sonar input value
	 * @param Right sonar value
	 */
	public void setRightInput(double rightInput) {
		right.setInput(rightInput);
	}
	
	/**
	 * setMiddleInput()
	 * 
	 * sets the sonar input value
	 * @param Middle sonar value
	 */
	public void setMiddleInput(double middleInput) {
		middle.setInput(middleInput);
	}
}
