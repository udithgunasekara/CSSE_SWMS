import React from 'react';
import { Camera, QrCode, CheckCircle } from 'lucide-react';

const HelpTab = () => (
  <div className="p-4">
    <h2 className="text-lg font-semibold mb-4">How to Use</h2>
    <div className="space-y-3">
      <HelpItem
        icon={Camera}
        title="Start Scanning"
        description="Click the scan button to activate your camera"
      />
      <HelpItem
        icon={QrCode}
        title="Position QR Code"
        description="Align the QR code within the scanning frame"
      />
      <HelpItem
        icon={CheckCircle}
        title="Confirm Collection"
        description="Wait for the success message to confirm waste collection"
      />
    </div>
  </div>
);

const HelpItem = ({ icon: Icon, title, description }) => (
  <div className="flex items-start space-x-3">
    <div className="bg-green-100 p-2 rounded-full">
      <Icon size={20} className="text-green-600" />
    </div>
    <div>
      <h3 className="font-medium">{title}</h3>
      <p className="text-sm text-gray-600">{description}</p>
    </div>
  </div>
);

export default HelpTab;