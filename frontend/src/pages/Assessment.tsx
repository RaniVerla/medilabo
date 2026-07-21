import { useEffect, useState } from "react";
import api from "../api/api";
import type { Assessment } from "../models/Assessment";

interface Props {
    patientId: number;
    onBack: () => void;
}

export default function Assessment({
    patientId,
    onBack
}: Props) {

    const [assessment, setAssessment] =
        useState<Assessment | null>(null);

    const [loading, setLoading] =
        useState(true);

    useEffect(() => {

        loadAssessment();

    }, [patientId]);

    const loadAssessment = async () => {

        try {

            const response =
                await api.get<Assessment>(
                    `/api/v3/assessments/${patientId}`
                );

            console.log("Assessment Response:", response.data);

            setAssessment(response.data);

        } catch (error) {

            console.error("Assessment Error:", error);

        } finally {

            setLoading(false);

        }

    };

    return (

        <div style={{ padding: "20px" }}>

            <h2>Diabetes Assessment Report</h2>

            {loading ? (

                <h3>Loading...</h3>

            ) : assessment ? (

                <table
                    border={1}
                    cellPadding={10}
                >

                    <tbody>

                        <tr>
                            <td><b>Patient Id</b></td>
                            <td>{assessment.patientId}</td>
                        </tr>

                        <tr>
                            <td><b>Name</b></td>
                            <td>{assessment.name}</td>
                        </tr>

                        <tr>
                            <td><b>Age</b></td>
                            <td>{assessment.age}</td>
                        </tr>

                        <tr>
                            <td><b>Gender</b></td>
                            <td>{assessment.gender}</td>
                        </tr>

                        <tr>
                            <td><b>Trigger Count</b></td>
                            <td>{assessment.triggerCount}</td>
                        </tr>

                        <tr>
                            <td><b>Risk Level</b></td>
                            <td>
                                <strong>{assessment.riskLevel}</strong>
                            </td>
                        </tr>

                    </tbody>

                </table>

            ) : (

                <h3>No Assessment Found</h3>

            )}

            <br />

            <button onClick={onBack}>
                Back
            </button>

        </div>

    );

}