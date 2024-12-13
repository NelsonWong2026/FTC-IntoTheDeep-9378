package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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
        //right auto 3
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -62, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(0, -47))
                //move to pieces
                .splineToLinearHeading(new Pose2d(35, -32, Math.toRadians(45)), Math.toRadians(90))
                //move piece 1
                .strafeToLinearHeading(new Vector2d(37, -43), Math.toRadians(-45))
                //move piece 2
                .strafeToLinearHeading(new Vector2d(48, -32), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(48, -43), Math.toRadians(-45))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -35), Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(46, -48.5))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(2, -52))
                .strafeToConstantHeading(new Vector2d(2, -49))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -44), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-48.5)
                //move to submersible
                .strafeToConstantHeading(new Vector2d(4, -52))
                .strafeToConstantHeading(new Vector2d(4, -49))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -44), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-48.5)
                //move to submersible
                .strafeToConstantHeading(new Vector2d(6, -52))
                .strafeToConstantHeading(new Vector2d(6, -49))
                .build());

 */

        /*
        //right auto 2
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -62, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(0, -47))
                //move to pieces
                .splineToConstantHeading(new Vector2d(37, -33), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(37, -15), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(46, -10, Math.toRadians(180)), Math.toRadians(-90))
                //move piece 1
                .splineToConstantHeading(new Vector2d(46, -55), Math.toRadians(-90), new TranslationalVelConstraint(100), new ProfileAccelConstraint(-30, 60))
                .splineToConstantHeading(new Vector2d(46, -10), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(56, -10), Math.toRadians(0))
                //move piece 2
                .strafeToConstantHeading(new Vector2d(56, -55))
                .splineToConstantHeading(new Vector2d(56, -50), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(56, -35, Math.toRadians(-90)), Math.toRadians(90))
                //move to pickup
                .splineToConstantHeading(new Vector2d(46, -35), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(46, -48.5), Math.toRadians(-90))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(2, -52))
                .strafeToConstantHeading(new Vector2d(2, -49))
                .setTangent(Math.toRadians(90))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -44), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-48.5)
                //move to submersible
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(4, -54), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(4, -49), Math.toRadians(90))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -44), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-48.5)
                //move to submersible
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(6, -54), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(6, -49), Math.toRadians(90))
                //park
                .strafeToConstantHeading(new Vector2d(46, -58))
                .build());
         */
/*
        //right auto 2
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -62, Math.toRadians(-90)))
                //move to submersible
                .strafeToConstantHeading(new Vector2d(0, -47))
                //move to pieces
                .splineToConstantHeading(new Vector2d(35, -33), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(35, -15), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(45, -10, Math.toRadians(0)), Math.toRadians(-90))
                //move piece 1
                .splineToConstantHeading(new Vector2d(45, -54), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(45, -10), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(55, -10), Math.toRadians(-90))
                //move piece 2
                .splineToConstantHeading(new Vector2d(55, -54), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(55, -10), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(60, -10), Math.toRadians(-90))
                //move piece 3
                .splineToConstantHeading(new Vector2d(60, -56), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(60, -48), Math.toRadians(90))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -45), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-47)
                //move to submersible
                .strafeToLinearHeading(new Vector2d(4, -47), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -45), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-47)
                //move to submersible
                .strafeToLinearHeading(new Vector2d(4, -47), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                //move to pickup
                .strafeToLinearHeading(new Vector2d(46, -45), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-47)
                //move to submersible
                .strafeToLinearHeading(new Vector2d(4, -47), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                //park
                .strafeToConstantHeading(new Vector2d(46, -58))
                .build());
*/

        //left auto
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-38, -61.5, Math.toRadians(90)))
                //move to basket
                .strafeToLinearHeading(new Vector2d(-52,-55), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45))
                //move to middle sample
                .strafeToLinearHeading(new Vector2d(-58.5,-38), Math.toRadians(90))
                //move to basket
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45))
                //move to right sample
                .strafeToLinearHeading(new Vector2d(-48.5,-36), Math.toRadians(90))
                //move to basket
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45))
                //move to left sample
                .strafeToLinearHeading(new Vector2d(-55,-37), Math.toRadians(140))
                //move to basket
                .strafeToLinearHeading(new Vector2d(-52.5,-52.5), Math.toRadians(45))
                //park
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-35, -10, Math.toRadians(0)), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-25, -10), Math.toRadians(0))


                .build());


/*
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
*/
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}