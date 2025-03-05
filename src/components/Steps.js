import React from 'react';
import './Steps.css';

const Steps = () => {
  return (
    <div className="steps">
      <div>
        <h3>1. Choose Service</h3>
        <p>Select a service and salon to begin.</p>
      </div>
      <div>
        <h3>2. Pick a Time</h3>
        <p>Choose a time that works for you.</p>
      </div>
      <div>
        <h3>3. Confirm Booking</h3>
        <p>Confirm and you're all set!</p>
      </div>
    </div>
  );
};

export default Steps;
