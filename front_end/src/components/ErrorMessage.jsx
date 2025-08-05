import { FiAlertCircle } from "react-icons/fi";

function ErrorMessage({ error }) {
  return (
    <div className="p-4 bg-red-50 border border-red-200 rounded-lg">
      <div className="flex items-center">
        <FiAlertCircle className="w-5 h-5 text-red-400 mr-2" />
        <p className="text-red-700">{error}</p>
      </div>
    </div>
  );
}

export default ErrorMessage;
