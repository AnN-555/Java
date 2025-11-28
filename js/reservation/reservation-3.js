
//tạo booking_id random
function generateBookingId() {

  return 'BKG-' + Date.now() + '-' + Math.floor(Math.random() * 1000);

}
document.addEventListener("DOMContentLoaded", function () {
  // ======= Navigation phase clicks =======
  const phaseItems = document.querySelectorAll(".phase ul li");
  if (phaseItems.length > 0) {
    phaseItems[0].addEventListener("click", () => {
      window.location.href = "./reservation-page-1.html";
    });
  }
  if (phaseItems.length > 2) {
    phaseItems[2].addEventListener("click", () => {
      window.location.href = "./reservation-page-2.html";
    });
  }

  // ======= Lấy dữ liệu stay từ localStorage =======
  const checkinRaw = localStorage.getItem("checkinDate") || "26/09/2025";
  const checkoutRaw = localStorage.getItem("checkoutDate") || "27/09/2025";


  const parseLocalDate = (dateStr) => {
    const p = dateStr.split("/");
    return new Date(p[2], p[1] - 1, p[0]);
  };

  const checkinDate = parseLocalDate(checkinRaw);
  const checkoutDate = parseLocalDate(checkoutRaw);

  const roomName = localStorage.getItem("stay_roomName") || "Deluxe Room";
  const roomImage =
    localStorage.getItem("stay_roomImage") ||
    "../../images/reservation/reservation-page-4/deluxe-room.jpg";
  const guests = localStorage.getItem("stay_guests") || "1 Room - 2 Guests";
  const roomId = localStorage.getItem("stay_roomId") || "101";
  const location =
    localStorage.getItem("stay_location") ||
    "Thu Duc Ward, Ho Chi Minh City, Vietnam";

  const formatDate = (date) =>
    date.toLocaleDateString("en-GB", {
      day: "numeric",
      month: "short",
      year: "numeric",
    });

  const nights = Math.round((checkoutDate - checkinDate) / (1000 * 60 * 60 * 24));
  const datesText = `${formatDate(checkinDate)} - ${formatDate(
    checkoutDate
  )} (${nights} Night${nights > 1 ? "s" : ""})`;

  const rooms = parseInt(localStorage.getItem('stay_rooms')) || 1; // số lượng phòng

  const priceRaw = localStorage.getItem('stay_price') || '500';
  const pricePerNight = parseFloat(priceRaw);

  const basePriceValue = pricePerNight * nights * rooms; // nhân với số phòng
  const taxValue = +(basePriceValue * 0.10).toFixed(2);
  const serviceValue = +(basePriceValue * 0.05).toFixed(2);
  const totalValue = +(basePriceValue + taxValue + serviceValue).toFixed(2);

  const formatUSD = (value) =>
    `$${value.toLocaleString('en-US', { minimumFractionDigits: 2 })}`;

  const basePrice = formatUSD(basePriceValue);
  const tax = formatUSD(taxValue);
  const serviceFee = formatUSD(serviceValue);
  const total = formatUSD(totalValue);


  // ======= Render stay info =======
  const details = document.querySelectorAll(".stay_box .stay-box-detail");
  if (details[0])
    details[0].innerHTML = `<img src="../../images/reservation/reservation-page-4/location.png" style="height: 20px"> ${location}`;
  if (details[1])
    details[1].innerHTML = `<img src="../../images/reservation/reservation-page-4/calender.png" style="height: 20px"> ${datesText}`;
  if (details[2])
    details[2].innerHTML = `<img src="../../images/reservation/reservation-page-4/room.png" style="height: 20px"> ${roomName}`;
  if (details[3])
    details[3].innerHTML = `<img src="../../images/reservation/reservation-page-4/people.png" style="height: 20px"> ${guests}`;

  const imgEl = document.querySelector(".stay-box-image img");
  if (imgEl) {
    imgEl.src = roomImage;
    imgEl.alt = roomName;
  }

  const spans = document.querySelectorAll(".stay_box span");
  if (spans[0]) spans[0].textContent = `exc. taxes & fees ${basePrice}`;
  if (spans[1])
    spans[1].innerHTML = `Taxes & fees<br><small>VAT 10%: ${tax} | Service 5%: ${serviceFee}</small>`;
  if (spans[2]) spans[2].textContent = `Total cost ${total}`;

  // ======= Lấy thông tin khách từ localStorage =======
  const bookingRaw = localStorage.getItem("tempBooking");
  let booking = null;
  try {
    booking = JSON.parse(bookingRaw);
  } catch (e) {
    console.error("Cannot parse tempBooking");
  }
  if (!booking) console.warn("No booking data found!");

  const title = booking?.customer?.title || "";
  const firstName = booking?.customer?.firstName || "";
  const lastName = booking?.customer?.lastName || "";
  const phone = booking?.customer?.phone || "";
  const email = booking?.customer?.email || "";
  const country = booking?.customer?.country || "";

  const fullName = `${title.toUpperCase()} ${firstName} ${lastName}`.trim();

  // ======= Render customer info =======
  const infoDivs = document.querySelectorAll(".detail_box .information");
  if (infoDivs[0])
    infoDivs[0].innerHTML = `<img src="../../images/reservation/reservation-page-5/information.png" style="height:20px">${fullName || "Not provided"}`;
  if (infoDivs[1])
    infoDivs[1].innerHTML = `<img src="../../images/reservation/reservation-page-5/telephone.png" style="height:20px">${phone || "Not provided"}`;
  if (infoDivs[2])
    infoDivs[2].innerHTML = `<img src="../../images/reservation/reservation-page-5/email.png" style="height:20px">${email || "Not provided"}`;
  if (infoDivs[3])
    infoDivs[3].innerHTML = `<img src="../../images/reservation/reservation-page-5/location.png" style="height:20px">${country}`;

  // ======= QR Pay modal + send info to backend + email =======
  const modal = document.getElementById("qrModal");
  const qrBtn = document.getElementById("qrBtn");
  const closeBtn = document.querySelector(".close");
  if (closeBtn) {
    closeBtn.addEventListener("click", () => {
      modal.style.display = "none";
    });
  }

  // Click ra ngoài modal-content tắt modal
  window.addEventListener("click", (event) => {
    if (event.target === modal) {
      modal.style.display = "none";
    }
  });


  if (qrBtn && modal) {
    qrBtn.addEventListener("click", function () {
      modal.style.display = "block";

      // Generate booking ID
      const bookingId = generateBookingId();
      localStorage.setItem("booking_id", bookingId);

      // Lấy dữ liệu stay + khách
      const checkin = localStorage.getItem("checkinDate") || "26/09/2025";
      const checkout = localStorage.getItem("checkoutDate") || "27/09/2025";
      const roomsSelected = parseInt(localStorage.getItem("stay_rooms")) || 1;
      const guests = localStorage.getItem("stay_guests") || "1 Room - 2 Guests";
      const roomName = localStorage.getItem("stay_roomName") || "Deluxe Room";
      const roomView = localStorage.getItem("stay_roomView") || "City View";
      const pricePerNight = parseFloat(localStorage.getItem("stay_price")) || 500;
      const nights = parseInt(localStorage.getItem("stay_nights")) || 1;

      const basePrice = pricePerNight * nights * roomsSelected;
      const tax = +(basePrice * 0.10).toFixed(2);
      const service = +(basePrice * 0.05).toFixed(2);
      const totalPrice = +(basePrice + tax + service).toFixed(2);

      const bookingRaw = localStorage.getItem("tempBooking");
      let booking = {};
      try {
        booking = JSON.parse(bookingRaw) || {};
      } catch (e) { console.error(e); }

      const customer = booking.customer || {};
      const fullName = `${customer.title || ""} ${customer.firstName || ""} ${customer.lastName || ""}`.trim();
      const email = customer.email || "";
      const address = customer.address || "";
      const phone = customer.phone || "";
      const reservationData = {
        bookingId,
        checkIn: checkin,
        checkOut: checkout,
        totalGuests: guests,
        totalPrice,
        customerName: fullName,
        phone,
        email,
        address,
        roomName,
        roomView,
        roomsSelected
      };

      console.log(reservationData);

      fetch("http://localhost:8080/api/reservation/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(reservationData)
      })
        .then(resp => resp.json())
        .then(res => {
          console.log("Saved reservation:", res);

          if (email) {
            const mailText = `Your reservation (ID: ${bookingId}) for ${roomName} from ${checkin} to ${checkout} has been confirmed.`;
            return fetch(`http://localhost:8080/api/email/send?to=${email}&subject=Reservation%20Confirmed&text=${encodeURIComponent(mailText)}`, {
              method: "POST"
            });
          }
        })
        .then(emailResp => emailResp?.text())
        .then(emailResult => {
          if (emailResult) alert("QR modal opened & Email sent: " + emailResult);
        })
        .catch(err => {
          console.error(err);
          alert("Error sending reservation/email: " + err);
        });
    });
  }
});
