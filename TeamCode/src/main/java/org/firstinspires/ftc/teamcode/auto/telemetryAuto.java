package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.drive;

@Autonomous(name="Telemetry Auto", group="Autonomous")
public  class telemetryAuto extends LinearOpMode {
    @Override

    public void runOpMode() {

        waitForStart();
        drive drive = new drive(hardwareMap);

        while (opModeIsActive()) {
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
        }

    }
}