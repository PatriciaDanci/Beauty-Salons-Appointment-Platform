import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './SalonPage.css';

const SalonPage = () => {
  const { id } = useParams(); // The salon name or ID
  const [salonData, setSalonData] = useState(null);
  const [facilities, setFacilities] = useState([]);
  const [specialists, setSpecialists] = useState([]);
  const [averageRating, setAverageRating] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSalonData = async () => {
      try {
        // Fetch salon details by name
        const salonResponse = await fetch(`http://localhost:8080/api/salons/name?name=${id}`);
        const salon = await salonResponse.json();
        setSalonData(salon);

        // Fetch facilities and specialists
        const [facilitiesResponse, specialistsResponse, reviewsResponse] = await Promise.all([
          fetch(`http://localhost:8080/api/salons/${salon.id}/facilities/details`),
          fetch(`http://localhost:8080/api/salons/${salon.id}/specialists/details`),
          fetch(`http://localhost:8080/api/reviews/salon/${salon.id}`), // Fetch reviews for the salon
        ]);

        const facilitiesData = await facilitiesResponse.json();
        const specialistsData = await specialistsResponse.json();
        const reviewsData = await reviewsResponse.json();

        setFacilities(facilitiesData);
        setSpecialists(specialistsData);

        // Calculate average rating
        if (reviewsData.length > 0) {
          const totalRating = reviewsData.reduce((sum, review) => sum + review.rating, 0);
          const avgRating = (totalRating / reviewsData.length).toFixed(1); // Round to 1 decimal
          setAverageRating(avgRating);
        } else {
          setAverageRating('No ratings yet');
        }
      } catch (error) {
        console.error('Error fetching salon data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchSalonData();
  }, [id]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!salonData) {
    return <div>Error loading salon data. Please try again later.</div>;
  }

  const { pictureUrl, name, address, city } = salonData;

  const formatTime = (minutes) => {
    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;
    return `${hours > 0 ? `${hours}h ` : ''}${remainingMinutes}m`;
  };

  return (
    <div className="salon-page">
      <div className="salon-page-card">
        <img src={pictureUrl} alt={name} className="salon-page-card-image" />
        <div className="salon-page-card-details">
          <h1 className="salon-page-name">{name}</h1>
          <p className="salon-page-address">{address}, {city}</p>
          <p className="salon-page-rating">
            <strong>Average Rating:</strong> {averageRating} ⭐
          </p>
        </div>
      </div>

      {/* Facilities Section */}
      <section className="salon-page-section">
        <h2 className="salon-page-section-title">Choose a Service</h2>
        <div className="salon-page-button-grid">
          {facilities.length > 0 ? (
            facilities.map((facility, index) => (
              <button
                key={index}
                className="salon-page-button"
                onClick={() =>
                  navigate(
                    `/salons/${salonData.id}/facilities/${facility.name.toLowerCase().replace(/\s+/g, '-')}/specialists`
                  )
                }
              >
                <strong>{facility.name}</strong>
                <p>Price: {facility.price.toFixed(2)} RON</p>
                <p>Time: {formatTime(facility.time)}</p>
              </button>
            ))
          ) : (
            <p className="salon-page-no-data">No facilities available at this salon.</p>
          )}
        </div>
      </section>

      {/* Specialists Section */}
      <section className="salon-page-section">
        <h2 className="salon-page-section-title">Choose a Specialist</h2>
        <div className="salon-page-button-grid">
          {specialists.length > 0 ? (
            specialists.map((specialist, index) => (
              <button
                key={index}
                className="salon-page-button"
                onClick={() =>
                  navigate(
                    `/salons/${salonData.id}/specialists/${specialist.name.toLowerCase().replace(/\s+/g, '-')}/facilities`
                  )
                }
              >
                <strong>{specialist.name}</strong>
                <p>{specialist.specialization || 'Specialization not provided'}</p>
              </button>
            ))
          ) : (
            <p className="salon-page-no-data">No specialists available at this salon.</p>
          )}
        </div>
      </section>

      {/* Reviews Button */}
      <button
        className="salon-page-button"
        onClick={() => navigate(`/salons/${salonData.id}/reviews`)}
      >
        View Reviews
      </button>
    </div>
  );
};

export default SalonPage;
