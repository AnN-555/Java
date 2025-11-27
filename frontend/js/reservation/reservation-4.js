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

  const location =
    localStorage.getItem("stay_location") ||
    "Thu Duc Ward, Ho Chi Minh City, Vietnam";
  const datesStored =
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

  const details = document.querySelectorAll(".stay_box .stay-box-detail");
  if (details[0])
    details[0].innerHTML = `<img src="../../images/reservation/reservation-page-4/location.png" alt="" style="height: 20px"> ${location}`;

  if (details[1])
    details[1].innerHTML = `<img src="../../images/reservation/reservation-page-4/calender.png" alt="" style="height: 20px"> ${
      datesText || datesStored
    }`;
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
    const checkbox1 = checkboxEls[0];
    const checkbox2 = checkboxEls[1];
    if (!checkbox1?.checked)
      errors.push(
        "You must confirm that you will present a valid ID during check-in"
      );
    if (!checkbox2?.checked)
      errors.push("You must agree to the Terms & Conditions");

    if (errors.length > 0) {
      errorDiv.innerHTML = errors.join("<br>");
      errorDiv.scrollIntoView({ behavior: "smooth" });
      return;
    }
    let titleText = "";
    if (titleChecked) {
      titleText =
        titleChecked.value ||
        (titleChecked.nextElementSibling?.textContent || "").trim();
    }
    localStorage.setItem("user_title", titleText || "Mr");
    localStorage.setItem("user_firstName", firstName.value.trim());
    localStorage.setItem("user_lastName", lastName.value.trim());
    localStorage.setItem("user_phone", phone.value.trim());
    localStorage.setItem("user_email", email.value.trim());

    const selects = document.querySelectorAll(
      ".input-row .input-group select, .input-group select"
    );
    let country = "Vietnam";
    if (selects && selects.length > 0) {
      let found = null;
      selects.forEach((s) => {
        const parentLabel =
          s
            .closest(".input-group")
            ?.querySelector("label")
            ?.textContent?.toLowerCase() || "";
        if (parentLabel.includes("country")) found = s;
      });
      country =
        (found ? found.value : selects[selects.length - 1].value) || "Vietnam";
    }
    localStorage.setItem("user_country", country);
    localStorage.setItem("stay_dates", datesText);
    window.location.href = "./reservation-page-5.html";
  });
  const navTop = document.getElementById("top-page");
  if (navTop) {
    navTop.addEventListener("click", function () {
      window.location.href = "../homePage.html";
    });
  }
});
