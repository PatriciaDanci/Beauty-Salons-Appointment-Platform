import React, { useState, useEffect } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import './CalendarComponent.css';

const CalendarComponent = () => {
  const { specialistName } = useParams(); // Extract specialist name from URL params
  const location = useLocation();
  const { facilityId, salonId } = location.state || {}; // Retrieve facilityId and salonId from state
  const [specialistId, setSpecialistId] = useState(null); // New state for specialist ID
  const [selectedDate, setSelectedDate] = useState(() => {
    const today = new Date();
    return today.toISOString().split('T')[0];
  });
  const [availability, setAvailability] = useState([]);
  const [loading, setLoading] = useState(true);
  const [bookingMessage, setBookingMessage] = useState('');

  console.log("Location state:", location.state); // Debug: Log location state

  // Fetch Specialist ID
  useEffect(() => {
    const fetchSpecialistId = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/specialists/name?name=${specialistName}`);
        if (!response.ok) {
          throw new Error('Failed to fetch specialist ID');
        }
        const specialist = await response.json();
        console.log('Fetched Specialist:', specialist); // Debug: Log specialist
        setSpecialistId(specialist.id); // Set the specialist ID
      } catch (error) {
        console.error('Error fetching specialist ID:', error);
      }
    };

    fetchSpecialistId();
  }, [specialistName]);

  // Fetch availability data
  useEffect(() => {
    const fetchAvailability = async () => {
      if (!specialistName || !selectedDate) {
        console.error('Specialist name or selected date is missing');
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8080/api/specialists/availability/name?name=${specialistName}&date=${selectedDate}`
        );
        if (!response.ok) {
          throw new Error('Failed to fetch availability');
        }
        const data = await response.json();
        console.log('Fetched Availability:', data); // Debug: Log data
        setAvailability(data); // Set availability state
      } catch (error) {
        console.error('Error fetching availability:', error);
        setAvailability([]);
      } finally {
        setLoading(false);
      }
    };

    fetchAvailability();
  }, [specialistName, selectedDate]);

  const handleBooking = async (slot) => {
  try {
    const userId = localStorage.getItem('userId'); // Retrieve userId from localStorage

    const payload = {
      specialistId: specialistId, // Use the fetched specialistId
      facilityId: facilityId, // Use the facilityId passed via state
      salonId: salonId, // Use the salonId passed via state
      userId: userId, // Logged-in user ID
      appointmentTime: `${slot.date}T${slot.startTime}`, // Combine date and time for ISO format
    };

    console.log('Booking Payload:', payload); // Debug: Log the booking payload

    const response = await fetch('http://localhost:8080/api/appointments/book', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      throw new Error('Failed to book appointment');
    }

    const data = await response.json();
    setBookingMessage('Appointment booked successfully!');
    console.log('Appointment Data:', data);

    // Remove the booked slot from the list of available slots
    setAvailability((prevAvailability) =>
      prevAvailability.filter(
        (availableSlot) =>
          !(
            availableSlot.date === slot.date &&
            availableSlot.startTime === slot.startTime
          )
      )
    );
  } catch (error) {
    setBookingMessage('Failed to book appointment');
    console.error('Error booking appointment:', error);
  }
};

  if (loading) {
    return <div>Loading...</div>;
  }

  if (availability.length === 0) {
    return <div>No availability data found</div>;
  }

  return (
    <div className="calendar-component">
      <h1>Specialist Availability</h1>
      <h3>{bookingMessage}</h3>
      <div className="date-picker">
        <label htmlFor="date">Select Date:</label>
        <input
          type="date"
          id="date"
          value={selectedDate}
          onChange={(e) => setSelectedDate(e.target.value)}
        />
      </div>
      <div className="slots-container">
        <h2>Available Slots</h2>
        <div className="slots">
          {availability.map((slot, index) => (
            <button
              key={index}
              className="slot-button"
              onClick={() => handleBooking(slot)}
            >
              {slot.startTime} - {slot.endTime}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
};

export default CalendarComponent;
