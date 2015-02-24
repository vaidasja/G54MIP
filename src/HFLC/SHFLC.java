package HFLC;

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

	//Sonar sensor input for wall following
	Input frontInput;
	Input backInput;
	
	//Sonar sensor input for obstacle avoidance
	Input obstacleMiddle;
	Input obstacleLeft;
	Input obstacleRight;
	
	//Output
	Output direction;
	
	//Obstacle avoidance membership function - close
    T1MF_Trapezoidal obstacleCloseLowerMF;
    T1MF_Trapezoidal obstacleCloseUpperMF;
    IntervalT2MF_Trapezoidal obstacleCloseMF;
    
    //Obstacle avoidance membership function - far
    T1MF_Trapezoidal obstacleFarUpperMF;
    T1MF_Trapezoidal obstacleFarLowerMF;
    IntervalT2MF_Trapezoidal obstacleFarMF;
	
    //Wall follow membership function - close
    T1MF_Trapezoidal wallCloseLowerMF;
    T1MF_Trapezoidal wallCloseUpperMF;
    IntervalT2MF_Trapezoidal wallCloseMF;
    
    //Wall follow membership function - far
    T1MF_Trapezoidal wallFarUpperMF;
    T1MF_Trapezoidal wallFarLowerMF;
    IntervalT2MF_Trapezoidal wallFarMF;
    
    //Obstacle avoidance antecedents
    IT2_Antecedent closeLeft;
    IT2_Antecedent farLeft;
    IT2_Antecedent closeMiddle;
    IT2_Antecedent farMiddle;
    IT2_Antecedent closeRight;
    IT2_Antecedent farRight;
    
    //Wall follow antecedents
    IT2_Antecedent closeFront;
    IT2_Antecedent farFront;
    IT2_Antecedent closeBack;
    IT2_Antecedent farBack;
    
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
    
    //Rulebases
    IT2_Rulebase leftWallRulebase;
    IT2_Rulebase rightWallRulebase;
    IT2_Rulebase obstacleRulebase;
    
	/**
	 * SHFLC Constructor. Creates the Singleton system 
	 * 
	 */
	public SHFLC() {
		
		//Sonar sensor input for wall following. Definition
        frontInput = new Input("Front sonar", new Tuple(0,100));
        backInput = new Input("Back sonar", new Tuple(0,100));
        
        //Sonar input for obstacle avoidance. Definition
        obstacleMiddle = new Input("Middle sonar", new Tuple(0, 200));
        obstacleLeft = new Input("Left sonar", new Tuple(0,200));
        obstacleRight = new Input("Right sonar", new Tuple(0,200));
        
        //Output definition
        direction = new Output("Direction", new Tuple(-90,90));
        
        
        //Obstacle avoidance membership function - close. Definition
        obstacleCloseLowerMF= new T1MF_Trapezoidal("Lower MF Close",new double[]{0.0, 0.0, 0.0, 80.0});
        obstacleCloseUpperMF= new T1MF_Trapezoidal("Upper MF Close",new double[]{0.0, 0.0, 80.0, 160.0});
        obstacleCloseMF = new IntervalT2MF_Trapezoidal("IT2MF Close",obstacleCloseUpperMF,obstacleCloseLowerMF);
        
        //Obstacle avoidance membership function - far. Definition
        obstacleFarUpperMF= new T1MF_Trapezoidal("Upper MF Far",new double[]{40.0, 120.0, 200.0, 200.0});
        obstacleFarLowerMF= new T1MF_Trapezoidal("Lower MF Far",new double[]{120.0, 200.0, 200.0, 200.0});
        obstacleFarMF = new IntervalT2MF_Trapezoidal("IT2MF Far",obstacleFarUpperMF,obstacleFarLowerMF);
        
        //Wall follow membership function - close. Definition
        wallCloseLowerMF= new T1MF_Trapezoidal("Lower MF Close",new double[]{0.0, 0.0, 0.0, 40.0});
        wallCloseUpperMF= new T1MF_Trapezoidal("Upper MF Close",new double[]{0.0, 0.0, 40.0, 80.0});
        wallCloseMF = new IntervalT2MF_Trapezoidal("IT2MF Close",wallCloseUpperMF,wallCloseLowerMF);
        
        //Wall follow membership function - far. Definition
        wallFarUpperMF= new T1MF_Trapezoidal("Upper MF Far",new double[]{20.0, 60.0, 100.0, 100.0});
        wallFarLowerMF= new T1MF_Trapezoidal("Lower MF Far",new double[]{60.0, 100.0, 100.0, 100.0});
        wallFarMF = new IntervalT2MF_Trapezoidal("IT2MF Far",wallFarUpperMF,wallFarLowerMF);
        
        
        //Obstacle avoidance antecedents. Definition
        closeLeft = new IT2_Antecedent("Close Left", obstacleCloseMF, obstacleLeft);
        farLeft  = new IT2_Antecedent("Far Left", obstacleFarMF, obstacleLeft);
        closeMiddle = new IT2_Antecedent("Close Middle", obstacleCloseMF, obstacleMiddle);
        farMiddle = new IT2_Antecedent("Far Middle", obstacleFarMF, obstacleMiddle);
        closeRight = new IT2_Antecedent("Close Right", obstacleCloseMF, obstacleRight);
        farRight = new IT2_Antecedent("Far Right", obstacleFarMF, obstacleRight);
        
        
        //Wall follow antecedents. Definition
        closeFront = new IT2_Antecedent("Close Front", wallCloseMF, frontInput);
        farFront = new IT2_Antecedent("Far Front", wallFarMF, frontInput);
        closeBack = new IT2_Antecedent("Close Back", wallCloseMF, backInput);
        farBack = new IT2_Antecedent("Far Back", wallFarMF, backInput);
        
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
        
		createLeftWallRulebase();
		createRightWallRulebase();
		createObstacleRulebase();
	}
	
	/**
	 * createLeftWallRulebase()
	 * 
	 * Creates left wall following behaviour's rulebase
	 */
	private void createLeftWallRulebase() {
		
		leftWallRulebase = new IT2_Rulebase(4);
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, closeBack}, straight));
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, farBack}, right));
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, closeBack}, left));
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, farBack}, straight));
        
		//helper. remove when done as will slow down runtime.
        plotMFs("whatever",new IntervalT2MF_Interface[]{obstacleCloseMF, obstacleFarMF},100);
        //plotMFs("whatever",new IntervalT2MF_Interface[]{straightMF, rightMF, leftMF},100);
	}
	
	/**
	 * createRightWallRulebase()
	 * 
	 * Creates right wall following behaviour's rulebase
	 */
	private void createRightWallRulebase() {
		
		rightWallRulebase = new IT2_Rulebase(4);
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, closeBack}, straight));
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, farBack}, left));
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, closeBack}, right));
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, farBack}, straight));
	}
	
	/**
	 * createObstacleRulebase()
	 * 
	 * Creates obstacle avoidance behaviour's rulebase
	 */
	private void createObstacleRulebase() {
		//change outputs
		obstacleRulebase = new IT2_Rulebase(8);
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, closeMiddle, closeRight}, straight));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, closeMiddle, farRight}, straight));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, farMiddle, closeRight}, straight));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, farMiddle, farRight}, straight));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, closeMiddle, closeRight}, straight));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, closeMiddle, farRight}, straight));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, farMiddle, closeRight}, straight));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, farMiddle, farRight}, straight));
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
