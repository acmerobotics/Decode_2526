package org.firstinspires.ftc.teamcode.robot;
import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Launcher {
  DcMotor CompressionMotor = null;
  Servo FeedServo = null;
    public Launcher(HardwareMap hardwareMap) {
        hMap = hardwareMap;
         CompressionMotor = hMap.get(DcMotorEx.class, "CompressionMotor");
         FeedServo = hMap.get(Servo.class, "FeedServo");
    }


}
