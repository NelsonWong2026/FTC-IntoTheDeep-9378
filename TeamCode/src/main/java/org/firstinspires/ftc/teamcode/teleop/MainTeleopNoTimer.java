package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.PIDF_Arm;

@TeleOp(name = "Main Teleop No Timer Mode")
public class MainTeleopNoTimer extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive = new MecanumDrive();
    private PIDF_Arm pidArm = new PIDF_Arm();
    private Intake intake = new Intake();

    public void init() {
        drive.init(hardwareMap);
        pidArm.init(hardwareMap);
        intake.init(hardwareMap);
    }

    public void start() {
        runtime.reset();
    }

    public void loop() {
        drive.setControl(gamepad1);
        pidArm.setControl(gamepad2);
        intake.setControl(gamepad2);

        telemetry.addData("Status","Enabled");
        telemetry.addData("Time Remaining",Constants.Time.teleopTime-this.runtime.seconds());
        telemetry.update();
    }

    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

}
