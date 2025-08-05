function StatsCard({ data }) {
  return (
    <div className="bg-white rounded-lg border border-gray-200 p-6">
      <div className="flex items-center">
        {data.icon}
        <div className="ml-4">
          <p className="text-sm text-gray-600">{data.title}</p>
          <p className="text-2xl font-semibold text-gray-900">{data.value}</p>
        </div>
      </div>
    </div>
  );
}
export default StatsCard;
