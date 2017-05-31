#define BLYNK_DEBUG

/*************************************************************
  Download latest Blynk library here:
    https://github.com/blynkkk/blynk-library/releases/latest

  Blynk is a platform with iOS and Android apps to control
  Arduino, Raspberry Pi and the likes over the Internet.
  You can easily build graphic interfaces for all your
  projects by simply dragging and dropping widgets.

    Downloads, docs, tutorials: http://www.blynk.cc
    Sketch generator:           http://examples.blynk.cc
    Blynk community:            http://community.blynk.cc
    Follow us:                  http://www.fb.com/blynkapp
                                http://twitter.com/blynk_app

  Blynk library is licensed under MIT license
  This example code is in public domain.

 *************************************************************

  You’ll need:
   - Blynk App (download from AppStore or Google Play)
   - Arduino Uno board
   - Decide how to connect to Blynk
     (USB, Ethernet, Wi-Fi, Bluetooth, ...)

  There is a bunch of great example sketches included to show you how to get
  started. Think of them as LEGO bricks  and combine them as you wish.
  For example, take the Ethernet Shield sketch and combine it with the
  Servo example, or choose a USB sketch and add a code from SendData
  example.
 *************************************************************/

/* Comment this out to disable prints and save space */


#include <SPI.h>
#include <WiFi.h>
#include <BlynkSimpleWifi.h>
#include <MP3.h>
#include <ChainableLED.h>


#define BLYNK_PRINT Serial
#define NUM_LEDS 5
#define folder_party 30
#define folder_romantic 10
#define folder_study 20
// You should get Auth Token in the Blynk App.
// Go to the Project Settings (nut icon).
char ssid[] = "Livebox-EC53";
char pass[] = "48F74EC1E8A522FF14842CE666";


char auth[] = "bce1fe5158ff43cca67e8aaf6a5f14c2";

// Your WiFi credentials.
// Set password to "" for open networks.

//char ssid[] = "julien";
//char pass[] = "coustillas";





// Define the B-value of the thermistor.
// This value is a property of the thermistor used in the Grove - Temperature Sensor,
// and used to convert from the analog value it measures and a temperature value.
const int B = 3975;


//Green LEdD
const int pinLed    = 5;
// Define the pin to which the temperature sensor is connected.
const int pinTemp = A0;
// Warmer
const int relayPinWarm =  4;
//Cooler
const int relayPinCold =  3;
//Ligthsensor
const int ligthsensor = 2;
//movementsensor
const int inputmovement = 8;
/**
 * Value Of the actual temp of the room
 */
float TempValue;
float* wantedtemp(0);

int pirState = LOW;             // we start, assuming no motion detected
int val = 0;                    // variable for reading the pin status

/**
 * Global variable to activate/deactivate threads
 */
uint8_t PARTY ;
uint8_t TEMP_CONTROL ;
ChainableLED leds(6, 7, NUM_LEDS);

/**
 * Handle the Blynk Music Player
 */
BLYNK_WRITE(V5)
{
  String action = param.asStr();

  if (action == "play") {
   
       PlayResume();
   
  }
  if (action == "stop") {
    PlayPause();
  }
  if (action == "next") {
    PlayNext();
  }
  if (action == "prev") {
    PlayPrevious();
  }

  
  Serial.print(action);
  Serial.println();
}
/*
 * Read the value of the V0 PIN and write periodicly in it
 */
BLYNK_READ(V0) // Widget in the app READs Virtal Pin V0 with the certain frequency
{
  // This command writes Arduino's uptime in seconds to Virtual Pin V5
  TempValue =  Get_temp();
  Blynk.virtualWrite(0, TempValue);
}

/**
 * Get Information from the Atmosphère Menu
 */
BLYNK_WRITE(V1){
  setAtmosphere(param.asInt());
}

/**
 * Get the state of the temparature parameter. 
 */
BLYNK_WRITE(V2){
  Serial.println(param.asInt());
  *wantedtemp = (float)param.asInt();
  Serial.println(*wantedtemp  );
}

/**
 * Handle the action button of the blynk turns the warmer and the led to sgnal it
 */
BLYNK_WRITE(V3){

  if (param.asInt()){
    digitalWrite(relayPinWarm, HIGH);
  }
  else{
    digitalWrite(relayPinWarm, LOW);
  }
  
}
//BLYNK_WRITE(V3){
//  pthread_t controletemparatureThread;
//  if (param.asInt()){
//    TEMP_CONTROL = 1;
//     analogWrite(pinLed, 255);
//     int p = pthread_create(&controletemparatureThread, NULL, temperaturecontrol, (void*)0);
//      if (p) {
//        Serial.println("Error : Unable to create the temperature control Thread");
//      }
//      else{
//        pthread_detach(controletemparatureThread);
//      }
//  }
//  else{
//    analogWrite(pinLed, 0);
//    TEMP_CONTROL = 0;
//  }
//
//}
/**
 * Get the value of the actual temperature wth the grove sensor
 */
float Get_temp(){
   int val = analogRead(pinTemp);

    // Determine the current resistance of the thermistor based on the sensor value.
    float resistance = (float)(1023-val)*10000/val;

    // Calculate the temperature based on the resistance value.
    float temperature = 1/(log(resistance/10000)/B+1/298.15)-273.15;

    // Print the temperature to the serial console.
    //Serial.println(temperature);

    return temperature;
}

/**
 * Send an alert to the alert widget in blynk
 */
void sendAlert(String alert){
//  Blynk.virtualWrite(1, alert);
  Blynk.notify(alert);
}

