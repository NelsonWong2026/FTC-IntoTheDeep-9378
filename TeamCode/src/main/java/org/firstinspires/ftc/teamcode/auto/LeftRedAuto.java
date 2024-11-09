package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
@Config
@Autonomous(name = "Left Red Auto", group = "Autonomous")
public class LeftRedAuto extends LinearOpMode {
        public class Lift {
            private DcMotorEx lift;

            public Lift(HardwareMap hardwareMap) {
                lift = hardwareMap.get(DcMotorEx.class, "upperArm");
                lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                lift.setDirection(DcMotorSimple.Direction.REVERSE);
            }

            public class LiftUp implements Action {
                private boolean initialized = false;

                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    if (!initialized) {
                        lift.setPower(0.8);
                        initialized = true;
                    }

                    double pos = lift.getCurrentPosition();
                    packet.put("liftPos", pos);
                    if (pos < 7200.0) {
                        return true;
                    } else {
                        lift.setPower(0);
                        return false;
                    }
                }
            }

            public Action liftUp() {
                return new LiftUp();
            }

            public class LiftDown implements Action {
                private boolean initialized = false;


                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    if (!initialized) {
                        lift.setPower(-0.8);
                        initialized = true;
                    }


                    double pos = lift.getCurrentPosition();
                    packet.put("liftPos", pos);
                    if (pos > 0.0) {
                        return true;
                    } else {
                        lift.setPower(0);
                        return false;
                    }
                }
            }


            public Action liftDown() {
                return new LiftDown();
            }


        }

        public class Intake {
            private DcMotorEx intake;

            public Intake(HardwareMap hardwareMap) {
                intake = hardwareMap.get(DcMotorEx.class, "intake");
                intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                intake.setDirection(DcMotorSimple.Direction.FORWARD);
            }

            public class IntakeIn implements Action {
                private boolean initialized = false;

                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    if (!initialized) {
                        intake.setPower(-0.8);
                        initialized = true;
                        sleep(100);
                        intake.setPower(0);
                        return true;
                    }
                    return false;
                }
            }

            public Action intakeIn() {
                return new Intake.IntakeIn();
            }
        }

        @Override
        public void runOpMode() {
            Pose2d initialPose = new Pose2d(-34,-63, Math.toRadians(180));
            MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


            Lift lift = new Lift(hardwareMap);
            Intake intake = new Intake(hardwareMap);
            Action trajectory;
            Action trajectorytwo;
            Action traj3;




            TrajectoryActionBuilder trajectory1 = drive.actionBuilder(initialPose)
                    .setTangent(90)
                    .strafeToConstantHeading(new Vector2d(-54,-62));


            TrajectoryActionBuilder trajectory2 = drive.actionBuilder(new Pose2d(-57,-60, Math.toRadians(180)))
                    .strafeToConstantHeading(new Vector2d(-47,-40));


            TrajectoryActionBuilder trajectory3 = drive.actionBuilder(new Pose2d(-47,-40, Math.toRadians(180)))
//                .strafeToConstantHeading(new Vector2d(-50,-10))
                    .splineToLinearHeading(new Pose2d(-23,-12, Math.toRadians(360)), 0)
                    .strafeToConstantHeading(new Vector2d(-6,-12));


            waitForStart();


            if (isStopRequested()) return;


            trajectory = trajectory1.build();
            trajectorytwo = trajectory2.build();
            traj3 = trajectory3.build();




            Actions.runBlocking(new SequentialAction(
                    lift.liftUp(),
                    trajectory,
                    intake.intakeIn(),
                    trajectorytwo,
                    lift.liftDown(),
                    traj3));
        }
    }
