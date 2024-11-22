package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.Arm.*;
import static org.firstinspires.ftc.teamcode.config.ArmPIDConfig.*;
import static org.firstinspires.ftc.teamcode.config.SlidesPIDConfig.SlidesD;
import static org.firstinspires.ftc.teamcode.config.SlidesPIDConfig.SlidesI;
import static org.firstinspires.ftc.teamcode.config.SlidesPIDConfig.SlidesP;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.config.ArmPIDConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.util.controller.calculation.pid.DoubleComponent;
import dev.frozenmilk.dairy.core.util.controller.implementation.DoubleController;
import dev.frozenmilk.dairy.core.util.supplier.numeric.CachedMotionComponentSupplier;
import dev.frozenmilk.dairy.core.util.supplier.numeric.EnhancedDoubleSupplier;
import dev.frozenmilk.dairy.core.util.supplier.numeric.MotionComponents;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.SDKSubsystem;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import dev.frozenmilk.util.cell.Cell;

public class Arm extends SDKSubsystem {
    public static final Arm INSTANCE = new Arm();
    public Arm() {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Attach{}

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Arm.Attach.class));

    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    public enum ArmState {
        HIGH_SCORING,
        MID_SCORING,
        SPECIMEN_SCORING,
        INTAKE,
        HOME
    }

    public static ArmState armState;

    //motors
    private final Cell<DcMotorEx> leftArm = subsystemCell(() -> getHardwareMap().get(DcMotorEx.class, Constants.Arm.leftArm));
    private final Cell<DcMotorEx> rightArm = subsystemCell(() -> getHardwareMap().get(DcMotorEx.class, Constants.Arm.rightArm));


    //encoder
    private final Cell<EnhancedDoubleSupplier> encoder = subsystemCell(() -> new EnhancedDoubleSupplier(() -> (double) leftArm.get().getCurrentPosition()));
    //current of motor
    private final Cell<EnhancedDoubleSupplier> current = subsystemCell(() -> new EnhancedDoubleSupplier(() -> leftArm.get().getCurrent(CurrentUnit.AMPS)));

    //controller
    private double targetPos = 0.0;
    private double targetVel = 0.0;
    private double posTolerance = 30.0;
    private double velTolerance = 1.0;
    private final CachedMotionComponentSupplier<Double> targetSupplier = new CachedMotionComponentSupplier<>(motionComponent -> {
        if (motionComponent == MotionComponents.STATE) {
            return targetPos;
        }/*
        else if (motionComponent == MotionComponents.VELOCITY) {
            return targetVel;
        }*/
        return Double.NaN;
    });
    private final CachedMotionComponentSupplier<Double> toleranceSupplier = new CachedMotionComponentSupplier<>(motionComponent -> {
        if (motionComponent == MotionComponents.STATE) {
            return posTolerance;
        }/*
        else if (motionComponent == MotionComponents.VELOCITY) {
            return velTolerance;
        }*/
        return Double.NaN;
    });
    private final Cell<DoubleController> controller = subsystemCell(() ->
            new DoubleController(
                    targetSupplier,
                    encoder.get(),
                    toleranceSupplier,
                    (Double power) -> {
                        leftArm.get().setPower(power);
                        rightArm.get().setPower(power);
                    },
                    new DoubleComponent.P(MotionComponents.STATE, ArmP)
                            .plus(new DoubleComponent.I(MotionComponents.STATE, ArmI))
                            .plus(new DoubleComponent.D(MotionComponents.STATE, ArmD))
            )
    );


    //set Target method
    public void setTarget(double target) {
        this.targetPos = target;
        targetSupplier.reset();
    }

    public void setArm(ArmState armState) {
        switch (armState) {
            case HIGH_SCORING:
                targetPos = highScoringPos;
                break;
            case MID_SCORING:
                targetPos = midScoringPos;
                break;
            case SPECIMEN_SCORING:
                targetPos = specimenScoringPos;
                break;
            case INTAKE:
                targetPos = intakePos;
                break;
            case HOME:
                targetPos = homePos;
                break;
        }
        Arm.armState = armState;
    }

    public double getVelocity() {
        return (this.encoder.get().rawVelocity());
    }

    public double getCurrent() {
        return (current.get().state());
    }

    public double getCurrentChange() {
        return (current.get().rawVelocity());
    }

    public double getEncoder() {
        return (encoder.get().state());
    }

    public boolean getControllerFinished() {
        return (controller.get().finished());
    }

    public void resetEncoder() {
        leftArm.get().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        targetSupplier.reset();
    }

    //init hook
    @Override
    public void preUserInitHook(@NonNull Wrapper opMode) {
        rightArm.get().setDirection(DcMotorSimple.Direction.REVERSE);
        leftArm.get().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.get().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        controller.get().setEnabled(false);
    }

    //start hook
    @Override
    public void preUserStartHook(@NonNull Wrapper opMode) {
        controller.get().setEnabled(true);
    }

    public Lambda runToPosition(double target) {
        return new Lambda("run_to_position-slides")
                .setInit(() -> setTarget(target))
                .setFinish(() -> controller.get().finished());
    }
    public Lambda setArmPosition(ArmState armState) {
        return new Lambda("setIntakePivot")
                .setInit(() -> setArm(armState));
    }
}
