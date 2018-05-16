/*var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
	if(this.readyState == 4 && this.status == 200) {
		if(this.responseText === 'false') {
                        alert('isloggedin falset valaszolt rohadna ki a bele');
			document.location = 'index.html';
		}
	}
};
alert('elkuldtem isloggedinnek: ' + getCookie('userid'));
xhttp.open("GET", "http://localhost:8080/api/user/isloggedin?id=" + getCookie('userid'), true);
xhttp.send();*/

if(getCookie('userid') === undefined) {
    document.location = 'index.html';
}

function getCookie(name) {
  var value = "; " + document.cookie;
  var parts = value.split("; " + name + "=");
  if (parts.length == 2) return parts.pop().split(";").shift();
}
