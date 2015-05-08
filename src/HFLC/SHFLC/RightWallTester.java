package HFLC.SHFLC;

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
import type1.sets.T1MF_Trapezoidal;
import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.ArUtil;
import com.mobilerobots.Aria.Aria;

public class RightWallTester {
	// Output
	Output leftWheelVelocity;
	Output rightWheelVelocity;

	T1MF_Trapezoidal highMF;

	public RightWallTester() {
		Input front = new Input("Front Sonar", new Tuple(0, 1000));
		Input back = new Input("Back Sonar", new Tuple(0, 1000));
		// 50, 500, 700, 850
		T1MF_Trapezoidal closeLowerMF = new T1MF_Trapezoidal("Lower MF Close",
				new double[] { 0.0, 0.0, 50.0, 500.0 });
		T1MF_Trapezoidal closeUpperMF = new T1MF_Trapezoidal("Upper MF Close",
				new double[] { 0.0, 0.0, 700.0, 850.0 });
		IntervalT2MF_Trapezoidal closeMF = new IntervalT2MF_Trapezoidal(
				"IT2MF Close", closeUpperMF, closeLowerMF);

//Change values to test
		// Membership function - far. Definition
		T1MF_Trapezoidal farLowerMF = new T1MF_Trapezoidal("Lower MF Far",
				new double[] { 500.0, 850.0, 1000, 1000 });
		T1MF_Trapezoidal farUpperMF = new T1MF_Trapezoidal("Upper MF Far",
				new double[] { 150.0, 350.0, 1000, 1000 });
		IntervalT2MF_Trapezoidal farMF = new IntervalT2MF_Trapezoidal(
				"IT2MF Far", farUpperMF, farLowerMF);

		IT2_Antecedent closeFront = new IT2_Antecedent("Close Front", closeMF,
				front);
		IT2_Antecedent farFront = new IT2_Antecedent("Far Front", farMF, front);
		IT2_Antecedent closeBack = new IT2_Antecedent("Close Back", closeMF,
				back);
		IT2_Antecedent farBack = new IT2_Antecedent("Far Back", farMF, back);

		// Output definition
		leftWheelVelocity = new Output("Left Wheel Velocity", new Tuple(0, 500));
		rightWheelVelocity = new Output("Right Wheel Velocity", new Tuple(0,
				500));

		// Output membership function - low. Definition
		T1MF_Trapezoidal lowLowerMF = new T1MF_Trapezoidal("Lower MF Low",
				new double[] { 0, 0, 0, 225.0 });
		T1MF_Trapezoidal lowUpperMF = new T1MF_Trapezoidal("Upper MF Low",
				new double[] { 0, 0, 50.0, 275.0 });
		IntervalT2MF_Trapezoidal lowMF = new IntervalT2MF_Trapezoidal(
				"IT2MF Low", lowUpperMF, lowLowerMF);

		// Output membership function - medium. Definition
		T1MF_Trapezoidal mediumLowerMF = new T1MF_Trapezoidal(
				"Lower MF Medium", new double[] { 200.0, 250.0, 250.0, 300.0 });
		T1MF_Trapezoidal mediumUpperMF = new T1MF_Trapezoidal(
				"Upper MF Medium", new double[] { 100.0, 200.0, 300.0, 400.0 });
		IntervalT2MF_Trapezoidal mediumMF = new IntervalT2MF_Trapezoidal(
				"IT2MF Medium", mediumUpperMF, mediumLowerMF);

		// Output membership function - high. Definition
		T1MF_Trapezoidal highLowerMF = new T1MF_Trapezoidal("Lower MF High",
				new double[] { 275.0, 500.0, 500.0, 500.0 });
		T1MF_Trapezoidal highUpperMF = new T1MF_Trapezoidal("Upper MF High",
				new double[] { 225.0, 450.0, 500.0, 500.0 });
		IntervalT2MF_Trapezoidal highMF = new IntervalT2MF_Trapezoidal(
				"IT2MF High", highUpperMF, highLowerMF);

		// Consequent definition
		IT2_Consequent leftWheelLow = new IT2_Consequent("Low", lowMF,
				leftWheelVelocity);
		IT2_Consequent leftWheelMedium = new IT2_Consequent("Medium", mediumMF,
				leftWheelVelocity);
		IT2_Consequent leftWheelHigh = new IT2_Consequent("High", highMF,
				leftWheelVelocity);
		IT2_Consequent rightWheelLow = new IT2_Consequent("Low", lowMF,
				rightWheelVelocity);
		IT2_Consequent rightWheelMedium = new IT2_Consequent("Medium",
				mediumMF, rightWheelVelocity);
		IT2_Consequent rightWheelHigh = new IT2_Consequent("High", highMF,
				rightWheelVelocity);

		IT2_Rulebase rulebase = new IT2_Rulebase(4);
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { closeFront,
				closeBack }, new IT2_Consequent[] { leftWheelLow,
				rightWheelMedium }));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { closeFront,
				farBack },
				new IT2_Consequent[] { leftWheelLow, rightWheelHigh }));
		rulebase.addRule(new IT2_Rule(new IT2_Antecedent[] { farFront,
				closeBack }, new IT2_Consequent[] { leftWheelHigh,
				rightWheelLow }));
		rulebase.addRule(new IT2_Rule(
				new IT2_Antecedent[] { farFront, farBack },
				new IT2_Consequent[] { leftWheelMedium, rightWheelLow }));

		try {
			System.loadLibrary("AriaJava");
		} catch (UnsatisfiedLinkError e) {
			System.err
					.println("Native code library libAriaJava failed to load. Make sure that its directory is in your library path; See javaExamples/README.txt and the chapter on Dynamic Linking Problems in the SWIG Java documentation (http://www.swig.org) for help.\n"
							+ e);
			System.exit(1);
		}

		Aria.init();

		ArRobot robot = new ArRobot();
		ArSimpleConnector conn = new ArSimpleConnector(new String[] {});

		if (!Aria.parseArgs()) {
			Aria.logOptions();
			Aria.exit(1);
		}

		if (!conn.connectRobot(robot)) {
			System.err.println("Could not connect to robot, exiting.\n");
			System.exit(1);
		}
		robot.runAsync(true);

		while (true) {
			// input

			double rightFrontSonar = robot.getSonarRange(6);
			double rightBackSonar = robot.getSonarRange(9);

			front.setInput(rightFrontSonar);
			back.setInput(rightBackSonar);

			double leftOutput = rulebase.evaluate(0).get(leftWheelVelocity);
			double rightOutput = rulebase.evaluate(0).get(rightWheelVelocity);

			robot.enableMotors();

			robot.lock();
			robot.setVel2(leftOutput, rightOutput);
			robot.unlock();

			ArUtil.sleep(50);

		}

	}

	private void plotMFs(String name, IntervalT2MF_Interface[] sets,
			int discretizationLevel) {
		JMathPlotter plotter = new JMathPlotter();
		plotter.plotMF(sets[0].getName(), sets[0], discretizationLevel, null,
				false);

		for (int i = 1; i < sets.length; i++) {
			plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel,
					null, false);
		}
		plotter.show(name);
	}
}
