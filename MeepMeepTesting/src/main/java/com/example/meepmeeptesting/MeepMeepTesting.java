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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 16)
                .build();


        /*
        //left auto
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-38, -61.5, Math.toRadians(90)))
                .setTangent(180)
                .strafeToLinearHeading(new Vector2d(-52,-52), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45))
                .strafeTo(new Vector2d(-56,-56))
                .strafeToLinearHeading(new Vector2d(-58.5,-38), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-50,-36), Math.toRadians(95))
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-48.5,-38), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-50,-16), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-6,-12, Math.toRadians(0)), Math.toRadians(180))
                .build());
*/

        //right auto
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -62, Math.toRadians(90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(0, -32))
                .waitSeconds(1)
                //move to pickup
                .lineToY(-40)
                .strafeToLinearHeading(new Vector2d(46, -50), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-58)
                .waitSeconds(1)
                //move to submersible
                .strafeToLinearHeading(new Vector2d(4, -40), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(-32)
                //park
                .lineToY(-40)
                .strafeToConstantHeading(new Vector2d(46, -58))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}