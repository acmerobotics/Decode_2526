package org.firstinspires.ftc.teamcode.robot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class compressionLauncher {
    HardwareMap hMap = null;
    DcMotor compressionMotor = null;
    Servo feedServo = null;

    public clHardwareMap(HardwareMap hardwareMap) {
        hMap = hardwareMap;
        compressionMotor = hMap.get(DcMotorEx.class, "leftFront");
        feedServo = hMap.get(Servo.class, "feedServo");
    }

    public void launch(float Tiles) {
        compressionMotor.setPower(1);
        feedServo.setPosition(1);
        sleep(500);
        compressionMotor.setPower(0);
        feedServo.setPosition(0);
    }

    public void load(){
        feedServo.setPosition(0.5);
        sleep(500);
        feedServo.setPosition(0);
    }

    public void fire(){
        compressionMotor.setPower(1);
        sleep(500);
        compressionMotor.setPower(0);
    }

}

