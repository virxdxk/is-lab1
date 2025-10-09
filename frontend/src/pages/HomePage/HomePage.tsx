import { Header, RouteTable } from "@/components";
import { useNavigate } from "react-router-dom";

export default function HomePage() {
    const navigate = useNavigate();

    const handleAddRoute = () => {
        console.log("Button clicked, navigating to /add-route");
        navigate("/add-route");
    };

    return (
        <div style={{ padding: '20px', position: 'relative', zIndex: 1 }}>
            <Header />
            <button
                onClick={handleAddRoute}
                style={{
                    padding: '10px 20px',
                    fontSize: '16px',
                    backgroundColor: '#646cff',
                    color: 'white',
                    border: 'none',
                    borderRadius: '5px',
                    cursor: 'pointer',
                    marginBottom: '20px'
                }}
            >
                add new route
            </button>
            <RouteTable routes={[]} />
        </div>
    )
}