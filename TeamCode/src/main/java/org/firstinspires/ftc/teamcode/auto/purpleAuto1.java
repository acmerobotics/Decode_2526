package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// import org.firstinspires.ftc.teamcode.robot.Drive;
import org.firstinspires.ftc.teamcode.robot.oldDrive;

@Autonomous(name="Purple Auto 1; touching wall", group="Autonomous")
public  class purpleAuto1 extends LinearOpMode {
    @Override

    public void runOpMode() {
        waitForStart();
        oldDrive drive = new oldDrive(hardwareMap);

        if (opModeIsActive() && !isStopRequested()) {
            oldDrive.driveTiles(1f);
        }

    }
}