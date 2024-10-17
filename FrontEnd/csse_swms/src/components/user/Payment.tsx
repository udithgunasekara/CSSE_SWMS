import React, { useState } from 'react';
import { CreditCard, Calendar, Lock, Building } from 'lucide-react';

const PaymentForm = () => {
  const [paymentInfo, setPaymentInfo] = useState({
    cardNumber: '',
    expiryDate: '',
    cvv: '',
    billingAddress: '',
    city: '',
    state: '',
    zipCode: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPaymentInfo(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle payment submission
  };

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      <h1 className="text-3xl font-bold mb-6">Payment Details</h1>

      <div className="bg-white border rounded-lg p-6">
        <div className="mb-6">
          <h2 className="flex items-center text-lg font-semibold">
            <CreditCard className="h-5 w-5 mr-2 text-green-600" />
            Card Information
          </h2>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-4">
            {/* Card Number */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Card Number
              </label>
              <div className="relative">
                <input
                  type="text"
                  name="cardNumber"
                  placeholder="1234 5678 9012 3456"
                  className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                  value={paymentInfo.cardNumber}
                  onChange={handleChange}
                />
                <CreditCard className="absolute right-3 top-2.5 h-5 w-5 text-gray-400" />
              </div>
            </div>

            {/* Expiry and CVV */}
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Expiry Date
                </label>
                <div className="relative">
                  <input
                    type="text"
                    name="expiryDate"
                    placeholder="MM/YY"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                    value={paymentInfo.expiryDate}
                    onChange={handleChange}
                  />
                  <Calendar className="absolute right-3 top-2.5 h-5 w-5 text-gray-400" />
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  CVV
                </label>
                <div className="relative">
                  <input
                    type="text"
                    name="cvv"
                    placeholder="123"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                    value={paymentInfo.cvv}
                    onChange={handleChange}
                  />
                  <Lock className="absolute right-3 top-2.5 h-5 w-5 text-gray-400" />
                </div>
              </div>
            </div>

            {/* Billing Address */}
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Billing Address
                </label>
                <div className="relative">
                  <input
                    type="text"
                    name="billingAddress"
                    placeholder="Street Address"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                    value={paymentInfo.billingAddress}
                    onChange={handleChange}
                  />
                  <Building className="absolute right-3 top-2.5 h-5 w-5 text-gray-400" />
                </div>
              </div>

              <div className="grid grid-cols-3 gap-4">
                <input
                  type="text"
                  name="city"
                  placeholder="City"
                  className="px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                  value={paymentInfo.city}
                  onChange={handleChange}
                />
                <input
                  type="text"
                  name="state"
                  placeholder="State"
                  className="px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                  value={paymentInfo.state}
                  onChange={handleChange}
                />
                <input
                  type="text"
                  name="zipCode"
                  placeholder="ZIP Code"
                  className="px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                  value={paymentInfo.zipCode}
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>

          <div className="pt-4">
            <button
              type="submit"
              className="w-full bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700 transition-colors focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
            >
              Pay Now
            </button>
          </div>
        </form>
      </div>

      <div className="text-sm text-gray-500 text-center">
        <p className="flex items-center justify-center">
          <Lock className="h-4 w-4 mr-1" />
          Your payment information is secure and encrypted
        </p>
      </div>
    </div>
  );
};

export default PaymentForm;