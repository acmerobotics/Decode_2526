package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class autoTurret {

    // ===================================================================
    //  TUNING VARIABLES
    // ===================================================================

    // 1. TURRET CONSTANTS (Swivel)
    public static double TURRET_TICKS_PER_DEG = 537.7 / 360.0;
    public static double TURRET_kP = 0.006;
    public static double TURRET_kD = 0.0001;

    // 2. FLYWHEEL CONSTANTS (Shooting)
    // Most GoBilda motors max out around 2500-2800 ticks per second
    public static double FLYWHEEL_VELOCITY = 2000; // Ticks per second
    public static String LEFT_FLYWHEEL_NAME = "leftLaunchMotor";
    public static String RIGHT_FLYWHEEL_NAME = "rightLaunchMotor";

    // 3. TARGET POSITION
    public static double GOAL_X = 288.0;
    public static double GOAL_Y = 288.0;

    // ===================================================================
    //  CLASS INTERNALS
    // ===================================================================

    private DcMotorEx turretMotor;
    private DcMotorEx leftFlywheel;
    private DcMotorEx rightFlywheel;

    private double lastTurretError = 0;
    private Telemetry telemetry;

    public autoTurret(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // --- Turret Setup ---
        turretMotor = hardwareMap.get(DcMotorEx.class, "turret");
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // --- Flywheel Setup ---
        leftFlywheel = hardwareMap.get(DcMotorEx.class, LEFT_FLYWHEEL_NAME);
        rightFlywheel = hardwareMap.get(DcMotorEx.class, RIGHT_FLYWHEEL_NAME);

        // Flywheels should run using internal PID for constant velocity
        leftFlywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFlywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // One flywheel usually needs to be reversed so they spin "inward"
        leftFlywheel.setDirection(DcMotorEx.Direction.REVERSE);
    }

    /**
     * @param robotX Current X from Odometry
     * @param robotY Current Y from Odometry
     * @param robotHeading Current Heading in DEGREES
     * @param flywheelsOn Should the flywheels be spinning?
     */
    public void update(double robotX, double robotY, double robotHeading, boolean flywheelsOn) {

        // --- Part 1: Flywheel Control ---
        if (flywheelsOn) {
            leftFlywheel.setVelocity(FLYWHEEL_VELOCITY);
            rightFlywheel.setVelocity(FLYWHEEL_VELOCITY);
        } else {
            leftFlywheel.setVelocity(0);
            rightFlywheel.setVelocity(0);
        }

        // --- Part 2: Turret Aiming ---
        double angleToGoalDeg = Math.toDegrees(Math.atan2(GOAL_Y - robotY, GOAL_X - robotX));
        double relativeTarget = angleToGoalDeg - robotHeading;

        // Normalize to shortest path (-180 to 180)
        while (relativeTarget > 180)  relativeTarget -= 360;
        while (relativeTarget <= -180) relativeTarget += 360;

        runTurretPID(relativeTarget);
    }

    private void runTurretPID(double targetAngle) {
        int targetTicks = (int) (targetAngle * TURRET_TICKS_PER_DEG);
        int currentTicks = turretMotor.getCurrentPosition();

        double error = targetTicks - currentTicks;
        double derivative = error - lastTurretError;
        lastTurretError = error;

        double power = (error * TURRET_kP) + (derivative * TURRET_kD);
        turretMotor.setPower(Range.clip(power, -.75, .75));

        // Telemetry for the Programmer
        telemetry.addData("Turret Error", error);
        telemetry.addData("Flywheel Velocity", leftFlywheel.getVelocity());
    }
}
