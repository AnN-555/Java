console.log("Enabled js")

const BASE_URL = 'http://localhost:8080/api/'; // adjust path if needed
const output = document.getElementById('output');

// // POST: Add a new user
// document.getElementById('send').onclick = async () => {
//     const name = document.getElementById('key').value;
//     const email = document.getElementById('value').value;

//     if (!name || !email) {
//         output.textContent = "⚠️ Please fill in both name and email!";
//         return;
//     }

//     const input = document.getElementById("apiurl").value.trim();

//     if (!input) {
//         alert("Please enter a value first!");
//         return;
//     }

//     const API_URL = `${BASE_URL}${encodeURI(input)}`; // append safely

//     try {
//         const res = await fetch(API_URL, {
//             method: 'POST',
//             headers: { 'Content-Type': 'application/json;charset=UTF-8' },
//             body: JSON.stringify({ name, email })
//         });
//         const data = await res.json();
//         output.textContent = '✅ Added:\n' + JSON.stringify(data, null, 2);
//     } catch (err) {
//         output.textContent = '❌ Error: ' + err;
//     }
// };

// GET: Fetch all users
document.getElementById('getAll').onclick = async () => {
    try {
        const input = document.getElementById("apiurl").value.trim();

        if (!input) {
            alert("Please enter a value first!");
            return;
        }

        const API_URL = `${BASE_URL}${encodeURI(input)}`; // append safely

        const res = await fetch(API_URL);
        const data = await res.json();
        output.textContent = '📋 All Content:\n' + JSON.stringify(data, null, 2);
    } catch (err) {
        output.textContent = '❌ Error: ' + err;
        console.error(API_URL);
    }
};