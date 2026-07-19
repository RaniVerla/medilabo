import { useState } from "react";
import Login from "./pages/Login";
import Patients from "./pages/Patients";


function App() {

    const [isLoggedIn, setIsLoggedIn] = useState(
    !!localStorage.getItem("authToken"));


    const handleLoginSuccess = () => {

        setIsLoggedIn(true);

    };


    if (!isLoggedIn) {

        return (
            <Login
                onLoginSuccess={handleLoginSuccess}
            />
        );

    }


    return (
        <Patients />
    );

}


export default App;