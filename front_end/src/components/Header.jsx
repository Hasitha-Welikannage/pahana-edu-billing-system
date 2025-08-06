import { FiPlus } from "react-icons/fi";
import { Link } from "react-router-dom";

function Header({ action, content }) {
  return (
    <div className="flex flex-col md:flex-row gap-6 md:gap-0 md:items-center md:justify-between mb-8">
      <div>
        <h1 className="text-2xl font-semibold text-gray-900">
          {content.title}
        </h1>
        <p className="text-gray-600 mt-1">{content.description}</p>
      </div>

      {/* Button Container */}
      <div className="flex flex-row space-x-4">
        {/* The original button for a specific action (e.g., opening a modal) */}
        <button
          onClick={action}
          className="bg-gray-900 text-white px-4 py-2 rounded-lg font-medium hover:bg-gray-800 transition-colors flex items-center space-x-2 cursor-pointer"
        >
          <FiPlus className="w-5 h-5" />
          <span className="text-base">{content.buttonText}</span>
        </button>

        {/* The new button for navigating to the home page */}
        <Link
          to="/home"
          className="bg-gray-200 text-gray-800 px-4 py-2 rounded-lg font-medium hover:bg-gray-300 transition-colors flex items-center space-x-2 cursor-pointer"
        >
          <span className="text-base">Go to Home</span>
        </Link>
      </div>
    </div>
  );
}

export default Header;
