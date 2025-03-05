import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './SalonReviewsPage.css';

const SalonReviewsPage = () => {
  const { salonId } = useParams();
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/reviews/salon/${salonId}`);
        if (!response.ok) {
          throw new Error('Failed to fetch reviews');
        }
        const data = await response.json();
        setReviews(data);
      } catch (error) {
        console.error('Error fetching reviews:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchReviews();
  }, [salonId]);

  if (loading) {
    return <div>Loading reviews...</div>;
  }

  if (reviews.length === 0) {
    return <div>No reviews available for this salon.</div>;
  }

  return (
    <div className="reviews-page">
      <h1>Salon Reviews</h1>
      <ul>
        {reviews.map((review) => (
          <li key={review.id} className="review-card">
            <p><strong>User:</strong> {review.user?.name || 'Anonymous'}</p>
            <p><strong>Rating:</strong> {review.rating} / 5</p>
            <p><strong>Comment:</strong> {review.comment}</p>
            <p><strong>Specialist:</strong> {review.specialist?.name || 'N/A'}</p>
            <p><strong>Date:</strong> {new Date(review.timestamp).toLocaleString()}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SalonReviewsPage;
