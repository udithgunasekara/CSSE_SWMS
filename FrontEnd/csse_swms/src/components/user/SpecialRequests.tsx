import React, { useState } from 'react';

const SpecialRequests = () => {
  const [request, setRequest] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here you would typically send the request to your backend
    console.log('Special request submitted:', request);
    alert('Your special request has been submitted!');
    setRequest('');
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Submit Special Waste Collection Request</h1>
      <div className="bg-white shadow rounded-lg p-6">
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="request" className="block text-sm font-medium text-gray-700">
              Request Details
            </label>
            <textarea
              id="request"
              rows={4}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
              value={request}
              onChange={(e) => setRequest(e.target.value)}
              required
            ></textarea>
          </div>
          <button
            type="submit"
            className="bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50"
          >
            Submit Request
          </button>
        </form>
      </div>
    </div>
  );
};

export default SpecialRequests;