import { useEffect, useState } from "react";
import api from "../api/api";
import type { MedicalHistory } from "../models/MedicalHistory";
import MedicalHistoryForm from "../components/MedicalHistoryForm";

interface Props {
    patientId: number;
    onBack: () => void;
}

export default function PatientHistory({
    patientId,
    onBack
}: Props) {

    const [history, setHistory] = useState<MedicalHistory[]>([]);
    const [showForm, setShowForm] = useState(false);
    const [loading, setLoading] = useState(false);


    const loadHistory = async () => {

        try {

            setLoading(true);

            const response = await api.get(
                `/api/v2/patients/${patientId}/medical-history`
            );


            console.log("API RESPONSE:", response.data);


            // Handles both possible backend responses
            if (Array.isArray(response.data)) {

                setHistory(response.data);

            } else {

                setHistory(response.data.medicalHistory || []);

            }


        } catch (error) {

            console.error("History Error:", error);

            setHistory([]);

        } finally {

            setLoading(false);

        }

    };


    // Load history when page opens
    useEffect(() => {

        loadHistory();

    }, [patientId]);



    const handleSuccess = () => {

        setShowForm(false);

        loadHistory();

    };


    return (

        <div style={{ padding: "20px" }}>

            <h2>Medical History</h2>


            {!showForm && (

                <button
                    onClick={() => setShowForm(true)}
                    style={{ marginBottom: "20px" }}
                >
                    Add Medical Note
                </button>

            )}



            {showForm && (

                <MedicalHistoryForm
                    patientId={patientId}
                    onSuccess={handleSuccess}
                    onCancel={() => setShowForm(false)}
                />

            )}



            {loading && (

                <p>Loading medical history...</p>

            )}



            {!loading && history.length === 0 && (

                <p>No medical history found.</p>

            )}



            {!loading && history.length > 0 && (

                <table
                    border={1}
                    cellPadding={10}
                >

                    <thead>

                        <tr>

                            <th>Physician</th>
                            <th>Medical Note</th>
                            <th>Created At</th>

                        </tr>

                    </thead>


                    <tbody>

                        {history.map((item, index) => (

                            <tr key={index}>

                                <td>{item.physician}</td>

                                <td>{item.note}</td>

                                <td>
                                    {new Date(item.createdAt)
                                        .toLocaleString()}
                                </td>

                            </tr>

                        ))}

                    </tbody>


                </table>

            )}



            <br />

            <button onClick={onBack}>

                Back

            </button>


        </div>

    );

}