garage
======

Multi-threading simulator of the Garage with SIZE(X,Y) with N Entrances and M exits

The Simulation starts with generated set of LicensePlates and randomly creates vehicles to them.
 
1. Generated Vehicles tries to enter the Garage
2. if Vehicle is successful -> ENTER -> assigned to the Parking Places and Garage Counter is incremented ("GarageCounter: A Vehicle has ENTERED)
3. if Vehicle is not accepted -> Message Generated into the Console (example: "GARAGE IS FULL licence= 4 Thread= Thread-0")
4. ManagerService -> can ask for the specific Vehicle based on its license knowledge (example: "RandomParkedVehicle = MotoVehicle licence= 9 garage= true level= 1 slot= 1")
5. ManagerService -> able to print out GarageStatus (example "Garage State: ...")
6. Vehicle are able to leave the Garage (example: "GarageCounter: A Vehicle has gone OUT", "REMOVE THE CAR = CarVehicle licence= 3 garage= true Thread= Thread-3")
7. Simulation is RESTARTED with data of some cars which didn't enter Garage on the 1st trial + Newly Generated Vehicles
8. Everything is printed into the console

IN/OUT process is controlled by the Gates. Every Vehicle must go through.
tasks 2,3,6 - are responsible thread base to the customization

There are included also tests
GarageCounter - important to keep update any time number of the cars inside, extends AtomicInteger
SensorExceptionHandler - when anything happens inside the Thread need to be processed

maven-assembly-plugin plugin allows run GarageSimulator1Test from the command line
#java -jar garage-1.0-SNAPSHOT-jar-with-dependencies.jar
