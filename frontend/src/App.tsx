import axios from 'axios'
import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  //  {/* TODO : Get the patients in the backend  */}
  useEffect(() => {
    const fetchTodos = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/api/v1/patients`);
        // const response = { data: [{ "firstName": "Patient 1" }] };
        console.log(response.data);
      } catch (error) {
        console.error('Error fetching todos:', error);
      }
    };
    fetchTodos();
  }, []);
  return (
    <>

      {/* TODO : Display patients in table using array map funtion */}
    </>
  )
}

export default App
