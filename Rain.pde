Drop[] drops = new Drop[3];
int cloudwidth = 100;
int tap;
int taptemp = 0;
int tapx;
int tapy;
int bpm;

void setup() {
  size(800,600,P3D);

  for (int i = 0; i < drops.length; i++){
    int s = int(random(1,10));
    drops[i] = new Drop(cloudwidth, s);
  }
}


void draw() {
  background(255);
  smooth();
  strokeWeight(1);
  
  for (int i = 0; i < drops.length; i++){
    drops[i].fall();
  }

}


void keyReleased(){

  if (key == 'd'){
    if (taptemp == 0){
      tapx = millis();
      println("x: "+tapx);      
      taptemp = 1;
    } else if (taptemp == 1){
      tapy = millis();
      bpm = tapy - tapx;
      taptemp = 0;
      println("y: "+tapy);      
      println("BPM: "+bpm);
    }
  } 
}
