var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
	if(this.readyState == 4 && this.status == 200) {
		if(this.responseText === 'false') {
			document.location = 'index.html';
		}
	}
};
xhttp.open("GET", "http://157.181.164.88:8080/api/user/isloggedin", true);
xhttp.send();
