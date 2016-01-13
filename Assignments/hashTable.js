function main(){
	var inp,val
	var TABMAX = 5;
	var dist=[];
	for(var i=0; i<TABMAX; i++){
		dist[i] = 0;
	}

	var ht=makeHashTab(TABMAX);

}

function collectHashes(tab,arr){
	var nwords=1000;
	for(vari=0; i<nwords; i++){
		arr[tab.hash(genString(10))]++;
	}
	return vals;
}

function makeHashTab(tabMax){
	var htobj = {
		table: [],
		max: tabMax,
		nelts: 0,

		insert: function(v){},
		remove: function(v){},
		find: function(v){},
		size: function() {
			return this.nelts;
		},

		hash: function(key){
			var hval = 0;
			for(var i=0; i<key.length; i++){
				hval = 37*hval + key[i].charCodeAt();
			}
			hval %= this.max;
			if(hval<0){
				hval += this.max;
				return hval;
			}
		}
	}	


}