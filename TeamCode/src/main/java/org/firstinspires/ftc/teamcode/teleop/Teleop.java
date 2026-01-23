package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.robot.CompressionLauncher;

@TeleOp(name = "Teleop", group = "Linear OpMode")
public class Teleop extends LinearOpMode {
    static double SLOWMO_POWER_SCALE = .2;
    boolean incrementReady = true;

    // REPLACE THESE NUMBERS with your actual calibration
    // How many encoder ticks does it take to move 1 inch forward?
    static double TICKS_PER_INCH_FORWARD = 45.0;
    // How many encoder ticks does it take to strafe 1 inch sideways? (Strafing is usually less efficient)
    static double TICKS_PER_INCH_STRAFE = 50.0;

    public void runOpMode() {
        String status = "OK";
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        CompressionLauncher launcher = new CompressionLauncher(hardwareMap);

        // Reverse the left side (standard for most tank/mecanum setups)
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reset encoders at the start so we start at (0,0)
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            // --- DRIVE CONTROLS ---
            double motorPowerScaling = gamepad1.left_bumper ? SLOWMO_POWER_SCALE : 1;
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y; // Remember, Y stick is usually reversed
            double turn = gamepad1.right_stick_x;

            // Your motor mixing logic:
            leftFront.setPower((x + y + turn) * motorPowerScaling);
            leftBack.setPower((-x + y + turn) * motorPowerScaling);
            rightBack.setPower((x + y - turn) * motorPowerScaling);
            rightFront.setPower((-x + y - turn) * motorPowerScaling);

            // --- LAUNCHER CONTROLS ---
            if (gamepad1.right_trigger > .50) {
                launcher.open();
            } else {
                launcher.close();
            }
            if (gamepad1.a) launcher.start();
            if (gamepad1.b) launcher.stop();

            if (gamepad1.dpad_up && incrementReady){
                launcher.addPower();
                incrementReady = false;
            }
            if (gamepad1.dpad_down && incrementReady){
                launcher.subPower();
                incrementReady = false;
            }
            if (!gamepad1.dpad_up && !gamepad1.dpad_down){
                incrementReady = true;
            }

            // --- POSITION CALCULATION (The Fix) ---

            // 1. Get positions
            double lf = leftFront.getCurrentPosition();
            double rf = rightFront.getCurrentPosition();
            double lb = leftBack.getCurrentPosition();
            double rb = rightBack.getCurrentPosition();

            // 2. Mix the values to isolate Forward vs Strafe
            // Forward (Y) = Average of all wheels spinning forward
            double ySum = (lf + rf + lb + rb) / 4.0;

            // Strafe (X) = Average of diagonal differences
            // Based on your motor powers: LF(+x), RF(-x), LB(-x), RB(+x)
            double xSum = (lf - rf - lb + rb) / 4.0;

            // 3. Convert to inches
            double xFieldPos = xSum / TICKS_PER_INCH_STRAFE;
            double yFieldPos = ySum / TICKS_PER_INCH_FORWARD;

            // --- TELEMETRY ---
            telemetry.addData("status: " , status);
            telemetry.addData("launcherPower: ", launcher.getPower());

            // Show raw encoder sums (Helpful for tuning)
            telemetry.addData("Raw X Sum", xSum);
            telemetry.addData("Raw Y Sum", ySum);

            // Show final calculated position
            telemetry.addData("X Position (in)", "%.2f", xFieldPos);
            telemetry.addData("Y Position (in)", "%.2f", yFieldPos);

            telemetry.update();
        }
    }
}