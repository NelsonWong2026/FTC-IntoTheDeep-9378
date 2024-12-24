package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.GroupedCommands;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.util.MercurialAction;
import org.firstinspires.ftc.teamcode.util.SilkRoad;

import dev.frozenmilk.mercurial.Mercurial;


@Arm.Attach
@Drive.Attach
@Intake.Attach
@Slides.Attach
@SilkRoad.Attach
@Mercurial.Attach
@Config
@Autonomous(name = "4 piece sample", group = "Autonomous")
public class LeftSideAuto2 extends OpMode {
    private final Pose2d initialPose = new Pose2d(-38, -61.5, Math.toRadians(90));
    private Action moveToBasket, score, moveToMiddle, score2, moveToRight, score3, moveToLeft, score4, park;
    private MecanumDrive drive;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, initialPose);
        //move to basket
        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-49,-52), Math.toRadians(45));
        TrajectoryActionBuilder tab1 = drive.actionBuilder(new Pose2d(-49,-52, Math.toRadians(45)))
                .strafeToLinearHeading(new Vector2d(-57,-57), Math.toRadians(45));
        //move to middle sample
        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(-57,-57, Math.toRadians(45)))
                .strafeToLinearHeading(new Vector2d(-60,-38), Math.toRadians(90));
        //move to basket
        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(-60,-38, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-57,-57), Math.toRadians(45));
        //move to right sample
        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(-57,-57, Math.toRadians(45)))
                .strafeToLinearHeading(new Vector2d(-49.5,-38), Math.toRadians(90));
        //move to basket
        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(-49.5,-38, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-57,-57), Math.toRadians(45));
        //move to left sample
        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(-57,-57, Math.toRadians(45)))
                .strafeToLinearHeading(new Vector2d(-56,-36), Math.toRadians(140));
        //move to basket
        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(-56,-36, Math.toRadians(140)))
                .strafeToLinearHeading(new Vector2d(-57,-57), Math.toRadians(45));
        //move to park
        TrajectoryActionBuilder tab8 = drive.actionBuilder(new Pose2d(-57,-57, Math.toRadians(45)))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-25, -2, Math.toRadians(0)), Math.toRadians(0));

        moveToBasket = tab0.build();
        score = tab1.build();
        moveToMiddle = tab2.build();
        score2 = tab3.build();
        moveToRight = tab4.build();
        score3 = tab5.build();
        moveToLeft = tab6.build();
        score4 = tab7.build();
        park = tab8.build();
    }

    @Override
    public void start(){
        SilkRoad.RunAsync(
                new SequentialAction(
                        //grip sample
                        new MercurialAction(Intake.INSTANCE.setClawPos(0)),
                        //move to basket
                        new ParallelAction(
                            moveToBasket,
                            new ParallelAction(
                                new SleepAction(2.2),
                                new MercurialAction(GroupedCommands.INSTANCE.autoSetScoringCommand())
                            )
                        ),
                        //score sample
                        score,
                        new SleepAction(0.6),
                        new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                        new SleepAction(0.7),
                        //move to middle sample
                        new ParallelAction(
                                new MercurialAction(GroupedCommands.INSTANCE.scoringToHomeCommand()),
                                new SequentialAction(
                                        new SleepAction(0.1),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(1))
                                ),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        moveToMiddle
                                ),
                                new SleepAction(2.2)
                        ),
                        new MercurialAction(Slides.INSTANCE.runToPosition(180)),
                        new SleepAction(0.5),
                        //grab sample
                        new MercurialAction(Intake.INSTANCE.setClawPos(0)),
                        new SleepAction(0.3),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME)),
                        //move to basket
                        new ParallelAction(
                                score2,
                                new ParallelAction(
                                        new SleepAction(2.2),
                                        new MercurialAction(GroupedCommands.INSTANCE.autoSetScoringCommand())
                                )
                        ),
                        new SleepAction(0.6),
                        //score 2nd sample
                        new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                        new SleepAction(0.7),
                        //move to right sample
                        new ParallelAction(
                                new MercurialAction(GroupedCommands.INSTANCE.scoringToHomeCommand()),
                                new SequentialAction(
                                        new SleepAction(0.1),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(1))
                                ),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        moveToRight
                                ),
                                new SleepAction(2.2)
                        ),
                        new MercurialAction(Slides.INSTANCE.runToPosition(180)),
                        new SleepAction(0.5),
                        //grab sample
                        new MercurialAction(Intake.INSTANCE.setClawPos(0)),
                        new SleepAction(0.3),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME)),
                        //move to basket
                        new ParallelAction(
                                score3,
                                new ParallelAction(
                                        new SleepAction(2.2),
                                        new MercurialAction(GroupedCommands.INSTANCE.autoSetScoringCommand())
                                )
                        ),
                        new SleepAction(0.6),
                        //score 3rd sample
                        new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                        new SleepAction(0.7),
                        //move to left sample
                        new ParallelAction(
                                new MercurialAction(GroupedCommands.INSTANCE.scoringToHomeCommand()),
                                new SequentialAction(
                                        new SleepAction(0.1),
                                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(1))
                                ),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        new MercurialAction(Intake.INSTANCE.setIntakeRotation(0.4)),
                                        moveToLeft
                                ),
                                new SleepAction(2.2)
                        ),
                        new MercurialAction(Arm.INSTANCE.runToPosition(600)),
                        new SleepAction(0.3),
                        new MercurialAction(Slides.INSTANCE.runToPosition(600)),
                        new SleepAction(0.3),
                        new MercurialAction(Arm.INSTANCE.runToPosition(0)),
                        new SleepAction(0.3),
                        //grab sample
                        new MercurialAction(Intake.INSTANCE.setClawPos(0)),
                        new SleepAction(0.3),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME)),
                        //move to basket
                        new ParallelAction(
                                score4,
                                new MercurialAction(Intake.INSTANCE.setIntakeRotation(0)),
                                new ParallelAction(
                                        new SleepAction(2.2),
                                        new MercurialAction(GroupedCommands.INSTANCE.autoSetScoringCommand())
                                )
                        ),
                        new SleepAction(0.6),
                        //score 4th sample
                        new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                        new SleepAction(0.7),
                        //park
                        new ParallelAction(
                                new MercurialAction(GroupedCommands.INSTANCE.scoringToHomeCommand()),
                                new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                                new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME)),
                                new SequentialAction(
                                        new SleepAction(0.5),
                                        park
                                )
                        ),
                        new MercurialAction(Intake.INSTANCE.setIntakePivotPosition(0.25))
                        /*

                        moveToMiddle,
                        score2,
                        moveToRight,
                        score3,
                        moveToLeft,
                        score4,
                        park

                         */
                        /*
                        new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                        path0,
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HIGH_SCORING)),
                        new SleepAction(2),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HIGH_SCORING)),
                        new SleepAction(0.5),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)),
                        scoring,
                        new SleepAction(1),
                        new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                        new SleepAction(0.5),
                        path2,
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                        new SleepAction(0.5),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)),
                        new SleepAction(2),
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)),
                        path3,
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.MID_SCORING)),
                        new SleepAction(0.5),
//                    new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.MID_SCORING)),
//                    new SleepAction(1.5),
                        new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                        new SleepAction(0.5),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)),
                        new SleepAction(1),
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)),
                        //go home
                        new SleepAction(0.2),
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HIGH_SCORING)),
                        new SleepAction(1),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HIGH_SCORING)),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)),
                        path4,
                        new SleepAction(1),
                        new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                        new SleepAction(0.5),


                        // 3RD PIECE
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                        new SleepAction(0.5),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)),
                        new SleepAction(2),
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)),
                        piece2,
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.MID_SCORING)),
                        new SleepAction(0.5),
                        new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                        new SleepAction(0.5),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)),
                        new SleepAction(1),
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)),
                        //go home
                        score2,
                        new SleepAction(0.2),
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HIGH_SCORING)),
                        new SleepAction(1),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HIGH_SCORING)),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)),

                        new SleepAction(1),
                        new MercurialAction(Intake.INSTANCE.setClawOpen(true)),
                        new SleepAction(0.5),
                        path6,
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                        new SleepAction(0.5),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)),
                        new SleepAction(2),
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)),
                        park

//                    path0,path1,path2,path3,path4,path5\

                         */
                )
        );
    }

    @Override
    public void loop() {
    }
}


