// document.addEventListener("DOMContentLoaded", function () {
//   const phaseItems = document.querySelectorAll(".phase ul li");

//   if (phaseItems.length > 0) {
//     phaseItems[0].addEventListener("click", function () {
//       window.location.href = "./reservation-page-1.html";
//     });
//   }

//   const roomButtons = document.querySelectorAll(
//     ".room__detail__booking__price button"
//   );

//   roomButtons.forEach((button) => {
//     button.addEventListener("click", function () {
//       const roomElement = this.closest(".room");
//       const roomName = roomElement.querySelector("h2").textContent.trim();
//       const roomImage = roomElement.querySelector("img").getAttribute("src");
//       const roomDescription = roomElement.querySelector("p").textContent.trim();
//       const roomSpecs = roomElement
//         .querySelector(".room__detail__booking span")
//         .textContent.trim();
//       const roomPrice = roomElement
//         .querySelector(".room__detail__booking__price p")
//         .textContent.trim();

//       const params = new URLSearchParams({
//         name: roomName,
//         image: roomImage,
//         description: roomDescription,
//         specs: roomSpecs,
//         price: roomPrice,
//       });

//       window.location.href = `./reservation-page-2.html?${params.toString()}`;
//     });
//   });
//   const navTop = document.getElementById("top-page");
//   if (navTop) {
//     navTop.addEventListener("click", function () {
//       window.location.href = "../homePage.html";
//     });
//   }
// });




document.addEventListener('DOMContentLoaded', () => {
    // Select all room elements
    const rooms = document.querySelectorAll('.room');

    rooms.forEach((room, index) => {
        // Assign a number to each room
        const roomType = index + 1;

        // Attach click event to BOOK NOW button
        const bookButton = room.querySelector('.room__detail__booking__price button');
        bookButton.addEventListener('click', () => {
            // Save only the room number
            localStorage.setItem('selectedRoomType', roomType);

            // Redirect to next page
            window.location.href = 'reservation-page-2.html';
        });
    });
});

