class Cloud {    
	int cloudWidth = 90;
	int dropsAmount = 10;  
	int cloudStart;
  int cloudEnd;  
	int dropSlotY;	

	Cloud () {		
		this.cloudStart = (width/2) - (cloudWidth/2);
    this.cloudEnd = this.cloudStart + cloudWidth;  
		this.dropSlotY = cloudHeight + 100; 
		//Draw Cloud
    line(cloudStart, 10, cloudEnd, 10);   
	}

	void createDrops(int dropsAmount){
		this.dropsAmount = dropsAmount; 
		//Create Drops		
		for (int i = 0; i < this.dropsAmount; i++){                    
			int dropSlotX = int(random(cloudStart, cloudEnd));			
			drops.add(new Drop(dropSlotX,dropSlotY));
	  }		       		

	}      
	
	void drawCloud(int distort){
		//Draw Cloud    
		noStroke();
		shapeMode(CENTER);                                 
		shape(cloudShape,cloudStart+(cloudWidth/2),dropSlotY);
	}
	
	void rain(String type){        
		if (type == "drops"){
	    //Draw Cloud
			drawCloud(dropsAmount);
			//Draw Drops      
			for (int i = 0; i < drops.size(); i++){
				Drop drop = (Drop) drops.get(i);
				drop.fall();                   
					if (drop.hitGround()){ 
						blobs.add(new Blob(drop.dropSlotX,drop.dropSlotZ));
						drops.remove(i);
					} 			
				}                 
		} else if (type == "blobs") {
			for (int z = 0; z < blobs.size(); z++){
				Blob blob = (Blob) blobs.get(z);
				blob.spread(); 
				if (blob.fullSpread()){
					blobs.remove(z); 
				}
			}                           
		}
	}

}
