import { useState } from "react";
import api from "../api/api";

interface Props {
    patientId: number;
    onSuccess: () => void;
    onCancel: () => void;
}

export default function MedicalHistoryForm({
    patientId,
    onSuccess,
    onCancel
}: Props) {

    const [note, setNote] = useState("");
    const [physician, setPhysician] = useState("");

    const addMedicalNote = async () => {

        try {

            await api.post(
                `/api/v2/patients/${patientId}/medical-history`,
                {
                    note,
                    physician
                }
            );

            alert("Medical note added successfully");

            onSuccess();

        } catch (error) {

            console.error(error);

            alert("Failed to add medical note");

        }

    };

    return (

        <div
            style={{
                marginTop: "20px",
                border: "1px solid gray",
                padding: "20px",
                width: "400px"
            }}
        >

            <h3>Add Medical Note</h3>

            <div style={{ marginBottom: "15px" }}>

                <label>Physician</label>

                <br />

                <input
                    type="text"
                    value={physician}
                    onChange={(e) => setPhysician(e.target.value)}
                    style={{
                        width: "100%",
                        padding: "8px"
                    }}
                />

            </div>

            <div style={{ marginBottom: "15px" }}>

                <label>Medical Note</label>

                <br />

                <textarea
                    rows={6}
                    value={note}
                    onChange={(e) => setNote(e.target.value)}
                    style={{
                        width: "100%",
                        padding: "8px"
                    }}
                />

            </div>

            <button onClick={addMedicalNote}>
                Save
            </button>

            <button
                onClick={onCancel}
                style={{ marginLeft: "10px" }}
            >
                Cancel
            </button>

        </div>

    );

}