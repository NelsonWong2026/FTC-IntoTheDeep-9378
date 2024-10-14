package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.PIDF_Arm;

@TeleOp(name = "Manual Teleop Mode")
public class ManualTeleop extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive = new MecanumDrive();
    private Arm arm = new Arm();
    private Intake intake = new Intake();

    public void init() {
        drive.init(hardwareMap);
        arm.init(hardwareMap);
        intake.init(hardwareMap);
    }

    public void start() {
        runtime.reset();
    }

    public void loop() {
        drive.setControl(gamepad1, false);
        arm.setControl(gamepad2);
        intake.setControl(gamepad2);

        telemetry.addData("Status","Enabled");
        telemetry.addData("Time Remaining",Constants.Time.teleopTime-this.runtime.seconds());
        telemetry.addData("Upper Arm Pos", arm.getUpperPos());
        telemetry.addData("Lower Arm Pos", arm.getLowerPos());
        telemetry.update();
    }

    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

}
