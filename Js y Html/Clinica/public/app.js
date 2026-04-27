const form = document.getElementById("appointmentForm");
const statusMessage = document.getElementById("statusMessage");
const appointmentsList = document.getElementById("appointmentsList");
const appointmentDateInput = document.getElementById("appointmentDate");
const appointmentTimeInput = document.getElementById("appointmentTime");
const timeSlotsContainer = document.getElementById("timeSlots");
const timeSlots = [
  "08:00",
  "08:30",
  "09:00",
  "09:30",
  "10:00",
  "10:30",
  "11:00",
  "11:30",
  "12:00",
  "12:30",
  "13:00",
  "13:30",
  "14:00",
  "14:30",
  "15:00",
  "15:30",
  "16:00",
  "16:30",
  "17:00",
  "17:30",
  "18:00",
];

function setStatus(message, isError = false) {
  statusMessage.textContent = message;
  statusMessage.classList.remove("success", "error");
  statusMessage.classList.add(isError ? "error" : "success");
}

function renderAppointments(appointments) {
  appointmentsList.innerHTML = "";

  if (!appointments.length) {
    const emptyItem = document.createElement("li");
    emptyItem.textContent = "No hay turnos cargados.";
    appointmentsList.appendChild(emptyItem);
    return;
  }

  appointments.forEach((appointment) => {
    const item = document.createElement("li");
    const patient = appointment.patient || {};
    item.innerHTML = `
      <strong>${patient.firstName || ""} ${patient.lastName || ""}</strong><br />
      Gmail: ${patient.gmail || "-"}<br />
      DNI: ${patient.dni || "-"}<br />
      Turno: ${appointment.appointmentDate} ${appointment.appointmentTime}<br />
      Especialidad: ${appointment.reason}
    `;
    appointmentsList.appendChild(item);
  });
}

function initializeDateValidation() {
  const today = new Date();
  const localDate = new Date(today.getTime() - today.getTimezoneOffset() * 60000)
    .toISOString()
    .split("T")[0];
  appointmentDateInput.min = localDate;
}

function renderTimeSlots() {
  timeSlotsContainer.innerHTML = "";
  timeSlots.forEach((slot) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "time-slot";
    button.dataset.time = slot;
    button.textContent = slot;
    button.addEventListener("click", () => {
      appointmentTimeInput.value = slot;
      document
        .querySelectorAll(".time-slot")
        .forEach((element) => element.classList.remove("active"));
      button.classList.add("active");
    });
    timeSlotsContainer.appendChild(button);
  });
}

async function loadAppointments() {
  try {
    const response = await fetch("/api/appointments");
    const appointments = await response.json();
    renderAppointments(appointments);
  } catch (_error) {
    setStatus("No se pudieron cargar los turnos.", true);
  }
}

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  const formData = new FormData(form);
  const payload = Object.fromEntries(formData.entries());
  const selectedDate = new Date(`${payload.appointmentDate}T00:00:00`);
  const now = new Date();
  const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

  if (selectedDate < today) {
    setStatus("La fecha del turno no puede ser una fecha pasada.", true);
    return;
  }

  if (!timeSlots.includes(payload.appointmentTime)) {
    setStatus("Debes elegir un horario valido entre 08:00 y 18:00.", true);
    return;
  }

  try {
    const response = await fetch("/api/appointments", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    const result = await response.json();

    if (!response.ok) {
      setStatus(result.error || "No se pudo registrar el turno.", true);
      return;
    }

    setStatus("Turno registrado correctamente.");
    form.reset();
    loadAppointments();
  } catch (_error) {
    setStatus("Error de conexion con el servidor.", true);
  }
});

initializeDateValidation();
renderTimeSlots();
loadAppointments();
