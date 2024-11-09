package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.75)
                .build();

        /*myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35,-60, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(35.0,-27.0, Math.toRadians(0)), Math.toRadians(90))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(-45)), Math.toRadians(50))
                //drop
                .splineToLinearHeading(new Pose2d(44, -27, Math.toRadians(0)), Math.toRadians(50))
                //PICKUP
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(-45)), Math.toRadians(50))
                //DROP
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(0)), Math.toRadians(50))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0,-27.0, Math.toRadians(0)), Math.toRadians(50))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0,-54.0, Math.toRadians(-45)), Math.toRadians(50))
                //drop
                .build());*/


        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35,-60, Math.toRadians(180)))
                .setTangent(Math.toRadians(180))
                .lineToX(45)
                .lineToX(35)
                .setTangent(Math.toRadians(90))
                .lineToYLinearHeading(-37, Math.toRadians(180))
                .lineToY(-13)
                .strafeToLinearHeading(new Vector2d(45, -13), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .lineToY(-13)
                .strafeToConstantHeading(new Vector2d(55, -13))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .lineToY(-13)
                .strafeToConstantHeading(new Vector2d(61, -13))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .build());



                /*
                .splineToSplineHeading(new Pose2d(-10.8,-12.4, Math.toRadians(-90)), Math.toRadians(60))
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-36,61.6, Math.toRadians(-90)), Math.toRadians(90))
                .build());

                 */

        /*myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-36,61.6, Math.toRadians(-90)))
                .splineToSplineHeading(new Pose2d(-10.8,-12.4, Math.toRadians(90)), Math.toRadians(0))
                .build());*/


        /*myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))
                .lineToX(30)
                .turn(Math.toRadians(90))
                .lineToY(30)
                .turn(Math.toRadians(90))
                .lineToX(0)
                .turn(Math.toRadians(90))
                .lineToY(0)
                .turn(Math.toRadians(90))
                .build());*/

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}