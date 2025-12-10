package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.robot.drive;
@Autonomous(name="Purple Auto 1; touching wall", group="Autonomous")
public  class purpleAuto1 extends LinearOpMode {
    @Override

    public void runOpMode() {
        waitForStart();
        drive drive = new drive(hardwareMap);

        if (opModeIsActive() && !isStopRequested()) {
            drive.driveTiles(1f);
        }

        while (opModeIsActive()) {
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
        }

    }
}