
class BlackSocket {
    constructor(url, field, output) {
        this.url = url;
        this.field = field;
        this.output = output;
        this.socket = null;
    }
    connect() {
        if (!this.isUp()) {
            this.socket = new WebSocket(this.url);
            this.socket.onopen = () => {
                this.show('Connected to server');
            };
            this.socket.onmessage = (response) => {
                this.show(response.data);
            };
        }
    }
    disconnect() {
        if (this.isUp()) {
            this.socket.close();
            this.socket = null;
            this.show('Disconnected from server');
        }
    }
    send() {
        this.socket.send(this.field.value);
        this.field.value = '';
    }
    show(message) {
        const textNode = document.createTextNode(message);
        const paragraph = document.createElement('p');
        paragraph.appendChild(textNode);
        this.output.appendChild(paragraph);
    }
    isUp() {
        return this.socket != null;
    }
}

export const socketSetup = () => {
    if (document.querySelector('#socket_output')) {
        const bs = new BlackSocket('ws://localhost:8837/black/chat-socket', message_field, socket_output);
        connectbtn.onclick = bs.connect.bind(bs);
        disconnectbtn.onclick = bs.disconnect.bind(bs);
        sendbtn.onclick = bs.send.bind(bs);
    }
}
