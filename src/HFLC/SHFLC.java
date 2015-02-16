package HFLC;

import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
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

	//Sonar sensor input
	Input sonarInput;
	
	//Output
	Output direction;
	
    //Wall follow membership function - close
    T1MF_Trapezoidal closeLowerMF;
    T1MF_Trapezoidal closeUpperMF;
    IntervalT2MF_Trapezoidal closeMF;
    
    //Wall follow membership function - far
    T1MF_Trapezoidal farUpperMF;
    T1MF_Trapezoidal farLowerMF;
    IntervalT2MF_Trapezoidal farMF;
    
    //Wall follow antecedents
    IT2_Antecedent close;
    IT2_Antecedent far;
    
    //Output membership function - turn left.
    T1MF_Trapezoidal leftLowerMF;
    T1MF_Trapezoidal leftUpperMF;
    IntervalT2MF_Trapezoidal leftMF;
    
    //Output membership function - straight.
    T1MF_Trapezoidal straightLowerMF;
    T1MF_Trapezoidal straightUpperMF;
    IntervalT2MF_Trapezoidal straightMF;
    
    //Output membership function - turn right.
    T1MF_Trapezoidal rightLowerMF;
    T1MF_Trapezoidal rightUpperMF;
    IntervalT2MF_Trapezoidal rightMF;
	
    //Consequents
    IT2_Consequent left;
    IT2_Consequent straight;
    IT2_Consequent right;
    
	/**
	 * SHFLC Constructor. Creates the Singleton system 
	 * 
	 */
	public SHFLC() {
		
		//Sonar sensor input. Definition
        sonarInput = new Input("Sonar", new Tuple(0,100));
        
        //Output definition
        direction = new Output("Direction", new Tuple(-90,90));
        
        //Wall follow membership function - close. Definition
        closeLowerMF= new T1MF_Trapezoidal("Lower MF Close",new double[]{0.0, 0.0, 0.0, 40.0});
        closeUpperMF= new T1MF_Trapezoidal("Upper MF Close",new double[]{0.0, 0.0, 40.0, 80.0});
        closeMF = new IntervalT2MF_Trapezoidal("IT2MF Close",closeUpperMF,closeLowerMF);
        
        //Wall follow membership function - far. Definition
        farUpperMF= new T1MF_Trapezoidal("Upper MF Far",new double[]{20.0, 60.0, 100.0, 100.0});
        farLowerMF= new T1MF_Trapezoidal("Lower MF Far",new double[]{60.0, 100.0, 100.0, 100.0});
        farMF = new IntervalT2MF_Trapezoidal("IT2MF Far",farUpperMF,farLowerMF);
        
        //Wall follow antecedents. Definition
        close = new IT2_Antecedent("Close", closeMF, sonarInput);
        far = new IT2_Antecedent("Far", farMF, sonarInput);
        
        //Output membership function - turn left. Definition
        leftLowerMF= new T1MF_Trapezoidal("Lower MF Left",new double[]{-72.0, -36.0, -36.0, 0.0});
        leftUpperMF= new T1MF_Trapezoidal("Upper MF Left",new double[]{-90.0, -54.0, -18.0, 18.0});
        leftMF = new IntervalT2MF_Trapezoidal("IT2MF Left",leftUpperMF,leftLowerMF);
        
        //Output membership function - straight. Definition
        straightLowerMF= new T1MF_Trapezoidal("Lower MF Straight",new double[]{-36.0, 0.0, 0.0, 36.0});
        straightUpperMF= new T1MF_Trapezoidal("Upper MF Straight",new double[]{-54.0, -18.0, 18.0, 54.0});
        straightMF = new IntervalT2MF_Trapezoidal("IT2MF Straight", straightUpperMF, straightLowerMF);
        
        //Output membership function - turn Right. Definition
        rightLowerMF= new T1MF_Trapezoidal("Lower MF Right",new double[]{0.0, 36.0, 36.0, 72.0});
        rightUpperMF= new T1MF_Trapezoidal("Upper MF Right",new double[]{-18.0, 18.0, 54.0, 90.0});
        rightMF = new IntervalT2MF_Trapezoidal("IT2MF Right", rightUpperMF, rightLowerMF);
        
        //Consequent definition
        left = new IT2_Consequent("Left", leftMF, direction);
        straight = new IT2_Consequent("Straight", straightMF, direction);
        right = new IT2_Consequent("Right", rightMF, direction);
        
		createLeftWallFollow();
	}
	
	/**
	 * wallFollow()
	 * 
	 * Creates the wall following fuzzy inference system. Whether right or left
	 * wall will be determined by the input to the rule base
	 */
	private void createLeftWallFollow() {
		
		//helper. remove when done as will slow down runtime.
        plotMFs("whatever",new IntervalT2MF_Interface[]{closeMF, farMF},100);
        plotMFs("whatever",new IntervalT2MF_Interface[]{straightMF, rightMF, leftMF},100);
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
