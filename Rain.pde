Drop[] drops = new Drop[30];


void setup() {
  size(800,600,P3D);
  for (int i = 0; i < drops.length; i++){
    int s = int(random(1,10));
    drops[i] = new Drop(s);
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

  } 
}
