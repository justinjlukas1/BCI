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

function writeData(data) {
    database.ref('/').set({
        BandPower: data
    })
    console.log("Listening...");
}

// Handles OSC Data
var oscServer = new osc.Server(12345, '127.0.0.1');

// FFT -> [ChannelD, 1hz - 125hz]
oscServer.on("/openbci", function (data, rinfo) {
      switch (data[1]) {
        case 1:
          writeData(data)
          //console.log("Channel 1: ", data[11]);
          break;
        default:
      }
});
