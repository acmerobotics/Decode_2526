package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.robot.visionTest;

@TeleOp(name = "Teleop", group = "Linear OpMode")
public class Teleop extends LinearOpMode {
    static double SLOWMO_POWER_SCALE = .2;

    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        visionTest vision = new visionTest();
        vision.initVision(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            // Translation and rotation controls

            {
                double motorPowerScaling = gamepad1.left_bumper ? SLOWMO_POWER_SCALE : 1;
                double x = gamepad1.left_stick_x;
                double y = -gamepad1.left_stick_y; // Invert y to match coordinate system
                double turn = gamepad1.right_stick_x;
                leftFront.setPower((x + y + turn) * motorPowerScaling);
                leftBack.setPower((-x + y + turn) * motorPowerScaling);
                rightBack.setPower((x + y - turn) * motorPowerScaling);
                rightFront.setPower((-x + y - turn) * motorPowerScaling);
            }

            {
                for (AprilTagDetection detection : vision.getDetections()) {
                    telemetry.addData("AprilTag ID", detection.id);
                }
                telemetry.update();
            }
        }
    }
}
