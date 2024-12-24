package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.Intake.*;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HIGH_SCORING;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmState.HOME;

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
    private Intake() {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Attach{}

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Attach.class));

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
        START,
        SCORING,
        SPECIMEN_SCORING,
        INTAKE,
        HOME
    }

    public enum ClawState {
        OPEN,
        CLOSED
    }

    public boolean clawOpen;
    public static IntakePivotState intakePivotState = IntakePivotState.START;
    private static ClawState clawState = ClawState.CLOSED;

    //hardware
    private final Cell<CachingServo> intakePivotLeft = subsystemCell(() -> new CachingServo(getHardwareMap().get(Servo.class, Constants.Intake.intakePivotLeft)));
    private final Cell<CachingServo> intakePivotRight = subsystemCell(() -> new CachingServo(getHardwareMap().get(Servo.class, Constants.Intake.intakePivotRight)));
    private final Cell<CachingServo> intake = subsystemCell(() -> new CachingServo(getHardwareMap().get(Servo.class, Constants.Intake.intake)));
    private final Cell<CachingServo> rotatingIntake = subsystemCell(() -> new CachingServo(getHardwareMap().get(Servo.class, Constants.Intake.rotatingIntake)));

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
            case START:
                intakePivotLeft.get().setPosition(startPos);
                intakePivotRight.get().setPosition(startPos);
        }
        Intake.intakePivotState = intakePivotState;
    }

    public void setClawPosition(double position) {
        intake.get().setPosition(position);
    }

    public void setRotation(double rotation) {
        rotatingIntake.get().setPosition(rotation);
    }

    public void clawOpen(boolean open) {

        if (open) {
            intake.get().setPosition(clawOpenPos);
            Intake.clawState = ClawState.OPEN;
        }
        else {
            intake.get().setPosition(clawClosedPos);
            Intake.clawState = ClawState.CLOSED;
        }
    }

    public void clawOpenAndClose() {
        switch (Intake.clawState) {
            case OPEN:
                intake.get().setPosition(clawClosedPos);
                Intake.clawState = ClawState.CLOSED;
                break;
            case CLOSED:
                intake.get().setPosition(clawOpenPos);
                Intake.clawState = ClawState.OPEN;
                break;
        }
    }

    public void clawOpenAndCloseSmaller() {
        switch (Intake.clawState) {
            case OPEN:
                intake.get().setPosition(clawClosedPos);
                Intake.clawState = ClawState.CLOSED;
                break;
            case CLOSED:
                intake.get().setPosition(clawOpenSmallerPos);
                Intake.clawState = ClawState.OPEN;
                break;
        }
    }

    public void clawOpenSmaller() {
            intake.get().setPosition(clawOpenSmallerPos);
            Intake.clawState = ClawState.OPEN;
    }

    @Override
    public void preUserInitHook(@NonNull Wrapper opMode) {
        intakePivotRight.get().setDirection(Servo.Direction.REVERSE);
        intake.get().setDirection(Servo.Direction.REVERSE);
    }

    public Lambda setIntakePivot(IntakePivotState intakePivotState) {
        return new Lambda("setIntakePivot")
                .setInit(() -> setPivot(intakePivotState));
    }

    public Lambda setClawOpen(boolean open) {
        return new Lambda("setClaw")
                .setInit(() -> clawOpen(open));
    }

    public Lambda setClawOpenAndClose() {
        return new Lambda("setClawOpenAndClose")
                .setInit(() -> clawOpenAndClose());
    }

    public Lambda setClawPos(double pos) {
        return new Lambda("SetClawPos")
                .setInit(() -> setClawPosition(pos));
    }

    public Lambda setIntakeRotation(double position) {
        return new Lambda("setIntakeRotation")
                .setInit(() -> setRotation(position));
    }

    public Lambda clawRegrip() {
        return new Lambda("clawRegrip")
                .setInit(() -> setClawPosition(clawRegripPos));
    }

    public Lambda setIntakePivotPosition(double position) {
        return new Lambda("setIntakePivotPosition")
                .setInit(() -> {
                    intakePivotLeft.get().setPosition(position);
                    intakePivotRight.get().setPosition(position);
                });
    }

    public Lambda setClawOpenSmaller() {
        return new Lambda("setClawOpenSmaller")
                .setInit(() -> clawOpenSmaller());
    }

    public Lambda setClawOpenAndCloseSmaller() {
        return new Lambda("setClawOpenSmaller")
                .setInit(() -> clawOpenAndCloseSmaller());
    }

}
