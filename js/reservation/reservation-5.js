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

  // Hàm parse dd/mm/yyyy từ localStorage
  const parseLocalDate = (dateStr) => {
    if (!dateStr) return new Date();
    const parts = dateStr.split('/');
    return new Date(parts[2], parts[1] - 1, parts[0]); // year, month-1, day
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

  // ======= Format dates & nights =======
  const formatDate = (date) =>
    date.toLocaleDateString("en-GB", {
      day: "numeric",
      month: "short",
      year: "numeric",
    });

  const nights = Math.round(
    (checkoutDate - checkinDate) / (1000 * 60 * 60 * 24)
  );
  const datesText = `${formatDate(checkinDate)} - ${formatDate(
    checkoutDate
  )} (${nights} Night${nights > 1 ? "s" : ""})`;

  // ======= Lấy giá từ localStorage & tính toán =======
  const priceRaw = localStorage.getItem('stay_price') || '500'; // default $500/đêm
  const pricePerNight = parseFloat(priceRaw); // convert về số

  // Tính basePrice, tax, serviceFee, total dựa trên nights
  const basePriceValue = pricePerNight * nights;
  const taxValue = +(basePriceValue * 0.10).toFixed(2);    // VAT 10%
  const serviceValue = +(basePriceValue * 0.05).toFixed(2); // Service 5%
  const totalValue = +(basePriceValue + taxValue + serviceValue).toFixed(2);

  // Format USD
  const formatUSD = (value) => `$${value.toLocaleString('en-US', { minimumFractionDigits: 2 })}`;

  const basePrice = formatUSD(basePriceValue);
  const tax = formatUSD(taxValue);
  const serviceFee = formatUSD(serviceValue);
  const total = formatUSD(totalValue);

  // ======= Render lên page =======
  const details = document.querySelectorAll(".stay_box .stay-box-detail");
  if (details[0])
    details[0].innerHTML = `<img src="../../images/reservation/reservation-page-4/location.png" alt="" style="height: 20px"> ${location}`;
  if (details[1])
    details[1].innerHTML = `<img src="../../images/reservation/reservation-page-4/calender.png" alt="" style="height: 20px"> ${datesText}`;
  if (details[2])
    details[2].innerHTML = `<img src="../../images/reservation/reservation-page-4/room.png" alt="" style="height: 20px"> ${roomName}`;
  if (details[3])
    details[3].innerHTML = `<img src="../../images/reservation/reservation-page-4/people.png" alt="" style="height: 20px"> ${guests}`;

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

  const title = localStorage.getItem("user_title") || "";
  const firstName = localStorage.getItem("user_firstName") || "";
  const lastName = localStorage.getItem("user_lastName") || "";
  const phone = localStorage.getItem("user_phone") || "";
  const email = localStorage.getItem("user_email") || "";
  const country = localStorage.getItem("user_country") || "Vietnam";

  const infoDivs = document.querySelectorAll(".detail_box .information");

  if (infoDivs[0]) {
    infoDivs[0].innerHTML = `<img src="../../images/reservation/reservation-page-5/information.png" style="height:20px"> 
        ${title ? title.toUpperCase() : ""} ${firstName} ${lastName}`;
  }

  if (infoDivs[1]) {
    infoDivs[1].innerHTML = `<img src="../../images/reservation/reservation-page-5/telephone.png" style="height:20px"> 
        ${phone}`;
  }

  if (infoDivs[2]) {
    infoDivs[2].innerHTML = `<img src="../../images/reservation/reservation-page-5/email.png" style="height:20px"> 
        ${email}`;
  }

  if (infoDivs[3]) {
    infoDivs[3].innerHTML = `<img src="../../images/reservation/reservation-page-5/location.png" style="height:20px"> ${country}`;
  }

  const continueBtn = document.querySelector(".continue-btn");
  if (!continueBtn) return;

  const errorDiv = document.createElement("div");
  errorDiv.style.cssText =
    "color:#d32f2f;background:#ffebee;padding:12px;border-radius:8px;margin:15px 0;text-align:center;font-size:0.95rem;border:1px solid #ffcdd2;display:none;";
  continueBtn.parentNode.insertBefore(errorDiv, continueBtn.nextSibling);

  continueBtn.addEventListener("click", function (e) {
    e.preventDefault();
    errorDiv.style.display = "none";

    const cardNumber = document.querySelector('.card-input input[type="text"]');
    const expiry = document.querySelector('input[placeholder="MM/YY"]');
    const cvv = document.querySelector(
      '.input-with-icons:last-of-type input[type="text"]'
    );

    const errors = [];

    if (!cardNumber?.value.trim())
      errors.push("Please enter your card number.");

    if (!expiry?.value.trim()) errors.push("Please enter expiration date.");
    else if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(expiry.value.trim()))
      errors.push("Invalid format. Please use MM/YY.");

    if (!cvv?.value.trim()) errors.push("Please enter your CVV.");

    if (errors.length > 0) {
      errorDiv.innerHTML = "<strong>Error:</strong><br>" + errors.join("<br>");
      errorDiv.style.display = "block";
    } else {
      alert("Your booking has been successfully completed! ❤️");
    }
  });
  const navTop = document.getElementById("top-page");
  if (navTop) {
    navTop.addEventListener("click", function () {
      window.location.href = "../homePage.html";
    });
  }
});
