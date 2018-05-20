if(getCookie('userid') !== undefined) {
    document.location = 'browse.html';
}

window.addEventListener('load', function () {
    document.getElementById("login_form").addEventListener('submit', function(e) {
        e.preventDefault();

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
                if (this.readyState === 4) {
                        if(this.status === 200) {
                                var data = JSON.parse(this.responseText);
                                document.cookie = 'userid=' + data.id;
                                document.cookie = 'username=' + data.username;
                                document.location = 'browse.html';
                        }
                        else {
                                alert('nincs ilyen felh');
                        }
                }
        };
        xhttp.open("POST", "/api/user/login", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send('{"username":"' + this.elements['username'].value + '","password":"' + this.elements['password'].value + '"}');
    });
});
