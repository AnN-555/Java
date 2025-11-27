document.addEventListener("DOMContentLoaded", function () {
  const params = new URLSearchParams(window.location.search);

  const name = params.get("name");
  const image = params.get("image");
  const description = params.get("description");
  const specs = params.get("specs");
  const price = params.get("price");

  const roomDetail = document.querySelector(".room__request .room__detail");
  if (roomDetail) {
    const title = roomDetail.querySelector("h2");
    const desc = roomDetail.querySelector("p");
    const specsSpan = roomDetail.querySelector(".room__detail__booking span");
    const priceTag = roomDetail.querySelector(
      ".room__detail__booking__price p"
    );

    if (title) title.textContent = name;
    if (desc) desc.textContent = description;
    if (specsSpan) specsSpan.textContent = specs;
    if (priceTag) priceTag.textContent = price;
  }

  const roomImage = document.querySelector(".room__request .room__image img");
  if (roomImage) {
    roomImage.src = image;
    roomImage.alt = `Image ${name}`;
  }

  const paymentImage = document.querySelector(".payment__image img");
  if (paymentImage) {
    paymentImage.src = image;
    paymentImage.alt = `Image ${name}`;
  }

  const paymentDetails = document.querySelectorAll(".payment__detail");
  paymentDetails.forEach((detail) => {
    if (
      detail.textContent.includes("Deluxe Room") ||
      detail.textContent.includes("Suite")
    ) {
      detail.textContent = name;
    }
  });

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

  if (paymentDetails[1]) {
    const img = paymentDetails[1].querySelector("img");
    paymentDetails[1].textContent = "";
    if (img) paymentDetails[1].appendChild(img);
    paymentDetails[1].append(" " + datesText);
  }
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

    const paymentButton = document.querySelector(".payment__button");
    if (paymentButton) {
      paymentButton.addEventListener("click", () => {
        window.location.href = "./reservation-page-4.html";
      });
    }
  }

  const paymentButton = document.querySelector(".payment__button");
  if (paymentButton) {
    paymentButton.addEventListener("click", function () {
      localStorage.setItem(
        "stay_location",
        "Thu Duc Ward, Ho Chi Minh City, Vietnam"
      );
      localStorage.setItem("stay_dates", datesText);

      const roomImg =
        document.querySelector(".payment__image img") ||
        document.querySelector(".room__image img");
      localStorage.setItem(
        "stay_roomImage",
        roomImg?.src ||
          "../images/reservation/reservation-page-2/deluxe-room.jpg"
      );

      const roomName = document
        .querySelector(".room__detail h2")
        ?.textContent.trim();
      localStorage.setItem("stay_roomName", roomName || "Deluxe Room");

      const guestsElement = document.querySelectorAll(".payment__detail")[4];
      localStorage.setItem(
        "stay_guests",
        guestsElement?.textContent.trim() || "1 Room - 2 Guests"
      );

      const priceEl = document.querySelector(".room__detail__booking__price p");
      const basePrice = priceEl?.textContent.trim() || "5,000,000 VND";
      localStorage.setItem("stay_basePrice", basePrice);

      const numPrice = parseFloat(basePrice.replace(/[^0-9]/g, "")) || 5000000;
      const tax = (numPrice * 0.1).toLocaleString("vi-VN");
      const fee = (numPrice * 0.05).toLocaleString("vi-VN");
      const total = (numPrice * 1.15).toLocaleString("vi-VN");

      localStorage.setItem("stay_tax", tax + " VND");
      localStorage.setItem("stay_serviceFee", fee + " VND");
      localStorage.setItem("stay_total", total + " VND");
    });
  }
  const navTop = document.getElementById("top-page");
  if (navTop) {
    navTop.addEventListener("click", function () {
      window.location.href = "../homePage.html";
    });
  }
});
