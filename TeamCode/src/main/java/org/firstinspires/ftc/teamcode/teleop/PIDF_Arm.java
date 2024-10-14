package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

@Config
@TeleOp
public class PIDF_Arm extends OpMode {
    private PIDController lowerController, upperController;

    public static double lowerP = 0.003, lowerI = 0, lowerD = 0, upperP = 0.002, upperI = 0.1, upperD = 0;
    public static double lowerF = 0, upperF = 0;

    public static int upperTarget = 0;
    public static int lowerTarget = 0;

    private final double ticks_in_degree = (5281.1/180.0);

    private DcMotorEx upperArm, lowerArm;

    @Override
    public void init() {
        upperController = new PIDController(upperP, upperI, upperD);
        lowerController = new PIDController(lowerP, lowerI, lowerD);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        upperArm = hardwareMap.get(DcMotorEx.class, Constants.Arm.upperArm);
        lowerArm = hardwareMap.get(DcMotorEx.class, Constants.Arm.lowerArm);
        upperArm.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        upperController.setPID(upperP, upperI, upperD);
        lowerController.setPID(lowerP, lowerI, lowerD);
        int upperArmPos = upperArm.getCurrentPosition();
        int lowerArmPos = lowerArm.getCurrentPosition();
        double upperPid = upperController.calculate(upperArmPos, upperTarget);
        double upperff = Math.cos(Math.toRadians(upperTarget / ticks_in_degree)) * upperF;
        double lowerPid = lowerController.calculate(lowerArmPos, lowerTarget);
        double lowerff = Math.cos(Math.toRadians(lowerTarget / ticks_in_degree)) * lowerF;

        double upperPower = upperPid + upperff;
        double lowerPower = lowerPid + lowerff;

        upperArm.setPower(upperPower);
        lowerArm.setPower(lowerPower);

        telemetry.addData("upper pos", upperArmPos);
        telemetry.addData("lower pos", lowerArmPos);
        telemetry.addData("upper target", upperTarget);
        telemetry.addData("lower target", lowerTarget);
        telemetry.update();

        if (gamepad1.start) {
            upperArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lowerArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            upperArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lowerArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

}
