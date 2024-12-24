package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slides;

import dev.frozenmilk.mercurial.commands.groups.CommandGroup;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.commands.util.Wait;

public class GroupedCommands {
    public static final GroupedCommands INSTANCE = new GroupedCommands();
    private GroupedCommands() {}

   /*public CommandGroup setHomeCommand() {
       if (Arm.armState == Arm.ArmState.HIGH_SCORING) {
           return new Parallel(
                   Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME),
                   new Sequential(
                           new Wait(0.3),
                           Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)
                   ),
                   new Sequential(
                           new Wait(1),
                           Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
                   )
           );
       }
       else if (Arm.armState == Arm.ArmState.INTAKE){
           return new Parallel(
                   Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME),
                   Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)
           );
       }
       else if (Arm.armState == Arm.ArmState.SPECIMEN_SCORING) {
           return new Parallel(
                   Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE),
                   new Sequential(
                           new Wait(2),
                           Intake.INSTANCE.setClawOpen(true)
                   ),
                   new Sequential(
                           new Wait(2.5),
                           Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
                   ),
                   new Sequential(
                           new Wait(2.5),
                           Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME)
                   )
           );
       }
       else {
           return new Parallel(
                   Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME),
                   Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME),
                   Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)
           );
       }
   }*/

    public CommandGroup intakeToHomeCommand() {
        return new Parallel(
                Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME),
                Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME),
                Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)
        );
    }

    public CommandGroup scoringToHomeCommand() {
        return new Parallel(
                Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME),
                new Sequential(
                        new Wait(0.3),
                        Intake.INSTANCE.setIntakeRotation(Constants.Intake.rotation0Pos),
                        Slides.INSTANCE.setSlidePosition(Slides.SlideState.HOME)
                ),
                new Sequential(
                        new Wait(1),
                        Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
                )
        );
    }

    public CommandGroup setSpecimenCommand() {
        return new Parallel(
                Arm.INSTANCE.setArmPosition(Arm.ArmState.SPECIMEN_SCORING),
                Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SPECIMEN_SCORING)
        );
    }

    public CommandGroup scoreSpecimenCommand() {
        return new Parallel(
                Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE),
                new Sequential(
                        new Wait(1),
                        Intake.INSTANCE.setClawOpen(true)
                ),
                new Sequential(
                        new Wait(1.5),
                        Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
                ),
                new Sequential(
                        new Wait(1.5),
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME)
                )
        );
    }

    public CommandGroup extendIntakeCommand() {
        return new Parallel(
                Slides.INSTANCE.setSlidePosition(Slides.SlideState.INTAKE),
                Intake.INSTANCE.setClawOpenSmaller(),
                new Sequential(
                        new Wait(0.25),
                        Arm.INSTANCE.setArmPosition(Arm.ArmState.INTAKE)
                ),
                new Sequential(
                        new Wait(0.5),
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)
                )
        );
    }

    public CommandGroup grabSample() {
        return new Parallel(
                //move arm down
                Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME),
                //grab
                new Sequential(
                        new Wait(0.25),
                        Intake.INSTANCE.setClawOpen(false)
                ),

                //move arm back up
                new Sequential(
                        new Wait(0.35),
                        Arm.INSTANCE.setArmPosition(Arm.ArmState.INTAKE)
                )
        );
    }

    public CommandGroup setScoringCommand() {
        return new Parallel(
                Arm.INSTANCE.setArmPosition(Arm.ArmState.HIGH_SCORING),
                Intake.INSTANCE.setIntakeRotation(Constants.Intake.rotation90Pos),
                new Sequential(
                        new Wait(0.7),
                        Slides.INSTANCE.setSlidePosition(Slides.SlideState.HIGH_SCORING)
                ),
                new Sequential(
                        new Wait(1.5),
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)
                )
        );
    }
    public CommandGroup autoSetScoringCommand() {
        return new Parallel(
                Arm.INSTANCE.setArmPosition(Arm.ArmState.HIGH_SCORING),
                new Sequential(
                        new Wait(0.7),
                        Slides.INSTANCE.setSlidePosition(Slides.SlideState.HIGH_SCORING)
                ),
                new Sequential(
                        new Wait(1.5),
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING)
                )
        );
    }

    public CommandGroup regripSpecimen() {
        return new Sequential(
                Intake.INSTANCE.clawRegrip(),
                new Wait(0.5),
                Intake.INSTANCE.setClawOpen(false)
        );
    }

    public CommandGroup setSpecimenBackwardsCommand() {
        return new Parallel(
                Arm.INSTANCE.runToPosition(4000),
                Intake.INSTANCE.setIntakePivotPosition(0.55)
        );
    }

    public CommandGroup scoreSpecimenBackwardsCommand() {
        return new Parallel(
                Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.SCORING),
                new Sequential(
                        new Wait(1),
                        Intake.INSTANCE.setClawOpen(false)
                ),
                new Sequential(
                        new Wait(1.3),
                        Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
                ),
                new Sequential(
                        new Wait(1.5),
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.HOME)
                )
        );
    }
}
