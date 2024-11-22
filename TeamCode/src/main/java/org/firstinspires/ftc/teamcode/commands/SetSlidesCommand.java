package org.firstinspires.ftc.teamcode.commands;

import androidx.annotation.NonNull;


import org.firstinspires.ftc.teamcode.subsystems.Slides;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.commands.Command;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.commands.util.Wait;

public class SetSlidesCommand implements Command {
    Slides slides = new Slides();
    Slides.SlideState slideState;

    public SetSlidesCommand(Slides.SlideState slideState) {
        this.slideState = slideState;
    }

    @Override
    public void initialise() {
        slides.setSlidePosition(slideState);
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
        returnset.add(slides);
        return (returnset);
    }

    @NonNull
    @Override
    public Set<Wrapper.OpModeState> getRunStates() {
        return Collections.singleton(Wrapper.OpModeState.ACTIVE);
    }
}
