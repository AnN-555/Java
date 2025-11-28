

document.addEventListener('DOMContentLoaded', () => {

    const bookingIdInput = document.getElementById('bookingId');
    const searchBtn = document.querySelector('.search-btn');
    const resultSection = document.getElementById('resultSection');
    const searchSection = document.getElementById('searchSection');
    const errorMessage = document.getElementById('errorMessage');

    function lookupBooking() {
        const bookingId = bookingIdInput.value.trim();
        if (!bookingId) {
            alert('Please enter your booking ID!');
            return;
        }

        const apiUrl = `http://localhost:8080/api/booking/${bookingId}`;

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Booking not found');
                }
                return response.json();
            })
            .then(data => {
                document.getElementById('displayId').textContent = data.id;
                document.getElementById('customerName').textContent = data.fullName;
                document.getElementById('customerEmail').textContent = data.email;
                document.getElementById('checkIn').textContent = formatDate(data.checkIn);
                document.getElementById('checkOut').textContent = formatDate(data.checkOut);
                document.getElementById('nights').textContent = data.nights + ' nights';
                document.getElementById('totalAmount').textContent = formatCurrency(data.totalAmount);
                document.getElementById('roomImage').src = data.roomImage || '/images/rooms/default.jpg';
                document.getElementById('roomType').textContent = data.roomType;

                searchSection.style.display = 'none';
                resultSection.style.display = 'block';
                errorMessage.style.display = 'none';
            })
            .catch(err => {
                console.error(err);
                showError();
            });
    }

    function showError() {
        errorMessage.textContent = 'Booking not found. Please check your booking ID.';
        errorMessage.style.display = 'block';
        resultSection.style.display = 'none';
    }

    function formatDate(dateStr) {
        const date = new Date(dateStr);
        const options = { day: 'numeric', month: 'long', year: 'numeric' };
        return date.toLocaleDateString('en-US', options);
    }

    function formatCurrency(amount) {
        return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'VND' }).format(amount);
    }

    searchBtn.addEventListener('click', lookupBooking);

    
    bookingIdInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            lookupBooking();
        }
    });

});
