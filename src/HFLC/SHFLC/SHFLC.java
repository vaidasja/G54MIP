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

	//Context of activation inputs
	Input leftWallContext;
	Input rightWallContext;
	Input obstacleContext;
	
	//Sonar sensor input for wall following
	Input frontInput;
	Input backInput;
	
	//Sonar sensor input for obstacle avoidance
	Input obstacleMiddle;
	Input obstacleLeft;
	Input obstacleRight;
	
	//Output
	Output leftWheelVelocity;
	Output rightWheelVelocity;
	
	//Context of activation membership functions
	T1MF_Trapezoidal obstacleLowerMF;
	T1MF_Trapezoidal obstacleUpperMF;
	IntervalT2MF_Trapezoidal obstacleMF;
	
	T1MF_Trapezoidal leftWallLowerMF;
	T1MF_Trapezoidal leftWallUpperMF;
	IntervalT2MF_Trapezoidal leftWallMF;
	
	T1MF_Trapezoidal rightWallLowerMF;
	T1MF_Trapezoidal rightWallUpperMF;
	IntervalT2MF_Trapezoidal rightWallMF;
	
	
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
    
    //Context of activation antecedents
    IT2_Antecedent obstacleLow;
    IT2_Antecedent leftWallLow;
    IT2_Antecedent rightWallLow;
    
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
    
    //Rulebases
    IT2_Rulebase leftWallRulebase;
    IT2_Rulebase rightWallRulebase;
    IT2_Rulebase obstacleRulebase;
    
	/**
	 * SHFLC Constructor. Creates the Singleton system 
	 * 
	 */
	public SHFLC() {
		
		//Context of activation inputs. Definition
		leftWallContext = new Input("Left wall following", new Tuple(0,100));
		rightWallContext = new Input("Right wall following", new Tuple(0,100));
		obstacleContext = new Input("Obstacle avoidance", new Tuple(0,100));
		
		//Sonar sensor input for wall following. Definition
        frontInput = new Input("Front sonar", new Tuple(0,100));
        backInput = new Input("Back sonar", new Tuple(0,100));
        
        //Sonar input for obstacle avoidance. Definition
        obstacleMiddle = new Input("Middle sonar", new Tuple(0, 200));
        obstacleLeft = new Input("Left sonar", new Tuple(0,200));
        obstacleRight = new Input("Right sonar", new Tuple(0,200));
        
        //Output definition
        leftWheelVelocity = new Output("Left Wheel Velocity", new Tuple(-100, 400));
        rightWheelVelocity = new Output("Right Wheel Velocity", new Tuple(-100, 400));
        
        
    	//Context of activation membership functions. Definitions
    	obstacleLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, 0.0, 80.0});
    	obstacleUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, 20.0, 100.0});
    	obstacleMF = new IntervalT2MF_Trapezoidal("Low", obstacleUpperMF, obstacleLowerMF);
    	
    	leftWallLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, 0.0, 80.0});
    	leftWallUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, 20.0, 100.0});
    	leftWallMF = new IntervalT2MF_Trapezoidal("Low", obstacleUpperMF, obstacleLowerMF);
    	
    	rightWallLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, 0.0, 80.0});
    	rightWallUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, 20.0, 100.0});
    	rightWallMF = new IntervalT2MF_Trapezoidal("Low", obstacleUpperMF, obstacleLowerMF);
        
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
        
        //Context of activation antecedents. Definition
        obstacleLow = new IT2_Antecedent("Obstacle Distance Low", obstacleMF, obstacleContext);
        leftWallLow = new IT2_Antecedent("Left Wall Distance Low", leftWallMF, leftWallContext);
        rightWallLow = new IT2_Antecedent("Right Wall Distance Low", rightWallMF, rightWallContext);
        
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
        
		createLeftWallRulebase();
		createRightWallRulebase();
		createObstacleRulebase();
        plotControlSurface(leftWheelVelocity, false, 100, 100);   //use COS type reduction
        plotControlSurface(rightWheelVelocity, true, 100, 100);  //use centroid type reduction
        
	}
	
	/**
	 * createLeftWallRulebase()
	 * 
	 * Creates left wall following behaviour's rulebase
	 */
	private void createLeftWallRulebase() {
		
		leftWallRulebase = new IT2_Rulebase(4);
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, closeBack}, new IT2_Consequent[]{leftWheelMedium, rightWheelMedium}));
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, farBack}, new IT2_Consequent[]{leftWheelHigh, rightWheelLow}));
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, closeBack}, new IT2_Consequent[]{leftWheelLow, rightWheelHigh}));
        leftWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, farBack}, new IT2_Consequent[]{leftWheelHigh, rightWheelHigh}));
        
		//helper. remove when done as will slow down runtime.
        plotMFs("whatever",new IntervalT2MF_Interface[]{obstacleMF},100);
        //plotMFs("whatever",new IntervalT2MF_Interface[]{straightMF, rightMF, leftMF},100);
        
	}
	
	/**
	 * createRightWallRulebase()
	 * 
	 * Creates right wall following behaviour's rulebase
	 */
	private void createRightWallRulebase() {
		
		rightWallRulebase = new IT2_Rulebase(4);
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, closeBack}, new IT2_Consequent[]{leftWheelMedium, rightWheelMedium}));
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeFront, farBack}, new IT2_Consequent[]{leftWheelLow, rightWheelHigh}));
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, closeBack}, new IT2_Consequent[]{leftWheelHigh, rightWheelLow}));
        rightWallRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farFront, farBack}, new IT2_Consequent[]{leftWheelHigh, rightWheelHigh}));
	}
	
	/**
	 * createObstacleRulebase()
	 * 
	 * Creates obstacle avoidance behaviour's rulebase
	 */
	private void createObstacleRulebase() {
		//change outputs
		obstacleRulebase = new IT2_Rulebase(8);
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, closeMiddle, closeRight}, new IT2_Consequent[]{leftWheelLow, rightWheelLow}));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, closeMiddle, farRight}, new IT2_Consequent[]{leftWheelMedium, rightWheelLow}));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, farMiddle, closeRight}, new IT2_Consequent[]{leftWheelMedium, rightWheelMedium}));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{closeLeft, farMiddle, farRight}, new IT2_Consequent[]{leftWheelHigh, rightWheelLow}));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, closeMiddle, closeRight}, new IT2_Consequent[]{leftWheelLow, rightWheelMedium}));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, closeMiddle, farRight}, new IT2_Consequent[]{leftWheelLow, rightWheelLow}));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, farMiddle, closeRight}, new IT2_Consequent[]{leftWheelLow, rightWheelMedium}));
		obstacleRulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{farLeft, farMiddle, farRight}, new IT2_Consequent[]{leftWheelHigh, rightWheelHigh}));
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
    
  //helper. Copied from Juzzy. Remove when done
    private void plotControlSurface(Output o, boolean useCentroidDefuzzification, int input1Discs, int input2Discs)
    {
        double output;
        double[] x = new double[input1Discs];
        double[] y = new double[input2Discs];
        double[][] z = new double[y.length][x.length];
        double incrX, incrY;
        incrX = frontInput.getDomain().getSize()/(input1Discs-1.0);
        incrY = backInput.getDomain().getSize()/(input2Discs-1.0);

        //first, get the values
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
            x[currentX] = currentX * incrX;        
        }
        for(int currentY=0; currentY<input2Discs; currentY++)
        {
            y[currentY] = currentY * incrY;
        }
        
        for(int currentX=0; currentX<input1Discs; currentX++)
        {
        	frontInput.setInput(x[currentX]);
            for(int currentY=0; currentY<input2Discs; currentY++)
            {//System.out.println("Current x = "+currentX+"  current y = "+currentY);
            	backInput.setInput(y[currentY]);
                if(useCentroidDefuzzification)
                {
                    output = leftWallRulebase.evaluate(1).get(o);
                }
                else
                {
                    output = leftWallRulebase.evaluate(0).get(o);
                }
                z[currentY][currentX] = output;
            }    
        }
        
        //now do the plotting
        JMathPlotter plotter = new JMathPlotter();
        plotter.plotControlSurface("Control Surface",
                new String[]{frontInput.getName(), backInput.getName(), "Tip"}, x, y, z, o.getDomain(), true); 
        plotter.show("Interval Type-2 Fuzzy Logic System Control Surface for "+o.getName());
    }
}
