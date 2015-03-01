package HFLC.SHFLC;

import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Antecedent;
import type1.sets.T1MF_Trapezoidal;
import generic.Input;
import generic.Tuple;


/**
 * Coordination
 * 
 * The upper layer of the hierarchy. Controls which behaviour to choose
 * @author vxj11u
 *
 */
public class Coordination {
	
	//Context of activation inputs
	private Input leftWallContext;
	private Input rightWallContext;
	private Input obstacleContext;
	
	//Context of activation membership functions
	private T1MF_Trapezoidal obstacleLowerMF;
	private T1MF_Trapezoidal obstacleUpperMF;
	private IntervalT2MF_Trapezoidal obstacleMF;
	
	private T1MF_Trapezoidal leftWallLowerMF;
	private T1MF_Trapezoidal leftWallUpperMF;
	private IntervalT2MF_Trapezoidal leftWallMF;
	
	private T1MF_Trapezoidal rightWallLowerMF;
	private T1MF_Trapezoidal rightWallUpperMF;
	private IntervalT2MF_Trapezoidal rightWallMF;	

    
    //Context of activation antecedents
	private IT2_Antecedent obstacleLow;
	private IT2_Antecedent leftWallLow;
	private IT2_Antecedent rightWallLow;    
    
    /**
     * Coordination
     * 
     * The coordination layer constructor
     */
    public Coordination(double maxObstacle, double maxWallFollow, double obstacleLowerLeft, double obstacleLowerRight, double obstacleUpperLeft, double obstacleUpperRight, double leftLowerLeft, double leftLowerRight, double leftUpperLeft, double leftUpperRight, double rightLowerLeft, double rightLowerRight, double rightUpperLeft, double rightUpperRight) {
    	createInputs(maxObstacle, maxWallFollow);
    	createObstacleMF(obstacleLowerLeft, obstacleLowerRight, obstacleUpperLeft, obstacleUpperRight);
    	createLeftMF(leftLowerLeft, leftLowerRight, leftUpperLeft, leftUpperRight);
    	createRightMF(rightLowerLeft, rightLowerRight, rightUpperLeft, rightUpperRight);
    	createAntecedents();

    }
    
    /**
     * createInputs() 
     * 
     * Creates Input fuzzy sets
     */
    
    private void createInputs(double maxObstacle, double maxWallFollow) {
		leftWallContext = new Input("Left wall following", new Tuple(0,maxWallFollow));
		rightWallContext = new Input("Right wall following", new Tuple(0,maxWallFollow));
		obstacleContext = new Input("Obstacle avoidance", new Tuple(0,maxObstacle));
    }
    
    /**
     * createObstacleMF()
     * 
     * Creates obstacle avoidance membership function
     */
    private void createObstacleMF(double obstacleLowerLeft, double obstacleLowerRight, double obstacleUpperLeft, double obstacleUpperRight) {
    	obstacleLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, obstacleLowerLeft, obstacleLowerRight});
    	obstacleUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, obstacleUpperLeft, obstacleUpperRight});
    	obstacleMF = new IntervalT2MF_Trapezoidal("Low", obstacleUpperMF, obstacleLowerMF);
    }
    
    /**
     * createLeftMF()
     * 
     * Creates Left wall following membership function
     */
    private void createLeftMF(double leftLowerLeft, double leftLowerRight, double leftUpperLeft, double leftUpperRight) {
    	leftWallLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, leftLowerLeft, leftLowerRight});
    	leftWallUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, leftUpperLeft, leftUpperRight});
    	leftWallMF = new IntervalT2MF_Trapezoidal("Low", leftWallUpperMF, leftWallLowerMF);
    }
    
    /**
     * createRightMF()
     * 
     * Creates Right wall following membership function
     */
    private void createRightMF(double rightLowerLeft, double rightLowerRight, double rightUpperLeft, double rightUpperRight) {
    	rightWallLowerMF = new T1MF_Trapezoidal("Lower MF Low",new double[]{0.0, 0.0, rightLowerLeft, rightLowerRight});
    	rightWallUpperMF = new T1MF_Trapezoidal("Upper MF Low",new double[]{0.0, 0.0, rightUpperLeft, rightUpperRight});
    	rightWallMF = new IntervalT2MF_Trapezoidal("Low", rightWallUpperMF, rightWallLowerMF);
    }
    
    /**
     * createAntecedents()
     * 
     * Creates the antecedents
     */
    private void createAntecedents() {
        obstacleLow = new IT2_Antecedent("Obstacle Distance Low", obstacleMF, obstacleContext);
        leftWallLow = new IT2_Antecedent("Left Wall Distance Low", leftWallMF, leftWallContext);
        rightWallLow = new IT2_Antecedent("Right Wall Distance Low", rightWallMF, rightWallContext);
    }
}
