const imgContainer = document.querySelector('.img-container'); 
const prevBtn = document.querySelector('.prv');      
const nextBtn = document.querySelector('.nxt');       
const images = document.querySelectorAll('.img-container img');

let currentIndex = 0;
const totalImages = images.length;

function updateSlider() {
    imgContainer.style.transform = `translateX(-${currentIndex * 100}%)`;
    setTimeout(() => {
        currentIndex++;
        if (currentIndex >= totalImages) currentIndex = 0;
        updateSlider();
    },4000);
}

// nextBtn.addEventListener('click', () => {
//     currentIndex++;
//     if (currentIndex >= totalImages) 
//         currentIndex = 0;
//     updateSlider();
// });

// prevBtn.addEventListener('click', () => {
//     currentIndex--;
//     if (currentIndex < 0) 
//         currentIndex = totalImages - 1;
//     updateSlider();
// });

updateSlider();