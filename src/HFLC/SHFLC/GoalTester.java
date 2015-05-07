package HFLC.SHFLC;

import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.ArUtil;
import com.mobilerobots.Aria.Aria;

import generic.Output;
import generic.Tuple;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Consequent;
import type1.sets.T1MF_Trapezoidal;

public class GoalTester {
	
	//Output
	Output leftWheelVelocity;
	Output rightWheelVelocity;
	
	public GoalTester() {
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
        
		GoalSeeking goalSeeking = new GoalSeeking(-180.0, -60.0, -120.0, 0.0, 60.0, 180.0, 0.0, 120.0, -30.0, 0.0, 0.0, 30.0, -100.0, 0.0, 0.0, 100.0, leftWheelLow, leftWheelMedium, leftWheelHigh, rightWheelLow, rightWheelMedium, rightWheelHigh);
//		    try {
//		        System.loadLibrary("AriaJava");
//		    } catch (UnsatisfiedLinkError e) {
//		      System.err.println("Native code library libAriaJava failed to load. Make sure that its directory is in your library path; See javaExamples/README.txt and the chapter on Dynamic Linking Problems in the SWIG Java documentation (http://www.swig.org) for help.\n" + e);
//		      System.exit(1);
//		    }
//		  
//		
//		    Aria.init();
//
//		    ArRobot robot = new ArRobot();
//		    ArSimpleConnector conn = new ArSimpleConnector(new String[]{});
//		 
//		    if(!Aria.parseArgs())
//		    {
//		      Aria.logOptions();
//		      Aria.exit(1);
//		    }
//
//		    if (!conn.connectRobot(robot))
//		    {
//		      System.err.println("Could not connect to robot, exiting.\n");
//		      System.exit(1);
//		    }
//		    robot.runAsync(true);
//		    
//			while(true) {
//				//input
//
//				double angle = 90-Math.toDegrees(Math.atan(Math.abs( robot.getX())/Math.abs( robot.getY())));
//				//System.out.println("Left Front: "+ leftFrontSonar + " Left Back : " + leftBackSonar + " Right front: " + rightFrontSonar + " Right back: " + rightBackSonar + " Front: " + frontMiddleSonar + " Front Left: "  + frontLeftSonar + "Front Right " + frontRightSonar);
//				//double bearing = 90-(180/Math.PI)*Math.atan2(GOAL_Y-robot.getY(),GOAL_X-robot.getX());
//				double bearing = robot.getTh()+angle;
//				//System.out.println(bearing );
//				
//				goalSeeking.setInput(bearing);
//				System.out.print(robot.getPose().getX());
//				System.out.println(" angle: "+ robot.getY());
//
////				double leftLeft = leftWallFollowing.getRulebase().evaluate(0).get(leftWheelVelocity);
////				double leftRight = leftWallFollowing.getRulebase().evaluate(0).get(rightWheelVelocity);
////				double rightLeft = rightWallFollowing.getRulebase().evaluate(0).get(leftWheelVelocity);
////				double rightRight = rightWallFollowing.getRulebase().evaluate(0).get(rightWheelVelocity);
//				double leftOutput = goalSeeking.getRulebase().evaluate(0).get(leftWheelVelocity);
//				double rightOutput = goalSeeking.getRulebase().evaluate(0).get(rightWheelVelocity);
//				//System.out.println(obstacleLeft + " " + obstacleRight);
//				
//			    robot.enableMotors();
//
//			    	robot.lock();
//			        robot.setVel2(leftOutput, rightOutput);
//			        robot.unlock();
//
//			    ArUtil.sleep(50);
//				
//			}
//	
	}
	
}
