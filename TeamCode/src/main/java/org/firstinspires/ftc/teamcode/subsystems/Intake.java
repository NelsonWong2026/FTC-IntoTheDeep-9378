package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.Intake.*;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.cachinghardware.CachingServo;
import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.SDKSubsystem;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import dev.frozenmilk.util.cell.Cell;

public class Intake extends SDKSubsystem {
    public static final Intake INSTANCE = new Intake();
    public Intake() {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Attach{}

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Intake.Attach.class));

    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    public enum IntakePivotState {
        SCORING,
        SPECIMEN_SCORING,
        INTAKE,
        HOME
    }

    public boolean clawOpen;
    private static IntakePivotState intakePivotState;

    //hardware
    private final Cell<CachingServo> intakePivotLeft = subsystemCell(() -> getHardwareMap().get(CachingServo.class, Constants.Intake.intakePivotLeft));
    private final Cell<CachingServo> intakePivotRight = subsystemCell(() -> getHardwareMap().get(CachingServo.class, Constants.Intake.intakePivotRight));
    private final Cell<CachingServo> intake = subsystemCell(() -> getHardwareMap().get(CachingServo.class, Constants.Intake.intake));
    private final Cell<CachingServo> rotatingIntake = subsystemCell(() -> getHardwareMap().get(CachingServo.class, Constants.Intake.rotatingIntake));

    //set target method
    public void setPivotPosition(double position) {
        intakePivotLeft.get().setPosition(position);
        intakePivotRight.get().setPosition(position);
    }
    public double getPivotPosition() {
        return intakePivotLeft.get().getPosition();
    }
    public void setPivot(IntakePivotState intakePivotState) {
        switch (intakePivotState) {
            case SCORING:
                intakePivotLeft.get().setPosition(scoringPos);
                intakePivotRight.get().setPosition(scoringPos);
                break;
            case SPECIMEN_SCORING:
                intakePivotLeft.get().setPosition(specimenScoringPos);
                intakePivotRight.get().setPosition(specimenScoringPos);
                break;
            case INTAKE:
                intakePivotLeft.get().setPosition(intakePos);
                intakePivotRight.get().setPosition(intakePos);
                break;
            case HOME:
                intakePivotLeft.get().setPosition(homePos);
                intakePivotRight.get().setPosition(homePos);
                break;
        }
        Intake.intakePivotState = intakePivotState;
    }

    public void setRotation(double rotation) {
        rotatingIntake.get().setPosition(rotation);
    }

    public void clawOpen(boolean open) {
        if (open) {
            intake.get().setPosition(1);
        }
        else {
            intake.get().setPosition(0);
        }
    }

    @Override
    public void preUserInitHook(@NonNull Wrapper opMode) {
        intakePivotLeft.get().setDirection(Servo.Direction.REVERSE);
    }

    public Lambda setIntakePivot(IntakePivotState intakePivotState) {
        return new Lambda("setIntakePivot")
                .setInit(() -> setPivot(intakePivotState));
    }

    public Lambda setClawOpen(boolean open) {
        return new Lambda("setClaw")
                .setInit(() -> clawOpen(open));
    }
}
