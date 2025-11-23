
    const API_BASE = "http://localhost:9090/api/";

    const apiEndpoints = {
      roomtype: API_BASE + "roomtype",
      rooms: API_BASE + "rooms",
      customers: API_BASE + "customers",
      booking: API_BASE + "booking",
      booking_details: API_BASE + "booking_details",
      payments: API_BASE + "payments"
    };

    let selectedRow = null;

    // Flatten nested JSON (handles arrays too)
    function flattenObject(obj, prefix = "") {
      let result = {};
      for (let key in obj) {
        if (!Object.prototype.hasOwnProperty.call(obj, key)) continue;
        const val = obj[key];
        if (Array.isArray(val)) {
          val.forEach((item, idx) => {
            Object.assign(result, flattenObject(item, prefix + key + "[" + idx + "]."));
          });
        } else if (typeof val === "object" && val !== null) {
          Object.assign(result, flattenObject(val, prefix + key + "."));
        } else {
          result[prefix + key] = val;
        }
      }
      return result;
    }

    // Row selection
    function enableRowSelection() {
      selectedRow = null;
      const rows = document.querySelectorAll("#tableContainer tbody tr");
      rows.forEach(row => {
        row.addEventListener("click", () => {
          if (selectedRow) selectedRow.classList.remove("selected");
          selectedRow = row;
          row.classList.add("selected");
        });
      });
    }

    // Read row data from DOM
    function getRowData(rowElement) {
      const headers = document.querySelectorAll("#tableContainer thead th");
      const cells = rowElement.children;
      const row = {};
      headers.forEach((h, i) => {
        row[h.innerText] = cells[i].innerText.trim();
      });
      return row;
    }

    // Renderers
    function renderCustomers(data) {
      const fields = ["id", "name", "email", "phone", "address"];
      let html = "<table><thead><tr>";
      fields.forEach(f => html += `<th>${f}</th>`);
      html += "</tr></thead><tbody>";

      if (data.length === 0) {
        html += "<tr>";
        fields.forEach(f => {
          const editable = f === "id" ? "false" : "true";
          html += `<td contenteditable="${editable}"></td>`;
        });
        html += "</tr>";
      } else {
        data.forEach(c => {
          html += "<tr>";
          fields.forEach(f => {
            const editable = f === "id" ? "false" : "true";
            html += `<td contenteditable="${editable}">${c[f] ?? ""}</td>`;
          });
          html += "</tr>";
        });
      }

      html += "</tbody></table>";
      document.getElementById("tableContainer").innerHTML = html;
      enableRowSelection();
    }

    function renderBookingDetails(data) {
      const rows = data.map(item => flattenObject(item));
      const allKeys = [...new Set(rows.flatMap(r => Object.keys(r)))];

      let html = "<table><thead><tr>";
      allKeys.forEach(k => html += `<th>${k}</th>`);
      html += "</tr></thead><tbody>";

      if (rows.length === 0) {
        html += "<tr>";
        allKeys.forEach(k => {
          const editable = k.includes("id") ? "false" : "true";
          html += `<td contenteditable="${editable}"></td>`;
        });
        html += "</tr>";
      } else {
        rows.forEach(r => {
          html += "<tr>";
          allKeys.forEach(k => {
            const editable = k.includes("id") ? "false" : "true";
            html += `<td contenteditable="${editable}">${r[k] ?? ""}</td>`;
          });
          html += "</tr>";
        });
      }

      html += "</tbody></table>";
      document.getElementById("tableContainer").innerHTML = html;
      enableRowSelection();
    }

    function renderRoomtype(data) {
      let rows = [];
      data.forEach(rt => {
        if (rt.rooms && Array.isArray(rt.rooms) && rt.rooms.length > 0) {
          rt.rooms.forEach(room => {
            const flat = {
              price: rt.price,
              capacity: rt.capacity,
              area: rt.area,
              ...flattenObject(room, "room.")
            };
            rows.push(flat);
          });
        } else {
          rows.push(flattenObject(rt));
        }
      });

      const allKeys = [...new Set(rows.flatMap(r => Object.keys(r)))];

      let html = "<table><thead><tr>";
      allKeys.forEach(k => html += `<th>${k}</th>`);
      html += "</tr></thead><tbody>";

      if (rows.length === 0) {
        html += "<tr>";
        allKeys.forEach(k => {
          const editable = k.includes("id") ? "false" : "true";
          html += `<td contenteditable="${editable}"></td>`;
        });
        html += "</tr>";
      } else {
        rows.forEach(r => {
          html += "<tr>";
          allKeys.forEach(k => {
            const editable = k.includes("id") ? "false" : "true";
            html += `<td contenteditable="${editable}">${r[k] ?? ""}</td>`;
          });
          html += "</tr>";
        });
      }

      html += "</tbody></table>";
      document.getElementById("tableContainer").innerHTML = html;
      enableRowSelection();
    }

    // Fallback renderer for flat tables using predefined fields
    const tableFields = {
      roomtype: ["type_id","type_name","price","capacity","type_description","area","image_url"],
      rooms: ["room_id","room_number","type_id","amenities","room_view","room_status"],
      customers: ["id","name","email","phone","address"],
      booking: ["booking_id","customer_id","checkin_date","checkout_date","num_guests","total_price","special_request","booking_status"],
      booking_details: ["booking_detail_id","booking_id","room_id"],
      payments: ["payment_id","booking_id","payment_date","payment_method","amount","payment_status","transaction_code"]
    };

    function renderFlat(tableName, data = []) {
      const fields = tableFields[tableName] || (data[0] ? Object.keys(data[0]) : ["id"]);

      let html = "<table><thead><tr>";
      fields.forEach(f => html += `<th>${f}</th>`);
      html += "</tr></thead><tbody>";

      if (data.length === 0) {
        html += "<tr>";
        fields.forEach(f => {
          const editable = f.includes("_id") || f === "id" ? "false" : "true";
          html += `<td contenteditable="${editable}"></td>`;
        });
        html += "</tr>";
      } else {
        data.forEach(row => {
          html += "<tr>";
          fields.forEach(f => {
            const editable = f.includes("_id") || f === "id" ? "false" : "true";
            html += `<td contenteditable="${editable}">${row[f] ?? ""}</td>`;
          });
          html += "</tr>";
        });
      }

      html += "</tbody></table>";
      document.getElementById("tableContainer").innerHTML = html;
      enableRowSelection();
    }

    // Dispatcher
    function renderTable(tableName, data = []) {
      if (tableName === "customers") return renderCustomers(data);
      if (tableName === "booking_details") return renderBookingDetails(data);
      if (tableName === "roomtype") return renderRoomtype(data);
      return renderFlat(tableName, data);
    }

    // Read
    document.querySelector(".btn-read").onclick = async () => {
      const table = document.getElementById("tableSelect").value;
      try {
        const res = await fetch(apiEndpoints[table]);
        const data = await res.json();
        renderTable(table, Array.isArray(data) ? data : [data]);
      } catch (err) {
        alert("Error fetching data: " + err);
      }
    };

    // Update: success if response JSON is a non-null object, failed if null
