const express = require("express");
const fs = require("fs");
const app = express();
const port = 3000;

app.use(express.json());

// Helpers
const readData = () => {
  const data = fs.readFileSync("students.json");
  return JSON.parse(data);
};

const writeData = (data) => {
  fs.writeFileSync("students.json", JSON.stringify(data, null, 2));
};

// GET all students
app.get("/students", (req, res) => {
  const data = readData();
  res.json(data.students);
});

// GET student by ID
app.get("/students/:id", (req, res) => {
  const data = readData();
  const student = data.students.find((s) => s.id == req.params.id);
  student ? res.json(student) : res.status(404).send("Student not found");
});

// POST add student
app.post("/students", (req, res) => {
  const data = readData();
  const newStudent = { id: Date.now().toString(), ...req.body };
  data.students.push(newStudent);
  writeData(data);
  res.status(201).json(newStudent);
});

// PUT update student
app.put("/students/:id", (req, res) => {
  const data = readData();
  const index = data.students.findIndex((s) => s.id == req.params.id);
  if (index !== -1) {
    data.students[index] = { ...data.students[index], ...req.body };
    writeData(data);
    res.json(data.students[index]);
  } else {
    res.status(404).send("Student not found");
  }
});

// DELETE student
app.delete("/students/:id", (req, res) => {
  const data = readData();
  const updated = data.students.filter((s) => s.id != req.params.id);
  if (updated.length !== data.students.length) {
    writeData({ students: updated });
    res.send("Student deleted");
  } else {
    res.status(404).send("Student not found");
  }
});

// Start server
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}/students`);
});
