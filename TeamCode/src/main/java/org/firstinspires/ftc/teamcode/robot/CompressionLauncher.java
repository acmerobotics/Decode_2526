package org.firstinspires.ftc.teamcode.robot;
import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class CompressionLauncher {
    HardwareMap hMap;
    DcMotor leftLaunchMotor, rightLaunchMotor;
    Servo feedServo;

    private static final int OPEN_GATE_DURATION_MS =  500;

    private static final double POWER_STEP = 0.1;

    private static final float GATE_OPEN_DEGREES = 45;

    private static final float GATE_CLOSED_DEGREES = 130;

    private static final float GATE_DEGREES_SCALING = 300;

    private double power = 0;


    public CompressionLauncher(HardwareMap hardwareMap){
        hMap = hardwareMap;
        leftLaunchMotor = hMap.get(DcMotorEx.class, "leftLaunchMotor");
        rightLaunchMotor = hMap.get(DcMotorEx.class, "rightLaunchMotor");
        feedServo = hMap.get(Servo.class, "feedServo");
        rightLaunchMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLaunchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLaunchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void feedOne(){
        feedServo.setPosition(GATE_OPEN_DEGREES/GATE_DEGREES_SCALING);
        sleep(OPEN_GATE_DURATION_MS);
        feedServo.setPosition(GATE_CLOSED_DEGREES/GATE_DEGREES_SCALING);
    }

    public void start(){
        leftLaunchMotor.setPower(power);
        rightLaunchMotor.setPower(power);
    }

    public void stop(){
        leftLaunchMotor.setPower(0);
        rightLaunchMotor.setPower(0);
    }

    public void addPower(){
        power = Math.min(1.0, power + POWER_STEP);
    }
    public void subPower(){
        power = Math.max(0.0, power - POWER_STEP);
    }
}

