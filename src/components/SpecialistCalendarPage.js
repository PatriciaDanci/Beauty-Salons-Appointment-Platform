import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './SpecialistCalendarPage.css';

const SpecialistCalendarPage = () => {
  const { salonId, specialistId, facilityId } = useParams();
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [availableSlots, setAvailableSlots] = useState([]);
  const [facilityDetails, setFacilityDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // Fetch specialist availability and facility details
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch facility details
        const facilityResponse = await fetch(
          `http://localhost:8080/api/salons/${salonId}/facilities/name?name=${facilityId}`
        );
        const facility = await facilityResponse.json();
        setFacilityDetails(facility);

        // Fetch availability for the selected specialist
        const availabilityResponse = await fetch(
          `http://localhost:8080/api/specialists/${specialistId}/availability?date=${selectedDate.toISOString().split('T')[0]}`
        );
        const availability = await availabilityResponse.json();
        setAvailableSlots(availability);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [selectedDate, specialistId, salonId, facilityId]);

  // Book an appointment
  const bookAppointment = async (slot) => {
    try {
      const appointment = {
        specialistId: parseInt(specialistId),
        facilityId: facilityDetails.id,
        date: slot.date,
        startTime: slot.startTime,
        endTime: slot.endTime,
      };

      const response = await fetch('http://localhost:8080/api/appointments', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(appointment),
      });

      if (response.ok) {
        alert('Appointment booked successfully!');
        setAvailableSlots((prevSlots) =>
          prevSlots.filter(
            (s) =>
              !(s.date === slot.date && s.startTime === slot.startTime)
          )
        );
      } else {
        alert('Failed to book the appointment. Try again.');
      }
    } catch (error) {
      console.error('Error booking appointment:', error);
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!facilityDetails) {
    return <div>Error loading facility details. Please try again later.</div>;
  }

  return (
    <div className="specialist-calendar-page">
      <h1>Book an Appointment</h1>
      <h2>Facility: {facilityDetails.name}</h2>
      <p>Price: {facilityDetails.price.toFixed(2)} RON</p>
      <p>Duration: {facilityDetails.time} minutes</p>

      {/* Calendar */}
      <div className="calendar-container">
        <Calendar
          onChange={setSelectedDate}
          value={selectedDate}
        />
      </div>

      {/* Available Slots */}
      <div className="slots-container">
        <h2>Available Slots for {selectedDate.toDateString()}</h2>
        {availableSlots.length > 0 ? (
          availableSlots.map((slot, index) => (
            <button
              key={index}
              className="slot-button"
              onClick={() => bookAppointment(slot)}
            >
              {slot.startTime} - {slot.endTime}
            </button>
          ))
        ) : (
          <p>No available slots for this day.</p>
        )}
      </div>
    </div>
  );
};

export default SpecialistCalendarPage;
