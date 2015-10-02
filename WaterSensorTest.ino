
int ledPin = 6;
volatile int NbTopsFan; //measuring the rising edges of the signal
int Calc;                               
int hallsensor = 2;
boolean ledState=HIGH;//The pin location of the sensor

void setup() {
  Calc=0;
  ledState=HIGH;
  ledPin = 6;
  pinMode(ledPin, OUTPUT);
  pinMode(hallsensor, INPUT); //initializes digital pin 2 as an input
  Serial.begin(9600); //This is the setup function where the serial port is initialised,
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
    delay(1000);
  cli();      //Disable interrupts
  Calc = (NbTopsFan * 60 / 73); //(Pulse frequency x 60) / 73Q, = flow rate in L/hour 
  Serial.print (Calc, DEC); //Prints the number calculated above
  Serial.print (" L/hour\r\n"); //Prints "L/hour" and returns a  new line
}
 

 
void rpm ()     //This is the function that the interupt calls 
{ 
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



  

