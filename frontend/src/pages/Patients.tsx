import { useEffect, useState } from "react";
import api from "../api/api";
import type { Patient } from "../models/Patient";
import PatientForm from "../components/PatientForm";
import PatientDetails from "./PatientDetails";
import PatientHistory from "./PatientHistory";


export default function Patients() {

    const [patients, setPatients] = useState<Patient[]>([]);

    const [showForm, setShowForm] = useState(false);

    const [selectedPatient, setSelectedPatient] = useState<Patient | null>(null);

    const [viewPatientId, setViewPatientId] = useState<number | null>(null);

    const [historyPatientId, setHistoryPatientId] = useState<number | null>(null);

    const loadPatients = async () => {

        try {

            const response = await api.get<Patient[]>(
                "/api/v1/patients"
            );

            setPatients(response.data);

        }

        catch (error) {

            console.error(error);

        }

    };

    useEffect(() => {

        loadPatients();

    }, []);

    const handleAdd = () => {

        setSelectedPatient(null);

        setShowForm(true);

    };

    const handleEdit = (patient: Patient) => {

        setSelectedPatient(patient);

        setShowForm(true);

    };

    const handleSuccess = () => {

        setShowForm(false);

        setSelectedPatient(null);

        loadPatients();

    };

    const handleCancel = () => {

        setShowForm(false);

        setSelectedPatient(null);

    };

    if (viewPatientId !== null) {

        return (

            <PatientDetails

                patientId={viewPatientId}

                onBack={() => setViewPatientId(null)}

            />

        );

    }

    if (historyPatientId !== null) {

        return (

            <PatientHistory

                patientId={historyPatientId}

                onBack={() => setHistoryPatientId(null)}

            />

        );

    }

    return (

        <div style={{ padding: "20px" }}>

            <h2>Patients</h2>

            {!showForm && (

                <button
                    onClick={handleAdd}
                    style={{
                        marginBottom: "20px"
                    }}
                >
                    Add Patient
                </button>

            )}

            {showForm && (

                <PatientForm

                    patient={selectedPatient}

                    onSuccess={handleSuccess}

                    onCancel={handleCancel}

                />

            )}

            {!showForm && (

                <table
                    border={1}
                    cellPadding={10}
                >

                    <thead>

                        <tr>

                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Date Of Birth</th>
                            <th>Gender</th>
                            <th>Address</th>
                            <th>Phone</th>
                            <th>Actions</th>

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

                                <td>

                                    <button
                                        onClick={() =>
                                            setViewPatientId(patient.id)
                                        }
                                    >
                                        View
                                    </button>

                                    <button
                                        onClick={() =>
                                            handleEdit(patient)
                                        }
                                        style={{ marginLeft: "5px" }}
                                    >
                                        Edit
                                    </button>

                                    <button
                                        onClick={() =>
                                            setHistoryPatientId(patient.id)
                                        }
                                        style={{ marginLeft: "5px" }}
                                    >
                                        History
                                    </button>

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            )}

        </div>

    );

}