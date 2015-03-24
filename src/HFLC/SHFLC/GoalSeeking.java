package HFLC.SHFLC;

import tools.JMathPlotter;
import type1.sets.T1MF_Trapezoidal;
import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
import generic.Input;
import generic.Tuple;

/**
 * Goal Seeking
 * 
 * Goal seeking behaviour for singleton fuzzy system.
 * 
 * @author vxj11u
 *
 */

public class GoalSeeking {
	
	//input as a bearing from the goal
	Input bearing;
	
	// Antecedents
	private IT2_Antecedent negative;
	private IT2_Antecedent zero;
	private IT2_Antecedent positive;
	
	private IT2_Rulebase rulebase;
	
	// Membership function - negative
	protected T1MF_Trapezoidal negativeLowerMF;
	protected T1MF_Trapezoidal negativeUpperMF;
	protected IntervalT2MF_Trapezoidal negativeMF;
	
	// Membership function - positive
	protected T1MF_Trapezoidal positiveLowerMF;
	protected T1MF_Trapezoidal positiveUpperMF;
	protected IntervalT2MF_Trapezoidal positiveMF;
	
	// Membership function - zero
	protected T1MF_Trapezoidal zeroLowerMF;
	protected T1MF_Trapezoidal zeroUpperMF;
	protected IntervalT2MF_Trapezoidal zeroMF;
	
	public GoalSeeking(double negativeLowerLeft, double negativeLowerRight,
			double negativeUpperLeft, double negativeUpperRight, double positiveLowerLeft, double positiveLowerRight,
			double positiveUpperLeft, double positiveUpperRight, double zeroLowerLeft,double zeroLowerLeftMiddle, double zeroLowerRightMiddle, double zeroLowerRight, double zeroUpperLeft, double zeroUpperLeftMiddle, double zeroUpperRightMiddle, double zeroUpperRight, IT2_Consequent leftWheelLow, IT2_Consequent leftWheelMedium,IT2_Consequent leftWheelHigh,IT2_Consequent rightWheelLow,IT2_Consequent rightWheelMedium,IT2_Consequent rightWheelHigh) {
		//creating the single input
		bearing = new Input("Bearing", new Tuple(-180, 180));
		
		createNegativeMF(negativeLowerLeft, negativeLowerRight, negativeUpperLeft, negativeUpperRight);
		createPositiveMF(positiveLowerLeft, positiveLowerRight, positiveUpperLeft, positiveUpperRight);
		createZeroMF(zeroLowerLeft, zeroLowerLeftMiddle, zeroLowerRightMiddle, zeroLowerRight, zeroUpperLeft, zeroUpperLeftMiddle, zeroUpperRightMiddle, zeroUpperRight);
		createAntecedents();
		createRulebase(leftWheelLow, leftWheelMedium, leftWheelHigh, rightWheelLow, rightWheelMedium, rightWheelHigh);
		// TODO remove
		//plotMFs("gh", new IntervalT2MF_Interface[] {negativeMF, zeroMF, positiveMF}, 100);
	}
	
	/**
	 * createAntecedents()
	 * 
	 * Creates the antecedents with the previously created membership functions
	 */
	protected void createAntecedents() {
		negative = new IT2_Antecedent("Negative", negativeMF, bearing);
		zero = new IT2_Antecedent("Zero", zeroMF, bearing);
		positive = new IT2_Antecedent("Positive", positiveMF, bearing);
	}
	
	/**
	 * createNegativeMF()
	 * 
	 * Creates the membership function Negative
	 * 
	 * @param negativeLowerLeft
	 * @param negativeLowerRight
	 * @param negativeUpperLeft
	 * @param negativeUpperRight
	 */
	private void createNegativeMF(double negativeLowerLeft, double negativeLowerRight,
			double negativeUpperLeft, double negativeUpperRight) {
		// Membership function - Negative. Definition
		negativeLowerMF = new T1MF_Trapezoidal("Lower MF Negative", new double[] {
				-180.0, -180.0, negativeLowerLeft, negativeLowerRight });
		negativeUpperMF = new T1MF_Trapezoidal("Upper MF Negative", new double[] {
				-180.0, -180.0, negativeUpperLeft, negativeUpperRight });
		negativeMF = new IntervalT2MF_Trapezoidal("IT2MF Negative", negativeUpperMF,
				negativeLowerMF);
	}
	
	/**
	 * createPositiveMF()
	 * 
	 * Creates the membership function positive
	 * 
	 * @param positiveLowerLeft
	 * @param positiveLowerRight
	 * @param positiveUpperLeft
	 * @param positiveUpperRight
	 */
	private void createPositiveMF(double positiveLowerLeft, double positiveLowerRight,
			double positiveUpperLeft, double positiveUpperRight) {
		// Membership function - positive. Definition
		positiveLowerMF = new T1MF_Trapezoidal("Lower MF Positive", new double[] {
				positiveLowerLeft, positiveLowerRight, 180.0, 180.0 });
		positiveUpperMF = new T1MF_Trapezoidal("Upper MF Positive", new double[] {
				positiveUpperLeft, positiveUpperRight, 180.0, 180.0  });
		positiveMF = new IntervalT2MF_Trapezoidal("IT2MF Positive", positiveUpperMF,
				positiveLowerMF);
	}
	
	/**
	 * createZeroMF()
	 * 
	 * Creates the membership function zero
	 * 
	 */
	private void createZeroMF(double zeroLowerLeft,double zeroLowerLeftMiddle, double zeroLowerRightMiddle, double zeroLowerRight, double zeroUpperLeft, double zeroUpperLeftMiddle, double zeroUpperRightMiddle, double zeroUpperRight){
		//Membership function - zero. Definition
		zeroLowerMF = new T1MF_Trapezoidal("Lower MF Zero", new double[] {zeroLowerLeft, zeroLowerLeftMiddle, zeroLowerRightMiddle, zeroLowerRight});
		zeroUpperMF = new T1MF_Trapezoidal("Upper MF Zero", new double[] {zeroUpperLeft, zeroUpperLeftMiddle,zeroUpperRightMiddle, zeroUpperRight});
		zeroMF = new IntervalT2MF_Trapezoidal("IT2MF Zero", zeroUpperMF, zeroLowerMF);
	}
	
	/**
	 * createRulebase()
	 * 
	 * Creates the goal seeking rulebase.
	 * 
	 * @param leftWheelLow
	 * @param leftWheelMedium
	 * @param leftWheelHigh
	 * @param rightWheelLow
	 * @param rightWheelMedium
	 * @param rightWheelHigh
	 */
	protected void createRulebase(IT2_Consequent leftWheelLow, IT2_Consequent leftWheelMedium,IT2_Consequent leftWheelHigh,IT2_Consequent rightWheelLow,IT2_Consequent rightWheelMedium,IT2_Consequent rightWheelHigh) {
		rulebase = new IT2_Rulebase(3);
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{negative}, new IT2_Consequent[]{leftWheelHigh, rightWheelLow}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{zero}, new IT2_Consequent[]{leftWheelMedium, rightWheelMedium}));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{positive}, new IT2_Consequent[]{leftWheelLow, rightWheelHigh}));

	}
	
	/**
	 * setInput()
	 * 
	 * Sets the input as a bearing from goal
	 * @param bearing
	 */
	public void setInput(double bearing) {
		this.bearing.setInput(bearing);
	}
	
	/**
	 * getRulebase()
	 * 
	 * @return rulebase
	 */
	public IT2_Rulebase getRulebase() {
		return rulebase;
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
