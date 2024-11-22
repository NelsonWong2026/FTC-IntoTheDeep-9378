package org.firstinspires.ftc.teamcode.tuning;

import static dev.frozenmilk.dairy.pasteurized.Pasteurized.gamepad1;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

import dev.frozenmilk.dairy.core.util.supplier.logical.EnhancedBooleanSupplier;
import dev.frozenmilk.dairy.pasteurized.Pasteurized;
import dev.frozenmilk.mercurial.Mercurial;


@Mercurial.Attach
@Arm.Attach
@Config
@TeleOp(name="ArmPIDTuning", group = "Tuning")
public class ArmPIDTuning extends OpMode {
    //private EnhancedBooleanSupplier increase, decrease;
    public static final Arm ARM = new Arm();
    public static double position = 0;

    @Override
    public void init() {
        /*increase = Pasteurized.gamepad1().a();
        decrease = Pasteurized.gamepad1().b();*/
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        if (gamepad1().a().onTrue()) {
            position += 5;
        }
        if (gamepad1().b().onTrue()) {
            position -= 5;
        }

        ARM.runToPosition(position);
        telemetry.addData("Target: ", position);
        telemetry.addData("Position: ", ARM.getEncoder());
        telemetry.update();
    }
}