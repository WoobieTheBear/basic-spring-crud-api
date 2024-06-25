import { socketSetup } from "./socket.mjs";

const createNumberGroups = () => {
    const numberGroups = document.querySelectorAll('.number-group');
    numberGroups.forEach( group => {
        const numberInput = group.querySelector('input');
        const less = document.createElement('button');
        less.onclick = (evt) => {
            evt.preventDefault();
            numberInput.stepDown();
        };
        less.classList.add('less');
        less.appendChild( document.createTextNode('-') );
        const more = document.createElement('button');
        more.onclick = (evt) => {
            evt.preventDefault();
            numberInput.stepUp();
        };
        more.classList.add('more');
        more.appendChild( document.createTextNode('+') );
        group.prepend(less);
        group.appendChild(more)
        console.log(group);
    })
}

const initPage = (evt) => {
    createNumberGroups();
    socketSetup();
}

window.onload = initPage;