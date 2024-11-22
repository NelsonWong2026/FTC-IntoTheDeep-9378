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
    Intake intake = new Intake();
    Slides slides = new Slides();

    @Override
    public void initialise() {
        new Parallel(
                intake.setIntakePivot(IntakePivotState.HOME),
                new Sequential(
                        new Wait(1),
                        slides.setSlidePosition(SlideState.HOME)
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

    @NonNull
    @Override
    public Set<Object> getRequirements() {
        Set<Object> returnset = new HashSet<>();
        returnset.add(intake);
        returnset.add(slides);
        return (returnset);
    }

    @NonNull
    @Override
    public Set<Wrapper.OpModeState> getRunStates() {
        return Collections.singleton(Wrapper.OpModeState.ACTIVE);
    }
}
