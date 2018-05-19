window.addEventListener('load', function() {
    getMatches();

    document.getElementById('new_message').addEventListener('submit', submitMessage);
});

function submitMessage(e) {
    e.preventDefault();

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {       
        if (this.readyState == 4 && this.status == 200) {
            displayMessages(this.responseText);
        }
    };
    xhttp.open("POST", "/api/chat/messages", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("sender=" + getCookie('userid') + '&receiver=' + document.querySelector('#matches input:checked').matchId + '&message=' + this.elements['message'].innerHTML);

    //displayMessages(messages);
}

function getMatches() {
    var matches = document.getElementById('matches');

    var fragment = document.createDocumentFragment();

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {       
        if (this.readyState == 4 && this.status == 200) {
            var data = JSON.parse(this.responseText);

            for(let user of data) {
                var li = document.createElement('li');
        
                var input = document.createElement('input');
                input.type = 'radio';
                input.name = 'match';
                input.id = 'match_selector_' + user.id;
                input.dataset.matchId = user.id;
                input.addEventListener('change', getMessages);
                li.appendChild(input);
        
                var label = document.createElement('label');
                label.setAttribute('for', 'match_selector_' + user.id);
        
                var img = document.createElement('img');
                img.src = 'https://avatars1.githubusercontent.com/u/22823703?s=40&v=4';
                label.appendChild(img);
        
                var h1 = document.createElement('h1');
                h1.appendChild(document.createTextNode(user.username));
                label.appendChild(h1);
        
                li.appendChild(label);
        
                fragment.appendChild(li);
            }
        
            matches.appendChild(fragment);
        }
    };
    xhttp.open("GET", "/api/user/matches?userID=" + getCookie('userid'), true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send();
}

function getMessages() {
    if(this.checked) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {       
            if (this.readyState == 4 && this.status == 200) {
                displayMessages(this.responseText);
            }
        };
        xhttp.open("GET", "/api/chat/messages?sender=" + getCookie('userid') + '&receiver=' + this.dataset.matchId, true);
        xhttp.send();
    }
}

function displayMessages(data) {
    var messages = document.getElementById('messages');
    messages.innerHTML = '';
    var fragment = document.createDocumentFragment();
    var li = null;
    var prevSender;
    var data = JSON.parse(data);
    if(data.length > 0) {
        for(let message of data) {
            if(prevSender !== undefined && prevSender != message.sender) {
                fragment.appendChild(li);
                li = null;
            }
            prevSender = message.sender;
            if(li === null) {
                li = document.createElement('li');
                if(message.sender == getCookie('userid')) {
                    li.className = 'sent';
                }
                else {
                    li.className = 'received';
                }
            }

            var p = document.createElement('p');
            p.appendChild(document.createTextNode(message.message));

            li.appendChild(p);
        }
        fragment.appendChild(li);
        messages.appendChild(fragment);
    }
}