package HFLC.SHFLC;

import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
/**
 * Left Wall Following
 * 
 * Left wall following behaviour for singleton fuzzy system.
 * 
 * @author vxj11u
 *
 */
public class LeftWallFollowing extends WallFollowing {

	// Wall follow antecedents
	private IT2_Antecedent closeFront;
	private IT2_Antecedent farFront;
	private IT2_Antecedent closeBack;
	private IT2_Antecedent farBack;

	private IT2_Rulebase rulebase;

	/**
	 * LeftWallFollowing()
	 * 
	 * Left wall following constructor
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
	public LeftWallFollowing(double maxInput, double closeLowerLeft,
			double closeLowerRight, double closeUpperLeft,
			double closeUpperRight, double farLowerLeft, double farLowerRight,
			double farUpperLeft, double farUpperRight,
			IT2_Consequent leftWheelLow, IT2_Consequent leftWheelMedium,
			IT2_Consequent leftWheelHigh, IT2_Consequent rightWheelLow,
			IT2_Consequent rightWheelMedium, IT2_Consequent rightWheelHigh) {
		// super constructor creates the membership functions
		super(maxInput, closeLowerLeft, closeLowerRight, closeUpperLeft,
				closeUpperRight, farLowerLeft, farLowerRight, farUpperLeft,
				farUpperRight, leftWheelLow, leftWheelMedium, leftWheelHigh,
				rightWheelLow, rightWheelMedium, rightWheelHigh);

		createRulebase(leftWheelLow, leftWheelMedium, leftWheelHigh,
				rightWheelLow, rightWheelMedium, rightWheelHigh);
	}

	/**
	 * createRulebase()
	 * 
	 * Creates the left wall following rulebase.
	 * 
	 * @param leftWheelLow
	 * @param leftWheelMedium
	 * @param leftWheelHigh
	 * @param rightWheelLow
	 * @param rightWheelMedium
	 * @param rightWheelHigh
	 */
	protected void createRulebase(IT2_Consequent leftWheelLow,
			IT2_Consequent leftWheelMedium, IT2_Consequent leftWheelHigh,
			IT2_Consequent rightWheelLow, IT2_Consequent rightWheelMedium,
			IT2_Consequent rightWheelHigh) {
		rulebase = new IT2_Rulebase(4);
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { closeFront,
				closeBack }, new IT2_Consequent[] { leftWheelMedium,
				rightWheelMedium }));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { closeFront,
				farBack },
				new IT2_Consequent[] { leftWheelHigh, rightWheelLow }));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { farFront,
				closeBack }, new IT2_Consequent[] { leftWheelLow,
				rightWheelHigh }));
		rulebase.addRule(new IT2_Rule(
				new IT2_Antecedent[] { farFront, farBack },
				new IT2_Consequent[] { leftWheelHigh, rightWheelHigh }));
	}

	/**
	 * getRulebase()
	 * 
	 * @return rulebase
	 */
	public IT2_Rulebase getRulebase() {
		return rulebase;
	}
}
