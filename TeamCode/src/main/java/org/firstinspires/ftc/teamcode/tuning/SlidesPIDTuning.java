package org.firstinspires.ftc.teamcode.tuning;

import static dev.frozenmilk.dairy.pasteurized.Pasteurized.gamepad1;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Slides;

import dev.frozenmilk.dairy.core.util.supplier.logical.EnhancedBooleanSupplier;
import dev.frozenmilk.dairy.pasteurized.Pasteurized;


@Slides.Attach
@TeleOp(name="SlidesPIDTuning", group = "Tuning")
public class SlidesPIDTuning extends OpMode {
    //private EnhancedBooleanSupplier increase, decrease;
    public static final Slides SLIDES = new Slides();
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

        SLIDES.runToPosition(position);
        telemetry.addData("Target: ", position);
        telemetry.addData("Position: ", SLIDES.getEncoder());
        telemetry.update();
    }
}