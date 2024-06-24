let ws;

const setConnected = (connected) => {
    if (connected) {
        console.log('connected');
    } else {
        console.log('disconnected');
    }
}

const connect = () => {
    let url = 'ws://localhost:8837/black/chat-socket';
    ws = new WebSocket(url);
    ws.onopen = () => {
        showOutput('Connected to server');
    };
    ws.onmessage = (data) => {
        showOutput(data.data);
    };
    setConnected(true);
}

const disconnect = () => {
    if (ws != null) {
        ws.close();
        showOutput('Disconnected from server');
    }
    setConnected(false);
}            

const send = () => {
    ws.send(messagefield.value);
    messagefield.value = '';
}

const showOutput = (message) => {
    const textNode = document.createTextNode(message);
    const paragraph = document.createElement('p');
    paragraph.appendChild(textNode);
    socket_output.appendChild(paragraph);
}


export const socketSetup = () => {
    console.debug('socket setup started');
    if (socket_output) {
        connectbtn.onclick = connect;
        disconnectbtn.onclick = disconnect;
        sendbtn.onclick = send;
    }
}
