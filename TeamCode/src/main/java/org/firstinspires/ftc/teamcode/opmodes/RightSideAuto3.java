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
@Autonomous(name = "four specimen auto #2", group = "Autonomous")
public class RightSideAuto3 extends OpMode {
    private final Pose2d initialPose = new Pose2d(9, -62, Math.toRadians(-90));
    private Action scoring, moveToPieces, movePiece1, moveToPiece2, movePiece2, pickup, score2, pickup2, score3, pickup3, score4, park, path2, path3, path4, piece2, path6;
    private MecanumDrive drive;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                //move to submersible
                .strafeToConstantHeading(new Vector2d(4, -47));
        TrajectoryActionBuilder tab1 = drive.actionBuilder(new Pose2d(4, -47, Math.toRadians(-90)))
                //move to piece 1
                .splineToLinearHeading(new Pose2d(37, -36, Math.toRadians(45)), Math.toRadians(90));
        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(37, -36, Math.toRadians(45)))
                //move piece 1
                .strafeToLinearHeading(new Vector2d(38, -49), Math.toRadians(-45));
        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(38, -49, Math.toRadians(-45)))
                //move to piece 2
                .strafeToLinearHeading(new Vector2d(47, -32), Math.toRadians(45));
        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(47, -32, Math.toRadians(45)))
                //move piece 2
                .strafeToLinearHeading(new Vector2d(46, -48), Math.toRadians(-45));
        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(46, -48, Math.toRadians(-45)))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -35), Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(46, -48));
        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(46, -48, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(6, -52))
                .strafeToConstantHeading(new Vector2d(6, -47));
        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(6, -47, Math.toRadians(-90)))
                //move to pickup
                .strafeToConstantHeading(new Vector2d(46, -39))
                .setTangent(Math.toRadians(90))
                .lineToY(-48);
        TrajectoryActionBuilder tab8 = drive.actionBuilder(new Pose2d(46, -48, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(8, -52))
                .strafeToConstantHeading(new Vector2d(8, -47));
        TrajectoryActionBuilder tab9 = drive.actionBuilder(new Pose2d(8, -48, Math.toRadians(-90)))
                //move to pickup
                .strafeToConstantHeading(new Vector2d(46, -39))
                .setTangent(Math.toRadians(90))
                .lineToY(-48);
        TrajectoryActionBuilder tab10 = drive.actionBuilder(new Pose2d(46, -48, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(10, -52))
                .strafeToConstantHeading(new Vector2d(10, -47));

        scoring = tab0.build();
        moveToPieces = tab1.build();
        movePiece1 = tab2.build();
        moveToPiece2 = tab3.build();
        movePiece2 = tab4.build();
        pickup = tab5.build();
        score2 = tab6.build();
        pickup2 = tab7.build();
        score3 = tab8.build();
        pickup3 = tab9.build();
        score4 = tab10.build();
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
                                //moves to first sample
                                moveToPieces,
                                //moves to position to grab sample
                                new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)),
                                new MercurialAction(Intake.INSTANCE.setIntakeRotation(0.1)),
                                new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE))

                        ),
                        new ParallelAction(
                                //grabs sample 1,
                                new MercurialAction(Intake.INSTANCE.setClawPos(0)),
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME))
                                ),
                                //move to human player station
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        movePiece1
                                )
                        ),
                        new ParallelAction(
                                //gives sample to human player
                                new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                                //moves to sample 2
                                new SequentialAction(
                                        new SleepAction(0.2),
                                        moveToPiece2
                                ),
                                new SequentialAction(
                                        new SleepAction(0.2),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE))
                                )
                        ),
                        new ParallelAction(
                                //grabs sample 2
                                new MercurialAction(Intake.INSTANCE.setClawPos(0)),
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.7))
                                ),
                                //move to human player station
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        movePiece2
                                )

                        ),
                        //gives sample 2 to human player
                        new ParallelAction(
                                new SleepAction(0.15),
                                new MercurialAction(Intake.INSTANCE.setClawOpen(true))
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
                                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.7))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation180Pos))
                                )
                        ),
                        //grabs 2nd specimen
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
                                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.7))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation180Pos))
                                )
                        ),
                        //grabs 3rd specimen
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
                                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.7))
                                ),
                                new SequentialAction(
                                        new SleepAction(0),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(rotation180Pos))
                                )
                        ),
                        //grabs 4th specimen
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
