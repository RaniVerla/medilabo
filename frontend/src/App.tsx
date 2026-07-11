import { useEffect, useState } from "react";
import axios from "axios";
import api from "./api/api";
import type { Patient } from "./models/Patient";

function App() {

  const [patients, setPatients] = useState<Patient[]>([]);

  useEffect(() => {

    const loadPatients = async () => {

      try {

        // Step 2: Call secured endpoint with JWT
        const response = await api.get<Patient[]>("/patients");

        setPatients(response.data);

      } catch (error) {
        console.error("Error fetching patients:", error);
      }
    };

    loadPatients();

  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h2>Patients</h2>

      <table border={1} cellPadding={10}>
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Date of Birth</th>
            <th>Gender</th>
            <th>Address</th>
            <th>Phone</th>
          </tr>
        </thead>

        <tbody>
          {patients.map((patient) => (
            <tr key={patient.id}>
              <td>{patient.id}</td>
              <td>{patient.firstName}</td>
              <td>{patient.lastName}</td>
              <td>{patient.dateOfBirth}</td>
              <td>{patient.gender}</td>
              <td>{patient.address}</td>
              <td>{patient.phone}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;