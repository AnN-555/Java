const API_URL = "http://localhost:8080/api/feedback";

// Load danh sách
async function loadFeedbacks() {
    const listContainer = document.getElementById("feedback-list");
    listContainer.innerHTML = "<p style='text-align:center'>Đang tải đánh giá...</p>";

    try {
        const res = await fetch(`${API_URL}/all`);
        const data = await res.json();

        listContainer.innerHTML = "";
        if (data.length === 0) {
            listContainer.innerHTML = "<p style='text-align:center; color:#999'>Chưa có đánh giá nào.</p>";
            return;
        }

        data.forEach(fb => {
            const date = new Date(fb.createdAt).toLocaleDateString('vi-VN');
            const card = `
                <div class="review-card">
                    <div class="review-header">
                        <strong>${escapeHtml(fb.customerName)}</strong>
                        <span class="review-date">${date}</span>
                    </div>
                    <p class="review-content">"${escapeHtml(fb.comment)}"</p>
                </div>
            `;
            listContainer.innerHTML += card;
        });
    } catch (err) {
        listContainer.innerHTML = "<p style='text-align:center; color:red'>Lỗi tải dữ liệu!</p>";
    }
}

// Gửi feedback
async function submitFeedback() {
    const name = document.getElementById("fb-name").value.trim();
    const comment = document.getElementById("fb-comment").value.trim();
    const btn = document.querySelector(".btn-submit");

    if (!name || !comment) {
        alert("Vui lòng nhập đầy đủ tên và nội dung!");
        return;
    }

    btn.disabled = true;
    btn.textContent = "ĐANG GỬI...";

    try {
        const res = await fetch(`${API_URL}/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ customerName: name, comment: comment })
        });

        const result = await res.json();
        if (result.success) {
            alert("Cảm ơn bạn! Đánh giá đã được gửi thành công ❤️");
            document.getElementById("fb-name").value = "";
            document.getElementById("fb-comment").value = "";
            loadFeedbacks();
        } else {
            alert("Lỗi: " + result.message);
        }
    } catch (err) {
        alert("Không kết nối được server!");
    } finally {
        btn.disabled = false;
        btn.textContent = "GỬI ĐÁNH GIÁ";
    }
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

document.addEventListener("DOMContentLoaded", loadFeedbacks);