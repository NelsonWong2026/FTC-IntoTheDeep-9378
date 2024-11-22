package org.firstinspires.ftc.teamcode.commands;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Intake.IntakePivotState;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.subsystems.Slides.SlideState;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.commands.Command;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.commands.util.Wait;

public class RetractIntakeCommand implements Command {
    @Override
    public void initialise() {
        new Parallel(
                Intake.INSTANCE.setIntakePivot(IntakePivotState.HOME),
                new Sequential(
                        new Wait(1),
                        Slides.INSTANCE.setSlidePosition(SlideState.HOME)
                )
        );
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean b) {

    }

    @Override
    public boolean finished() {
        return true;
    }

    private final HashSet<Object> requirements = new HashSet<>(); {
        requirements.add(Intake.INSTANCE);
        requirements.add(Slides.INSTANCE);
    }

    @NonNull
    @Override
    public Set<Object> getRequirements() {
        return requirements;
    }

    @NonNull
    @Override
    public Set<Wrapper.OpModeState> getRunStates() {
        return Collections.singleton(Wrapper.OpModeState.ACTIVE);
    }
}
