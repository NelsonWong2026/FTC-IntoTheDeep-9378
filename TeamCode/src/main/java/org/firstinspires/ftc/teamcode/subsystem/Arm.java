package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Arm {
    private DcMotor upperArm, lowerArm;

    public void init(HardwareMap hwMap) {
        upperArm = hwMap.get(DcMotor.class, Constants.Arm.upperArm);
        lowerArm = hwMap.get(DcMotor.class, Constants.Arm.lowerArm);
        upperArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lowerArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upperArm.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setUpperArm(double power) {
        upperArm.setPower(power);
    }

    public void setLowerArm(double power) {
        lowerArm.setPower(power);
    }
    public void stopArm() {
        upperArm.setPower(0);
        lowerArm.setPower(0);
    }

    public int getUpperPos() {
        return upperArm.getCurrentPosition();
    }

    public int getLowerPos() {
        return lowerArm.getCurrentPosition();
    }

    public void setControl(Gamepad gamepad) {
        /*if (gamepad.x) {
            setUpperArm(-0.7);
        }
        else if (gamepad.y) {
            setUpperArm(0.7);
        }
        else if (gamepad.a) {
            setLowerArm(-0.7);
        }
        else if (gamepad.b) {
            setLowerArm(0.7);
        }
        else {
            stopArm();
        }*/
        double leftStick = -gamepad.left_stick_y;
        double rightStick = -gamepad.right_stick_y;
        setUpperArm(leftStick);
        setLowerArm(rightStick);
    }

}
