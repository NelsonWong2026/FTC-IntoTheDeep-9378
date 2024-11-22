package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotor;
import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.dairy.pasteurized.SDKGamepad;
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.bindings.BoundGamepad;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.SDKSubsystem;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import dev.frozenmilk.util.cell.Cell;

public class Drive extends SDKSubsystem {
    public static final Drive INSTANCE = new Drive();
    public Drive() {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Attach{}

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Drive.Attach.class));

    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    private final Cell<CachingDcMotor> leftFront = subsystemCell(() -> getHardwareMap().get(CachingDcMotor.class, ""));
    private final Cell<CachingDcMotor> leftBack = subsystemCell(() -> getHardwareMap().get(CachingDcMotor.class, ""));
    private final Cell<CachingDcMotor> rightFront = subsystemCell(() -> getHardwareMap().get(CachingDcMotor.class, ""));
    private final Cell<CachingDcMotor> rightBack = subsystemCell(() -> getHardwareMap().get(CachingDcMotor.class, ""));

    @Override
    public void preUserInitHook(@NonNull Wrapper opMode) {
        rightFront.get().setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.get().setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Lambda robotCentricDriveCommand() {
        BoundGamepad gamepad1 = Mercurial.gamepad1();
        return new Lambda("mecanum-drive-robot-centric")
                .setInit(() -> {

                })
                .setExecute(() -> {
                    double y = -gamepad1.leftStickY().state();
                    double x = gamepad1.leftStickX().state() * 1.1;
                    double rx = gamepad1.rightStickX().state();

                    double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                    double frontLeftPower = (y + x + rx) / denominator;
                    double backLeftPower = (y - x + rx) / denominator;
                    double frontRightPower = (y - x - rx) / denominator;
                    double backRightPower = (y + x - rx) / denominator;

                    leftFront.get().setPower(frontLeftPower);
                    leftBack.get().setPower(backLeftPower);
                    rightFront.get().setPower(frontRightPower);
                    rightBack.get().setPower(backRightPower);
                })
                .setFinish(() -> false);
    }
}
