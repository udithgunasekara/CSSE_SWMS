import React from 'react';
import { CheckCircle, XCircle } from 'lucide-react';

const StatusMessage = ({ status }) => (
  <div 
    className={`m-4 p-4 rounded-lg flex items-start space-x-2 ${
      status.success 
        ? 'bg-green-50 text-green-800 border border-green-200' 
        : 'bg-red-50 text-red-800 border border-red-200'
    }`}
  >
    {status.success ? (
      <CheckCircle className="h-5 w-5 flex-shrink-0" />
    ) : (
      <XCircle className="h-5 w-5 flex-shrink-0" />
    )}
    <div>
      <div className="font-medium">
        {status.success ? 'Success' : 'Error'}
      </div>
      <div className="text-sm mt-1">
        {status.message}
      </div>
    </div>
  </div>
);

export default StatusMessage;