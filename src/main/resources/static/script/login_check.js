if(getCookie('userid') === undefined) {
    document.location = 'index.html';
}

window.addEventListener('load', function() {
    document.getElementById('logout_btn').onclick = function() {
        document.cookie = 'userid=;Path=/;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        document.location = 'index.html';
    };
});