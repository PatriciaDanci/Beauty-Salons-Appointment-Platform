import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './SpecialistsByFacilityPage.css';

const SpecialistsByFacilityPage = () => {
  const { salonId, facilityId } = useParams();
  const [specialists, setSpecialists] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSpecialists = async () => {
      try {
        if (!facilityId || facilityId === 'undefined') {
          console.error('Facility ID is invalid or undefined');
          return;
        }

        const facilityResponse = await fetch(
          `http://localhost:8080/api/salons/${salonId}/facilities/name?name=${facilityId}`
        );
        if (!facilityResponse.ok) {
          throw new Error(`Error fetching facility: ${facilityResponse.statusText}`);
        }
        const facility = await facilityResponse.json();

        const specialistsResponse = await fetch(
          `http://localhost:8080/api/salons/${salonId}/facilities/${facility.id}/specialists/names`
        );
        if (!specialistsResponse.ok) {
          throw new Error(`Error fetching specialists: ${specialistsResponse.statusText}`);
        }
        const specialistsData = await specialistsResponse.json();

        setSpecialists(
          specialistsData.map((specialist) => ({
            name: specialist,
            facilityId: facility.id,
          }))
        );
      } catch (error) {
        console.error('Error fetching specialists:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchSpecialists();
  }, [salonId, facilityId]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="specialists-by-facility-page">
      <h1>Specialists for Facility</h1>
      <div className="specialists-list">
        {specialists.length > 0 ? (
          specialists.map((specialist, index) => (
            <button
              key={index}
              className="specialist-button"
              onClick={() =>
              {
                console.log("Navigating with state:", {facilityId: specialist.facilityId, salonId});
              
                navigate(
                  `/salons/${salonId}/facilities/${facilityId}/specialists/${specialist.name
                    .toLowerCase()
                    .replace(/\s+/g, '-')}/calendar`,
                    {state: {facilityId: specialist.facilityId, salonId}}
                );
              }}
            >
              {specialist.name}
            </button>
          ))
        ) : (
          <p>No specialists found for this facility.</p>
        )}
      </div>
    </div>
  );
};

export default SpecialistsByFacilityPage;
