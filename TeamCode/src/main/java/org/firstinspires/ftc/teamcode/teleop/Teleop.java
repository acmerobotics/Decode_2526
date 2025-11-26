package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.CompressionLauncher;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "Teleop", group = "Linear OpMode")
public class Teleop extends LinearOpMode {

    static float GATE_OPEN_DEGREES = 45;
    static float GATE_CLOSED_DEGREES = 45 + 90;
    static float GATE_DEGREES_SCALING = 300;

    static double SLOWMO_POWER_SCALE = .2;

    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        DcMotor launchMotor = hardwareMap.get(DcMotor.class, "launchMotor");

        Servo feedServo = hardwareMap.get(Servo.class, "feedServo");

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        
        CompressionLauncher launcher = new CompressionLauncher(hardwareMap);

        waitForStart();
        
        while (opModeIsInit()){
            launchMotor.setPower(.7);
        }
        
        while (opModeIsActive()) {
            // Translation and rotation controls
            {
                double motorPowerScaling = gamepad1.left_bumper ? SLOWMO_POWER_SCALE : 1;
                double x = gamepad1.left_stick_x;
                double y = -gamepad1.left_stick_y;
                double turn = gamepad1.right_stick_x;
                leftFront.setPower((x + y + turn) * motorPowerScaling);
                leftBack.setPower((-x + y + turn) * motorPowerScaling);
                rightBack.setPower((x + y - turn) * motorPowerScaling);
                rightFront.setPower((-x + y - turn) * motorPowerScaling);
            }
            
            if (gamepad1.right_trigger > .50) {
                feedServo.setPosition(GATE_CLOSED_DEGREES/GATE_DEGREES_SCALING);
            }
            
            if (gamepad1.left_trigger > .50) {
                feedServo.setPosition(GATE_OPEN_DEGREES/GATE_DEGREES_SCALING);
            }

            telemetry.addData("servo pos", feedServo.getPosition());
            telemetry.addData("right trigger pose: ", gamepad1.right_trigger);
            telemetry.addData("left trigger pose: ", gamepad1.left_trigger);
            telemetry.update();
        }
    }
}
