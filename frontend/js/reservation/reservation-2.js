
const API_BASE = "http://localhost:9090/api/";

// const apiEndpoints = {
//     roomtype: API_BASE + "roomtype",
//     rooms: API_BASE + "rooms",
//     customers: API_BASE + "customers",
//     booking: API_BASE + "booking",
//     booking_details: API_BASE + "booking_details",
//     payments: API_BASE + "payments"
// };

// let selectedRow = null;

// document.querySelector(".search-button").onclick = async () => {
//     const table = "rooms";
//     try {
//         const res = await fetch(apiEndpoints[table]);
//         const data = await res.json();
//     } catch (err) {
//         alert("Error fetching data: " + err);
//     }
// };


document.getElementById('searchBtn').addEventListener('click', function () {
    // Get values from HTML inputs
    const checkinDate = document.getElementById('date-input-fromd').value; // dd/mm/yyyy
    const checkoutDate = document.getElementById('date-input-tod').value;  // dd/mm/yyyy
    // const rooms = document.getElementById('select-rooms').value;
    // const adults = document.getElementById('select-adults').value;
    // const children = document.getElementById('select-children').value;

    // Validate input
    if (!checkinDate || !checkoutDate) {
        alert("Please select both check-in and check-out dates.");
        return;
    }

    // Convert dd/mm/yyyy to ISO format for LocalDateTime.parse()
    function convertToISO(dateStr) {
        const parts = dateStr.split('/');
        return `${parts[2]}-${parts[1]}-${parts[0]}T00:00:00`; // yyyy-MM-ddTHH:mm:ss
    }

    const roomType_Id = localStorage.getItem('selectedRoomType');
    console.log("type chosen:" + roomType_Id );

    // Build JSON payload
    const payload = {
        checkinDate: convertToISO(checkinDate),
        checkoutDate: convertToISO(checkoutDate),
        requestedRoomType: roomType_Id 
    };

    console.log("Sending payload:", payload);

    // Send POST request to Spring Boot
    const api_available = API_BASE + 'booking/available';
    fetch(api_available, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Available Rooms:', data);
            renderRooms(data, payload);
        })
        .catch(error => console.error('Error:', error));
});


function renderRooms(rooms, payload) {
    const container = document.getElementById('tableContainer');
    container.innerHTML = ''; // Clear previous results

    if (rooms.length === 0) {
        container.innerHTML = '<p>No rooms available for selected criteria.</p>';
        return;
    }

    let cardsHTML = '<div class="room-grid">';
    rooms.forEach(room => {
        cardsHTML += `
            <div class="room-card" data-roomid="${room.id}" data-roomnumber="${room.roomNumber}">
                <h3>Room ${room.roomNumber}</h3>
                <p>Type: ${room.roomType.typeName}</p>
                <p>Price: ${room.roomType.price}</p>
                <button class="select-btn">Book Now</button>
            </div>
        `;
    });
    cardsHTML += '</div>';
    container.innerHTML = cardsHTML;

    // Add click event for each button
    document.querySelectorAll('.room-card .select-btn').forEach(button => {
        button.addEventListener('click', function () {
            const roomCard = this.closest('.room-card');
            const roomNumber = roomCard.dataset.roomnumber;

            // Build payload with roomNumber
            const bookingPayload = {
                roomNumber: roomNumber,
                checkinDate: payload.checkinDate,
                checkoutDate: payload.checkoutDate
            };

            console.log("Booking Payload:", bookingPayload);

            // Save to localStorage
            localStorage.setItem('bookingPayload', JSON.stringify(bookingPayload));

            // Redirect to next page
            window.location.href = 'reservation-page-3.html';
        });
    });

}
// Render available rooms in a table
// function renderRooms(rooms, payload) {
//     const container = document.getElementById('tableContainer');
//     container.innerHTML = ''; // Clear previous results

//     if (rooms.length === 0) {
//         container.innerHTML = '<p>No rooms available for selected criteria.</p>';
//         return;
//     }

//     let tableHTML = '<table class="room-table"><thead><tr><th>Room Number</th><th>Type</th><th>Price</th><th>Select</th></tr></thead><tbody>';
//     rooms.forEach(room => {
//         tableHTML += `<tr>
//             <td>${room.roomNumber}</td>
//             <td>${room.roomType.typeName}</td>
//             <td>${room.roomType.price}</td>
//             <td><button class="select-btn" data-roomid="${room.id}">Select</button></td>
//         </tr>`;
//     });
//     tableHTML += '</tbody></table>';
//     container.innerHTML = tableHTML;

//     // Add click event for select buttons
//     document.querySelectorAll('.select-btn').forEach(button => {
//         button.addEventListener('click', function () {
//             // const roomId = this.dataset.roomid;
//             const bookingPayload = {
//                 checkinDate: payload.checkinDate,
//                 checkoutDate: payload.checkoutDate,
//                 requestedRoomType: payload.roomType_Id
//             };

//             console.log("Booking payload:", bookingPayload);

//             // Send booking confirmation to Spring Boot
//             fetch('/api/rooms/select', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json' },
//                 body: JSON.stringify(bookingPayload)
//             })
//             .then(response => response.text())
//             .then(msg => alert(msg))
//             .catch(error => console.error('Error:', error));
//         });
//     });
// }



// function renderRooms(rooms, payload) {
//     const container = document.getElementById('tableContainer');
//     container.innerHTML = ''; // Clear previous results

//     if (rooms.length === 0) {
//         container.innerHTML = '<p>No rooms available for selected criteria.</p>';
//         return;
//     }

//     let cardsHTML = '<div class="room-grid">';
//     rooms.forEach(room => {
//         cardsHTML += `
//             <div class="room-card" data-roomid="${room.id}">
//                 <h3>Room ${room.roomNumber}</h3>
//                 <p>Type: ${room.roomType.typeName}</p>
//                 <p>Price: ${room.roomType.price}</p>
//                 <button class="select-btn">Book Now</button>
//             </div>
//         `;
//     });
//     cardsHTML += '</div>';
//     container.innerHTML = cardsHTML;


//         // Add click event for each card or button
//         document.querySelectorAll('.room-card .select-btn').forEach(button => {
//             button.addEventListener('click', function () {
//                 const roomId = this.closest('.room-card').dataset.roomType.id;

//                 // Pass data to next page via query params or localStorage
//                 const bookingPayload = {
//                     roomNumber: this.closest('.room-card').dataset.roomNumber,
//                     checkinDate: payload.checkinDate,
//                     checkoutDate: payload.checkoutDate
//                 };

//                 console.log(bookingPayload);

//                 // Option 1: Use localStorage
//                 localStorage.setItem('bookingPayload', JSON.stringify(bookingPayload));

//                 // Redirect to next page
//                 window.location.href = 'reservation-page-3.html';
//             });
//         });
//     }