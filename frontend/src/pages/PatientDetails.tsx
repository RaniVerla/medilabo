import { useEffect, useState } from "react";
import api from "../api/api";
import type { Patient } from "../models/Patient";


interface Props {

    patientId: number;

    onBack: () => void;

}


export default function PatientDetails({
    patientId,
    onBack
}: Props) {


    const [patient, setPatient] = useState<Patient | null>(null);


    const loadPatient = async () => {

        try {

            const response = await api.get<Patient>(
                `/api/v1/patients/${patientId}`
            );

            setPatient(response.data);


        } catch (error) {

            console.error(
                "Error loading patient:",
                error
            );

        }

    };



    useEffect(() => {

        loadPatient();

    }, [patientId]);



    if (!patient) {

        return (
            <div>
                Loading patient...
            </div>
        );

    }



    return (

        <div
            style={{
                padding: "20px",
                border: "1px solid gray",
                width: "400px"
            }}
        >

            <h2>
                Patient Details
            </h2>


            <p>
                <b>ID:</b> {patient.id}
            </p>


            <p>
                <b>First Name:</b> {patient.firstName}
            </p>


            <p>
                <b>Last Name:</b> {patient.lastName}
            </p>


            <p>
                <b>Date Of Birth:</b> {patient.dateOfBirth}
            </p>


            <p>
                <b>Gender:</b> {patient.gender}
            </p>


            <p>
                <b>Address:</b> {patient.address}
            </p>


            <p>
                <b>Phone:</b> {patient.phone}
            </p>



            <button onClick={onBack}>
                Back
            </button>


        </div>

    );

}