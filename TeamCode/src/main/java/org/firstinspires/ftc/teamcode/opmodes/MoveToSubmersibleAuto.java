package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.GroupedCommands;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.util.MercurialAction;
import org.firstinspires.ftc.teamcode.util.SilkRoad;

import java.security.acl.Group;

import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.commands.util.Wait;

@Arm.Attach
@Drive.Attach
@Intake.Attach
@Slides.Attach
@SilkRoad.Attach
@Mercurial.Attach
@Config
@Autonomous(name = "moveToSubmersible", group = "Autonomous")
public class MoveToSubmersibleAuto extends OpMode {
    private final Pose2d initialPose = new Pose2d(9, -62, Math.toRadians(-90));
    private Action scoring, movePieces, pickup, score2, pickup2, park, path2, path3, path4, piece2, path6;
    private MecanumDrive drive;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, initialPose);
        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                //move to submersible
                .strafeToConstantHeading(new Vector2d(0, -48.5));
        TrajectoryActionBuilder tab1 = drive.actionBuilder(new Pose2d(0, -40, Math.toRadians(-90)))
                //move to pieces
                .splineToConstantHeading(new Vector2d(35, -33), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(35, -15), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(45, -10, Math.toRadians(0)), Math.toRadians(-90))
                //move piece 1
                .splineToConstantHeading(new Vector2d(45, -54), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(45, -10), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(55, -10), Math.toRadians(-90))
                //move piece 2
                .splineToConstantHeading(new Vector2d(55, -54), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(55, -10), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(60, -10), Math.toRadians(-90))
                //move piece 3
                .splineToConstantHeading(new Vector2d(60, -56), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(60, -48), Math.toRadians(90));
        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(60, -48, Math.toRadians(0)))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -50), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-58);
        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(46, -50, Math.toRadians(-90)))
                //move to submersible
                .strafeToLinearHeading(new Vector2d(4, -40), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-40);
        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(4, -40, Math.toRadians(-90)))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -50), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-58);
        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(46, -50, Math.toRadians(-90)))
                //park
                .strafeToConstantHeading(new Vector2d(46, -58));

        scoring = tab0.build();
        movePieces = tab1.build();
        pickup = tab2.build();
        score2 = tab3.build();
        pickup2 = tab4.build();
        park = tab5.build();
    }

    @Override
    public void start() {
        SilkRoad.RunAsync(
                new SequentialAction(
                        new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                        new ParallelAction(
                                //move to submersible
                                scoring
                                //set to scoring pos
                                //new MercurialAction(GroupedCommands.INSTANCE.setSpecimenBackwardsCommand())
                        )
                )
        );
    }

    @Override
    public void loop() {

    }

}
