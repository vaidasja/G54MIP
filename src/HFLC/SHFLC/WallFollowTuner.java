package HFLC.SHFLC;

import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.ArUtil;
import com.mobilerobots.Aria.Aria;

import generic.Input;
import generic.Output;
import generic.Tuple;
import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
import tools.JMathPlotter;
import type1.sets.T1MF_Interface;
import type1.sets.T1MF_Trapezoidal;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class WallFollowTuner {
	//Output
	Output leftWheelVelocity;
	Output rightWheelVelocity;
	
    T1MF_Trapezoidal lowMF;
    
    T1MF_Trapezoidal mediumMF;
    
    T1MF_Trapezoidal highMF;
	
    //Consequents
    T1_Consequent leftWheelLow;
    T1_Consequent leftWheelMedium;
    T1_Consequent leftWheelHigh;
    T1_Consequent rightWheelLow;
    T1_Consequent rightWheelMedium;
    T1_Consequent rightWheelHigh;
	public WallFollowTuner() {
		
		double [][] sampleInput = {	{100,100,139,450},
									{200,200,156,400},
									{300,300,179,350},
									{400,400,208,300},
									{500,500,250,250},
									{600,600,300,208},
									{700,700,350,179},
									{800,800,400,156},
									{900,900,450,139} };	

/*		Input front = new Input("Front Sonar", new Tuple(0, 1000.0));
		Input back = new Input("Back Sonar", new Tuple(0, 1000.0));
		
        //Output definition
        leftWheelVelocity = new Output("Left Wheel Velocity", new Tuple(0, 500));
        rightWheelVelocity = new Output("Right Wheel Velocity", new Tuple(0, 500));
        
        //Output membership function - low. Definition
        lowMF= new T1MF_Trapezoidal("Lower MF Low",new double[]{0, 0, 125.0, 250.0});

        
        //Output membership function - medium. Definition
        mediumMF= new T1MF_Trapezoidal("Lower MF Medium",new double[]{125.0, 250.0, 250.0, 375.0});

        
        //Output membership function - high. Definition
        highMF= new T1MF_Trapezoidal("Lower MF High",new double[]{250.0, 375.0, 500.0, 500.0});

        
        //Consequent definition
        leftWheelLow = new T1_Consequent("Low", lowMF, leftWheelVelocity);
        leftWheelMedium = new T1_Consequent("Medium", mediumMF, leftWheelVelocity);
        leftWheelHigh = new T1_Consequent("High", highMF, leftWheelVelocity);
        rightWheelLow = new T1_Consequent("Low", lowMF, rightWheelVelocity);
        rightWheelMedium = new T1_Consequent("Medium", mediumMF, rightWheelVelocity);
        rightWheelHigh = new T1_Consequent("High", highMF, rightWheelVelocity);
        T1_Consequent[] leftList = {leftWheelLow,leftWheelMedium,leftWheelHigh};
        T1_Consequent[] rightList = {rightWheelLow,rightWheelMedium,rightWheelHigh};
        
		T1MF_Trapezoidal closeMF = new T1MF_Trapezoidal("Close", new double[] {
				0.0, 0.0, 0, 500.0 });
		
		T1MF_Trapezoidal farMF = new T1MF_Trapezoidal("Far", new double[] {
				500.0, 1000.0, 5000.0, 5000.0 });
		
		T1_Antecedent closeFront = new T1_Antecedent("Close Front", closeMF, front);
		T1_Antecedent farFront = new T1_Antecedent("Far Front", farMF, front);
		T1_Antecedent closeBack = new T1_Antecedent("Close Back", closeMF, back);
		T1_Antecedent farBack = new T1_Antecedent("Far Back", farMF, back);
		*/
		double overalSum = 99999999;
		double i1 = 0;
		double j1 = 0;

		double k1 = 0;
		double l1 = 0;
		
		double finalLeft = 0;
		double finalRight = 0;
//		for ( int i = 0; i < 1000; i=i+50) {
//			System.out.println(i);
//			for ( int j = 0; j < 1000; j=j+50) {
//				for ( int k = 0; k < 1000; k=k+50) {
//					for ( int l= 0; l < 1000; l=l+50) {
//if (i <k && j<l) {
		Input front = new Input("Front Sonar", new Tuple(0, 1000));
		Input back = new Input("Back Sonar", new Tuple(0, 1000));
		
		T1MF_Trapezoidal closeLowerMF = new T1MF_Trapezoidal("Lower MF Close", new double[] {
				0.0, 0.0, 50, 500 });
		T1MF_Trapezoidal closeUpperMF = new T1MF_Trapezoidal("Upper MF Close", new double[] {
				0.0, 0.0, 700,850
				});
		IntervalT2MF_Trapezoidal closeMF = new IntervalT2MF_Trapezoidal("IT2MF Close", closeUpperMF,
				closeLowerMF);

		// Membership function - far. Definition
		T1MF_Trapezoidal farLowerMF = new T1MF_Trapezoidal("Lower MF Far", new double[] {
				500, 850, 1000, 1000 });
		T1MF_Trapezoidal farUpperMF = new T1MF_Trapezoidal("Upper MF Far", new double[] {
				150, 350, 1000, 1000 });
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
        


			plotMFs("gh", new IntervalT2MF_Interface[] {closeMF,farMF}, 100);
//
//										IT2_Rulebase rulebase = new IT2_Rulebase(4);
//								        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, closeBack}, new IT2_Consequent[]{leftWheelLow, rightWheelMedium}));
//								        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, farBack}, new IT2_Consequent[]{leftWheelLow, rightWheelHigh}));
//								        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, closeBack}, new IT2_Consequent[]{leftWheelHigh, rightWheelLow}));
//								        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, farBack}, new IT2_Consequent[]{leftWheelMedium, rightWheelLow}));
//
//								        double sumLeft = 0;
//								        double sumRight = 0;
//								        for (int o = 0; o < 9; o++) {
//											front.setInput(sampleInput[o][0]);
//											back.setInput(sampleInput[o][1]);
//
//											double leftOutput = rulebase.evaluate(0).get(leftWheelVelocity);
//											double rightOutput = rulebase.evaluate(0).get(rightWheelVelocity);
//
//											sumLeft = sumLeft+ (Math.pow(leftOutput-sampleInput[o][2], 2));
//											sumRight = sumRight+ (Math.pow(rightOutput-sampleInput[o][3], 2));
//								        }
//								        sumLeft = Math.sqrt(sumLeft/7);
//								        sumRight = Math.sqrt(sumRight/7);
//								        if (sumLeft+sumRight < overalSum) {
//								        	finalLeft = sumLeft;
//								        	finalRight = sumRight;
//								        	overalSum=sumLeft+sumRight;
//								        	i1 = i;
//								        	j1=j;
//								        	k1 = k;
//								        	l1 =l;
//								        }
//
//        
//}}}}}
		System.out.println(finalLeft + " " + finalRight + " " + i1 + " " + j1 + " " + k1 + " " + l1);

	}

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
