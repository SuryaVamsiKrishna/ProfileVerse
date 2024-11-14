"use client";
import "antd/dist/reset.css";
import {
  GithubOutlined,
  LinkedinOutlined,
  PlusCircleOutlined,
} from "@ant-design/icons";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { Card, Button, Empty, message } from "antd";

type Batch = {
  batchId: number;
  season: string;
  year: number;
  role: string;
  jobDescription: string;
  createdAt: string;
  updatedAt: string;
};

export default function Home() {
  const router = useRouter();
  const [batches, setBatches] = useState<Batch[]>([]);
  const [loading, setLoading] = useState(true);

  // Fetch batches from the backend
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/batch")
      .then((response) => {
        console.log(response.data, "batches");
        setBatches(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Failed to fetch batches:", error);
        message.error("Error fetching batches!");
        setLoading(false);
      });
  }, []);

  return (
    <div className="flex flex-col items-center justify-center w-full min-h-screen bg-[#f3f7fa] py-12 px-4">
      <div className="flex flex-col items-center justify-center w-full max-w-4xl bg-white p-8 rounded-lg shadow-lg">
        {/* Header Section */}
        <h1 className="text-4xl font-bold mb-6 text-center">
          Welcome to ProfileVerse
        </h1>
        <p className="text-xl mb-8 text-center">
          Effortlessly evaluate candidate profiles.
        </p>

        {/* Create Batch Button */}
        <div className="flex justify-end w-full mb-8">
          <Button
            type="primary"
            icon={<PlusCircleOutlined />}
            onClick={() => router.push("/batch")}
            size="large"
          >
            Create Batch
          </Button>
        </div>

        {/* Batch Cards */}
        {loading ? (
          <p>Loading...</p>
        ) : batches.length === 0 ? (
          <Empty description="No Batches Available" />
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 w-full">
            {batches.map((batch) => (
              <Card
                key={batch.batchId}
                hoverable
                onClick={() => router.push(`/batch/${batch.batchId}`)}
                className="bg-[#eaf4fc] shadow-lg rounded-lg cursor-pointer transition-transform duration-300 ease-in-out hover:scale-105"
              >
                <h2 className="text-2xl font-semibold">{batch.role}</h2>
                <p className="text-gray-700 mt-2">
                  {batch.season} {batch.year}
                </p>
                <p className="text-gray-500 mt-2 text-sm line-clamp-3">
                  {batch.jobDescription}
                </p>
              </Card>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
