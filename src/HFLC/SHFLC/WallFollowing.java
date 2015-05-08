package HFLC.SHFLC;

import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rulebase;
import generic.Input;
import generic.Tuple;

/**
 * Wall Following
 * 
 * Wall following behaviour.
 * 
 * @author vxj11u
 *
 */
public abstract class WallFollowing extends SonarInputBehaviour {

	// Sonar sensor input
	private Input front;
	private Input back;

	// Wall follow antecedents
	protected IT2_Antecedent closeFront;
	protected IT2_Antecedent farFront;
	protected IT2_Antecedent closeBack;
	protected IT2_Antecedent farBack;

	/**
	 * WallFollowing()
	 * 
	 * Wall following constructor
	 * 
	 * @param maxInput
	 * @param closeLowerLeft
	 * @param closeLowerRight
	 * @param closeUpperLeft
	 * @param closeUpperRight
	 * @param farLowerLeft
	 * @param farLowerRight
	 * @param farUpperLeftee
	 * @param farUpperRight
	 */
	public WallFollowing(double maxInput, double closeLowerLeft,
			double closeLowerRight, double closeUpperLeft,
			double closeUpperRight, double farLowerLeft, double farLowerRight,
			double farUpperLeft, double farUpperRight,
			IT2_Consequent leftWheelLow, IT2_Consequent leftWheelMedium,
			IT2_Consequent leftWheelHigh, IT2_Consequent rightWheelLow,
			IT2_Consequent rightWheelMedium, IT2_Consequent rightWheelHigh) {
		
		// super constructor creates the membership functions
		super(maxInput, closeLowerLeft, closeLowerRight, closeUpperLeft, closeUpperRight,
				farLowerLeft, farLowerRight, farUpperLeft, farUpperRight);
		createInputs(maxInput);
		createAntecedents();
		createRulebase(leftWheelLow, leftWheelMedium, leftWheelHigh,
				rightWheelLow, rightWheelMedium, rightWheelHigh);
	}

	/**
	 * createInputs()
	 * 
	 * Creates the inputs
	 * 
	 * @param maxInput
	 */
	protected void createInputs(double maxInput) {
		front = new Input("Front Sonar", new Tuple(0, maxInput));
		back = new Input("Back Sonar", new Tuple(0, maxInput));
	}

	/**
	 * createAntecedents()
	 * 
	 * Creates the antecedents with the previously created membership functions
	 */
	protected void createAntecedents() {
		closeFront = new IT2_Antecedent("Close Front", closeMF, front);
		farFront = new IT2_Antecedent("Far Front", farMF, front);
		closeBack = new IT2_Antecedent("Close Back", closeMF, back);
		farBack = new IT2_Antecedent("Far Back", farMF, back);
	}

	/**
	 * createRulebase()
	 * 
	 * Creates the wall following rulebase.
	 * 
	 * @param leftWheelLow
	 * @param leftWheelMedium
	 * @param leftWheelHigh
	 * @param rightWheelLow
	 * @param rightWheelMedium
	 * @param rightWheelHigh
	 */
	protected abstract void createRulebase(IT2_Consequent leftWheelLow,
			IT2_Consequent leftWheelMedium, IT2_Consequent leftWheelHigh,
			IT2_Consequent rightWheelLow, IT2_Consequent rightWheelMedium,
			IT2_Consequent rightWheelHigh);

	/**
	 * getRulebase()
	 * 
	 * @return rulebase
	 */
	public abstract IT2_Rulebase getRulebase();
	
	/**
	 * setFrontInput()
	 * 
	 * sets the sonar input value
	 * @param Front sonar value
	 */
	public void setFrontInput(double frontInput) {
		front.setInput(frontInput);
	}
	
	/**
	 * setBackInput()
	 * 
	 * sets the sonar input value
	 * @param Back sonar value
	 */
	public void setBackInput(double backInput) {
		back.setInput(backInput);
	}
}
