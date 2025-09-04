const express = require("express");
const fs = require("fs");
const cookieParser = require("cookie-parser");
const multer = require("multer");
const path = require("path");

const app = express();
const port = 3000;

app.use(cookieParser());

// Configure multer for file storage
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, "uploads/"); // all files go into /uploads folder
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + path.extname(file.originalname)); // unique name
  },
});

const upload = multer({ storage });

// Serve paginated XML
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
  res.send(`<!-- Hello ${user} | Page ${page} -->\n${xmlData}`);
});

// Upload API (single file)
app.post("/upload", upload.single("file"), (req, res) => {
  if (!req.file) {
    return res.status(400).send("No file uploaded");
  }
  res.send({
    message: "File uploaded successfully",
    file: req.file,
  });
});

// Upload API (multiple files)
app.post("/upload-multiple", upload.array("files", 5), (req, res) => {
  if (!req.files || req.files.length === 0) {
    return res.status(400).send("No files uploaded");
  }
  res.send({
    message: "Multiple files uploaded successfully",
    files: req.files,
  });
});

// Logout
app.get("/logout", (req, res) => {
  res.clearCookie("user");
  res.clearCookie("sessionId");
  res.send("Cookies cleared!");
});

app.listen(port, () => {
  console.log(`XML server running at http://localhost:${port}/travelers?page=1`);
  console.log(`Upload API available at http://localhost:${port}/upload`);
  console.log(`Multi Upload API available at http://localhost:${port}/upload-multiple`);
});