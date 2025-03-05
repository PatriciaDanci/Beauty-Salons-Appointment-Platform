import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './FacilitiesBySpecialistPage.css';

const FacilitiesBySpecialistPage = () => {
  const { salonId, specialistId } = useParams(); // `specialistId` is the name passed with `-`
  const [facilities, setFacilities] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchFacilities = async () => {
      try {
        // Fetch specialist details by name (handles hyphen and lowercase)
        const specialistResponse = await fetch(
          `http://localhost:8080/api/salons/${salonId}/specialists/name?name=${specialistId}`
        );
        const specialist = await specialistResponse.json();

        // Use the fetched specialist's ID to get their facilities
        const facilitiesResponse = await fetch(
          `http://localhost:8080/api/salons/${salonId}/specialists/${specialist.id}/facilities/names`
        );
        const facilitiesData = await facilitiesResponse.json();

        setFacilities(facilitiesData);
      } catch (error) {
        console.error('Error fetching facilities:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchFacilities();
  }, [salonId, specialistId]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="facilities-by-specialist-page">
      <h1>Facilities by Specialist</h1>
      <div className="facilities-list">
        {facilities.length > 0 ? (
          facilities.map((facility, index) => (
            <div key={index} className="facility-item">
              {facility}
            </div>
          ))
        ) : (
          <p>No facilities found for this specialist.</p>
        )}
      </div>
    </div>
  );
};

export default FacilitiesBySpecialistPage;