void sendAlert(){
//  Blynk.virtualWrite(1, alert);
  Blynk.notify("Intrusion");
}

/**
 * Set the leds to the party mode
 */
void *partyAtm(void* Chainableled_ptr) {
    {
        ChainableLED * leds =  (ChainableLED *)Chainableled_ptr;
//        unsigned long currentMillis = millis();
//        unsigned long previousMillis = 0;        // will store last time LED was updated
//
//        // constants won't change :
//        const long interval = 100;  
        
        while (PARTY){
          

//          if (currentMillis - previousMillis >= interval) {
//            // save the last time you blinked the LED
//            previousMillis = currentMillis;
            (*leds).init();
            for (byte i=0; i<NUM_LEDS; i++){
               (*leds).setColorRGB(i,(rand() % 155+100),(rand() % 155+100),(rand() % 155+20));
             delay(150);
              
            }
//          }
        }
    }
}

/**
 * Set the leds to the study mode
 */
void studyAtm(ChainableLED L) {
    L.init();
    Serial.println("study leds");
    for (int i=0; i<NUM_LEDS; i++){

        L.setColorRGB(i,255,255,255);
   

  }
  

}

/**
 * Set the leds to the romantic mode
 */
void romanticAtm(ChainableLED L) {
  L.init();
  Serial.println("Romantic leds");
    L.setColorRGB(0,255,255,255);
    L.setColorRGB(1,204,0,0);
    L.setColorRGB(2,255,255,255);
    L.setColorRGB(3,204,0,0);
    L.setColorRGB(4,255,255,255);

}

void shutleds(ChainableLED L){
    L.init();

}
void setAtmosphere(int atm){
  
  switch (atm){
     pthread_t ledThread;
    case 1:
      Blynk.setProperty(V5, "label", "");
      PARTY = 0;
      delay(300);
      shutleds(leds);
      
      PlayPause();  
      Blynk.setProperty(V5, "isOnPlay", "false");
    break;
    
    case 2: //STUDY MODE
      Blynk.setProperty(V5, "label", "Travail");
      SpecifyfolderPlay(folder_study,1);
      PARTY = 0;
      delay(200);
      studyAtm(leds);
      
      Blynk.setProperty(V5, "isOnPlay", "true");
    break;

    case 3: // ROMANTIC MODE
      SpecifyfolderPlay(folder_romantic,1);
       PARTY = 0;
      delay(200);
      romanticAtm(leds);
      Blynk.setProperty(V5, "label", "romantique");
       Blynk.setProperty(V5, "isOnPlay", "true");
      PARTY = 0;
    break;

    case 4: // PARTY MODE
     {
      Blynk.setProperty(V5, "label", "soirée");
      SpecifyfolderPlay(folder_party,1);
      PARTY = 1;
      Blynk.setProperty(V5, "isOnPlay", "true");
      int p = pthread_create(&ledThread, NULL, partyAtm, &leds);
      if (p) {
        Serial.println("Error : Unable to create the led Thread");
      }
      else{
        pthread_detach(ledThread);
      }
     }
      
    break;
    default :
      PARTY = 0;
      PlayPause();
      shutleds(leds);
      Serial.println("erreur sur l'atmosphere : " + atm);
    break;
  }
}

void checkmovement(){
  val = digitalRead(inputmovement);  // read input value
  if (val == 0) {            // check if the input is HIGH

    if (pirState == LOW) {
      // we have just turned on
      Serial.println("Motion detected!");
      // We only want to print on the output change, not state
      sendAlert(); //send alert to Blynkserver
    }
  } else {

    if (pirState == HIGH){
      // we have just turned of
      Serial.println("Motion ended!");
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }
}
void* temperaturecontrol(void * x) {
  
  float seuil = *wantedtemp * 5/100; // 5 % of gigue
  while(TEMP_CONTROL){
      float actualtemp = Get_temp();
      if (  actualtemp < *wantedtemp - seuil )
      {
        digitalWrite(relayPinWarm, HIGH);
        digitalWrite(relayPinCold, LOW);
       }
      else if ( actualtemp > *wantedtemp + seuil)
      {
        digitalWrite(relayPinWarm, LOW);
        digitalWrite(relayPinCold, HIGH);
        
      }
      else if( *wantedtemp - seuil < actualtemp < *wantedtemp + seuil ) 
      {
        digitalWrite(relayPinWarm, LOW);
        digitalWrite(relayPinCold, LOW);
      }
      else{
        digitalWrite(relayPinWarm, LOW);
        digitalWrite(relayPinCold, LOW);
      }

  }
}

BlynkTimer timer;
void setup()
{
  
    Serial1.begin(9600);
   // Debug console
   Serial.begin(9600); 
   Serial.println("plop");

     Blynk.begin(auth, ssid, pass);

    SelectPlayerDevice(0x02);       // Select SD card as the player device.
    SetVolume(0x1E);                // Set the volume, the range is 0x00 to 0x1E.

    pinMode(pinLed, OUTPUT);
    pinMode(relayPinWarm, OUTPUT);
    pinMode(relayPinCold, OUTPUT);
    leds.init();
    TempValue = Get_temp();
    PARTY = 0;
    TEMP_CONTROL = 0;
    timer.setInterval(100L, checkmovement);
    pinMode(inputmovement, INPUT);
}


void loop()
{
  if (!Blynk.connected()){ // IF the device is deconnect from the blynk server, we can reconnect with some times
  
    Serial.println("Got deconnected from the server, trying to reconnect");
    Blynk.connect();
  }
  Blynk.run();
  timer.run();
 
 

}

