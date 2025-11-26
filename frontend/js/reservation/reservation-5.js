document.addEventListener("DOMContentLoaded", function () {
  const phaseItems = document.querySelectorAll(".phase ul li");
  if (phaseItems.length > 0) {
    phaseItems[0].addEventListener("click", function () {
      window.location.href = "./reservation-page-1.html";
    });

    if (phaseItems.length > 2) {
      phaseItems[2].addEventListener("click", function () {
        window.location.href = "./reservation-page-2.html";
      });
    }
  }

  const location =
    localStorage.getItem("selectedLocation") ||
    "Thu Duc Ward, Ho Chi Minh City, Vietnam";
  const checkinRaw = localStorage.getItem("checkinDate") || "2025-09-26";
  const checkoutRaw = localStorage.getItem("checkoutDate") || "2025-09-27";
  const checkinDate = new Date(checkinRaw + "T00:00:00");
  const checkoutDate = new Date(checkoutRaw + "T00:00:00");

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

  const dates =
    localStorage.getItem("stay_dates") || "26 Sep 2025 - 27 Sep 2025 (1 Night)";
  const roomImage =
    localStorage.getItem("stay_roomImage") ||
    "../../images/reservation/reservation-page-4/deluxe-room.jpg";
  const roomName = localStorage.getItem("stay_roomName") || "Deluxe Room";
  const guests = localStorage.getItem("stay_guests") || "1 Room - 2 Guests";
  const basePrice = localStorage.getItem("stay_basePrice") || "5,000,000 VND";
  const tax = localStorage.getItem("stay_tax") || "500,000 VND";
  const serviceFee = localStorage.getItem("stay_serviceFee") || "250,000 VND";
  const total = localStorage.getItem("stay_total") || "5,750,000 VND";

  const stayDetails = document.querySelectorAll(".stay_box .stay-box-detail");
  if (stayDetails[0])
    stayDetails[0].innerHTML = `<img src="../../images/reservation/reservation-page-4/location.png" style="height:20px"> ${location}`;
  if (stayDetails[1])
    stayDetails[1].innerHTML = `<img src="../../images/reservation/reservation-page-4/calender.png" style="height:20px"> ${datesText}`;
  if (stayDetails[2])
    stayDetails[2].innerHTML = `<img src="../../images/reservation/reservation-page-4/room.png" style="height:20px"> ${roomName}`;
  if (stayDetails[3])
    stayDetails[3].innerHTML = `<img src="../../images/reservation/reservation-page-4/people.png" style="height:20px"> ${guests}`;

  const img = document.querySelector(".stay-box-image img");
  if (img) {
    img.src = roomImage;
    img.alt = roomName;
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
