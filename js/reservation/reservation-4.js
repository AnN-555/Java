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


  // ======= Continue button validation + gửi data =======
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

    // Validate form khách hàng
    const titleChecked = document.querySelector('input[name="title"]:checked');
    if (!titleChecked) errors.push("Please select a title (Mr / Ms / Mrs)");
    const firstName =
      document.querySelector('input[placeholder="First name"]') ||
      document.querySelectorAll(".input-group input[type=text]")[0];
    const lastName =
      document.querySelector('input[placeholder="Last name"]') ||
      document.querySelectorAll(".input-group input[type=text]")[1];
    if (!firstName?.value.trim()) errors.push("Please enter your first name");
    if (!lastName?.value.trim()) errors.push("Please enter your last name");

    const phone =
      document.querySelector('input[placeholder="Phone number"]') ||
      document.querySelectorAll('input[type="text"]')[3] ||
      null;
    if (!phone?.value.trim()) errors.push("Please enter your phone number");
    else if (!/^\d{8,12}$/.test(phone.value.trim()))
      errors.push("Phone number must contain 8 to 12 digits");

    const email = document.querySelector('input[placeholder="Email"]');
    if (!email?.value.trim()) errors.push("Please enter your email address");
    else if (!/^\S+@\S+\.\S+$/.test(email.value.trim()))
      errors.push("Invalid email format");

    const checkboxEls = document.querySelectorAll(
      ".checkbox-group input[type=checkbox]"
    );
    if (!checkboxEls[0]?.checked)
      errors.push(
        "You must confirm that you will present a valid ID during check-in"
      );
    if (!checkboxEls[1]?.checked)
      errors.push("You must agree to the Terms & Conditions");

    if (errors.length > 0) {
      errorDiv.innerHTML = errors.join("<br>");
      errorDiv.scrollIntoView({ behavior: "smooth" });
      return;
    }

    // Gửi data khách + thông tin phòng
    const titleText =
      titleChecked.value ||
      (titleChecked.nextElementSibling?.textContent || "").trim();

    const selects = document.querySelectorAll(
      ".input-row .input-group select, .input-group select"
    );
    let country = "Vietnam";
    if (selects && selects.length > 0) {
      let found = null;
      selects.forEach((s) => {
        const parentLabel =
          s.closest(".input-group")?.querySelector("label")?.textContent?.toLowerCase() ||
          "";
        if (parentLabel.includes("country")) found = s;
      });
      country = (found ? found.value : selects[selects.length - 1].value) || "Vietnam";
    }

    const tempBooking = {
      roomId: roomId,
      checkIn: checkinRaw,
      checkOut: checkoutRaw,
      customer: {
        title: titleText,
        firstName: firstName.value.trim(),
        lastName: lastName.value.trim(),
        phone: phone.value.trim(),
        email: email.value.trim(),
        country: country
      }
    };

    fetch("http://localhost:8080/api/reservation/InformationCustomer", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(tempBooking)
    })
      .then((res) => res.json())
      .then((data) => {
        const tempId = data.tempId;
        if (!tempId) throw new Error("No tempId returned from backend");
        // Chuyển trang kèm tempId
        window.location.href = `./reservation-page-5.html?tempId=${tempId}`;
      })
      .catch((err) => {
        errorDiv.textContent =
          "Error saving customer info, please try again later.";
        console.error(err);
      });
  });

  // ======= Navigation top page =======
  const navTop = document.getElementById("top-page");
  if (navTop) {
    navTop.addEventListener("click", function () {
      window.location.href = "../homePage.html";
    });
  }
});
