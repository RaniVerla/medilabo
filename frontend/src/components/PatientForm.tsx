import { useEffect, useState } from "react";
import api from "../api/api";
import type { Patient } from "../models/Patient";

interface PatientFormProps {
    patient?: Patient | null;
    onSuccess: () => void;
    onCancel: () => void;
}

export default function PatientForm({
    patient,
    onSuccess,
    onCancel
}: PatientFormProps) {

    const isEdit = patient !== null && patient !== undefined;

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [dateOfBirth, setDateOfBirth] = useState("");
    const [gender, setGender] = useState("");
    const [address, setAddress] = useState("");
    const [phone, setPhone] = useState("");

    useEffect(() => {

        if (patient) {
            setFirstName(patient.firstName);
            setLastName(patient.lastName);
            setDateOfBirth(patient.dateOfBirth);
            setGender(patient.gender);
            setAddress(patient.address);
            setPhone(patient.phone);
        }

    }, [patient]);

    const savePatient = async () => {

        const request = {
            firstName,
            lastName,
            dateOfBirth,
            gender,
            address,
            phone
        };

        try {

            if (isEdit) {

                await api.put(
                    `/api/v1/patients/${patient!.id}`,
                    request
                );

                alert("Patient updated successfully.");

            } else {

                await api.post(
                    "/api/v1/patients",
                    request
                );

                alert("Patient added successfully.");

            }

            onSuccess();

        } catch (error) {

            console.error(error);
            alert("Operation failed.");

        }

    };

    return (

        <div
            style={{
                border: "1px solid gray",
                padding: "20px",
                width: "400px",
                marginTop: "20px"
            }}
        >

            <h3>
                {isEdit ? "Edit Patient" : "Add Patient"}
            </h3>

            <input
                placeholder="First Name"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Last Name"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
            />

            <br /><br />

            <input
                type="date"
                value={dateOfBirth}
                onChange={(e) => setDateOfBirth(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Gender"
                value={gender}
                onChange={(e) => setGender(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Address"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Phone"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
            />

            <br /><br />

            <button onClick={savePatient}>
                {isEdit ? "Update Patient" : "Add Patient"}
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