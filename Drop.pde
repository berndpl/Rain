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

    int msframeRate = int(frameRate);  

    speed = ((height * msframeRate)/bpm)*int(random(1,3))/4;
    println ("Speed :"+speed);  

    //    speed = int(random(1,3));

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
    point(posx + inputgained, posy);
  } 

  void hitground(){
    beep.trigger();
    strokeWeight(5);
    line(posx-10, posy-5, posx+10, posy-5);
  } 
  
  void reset(){
    posy = 0;
  }

}


