package org.firstinspires.ftc.teamcode.pedroPathing;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.telemetryM;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/// MESSAGE FOR BARON: Is all of this right?

@Autonomous(name = "PedroAutoBlue", group = "Auto")
public class PedroAutoBlue extends OpMode {
    public DcMotorSimple ShooterMotor;
    public DcMotorSimple IntakeMotor1;
    public DcMotorSimple IntakeMotor2;
    public void setPathState(int pState) {
        pathState = pState;
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
        follower.setStartingPose(new Pose(22,122));
        follower.setHeading(Math.toRadians(143));
        buildPaths();
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        pathTimer.resetTimer();
        opmodeTimer.resetTimer();
        setPathState(0);
    }
    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    private Path StartScore;
    private PathChain GoPickup1, GrabPickup1, ScorePickup1, GoPickup2, GrabPickup2, ScorePickup2, GoPickup3, GrabPickup3, ScorePickup3, End;

    private void buildPaths() {
        StartScore = new Path(new BezierLine(new Pose(22, 122.000), new Pose(46.000, 96.000)));
        StartScore.setConstantHeadingInterpolation(Math.toRadians(143));

        GoPickup1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000),
                                //new Pose(45.134, 70.028),
                                new Pose(44.000, 36.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(-180),0.8)

                .build();

        GrabPickup1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 36.000),

                                new Pose(32.000, 36.000)
                        )
                ).setConstantHeadingInterpolation(-180)

                .build();

        ScorePickup1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.000, 36.000),
                                //new Pose(39.883, 74.413),
                                new Pose(46.000, 96.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-180), Math.toRadians(143),0.8)

                .build();

        GoPickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000),
                                //new Pose(44.709, 83.216),
                                new Pose(46.000, 60.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(-180),0.8)

                .build();

        GrabPickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 60.000),

                                new Pose(40.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(-180)

                .build();

        ScorePickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(33.000, 60.000),
                                //new Pose(39.4990477077817, 77.99525264739836),
                                new Pose(46.000, 96.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-180), Math.toRadians(143),0.8)

                .build();

        GoPickup3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000),

                                new Pose(44.000, 84.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(-180),0.8)

                .build();

        GrabPickup3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 84.000),

                                new Pose(40.000, 84.000)
                        )
                ).setConstantHeadingInterpolation(-180)

                .build();

        ScorePickup3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.000, 84.000),

                                new Pose(46.000, 96.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-180), Math.toRadians(143),0.8)

                .build();

        End = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000),

                                new Pose(60.000, 130.000)
                        )
                ).setConstantHeadingInterpolation(143)

                .build();
    }
    /// Reminder: CREATE NEW SEPARATE STATES FOR SHOOTING
    private void autonomousPathUpdate() {
        
        switch (pathState) {
            //Starting pose
            case 0: {
                ShooterMotor.setPower(.6);
                follower.followPath(StartScore, true);
                setPathState(20);
                pathTimer.resetTimer();
            }
            case 20:
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTimeSeconds() > 3 && pathTimer.getElapsedTimeSeconds() < 4.05) {
                        IntakeMotor2.setPower(-1);
                    }
                    if (pathTimer.getElapsedTimeSeconds() > 8 && pathTimer.getElapsedTimeSeconds() < 9) {
                        IntakeMotor2.setPower(-1);
                        IntakeMotor1.setPower(-1);
                    }
                }
                break;
            case 2:
                ShooterMotor.setPower(0);
                IntakeMotor1.setPower(1);
                IntakeMotor2.setPower(1);
                follower.followPath(GoPickup1,true);
                IntakeMotor1.setPower(-1);
                setPathState(3);
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup1,true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    ShooterMotor.setPower(.65);
                    follower.followPath(ScorePickup1, true);
                    setPathState(21);
                    pathTimer.resetTimer();
                }
                break;

            case 21:
                if (!follower.isBusy()) {
                    double timeElapsed = pathTimer.getElapsedTimeSeconds();
                    IntakeMotor2.setPower(-1);
                    IntakeMotor1.setPower(-1);
                    if (timeElapsed > .5) {
                        IntakeMotor2.setPower(0);
                    }
                    if (timeElapsed > 1) {
                        IntakeMotor2.setPower(-1);
                        setPathState(5);
                    }
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(GoPickup2,true);
                    ShooterMotor.setPower(-.2);
                    IntakeMotor2.setPower(0);
                    IntakeMotor1.setPower(-1);
                    setPathState(6);
                }
                break;

            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup2,true);
                    setPathState(7);
                }
                break;

            case 7:
                if (!follower.isBusy()) {
                    ShooterMotor.setPower(.65);
                    follower.followPath(ScorePickup2, true);
                    setPathState(22);
                    pathTimer.resetTimer();
                }
                break;

            case 22:
                if (!follower.isBusy()) {
                    double timeElapsed = pathTimer.getElapsedTimeSeconds();
                    IntakeMotor2.setPower(-1);
                    IntakeMotor1.setPower(-1);
                    if (timeElapsed > .5) {
                        IntakeMotor2.setPower(0);
                    }
                    if (timeElapsed > 1) {
                        IntakeMotor2.setPower(-1);
                        setPathState(8);
                    }
                }
                break;

            case 8:
                if (!follower.isBusy()) {
                    follower.followPath(GoPickup3,true);
                    IntakeMotor2.setPower(0);
                    ShooterMotor.setPower(-.2);
                    IntakeMotor1.setPower(-1);
                    setPathState(9);
                }
                break;

            case 9:
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup3);
                    setPathState(10);
                }
                break;

            case 10:
                if (!follower.isBusy()) {
                    ShooterMotor.setPower(.65);
                    follower.followPath(ScorePickup3, true);
                    setPathState(23);
                    pathTimer.resetTimer();
                }
                break;

            case 23:
                if (!follower.isBusy()) {
                    double timeElapsed = pathTimer.getElapsedTimeSeconds();
                    IntakeMotor2.setPower(-1);
                    IntakeMotor1.setPower(-1);
                    if (timeElapsed > .5) {
                        IntakeMotor2.setPower(0);
                    }
                    if (timeElapsed > 1) {
                        IntakeMotor2.setPower(-1);
                        setPathState(12);
                    }
                }
                break;

            case 12:
                if (!follower.isBusy()) {
                    follower.followPath(End, true);
                    ShooterMotor.setPower(0);
                    IntakeMotor1.setPower(0);
                    IntakeMotor2.setPower(0);
                }
                break;
        }
    }
    @Override
    public void stop() {
    }
}