document.querySelector(".btn-update").onclick = async () => {
  const table = document.getElementById("tableSelect").value;
  if (!selectedRow) {
    alert("Please select a row to update");
    return;
  }
  const row = getRowData(selectedRow);

  // Find the first column whose name includes "id"
  const idField = Object.keys(row).find(k => k.toLowerCase().includes("id"));
  if (!idField) {
    alert("No ID column found in this table");
    return;
  }
  const idValue = row[idField];

  try {
    const res = await fetch(apiEndpoints[table] + "/" + encodeURIComponent(idValue), {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(row)
    });

    // Try to parse JSON; handle empty body gracefully
    let result = null;
    const text = await res.text();
    if (text) {
      try { result = JSON.parse(text); } catch (_) { result = null; }
    }

    if (result) {
      alert("Update successful");
    } else {
      alert("Update failed: object was null");
    }
  } catch (err) {
    alert("Error updating: " + err);
  }
};

    // Delete: success if backend returns non-null or empty body with 2xx
document.querySelector(".btn-delete").onclick = async () => {
  const table = document.getElementById("tableSelect").value;
  if (!selectedRow) {
    alert("Please select a row to delete");
    return;
  }
  const row = getRowData(selectedRow);

  // Find the first column whose name includes "id"
  const idField = Object.keys(row).find(k => k.toLowerCase().includes("id"));
  if (!idField) {
    alert("No ID column found in this table");
    return;
  }
  const idValue = row[idField];

  try {
    const res = await fetch(apiEndpoints[table] + "/" + encodeURIComponent(idValue), {
      method: "DELETE"
    });

    // Some backends return 204 No Content. We'll treat 2xx as success if body is empty.
    let result = null;
    const text = await res.text();
    if (text) {
      try { result = JSON.parse(text); } catch (_) { result = null; }
    }

    if (res.ok && (result || !text)) {
      alert("Delete successful");
      selectedRow.remove();
      selectedRow = null;
    } else {
      alert("Delete failed");
    }
  } catch (err) {
    alert("Error deleting: " + err);
  }
};


    // Create (optional: success if non-null object returned)
    document.querySelector(".btn-create").onclick = async () => {
      const table = document.getElementById("tableSelect").value;
      const tbody = document.querySelector("#tableContainer tbody");
      if (!tbody || !tbody.firstElementChild) {
        alert("No row to create. Click Read first or switch table and edit the first row.");
        return;
      }
      const row = getRowData(tbody.firstElementChild);

      try {
        const res = await fetch(apiEndpoints[table], {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(row)
        });

        let result = null;
        const text = await res.text();
        if (text) {
          try { result = JSON.parse(text); } catch (_) { result = null; }
        }

        if (result) {
          alert("Create successful");
        } else {
          alert("Create failed: object was null");
        }
      } catch (err) {
        alert("Error creating: " + err);
      }
    };

    // Render empty table on change
    document.getElementById("tableSelect").addEventListener("change", (e) => {
      const table = e.target.value;
      renderTable(table, []);
    });

    // Initial render
    renderTable(document.getElementById("tableSelect").value, []);

