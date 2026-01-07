package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.robot.Drive;

@Autonomous(name="Telemetry Auto", group="Autonomous")
public  class telemetryAuto extends LinearOpMode {
    @Override

    public void runOpMode() {

        waitForStart();
        Drive drive = new Drive(hardwareMap);

        while (opModeIsActive()) {
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.addLine(drive.forEachMotorString(m -> String.valueOf(((DcMotorEx) m).getTargetPositionTolerance())));
            telemetry.addLine(drive.forEachMotorString(m -> String.valueOf(((DcMotorEx) m).getVelocity())));
            telemetry.addLine(drive.forEachMotorString(m -> ((DcMotorEx) m).getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).toString()));
            telemetry.update();
        }

    }
}