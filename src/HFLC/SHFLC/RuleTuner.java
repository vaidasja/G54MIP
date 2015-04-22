package HFLC.SHFLC;

import type1.sets.T1MF_Trapezoidal;
import generic.Input;
import generic.Output;
import generic.Tuple;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;

public class RuleTuner {

	//Output
	Output leftWheelVelocity;
	Output rightWheelVelocity;
	
	public RuleTuner() {
		
		double [][] sampleInput = {	{500,500,250,250},
									{600,600,300,200},
									{800,800,400,100},
									{550,500,275,250},
									{500,550,250,275},
									{400,400,200,300},
									{200,200,100,400}};
		
		
		Input front = new Input("Front Sonar", new Tuple(0, 1000));
		Input back = new Input("Back Sonar", new Tuple(0, 1000));
		
		T1MF_Trapezoidal closeLowerMF = new T1MF_Trapezoidal("Lower MF Close", new double[] {
				0.0, 0.0, 0, 400 });
		T1MF_Trapezoidal closeUpperMF = new T1MF_Trapezoidal("Upper MF Close", new double[] {
				0.0, 0.0, 100, 600 });
		IntervalT2MF_Trapezoidal closeMF = new IntervalT2MF_Trapezoidal("IT2MF Close", closeUpperMF,
				closeLowerMF);
		
		// Membership function - far. Definition
		T1MF_Trapezoidal farLowerMF = new T1MF_Trapezoidal("Lower MF Far", new double[] {
				600, 1000, 1000, 1000 });
		T1MF_Trapezoidal farUpperMF = new T1MF_Trapezoidal("Upper MF Far", new double[] {
				400, 900, 1000, 1000 });
		IntervalT2MF_Trapezoidal farMF = new IntervalT2MF_Trapezoidal("IT2MF Far", farUpperMF,
				farLowerMF);
		
		IT2_Antecedent closeFront = new IT2_Antecedent("Close Front", closeMF, front);
		IT2_Antecedent farFront = new IT2_Antecedent("Far Front", farMF, front);
		IT2_Antecedent closeBack = new IT2_Antecedent("Close Back", closeMF, back);
		IT2_Antecedent farBack = new IT2_Antecedent("Far Back", farMF, back);
		
		
        //Output definition
        leftWheelVelocity = new Output("Left Wheel Velocity", new Tuple(0, 500));
        rightWheelVelocity = new Output("Right Wheel Velocity", new Tuple(0, 500));
        
        //Output membership function - low. Definition
        T1MF_Trapezoidal lowLowerMF= new T1MF_Trapezoidal("Lower MF Low",new double[]{0, 0, 0, 225.0});
        T1MF_Trapezoidal lowUpperMF= new T1MF_Trapezoidal("Upper MF Low",new double[]{0, 0, 50.0, 275.0});
        IntervalT2MF_Trapezoidal lowMF = new IntervalT2MF_Trapezoidal("IT2MF Low",lowUpperMF,lowLowerMF);
        
        //Output membership function - medium. Definition
        T1MF_Trapezoidal mediumLowerMF= new T1MF_Trapezoidal("Lower MF Medium",new double[]{200.0, 250.0, 250.0, 300.0});
        T1MF_Trapezoidal mediumUpperMF= new T1MF_Trapezoidal("Upper MF Medium",new double[]{100.0, 200.0, 300.0, 400.0});
        IntervalT2MF_Trapezoidal mediumMF = new IntervalT2MF_Trapezoidal("IT2MF Medium", mediumUpperMF, mediumLowerMF);
        
        //Output membership function - high. Definition
        T1MF_Trapezoidal highLowerMF= new T1MF_Trapezoidal("Lower MF High",new double[]{275.0, 500.0, 500.0, 500.0});
        T1MF_Trapezoidal highUpperMF= new T1MF_Trapezoidal("Upper MF High",new double[]{225.0, 450.0, 500.0, 500.0});
        IntervalT2MF_Trapezoidal highMF = new IntervalT2MF_Trapezoidal("IT2MF High", highUpperMF, highLowerMF);
        
        //Consequent definition
        IT2_Consequent leftWheelLow = new IT2_Consequent("Low", lowMF, leftWheelVelocity);
        IT2_Consequent leftWheelMedium = new IT2_Consequent("Medium", mediumMF, leftWheelVelocity);
        IT2_Consequent leftWheelHigh = new IT2_Consequent("High", highMF, leftWheelVelocity);
        IT2_Consequent rightWheelLow = new IT2_Consequent("Low", lowMF, rightWheelVelocity);
        IT2_Consequent rightWheelMedium = new IT2_Consequent("Medium", mediumMF, rightWheelVelocity);
        IT2_Consequent rightWheelHigh = new IT2_Consequent("High", highMF, rightWheelVelocity);
        
        IT2_Consequent[] leftList = {leftWheelLow,leftWheelMedium,leftWheelHigh};
        IT2_Consequent[] rightList = {rightWheelLow,rightWheelMedium,rightWheelHigh};
        
	double overalSum = 99999999;
	double finalLeft = 0;
	double finalRight = 0;
	String finalBase = "";
	
	for (IT2_Consequent c1 : leftList) {
		for (IT2_Consequent c2 : rightList) {
			for (IT2_Consequent c3 : leftList) {
				for (IT2_Consequent c4 : rightList) {
					for (IT2_Consequent c5 : leftList) {
						for (IT2_Consequent c6 : rightList) {
							for (IT2_Consequent c7 : leftList) {
								for (IT2_Consequent c8 : rightList) {
									IT2_Rulebase rulebase = new IT2_Rulebase(4);
							        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, closeBack}, new IT2_Consequent[]{c1, c2}));
							        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, farBack}, new IT2_Consequent[]{c3, c4}));
							        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, closeBack}, new IT2_Consequent[]{c5, c6}));
							        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, farBack}, new IT2_Consequent[]{c7, c8}));

							        double sumLeft = 0;
							        double sumRight = 0;
							        for (int o = 0; o < 7; o++) {
										front.setInput(sampleInput[o][0]);
										back.setInput(sampleInput[o][1]);

										double leftOutput = rulebase.evaluate(0).get(leftWheelVelocity);
										double rightOutput = rulebase.evaluate(0).get(rightWheelVelocity);

										sumLeft = sumLeft+ (Math.pow(leftOutput-sampleInput[o][2], 2));
										sumRight = sumRight+ (Math.pow(rightOutput-sampleInput[o][3], 2));
							        }
							        sumLeft = Math.sqrt(sumLeft/7);
							        sumRight = Math.sqrt(sumRight/7);
							        if (sumLeft+sumRight < overalSum) {
							        	finalLeft = sumLeft;
							        	finalRight = sumRight;
							        	overalSum=sumLeft+sumRight;
							        	finalBase = c1.getName() + " " +c2.getName() + " " +c3.getName() + " " +c4.getName() + " " +c5.getName() + " " +c6.getName() + " " +c7.getName() + " " +c8.getName();
							        }
								}
							}
						}
					}
				}
			}
		}
	}
	
	System.out.println(finalLeft + " " + finalRight + " " + finalBase);
	}
	
}
