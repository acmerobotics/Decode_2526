package org.firstinspires.ftc.teamcode.robot;
import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class CompressionLauncher {
    HardwareMap hMap = null;
    DcMotor compressionMotor = null;
    Servo feedServo = null;

    private static int SPINUP_WAIT_MS = 250;
    private static double FEED_OPEN_POSE =  0.25;
    private static double FEED_CLOSE_POSE =  0;

    private static int OPEN_GATE_DURATION_MS =  3000;
//: Comment

    public CompressionLauncher(HardwareMap hardwareMap){
        hMap = hardwareMap;
        compressionMotor = hMap.get(DcMotorEx.class, "leftFront");
        feedServo = hMap.get(Servo.class, "feedServo");
    }


    public void launchAll(){
        compressionMotor.setPower(1);
        sleep(SPINUP_WAIT_MS);
        feedServo.setPosition(FEED_OPEN_POSE);
        sleep(OPEN_GATE_DURATION_MS);
        feedServo.setPosition(FEED_CLOSE_POSE);
        compressionMotor.setPower(0);


    }

}

