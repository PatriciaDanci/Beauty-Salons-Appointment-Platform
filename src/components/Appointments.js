import React, { useEffect, useState } from "react";
import "./Appointments.css";

const Appointments = () => {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAppointments = async () => {
      const userId = localStorage.getItem("userId"); // Get userId from localStorage
      if (!userId) {
        console.error("User ID is missing");
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/api/appointments/user/${userId}`);
        if (!response.ok) {
          throw new Error("Failed to fetch appointments");
        }
        const data = await response.json();
        setAppointments(data);
      } catch (error) {
        console.error("Error fetching appointments:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchAppointments();
  }, []);

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (appointments.length === 0) {
    return <div className="no-appointments">No appointments found</div>;
  }

  const calculateStatus = (appointmentTime) => {
    const appointmentDate = new Date(appointmentTime);
    const now = new Date();

    const diffInTime = appointmentDate - now;
    if (diffInTime < 0) {
      return { label: "Finalized", className: "status-finalized" };
    } else {
      const diffInDays = Math.ceil(diffInTime / (1000 * 60 * 60 * 24));
      return { label: `${diffInDays} more days`, className: "status-upcoming" };
    }
  };

  return (
    <div className="appointments-page">
      <h1 className="appointments-title">Your Appointments</h1>
      <ul className="appointments-list">
        {appointments.map((appointment) => {
          const status = calculateStatus(appointment.appointmentTime);
          return (
            <li key={appointment.id} className="appointment-card">
              <div className="appointment-details">
                <p>
                  <strong>Service:</strong> {appointment.facilityName || "Unknown Facility"}
                </p>
                <p>
                  <strong>Specialist:</strong> {appointment.specialistName || "Unknown Specialist"}
                </p>
                <p>
                  <strong>Salon:</strong> {appointment.salonName || "Unknown Salon"}
                </p>
                <p>
                  <strong>Time:</strong>{" "}
                  {appointment.appointmentTime
                    ? new Date(appointment.appointmentTime).toLocaleString()
                    : "Unknown Time"}
                </p>
                <p>
                  <strong>Status:</strong>{" "}
                  <span className={status.className}>{status.label}</span>
                </p>
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default Appointments;
