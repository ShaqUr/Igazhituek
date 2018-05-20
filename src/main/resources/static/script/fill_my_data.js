window.addEventListener('load', function() {
    var username = getCookie('username');
    document.querySelector('#my_name').innerHTML = username;
});