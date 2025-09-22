const express = require("express");
const bodyParser = require("body-parser");
const path = require("path");
const auth = require("http-auth");
const authConnect = require("http-auth-connect");
const crypto = require("crypto");

const app = express();
const PORT = 3000;

// Middleware
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, "public")));

// ======================= AUTHENTICATION =======================

// --- Basic Authentication ---
const basic = auth.basic(
  { realm: "Basic Area" },
  (username, password, callback) => {
    callback(username === "adminBas" && password === "passwordBas");
  }
);

// --- Digest Authentication ---
const digest = auth.digest(
  { realm: "Digest Area", qop: "auth" },
  (username, callback) => {
    if (username === "adminDig") {
      const ha1 = crypto
        .createHash("md5")
        .update("adminDig:Digest Area:passwordDig")
        .digest("hex");
      callback(ha1); // only HA1!
    } else {
      callback(); // no args for failure
    }
  }
);

// ======================= BEARER TOKEN AUTH =======================
const BEARER_TOKEN = "mySecretBearerToken"; // you can generate dynamically if needed

function bearerAuth(req, res, next) {
  const authHeader = req.headers["authorization"];
  if (!authHeader) {
    return res.status(401).json({ message: "Missing Authorization header" });
  }

  const parts = authHeader.split(" ");
  if (parts.length !== 2 || parts[0] !== "Bearer" || parts[1] !== BEARER_TOKEN) {
    return res.status(401).json({ message: "Invalid Bearer token" });
  }

  next(); // token valid -> continue
}



// ======================= DATA =======================
let emps = [
  { id: 1, name: "John Doe", role: "Software Engineer", salary: 60000 },
  { id: 2, name: "Jane Smith", role: "Project Manager", salary: 75000 },
  { id: 3, name: "Mike Johnson", role: "Tester", salary: 50000 },
];

// ======================= ROUTES =======================
app.get("/", (req, res) => {
  res.send("Express App with Basic & Digest Authentication!");
});

// ----- BASIC AUTH CRUD ROUTES -----
app.get("/api/basic/emps", authConnect(basic), (req, res) => res.json(emps));
app.get("/api/basic/emps/:id", authConnect(basic), (req, res) => {
  const id = parseInt(req.params.id);
  const emp = emps.find((e) => e.id === id);
  if (!emp) return res.status(404).json({ message: "Emp not found" });
  res.json(emp);
});
app.post("/api/basic/emps", authConnect(basic), (req, res) => {
  const newEmp = { id: emps.length + 1, ...req.body };
  emps.push(newEmp);
  res.status(201).json(newEmp);
});
app.put("/api/basic/emps/:id", authConnect(basic), (req, res) => {
  const id = parseInt(req.params.id);
  const emp = emps.find((e) => e.id === id);
  if (!emp) return res.status(404).json({ message: "Emp not found" });
  Object.assign(emp, req.body);
  res.json(emp);
});
app.delete("/api/basic/emps/:id", authConnect(basic), (req, res) => {
  const id = parseInt(req.params.id);
  const index = emps.findIndex((e) => e.id === id);
  if (index === -1) return res.status(404).json({ message: "Emp not found" });
  const deleted = emps.splice(index, 1);
  res.json({ message: "Emp deleted", deleted });
});

// ----- DIGEST AUTH CRUD ROUTES -----
app.get("/api/digest/emps", authConnect(digest), (req, res) => res.json(emps));
app.get("/api/digest/emps/:id", authConnect(digest), (req, res) => {
  const id = parseInt(req.params.id);
  const emp = emps.find((e) => e.id === id);
  if (!emp) return res.status(404).json({ message: "Emp not found" });
  res.json(emp);
});
app.post("/api/digest/emps", authConnect(digest), (req, res) => {
  const newEmp = { id: emps.length + 1, ...req.body };
  emps.push(newEmp);
  res.status(201).json(newEmp);
});
app.put("/api/digest/emps/:id", authConnect(digest), (req, res) => {
  const id = parseInt(req.params.id);
  const emp = emps.find((e) => e.id === id);
  if (!emp) return res.status(404).json({ message: "Emp not found" });
  Object.assign(emp, req.body);
  res.json(emp);
});
app.delete("/api/digest/emps/:id", authConnect(digest), (req, res) => {
  const id = parseInt(req.params.id);
  const index = emps.findIndex((e) => e.id === id);
  if (index === -1) return res.status(404).json({ message: "Emp not found" });
  const deleted = emps.splice(index, 1);
  res.json({ message: "Emp deleted", deleted });
});

// ----- BEARER AUTH CRUD ROUTES -----
app.get("/api/bearer/emps", bearerAuth, (req, res) => res.json(emps));

app.get("/api/bearer/emps/:id", bearerAuth, (req, res) => {
  const id = parseInt(req.params.id);
  const emp = emps.find((e) => e.id === id);
  if (!emp) return res.status(404).json({ message: "Emp not found" });
  res.json(emp);
});

app.post("/api/bearer/emps", bearerAuth, (req, res) => {
  const newEmp = { id: emps.length + 1, ...req.body };
  emps.push(newEmp);
  res.status(201).json(newEmp);
});

app.put("/api/bearer/emps/:id", bearerAuth, (req, res) => {
  const id = parseInt(req.params.id);
  const emp = emps.find((e) => e.id === id);
  if (!emp) return res.status(404).json({ message: "Emp not found" });
  Object.assign(emp, req.body);
  res.json(emp);
});

app.delete("/api/bearer/emps/:id", bearerAuth, (req, res) => {
  const id = parseInt(req.params.id);
  const index = emps.findIndex((e) => e.id === id);
  if (index === -1) return res.status(404).json({ message: "Emp not found" });
  const deleted = emps.splice(index, 1);
  res.json({ message: "Emp deleted", deleted });
});

// ======================= START SERVER =======================
app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});
