#include <ArduinoJson.h>


int ledPin = 6;
volatile int NbTopsFan; //measuring the rising edges of the signal
int Calc;                               
int hallsensor = 26;
boolean ledState=HIGH;//The pin location of the sensor

StaticJsonBuffer<200> jsonBuffer;//reserve memory space for Json
JsonObject& json=jsonBuffer.createObject();

void setup() {
  json["Sensor"]="flow";
  Calc=0;
  json["flow_rate"]=Calc;
  json["units"]="L/Hr";
  ledState=HIGH;
  ledPin = 6;  
  pinMode(ledPin, OUTPUT);
  pinMode(hallsensor, INPUT); //initializes hall sensor pin as an input
  delay(1000);
  Serial1.begin(9600); //This is the setup function where the serial port is initialised,
  Serial.begin(9600);
  attachInterrupt(digitalPinToInterrupt(hallsensor), rpm, RISING); //and the interrupt is attached
}

// the loop() method runs over and over again,
// as long as the Arduino has power
void loop ()    
{
  ledState=!ledState;
  digitalWrite(ledPin, ledState);
  NbTopsFan = 0;   //Set NbTops to 0 ready for calculations
  sei();      //Enables interrupts
    delay(100);
  cli();      //Disable interrupts
  //Serial.println(NbTopsFan);
  Calc = NbTopsFan * (60 / 73); //(Pulse frequency x 60) / 73Q, = flow rate in L/hour 
  //json["flow_rate"]=Calc;
  
  //Serial1.println();
  //Serial1.println(Calc);
  //json.printTo(Serial1);
  //Serial1.printf("{\"Sensor\":\"flow\",\"flow_rate\":%d,\"unit",Calc);

  //Serial1.println();
  //Serial1.printf("\"");
  Serial.printf("{\"Sensor\":\"flow\",\"flow_rate\":%d,\"units\":\"L/Hr\"}\n",Calc);
}
 

 
void rpm ()     //This is the function that the interupt calls 
{ 
  Serial.println("here");
  NbTopsFan++;  //This function measures the rising and falling edge of the hall effect sensors signal 
} 

//this function blinks the led with a delay passed as parameter
void blink_led_pin(int del) 
{
  digitalWrite(ledPin, HIGH);
  delay(del);
  digitalWrite(ledPin, LOW);
  delay(del); 
}



  

