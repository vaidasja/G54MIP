package HFLC.SHFLC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.system.IT2_Consequent;
import type1.sets.T1MF_Trapezoidal;
import generic.Output;
import generic.Tuple;

import com.mobilerobots.Aria.*;

/**
 * Singleton HFLC
 * 
 * This class is the hierarchical fuzzy logic controller.
 * 
 * @author Vaidas
 * 
 */

public class HFLC {

	// set the goal
	final int GOAL_X = 4000;
	final int GOAL_Y = -1000;

	// sonar sensor constants
	final int LEFT_FRONT_SONAR = 0;
	final int LEFT_BACK_SONAR = 15;
	final int RIGHT_FRONT_SONAR = 7;
	final int RIGHT_BACK_SONAR = 8;
	final int FRONT_LEFT_SONAR = 2;
	final int FRONT_RIGHT_SONAR = 5;
	final int FRONT_MIDDLE_SONAR_1 = 3;
	final int FRONT_MIDDLE_SONAR_2 = 4;

	// Output
	Output leftWheelVelocity;
	Output rightWheelVelocity;

	// Output membership function - low.
	T1MF_Trapezoidal lowLowerMF;
	T1MF_Trapezoidal lowUpperMF;
	IntervalT2MF_Trapezoidal lowMF;

	// Output membership function - medium.
	T1MF_Trapezoidal mediumLowerMF;
	T1MF_Trapezoidal mediumUpperMF;
	IntervalT2MF_Trapezoidal mediumMF;

	// Output membership function - high.
	T1MF_Trapezoidal highLowerMF;
	T1MF_Trapezoidal highUpperMF;
	IntervalT2MF_Trapezoidal highMF;

	// Consequents
	IT2_Consequent leftWheelLow;
	IT2_Consequent leftWheelMedium;
	IT2_Consequent leftWheelHigh;
	IT2_Consequent rightWheelLow;
	IT2_Consequent rightWheelMedium;
	IT2_Consequent rightWheelHigh;

	// Behaviors
	Coordination coordination;
	GoalSeeking goalSeeking;
	ObstacleAvoidance obstacleAvoidance;
	LeftWallFollowing leftWallFollowing;
	RightWallFollowing rightWallFollowing;

	/**
	 * HFLC Constructor. Creates the Hierarchical Fuzzy Logic Controller
	 * 
	 */
	public HFLC() {

		// Output definition
		leftWheelVelocity = new Output("Left Wheel Velocity", new Tuple(0, 500));
		rightWheelVelocity = new Output("Right Wheel Velocity", new Tuple(0,
				500));

		// Output membership function - low. Definition
		lowLowerMF = new T1MF_Trapezoidal("Lower MF Low", new double[] { 0, 0,
				0, 125.0 });
		lowUpperMF = new T1MF_Trapezoidal("Upper MF Low", new double[] { 0.0,
				0.0, 50.0, 175.0 });
		lowMF = new IntervalT2MF_Trapezoidal("IT2MF Low", lowUpperMF,
				lowLowerMF);

		// Output membership function - medium. Definition
		mediumLowerMF = new T1MF_Trapezoidal("Lower MF Medium", new double[] {
				50.0, 150.0, 150.0, 200.0 });
		mediumUpperMF = new T1MF_Trapezoidal("Upper MF Medium", new double[] {
				0.0, 100.0, 200.0, 300.0 });
		mediumMF = new IntervalT2MF_Trapezoidal("IT2MF Medium", mediumUpperMF,
				mediumLowerMF);

		// Output membership function - high. Definition
		highUpperMF = new T1MF_Trapezoidal("Lower MF High", new double[] {
				125.0, 250.0, 500.0, 500.0 });
		highLowerMF = new T1MF_Trapezoidal("Upper MF High", new double[] {
				175.0, 300.0, 500.0, 500.0 });
		highMF = new IntervalT2MF_Trapezoidal("IT2MF High", highUpperMF,
				highLowerMF);

		// Consequent definition
		leftWheelLow = new IT2_Consequent("Low", lowMF, leftWheelVelocity);
		leftWheelMedium = new IT2_Consequent("Medium", mediumMF,
				leftWheelVelocity);
		leftWheelHigh = new IT2_Consequent("High", highMF, leftWheelVelocity);
		rightWheelLow = new IT2_Consequent("Low", lowMF, rightWheelVelocity);
		rightWheelMedium = new IT2_Consequent("Medium", mediumMF,
				rightWheelVelocity);
		rightWheelHigh = new IT2_Consequent("High", highMF, rightWheelVelocity);

		// Initialising the behaviours
		coordination = new Coordination(5000.0, 5000.0, 5000.0, 0.0, 500.0,
				500.0, 1000.0, 0.0, 800.0, 200.0, 1000.0, 0.0, 800.0, 200.0,
				1000.0, 700.0, 1700.0, 500.0, 1500.0);
		leftWallFollowing = new LeftWallFollowing(1000.0, 90.0, 546.0, 349.0,
				592.0, 471.0, 861.0, 426.0, 559.0, leftWheelLow,
				leftWheelMedium, leftWheelHigh, rightWheelLow,
				rightWheelMedium, rightWheelHigh);
		rightWallFollowing = new RightWallFollowing(1000.0, 90.0, 546.0, 349.0,
				592.0, 471.0, 861.0, 426.0, 559.0, leftWheelLow,
				leftWheelMedium, leftWheelHigh, rightWheelLow,
				rightWheelMedium, rightWheelHigh);
		obstacleAvoidance = new ObstacleAvoidance(2000.0, 90.0, 1020.0, 600.0,
				1050.0, 980.0, 1600.0, 950.0, 1400.0, leftWheelLow,
				leftWheelMedium, leftWheelHigh, rightWheelLow,
				rightWheelMedium, rightWheelHigh);
		goalSeeking = new GoalSeeking(-180.0, -60.0, -10.0, 0.0, 60.0, 180.0,
				0.0, 10.0, -30.0, 0.0, 0.0, 30.0, -100.0, 0.0, 0.0, 100.0,
				leftWheelLow, leftWheelMedium, leftWheelHigh, rightWheelLow,
				rightWheelMedium, rightWheelHigh);
	}

