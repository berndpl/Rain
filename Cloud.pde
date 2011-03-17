class Cloud {    
	int cloudWidth = 90;
	int dropsAmount = 10;  
	int cloudStart;
	int cloudHeight;	
  int cloudEnd;

	Cloud () {		
		this.cloudStart = width/2 - cloudWidth/2;
    this.cloudEnd = cloudStart + cloudWidth;  
		this.cloudHeight = 20;
		//Draw Cloud
    line(cloudStart, 1, cloudEnd, 1);   

	}

	void createDrops(int dropsAmount){
		this.dropsAmount = dropsAmount; 
		//Create Drops		
		for (int i = 0; i < this.dropsAmount; i++){                    
			int dropSlotX = int(random(cloudStart, cloudEnd));			
			println("dropsAmount: " + dropsAmount);
//			int dropProbability = int(random(0, 100)); 
//			if (dropProbability == 0)  {		
			drops.add(new Drop(dropSlotX));
//			}
	  }		       		

	}      
	
	void drawCloud(int distort){
		//Draw Cloud    
		distort = 0;
//		if (distort < 5) distort = 0;
		noSmooth();            
		noStroke();
		/*
		rect(distort+this.cloudStart+62,this.cloudHeight+19,39,39);
		rect(distort+this.cloudStart+43,this.cloudHeight+7,39,39); 	
		rect(distort+this.cloudStart+43,this.cloudHeight+28,39,39); 	
		rect(distort+this.cloudStart+5,this.cloudHeight+28,39,39); 	
		rect(distort+this.cloudStart+5,this.cloudHeight+0,39,39); 	
		rect(distort+this.cloudStart+(-15),this.cloudHeight+19,39,39);
		  */
		shape(cloudShape,cloudStart-250,cloudHeight-365);
	}
	
	void rain(){        
    drawCloud(dropsAmount);
		//Draw Drops      
		for (int i = 0; i < drops.size()-1; i++){
			Drop drop = (Drop) drops.get(i);
			drop.fall();                   
			if (drop.hitGround()){
				drops.remove(i); 
			}
		}
	}

}
