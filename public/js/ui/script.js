import { initDropdown } from './dropdown.js';


function loadHeader(){
    return fetch('/components/header.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('header').outerHTML = data;
        });
} // Code Editor가 async를 추천하는데 왜 그런지는 잘 모르겠음

// async function loadHeader(){
//     const response = await fetch('/components/header.html');
//     const data = await response.text();
//     document.querySelector('header').outerHTML = data;
// }

document.addEventListener('DOMContentLoaded', () => {
    loadHeader().
        then(() => {
            initDropdown('.profile-btn', '.profile-dropdown');
        });
});
