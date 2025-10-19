export function initDropdown(buttonSelector, dropdownSelector) {
    const profileBtn = document.querySelector(buttonSelector);
    const profileDropdown = document.querySelector(dropdownSelector);

    profileBtn.addEventListener('click', () => {
        profileDropdown.classList.toggle('show');
    });
}
