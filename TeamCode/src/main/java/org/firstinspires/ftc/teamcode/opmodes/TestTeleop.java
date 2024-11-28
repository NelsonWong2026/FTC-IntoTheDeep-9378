package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HIGH_SCORING;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HOME;
import static org.firstinspires.ftc.teamcode.subsystems.Intake.IntakePivotState;

import static dev.frozenmilk.mercurial.Mercurial.gamepad2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.Slides.SlideState;

import dev.frozenmilk.mercurial.Mercurial;

@Mercurial.Attach
@Arm.Attach
@Drive.Attach
@Intake.Attach
@Slides.Attach
@TeleOp(name = "Test Teleop")
public class TestTeleop extends OpMode {
    @Override
    public void init() {
        Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.driveCommand(true));
        gamepad2().leftBumper()
                .onTrue(Arm.INSTANCE.setArmPosition(ArmState.HOME));
        gamepad2().rightBumper()
                .onTrue(Arm.INSTANCE.setArmPosition(ArmState.HIGH_SCORING));
        gamepad2().dpadUp()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.HOME));
        gamepad2().dpadRight()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.INTAKE));
        gamepad2().dpadLeft()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.SCORING));
        gamepad2().dpadDown()
                .onTrue(Intake.INSTANCE.setClawOpen(true));
        gamepad2().y()
                .onTrue(Intake.INSTANCE.setClawOpen(false));
        gamepad2().b()
                .onTrue(Slides.INSTANCE.setSlidePosition(SlideState.INTAKE));
        gamepad2().a()
                .onTrue(Slides.INSTANCE.setSlidePosition(SlideState.HOME));
        gamepad2().x()
                .onTrue(Slides.INSTANCE.setSlidePosition(SlideState.HIGH_SCORING));
    }

    @Override
    public void loop() {

    }
}
