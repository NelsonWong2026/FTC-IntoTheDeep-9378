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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@Autonomous(name = "specimen auto", group = "Autonomous")
@Disabled
public class RightSideAuto extends OpMode {
    private final Pose2d initialPose = new Pose2d(9, -62, Math.toRadians(-90));
    private Action scoring, pickup, score2, park, path2, path3, path4, piece2, path6;
    private MecanumDrive drive;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, initialPose);
        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(0, -32));
        TrajectoryActionBuilder tab1 = drive.actionBuilder(new Pose2d(0, -32, Math.toRadians(90)))
                .lineToY(-40)
                .strafeToLinearHeading(new Vector2d(46, -50), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-53);
        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(46, -50, Math.toRadians(-90)))
                .strafeToLinearHeading(new Vector2d(4, -40), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(-32);
        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(4, -32, Math.toRadians(90)))
                .lineToY(-40)
                .strafeToConstantHeading(new Vector2d(46, -58));

        scoring = tab0.build();
        pickup = tab1.build();
        score2 = tab2.build();
        park = tab3.build();
    }

    @Override
    public void start() {
        SilkRoad.RunAsync(
                new SequentialAction(
                    new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                    new ParallelAction(
                            //move to submersible
                            scoring,
                            //set to scoring pos
                            new MercurialAction(GroupedCommands.INSTANCE.setSpecimenCommand())
                    ),
                    //scores
                    new ParallelAction(
                            new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                            new SequentialAction(
                                    new SleepAction(1),
                                    new MercurialAction(Intake.INSTANCE.setClawOpen(true))
                            )
                    ),
                    //move to human player zone
                    new ParallelAction(
                            pickup,
                            new SequentialAction(
                                    new SleepAction(1),
                                    new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME))
                            ),
                            new SequentialAction(
                                    new SleepAction(1),
                                    new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME))
                            )
                    ),
                    //grab specimen
                    new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                    new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)),
                    new ParallelAction(
                            //move to submersible
                            score2,
                            //set to scoring pos
                            new SequentialAction(
                                    new SleepAction(0.5),
                                    new MercurialAction(GroupedCommands.INSTANCE.setSpecimenCommand())
                            )
                    ),
                    //scores
                    new ParallelAction(
                            new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                            new SequentialAction(
                                    new SleepAction(1),
                                    new MercurialAction(Intake.INSTANCE.setClawOpen(true))
                            )
                    ),
                    //park
                    new ParallelAction(
                            park,
                            new SequentialAction(
                                    new SleepAction(1),
                                    new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME))
                            ),
                            new SequentialAction(
                                    new SleepAction(1),
                                    new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME))
                            )
                    )
                )
        );
    }

    @Override
    public void loop() {

    }

}
