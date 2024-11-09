package org.firstinspires.ftc.teamcode.auto;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
@Autonomous(name = "RightRed")
public class RightRedAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(35,-60, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Action trajectory;

        /*TrajectoryActionBuilder trajectory1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(35.0,-27.0, Math.toRadians(0)), Math.toRadians(90))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(-45)), Math.toRadians(50))
                //drop
                .splineToLinearHeading(new Pose2d(44, -27, Math.toRadians(0)), Math.toRadians(50))
                //PICKUP
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(-45)), Math.toRadians(50))
                //DROP
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(0)), Math.toRadians(50))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0,-27.0, Math.toRadians(0)), Math.toRadians(50))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(-45)), Math.toRadians(50));
                //drop*/
        TrajectoryActionBuilder trajectory1 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(180))
                .lineToX(45)
                .lineToX(35)
                .setTangent(Math.toRadians(90))
                .lineToYLinearHeading(-37, Math.toRadians(180))
                .lineToY(-13)
                .strafeToLinearHeading(new Vector2d(45, -13), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .lineToY(-13)
                .strafeToConstantHeading(new Vector2d(55, -13))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .lineToY(-13)
                .strafeToConstantHeading(new Vector2d(61, -13))
                .setTangent(Math.toRadians(90))
                .lineToY(-57);

        waitForStart();

        if (isStopRequested()) return;

        trajectory = trajectory1.build();

        Actions.runBlocking(
                new SequentialAction(
                    trajectory
                )
        );
    }
}
