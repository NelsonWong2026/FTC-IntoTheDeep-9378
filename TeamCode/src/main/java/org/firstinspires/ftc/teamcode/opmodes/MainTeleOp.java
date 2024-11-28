package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
        Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.driveCommand(true));
        Mercurial.gamepad2().leftBumper()
                .onTrue(Arm.INSTANCE.setArmPosition(ArmState.SPECIMEN_SCORING));
        Mercurial.gamepad2().rightBumper()
                .onTrue(
                        new Parallel(
                                Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE),
                                new Sequential(
                                        new Wait(2),
                                        Intake.INSTANCE.setClawOpen(true)
                                ),
                                new Sequential(
                                        new Wait(2.5),
                                        Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
                                ),
                                new Sequential(
                                        new Wait(2.5),
                                        Intake.INSTANCE.setIntakePivot(IntakePivotState.HOME)
                                )
                        )
                );
        Mercurial.gamepad2().a()
                .onTrue(
                    new Parallel(
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME),
                        Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)
                    )
                );
        Mercurial.gamepad2().b()
                .onTrue(
                    new Parallel(
                            Arm.INSTANCE.setArmPosition(Arm.ArmState.HIGH_SCORING),
                            new Sequential(
                                    new Wait(0.7),
                                    Slides.INSTANCE.setSlidePosition(Slides.SlideState.HIGH_SCORING)
                            ),
                            new Sequential(
                                    new Wait(1.5),
                                    Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)
                            )
                    )
                );
        Mercurial.gamepad2().x()
                .onTrue(
                    new Parallel(
                        Slides.INSTANCE.setSlidePosition(Slides.SlideState.INTAKE),
                        new Sequential(
                                new Wait(0.5),
                                Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)
                        )
                    )
                );
        Mercurial.gamepad2().y()
                .onTrue(
                    new Parallel(
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME),
                        new Sequential(
                                new Wait(0.3),
                                Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)
                        ),
                        new Sequential(
                                new Wait(1),
                                Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
                        )
                    )
                );
        Mercurial.gamepad2().dpadUp()
                .onTrue(Intake.INSTANCE.setClawOpenAndClose());
        Mercurial.gamepad2().dpadDown()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.HOME));
        Mercurial.gamepad2().dpadLeft()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.INTAKE));
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
            Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.slowDriveCommand(true));
        }
        if (Mercurial.gamepad1().rightBumper().onFalse()) {
            Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.driveCommand(true));
        }
    }
}
