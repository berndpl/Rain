class Drop {
 int posx;
 int posy;
 int speed;

  void Init(){
    posx = int(random(width));
    posy = 0;
    speed = int(random(5,10));

  }

  Drop(int s){
    Init();
  } 
  
  void fall(){
    if (posy > height){
      Init();
    }
    posy = posy + speed;
    strokeWeight(5);
    point(posx, posy);
  } 
  
}

