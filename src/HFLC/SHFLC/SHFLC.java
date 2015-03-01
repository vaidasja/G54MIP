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
import generic.Output;
import generic.Tuple;

/**
 * Singleton HFLC
 * 
 * This class is the hierarchical fuzzy logic controller with singleton inputs.
 * 
 * @author Vaidas
 * 
 */

public class SHFLC {

	//Output
	Output leftWheelVelocity;
	Output rightWheelVelocity;
	
    //Output membership function - low.
    T1MF_Trapezoidal lowLowerMF;
    T1MF_Trapezoidal lowUpperMF;
    IntervalT2MF_Trapezoidal lowMF;
    
    //Output membership function - medium.
    T1MF_Trapezoidal mediumLowerMF;
    T1MF_Trapezoidal mediumUpperMF;
    IntervalT2MF_Trapezoidal mediumMF;
    
    //Output membership function - high.
    T1MF_Trapezoidal highLowerMF;
    T1MF_Trapezoidal highUpperMF;
    IntervalT2MF_Trapezoidal highMF;
	
    //Consequents
    IT2_Consequent leftWheelLow;
    IT2_Consequent leftWheelMedium;
    IT2_Consequent leftWheelHigh;
    IT2_Consequent rightWheelLow;
    IT2_Consequent rightWheelMedium;
    IT2_Consequent rightWheelHigh;
    
	/**
	 * SHFLC Constructor. Creates the Singleton system 
	 * 
	 */
	public SHFLC() {

        //Output definition
        leftWheelVelocity = new Output("Left Wheel Velocity", new Tuple(-100, 400));
        rightWheelVelocity = new Output("Right Wheel Velocity", new Tuple(-100, 400));
        
        //Output membership function - low. Definition
        lowLowerMF= new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 50.0, 50.0, 100.0});
        lowUpperMF= new T1MF_Trapezoidal("Upper MF Low",new double[]{-100.0, 0.0, 100.0, 200.0});
        lowMF = new IntervalT2MF_Trapezoidal("IT2MF Low",lowUpperMF,lowLowerMF);
        
        //Output membership function - medium. Definition
        mediumLowerMF= new T1MF_Trapezoidal("Lower MF Medium",new double[]{100.0, 150.0, 150.0, 200.0});
        mediumUpperMF= new T1MF_Trapezoidal("Upper MF Medium",new double[]{0.0, 100.0, 200.0, 300.0});
        mediumMF = new IntervalT2MF_Trapezoidal("IT2MF Medium", mediumUpperMF, mediumLowerMF);
        
        //Output membership function - high. Definition
        highLowerMF= new T1MF_Trapezoidal("Lower MF High",new double[]{200.0, 250.0, 250.0, 300.0});
        highUpperMF= new T1MF_Trapezoidal("Upper MF High",new double[]{100.0, 200.0, 300.0, 400.0});
        highMF = new IntervalT2MF_Trapezoidal("IT2MF High", highUpperMF, highLowerMF);
        
        //Consequent definition
        leftWheelLow = new IT2_Consequent("Low", lowMF, leftWheelVelocity);
        leftWheelMedium = new IT2_Consequent("Medium", mediumMF, leftWheelVelocity);
        leftWheelHigh = new IT2_Consequent("High", highMF, leftWheelVelocity);
        rightWheelLow = new IT2_Consequent("Low", lowMF, rightWheelVelocity);
        rightWheelMedium = new IT2_Consequent("Medium", mediumMF, rightWheelVelocity);
        rightWheelHigh = new IT2_Consequent("High", highMF, rightWheelVelocity);
        
        new Coordination(200.0, 100.0, 0.0, 160.0, 40.0, 200.0, 0.0, 80.0, 20.0, 100.0, 0.0, 80.0, 20.0, 100.0);
		new LeftWallFollowing(100.0, 0.0, 40.0, 40.0, 80.0, 60.0, 100.0, 20.0, 60.0, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh);
		new RightWallFollowing(100.0, 0.0, 40.0, 40.0, 80.0, 60.0, 100.0, 20.0, 60.0, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh);
		new ObstacleAvoidance(200.0, 0.0, 80.0, 80.0, 160.0, 120.0, 200.0, 40.0, 120.0, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh, leftWheelHigh);
        

        
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
    
//  //helper. Copied from Juzzy. Remove when done
//    private void plotControlSurface(Output o, boolean useCentroidDefuzzification, int input1Discs, int input2Discs)
//    {
//        double output;
//        double[] x = new double[input1Discs];
//        double[] y = new double[input2Discs];
//        double[][] z = new double[y.length][x.length];
//        double incrX, incrY;
//        incrX = frontInput.getDomain().getSize()/(input1Discs-1.0);
//        incrY = backInput.getDomain().getSize()/(input2Discs-1.0);
//
//        //first, get the values
//        for(int currentX=0; currentX<input1Discs; currentX++)
//        {
//            x[currentX] = currentX * incrX;        
//        }
//        for(int currentY=0; currentY<input2Discs; currentY++)
//        {
//            y[currentY] = currentY * incrY;
//        }
//        
//        for(int currentX=0; currentX<input1Discs; currentX++)
//        {
//        	frontInput.setInput(x[currentX]);
//            for(int currentY=0; currentY<input2Discs; currentY++)
//            {//System.out.println("Current x = "+currentX+"  current y = "+currentY);
//            	backInput.setInput(y[currentY]);
//                if(useCentroidDefuzzification)
//                {
//                    output = leftWallRulebase.evaluate(1).get(o);
//                }
//                else
//                {
//                    output = leftWallRulebase.evaluate(0).get(o);
//                }
//                z[currentY][currentX] = output;
//            }    
//        }
//        
//        //now do the plotting
//        JMathPlotter plotter = new JMathPlotter();
//        plotter.plotControlSurface("Control Surface",
//                new String[]{frontInput.getName(), backInput.getName(), "Tip"}, x, y, z, o.getDomain(), true); 
//        plotter.show("Interval Type-2 Fuzzy Logic System Control Surface for "+o.getName());
//    }
}
