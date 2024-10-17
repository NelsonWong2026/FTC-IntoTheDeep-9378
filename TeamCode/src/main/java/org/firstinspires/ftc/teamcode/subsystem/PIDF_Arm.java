package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class PIDF_Arm {
    private PIDController lowerController, upperController;

    public static double lowerP = 0.003, lowerI = 0, lowerD = 0, upperP = 0.002, upperI = 0.1, upperD = 0;
    public static double lowerF = 0, upperF = 0;

    public static int upperTarget = 0;
    public static int lowerTarget = 0;

    private final double ticks_in_degree = (5281.1/180.0);

    private DcMotorEx upperArm, lowerArm;

    public void init(HardwareMap hwMap) {
        upperController = new PIDController(upperP, upperI, upperD);
        lowerController = new PIDController(lowerP, lowerI, lowerD);

        upperArm = hwMap.get(DcMotorEx.class, Constants.Arm.upperArm);
        lowerArm = hwMap.get(DcMotorEx.class, Constants.Arm.lowerArm);
        upperArm.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public int getUpperPos() {
        return upperArm.getCurrentPosition();
    }

    public int getLowerPos() {
        return lowerArm.getCurrentPosition();
    }

    public void setControl(Gamepad gamepad) {
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

        if (gamepad.start && gamepad.y) {
            upperArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lowerArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            upperArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lowerArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else if (gamepad.dpad_down) {
            upperTarget = Constants.Arm.START[0];
            lowerTarget = Constants.Arm.START[1];
        }
        else if (gamepad.dpad_up) {
            upperTarget = Constants.Arm.INTAKE[0];
            lowerTarget = Constants.Arm.INTAKE[1];
        }
        else if (gamepad.x) {
            upperTarget = Constants.Arm.LOWER_BASKET[0];
            lowerTarget = Constants.Arm.LOWER_BASKET[1];
        }
        else if (gamepad.y) {
            upperTarget = Constants.Arm.UPPER_BASKET[0];
            lowerTarget = Constants.Arm.UPPER_BASKET[1];
        }
    }

    /*public void setManualControl(Gamepad gamepad) {
        if (gamepad.)
    }*/
}
