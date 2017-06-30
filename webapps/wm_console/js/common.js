function showsubcontent(source,mark){
		 
		 if(mark=='fairshare'){
		 	
		 	enabled('fair1');
		 	enabled('fair2');
		 	enabled('fair3');
		 	enabled('fair4');
		 	
		 	disabled('response1');
		 	disabled('response2');
		 	disabled('response3');
		 	disabled('response4');
		 
		 }
		 else if(mark=='responsetime'){
		 	enabled('response1');
		 	enabled('response2');
		 	enabled('response3');
		 	enabled('response4');
		 	
		 	disabled('fair1');
		 	disabled('fair2');
		 	disabled('fair3');
		 	disabled('fair4');
		 
		 }
		 
	}
	
	function enabled(cid){
			document.getElementById(cid).style.display= "";
		}
	function disabled(cid){
			document.getElementById(cid).style.display= "none";
		}
		
	/*function allSubmit(){
	
			document.getElementById('wmForm').submit();
	}*/
//	function checksubmit(){}
	function checksubmit(){
		//��Ч����֤
		var valid = true;
		/*
		if(!document.getElementById('delmax').value&&checkName(document.getElementById('maxName').value!=null){
			//var tmp1 = checkName(document.getElementById('maxName'),document.getElementById('maxNamelabel'));
			var tmp2 = checkValue(document.getElementById('maxValue'),document.getElementById('maxNamelabel'));
			tmp1 = true;
			if(!(tmp1&&tmp2))valid = false;
		}
		if(!document.getElementById('delmin').value&&checkName(document.getElementById('minName').value!=null){
			//var tmp1 = checkName(document.getElementById('minName'),document.getElementById('maxNamelabel'));
			var tmp2 = checkValue(document.getElementById('minValue'),document.getElementById('maxNamelabel'));
			tmp1 = true;
			if(!(tmp1&&tmp2))valid = false;
		}
		if(!document.getElementById('delcap').value&&checkName(document.getElementById('capName').value!=null){
			//var tmp1 = checkName(document.getElementById('capName'),document.getElementById('maxNamelabel'));
			var tmp2 = checkValue(document.getElementById('capValue'),document.getElementById('maxNamelabel'));
			tmp1 = true;
			if(!(tmp1&&tmp2))valid = false;
		}
		if(!document.getElementById('delfair').value&&checkName(document.getElementById('fairName').value!=null){
			//var tmp1 = checkName(document.getElementById('fairName'),document.getElementById('maxNamelabel'));
			var tmp2 = checkValue(document.getElementById('fairValue'),document.getElementById('maxNamelabel'));
			tmp1 = true;
			if(!(tmp1&&tmp2))valid = false;
		}
		if(!document.getElementById('delgoal').value&&checkName(document.getElementById('goalName').value!=null){
			tmp1 = true;
			//var tmp1 = checkName(document.getElementById('goalName'),document.getElementById('maxNamelabel'));
			var tmp2 = checkValue(document.getElementById('goalValue'),document.getElementById('maxNamelabel'));
			
			if(!(tmp1&&tmp2))valid = false;
		}
		*/
		if(document.getElementById('delmax')==null||!document.getElementById('delmax').checked){
			if(document.getElementById('maxName')&&document.getElementById('maxName').value!=null)
			var tmp1 = checkValue(document.getElementById('maxValue'),document.getElementById('maxLabel'));
		}
//		document.getElementById('maxLabel').innerHTML = "ttttttttttttttttttttestttttttttttttttttttttt";
		if(document.getElementById('delmin')==null||!document.getElementById('delmin').checked){
			if(document.getElementById('minName')&&document.getElementById('minName').value!=null)
			var tmp2 = checkValue(document.getElementById('minValue'),document.getElementById('minLabel'));
		}
		
		if(document.getElementById('delcap')==null||!document.getElementById('delcap').checked){
			if(document.getElementById('capName')&&document.getElementById('capName').value!=null)
			var tmp3 = checkValue(document.getElementById('capValue'),document.getElementById('capLabel'));	
		}
		
		if(document.getElementById('fair1Name')&&document.getElementById('fair1Name').value!=null)
		var tmp4 = checkValue(document.getElementById('fairValue1'),document.getElementById('fair1Label'));
		
		if(document.getElementById('goal1Name')&&document.getElementById('goal1Name').value!=null)
		var tmp5 = checkValue(document.getElementById('goalValue1'),document.getElementById('goal1Label'));
		
		if(document.getElementById('delfair')==null||!document.getElementById('delfair').value){
			if(document.getElementById('fair2Name')&&document.getElementById('fair2Name').value!=null)
			var tmp6 = checkValue(document.getElementById('fairValue2'),document.getElementById('fair2Label'));
		}
		
		if(document.getElementById('delgoal')==null||!document.getElementById('delgoal').checked){
			if(document.getElementById('goal2Name')&&document.getElementById('goal2Name').value!=null)
			var tmp7 = checkValue(document.getElementById('goalValue2'),document.getElementById('goal2Label'));
		}
			
		//��Լ����֤
		var testmax;
		var testmin;
		var tmp = document.getElementById('delmax');
		
		if(document.getElementById('delmax')!=null)
		testmax = document.getElementById('delmax').checked;
		if(document.getElementById('delmin')!=null)
		testmin = document.getElementById('delmin').checked;
		if(!(testmax||testmin)){
			if(document.getElementById('maxName')&&document.getElementById('minName')&&(document.getElementById('maxName').value!=null)&&(document.getElementById('minName').value!=null)){
			maxvalue = document.getElementById('maxValue').value;
			minvalue = document.getElementById('minValue').value;
			var test1 = maxvalue-minvalue;
			if(test1<0){
				alert('最大线程约束不能小于最小线程约束，请重新输入');
				document.getElementById('maxValue').value = null;
				document.getElementById('minValue').value = null;
				return;
			}
			}
		}
		//
		if(valid==true){
		document.getElementById('wmForm').submit();
		}
	}
	
	function checkName(name,label){
		if(name.value==null){
			return false;
		}
		return true;
	}
	
	function checkValue(value,label){
		var sreg = '^([1-9]\d*)';
		if(value==null)return true;
		var sval = value.value;
		
		var reg = new RegExp(sreg,'i');
		
		if(!reg.test(sval)){
			label.innerHTML = "请输入大于0的整数";
			return false;
		}
		return true;
	}