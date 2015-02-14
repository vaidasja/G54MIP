package NHFLC;

/**
 * Non-singleton Hierarchical Fuzzy Logic Controller
 * 
 * This controller addresses the problem of a mobile robot's navigation in
 * non-deterministic indoor environment. The aim of this project is to compare
 * how a hierarchical fuzzy logic controller (HFLC) with crisp inputs compares
 * to a HFLC with non-singleton inputs. The low level behaviors are: 
 * 
 * 1.Left wall following
 * 2.Right wall following
 * 3.Obstacle avoidance
 * 4.Goal seeking
 * 
 * All of the above behaviors are controlled by the higher level layer which coordinates which behavior to activate.
 * 
 * The Launcher class is created only to launch either Non-singleton or Singleton systems.
 * 
 * @author Vaidas
 * 
 */


public class Launcher {
	public static void main(String [] args) {
		
		//Launches the Non-singleton version
		//new NHFLC();
		
		//Launches the Singleton version
		new SHFLC();
	}
}
