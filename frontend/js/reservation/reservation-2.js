document.addEventListener("DOMContentLoaded", function () {
  const phaseItems = document.querySelectorAll(".phase ul li");
  if (phaseItems.length > 0) {
    phaseItems[0].addEventListener("click", function () {
      window.location.href = "./reservation-page-1.html";
    });
  }
  if (phaseItems.length > 2) {
    phaseItems[2].addEventListener("click", function () {
      window.location.href = "./reservation-page-2.html";
    });
  }

  // ======= Lấy dữ liệu từ localStorage =======
  const checkinRaw = localStorage.getItem("checkinDate") || "26/09/2025";
  const checkoutRaw = localStorage.getItem("checkoutDate") || "27/09/2025";

  const parseLocalDate = (dateStr) => {
    if (!dateStr) return new Date();
    const parts = dateStr.split('/');
    return new Date(parts[2], parts[1] - 1, parts[0]);
  };

  const checkinDate = parseLocalDate(checkinRaw);
  const checkoutDate = parseLocalDate(checkoutRaw);

  const roomName = localStorage.getItem("stay_roomName") || "Deluxe Room";
  const roomImage =
    localStorage.getItem("stay_roomImage") ||
    "../../images/reservation/reservation-page-4/deluxe-room.jpg";
  const guests = localStorage.getItem("stay_guests") || "1 Room - 2 Guests";
  const location =
    localStorage.getItem("stay_location") ||
    "Thu Duc Ward, Ho Chi Minh City, Vietnam";

  const countrySelect = document.getElementById("countrySelect");



  // ======= Tính ngày - đêm =======
  const formatDate = (date) =>
    date.toLocaleDateString("en-GB", {
      day: "numeric",
      month: "short",
      year: "numeric",
    });

  const nights = Math.round(
    (checkoutDate - checkinDate) / (1000 * 60 * 60 * 24)
  );

  const datesText = `${formatDate(checkinDate)} - ${formatDate(checkoutDate)} (${nights} Night${nights > 1 ? "s" : ""})`;

  // ======= Lấy giá & tính toán =======
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


  // ======= Render UI =======
  const details = document.querySelectorAll(".stay_box .stay-box-detail");
  if (details[0])
    details[0].innerHTML = `<img src="../images/reservation/reservation-page-4/location.png" style="height: 20px"> ${location}`;
  if (details[1])
    details[1].innerHTML = `<img src="../images/reservation/reservation-page-4/calender.png" style="height: 20px"> ${datesText}`;
  if (details[2])
    details[2].innerHTML = `<img src="../images/reservation/reservation-page-4/room.png" style="height: 20px"> ${roomName}`;
  if (details[3])
    details[3].innerHTML = `<img src="../images/reservation/reservation-page-4/people.png" style="height: 20px"> ${guests}`;

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

  // ======= Continue button =======
  const continueBtn = document.querySelector(".continue-btn");
  if (!continueBtn) return;

  const errorDiv = document.createElement("div");
  errorDiv.style.cssText =
    "color:red;font-size:0.9rem;margin-top:0.5rem;text-align:center;";
  continueBtn.parentNode.insertBefore(errorDiv, continueBtn.nextSibling);

  continueBtn.addEventListener("click", function (e) {
    e.preventDefault();
    errorDiv.textContent = "";
    const errors = [];

    // Validate form
    const titleChecked = document.querySelector('input[name="title"]:checked');
    if (!titleChecked) errors.push("Please select a title");

    const firstName = document.querySelector('input[placeholder="First name"]');
    const lastName = document.querySelector('input[placeholder="Last name"]');

    if (!firstName?.value.trim()) errors.push("Please enter your first name");
    if (!lastName?.value.trim()) errors.push("Please enter your last name");

    const phone = document.querySelector('input[placeholder="Phone number"]');
    if (!phone?.value.trim()) errors.push("Please enter your phone number");
    else if (!/^\d{8,12}$/.test(phone.value.trim()))
      errors.push("Phone number must contain 8 to 12 digits");

    const email = document.querySelector('input[placeholder="Email"]');
    if (!email?.value.trim()) errors.push("Please enter your email");
    else if (!/^\S+@\S+\.\S+$/.test(email.value.trim()))
      errors.push("Invalid email format");

    const checkboxEls = document.querySelectorAll(
      ".checkbox-group input[type=checkbox]"
    );
    if (!checkboxEls[0]?.checked)
      errors.push("You must confirm to present a valid ID");
    if (!checkboxEls[1]?.checked)
      errors.push("You must agree to the Terms & Conditions");

    if (errors.length > 0) {
      errorDiv.innerHTML = errors.join("<br>");
      return;
    }

    // ======= Lưu toàn bộ vào localStorage =======
    const customerInfo = {
      checkIn: checkinRaw,
      checkOut: checkoutRaw,
      customer: {
        title: titleChecked.value,
        firstName: firstName.value.trim(),
        lastName: lastName.value.trim(),
        phone: phone.value.trim(),
        email: email.value.trim(),
        country: countrySelect.value
      },
      pricing: {
        nights,
        basePriceValue,
        taxValue,
        serviceValue,
        totalValue
      }
    };

    localStorage.setItem("tempBooking", JSON.stringify(customerInfo));

    // 🔥 Không gọi API nữa — chuyển trang luôn
    window.location.href = "./reservation-page-3.html";
  });

  const navTop = document.getElementById("top-page");
  if (navTop) {
    navTop.addEventListener("click", function () {
      window.location.href = "../homePage.html";
    });
  }
});
