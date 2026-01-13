package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.opencv.core.Mat;

import kotlin.math.UMathKt;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/// MESSAGE FOR BARON: Is all of this right?

@Autonomous(name = "PedroAutoBlue", group = "Auto")
public class PedroAutoBlue extends OpMode {
    public DcMotorEx ShooterMotor;
    public DcMotorEx IntakeMotor1;
    public DcMotorEx IntakeMotor2;
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
        follower.setStartingPose(new Pose(22, 122, 143));
        buildPaths();
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }
    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    private Path StartScore;
    private PathChain GoPickup1, GrabPickup1, ScorePickup1, GoPickup2, GrabPickup2, ScorePickup2, GoPickup3, GrabPickup3, ScorePickup3, End;

    private void buildPaths() {
        StartScore = new Path(new BezierLine(new Pose(22, 122.000, -143), new Pose(46.000, 96.000,-143)));
        StartScore.setTangentHeadingInterpolation();

       GoPickup1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000, -143),
                                //new Pose(45.134, 70.028),
                                new Pose(44.000, 36.000, 92)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        GrabPickup1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 36.000, 92),

                                new Pose(32.000, 36.000, 180)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ScorePickup1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.000, 36.000, 180),
                                //new Pose(39.883, 74.413),
                                new Pose(46.000, 96.000, -79)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        GoPickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000, -143),
                                //new Pose(44.709, 83.216),
                                new Pose(46.000, 60.000, 87)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        GrabPickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 60.000, 87),

                                new Pose(33.000, 60.000, 180)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ScorePickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(33.000, 60.000, 180),
                                //new Pose(39.4990477077817, 77.99525264739836),
                                new Pose(46.000, 96.000, -70)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        GoPickup3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000, -143),

                                new Pose(44.000, 84.000, 99)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        GrabPickup3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.000, 84.000, 99),

                                new Pose(32.000, 84.000, 180)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ScorePickup3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.000, 84.000, 180),

                                new Pose(46.000, 96.000 , -41)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        End = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.000, 96.000, -143),

                                new Pose(60.000, 135.000, -70)
                        )
                ).setTangentHeadingInterpolation()

                .build();
    }
    private void autonomousPathUpdate() {
        switch (pathState) {
            case 0: {
                follower.followPath(StartScore, true);
                setPathState(1);
            }
            break;

            case 1:
                if (!follower.isBusy()) {
                    ShooterMotor.setPower(1);
                    IntakeMotor2.setPower(1);
                    double timeElapsed = pathTimer.getElapsedTimeSeconds();
                    if (timeElapsed > .3) {
                        IntakeMotor2.setPower(0);
                    }
                    if (timeElapsed > .8) {
                        IntakeMotor2.setPower(1);
                    }
                        setPathState(2);
                }
                break;

            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(GoPickup1);
                    ShooterMotor.setPower(-.2);
                    IntakeMotor2.setPower(0.035);
                    IntakeMotor1.setPower(1);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup1);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(ScorePickup1, true);
                    IntakeMotor1.setPower(0);
                    ShooterMotor.setPower(1);
                    IntakeMotor2.setPower(1);
                    double timeElapsed = pathTimer.getElapsedTimeSeconds();
                    if (timeElapsed > .3) {
                        IntakeMotor2.setPower(0);
                    }
                    if (timeElapsed > .8) {
                        IntakeMotor2.setPower(1);
                    }
                        setPathState(4);
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(GoPickup2);
                    ShooterMotor.setPower(-.2);
                    IntakeMotor2.setPower(0);
                    IntakeMotor1.setPower(1);
                    setPathState(5);
                }
                break;

            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup2);
                    setPathState(6);
                }
                break;

            case 7:
                if (!follower.isBusy()) {
                    follower.followPath(ScorePickup2, true);
                    IntakeMotor1.setPower(0);
                    ShooterMotor.setPower(1);
                    IntakeMotor2.setPower(1);
                    double timeElapsed = pathTimer.getElapsedTimeSeconds();
                    if (timeElapsed > .3) {
                        IntakeMotor2.setPower(0);
                    }
                    if (timeElapsed > .8) {
                        IntakeMotor2.setPower(1);
                    }
                        setPathState(7);
                }
                break;

            case 8:
                if (!follower.isBusy()) {
                    follower.followPath(GoPickup3);
                    IntakeMotor2.setPower(0);
                    ShooterMotor.setPower(-.2);
                    IntakeMotor1.setPower(1);
                    setPathState(8);
                }
                break;

            case 9:
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup3);
                    setPathState(9);
                }
                break;

            case 10:
                if (!follower.isBusy()) {
                    follower.followPath(ScorePickup3, true);
                    setPathState(11);
                }
                break;

            case 11:
                if ((!follower.isBusy())) {
                    IntakeMotor1.setPower(0);
                    ShooterMotor.setPower(1);
                    IntakeMotor2.setPower(1);
                    double timeElapsed = pathTimer.getElapsedTimeSeconds();
                    if (timeElapsed > .3) {
                        IntakeMotor2.setPower(0);
                    }
                    if (timeElapsed > .8) {
                        IntakeMotor2.setPower(1);
                    }
                        setPathState(12);
                }

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