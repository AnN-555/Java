document.addEventListener('DOMContentLoaded', function(){
    const hamburger = document.getElementById('hamburger');
    const close = document.getElementById('close');
    const menuList = document.querySelector('.menu-list')
    const body = document.body;

    //Hiển thị hamburger menu
    hamburger.addEventListener('click', function(){
        hamburger.style.display = 'none';
        close.style.display = 'block';
        menuList.classList.add('menu-open');
        body.style.overflow = 'hidden';
    })

    //Ẩn hamburger menu
    close.addEventListener('click', function(){
        close.style.display = 'none';
        hamburger.style.display = '';
        menuList.classList.remove('menu-open');
        body.style.overflow = 'auto';
    })

    //Đóng menu và reset state
    function closeMenu() {
        close.style.display = 'none';
        hamburger.style.display = '';
        menuList.classList.remove('menu-open');
        body.style.overflow = 'auto';
    }

    // Tự động đóng menu khi màn hình lớn
    const breakpoint = 990; //CSS breakpoint
    window.addEventListener('resize', function(){
        if (window.innerWidth > breakpoint) {
            // Nếu menu đang mở, tự động đóng
            if (menuList.classList.contains('menu-open')) {
                closeMenu();
            }
        }
    })
})