window.addEventListener('load', function() {
    var username = getCookie('username');
    document.querySelector('#my_data img').src = getCookie('picture');
    document.querySelector('#my_data h1').innerHTML = username;
});