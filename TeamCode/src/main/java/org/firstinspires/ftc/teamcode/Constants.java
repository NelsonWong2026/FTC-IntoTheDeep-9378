package org.firstinspires.ftc.teamcode;

public class Constants {

    public static final class MecanumDrive {
        public static final String leftFront = "leftFront";
        public static final String leftBack = "leftBack";
        public static final String rightFront = "rightFront";
        public static final String rightBack = "rightBack";
    }

    public static final class Time {
        public static final int teleopTime = 120;
        public static final int autoTime = 30;
    }

    public static final class Intake {
        public static final String intake = "intake";
    }

    public static final class Vision {
        public static final String camera1 = "Webcam 1";
        public static final String camera2 = "camera2";
    }

    public static final class Arm {
        public static final String upperArm = "upperArm";
        public static final String lowerArm = "lowerArm";
        //first element is encoder position for upperArm; second element is for lowerArm
        public static final int[] START = {0, 0};
        public static final int[] INTAKE = {3000, 4700};
        public static final int[] LOWER_BASKET = {4500, 1000};
        public static final int[] UPPER_BASKET = {7200, -300};
    }
}
