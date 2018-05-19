if(getCookie('userid') === undefined) {
    document.location = 'index.html';
}

window.addEventListener('load', function() {
    document.getElementById('logout_btn').onclick = function() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if(this.readyState == 4 && this.status == 200) {
                    document.location = 'index.html';
            }
        };
        xhttp.open("GET", "/api/user/logout?id=" + getCookie(userid), true);
        xhttp.send();
    };
});