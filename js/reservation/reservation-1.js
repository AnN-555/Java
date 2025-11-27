document.addEventListener('DOMContentLoaded', () => {
    const checkinInput = document.getElementById('date-input-fromd');
    const checkoutInput = document.getElementById('date-input-tod');
    const calendarIcons = document.querySelectorAll('.field-checkin .fa-calendar, .field-checkout .fa-calendar');
    const searchBtn = document.querySelector('.search-button');
    const roomBox = document.querySelector('.room-box');
    

    // ======= Date utils =======
    const formatIsoDate = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    const formatDisplayDate = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${day}/${month}/${year}`;
    };

    const parseDisplayDate = (dateString) => {
        if (!dateString) return new Date();
        const parts = dateString.split('/');
        return new Date(parts[2], parts[1] - 1, parts[0]);
    };

    const parseIsoDate = (dateString) => {
        if (!dateString) return null;
        const parts = dateString.split('-');
        return new Date(parts[0], parts[1] - 1, parts[2]);
    };

    // ======= Calendar =======
    const createCalendarElement = (inputElement) => {
        const existingCalendar = inputElement.parentElement.querySelector('.calendar-popup');
        if (existingCalendar) existingCalendar.remove();

        const calendarDiv = document.createElement('div');
        calendarDiv.className = 'calendar-popup';
        calendarDiv.innerHTML = `
            <div class="calendar-header">
                <button type="button" class="prev-month">&lt;</button>
                <span class="calendar-month-year"></span>
                <button type="button" class="next-month">&gt;</button>
            </div>
            <div class="calendar-grid">
                <div class="calendar-day-name">Sun</div>
                <div class="calendar-day-name">Mon</div>
                <div class="calendar-day-name">Tue</div>
                <div class="calendar-day-name">Wed</div>
                <div class="calendar-day-name">Thu</div>
                <div class="calendar-day-name">Fri</div>
                <div class="calendar-day-name">Sat</div>
            </div>
        `;
        inputElement.parentElement.appendChild(calendarDiv);
        return calendarDiv;
    };

    const renderCalendar = (calendarDiv, date, inputElement) => {
        const grid = calendarDiv.querySelector('.calendar-grid');
        const monthYear = calendarDiv.querySelector('.calendar-month-year');
        const currentMonth = date.getMonth();
        const currentYear = date.getFullYear();

        const minDateStr = inputElement.getAttribute('data-min');
        const maxDateStr = inputElement.getAttribute('data-max');
        let minDate = parseIsoDate(minDateStr); if(minDate) minDate.setHours(0,0,0,0);
        let maxDate = parseIsoDate(maxDateStr); if(maxDate) maxDate.setHours(0,0,0,0);

        const monthNames = ["January","February","March","April","May","June","July","August","September","October","November","December"];
        monthYear.textContent = `${monthNames[currentMonth]} ${currentYear}`;

        // Remove previous days
        const days = grid.querySelectorAll('.calendar-day');
        days.forEach(d => d.remove());

        const firstDayOfMonth = new Date(currentYear, currentMonth, 1).getDay();
        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
        const today = new Date(); today.setHours(0,0,0,0);
        let selectedDate = parseDisplayDate(inputElement.value);
        if(isNaN(selectedDate.getTime())) selectedDate = null;

        for(let i=0;i<firstDayOfMonth;i++){ grid.appendChild(document.createElement('div')); }

        for(let i=1;i<=daysInMonth;i++){
            const dayCell = document.createElement('div');
            dayCell.className = 'calendar-day';
            dayCell.textContent = i;
            const checkDate = new Date(currentYear, currentMonth, i); checkDate.setHours(0,0,0,0);

            if(checkDate.getTime() === today.getTime()) dayCell.classList.add('today');
            if(selectedDate && checkDate.getTime() === selectedDate.getTime()) dayCell.classList.add('selected');

            let isDisabled = false;
            if(minDate && checkDate < minDate) isDisabled = true;
            if(maxDate && checkDate > maxDate) isDisabled = true;

            if(isDisabled) dayCell.classList.add('disabled');
            else dayCell.addEventListener('click',(e)=>{
                e.stopPropagation();
                inputElement.value = formatDisplayDate(checkDate);
                if(inputElement.id === 'date-input-fromd') updateCheckoutDate();
                calendarDiv.classList.remove('active');
            });

            grid.appendChild(dayCell);
        }
    };

    const setupCalendar = (icon) => {
        icon.addEventListener('click', (e)=>{
            e.stopPropagation();
            const label = icon.closest('label');
            const input = label.querySelector('input');
            let calendar = label.querySelector('.calendar-popup');
            if(!calendar) calendar = createCalendarElement(input);

            document.querySelectorAll('.calendar-popup').forEach(c => {if(c!==calendar)c.classList.remove('active');});
            calendar.classList.toggle('active');

            if(calendar.classList.contains('active')){
                let currentDate = parseDisplayDate(input.value);
                if(isNaN(currentDate.getTime())) currentDate = new Date();
                renderCalendar(calendar, currentDate, input);

                const prevBtn = calendar.querySelector('.prev-month');
                const nextBtn = calendar.querySelector('.next-month');
                const newPrevBtn = prevBtn.cloneNode(true);
                const newNextBtn = nextBtn.cloneNode(true);
                prevBtn.parentNode.replaceChild(newPrevBtn, prevBtn);
                nextBtn.parentNode.replaceChild(newNextBtn, nextBtn);

                newPrevBtn.addEventListener('click',(e)=>{
                    e.stopPropagation();
                    currentDate.setMonth(currentDate.getMonth()-1);
                    renderCalendar(calendar,currentDate,input);
                });
                newNextBtn.addEventListener('click',(e)=>{
                    e.stopPropagation();
                    currentDate.setMonth(currentDate.getMonth()+1);
                    renderCalendar(calendar,currentDate,input);
                });
            }
        });
    };

    const updateCheckoutDate = () => {
        if(!checkinInput || !checkoutInput) return;
        let checkinValue = checkinInput.value;
        if(checkinValue){
            let checkinDate = parseDisplayDate(checkinValue);
            const checkoutDate = new Date(checkinDate);
            checkoutDate.setDate(checkoutDate.getDate()+1);
            checkoutInput.value = formatDisplayDate(checkoutDate);
            checkoutInput.setAttribute('data-min', formatIsoDate(checkinDate));
        }
    };

    calendarIcons.forEach(icon => setupCalendar(icon));
    document.addEventListener('click',(e)=>{
        if(!e.target.closest('.field-checkin') && !e.target.closest('.field-checkout')){
            document.querySelectorAll('.calendar-popup').forEach(c=>c.classList.remove('active'));
        }
    });

    if(checkinInput){
        const today = new Date();
        checkinInput.value = formatDisplayDate(today);
        checkinInput.setAttribute('data-min', formatIsoDate(today));
        updateCheckoutDate();
    }

    // ======= Render room cards =======
    const renderRooms = (rooms, checkIn, checkOut, nights) => {
        roomBox.innerHTML = ''; // Xóa card cũ
        if(!rooms || rooms.length === 0){
            roomBox.innerHTML = `<p>No rooms available for selected dates.</p>`;
            return;
        }

        rooms.forEach(room => {
            const card = document.createElement('div');
            card.classList.add('room-card');
            card.innerHTML = `
                <h3>${room.typeName}</h3>
                <p>View: ${room.roomView}</p>
                <p>Price: $${room.price} / night</p>
                <p>image: ${room.image}</p>
                <p>Check-in: ${checkIn}</p>
                <p>Check-out: ${checkOut}</p>
                <p>Nights: ${nights}</p>
                <button class="book-btn">Book Now</button>
            `;
            roomBox.appendChild(card);

            // ======= Lưu tạm thông tin vào localStorage =======
            const bookBtn = card.querySelector('.book-btn');
            bookBtn.addEventListener('click', () => {
                const roomsVal = document.getElementById('select-rooms').value;
                const adultsVal = document.getElementById('select-adults').value;
                const childrenVal = document.getElementById('select-children').value;

                localStorage.setItem('stay_roomName', room.typeName);
                localStorage.setItem('stay_roomImage', room.image);
                localStorage.setItem('stay_roomView', room.roomView);
                localStorage.setItem('stay_price', room.price);
                localStorage.setItem('checkinDate', checkIn);
                localStorage.setItem('checkoutDate', checkOut);
                localStorage.setItem('stay_nights', nights);
                localStorage.setItem('stay_rooms', roomsVal);
                localStorage.setItem('stay_adults', adultsVal);
                localStorage.setItem('stay_children', childrenVal);
                localStorage.setItem('stay_guests', `${roomsVal} Room(s) - ${parseInt(adultsVal)+parseInt(childrenVal)} Guests`);

                window.location.href = './reservation-page-4.html';
            });
        });
    };

    // ======= Search button =======
    if(searchBtn){
        searchBtn.addEventListener('click', async (e)=>{
            e.preventDefault();
            const checkInVal = checkinInput.value;
            const checkOutVal = checkoutInput.value;
            const roomsVal = document.getElementById('select-rooms').value;
            const adultsVal = parseInt(document.getElementById('select-adults').value);
            const childrenVal = parseInt(document.getElementById('select-children').value);

            const toIso = (dateStr) => {
                const parts = dateStr.split('/');
                return `${parts[2]}-${parts[1].padStart(2,'0')}-${parts[0].padStart(2,'0')}`;
            };

            const requestData = {
                checkIn: toIso(checkInVal),
                checkOut: toIso(checkOutVal),
                rooms: roomsVal,
                adults: adultsVal,
                children: childrenVal
            };

            try {
                const res = await fetch('http://localhost:8080/api/reservation/search',{
                    method:'POST',
                    headers:{'Content-Type':'application/json'},
                    body: JSON.stringify(requestData)
                });
                const data = await res.json();
                const nights = (parseDisplayDate(checkOutVal) - parseDisplayDate(checkInVal))/(1000*60*60*24);
                renderRooms(data, checkInVal, checkOutVal, nights);
            } catch(err){
                console.error('Fetch error:', err);
                roomBox.innerHTML = `<p>Error fetching rooms.</p>`;
            }
        });
    }
});
