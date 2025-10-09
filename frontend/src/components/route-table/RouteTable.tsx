import React from 'react';
import type { RouteDto } from '@/domain';
import styles from './RouteTable.module.css';

const RouteTable: React.FC<{ routes: RouteDto[] }> = ({ routes }) => {
    return (
        <div className={styles.container}>
            <h2 className={styles.title}>Route Table</h2>
            <table className={styles.table}>
                <thead className={styles.header}>
                    <tr>
                        <th className={styles.headerCell}>id</th>
                        <th className={styles.headerCell}>name</th>
                        <th className={styles.headerCell}>coordinates</th>
                        <th className={styles.headerCell}>creation date</th>
                        <th className={styles.headerCell}>from</th>
                        <th className={styles.headerCell}>to</th>
                        <th className={styles.headerCell}>distance</th>
                        <th className={styles.headerCell}>rating</th>
                    </tr>
                </thead>
                <tbody>
                    {routes.map((route) => (
                        <tr key={route.id} className={styles.row}>
                            <td className={styles.cell}>{route.id}</td>
                            <td className={styles.cell}>{route.name}</td>
                            <td className={styles.cell}>{route.coordinates.x}, {route.coordinates.y}</td>
                            <td className={styles.cell}>{route.creationDate}</td>
                            <td className={styles.cell}>{route.from?.x}, {route.from?.y}</td>
                            <td className={styles.cell}>{route.to?.x}, {route.to?.y}</td>
                            <td className={styles.cell}>{route.distance}</td>
                            <td className={styles.cell}>{route.rating}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default RouteTable;
