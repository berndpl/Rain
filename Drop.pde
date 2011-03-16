class Drop {
		
    int dropSlotX;
		int posY;    
		int bpmDrop;
		int speed;
		
		Drop (int dropSlotX) {
    this.dropSlotX = dropSlotX;
		this.posY = 80; //cloud (60) + margin (20)
//		this.bpmDrop = bpm;
		this.bpmDrop = int(random(bpm, 400)); ;
    setSpeed(); //sets speed
		}                      
		
		void fall(){
				setSpeed();         		 			
		  	posY +=speed;
			//Draw
				strokeWeight(3);
				stroke(255);
				point(dropSlotX,posY);
//				shape(dropShape,dropSlotX,posY); 
				smooth();				
				shape(dropShape,dropSlotX-10,posY-20); 				
								
 

		}                             
		
		boolean hitGround(){
			if (posY >= height){
				rect(this.dropSlotX-20,height-10,40,10);
				posY = 0;
				return true;
			} else {
				return false;
			}

		}		          
		
		void setSpeed(){
			int getSpeed = int((height/frameRate * (bpmDrop/60))/20);
			if (getSpeed > 0) {
			 speed = int(getSpeed);
			}
		}

}


