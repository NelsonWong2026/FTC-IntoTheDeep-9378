package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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
@Autonomous(name = "the 3 piece", group = "Autonomous")
public class LeftSideAuto extends OpMode {
    private final Pose2d initialPose = new Pose2d(-38, -61.5, Math.toRadians(90));
    private Action path0, scoring, path2, path3, path4, piece2, path6, park, score2;
    private MecanumDrive drive;

    @Override
    public void init() { drive = new MecanumDrive(hardwareMap, initialPose); }

    @Override
    public void init_loop(){
        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                .setTangent(180)
                .strafeToLinearHeading(new Vector2d(-52,-52), Math.toRadians(45));

        TrajectoryActionBuilder tab1 = drive.actionBuilder(new Pose2d(-52,-52, Math.toRadians(45)))
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45));

        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(-52.5,-52.5, Math.toRadians(90)))
                .strafeTo(new Vector2d(-56,-56));

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(-56,-56, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-58.5,-38), Math.toRadians(90));

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(-58.5,-38, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45));

        TrajectoryActionBuilder score = drive.actionBuilder(new Pose2d(-50,-38, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45));

        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(-58.5,-38, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-50,-36), Math.toRadians(95));

        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(-52.5,-52.5, Math.toRadians(45)))
                .strafeToLinearHeading(new Vector2d(-48.5,-38), Math.toRadians(90));

        TrajectoryActionBuilder end = drive.actionBuilder(new Pose2d(-56,-56, Math.toRadians(45)))
                .strafeToLinearHeading(new Vector2d(-50,-16), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-6,-12, Math.toRadians(0)), Math.toRadians(180));

        path0 = tab0.build();
        scoring = tab1.build();
        path2 = tab2.build();
        path3 = tab3.build();
        path4 = tab4.build();
        piece2 = tab5.build();
        score2 = score.build();
        path6 = tab6.build();
        park = end.build();
    }

    @Override
    public void start(){
        SilkRoad.RunAsync(
                new SequentialAction(
                        new MercurialAction(Intake.INSTANCE.setClawOpen(false)),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)),
                        path0,
                        new MercurialAction(Arm.INSTANCE.setArmPosition(Arm.ArmState.HIGH_SCORING)),
                        new SleepAction(2),
                        new MercurialAction(Slides.INSTANCE.setSlidePosition(Slides.SlideState.HIGH_SCORING)),
                        new SleepAction(0.5),
                        new MercurialAction(Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)),
                        scoring,
                        new SleepAction(0.5),
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
                        new SleepAction(2),
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
                        new SleepAction(2),
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

//                    path0,path1,path2,path3,path4,path5
                )
        );
    }

    @Override
    public void loop() {
    }
}



