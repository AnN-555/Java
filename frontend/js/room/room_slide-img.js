// Slide 
const imgContainer = document.querySelector('.img-container'); 
const prevBtn = document.querySelector('.prv');      
const nextBtn = document.querySelector('.nxt');       

let currentPage = 0;
let totalPages;
let slideAmountPercent;

const breakpoint = 1100;
function updateSliderSettings() {
  const isSmallScreen = window.innerWidth <= breakpoint;

  if (isSmallScreen) {
    // view < 1100px
    totalPages = 4; 
    slideAmountPercent = 100 / totalPages; 
  } else {
    // view >= 1100px
    totalPages = 2; 
    slideAmountPercent = 100 / totalPages; 
  }

  // Reset về trang đầu khi thay đổi kích thước
currentPage = 0;
imgContainer.style.transform = 'translateX(0)';
updateButtons();
}

// Hàm di chuyển slide
function slideToPage(pageIndex) {
    const translateValue = -pageIndex * slideAmountPercent;
    imgContainer.style.transform = `translateX(${translateValue}%)`;
}

nextBtn.addEventListener('click', () => {
    if (currentPage < totalPages - 1) {
      currentPage++;
      slideToPage(currentPage);
      updateButtons();
    }
});

prevBtn.addEventListener('click', () => {
    if (currentPage > 0) {
      currentPage--;
      slideToPage(currentPage);
      updateButtons();
    }
});

window.addEventListener('resize', updateSliderSettings);

updateSliderSettings();

 