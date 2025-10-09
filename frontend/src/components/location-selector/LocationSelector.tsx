import { useState, useEffect } from "react";
import { locationApi } from "@/domain/location/api";
import type { Location } from "@/domain/location/entity";
import styles from "./LocationSelector.module.css";

interface LocationSelectorProps {
    value: Location | null;
    onChange: (location: Location | null) => void;
    placeholder?: string;
}

export default function LocationSelector({ value, onChange, placeholder = "select location" }: LocationSelectorProps) {
    const [locations, setLocations] = useState<Location[]>([]);
    const [isOpen, setIsOpen] = useState(false);
    const [isCreating, setIsCreating] = useState(false);
    const [newLocation, setNewLocation] = useState<Partial<Location>>({
        name: "",
        x: 0,
        y: 0
    });
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        loadLocations();
    }, []);

    const loadLocations = async () => {
        try {
            setLoading(true);
            const data = await locationApi.getAllLocations();
            setLocations(data);
        } catch (error) {
            console.error("Failed to load locations:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleCreateLocation = async () => {
        if (!newLocation.name || newLocation.x === undefined || newLocation.y === undefined) {
            return;
        }

        try {
            const createdLocation = await locationApi.createLocation({
                id: 0,
                name: newLocation.name,
                x: newLocation.x,
                y: newLocation.y
            });

            setLocations(prev => [...prev, createdLocation]);
            onChange(createdLocation);
            setIsCreating(false);
            setNewLocation({ name: "", x: 0, y: 0 });
        } catch (error) {
            console.error("Failed to create location:", error);
        }
    };

    const handleSelectLocation = (location: Location) => {
        onChange(location);
        setIsOpen(false);
    };

    return (
        <div className={styles.locationSelector}>
            <div
                className={styles.locationSelectorTrigger}
                onClick={() => setIsOpen(!isOpen)}
            >
                {value ? `${value.name} (${value.x}, ${value.y})` : placeholder}
                <span className={styles.arrow}>{isOpen ? "▲" : "▼"}</span>
            </div>

            {isOpen && (
                <div className={styles.locationSelectorDropdown}>
                    {loading ? (
                        <div className={styles.loading}>Loading...</div>
                    ) : (
                        <>
                            <div className={styles.locationList}>
                                {locations.map(location => (
                                    <div
                                        key={location.id}
                                        className={styles.locationItem}
                                        onClick={() => handleSelectLocation(location)}
                                    >
                                        {location.name} ({location.x}, {location.y})
                                    </div>
                                ))}
                            </div>

                            <div className={styles.locationActions}>
                                {isCreating ? (
                                    <div className={styles.createLocationForm}>
                                        <input
                                            type="text"
                                            placeholder="name"
                                            value={newLocation.name || ""}
                                            onChange={(e) => setNewLocation(prev => ({ ...prev, name: e.target.value }))}
                                        />
                                        <input
                                            type="number"
                                            placeholder="x"
                                            value={newLocation.x || ""}
                                            onChange={(e) => setNewLocation(prev => ({ ...prev, x: Number(e.target.value) }))}
                                        />
                                        <input
                                            type="number"
                                            placeholder="y"
                                            value={newLocation.y || ""}
                                            onChange={(e) => setNewLocation(prev => ({ ...prev, y: Number(e.target.value) }))}
                                        />
                                        <div className={styles.formActions}>
                                            <button onClick={handleCreateLocation}>create</button>
                                            <button onClick={() => setIsCreating(false)}>cancel</button>
                                        </div>
                                    </div>
                                ) : (
                                    <button
                                        className={styles.createNewBtn}
                                        onClick={() => setIsCreating(true)}
                                    >
                                        Create New Location
                                    </button>
                                )}
                            </div>
                        </>
                    )}
                </div>
            )}
        </div>
    );
}
