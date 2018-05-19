if(getCookie('userid') === undefined) {
    document.location = 'index.html';
}

window.addEventListener('load', function() {
    document.getElementById('logout_btn').onclick = function() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if(this.readyState == 4 && this.status == 200) {
                document.cookie = 'userid=;Path=/;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
                document.location = 'index.html';
            }
        };
        xhttp.open("GET", "/api/user/logout?id=" + getCookie('userid'), true);
        xhttp.send();
    };
});