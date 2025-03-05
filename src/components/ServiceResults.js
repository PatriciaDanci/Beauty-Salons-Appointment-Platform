import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import './SearchResult.css'; // Reuse styling for consistency

const ServiceResults = () => {
  const location = useLocation();
  const [results, setResults] = useState([]);
  const [keyword, setKeyword] = useState('');

  useEffect(() => {
    const serviceKeyword = location.state?.keyword || '';
    setKeyword(serviceKeyword);

    if (serviceKeyword) {
      fetch(`http://localhost:8080/api/salons/search?keyword=${serviceKeyword}`)
        .then((response) => response.json())
        .then((data) => setResults(data))
        .catch((error) => console.error('Error fetching service results:', error));
    }
  }, [location.state]);

  return (
    <div className="search-results">
      <h1 className="search-header">Results for "{keyword}"</h1>
      {results.length > 0 ? (
        <div className="salon-grid">
          {results.map((salon) => (
            <div key={salon.id} className="salon-card">
              <img src={salon.pictureUrl} alt={salon.name} className="salon-image" />
              <div className="salon-info">
                <h2 className="salon-name">
                  <Link to={`/salons/${salon.name.toLowerCase().replace(/\s+/g, '-')}`}>
                    {salon.name}
                  </Link>
                </h2>
                <p className="salon-address">{salon.address}</p>
                <p className="salon-city">{salon.city}</p>
                <p className="salon-rating">⭐ Average Rating: {salon.averageRating || 'N/A'}</p>
                <Link
                  to={`/salons/${salon.name.toLowerCase().replace(/\s+/g, '-')}`}
                  className="details-link"
                >
                  View Details
                </Link>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p className="no-results">No salons found for this service!</p>
      )}
    </div>
  );
};

export default ServiceResults;
