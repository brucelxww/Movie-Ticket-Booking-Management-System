# W16R5_GROUP2 Assignment2 Project

## How-to guide

Please refer to the `HOWTO.md` file for a guide of using the system.

## collaboration rules

1. Pull before push.

2. Don't leave the build broken, if you can't fix it revert it back to it's last working state.

3. If you can't fix a bug in 30 minutes, revert back to the previous working build.

4. Test in your local environment before pushing.

## How to run the code
Generate the jar file of this project using the commands below in the root directory of the project. The commands are:

    gradle shadowJar
    java -jar .\build\libs\SOFT2412_A2-1.0-SNAPSHOT-all.jar

## How to test the code

    gradle clean
    gradle test
