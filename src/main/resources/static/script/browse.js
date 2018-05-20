window.onload = function () {
    getUsers();

    document.getElementById('dislike').onclick = function() {
        var cards = document.getElementById('cards').querySelectorAll('.card:not(.left):not(.right)');
        var lastCard = cards[cards.length - 1];

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            /*if (this.readyState == 4) {
                console.log(this.status, this.responseText);
            }*/
        };
        xhttp.open("POST", "/api/user/dislike", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send('userID=' + getCookie('userid') + '&likedId=' + lastCard.dataset.id);

        if(cards.length == 1) {
            hideButtons();
        }

        lastCard.className += " left";
    };
    document.getElementById('like').onclick = function() {
        var cards = document.getElementById('cards').querySelectorAll('.card:not(.left):not(.right)');
        var lastCard = cards[cards.length - 1];

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
                /*if (this.readyState == 4) {
                    console.log(this.status, this.responseText);
                }*/
        };
        xhttp.open("POST", "/api/user/like", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send('userID=' + getCookie('userid') + '&likedId=' + lastCard.dataset.id);

        if(cards.length == 1) {
            hideButtons();
        }

        lastCard.className += " right";
    };
};

function hideButtons() {
    document.getElementById('dislike').className = 'hidden';
    document.getElementById('like').className = 'hidden';
}

function getUsers() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var users = JSON.parse(this.responseText);

            var root = document.createDocumentFragment();
            for(let user of users) {
                let li = document.createElement('li');
                li.className = 'card';
                li.dataset.id = user.id;

                li.appendChild(document.createElement('img'));
                li.lastChild.src = user.base64;//user.image;

                li.appendChild(document.createElement('div'));
                li.lastChild.appendChild(document.createElement('h1'));
                li.lastChild.lastChild.appendChild(document.createTextNode(user.username));
                li.lastChild.appendChild(document.createElement('h2'));
                li.lastChild.lastChild.appendChild(document.createTextNode(user.age));
                li.lastChild.appendChild(document.createElement('h3'));
                li.lastChild.lastChild.appendChild(document.createTextNode(user.denomination));

                li.addEventListener('transitionend', function() {
                    this.parentNode.removeChild(this);
                }, { once: true });

                root.appendChild(li);
            }

            document.getElementById('cards').appendChild(root);
        }
    };
    xhttp.open("GET", "/api/user/notliked?userID=" + getCookie('userid'), true);
    xhttp.send();
}
