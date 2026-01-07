package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.robot.Drive;
@Autonomous(name="Purple Auto 1; touching wall", group="Autonomous")
public  class purpleAuto1 extends LinearOpMode {
    @Override

    public void runOpMode() {
        waitForStart();
        Drive drive = new Drive(hardwareMap);

        if (opModeIsActive() && !isStopRequested()) {
            drive.driveTiles(1f);
        }

        while (opModeIsActive()) {
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.addLine(drive.forEachMotorString(m -> String.valueOf(((DcMotorEx) m).getTargetPositionTolerance())));
            telemetry.addLine(drive.forEachMotorString(m -> String.valueOf(((DcMotorEx) m).getVelocity())));
            telemetry.addLine(drive.forEachMotorString(m -> ((DcMotorEx) m).getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).toString()));
            telemetry.update();
            telemetry.update();
        }

    }
}