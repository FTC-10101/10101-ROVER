package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.ROVERHardware1;

@Autonomous (name = "CraterMineral3")
//@Disabled
public class CraterMineral3 extends LinearOpMode{

    ROVERHardware1 ROVER = new ROVERHardware1();
    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    @Override
    public void runOpMode() throws InterruptedException {

        ROVER.init(hardwareMap);

        ROVER.leftf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ROVER.leftb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ROVER.rightf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ROVER.rightb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ROVER.rightb.setDirection(DcMotor.Direction.REVERSE);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AQMXapn/////AAABmUkIKliu8UhAlsC0hI8AZ/gGzHN693N16RDIV6KvJMcygzolaMYuceUhBHEFuw9JwHBpBSS2OV/BEczUwrgYp9iMPev1ooBl10M89qxmmps38aXL7YycUEe3FTH/0YnvFmPCqUc60Hr0rpAgYqcbmKNfGPF7GCVYsHDGTjUUJAepX5HiX1UUES01Wji5ZArDu9A3oTSMvjSVULFB6wLXRKK8Qk8p/sh3NZsg11NtgjePsUckyvJXTVxTaRwltAWBh9eLZsMwHsZD5pcUSsJwXQFIqGwYE7T7fTMGhPZw/V1bsKTzp7rw5ErPbeBvLUzyHe9DlIyLbJqQ1pIoF9UP+PbQgz3HHf0F7bsKpc3EGa0l";

        vision = new MasterVision(parameters, hardwareMap, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time


        waitForStart();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        while(opModeIsActive()){
            telemetry.addData("goldPosition was", goldPosition);// giving feedback
            telemetry.update();

            switch (goldPosition){ // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("going to the left");
                    telemetry.update();
                    sleep(250);
                    land();
                    ROVER.dump.setPosition(30);
                    moveForward(.5);
                    sleep(700);
                    halt();
                    turnLeft(.5);
                    sleep(550);
                    halt();
                    sleep(500);
                    moveForward(.5);
                    sleep(1300);
                    halt();
                    sleep(500);
                    moveBackwards(.5);
                    sleep(1200);
                    halt();
                    sleep(500);
                    turnRight(.5);
                    sleep(500);
                    halt();
                    rest();
                    break;
                case CENTER:
                    telemetry.addLine("going straight");
                    telemetry.update();
                    sleep(250);
                    land();
                    ROVER.dump.setPosition(30);
                    moveForward(.5);
                    sleep(700);
                    halt();
                    moveForward(.5);
                    sleep(800);
                    halt();
                    sleep(500);
                    moveBackwards(.5);
                    sleep(700);
                    halt();
                    sleep(500);
                    rest();
                    break;
                case RIGHT:
                    telemetry.addLine("going to the right");
                    telemetry.update();
                    sleep(250);
                    land();
                    ROVER.dump.setPosition(30);
                    moveForward(.5);
                    sleep(700);
                    halt();
                    turnRight(.5);
                    sleep(850);
                    halt();
                    sleep(500);
                    moveForward(.5);
                    sleep(1200);
                    halt();
                    sleep(500);
                    moveBackwards(.5);
                    sleep(1025);
                    halt();
                    sleep(500);
                    turnLeft(.5);
                    sleep(950);
                    halt();
                    rest();
                    break;
                case UNKNOWN:
                    telemetry.addLine("Confused, RIP Autonomous");
                    telemetry.update();
                    sleep(250);
                    land();
                    ROVER.dump.setPosition(50);
                    moveForward(.5);
                    sleep(700);
                    halt();
                    moveForward(.5);
                    sleep(800);
                    halt();
                    sleep(500);
                    moveBackwards(.5);
                    sleep(700);
                    halt();
                    sleep(500);
                    rest();
                    break;
            }

            telemetry.update();
        }

        vision.shutdown();
    }
    public void moveForward(double value){
        ROVER.leftf.setPower(-value);
        ROVER.leftb.setPower(-value);
        ROVER.rightf.setPower(-value);
        ROVER.rightb.setPower(-value);
    }

    public void halt (){
        ROVER.leftf.setPower(0);
        ROVER.leftb.setPower(0);
        ROVER.rightf.setPower(0);
        ROVER.rightb.setPower(0);
    }

    public void turnLeft(double value){
        ROVER.leftf.setPower(value);
        ROVER.leftb.setPower(value);
        ROVER.rightf.setPower(-value);
        ROVER.rightb.setPower(-value);
    }

    public void turnRight(double value){
        ROVER.leftf.setPower(-value);
        ROVER.leftb.setPower(-value);
        ROVER.rightf.setPower(value);
        ROVER.rightb.setPower(value);
    }

    public void moveBackwards(double value){
        ROVER.leftf.setPower(value);
        ROVER.leftb.setPower(value);
        ROVER.rightf.setPower(value);
        ROVER.rightb.setPower(value);
    }
    public void land(){
        ROVER.climb.setPower(1);
        sleep(4900);
        ROVER.climb.setPower(0);
        sleep(200);
        ROVER.climb.setPower(-1);
        sleep(250);
        ROVER.climb.setPower(0);
        ROVER.grab.setPosition(-90);
        sleep(1000);
        ROVER.climb.setPower(1);
        sleep(750);
        ROVER.climb.setPower(0);
        sleep(1000);
    }
    //Instead of completely reworking the entire loop to run the same code in each of the 3 conditons,
    //we created a method called "rest()" that is always ran after pushing the gold mineral off.
    public void rest(){
        turnLeft(.75);
        sleep(700);
        halt();
        sleep(500);
        moveForward(.5);
        sleep(1700);
        halt();
        sleep(1000);
        moveForward(.5);
        while (opModeIsActive() && ROVER.rangeSensor.rawUltrasonic() > 45){
            telemetry.addData("raw ultrasonic", ROVER.rangeSensor.rawUltrasonic());
            telemetry.update();
        }
        halt();
        sleep(250);
        turnLeft(.75);
        sleep(325);
        halt();
        sleep(500);
        moveForward(.5);
        sleep(3000);
        halt();
        sleep(1000);
        moveForward(.4);
        while (opModeIsActive() && ROVER.rangeSensor.rawUltrasonic() > 60){
        }
        halt();
        sleep(500);
        ROVER.dump.setPosition(-90);
        sleep(1000);
        turnLeft(.75);
        sleep(50);
        halt();
        moveBackwards(.75);
        sleep(3300);
        halt();
        sleep(250);
        moveBackwards(.25);
        sleep(400);
        halt();
        sleep(50000);
    }

}
