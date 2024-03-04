console.log('script tag loaded');

const initPage = (evt) => {
    console.log('page loaded');
    createNumberGroups();
}

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

window.onload = initPage;