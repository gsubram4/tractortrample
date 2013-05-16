package com.me.mygdxgame.gameobjects.intervalsystem.factories;

import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.intervalsystem.Corn;
import com.me.mygdxgame.gameobjects.intervalsystem.Entity;
import com.me.mygdxgame.gameobjects.intervalsystem.Interval;
import com.me.mygdxgame.gameobjects.intervalsystem.IntervalFactory;

///NOTES: LOWEST = 169, HIGHEST = 402 - height of your collision box
///

/////STEP 1: Copy + Rename file to your new IntervalFactory
/////STEP 2: Muck around below to customize
/////STEP 3: Go to NewIntervalSystem and add Factory in (See IntervalSystem.java)
public class CornFactory implements IntervalFactory {

	@Override
	public Interval createInterval(float xPos, float yPos, SlidingSpeed slidingSpeed) {
		////                         .<----- this is the number of entities you want!
		Entity[] myEntities = new Entity[5];
		////Now you have to create every entity. You can use a loop (for, while, etc) but I'm doing it manually for clarity
		////        	.... <-----Change Corn to the name of the Entity you want           ......... <-------Don't fuck with slidingSpeed for now
		myEntities[0] = Corn.Factory().setPosition(xPos + 0, yPos + 150).setSlidingSpeed(slidingSpeed);
		myEntities[1] = Corn.Factory().setPosition(xPos + 50, yPos + 120).setSlidingSpeed(slidingSpeed);
		myEntities[2] = Corn.Factory().setPosition(xPos + 100, yPos + 90).setSlidingSpeed(slidingSpeed);
		myEntities[3] = Corn.Factory().setPosition(xPos + 150, yPos + 60).setSlidingSpeed(slidingSpeed);
		myEntities[4] = Corn.Factory().setPosition(xPos + 200, yPos + 30).setSlidingSpeed(slidingSpeed);
		////											  ... <--------I slide the X Position by 50 and Y by 30 to create the diagonal effect
		
		////Now just create an interval to package your entities in. 
		////                                          ... <-----Size of the Interval's Collision Box!
		////										  ... <-----For Example 200 + width of Corn + Buffer 
		Interval output  = Interval.newInterval(xPos, 300, slidingSpeed, myEntities);
		
		return output;
	}
	

}
