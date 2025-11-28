const hamburBtn = document.querySelectorAll('.header__wrapper .hamber-btn');
const closeBtn = document.querySelectorAll('.side__bar .close-btn');
const sideBar = document.querySelector('.side__bar');
const overlay = document.querySelector('.overlay');

hamburBtn.forEach(btn => {
    btn.addEventListener('click', function() {
        sideBar.style.display = 'flex';
        overlay.style.display = 'block';
        document.body.style.overflow = 'hidden';
    })
});

closeBtn.forEach(btn => {
    btn.addEventListener('click', function() {
        sideBar.style.display = 'none';
        overlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    })
});