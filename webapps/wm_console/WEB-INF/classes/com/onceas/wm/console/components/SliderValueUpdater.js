function SliderValueUpdater(k,tjrequester) {
	this.key = k;
	this.tjsonrequester = tjrequester;
	this.selfcount = 0;
};

SliderValueUpdater.prototype.updateValue = function updateValue(newvalue,valueComp){
	  valueComp.innerHTML=newvalue;
//	   valueComp.value = newvalue;
	 
	  this.sliderValue = newvalue;
	  
	  this.selfcount++;
	  
	  var myself = this;
	  var delay = this.delayUpdater(myself,this.selfcount);
      setTimeout(delay,1000);		 //delay 1 second     
}

SliderValueUpdater.prototype.delayUpdater =	function delayUpdater(s,num){
	return function(){
		if(num == s.selfcount){
			var requester =  s.tjsonrequester;
			requester.invoke({'value':s.sliderValue,'key':s.key},function(){});
		}
	}
}