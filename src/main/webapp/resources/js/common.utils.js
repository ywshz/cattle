(function(factory) {
	if (typeof define === 'function' && define.amd) {
		define('BkUtils', [ 'jquery' ], factory);
	} else {
		factory(jQuery);
	}
}(function($) {

	BkUtils = function(options) {

	};
	$.extend(BkUtils.prototype, {
		formatMoney : function(money,scale) {
			scale = scale > 0 && scale <= 20 ? scale : 2;  
			money = parseFloat((money + "").replace(/[^\d\.-]/g, "")).toFixed(scale) + "";  
		    var l = money.split(".")[0].split("").reverse(), r = money.split(".")[1];  
		    t = "";  
		    for (i = 0; i < l.length; i++) {  
		        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		    }  
		    return t.split("").reverse().join("") + "." + r;  
		},
		reverseMoney : function() {
			return parseFloat(s.replace(/[^\d\.-]/g, ""));  
		}
	});

	return BkUtils;
}));

var BkUtils = new BkUtils();
