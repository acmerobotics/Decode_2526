package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.robot.drive;
@Autonomous(name="Purple auto 2; touching goal", group="Autonomous")
public  class purpleAuto2 extends LinearOpMode {
    @Override

    public void runOpMode() {

        waitForStart();
        drive drive = new drive(hardwareMap);

        if (opModeIsActive() && !isStopRequested()) {
            drive.driveTiles(1f);
            drive.setRotateDegrees(180);
        }

        while (opModeIsActive()) {
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
        }

    }
}