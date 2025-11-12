package org.firstinspires.ftc.teamcode.robot;
import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class compressionLauncher {
    HardwareMap hMap = null;
    DcMotor compressionMotor = null;
    Servo feedServo = null;

    public void clHardwareMap(HardwareMap hardwareMap) {
        hMap = hardwareMap;
        compressionMotor = hMap.get(DcMotorEx.class, "leftFront");
        feedServo = hMap.get(Servo.class, "feedServo");
    }

    public void fire() {
        compressionMotor.setPower(1);
        load();
    }

    public void load(){
        feedServo.setPosition(0.5);
        sleep(500);
        feedServo.setPosition(0);
    }

    public void poopoospray(){
        compressionMotor.setPower(1);
        sleep(500);
        compressionMotor.setPower(0);
    }

}

