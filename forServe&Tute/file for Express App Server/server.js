const express = require("express");
const fs = require("fs");
const cookieParser = require("cookie-parser");

const app = express();
const port = 3000;

app.use(cookieParser());

// Serve paginated XML
app.get("/travelers", (req, res) => {
  res.set("Content-Type", "application/xml");

  // Get page from query (default 1)
  const page = req.query.page || "1";
  const fileName = `travelers${page}.xml`; // e.g. store1.xml, store2.xml

  // If file does not exist → send error
  if (!fs.existsSync(fileName)) {
    return res.status(404).send(`<error>Page ${page} not found</error>`);
  }

  // Set cookies
  res.cookie("user", "TestUser", { httpOnly: true, maxAge: 60000 });
  res.cookie("sessionId", "ABC123XYZ", { httpOnly: true, maxAge: 60000 });

  // Debug: print cookies received from client
  console.log("Cookies received from client:", JSON.stringify(req.cookies));

  const user = req.cookies.user || "Guest";

  // Load XML file for the given page
  const xmlData = fs.readFileSync(fileName, "utf8");

  // Send XML with a comment
  res.send(`<!-- Hello ${user} | Page ${page} -->\n${xmlData}`);
});

// Logout: clear cookies
app.get("/logout", (req, res) => {
  res.clearCookie("user");
  res.clearCookie("sessionId");
  res.send("Cookies cleared!");
});

app.listen(port, () => {
  console.log(`XML server running at http://localhost:${port}/travelers?page=1`);
});