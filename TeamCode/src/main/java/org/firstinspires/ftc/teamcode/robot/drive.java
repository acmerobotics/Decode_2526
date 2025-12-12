package org.firstinspires.ftc.teamcode.robot;
import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.Consumer;

public class drive {
    HardwareMap hMap;
    DcMotor dcMotorA;
    DcMotor dcMotorB;
    DcMotor dcMotorC;
    DcMotor dcMotorD;
    
    // The value that we multiply the encoder by to get one tile
    static final float WHEEL_MOTOR_ENCODER_SCALING = 1003.046f;

    public drive(HardwareMap hardwareMap) {
        hMap = hardwareMap;
        dcMotorA = hMap.get(DcMotorEx.class, "leftFront");
        dcMotorB = hMap.get(DcMotorEx.class, "leftBack");
        dcMotorC = hMap.get(DcMotorEx.class, "rightBack");
        dcMotorD = hMap.get(DcMotorEx.class, "rightFront");
        dcMotorA.setDirection(DcMotorSimple.Direction.REVERSE);
        dcMotorB.setDirection(DcMotorSimple.Direction.REVERSE);
        forEachMotor(m -> m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER));
    }

    public void driveTiles(float Tiles) {
        forEachMotor(m -> m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        forEachMotor(m -> m.setTargetPosition((int) (WHEEL_MOTOR_ENCODER_SCALING * Tiles)));
        forEachMotor(m -> m.setMode(DcMotor.RunMode.RUN_TO_POSITION));
        forEachMotor(m -> m.setPower(.75));
        sleep(2000);
    }

    
    // Positive rotates to the left, and negative rotates to the right
    public void setRotateDegrees(double Deg) {
        
        // This assumes the bot wheelbase radius is 8 in times sqrt of 2
        final float DEGREES_TO_TICKS = (float) (2 * Math.PI * Math.sqrt(2) / 3 / 360 * WHEEL_MOTOR_ENCODER_SCALING);
        forEachMotor(m -> m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        forEachMotor(m -> m.setTargetPosition((int) (WHEEL_MOTOR_ENCODER_SCALING * DEGREES_TO_TICKS)));
        forEachMotor(m -> m.setMode(DcMotor.RunMode.RUN_TO_POSITION));
        forEachMotor(m -> m.setPower(.75));
        sleep(2000);
    }
    
    // Negative is left, Positive is right
    public void strafeTiles(float toBeStrafed) {
        forEachMotor(m -> m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        forEachMotor(m -> m.setTargetPosition((int) (WHEEL_MOTOR_ENCODER_SCALING * toBeStrafed)));
        forEachMotor(m -> m.setMode(DcMotor.RunMode.RUN_TO_POSITION));
        forEachMotor(m -> m.setPower(.75));
        sleep(2000);
    }
    
    public String gatherMotorPos() {
        return "dcMotorA Position " + dcMotorA.getCurrentPosition() +
                "\ndcMotorB Position " + dcMotorB.getCurrentPosition() +
                "\ndcMotorC Position " + dcMotorC.getCurrentPosition() +
                "\nDcMotorD Position " + dcMotorD.getCurrentPosition();

    }
    private void forEachMotor(Consumer<DcMotor> f){
        f.accept(dcMotorA);
        f.accept(dcMotorB);
        f.accept(dcMotorC);
        f.accept(dcMotorD);
    }
}
