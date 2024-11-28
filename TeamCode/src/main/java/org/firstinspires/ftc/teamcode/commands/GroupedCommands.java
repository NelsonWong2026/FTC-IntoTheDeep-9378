package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slides;

import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.commands.groups.CommandGroup;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.commands.util.Wait;

public class GroupedCommands {
    public static final GroupedCommands INSTANCE = new GroupedCommands();
    private GroupedCommands() {}

    public CommandGroup setHomeCommand() {
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
                            new Wait(0.4),
                            Intake.INSTANCE.setClawOpen(true)
                    ),
                    new Sequential(
                            new Wait(0.4),
                            Arm.INSTANCE.setArmPosition(Arm.ArmState.HOME)
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
    }

    public CommandGroup extendIntakeCommand() {
        return new Parallel(
                Slides.INSTANCE.setSlidePosition(Slides.SlideState.INTAKE),
                new Sequential(
                        new Wait(0.5),
                        Intake.INSTANCE.setIntakePivot(Intake.IntakePivotState.INTAKE)
                )
        );
    }

    public CommandGroup setScoringCommand() {
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
}
