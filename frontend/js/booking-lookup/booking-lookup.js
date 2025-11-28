function lookupBooking() {
    const bookingId = document.getElementById('bookingId').value.trim();
    const resultSection = document.getElementById('resultSection');
    const searchSection = document.getElementById('searchSection');
    const errorMessage = document.getElementById('errorMessage');

    if (!bookingId) {
        alert('Vui lòng nhập mã đặt phòng!');
        return;
    }

    // Gọi API Spring Boot (sẽ thay bằng link thật)
    fetch(`/api/booking/${bookingId}`)
        .then(response => response.json())
        .then(data => {
            if (data && data.id) {
                // Hiển thị kết quả
                document.getElementById('displayId').textContent = data.id;
                document.getElementById('customerName').textContent = data.fullName;
                document.getElementById('customerEmail').textContent = data.email;
                document.getElementById('checkIn').textContent = formatDate(data.checkIn);
                document.getElementById('checkOut').textContent = formatDate(data.checkOut);
                document.getElementById('nights').textContent = data.nights + ' đêm';
                document.getElementById('totalAmount').textContent = formatCurrency(data.totalAmount);
                document.getElementById('roomImage').src = data.roomImage || '/images/rooms/default.jpg';
                document.getElementById('roomType').textContent = data.roomType;

                searchSection.style.display = 'none';
                resultSection.style.display = 'block';
                errorMessage.style.display = 'none';
            } else {
                showError();
            }
        })
        .catch(err => {
            console.error(err);
            showError();
        });
}

function showError() {
    document.getElementById('errorMessage').style.display = 'block';
    document.getElementById('resultSection').style.display = 'none';
}

function formatDate(dateStr) {
    const date = new Date(dateStr);
    const options = { day: 'numeric', month: 'long', year: 'numeric' };
    return date.toLocaleDateString('vi-VN', options);
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
}

// Enter để tìm
document.getElementById('bookingId').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        lookupBooking();
    }
});