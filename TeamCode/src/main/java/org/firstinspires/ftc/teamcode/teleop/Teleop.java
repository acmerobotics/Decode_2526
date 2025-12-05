package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.CompressionLauncher;

@TeleOp(name = "Teleop", group = "Linear OpMode")
public class Teleop extends LinearOpMode {

    static double SLOWMO_POWER_SCALE = .2;

    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        DcMotor leftLaunchMotor = hardwareMap.get(DcMotorEx.class, "leftLaunchMotor");
        DcMotor rightLaunchMotor = hardwareMap.get(DcMotorEx.class, "rightLaunchMotor");

        Servo feedServo = hardwareMap.get(Servo.class, "feedServo");

        CompressionLauncher launcher = new CompressionLauncher(hardwareMap);

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLaunchMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftLaunchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLaunchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        /*
        while (opModeInInit()){
            launchMotor.setPower(.7);
        }
        */
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
                launcher.feedOne();
            }

            if (gamepad1.dpad_down){
                launcher.addPower();
            }

            if (gamepad1.dpad_up){
                launcher.subPower();
            }

            if (gamepad1.a){
                launcher.start();
            }

            if (gamepad1.b){
                launcher.stop();
            }


            telemetry.addData("servo pos", feedServo.getPosition());
            telemetry.addData("right trigger pose: ", gamepad1.right_trigger);
            telemetry.addData("left trigger pose: ", gamepad1.left_trigger);
            telemetry.addData("Left launcher speed: ", leftLaunchMotor.getPower());
            telemetry.addData("Right launcher speed: ", rightLaunchMotor.getPower());

            telemetry.update();
        }
    }
}
