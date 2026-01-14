//package org.firstinspires.ftc.teamcode.pedroPathing;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierCurve;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.Path;
//import com.pedropathing.paths.PathChain;
//import com.pedropathing.util.Timer;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
////import com.qualcomm.robotcore.hardware.DcMotorEx;
/// MESSAGE FOR BARON: Just ignore this.
//
//@Autonomous(name = "PedroAutoRed", group = "Auto")
//public class PedroAutoRed extends OpMode {
////    public DcMotorEx ShooterMotor;
////    public DcMotorEx IntakeMotor1;
////    public DcMotorEx IntakeMotor2;
//public void setPathState(int pState) {
//    pathState = pState;
//    pathTimer.resetTimer();
//}
//
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
//    @Override
//    public void init() {
//        pathTimer = new Timer();
//        opmodeTimer = new Timer();
//        opmodeTimer.resetTimer();
//
//        // Hardware Map
//        //ShooterMotor = hardwareMap.get(DcMotorEx.class, "ShooterMotor");
//        //IntakeMotor1 = hardwareMap.get(DcMotorEx.class, "IntakeMotor1");
//        //IntakeMotor2 = hardwareMap.get(DcMotorEx.class, "IntakeMotor2");
//
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(new Pose(122, 122, 43));
//        buildPaths();
//    }
//
//    @Override
//    public void init_loop() {
//    }
//
//    @Override
//    public void start() {
//        opmodeTimer.resetTimer();
//        setPathState(0);
//    }
//    private Follower follower;
//    private Timer pathTimer, opmodeTimer;
//    private int pathState;
//    private Path StartScore;
//    private PathChain GoPickup1, GrabPickup1, ScorePickup1, GoPickup2, GrabPickup2, ScorePickup2, GoPickup3, GrabPickup3, ScorePickup3, End;
///// Below is building the paths
//    private void buildPaths() {
//        StartScore = new Path(new BezierLine(new Pose(122.000, 122.000, 43), new Pose(96.000, 96.000, 43)));
//        StartScore.setLinearHeadingInterpolation(new Pose(122,122,43).getHeading(), new Pose(96,96,43).getHeading());
//
//        GoPickup1 = follower.pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(96.000, 96.000,43),
//                                new Pose(97.95797775030248, 66.64005346235581),
//                                new Pose(100.000, 36.000, -86)
//                        )
//                ).setLinearHeadingInterpolation(new Pose(96,96).getHeading(), new Pose(100,36).getHeading())
//
//                .build();
//
//        GrabPickup1 = follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(100.000, 36.000,-86),
//
//                                new Pose(130.000, 36.000,0)
//                        )
//                ).setLinearHeadingInterpolation(new Pose(100,36).getHeading(),)
//
//                .build();
//
//        ScorePickup1 = follower.pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(130.000, 36.000,0),
//                                new Pose(115.83154904423336, 35.99993183069257),
//                                new Pose(100, 36.000,180)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//
//        GoPickup2 = follower.pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(96.000, 96.000,101),
//                                new Pose(72.000, 55.000),
//                                new Pose(108.000, 60.000)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//
//        GrabPickup2 = follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(108.000, 60.000),
//
//                                new Pose(120.000, 60.000)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//
//        ScorePickup2 = follower.pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(120.000, 60.000),
//                                new Pose(109.000, 77.000),
//                                new Pose(96.000, 96.000)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//
//        GoPickup3 = follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(96.000, 96.000),
//
//                                new Pose(96.000, 84.000)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//
//        GrabPickup3 = follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(96.000, 84.000),
//
//                                new Pose(125.000, 84.000)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//
//        ScorePickup3 = follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(125.000, 84.000),
//
//                                new Pose(96.000, 96.000)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//
//        End = follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(96.000, 96.000),
//
//                                new Pose(84.000, 130.000)
//                        )
//                ).setLinearHeadingInterpolation()
//
//                .build();
//    }
//
//    private void autonomousPathUpdate() {
//        switch (pathState) {
//            case 0: {
//                follower.followPath(StartScore, true);
////                ShooterMotor.setPower(1);
////                IntakeMotor2.setPower(.5);
////                try {
////                    wait(150);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
////                IntakeMotor2.setPower(0);
////                try {
////                    wait(150);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
////                IntakeMotor2.setPower(1);
////                try {
////                    wait(150);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
////                ShooterMotor.setPower(0);
////                IntakeMotor2.setPower(0);
//                setPathState(1);
//            }
//            break;
//
//            case 1:
//                if (!follower.isBusy()) {
//                    follower.followPath(GoPickup1);
//                    setPathState(2);
//                }
//                break;
//            case 2:
//                if (!follower.isBusy()) {
//                    follower.followPath(GrabPickup1);
////                    ShooterMotor.setPower(-0.3);
////                    IntakeMotor1.setPower(1);
////                    try {
////                        wait(300);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(.5);
////                    try {
////                        wait(300);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor1.setPower(0);
////                    IntakeMotor2.setPower(0);
////                    try {
////                        wait(30);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    ShooterMotor.setPower(0);
//                    setPathState(3);
//                }
//                break;
//            case 3:
//                if (!follower.isBusy()) {
//                    telemetry.addLine("It worked again!!!");
//                    follower.followPath(ScorePickup1, true);
////                    ShooterMotor.setPower(1);
////                    IntakeMotor2.setPower(.5);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(0);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(1);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    ShooterMotor.setPower(0);
////                    IntakeMotor2.setPower(0);
//                    setPathState(4);
//                }
//                break;
//
//            case 4:
//                if (!follower.isBusy()) {
//                    follower.followPath(GoPickup2);
//                    setPathState(5);
//                }
//                break;
//
//            case 5:
//                if (!follower.isBusy()) {
//                    follower.followPath(GrabPickup2);
////                    ShooterMotor.setPower(-0.3);
////                    IntakeMotor1.setPower(1);
////                    try {
////                        wait(300);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(.5);
////                    try {
////                        wait(300);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor1.setPower(0);
////                    IntakeMotor2.setPower(0);
////                    try {
////                        wait(30);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    ShooterMotor.setPower(0);
//                    setPathState(6);
//                }
//                break;
//
//            case 6:
//                if (!follower.isBusy()) {
//                    follower.followPath(ScorePickup2, true);
////                    ShooterMotor.setPower(1);
////                    IntakeMotor2.setPower(.5);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(0);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(1);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    ShooterMotor.setPower(0);
////                    IntakeMotor2.setPower(0);
//                    setPathState(7);
//                }
//                break;
//
//            case 7:
//                if (!follower.isBusy()) {
//                    follower.followPath(GoPickup3);
//                    setPathState(8);
//                }
//                break;
//
//            case 8:
//                if (!follower.isBusy()) {
//                    follower.followPath(GrabPickup3);
////                    ShooterMotor.setPower(-0.3);
////                    IntakeMotor1.setPower(1);
////                    try {
////                        wait(300);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(.5);
////                    try {
////                        wait(300);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor1.setPower(0);
////                    IntakeMotor2.setPower(0);
////                    try {
////                        wait(30);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    ShooterMotor.setPower(0);
//                    setPathState(9);
//                }
//                break;
//
//            case 9:
//                if (!follower.isBusy()) {
//                    follower.followPath(ScorePickup3, true);
////                    ShooterMotor.setPower(1);
////                    IntakeMotor2.setPower(.5);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(0);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    IntakeMotor2.setPower(1);
////                    try {
////                        wait(150);
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                    ShooterMotor.setPower(0);
////                    IntakeMotor2.setPower(0);
//                    setPathState(10);
//                }
//                break;
//
//            case 10:
//                if (!follower.isBusy()) {
//                    follower.followPath(End);
//                }
//                break;
//        }
//    }
//    @Override
//    public void stop() {
//    }
//}