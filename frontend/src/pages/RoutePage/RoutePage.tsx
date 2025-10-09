import { useState } from "react";
import { useNavigate } from "react-router-dom";
import type { Location, RouteDto } from "@/domain";
import { LocationSelector } from "@/components";
import styles from "./RoutePage.module.css";

export default function RoutePage() {
    const navigate = useNavigate();

    const [name, setName] = useState<string>("");
    const [x, setCoordinateX] = useState<number>(0);
    const [y, setCoordinateY] = useState<number>(0);
    const [creationDate, setCreationDate] = useState<string>("");
    const [from, setFrom] = useState<Location | null>(null);
    const [to, setTo] = useState<Location | null>(null);

    const [route, setRoute] = useState<RouteDto>({
        id: 0,
        name,
        coordinates: { x, y },
        creationDate,
        from,
        to,
        distance: null,
        rating: 0
    });

    const handleCreateRoute = () => {
        console.log("Button clicked, navigating to /");
        navigate("/");
    };

    return (
        <div className={styles.routePage}>
            <h1 className={styles.title}>create new route</h1>
            <form className={styles.form}>
                <input type="text" placeholder="name" onChange={e => setName(e.target.value)} />
                <input type="text" placeholder="x" onChange={e => setCoordinateX(Number(e.target.value))} />
                <input type="text" placeholder="y" onChange={e => setCoordinateY(Number(e.target.value))} />
                <input type="text" placeholder="creation date" onChange={e => setCreationDate(e.target.value)} />
                <LocationSelector
                    value={from}
                    onChange={setFrom}
                    placeholder="select from location"
                />
                <LocationSelector
                    value={to}
                    onChange={setTo}
                    placeholder="select to location"
                />
            </form>
            <div className={styles.buttons}>
                <button className={`${styles.button} ${styles.createButton}`} onClick={handleCreateRoute}>create</button>
                <button className={`${styles.button} ${styles.cancelButton}`} onClick={() => {
                    navigate("/");
                }}>cancel</button>
            </div>
        </div>
    )
}