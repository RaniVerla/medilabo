import { useState } from "react";
import api from "../api/api";


interface LoginProps {
    onLoginSuccess: () => void;
}


export default function Login(
    { onLoginSuccess }: LoginProps
) {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");


    const handleLogin = async () => {

        try {

            const response = await api.post("/login", {
                username,
                password
            });


            const token = response.data.token;


            localStorage.setItem(
                "authToken",
                token
            );


            console.log("Login successful");


            // Notify App.tsx that login succeeded
            onLoginSuccess();


        } catch (error) {

            console.error(
                "Login failed:",
                error
            );

        }
    };


    return (
        <div
            style={{
                width: "350px",
                margin: "80px auto",
                padding: "20px",
                border: "1px solid #ccc",
                borderRadius: "8px"
            }}
        >

            <h2>MediLabo Login</h2>


            <div style={{ marginBottom: "15px" }}>

                <label>
                    Username
                </label>


                <input
                    type="text"
                    value={username}
                    onChange={(e) =>
                        setUsername(e.target.value)
                    }
                    style={{
                        width: "100%",
                        padding: "8px"
                    }}
                />

            </div>


            <div style={{ marginBottom: "15px" }}>

                <label>
                    Password
                </label>


                <input
                    type="password"
                    value={password}
                    onChange={(e) =>
                        setPassword(e.target.value)
                    }
                    style={{
                        width: "100%",
                        padding: "8px"
                    }}
                />

            </div>


            <button
                onClick={handleLogin}
                style={{
                    width: "100%",
                    padding: "10px"
                }}
            >
                Login
            </button>


        </div>
    );
}