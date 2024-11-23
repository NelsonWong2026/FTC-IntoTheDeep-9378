package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HIGH_SCORING;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HOME;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.Intake.IntakePivotState;
import org.firstinspires.ftc.teamcode.subsystems.Slides.SlideState;

import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.commands.util.Wait;

@Mercurial.Attach
@Arm.Attach
@Drive.Attach
@Intake.Attach
@Slides.Attach
@TeleOp(name = "Main TeleOp")
public class MainTeleOp extends OpMode {
    @Override
    public void init() {
        Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.robotCentricDriveCommand());
        Mercurial.gamepad2().leftBumper()
                .onTrue(Arm.INSTANCE.setArmPosition(ArmState.HOME));
        Mercurial.gamepad2().rightBumper()
                .onTrue(Arm.INSTANCE.setArmPosition(ArmState.HIGH_SCORING));
        Mercurial.gamepad2().a()
                .onTrue(
                        new Parallel(
                                Slides.INSTANCE.setSlidePosition(SlideState.HOME),
                                Intake.INSTANCE.setIntakePivot(IntakePivotState.HOME)
                        )
                );
        Mercurial.gamepad2().b()
                .onTrue(Slides.INSTANCE.setSlidePosition(SlideState.HIGH_SCORING));
        Mercurial.gamepad2().x()
                .onTrue(Slides.INSTANCE.setSlidePosition(SlideState.INTAKE));
        Mercurial.gamepad2().y()
                .onTrue(
                        new Parallel(
                            Intake.INSTANCE.setIntakePivot(IntakePivotState.INTAKE),
                            new Sequential(
                                    new Wait(0.5),
                                    Slides.INSTANCE.setSlidePosition(SlideState.HOME)
                                ),
                            new Sequential(
                                    new Wait(0.5),
                                    Arm.INSTANCE.setArmPosition(ArmState.HOME)
                            )
                        )

                );
        Mercurial.gamepad2().dpadUp()
                .onTrue(Intake.INSTANCE.setClawOpenAndClose());
        Mercurial.gamepad2().dpadDown()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.INTAKE));
        Mercurial.gamepad2().dpadLeft()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.HOME));
        Mercurial.gamepad2().dpadRight()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.SCORING));
        Mercurial.gamepad2().leftStickButton()
                .onTrue(Intake.INSTANCE.setIntakeRotation(Constants.Intake.rotation0Pos));
        Mercurial.gamepad2().rightStickButton()
                .onTrue(Intake.INSTANCE.setIntakeRotation(Constants.Intake.rotation90Pos));

    }

    @Override
    public void loop() {
        if (Mercurial.gamepad1().rightBumper().onTrue()) {
            Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.slowRobotCentricDriveCommand());
        }
        if (Mercurial.gamepad1().rightBumper().onFalse()) {
            Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.robotCentricDriveCommand());
        }
    }
}
