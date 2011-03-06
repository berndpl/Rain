import ddf.minim.*;

Minim minim;
AudioSample beep;
AudioInput input;

PFont font;

Drop[] drops = new Drop[5];

int cloudwidth = 100;
int tap;
int taptemp = 0;
int tapx;
int tapy;
int bpm = 500;

float gain = 500;
float inputgained;
float in = 0;

void setup() {
  size(800,600,P3D);
  frameRate(30);
  font = createFont("Arial Bold",48);
  textFont(font,24);
  minim = new Minim(this);
  beep = minim.loadSample("tick.wav");
  input = minim.getLineIn(Minim.STEREO, 512);  
  for (int i = 0; i < drops.length; i++){
    int s = int(random(1,10));
    drops[i] = new Drop(cloudwidth, s);
  }
}


void draw() {
  background(255);
  
  fill(200);
  text("FPS "+int(frameRate),20,40);
  text("BPM "+bpm,20,80);
  in = input.mix.level ();  
  text("Input Level: " + (float)in,10,120);   
  inputgained = in * gain;
  text("Gained Level: " + (float)inputgained,10,160);     
  
  smooth();
  strokeWeight(1);
  
  for (int i = 0; i < drops.length; i++){
    drops[i].fall();
  }

}


void keyReleased(){
  if (key == 's'){
      for (int i = 0; i < drops.length; i++){
      drops[i].reset();
  }
  } 
  if (key == 'd'){
    if (taptemp == 0){
      tapx = millis();
      taptemp = 1;
    } else if (taptemp == 1){
      tapy = millis();
      bpm = tapy - tapx;
      taptemp = 0;
      println("BPM: "+bpm);
    }
  }
  if (key == 'k'){
    gain=gain+10;
    text("GAIN "+gain, width/2, height-20);
  }
  if (key == 'j'){
    gain=gain-10;
    text("GAIN "+gain, width/2, height-20);    
  }  
}
