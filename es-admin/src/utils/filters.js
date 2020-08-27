/**
 * 部分显示过滤器
 * @param {过滤对象的值} value 
 * @param {值最大处理长度，默认20} maxlength 
 * @param {后缀，默认...} suffix 
 */
const displayFilter = function(value,maxlength,suffix) {
    if (!value) return ''
      if(!maxlength) maxlength = 20;
      if(suffix==null) suffix = '...';
      if(value.length>maxlength){
          return value.substring(0,maxlength)+suffix;
      }
      return value
}
/**
 * 年龄过滤器
 * @param {过滤对象的值} value 
 */
const ageFilter = function(value) {
    if(!value) return ''
	if(value.length!=10) return ''
	var birthday=new Date(value.replace(/-/g, "\/")); 
	var now = new Date(); 
	var age = now.getFullYear()-birthday.getFullYear()-((now.getMonth()<birthday.getMonth()|| now.getMonth()==birthday.getMonth() && now.getDate()<birthday.getDate())?1:0);
	return age;
}

/**
 * 键值过滤器
 * 给一个键值对，从列表中找出对应对象其他键的值
 * @param {已给的键} key 
 * @param {已给键的值} val
 * @param {列表} list 
 * @param {其他键} key2 
 */
const keyvalFilter = function(value,key,list,key2) {
    let res = null;
	for(let i=0;i<list.length;i++){
		if(value==list[i][key+'']){
			if(key2!=null && key2!=''){
				return list[i][key2+''];
			}
			return list[i];
		}
	}
	return '';
}

export {
    displayFilter,
	ageFilter,
	keyvalFilter
}