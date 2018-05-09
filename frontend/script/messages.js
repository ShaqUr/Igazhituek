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

    // Get matches from server

    var matches = document.getElementById('matches');

    var fragment = document.createDocumentFragment();

    for(var i = 0; i < 10; ++i) {
        var li = document.createElement('li');

        var input = document.createElement('input');
        input.type = 'radio';
        input.name = 'match';
        input.id = 'match_selector_' + i;
        input.dataset.matchId = i;
        if(i === 0) {
            input.checked = true;
            getMessages.call(input);
        }
        input.addEventListener('change', getMessages);
        li.appendChild(input);

        var label = document.createElement('label');
        label.setAttribute('for', 'match_selector_' + i);

        var img = document.createElement('img');
        img.src = 'https://avatars1.githubusercontent.com/u/22823703?s=40&v=4';
        label.appendChild(img);

        var h1 = document.createElement('h1');
        h1.appendChild(document.createTextNode('Name'));
        label.appendChild(h1);

        li.appendChild(label);

        fragment.appendChild(li);
    }

    matches.appendChild(fragment);
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