const express = require("express");
const fs = require("fs");
const cookieParser = require("cookie-parser");
const multer = require("multer");
const path = require("path");
const xml2js = require("xml2js");   // XML parser + builder

const app = express();
const port = 3000;

app.use(cookieParser());
app.use(express.json()); // allow JSON input from client

// ---------------- Security Middleware ----------------
const API_KEY = "MY_SECRET_KEY_123";  // 👈 set your secret key

function checkApiKey(req, res, next) {
  // Allow GET requests without API key
  if (req.method === "GET") {
    return next();
  }

  const clientKey = req.header("x-api-key");
  if (!clientKey || clientKey !== API_KEY) {
    return res.status(401).json({ error: "Unauthorized: Invalid or missing API key" });
  }
  next(); // continue if valid
}

// Apply middleware to ALL routes
app.use(checkApiKey);

// ---------------- Multer Config ----------------
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, "uploads/"); // all files go into /uploads folder
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + path.extname(file.originalname)); // unique name
  },
});

const upload = multer({ storage });
const builder = new xml2js.Builder(); // convert JS → XML

// ---------------- Travelers API ----------------

// GET travelers (read XML file)
app.get("/travelers", (req, res) => {
  res.set("Content-Type", "application/xml");

  const page = req.query.page || "1";
  const fileName = `travelers${page}.xml`;

  if (!fs.existsSync(fileName)) {
    return res.status(404).send(`<error>Page ${page} not found</error>`);
  }

  res.cookie("user", "TestUser", { httpOnly: true, maxAge: 60000 });
  res.cookie("sessionId", "ABC123XYZ", { httpOnly: true, maxAge: 60000 });

  console.log("Cookies received from client:", JSON.stringify(req.cookies));
  const user = req.cookies.user || "Guest";

  const xmlData = fs.readFileSync(fileName, "utf8");
  return res.status(200).send(`<!-- Hello ${user} | Page ${page} -->\n${xmlData}`);
});

// POST traveler (append JSON into XML file)
app.post("/travelers", (req, res) => {
  const page = req.query.page || "1";
  const fileName = `travelers${page}.xml`;

  const newTraveler = req.body; // JSON sent by client

  // Read existing XML (or create if missing)
  let travelersData = { travelers: { traveler: [] } };

  if (fs.existsSync(fileName)) {
    const xmlData = fs.readFileSync(fileName, "utf8");
    xml2js.parseString(xmlData, (err, result) => {
      if (err) {
        return res.status(500).json({ error: "Error parsing XML" });
      }

      travelersData = result;
      if (!Array.isArray(travelersData.travelers.traveler)) {
        travelersData.travelers.traveler = [];
      }

      travelersData.travelers.traveler.push(newTraveler);

      const updatedXml = builder.buildObject(travelersData);
      fs.writeFileSync(fileName, updatedXml);

      return res.status(201).json({
        message: "Traveler added successfully",
        traveler: newTraveler,
      });
    });
  } else {
    // First traveler if file does not exist
    travelersData.travelers.traveler.push(newTraveler);
    const updatedXml = builder.buildObject(travelersData);
    fs.writeFileSync(fileName, updatedXml);

    return res.status(201).json({
      message: "Traveler added successfully (new file created)",
      traveler: newTraveler,
    });
  }
});

// ---------------- File Upload APIs ----------------

// Upload API (single file)
app.post("/upload", upload.single("file"), (req, res) => {
  if (!req.file) {
    return res.status(400).json({ error: "No file uploaded" });
  }
  return res.status(201).json({
    message: "File uploaded successfully",
    file: req.file,
  });
});

// Upload API (multiple files)
app.post("/upload-multiple", upload.array("files", 5), (req, res) => {
  if (!req.files || req.files.length === 0) {
    return res.status(400).json({ error: "No files uploaded" });
  }
  return res.status(201).json({
    message: "Multiple files uploaded successfully",
    files: req.files,
  });
});

// Logout
app.get("/logout", (req, res) => {
  res.clearCookie("user");
  res.clearCookie("sessionId");
  return res.status(200).send("Cookies cleared!");
});

// Start server
app.listen(port, () => {
  console.log(`XML server running at http://localhost:${port}/travelers?page=1`);
  console.log(`Upload API available at http://localhost:${port}/upload`);
  console.log(`Multi Upload API available at http://localhost:${port}/upload-multiple`);
});
