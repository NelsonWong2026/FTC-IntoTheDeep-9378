package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants;

public class MecanumDrive {
    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private IMU imu;
    public MecanumDrive() {
    }

    public void init(HardwareMap hwMap) {
        leftFront = hwMap.get(DcMotor.class, Constants.MecanumDrive.leftFront);
        leftBack = hwMap.get(DcMotor.class, Constants.MecanumDrive.leftBack);
        rightFront = hwMap.get(DcMotor.class,Constants.MecanumDrive.rightFront);
        rightBack = hwMap.get(DcMotor.class,Constants.MecanumDrive.rightBack);
        imu = hwMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(parameters);

        this.leftFront.setDirection(DcMotor.Direction.REVERSE);
        this.leftBack.setDirection(DcMotor.Direction.REVERSE);
        this.rightFront.setDirection(DcMotor.Direction.FORWARD);
        this.rightBack.setDirection(DcMotor.Direction.FORWARD);

        this.leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public void setNormal(double leftPower, double rightPower) {
        this.leftFront.setPower(leftPower);
        this.leftBack.setPower(leftPower);
        this.rightFront.setPower(rightPower);
        this.rightBack.setPower(rightPower);
    }

    public void stop() {
        this.setNormal(0,0);
    }

    public void setControl(Gamepad gamepad, boolean fieldCentric) {

        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x * 1.1;
        double rx = gamepad.right_stick_x;

        if (gamepad.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX *= 1.1;


        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        if (fieldCentric) {
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            leftFront.setPower(frontLeftPower);
            leftBack.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack.setPower(backRightPower);
        }

        else {
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x -rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            leftFront.setPower(frontLeftPower);
            leftBack.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack.setPower(backRightPower);
        }

        /*double leftDrive = -gamepad.left_stick_y;
        double rightDrive = -gamepad.right_stick_y;

        leftFront.setPower(leftDrive);
        leftBack.setPower(leftDrive);
        rightFront.setPower(rightDrive);
        rightBack.setPower(rightDrive);

        if (gamepad.left_bumper) {
            strafeLeft(1);
        }
        else if (gamepad.right_bumper) {
            strafeRight(1);
        }*/

    }


}
