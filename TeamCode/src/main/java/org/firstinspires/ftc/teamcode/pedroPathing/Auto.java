package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "PedroAuto", group = "Pedro!!!")
public class Auto extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;

    // Motors
    private DcMotorEx ShooterMotor;
    private DcMotorEx IntakeMotor1;
    private DcMotorEx IntakeMotor2;

    // Poses
    private final Pose startPose = new Pose(22, 122, Math.toRadians(143));
    private final Pose scorePose = new Pose(48, 96, Math.toRadians(143));
    private final Pose pickup1Pose = new Pose(32, 84, Math.toRadians(0));
    private final Pose pickup2Pose = new Pose(32, 60, Math.toRadians(0));
    private final Pose pickup3Pose = new Pose(32, 36, Math.toRadians(0));
    private final Pose endPose = new Pose(48, 12, Math.toRadians(0));

    // Paths
    private Path scorePreload;
    private PathChain grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3, finalPose1;

    public void buildPaths() {
        // Preload Path
        scorePreload = new Path(new BezierLine(startPose, scorePose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        // Cycle 1 Paths
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, pickup1Pose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(pickup1Pose, scorePose))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        // Cycle 2 Paths
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, pickup2Pose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(pickup2Pose, scorePose))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        // Cycle 3 Paths
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, pickup3Pose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierCurve(pickup3Pose, scorePose))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        // Park Path
        finalPose1 = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, endPose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), endPose.getHeading())
                .build();
    }

    public void statePathUpdate() throws InterruptedException {
        switch (pathState) {
            case 0: // Drive to Score Preload
                follower.followPath(scorePreload, true);
                setPathState(1);
                break;

            case 1: // Wait until we arrive at Score Pose
                if(!follower.isBusy()) {
                    telemetry.addLine("It worked!!!");
                    ShooterMotor.setPower(.65);
                    wait(2500);
                    IntakeMotor2.setPower(.75);
                    wait(200);
                    IntakeMotor2.setPower(0);
                    ShooterMotor.setPower(1);
                    wait(150);
                    ShooterMotor.setPower(.65);
                    IntakeMotor2.setPower(1);
                    wait(1);
                    IntakeMotor2.setPower(.65);
                    ShooterMotor.setPower(-0.075);
                    IntakeMotor1.setPower(1);
                    follower.followPath(grabPickup1);
                    setPathState(2);
                }
                break;
            case 2: //Going to scorepose
                if (!follower.isBusy()) {
                    follower.followPath(scorePickup1);
                    setPathState(3);
                }
                break;
            case 3: // Scoring Pickup 1
                if(!follower.isBusy()) {
                    telemetry.addLine("It worked again!!!");
                    ShooterMotor.setPower(.65);
                    wait(2500);
                    IntakeMotor2.setPower(.75);
                    wait(200);
                    IntakeMotor2.setPower(0);
                    ShooterMotor.setPower(1);
                    wait(150);
                    ShooterMotor.setPower(.65);
                    IntakeMotor2.setPower(1);
                    wait(1);
                    IntakeMotor2.setPower(.65);
                    ShooterMotor.setPower(-0.075);
                    IntakeMotor1.setPower(1);
                    follower.followPath(grabPickup2);
                    setPathState(4);
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(scorePickup2);
                    setPathState(5);
                }
                break;

            case 5: // ACTION: Grab pickup 2
                if (!follower.isBusy()) {
                    ShooterMotor.setPower(.65);
                    wait(2500);
                    IntakeMotor2.setPower(.75);
                    wait(200);
                    IntakeMotor2.setPower(0);
                    ShooterMotor.setPower(1);
                    wait(150);
                    ShooterMotor.setPower(.65);
                    IntakeMotor2.setPower(1);
                    wait(1);
                    IntakeMotor2.setPower(.65);
                    ShooterMotor.setPower(-0.075);
                    IntakeMotor1.setPower(1);
                    follower.followPath(grabPickup3);
                    setPathState(6);
                }
                break;

            case 6: // Drive to Score 1
                if(!follower.isBusy()) {
                    follower.followPath(scorePickup3);
                    setPathState(7);
                }
                break;

            case 7: // ACTION: Score Pickup 3
                if (!follower.isBusy()) {
                    ShooterMotor.setPower(.65);
                    IntakeMotor2.setPower(.75);
                    wait(200);
                    IntakeMotor2.setPower(0);
                    ShooterMotor.setPower(1);
                    ShooterMotor.setPower(.65);
                    IntakeMotor2.setPower(1);
                    wait(1);
                    IntakeMotor2.setPower(0);
                    ShooterMotor.setPower(0);
                    IntakeMotor1.setPower(0);
                    follower.followPath(finalPose1);
                    setPathState(8);
                }
                break;
        }
    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    private void autonomousPathUpdate() {

    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        // Hardware Map
        ShooterMotor = hardwareMap.get(DcMotorEx.class, "ShooterMotor");
        IntakeMotor1 = hardwareMap.get(DcMotorEx.class, "IntakeMotor1");
        IntakeMotor2 = hardwareMap.get(DcMotorEx.class, "IntakeMotor2");

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {}
}







/// Old code!!!
//package org.firstinspires.ftc.teamcode.pedroPathing;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.Path;
//import com.pedropathing.paths.PathChain;
//import com.pedropathing.util.Timer;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//// Make sure we import our new Constants file
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//@Autonomous(name = "Auto", group = "Examples")
//public class Auto extends OpMode {
//    private Follower follower;
//    private Timer pathTimer, actionTimer, opmodeTimer;
//    private int pathState;
//
//    /// These are our motors
//    /// We only have shooter motors because our drive motors and encoders are already defined in our constants file as a public  class(IDK why I didn't do that with this)
//    private DcMotorEx ShooterMotor;
//    private DcMotorEx IntakeMotor1;
//    private DcMotorEx IntakeMotor2;
///// The code below is the different points on the field our robot will go to
//    // Start Pose of Alfred.
//    private final Pose startPose = new Pose(22, 122, Math.toRadians(143));
//    // Scoring Pose of Alfred. It is facing the goal at a 143 degree angle.
//    private final Pose scorePose = new Pose(48, 96, Math.toRadians(143));
//    // Highest (First Set) of Artifacts from the Spike Mark.
//    private final Pose pickup1Pose = new Pose(32, 84, Math.toRadians(0));
//    // Middle (Second Set) of Artifacts from the Spike Mark.
//    private final Pose pickup2Pose = new Pose(32, 60, Math.toRadians(0));
//    // Lowest (Third Set) of Artifacts from the Spike Mark.
//    private final Pose pickup3Pose = new Pose(32, 36, Math.toRadians(0));
//    // final pose to get field centric working
//    private final Pose endPose = new Pose(48,12, Math.toRadians(0));
//
//    private Path scorePreload;
//    private PathChain grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3, finalPose1;
///// The code below is our auto paths
//    public void buildPaths() {
//        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
//        scorePreload = new Path(new BezierLine(startPose, scorePose));
//        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());
//
//        /* This is our grabPickup1 PathChain. */
//        grabPickup1 = follower.pathBuilder()
//                .addPath(new BezierLine(scorePose, pickup1Pose))
//                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
//                .build();
//
//        /* This is our scorePickup1 PathChain. */
//        scorePickup1 = follower.pathBuilder()
//                .addPath(new BezierLine(pickup1Pose, scorePose))
//                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
//                .build();
//
//        /* This is our grabPickup2 PathChain. */
//        grabPickup2 = follower.pathBuilder()
//                .addPath(new BezierLine(scorePose, pickup2Pose))
//                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
//                .build();
//
//        /* This is our scorePickup2 PathChain. */
//        scorePickup2 = follower.pathBuilder()
//                .addPath(new BezierLine(pickup2Pose, scorePose))
//                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
//                .build();
//
//        /* This is our grabPickup3 PathChain. */
//        grabPickup3 = follower.pathBuilder()
//                .addPath(new BezierLine(scorePose, pickup3Pose))
//                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
//                .build();
//
//        /* This is our scorePickup3 PathChain. */
//        scorePickup3 = follower.pathBuilder()
//                .addPath(new BezierLine(pickup3Pose, scorePose))
//                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
//                .build();
//
//        finalPose1 = follower.pathBuilder()
//                .addPath(new BezierLine(scorePose, endPose))
//                .setLinearHeadingInterpolation(scorePose.getHeading(), endPose.getHeading())
//                .build();
//    }
///// In case it isn't obvious, the code below is telling to robot to follow the paths we defined above
//    public void autonomousPathUpdate() {
//        switch (pathState) {
//            case 0:
//                follower.followPath(scorePreload);
//                setPathState(1);
//                break;
//            case 1:
//                /* Wait until the robot is close to scorePose */
//                if(!follower.isBusy()) {
//                    /* Since this is a pathChain, we can have Pedro hold the end point */
//                    follower.followPath(grabPickup1,true);
//                    setPathState(2);
//                }
//                break;
//            case 2:
//                if(!follower.isBusy()) {
//                    follower.followPath(scorePickup1,true);
//                    setPathState(3);
//                }
//                break;
//            case 3:
//                if(!follower.isBusy()) {
//                    follower.followPath(grabPickup2,true);
//                    setPathState(4);
//                }
//                break;
//            case 4:
//                if(!follower.isBusy()) {
//                    follower.followPath(scorePickup2,true);
//                    setPathState(5);
//                }
//                break;
//            case 5:
//                if(!follower.isBusy()) {
//                    follower.followPath(grabPickup3,true);
//                    setPathState(6);
//                }
//                break;
//            case 6:
//                if(!follower.isBusy()) {
//                    follower.followPath(scorePickup3, true);
//                    setPathState(7);
//                }
//                break;
//            case 7:
//                if(!follower.isBusy()) {
//                    setPathState(-1);
//                }
//                break;
//        }
//    }
//
//    /** These change the states of the paths and actions. **/
//    public void setPathState(int pState) {
//        pathState = pState;
//        pathTimer.resetTimer();
//    }
//
//    /** This is the main loop of the OpMode **/
//    @Override
//    public void loop() {
//        follower.update();
//        autonomousPathUpdate();
//
//        telemetry.addData("path state", pathState);
//        telemetry.addData("x", follower.getPose().getX());
//        telemetry.addData("y", follower.getPose().getY());
//        telemetry.addData("heading", follower.getPose().getHeading());
//        telemetry.update();
//    }
//
//    /** This method is called once at the init of the OpMode. **/
//    @Override
//    public void init() {
//        pathTimer = new Timer();
//        opmodeTimer = new Timer();
//        opmodeTimer.resetTimer();
//
//        // This uses the Constants file we just created
//        follower = Constants.createFollower(hardwareMap);
//
//        follower.setStartingPose(startPose);
//        buildPaths();
//    }
//
//    @Override
//    public void init_loop() {}
//
//    @Override
//    public void start() {
//        opmodeTimer.resetTimer();
//        setPathState(0);
//    }
//
//    @Override
//    public void stop() {}
//}
///// This is a backup of the old auto
////package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location
////
////import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
////import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
////import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;
////
////import com.pedropathing.follower.Follower;
////import com.pedropathing.geometry.BezierLine;
////import com.pedropathing.geometry.Pose;
////import com.pedropathing.paths.Path;
////import com.pedropathing.paths.PathChain;
////import com.pedropathing.util.Timer;
////import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
////import com.qualcomm.robotcore.eventloop.opmode.OpMode;
////
////@Autonomous(name = "Auto", group = "Examples")
////public class Auto extends OpMode {
////    private Follower follower;
////    private Timer pathTimer, actionTimer, opmodeTimer;
////    private int pathState;
////
////
////    private final Pose startPose = new Pose(21.721716514954487, 121.71651495448634, Math.toRadians(143)); // Start Pose of Alfred.
////    private final Pose scorePose = new Pose(48, 96, Math.toRadians(143)); // Scoring Pose of Alfred. It is facing the goal at a 143 degree angle.
////    private final Pose pickup1Pose = new Pose(32, 84, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
////    private final Pose pickup2Pose = new Pose(32, 60, Math.toRadians(0)); // Middle (Second Set) of Artifacts from the Spike Mark.
////    private final Pose pickup3Pose = new Pose(32, 36, Math.toRadians(0)); // Lowest (Third Set) of Artifacts from the Spike Mark.
////    private final Pose endPose = new Pose(48,12, Math.toRadians(0)); // final pose to get field centric working
////
////
////    private Path scorePreload;
////    private PathChain grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3, finalPose1;
////
////    public void buildPaths() {
////        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
////        scorePreload = new Path(new BezierLine(startPose, scorePose));
////        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());
////
////    /* Here is an example for Constant Interpolation
////    scorePreload.setConstantInterpolation(startPose.getHeading()); */
////
////        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
////        grabPickup1 = follower.pathBuilder()
////                .addPath(new BezierLine(scorePose, pickup1Pose))
////                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
////                .build();
////
////        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
////        scorePickup1 = follower.pathBuilder()
////                .addPath(new BezierLine(pickup1Pose, scorePose))
////                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
////                .build();
////
////        /* This is our grabPickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
////        grabPickup2 = follower.pathBuilder()
////                .addPath(new BezierLine(scorePose, pickup2Pose))
////                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
////                .build();
////
////        /* This is our scorePickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
////        scorePickup2 = follower.pathBuilder()
////                .addPath(new BezierLine(pickup2Pose, scorePose))
////                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
////                .build();
////
////        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
////        grabPickup3 = follower.pathBuilder()
////                .addPath(new BezierLine(scorePose, pickup3Pose))
////                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
////                .build();
////
////        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
////        scorePickup3 = follower.pathBuilder()
////                .addPath(new BezierLine(pickup3Pose, scorePose))
////                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
////                .build();
////// ... previous code for finalPose1 ...
////        finalPose1 = follower.pathBuilder()
////                .addPath(new BezierLine(scorePose, endPose))
////                .setLinearHeadingInterpolation(scorePose.getHeading(), endPose.getHeading())
////                .build();
////    } // <--- 1. ADD THIS to close buildPaths()
////
////    public void autonomousPathUpdate() { // <--- 2. ADD THIS to open the method
////        switch (pathState) {
////                case 0:
////                    follower.followPath(scorePreload);
////                    setPathState(1);
////                    break;
////                case 1:
////
////            /* You could check for
////            - Follower State: "if(!follower.isBusy()) {}"
////            - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
////            - Robot Position: "if(follower.getPose().getX() > 36) {}"
////            */
////
////                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
////                    if(!follower.isBusy()) {
////                        /* Score Preload */
////
////                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
////                        follower.followPath(grabPickup1,true);
////                        setPathState(2);
////                    }
////                    break;
////                case 2:
////                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
////                    if(!follower.isBusy()) {
////                        /* Grab Sample */
////
////                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
////                        follower.followPath(scorePickup1,true);
////                        setPathState(3);
////                    }
////                    break;
////                case 3:
////                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
////                    if(!follower.isBusy()) {
////                        /* Score Sample */
////
////                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
////                        follower.followPath(grabPickup2,true);
////                        setPathState(4);
////                    }
////                    break;
////                case 4:
////                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup2Pose's position */
////                    if(!follower.isBusy()) {
////                        /* Grab Sample */
////
////                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
////                        follower.followPath(scorePickup2,true);
////                        setPathState(5);
////                    }
////                    break;
////                case 5:
////                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
////                    if(!follower.isBusy()) {
////                        /* Score Sample */
////
////                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
////                        follower.followPath(grabPickup3,true);
////                        setPathState(6);
////                    }
////                    break;
////                case 6:
////                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
////                    if(!follower.isBusy()) {
////                        /* Grab Sample */
////
////                        /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
////                        follower.followPath(scorePickup3, true);
////                        setPathState(7);
////                    }
////                    break;
////                case 7:
////                    /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
////                    if(!follower.isBusy()) {
////                        /* Set the state to a Case we won't use or define, so it just stops running an new paths */
////                        setPathState(-1);
////                    }
////                    break;
////            }
////        }
////
/////** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
////        public void setPathState(int pState) {
////            pathState = pState;
////            pathTimer.resetTimer();
////        }
////
////    }
////
////    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
////    @Override
////    public void loop() {
////
////        // These loop the movements of the robot, these must be called continuously in order to work
////        follower.update();
////        autonomousPathUpdate();
////
////        // Feedback to Driver Hub for debugging
////        telemetry.addData("path state", pathState);
////        telemetry.addData("x", follower.getPose().getX());
////        telemetry.addData("y", follower.getPose().getY());
////        telemetry.addData("heading", follower.getPose().getHeading());
////        telemetry.update();
////    }
////
////    /** This method is called once at the init of the OpMode. **/
////    @Override
////    public void init() {
////        pathTimer = new Timer();
////        opmodeTimer = new Timer();
////        opmodeTimer.resetTimer();
////
////
////        follower = Constants.createFollower(hardwareMap);
////        buildPaths();
////        follower.setStartingPose(startPose);
////
////    }
////
////    /** This method is called continuously after Init while waiting for "play". **/
////    @Override
////    public void init_loop() {}
////
////    /** This method is called once at the start of the OpMode.
////     * It runs all the setup actions, including building paths and starting the path system **/
////    @Override
////    public void start() {
////        opmodeTimer.resetTimer();
////        setPathState(0);
////    }
////
////    /** We do not use this because everything should automatically disable **/
////    @Override
////    public void stop() {}