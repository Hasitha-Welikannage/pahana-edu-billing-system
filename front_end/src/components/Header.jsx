import { FiPlus } from "react-icons/fi";

function Header({ action, content }) {
  return (
    <div className="flex flex-col md:flex-row gap-6 md:gap-0 md:items-center md:justify-between mb-8">
      <div>
        <h1 className="text-2xl font-semibold text-gray-900">
          {content.title}
        </h1>
        <p className="text-gray-600 mt-1">{content.description}</p>
      </div>
      <button
        onClick={action}
        className="bg-gray-900 text-white px-4 py-2 rounded-lg font-medium hover:bg-gray-800 transition-colors flex items-center space-x-2 cursor-pointer"
      >
        <FiPlus className="w-5 h-5" />
        <span className="text-base">{content.buttonText}</span>
      </button>
    </div>
  );
}
export default Header;
