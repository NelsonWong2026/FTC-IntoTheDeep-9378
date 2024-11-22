package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HIGH_SCORING;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HOME;
import static org.firstinspires.ftc.teamcode.subsystems.Intake.IntakePivotState;

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
        Drive.INSTANCE.setDefaultCommand(Drive.INSTANCE.robotCentricDriveCommand());
        Mercurial.gamepad2().leftBumper()
                .onTrue(new SetArmCommand(ArmState.HOME));
        Mercurial.gamepad2().rightBumper()
                .onTrue(new SetArmCommand(HIGH_SCORING));
        Mercurial.gamepad2().dpadUp()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.HOME));
        Mercurial.gamepad2().dpadRight()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.INTAKE));
        Mercurial.gamepad2().dpadLeft()
                .onTrue(Intake.INSTANCE.setIntakePivot(IntakePivotState.SCORING));
        Mercurial.gamepad2().b()
                .onTrue(new SetSlidesCommand(SlideState.INTAKE));
        Mercurial.gamepad2().a()
                .onTrue(new SetSlidesCommand(SlideState.HOME));
        Mercurial.gamepad2().x()
                .onTrue(new SetSlidesCommand(SlideState.HIGH_SCORING));
    }

    @Override
    public void loop() {

    }
}
