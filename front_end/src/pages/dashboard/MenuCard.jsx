import { useNavigate } from "react-router-dom";

function MenuCard({ data }) {
  const navigate = useNavigate();

  return (
    <>
      <div
        className="bg-white rounded-lg border border-gray-200 p-6 hover:shadow-md transition-shadow cursor-pointer group"
        onClick={() => navigate(data.path)}
      >
        <div className="flex items-center mb-4">{data.icon}</div>
        <h3 className="text-lg font-semibold text-gray-900 mb-2">
          {data.title}
        </h3>
        <p className="text-gray-600 text-sm">{data.description}</p>
      </div>
    </>
  );
}

export default MenuCard;
