Running the program...

Initialize OpenBCI:
1. Open the OpenBCI GUI on a computer and start the EEG data stream.

2. In the OSC networking quadrant, set stream1 data type to band power and click start.

NodeJS:
1. After starting the OpenBCI system, open a terminal window.

2. Navigate to the directory containing the "index.js" file. 
   - (In this case, .../Code/nodejs/)

3. Run the following command:
   $ node index.js

4. The command prompt should print "Listening..." if the data stream is being heard and uploaded up successfully.

Android:
1. Initialize the android application either on a smartphone or a virtual device emulator.

2. The app will start to pull data from firebase and when an event occurs, should prompt the user with a stimulus.

