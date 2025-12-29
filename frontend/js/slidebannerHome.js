fetch(`http://localhost:8080/api/booking/${bookingId}`)
    .then(response => {
        if (!response.ok) throw new Error("Booking not found");
        return response.json();
    })
    .then(data => {
        console.log("Fetched data:", data);
        if (data && data.id) {
            document.getElementById('displayId').textContent = data.id;
            document.getElementById('customerName').textContent = data.fullName;
            document.getElementById('customerEmail').textContent = data.email;
            document.getElementById('checkIn').textContent = formatDate(data.checkIn);
            document.getElementById('checkOut').textContent = formatDate(data.checkOut);
            document.getElementById('nights').textContent = data.nights + ' đêm';
            document.getElementById('totalAmount').textContent = formatCurrency(data.totalAmount);
            
            // Fix room image path
            const imgPath = data.roomImage.startsWith('http') 
                ? data.roomImage 
                : '/' + data.roomImage.replace(/^(\.\.\/)+/, ''); 
            document.getElementById('roomImage').src = imgPath;
            
            document.getElementById('roomType').textContent = data.roomType;

            document.getElementById('searchSection').style.display = 'none';
            document.getElementById('resultSection').style.display = 'block';
            document.getElementById('errorMessage').style.display = 'none';
        } else {
            showError();
        }
    })
    .catch(err => {
        console.error(err);
        showError();
    });
