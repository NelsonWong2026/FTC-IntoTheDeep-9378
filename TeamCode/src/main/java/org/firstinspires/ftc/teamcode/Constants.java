package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

public class Constants {
    public static final class Drive {
        public static final String leftFront = "leftFront";
        public static final String leftBack = "leftBack";
        public static final String rightFront = "rightFront";
        public static final String rightBack = "rightBack";
    }

    public static final class Arm {
        public static final String leftArm = "leftArm";
        public static final String rightArm = "rightArm";
        public static final double highScoringPos = 3700.0;
        public static final double midScoringPos = 0.0;
        public static final double specimenScoringPos = 0.0;
        public static final double intakePos = 0.0;
        public static final double homePos = 0.0;
    }

    public static final class Slides {
        public static final String slides = "slides";
        public static final double highScoringPos = 0.0;
        public static final double midScoringPos = 0.0;
        public static final double specimenScoringPos = 0.0;
        public static final double intakePos = 1600.0;
        public static final double homePos = 0.0;
        public static final double MAX = 3100.0;
    }

    @Config
    public static final class Intake {
        public static final String intakePivotLeft = "intakePivotLeft";
        public static final String intakePivotRight = "intakePivotRight";
        public static final String intake = "intake";
        public static final String rotatingIntake = "rotatingIntake";
        public static final double scoringPos = 0.0;
        public static final double specimenScoringPos = 0.0;
        public static final double intakePos = 0.0;
        public static final double homePos = 0.0;
    }


}