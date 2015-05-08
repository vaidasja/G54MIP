package HFLC;

import HFLC.SHFLC.GoalTester;
import HFLC.SHFLC.LeftWallTester;
import HFLC.SHFLC.RuleTuner;
import HFLC.SHFLC.HFLC;
import HFLC.SHFLC.RightWallTester;
import HFLC.SHFLC.WallFollowTuner;

/**
 * Hierarchical Fuzzy Logic Controller
 * 
 * This controller addresses the problem of a mobile robot's navigation in
 * non-deterministic indoor environment. The low level behaviors are: 
 * 
 * 1.Left wall following
 * 2.Right wall following
 * 3.Obstacle avoidance
 * 4.Goal seeking
 * 
 * All of the above behaviors are controlled by the higher level layer which coordinates which behavior to activate.
 * 
 * The Launcher class is created only to launch the system.
 * 
 * @author Vaidas
 * 
 */


public class Launcher {
	public static void main(String [] args) {
		
		//Launches the Non-singleton version
		//new NHFLC();
		
		//Launches the controller
		HFLC shflc = new HFLC();
		shflc.run();
//		
		//launches the tuner
		//WallFollowTuner tuner = new WallFollowTuner();
		
		//RuleTuner tuner1 = new RuleTuner();
	
//		RightWallTester tester = new RightWallTester();

//		LeftWallTester tester = new LeftWallTester();
		
//		GoalTester tester = new GoalTester();
	}
}
