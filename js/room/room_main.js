// Active 
const activeItem = document.querySelectorAll('.header__main-nav .contents li')
const activeItem2 = document.querySelectorAll('.side__bar .content li')

activeItem.forEach((item, index) => {
    item.addEventListener(('click'), () => {
        activeItem.forEach((item) => {
            item.classList.remove('active')
        })
        
        activeItem[index].classList.add('active')
    })
})

activeItem2.forEach((item, index) => {
    item.addEventListener(('click'), () => {
        activeItem2.forEach((item) => {
            item.classList.remove('active')
        })
        
        activeItem2[index].classList.add('active')
    })
})




