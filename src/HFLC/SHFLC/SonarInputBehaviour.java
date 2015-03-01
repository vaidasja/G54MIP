package HFLC.SHFLC;

import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rulebase;
import type1.sets.T1MF_Trapezoidal;

/**
 * SonarInputBehaviour
 * 
 * Abstract class which allows creating different parameter MFs for wall
 * following and obstacle avoidance behaviours because the shapes of the MFs are
 * the same.
 * 
 * @author vxj11u
 *
 */
public abstract class SonarInputBehaviour {

	// Membership function - close
	protected T1MF_Trapezoidal closeLowerMF;
	protected T1MF_Trapezoidal closeUpperMF;
	protected IntervalT2MF_Trapezoidal closeMF;

	// Membership function - far
	protected T1MF_Trapezoidal farUpperMF;
	protected T1MF_Trapezoidal farLowerMF;
	protected IntervalT2MF_Trapezoidal farMF;

	/**
	 * SonarInputBehaviour()
	 * 
	 * Constructs the membership functions
	 * 
	 * @param closeLowerLeft
	 * @param closeLowerRight
	 * @param closeUpperLeft
	 * @param closeUpperRight
	 * @param farLowerLeft
	 * @param farLowerRight
	 * @param farUpperLeft
	 * @param farUpperRight
	 */
	public SonarInputBehaviour(double maxInput, double closeLowerLeft, double closeLowerRight,
			double closeUpperLeft, double closeUpperRight, double farLowerLeft,
			double farLowerRight, double farUpperLeft, double farUpperRight) {
		createCloseMF(closeLowerLeft, closeLowerRight, closeUpperLeft,
				closeUpperRight);
		createFarMF(maxInput, farLowerLeft, farLowerRight, farUpperLeft, farUpperRight);
	}
	
	public SonarInputBehaviour() {
		
	}

	/**
	 * createCloseMF()
	 * 
	 * Creates the membership function Close
	 * 
	 * @param closeLowerLeft
	 * @param closeLowerRight
	 * @param closeUpperLeft
	 * @param closeUpperRight
	 */
	private void createCloseMF(double closeLowerLeft, double closeLowerRight,
			double closeUpperLeft, double closeUpperRight) {
		// Membership function - close. Definition
		closeLowerMF = new T1MF_Trapezoidal("Lower MF Close", new double[] {
				0.0, 0.0, closeLowerLeft, closeLowerRight });
		closeUpperMF = new T1MF_Trapezoidal("Upper MF Close", new double[] {
				0.0, 0.0, closeUpperLeft, closeUpperRight });
		closeMF = new IntervalT2MF_Trapezoidal("IT2MF Close", closeUpperMF,
				closeLowerMF);

	}

	/**
	 * createFarMF()
	 * 
	 * Creates the membership function Far
	 * 
	 * @param farLowerLeft
	 * @param farLowerRight
	 * @param farUpperLeft
	 * @param farUpperRight
	 */
	private void createFarMF(double maxInput, double farLowerLeft, double farLowerRight,
			double farUpperLeft, double farUpperRight) {
		// Membership function - far. Definition
		farLowerMF = new T1MF_Trapezoidal("Lower MF Far", new double[] {
				farLowerLeft, farLowerRight, maxInput, maxInput });
		farUpperMF = new T1MF_Trapezoidal("Upper MF Far", new double[] {
				farUpperLeft, farUpperRight, maxInput, maxInput });
		farMF = new IntervalT2MF_Trapezoidal("IT2MF Far", farUpperMF,
				farLowerMF);
	}
	
	protected abstract void createInputs(double maxInput);
	
	protected abstract void createAntecedents();
	
	protected abstract void createRulebase(IT2_Consequent leftWheelLow, IT2_Consequent leftWheelMedium,IT2_Consequent leftWheelHigh,IT2_Consequent rightWheelLow,IT2_Consequent rightWheelMedium,IT2_Consequent rightWheelHigh);

	public abstract IT2_Rulebase getRulebase();
}
