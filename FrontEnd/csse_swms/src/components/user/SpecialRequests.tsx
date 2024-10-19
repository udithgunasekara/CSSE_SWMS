import React, { useState } from 'react';

const WasteCollectionForm = () => {
  const [formData, setFormData] = useState({
    id: JSON.parse(localStorage.getItem('user') || '{}').userid,
  });

  const user = JSON.parse(localStorage.getItem('user') || '{}');
  console.log("Here the id", user.userid);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      const response = await fetch('http://localhost:8081/requestSpecialWaste', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        const result = await response.json();
        console.log('Form submitted successfully:', result);
        alert('Your waste collection request has been submitted!');
        
        // Optionally, reset form fields
        setFormData({
          id: user.userid,
          name: '',
          address: '',
          city: '',
          number: '',
          typeofWaste: '',
        });
      } else {
        alert('Failed to submit the request');
        console.error('Error:', response.statusText);
      }
    } catch (error) {
      console.error('Error submitting form:', error);
      alert('An error occurred while submitting the request');
    }
  };

  return (
    <div className="max-w-2xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">Submit Waste Collection Request</h1>
      <div className="bg-white shadow rounded-lg p-6">
        <form onSubmit={handleSubmit}>
          <div className="grid grid-cols-1 gap-6">
            <div>
              <label htmlFor="name" className="block text-sm font-medium text-gray-700">Name</label>
              <input
          type="text"
          id="name"
          name="name"
          placeholder="Enter your name"
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
          value={formData.name}
          onChange={handleChange}
          required
              />
            </div>
            <div>
              <label htmlFor="address" className="block text-sm font-medium text-gray-700">Address</label>
              <input
          type="text"
          id="address"
          name="address"
          placeholder="Enter your address"
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
          value={formData.address}
          onChange={handleChange}
          required
              />
            </div>
            <div>
              <label htmlFor="city" className="block text-sm font-medium text-gray-700">City</label>
              <input
          type="text"
          id="city"
          name="city"
          placeholder="Enter your city"
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
          value={formData.city}
          onChange={handleChange}
          required
              />
            </div>
            <div>
              <label htmlFor="number" className="block text-sm font-medium text-gray-700">Contact Number</label>
              <input
          type="tel"
          id="number"
          name="number"
          placeholder="Enter your contact number"
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
          value={formData.number}
          onChange={handleChange}
          required
              />
            </div>
            <div>
              <label htmlFor="typeofWaste" className="block text-sm font-medium text-gray-700">Type of Waste</label>
              <input
          type="text"
          id="typeofWaste"
          name="typeofWaste"
          placeholder="Enter the type of waste"
          className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
          value={formData.typeofWaste}
          onChange={handleChange}
          required
              />
            </div>
          </div>
          <div className="mt-6">
            <button
              type="submit"
              className="w-full bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50"
            >
              Submit Request
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default WasteCollectionForm;
