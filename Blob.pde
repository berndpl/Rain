class Blob {                
	int blobX;
	int blobZ;
	int r;
	int rMax;
	int speed = 5;
	
	Blob (int dropSlotX, int dropSlotZ){
		this.blobX = dropSlotX;
		this.blobZ = dropSlotZ;   
		println("BlobX (Init) "+this.blobX);
		println("BlobZ (Init) "+this.blobZ);
		this.r = 1;
		this.rMax = 800;
	}         
	
	void spread(){         
		stroke(255);
		fill(255,255,255,150);
		ellipse(blobX, blobZ, r, r);
		if (r == 10) println("BlobX (Spread) "+this.blobX);
//		r += speed + (r/8) * 0.8;
		r += speed;
	}     
	
	boolean fullSpread(){
		if (r > rMax){
			return true;
		} else {
			return false;
		}
	}
	
}