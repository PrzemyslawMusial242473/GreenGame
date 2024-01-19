import axios from "axios";

export default axios.create({
    baseURL: "http://localhost:8080/secured/api/inventory",
    headers: {
        "Content-type": "application/json"
    },
    withCredentials: true
});