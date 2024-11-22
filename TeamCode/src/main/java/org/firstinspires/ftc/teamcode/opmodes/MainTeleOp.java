package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HIGH_SCORING;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HOME;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.subsystems.*;

import dev.frozenmilk.mercurial.Mercurial;

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
        Mercurial.gamepad2().a()
                .onTrue(new SetArmCommand(HOME));
        Mercurial.gamepad2().b()
                .onTrue(new SetArmCommand(HIGH_SCORING));
        Mercurial.gamepad2().x()
                .onTrue(new RetractIntakeCommand());
        Mercurial.gamepad2().y()
                .onTrue(new ExtendIntakeCommand());
    }

    @Override
    public void loop() {

    }
}
