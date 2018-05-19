window.addEventListener('load', function() {
    getMatches();

    document.getElementById('new_message').addEventListener('submit', submitMessage);
});

function submitMessage(e) {
    e.preventDefault();

    // Send new message to server, get every message in reponse

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
                input.id = 'match_selector_' + data.id;
                input.dataset.matchId = data.id;
                if(i === 0) {
                    input.checked = true;
                    //getMessages.call(input);
                }
                //input.addEventListener('change', getMessages);
                li.appendChild(input);
        
                var label = document.createElement('label');
                label.setAttribute('for', 'match_selector_' + data.id);
        
                var img = document.createElement('img');
                img.src = 'https://avatars1.githubusercontent.com/u/22823703?s=40&v=4';
                label.appendChild(img);
        
                var h1 = document.createElement('h1');
                h1.appendChild(document.createTextNode(data.username));
                label.appendChild(h1);
        
                li.appendChild(label);
        
                fragment.appendChild(li);
            }
        
            matches.appendChild(fragment);
        }
    };
    xhttp.open("POST", "/api/user/matches", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send('userID=' + getCookie('userid'));
}

function getMessages() {
    if(this.checked) {
        // get messages

        // displayMessages(messages)
    }
}

function displayMessages(messages) {
    var messages = document.getElementById('messages');
    messages.innerHTML = '';

    for(var i = 0; i < 50; ++i) {
        //disaply em
    }
}