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

@Autonomous(name = "PedroAutoBlue", group = "Auto")
public class PedroAutoBlue extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    Path StartScore;
    PathChain GoPickup1, GrabPickup1, ScorePickup1, GoPickup2, GrabPickup2, ScorePickup2, GoPickup3, GrabPickup3, ScorePickup3, End;


    public void buildPaths() {
            StartScore = new Path(new BezierLine(new Pose(122.000, 122.000), new Pose(96.000, 96.000)));
            StartScore.setLinearHeadingInterpolation(Math.toRadians(43), Math.toRadians(43));

            GoPickup1 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(96.000, 96.000),
                                    new Pose(101.787, 67.060),
                                    new Pose(108.000, 36.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            GrabPickup1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(108.000, 36.000),

                                    new Pose(130.000, 36.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            ScorePickup1 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(130.000, 36.000),
                                    new Pose(80.000, 50.000),
                                    new Pose(96.000, 96.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            GoPickup2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(96.000, 96.000),
                                    new Pose(72.000, 55.000),
                                    new Pose(108.000, 60.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            GrabPickup2 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(108.000, 60.000),

                                    new Pose(120.000, 60.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            ScorePickup2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(120.000, 60.000),
                                    new Pose(109.000, 77.000),
                                    new Pose(96.000, 96.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            GoPickup3 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(96.000, 96.000),

                                    new Pose(96.000, 84.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            GrabPickup3 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(96.000, 84.000),

                                    new Pose(125.000, 84.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            ScorePickup3 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(125.000, 84.000),

                                    new Pose(96.000, 96.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            End = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(96.000, 96.000),

                                    new Pose(84.000, 130.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();
        }
    {
        Path StartScore = null;
        PathChain GoPickup1 = null, GrabPickup1 = null, ScorePickup1 = null, GoPickup2 = null, GrabPickup2 = null, ScorePickup2 = null, GoPickup3 = null, GrabPickup3 = null, ScorePickup3 = null, End = null;

        switch (pathState) {
            case 0: // Drive to Score Preload
                follower.followPath(StartScore, true);
                setPathState(1);
                break;

            case 1: // Wait until we arrive at Score Pose
                if(!follower.isBusy()) {
                    follower.followPath(GoPickup1);
                    setPathState(2);
                }
                break;
            case 2: //Going to scorepose
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup1);
                    setPathState(3);
                }
                break;
            case 3: // Scoring Pickup 1
                if(!follower.isBusy()) {
                    telemetry.addLine("It worked again!!!");
                    follower.followPath(ScorePickup1, true);
                    setPathState(4);
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(GoPickup2);
                    setPathState(5);
                }
                break;

            case 5: // ACTION: Grab pickup 2
                if (!follower.isBusy()) {
                    follower.followPath(GrabPickup2);
                    setPathState(6);
                }
                break;

            case 6: // Drive to Score 1
                if(!follower.isBusy()) {
                    follower.followPath(ScorePickup2, true);
                    setPathState(7);
                }
                break;

            case 7: // ACTION: Score Pickup 3
                if (!follower.isBusy()) {
                    follower.followPath(GoPickup3);
                    setPathState(8);
                }
                break;

            case 8:
                if(!follower.isBusy()) {
                    follower.followPath(GrabPickup3);
                    setPathState(9);
                }
                break;

            case 9:
                if(!follower.isBusy()) {
                    follower.followPath(ScorePickup3, true);
                    setPathState(10);
                }
                break;

            case 10:
                if(!follower.isBusy()) {
                    follower.followPath(End);
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
        //ShooterMotor = hardwareMap.get(DcMotorEx.class, "ShooterMotor");
        //IntakeMotor1 = hardwareMap.get(DcMotorEx.class, "IntakeMotor1");
        //IntakeMotor2 = hardwareMap.get(DcMotorEx.class, "IntakeMotor2");

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(122, 122));
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