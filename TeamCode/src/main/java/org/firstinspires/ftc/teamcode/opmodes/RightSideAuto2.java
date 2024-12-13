package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.Constants.Intake.*;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Constants;
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
@Autonomous(name = "four specimen auto", group = "Autonomous")
@Disabled
public class RightSideAuto2 extends OpMode {
    private final Pose2d initialPose = new Pose2d(9, -62, Math.toRadians(-90));
    private Action scoring, movePieces, pickup, score2, pickup2, score3, pickup3, score4, park, path2, path3, path4, piece2, path6;
    private MecanumDrive drive;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                //move to submersible
                .strafeToConstantHeading(new Vector2d(0, -47));
        TrajectoryActionBuilder tab1 = drive.actionBuilder(new Pose2d(0, -40, Math.toRadians(-90)))
                //move to pieces
                .splineToConstantHeading(new Vector2d(37, -33), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(37, -15), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(46, -10, Math.toRadians(180)), Math.toRadians(-90))
                //move piece 1
                .strafeToConstantHeading(new Vector2d(46, -55))
                .splineToConstantHeading(new Vector2d(46, -10), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(56, -10), Math.toRadians(0))
                //move piece 2
                .strafeToConstantHeading(new Vector2d(56, -55))
                .splineToConstantHeading(new Vector2d(56, -35), Math.toRadians(90));
                /*
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
                 */
        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(56, -35, Math.toRadians(180)))
                //move to pickup
                .splineToLinearHeading(new Pose2d(46, -35, Math.toRadians(-90)), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(46, -48.5), Math.toRadians(-90));
        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(46, -48.5, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(2, -52))
                .strafeToConstantHeading(new Vector2d(2, -49));
        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(2, -49, Math.toRadians(-90)))
                //move to pickup
                .strafeToConstantHeading(new Vector2d(46, -38))
                .setTangent(Math.toRadians(90))
                .lineToY(-48.5);
        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(46, -48.5, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(4, -52))
                .strafeToConstantHeading(new Vector2d(4, -49));
        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(4, -49, Math.toRadians(-90)))
                //move to pickup
                .strafeToConstantHeading(new Vector2d(46, -38))
                .setTangent(Math.toRadians(90))
                .lineToY(-48.5);
        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(46, -48.5, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(6, -52))
                .strafeToConstantHeading(new Vector2d(6, -49));
        TrajectoryActionBuilder tab8 = drive.actionBuilder(new Pose2d(6, -49, Math.toRadians(-90)))
                //park
                .strafeToConstantHeading(new Vector2d(46, -58));

        scoring = tab0.build();
        movePieces = tab1.build();
        pickup = tab2.build();
        score2 = tab3.build();
        pickup2 = tab4.build();
        score3 = tab5.build();
        pickup3 = tab6.build();
        score4 = tab7.build();
        park = tab8.build();
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
                                new MercurialAction(GroupedCommands.INSTANCE.setSpecimenBackwardsCommand())
                        ),
                        //scores 1st specimen and lets go
                        new ParallelAction(
                                new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.33)),
                                new SequentialAction(
                                        new SleepAction(0.4),
                                        new MercurialAction(Intake.INSTANCE.setClawOpen(true))
                                )
                        ),
                        new ParallelAction(
                                //moves samples to human player zone
                                movePieces,
                                //moves to default position
                                new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)),
                                new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.START))
                        ),
                        //move to human player zone
                        new ParallelAction(
                                pickup,
                                //prepare to grab specimen
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation180Pos))
                                )
                        ),
                        //grab specimen
                        new ParallelAction(
                            new SleepAction(0.3),
                            new MercurialAction(Intake.INSTANCE.setClawOpen(false))
                        ),
                        new ParallelAction(
                            new SleepAction(0.5),
                            new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING))
                        ),
                        new ParallelAction(
                                //move to submersible
                                score2,
                                //set to scoring pos
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(GroupedCommands.INSTANCE.setSpecimenBackwardsCommand())
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation0Pos))
                                )
                        ),
                        //scores 2nd specimen and lets go
                        new ParallelAction(
                                new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.33)),
                                new SequentialAction(
                                        new SleepAction(0.4),
                                        new MercurialAction(Intake.INSTANCE.setClawOpen(true))
                                ),
                                new SleepAction(0.9)
                        ),
                        new ParallelAction(
                                //move to human player zone
                                pickup2,
                                //prepare to grab specimen
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation180Pos))
                                )
                        ),
                        //grab specimen
                        new ParallelAction(
                                new SleepAction(0.3),
                                new MercurialAction(Intake.INSTANCE.setClawOpen(false))
                        ),
                        new ParallelAction(
                                new SleepAction(0.5),
                                new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING))
                        ),
                        new ParallelAction(
                                //move to submersible
                                score3,
                                //set to scoring pos
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(GroupedCommands.INSTANCE.setSpecimenBackwardsCommand())
                                ),
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation0Pos))
                                )
                        ),
                        //scores 3rd specimen and lets go
                        new ParallelAction(
                                new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.33)),
                                new SequentialAction(
                                        new SleepAction(0.4),
                                        new MercurialAction(Intake.INSTANCE.setClawOpen(true))
                                )
                        ),
                        new ParallelAction(
                                //move to human player zone
                                pickup3,
                                //prepare to grab specimen
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation180Pos))
                                )
                        ),
                        //grab specimen
                        new ParallelAction(
                                new SleepAction(0.3),
                                new MercurialAction(Intake.INSTANCE.setClawOpen(false))
                        ),
                        new ParallelAction(
                                new SleepAction(0.5),
                                new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING))
                        ),
                        new ParallelAction(
                                //move to submersible
                                score4,
                                //set to scoring pos
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(GroupedCommands.INSTANCE.setSpecimenBackwardsCommand())
                                ),
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation0Pos))
                                )
                        ),
                        //scores 4th specimen and lets go
                        new ParallelAction(
                                new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.33)),
                                new SequentialAction(
                                        new SleepAction(0.4),
                                        new MercurialAction(Intake.INSTANCE.setClawOpen(true))
                                )
                        )/*,
                        //park
                        new ParallelAction(
                                park,
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME))
                                ),
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.START))
                                )
                        )*/
                )
        );
    }

    @Override
    public void loop() {

    }

}
