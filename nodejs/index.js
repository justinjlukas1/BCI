var express = require('express');
var app = express();
var sock = null;
var osc = require('node-osc');
var firebase = require('firebase');

//Initialize Firebase
var config = {
  apiKey: "945db729b00c33d7a4c8553e12a1a6d0e7aabcce",
  authDomain: "bci491-ART.firebaseapp.com",
  databaseURL: "https://bci491-art.firebaseio.com/",
};
firebase.initializeApp(config);
var database = firebase.database();

function writeData(data,wave) {
    database.ref(wave + '/').set({
        BandPower: data
    })
}

// Handles OSC Data
var oscServer = new osc.Server(12345, '127.0.0.1');

// FFT -> [ChannelD, 1hz - 125hz]
oscServer.on("/openbci", function (data, rinfo) {
      switch (data[1]) {
        case 1:
            console.log("Listening...")
            var sum = data[2]+data[3]+data[4]+data[5]+data[6]
            writeData(String(data[2]),"Delta")
            writeData(String(data[3]),"Theta")
            writeData(String(data[4]),"Alpha")
            writeData(String(data[5]),"Beta")
            writeData(String(data[6]),"Gamma")
          break;
        default:
      }
});
