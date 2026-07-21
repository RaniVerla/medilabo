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

    const [errors, setErrors] = useState<Record<string, string>>({});

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

    const validate = () => {

        const validationErrors: Record<string, string> = {};

        if (!firstName.trim()) {
            validationErrors.firstName = "First Name is required";
        }

        if (!lastName.trim()) {
            validationErrors.lastName = "Last Name is required";
        }

        if (!dateOfBirth) {
            validationErrors.dateOfBirth = "Date of Birth is required";
        } else if (new Date(dateOfBirth) > new Date()) {
            validationErrors.dateOfBirth = "Date of Birth cannot be in the future";
        }

        if (!gender.trim()) {
            validationErrors.gender = "Gender is required";
        }

        if (!address.trim()) {
            validationErrors.address = "Address is required";
        }

        if (!phone.trim()) {
            validationErrors.phone = "Phone Number is required";
        } else if (!/^\d{10}$/.test(phone)) {
            validationErrors.phone = "Phone Number must be exactly 10 digits";
        }

        setErrors(validationErrors);

        return Object.keys(validationErrors).length === 0;
    };

    const savePatient = async () => {

        if (!validate()) {
            return;
        }

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

        } catch (error: any) {

            console.error(error);

            if (error.response?.data?.message) {
                alert(error.response.data.message);
            } else {
                alert("Operation failed.");
            }

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
                style={{ width: "100%" }}
            />
            {errors.firstName && (
                <div style={{ color: "red", fontSize: "12px" }}>
                    {errors.firstName}
                </div>
            )}

            <br /><br />

            <input
                placeholder="Last Name"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                style={{ width: "100%" }}
            />
            {errors.lastName && (
                <div style={{ color: "red", fontSize: "12px" }}>
                    {errors.lastName}
                </div>
            )}

            <br /><br />

            <input
                type="date"
                value={dateOfBirth}
                onChange={(e) => setDateOfBirth(e.target.value)}
                style={{ width: "100%" }}
            />
            {errors.dateOfBirth && (
                <div style={{ color: "red", fontSize: "12px" }}>
                    {errors.dateOfBirth}
                </div>
            )}

            <br /><br />

            <select
                value={gender}
                onChange={(e) => setGender(e.target.value)}
                style={{ width: "100%" }}
            >
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>

            {errors.gender && (
                <div style={{ color: "red", fontSize: "12px" }}>
                    {errors.gender}
                </div>
            )}

            <br /><br />

            <input
                placeholder="Address"
                value={address}
                maxLength={200}
                onChange={(e) => setAddress(e.target.value)}
                style={{ width: "100%" }}
            />
            {errors.address && (
                <div style={{ color: "red", fontSize: "12px" }}>
                    {errors.address}
                </div>
            )}

            <br /><br />

            <input
                placeholder="Phone"
                value={phone}
                maxLength={10}
                onChange={(e) =>
                    setPhone(e.target.value.replace(/\D/g, ""))
                }
                style={{ width: "100%" }}
            />
            {errors.phone && (
                <div style={{ color: "red", fontSize: "12px" }}>
                    {errors.phone}
                </div>
            )}

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