	public void run() {

		// loading the Aria library
		try {
			System.loadLibrary("AriaJava");
		} catch (UnsatisfiedLinkError e) {
			System.err
					.println("Native code library libAriaJava failed to load. Make sure that its directory is in your library path; See javaExamples/README.txt and the chapter on Dynamic Linking Problems in the SWIG Java documentation (http://www.swig.org) for help.\n"
							+ e);
			System.exit(1);
		}

		Aria.init();

		// robot initialisation
		ArRobot robot = new ArRobot();
		ArSimpleConnector conn = new ArSimpleConnector(new String[] {});

		// Processing any passed arguments
		if (!Aria.parseArgs()) {
			Aria.logOptions();
			Aria.exit(1);
		}

		if (!conn.connectRobot(robot)) {
			System.err.println("Could not connect to robot, exiting.\n");
			System.exit(1);
		}

		robot.runAsync(true);

		// Uncomment to store the path in a string
		// String path="";

		// While the robot hasn't reached the goal
		while (robot.getX() < 4000) {

			// Input
			double leftFrontSonar = robot.getSonarRange(LEFT_FRONT_SONAR);
			double leftBackSonar = robot.getSonarRange(LEFT_BACK_SONAR);
			double rightFrontSonar = robot.getSonarRange(RIGHT_FRONT_SONAR);
			double rightBackSonar = robot.getSonarRange(RIGHT_BACK_SONAR);

			// Obstacle input
			double frontLeftSonar = robot.getSonarRange(FRONT_LEFT_SONAR);
			double frontMiddleSonar = Math.min(
					robot.getSonarRange(FRONT_MIDDLE_SONAR_1),
					robot.getSonarRange(FRONT_MIDDLE_SONAR_2));
			double frontRightSonar = robot.getSonarRange(FRONT_RIGHT_SONAR);

			// Calculating the robot's signed bearing from the goal
			double bearing = (Math.atan2(GOAL_X, GOAL_Y) - robot.getTh())
					+ (90 - ((180 / Math.PI) * Math.atan2(
							GOAL_X - robot.getX(), GOAL_Y - robot.getY())));
			if (bearing <= -180) {
				bearing = bearing + 360;
			} else if (bearing >= 180) {
				bearing = bearing - 360;
			}

			leftWallFollowing.setBackInput(leftBackSonar);
			leftWallFollowing.setFrontInput(leftFrontSonar);

			rightWallFollowing.setBackInput(rightBackSonar);
			rightWallFollowing.setFrontInput(rightFrontSonar);

			obstacleAvoidance.setLeftInput(frontLeftSonar);
			obstacleAvoidance.setMiddleInput(frontMiddleSonar);
			obstacleAvoidance.setRightInput(frontRightSonar);

			goalSeeking.setInput(bearing);

			IT2_Consequent leftWallFollowConsequentLeft = new IT2_Consequent(
					(Tuple) leftWallFollowing.getRulebase()
							.evaluateGetCentroid(0).get(leftWheelVelocity)[0]);
			IT2_Consequent leftWallFollowConsequentRight = new IT2_Consequent(
					(Tuple) leftWallFollowing.getRulebase()
							.evaluateGetCentroid(0).get(rightWheelVelocity)[0]);
			IT2_Consequent rightWallFollowConsequentLeft = new IT2_Consequent(
					(Tuple) rightWallFollowing.getRulebase()
							.evaluateGetCentroid(0).get(leftWheelVelocity)[0]);
			IT2_Consequent rightWallFollowConsequentRight = new IT2_Consequent(
					(Tuple) rightWallFollowing.getRulebase()
							.evaluateGetCentroid(0).get(rightWheelVelocity)[0]);
			IT2_Consequent obstacleConsequentLeft = new IT2_Consequent(
					(Tuple) obstacleAvoidance.getRulebase()
							.evaluateGetCentroid(0).get(leftWheelVelocity)[0]);
			IT2_Consequent obstacleConsequentRight = new IT2_Consequent(
					(Tuple) obstacleAvoidance.getRulebase()
							.evaluateGetCentroid(0).get(rightWheelVelocity)[0]);
			IT2_Consequent goalConsequentLeft = new IT2_Consequent(
					(Tuple) goalSeeking.getRulebase().evaluateGetCentroid(0)
							.get(leftWheelVelocity)[0]);
			IT2_Consequent goalConsequentRight = new IT2_Consequent(
					(Tuple) goalSeeking.getRulebase().evaluateGetCentroid(0)
							.get(rightWheelVelocity)[0]);

			leftWallFollowConsequentLeft.setOutput(leftWheelVelocity);
			leftWallFollowConsequentRight.setOutput(rightWheelVelocity);
			rightWallFollowConsequentLeft.setOutput(leftWheelVelocity);
			rightWallFollowConsequentRight.setOutput(rightWheelVelocity);
			obstacleConsequentLeft.setOutput(leftWheelVelocity);
			obstacleConsequentRight.setOutput(rightWheelVelocity);
			goalConsequentLeft.setOutput(leftWheelVelocity);
			goalConsequentRight.setOutput(rightWheelVelocity);

			coordination.createRulebase(leftWallFollowConsequentLeft,
					leftWallFollowConsequentRight,
					rightWallFollowConsequentLeft,
					rightWallFollowConsequentRight, obstacleConsequentLeft,
					obstacleConsequentRight, goalConsequentLeft,
					goalConsequentRight);

			// sends minimum values of each behaviour's sonars
			if (leftFrontSonar <= leftBackSonar) {
				coordination.setLeftInput(leftFrontSonar);
			} else {
				coordination.setLeftInput(leftBackSonar);
			}

			if (rightFrontSonar <= rightBackSonar) {
				coordination.setRightInput(rightFrontSonar);
			} else {
				coordination.setRightInput(rightBackSonar);
			}

			if (frontLeftSonar <= frontMiddleSonar
					&& frontLeftSonar <= frontRightSonar) {
				coordination.setObstacleInput(frontLeftSonar);
			} else if (frontRightSonar <= frontMiddleSonar
					&& frontRightSonar <= frontLeftSonar) {
				coordination.setObstacleInput(frontRightSonar);
			} else {
				coordination.setObstacleInput(frontMiddleSonar);
			}

			coordination.setGoalInput();

			double leftOutput = coordination.getRulebase().evaluate(0)
					.get(leftWheelVelocity);
			double rightOutput = coordination.getRulebase().evaluate(0)
					.get(rightWheelVelocity);

			// Uncomment to add point to the path
			// path=path+"\n"+robot.getX()+","+robot.getY();

			robot.enableMotors();

			robot.lock();
			robot.setVel2(leftOutput, rightOutput);
			robot.unlock();

			ArUtil.sleep(50);
		}
		robot.stop();

// Uncomment to write the path to a file
//
//		try {
//			File file = new File("simGoalTestLeft5.csv");
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(path);
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		Aria.exit();

	}
}
