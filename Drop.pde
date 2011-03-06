class Drop {
 int posx;
 int posy;
 int speed;
 int cloudwidth;

  void Init(){
    int xmin = width/2 - cloudwidth/2;
    int xmax = xmin + cloudwidth;
    line(xmin, 1, xmax, 1);
    posx = int(random(xmin, xmax));
    posy = 0;
    speed = int(random(1,3));
    println("Init"+bpm);

  }

  Drop(int cloudwi, int s){
    Init();
    cloudwidth = cloudwi;
  } 
  
  void fall(){
    if (posy > height){
      hitground();
      Init();
    }
    posy = posy + speed;
    strokeWeight(5);
    point(posx, posy);
  } 
  
  void hitground(){
    strokeWeight(5);
    line(posx-10, posy-5, posx+10, posy-5);
  } 
  
}

