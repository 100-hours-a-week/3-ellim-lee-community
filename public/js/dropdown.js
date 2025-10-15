const profileBtn = document.querySelector('.profile-btn');
const profileDropdown = document.querySelector('.profile-dropdown');

profileBtn.addEventListener('click', () => {
    profileDropdown.classList.toggle('show');
});
