const express = require("express");
const fs = require("fs");
const path = require("path");

const app = express();
const PORT = process.env.PORT || 3000;
const DB_DIR = path.join(__dirname, "data");
const DB_PATH = path.join(DB_DIR, "db.json");

app.use(express.json());
app.use(express.static(path.join(__dirname, "public")));

function ensureDb() {
  if (!fs.existsSync(DB_DIR)) {
    fs.mkdirSync(DB_DIR, { recursive: true });
  }
  if (!fs.existsSync(DB_PATH)) {
    fs.writeFileSync(
      DB_PATH,
      JSON.stringify({ patients: [], appointments: [] }, null, 2),
      "utf-8"
    );
  }
}

function readDb() {
  ensureDb();
  return JSON.parse(fs.readFileSync(DB_PATH, "utf-8"));
}

function writeDb(data) {
  fs.writeFileSync(DB_PATH, JSON.stringify(data, null, 2), "utf-8");
}

function validateEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

function validateDni(dni) {
  const dniRegex = /^\d{7,10}$/;
  return dniRegex.test(String(dni));
}

const allowedSpecialties = [
  "Oncologia",
  "Traumatologia",
  "Cardiologia",
  "Pediatria",
  "Dermatologia",
  "Neurologia",
  "Ginecologia",
  "Clinica Medica",
];

const allowedTimes = [
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

function isPastDate(dateString) {
  const selectedDate = new Date(`${dateString}T00:00:00`);
  const today = new Date();
  const localToday = new Date(today.getFullYear(), today.getMonth(), today.getDate());
  return selectedDate < localToday;
}

app.post("/api/appointments", (req, res) => {
  const {
    gmail,
    birthDate,
    firstName,
    lastName,
    dni,
    reason,
    appointmentDate,
    appointmentTime,
  } = req.body;

  const requiredFields = {
    gmail,
    birthDate,
    firstName,
    lastName,
    dni,
    reason,
    appointmentDate,
    appointmentTime,
  };

  for (const [field, value] of Object.entries(requiredFields)) {
    if (!value || String(value).trim() === "") {
      return res.status(400).json({ error: `El campo ${field} es obligatorio.` });
    }
  }

  if (!validateEmail(gmail)) {
    return res.status(400).json({ error: "El gmail no tiene un formato valido." });
  }

  if (!validateDni(dni)) {
    return res
      .status(400)
      .json({ error: "El DNI debe contener entre 7 y 10 digitos numericos." });
  }

  if (!allowedSpecialties.includes(reason.trim())) {
    return res.status(400).json({ error: "Debes seleccionar una especialidad valida." });
  }

  if (!allowedTimes.includes(appointmentTime.trim())) {
    return res
      .status(400)
      .json({ error: "El horario debe ser entre 08:00 y 18:00." });
  }

  if (isPastDate(appointmentDate.trim())) {
    return res
      .status(400)
      .json({ error: "La fecha del turno no puede ser una fecha pasada." });
  }

  const db = readDb();
  const normalizedGmail = gmail.trim().toLowerCase();
  const normalizedDni = String(dni).trim();

  let patient = db.patients.find(
    (p) =>
      p.gmail.toLowerCase() === normalizedGmail &&
      String(p.dni).trim() === normalizedDni
  );

  if (!patient) {
    patient = {
      id: Date.now().toString(),
      gmail: normalizedGmail,
      birthDate,
      firstName: firstName.trim(),
      lastName: lastName.trim(),
      dni: normalizedDni,
      createdAt: new Date().toISOString(),
    };
    db.patients.push(patient);
  }

  const appointment = {
    id: `${Date.now()}-${Math.floor(Math.random() * 1000)}`,
    patientId: patient.id,
    reason: reason.trim(),
    appointmentDate,
    appointmentTime,
    createdAt: new Date().toISOString(),
  };

  db.appointments.push(appointment);
  writeDb(db);

  return res.status(201).json({
    message: "Turno registrado correctamente.",
    appointment,
    patient,
  });
});

app.get("/api/appointments", (_req, res) => {
  const db = readDb();
  const appointmentsWithPatient = db.appointments
    .map((appointment) => {
      const patient = db.patients.find((p) => p.id === appointment.patientId);
      return {
        ...appointment,
        patient: patient
          ? {
              firstName: patient.firstName,
              lastName: patient.lastName,
              gmail: patient.gmail,
              dni: patient.dni,
            }
          : null,
      };
    })
    .sort((a, b) => {
      const aDate = new Date(`${a.appointmentDate}T${a.appointmentTime}`);
      const bDate = new Date(`${b.appointmentDate}T${b.appointmentTime}`);
      return aDate - bDate;
    });

  res.json(appointmentsWithPatient);
});

app.get("/api/config", (_req, res) => {
  res.json({
    specialties: allowedSpecialties,
    times: allowedTimes,
  });
});

app.listen(PORT, () => {
  ensureDb();
  console.log(`Servidor activo en http://localhost:${PORT}`);
});
