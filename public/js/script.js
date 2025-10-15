function loadHeader(){
    fetch('/components/header.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector('header').innerHTML = data;
            const dropdownScript = document.createElement('script');
            dropdownScript.src = '/js/dropdown.js';
            document.body.appendChild(dropdownScript);
        });
}

loadHeader();