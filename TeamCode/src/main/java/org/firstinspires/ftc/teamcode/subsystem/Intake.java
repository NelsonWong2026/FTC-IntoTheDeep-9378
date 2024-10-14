package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake {
    private DcMotor intake;

    public void init(HardwareMap hwMap) {
        intake = hwMap.get(DcMotor.class, Constants.Intake.intake);
    }

    /*public class IntakeSample implements Action {
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.setPower(0.6);
        }
    }*/
    public void setIntake(double power) {
        intake.setPower(power);
    }

    public void stopIntake() {
        intake.setPower(0);
    }

    public void setControl(Gamepad gamepad) {
        if (gamepad.left_bumper) {
            intake.setPower(0.7);
        }
        else if (gamepad.right_bumper) {
            intake.setPower(-1);
        }
        else if (gamepad.a) {
            stopIntake();
        }
    }
}
