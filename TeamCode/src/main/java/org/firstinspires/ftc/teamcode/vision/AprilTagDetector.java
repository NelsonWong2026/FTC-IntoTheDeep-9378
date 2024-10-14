package org.firstinspires.ftc.teamcode.vision;

import android.annotation.SuppressLint;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.concurrent.TimeUnit;

public class AprilTagDetector {
    @SuppressLint("DefaultLocale")
    private HardwareMap hwMap;

    public AprilTagDetector(HardwareMap hwMap) {
        this.hwMap = hwMap;

    }

    AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
            .setDrawAxes(true)
            .setDrawCubeProjection(true)
            .setDrawTagID(true)
            .setDrawTagOutline(true)
            .setTagLibrary(AprilTagGameDatabase.getIntoTheDeepTagLibrary())
            .build();

    VisionPortal visionPortal = new VisionPortal.Builder()
            .addProcessor(tagProcessor)
            .setCamera(hwMap.get(WebcamName.class, Constants.Vision.camera1))
            .setCameraResolution(new Size(640, 480))
            .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
            .build();


    /*while (visionPortal != VisionPortal.CameraState.STREAMING) {}

    ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
    exposure.setMode(ExposureControl.Mode.Manual);
    exposure.setExposure(10, TimeUnit.MILLISECONDS);

    GainControl gain = visionPortal.getCameraControl(GainControl.class);
    gain.setGain(gain.getMaxGain());



        if (tagProcessor.getDetections().size() > 0) {
        AprilTagDetection tag = tagProcessor.getDetections().get(0);


            telemetry.addLine(String.format("XYZ %6.2f %6.2f %6.2f", tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z));

            telemetry.addData("roll", tag.ftcPose.roll);
            telemetry.addData("pitch", tag.ftcPose.pitch);
            telemetry.addData("yaw", tag.ftcPose.yaw);

        telemetry.update();
    }
*/
}