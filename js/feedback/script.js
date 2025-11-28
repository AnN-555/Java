const API_URL = "http://localhost:8080/api/feedback";

// 1. Hàm load danh sách feedback
async function loadFeedbacks() {
    const listContainer = document.getElementById("feedback-list");
    listContainer.innerHTML = "<p style='text-align:center'>Loading reviews...</p>";

    try {
        const response = await fetch(`${API_URL}/all`);
        if (!response.ok) throw new Error("Network response was not ok");
        
        const data = await response.json();
        
        listContainer.innerHTML = ""; // Xóa loading

        if(data.length === 0) {
            listContainer.innerHTML = "<p style='text-align:center'>No reviews yet. Be the first!</p>";
            return;
        }

        data.forEach(fb => {
            const date = new Date(fb.createdAt).toLocaleDateString('en-GB'); // dd/mm/yyyy

            const card = document.createElement("div");
            card.className = "review-card";
            card.innerHTML = `
                <div class="review-header">
                    <span class="customer-name">${fb.customerName}</span>
                    <span class="review-date">${date}</span>
                </div>
                <p class="review-content">"${fb.comment}"</p>
            `;
            listContainer.appendChild(card);
        });

    } catch (error) {
        console.error("Error:", error);
        listContainer.innerHTML = "<p style='text-align:center; color:red'>Failed to load reviews.</p>";
    }
}

// 2. Hàm gửi feedback
async function submitFeedback() {
    const nameInput = document.getElementById("fb-name");
    const commentInput = document.getElementById("fb-comment");
    const btn = document.querySelector(".btn-submit");

    const name = nameInput.value.trim();
    const comment = commentInput.value.trim();

    if(!name || !comment) {
        alert("Please fill in all fields!");
        return;
    }

    // Hiệu ứng nút bấm đang xử lý
    const originalText = btn.innerText;
    btn.innerText = "SENDING...";
    btn.disabled = true;

    try {
        const response = await fetch(`${API_URL}/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                customerName: name,
                comment: comment
            })
        });

        if (response.ok) {
            alert("Thank you! Your feedback has been submitted.");
            nameInput.value = "";
            commentInput.value = "";
            loadFeedbacks(); // Tải lại danh sách ngay lập tức
        } else {
            alert("Something went wrong. Please try again.");
        }
    } catch (error) {
        console.error("Error submit:", error);
        alert("Server connection failed.");
    } finally {
        // Trả lại nút bấm bình thường
        btn.innerText = originalText;
        btn.disabled = false;
    }
}

// Gọi hàm load ngay khi vào trang
document.addEventListener("DOMContentLoaded", loadFeedbacks);