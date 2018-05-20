window.addEventListener('load', function() {
    document.getElementById('form').addEventListener('submit', function(e) {
        e.preventDefault();

        var elements = this.elements;

        var reader = new FileReader();
        reader.readAsDataURL(elements['picture'].files[0]);
        reader.onload = function () {
            var obj = {
                username: elements['username'].value,
                birth: elements['birth'].value,
                email: elements['email'].value,
                password: elements['password'].value,
                picture: reader.result,
                felekezet: elements['felekezet'].value,
                where: elements['where'].value,
                sex: elements['sex'].value,
                about: elements['about'].value
            };

            var request = new XMLHttpRequest();
            request.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    console.log(this.responseText);
                    var data = JSON.parse(this.responseText);
                    document.cookie = 'userid=' + data.id;
                    document.cookie = 'username=' + data.username;
                    document.location = 'browse.html';
                }
            };
            request.open("POST", "/api/user/register");
            request.setRequestHeader("Content-type", "application/json");
            request.send(JSON.stringify(obj));
        };
    });
});
