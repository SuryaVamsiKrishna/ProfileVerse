"use client";
import React, { useState } from "react";
import { Layout, Form, Input, Button, Typography, Select, message } from "antd";
import axios from "axios";
import { useRouter } from "next/navigation";

const { Content } = Layout;
const { Title, Text } = Typography;
const { TextArea } = Input;
const { Option } = Select;

const Page = () => {
  const [form] = Form.useForm();
  const router = useRouter();

  // Dropdown options for season and year
  const seasons = ["Fall", "Spring", "Summer", "Winter", "Immediate"];
  const currentYear = new Date().getFullYear();
  const years = Array.from({ length: 5 }, (_, index) => currentYear + index);

  // Handle form submission
  const handleFormSubmit = async (values: any) => {
    try {
      const payload = {
        season: values.season,
        year: values.year,
        role: values.role,
        jobDescription: values.jobDescription,
      };
      console.log(payload, "payload");

      // Make a POST request to the server
      await axios
        .post("http://localhost:8080/api/batch", payload)
        .then((res) => {
          const { batchId } = res.data;
          console.log(res.data, "response");

          // Display success message
          message.success("Batch data submitted successfully!");

          // Redirect to the batch details page using the batchId
          router.push(`/batch/${batchId}`);
        })
        .catch((e) => {
          console.log(e);
        });
      message.success("Batch data submitted successfully!");
      form.resetFields();
    } catch (error) {
      message.error("Failed to submit data. Please try again.");
    }
  };

  return (
    <Layout style={{ minHeight: "100vh", padding: "20px" }}>
      <Content>
        <div style={{ maxWidth: 600, margin: "0 auto", textAlign: "center" }}>
          <Title level={2}>Batch Information Submission</Title>
          <Text type="secondary">
            Enter batch details including season, year, role, and job
            description.
          </Text>
          <Form
            form={form}
            layout="vertical"
            onFinish={handleFormSubmit}
            style={{ marginTop: 30 }}
          >
            {/* Season Dropdown */}
            <Form.Item
              label="Season"
              name="season"
              rules={[{ required: true, message: "Please select a season" }]}
            >
              <Select placeholder="Select season">
                {seasons.map((season) => (
                  <Option key={season} value={season}>
                    {season}
                  </Option>
                ))}
              </Select>
            </Form.Item>

            {/* Year Dropdown */}
            <Form.Item
              label="Year"
              name="year"
              rules={[{ required: true, message: "Please select a year" }]}
            >
              <Select placeholder="Select year">
                {years.map((year) => (
                  <Option key={year} value={year}>
                    {year}
                  </Option>
                ))}
              </Select>
            </Form.Item>

            {/* Role Input */}
            <Form.Item
              label="Role"
              name="role"
              rules={[
                { required: true, message: "Please enter a role" },
                { max: 100, message: "Role must be less than 100 characters" },
              ]}
            >
              <Input placeholder="Enter job role" maxLength={100} />
            </Form.Item>

            {/* Job Description Text Area */}
            <Form.Item
              label="Job Description"
              name="jobDescription"
              rules={[
                { required: true, message: "Please enter a job description" },
              ]}
            >
              <TextArea
                placeholder="Enter job description here..."
                rows={5}
                maxLength={5000}
                showCount
              />
            </Form.Item>

            {/* Submit Button */}
            <Form.Item>
              <Button type="primary" htmlType="submit" block>
                Submit
              </Button>
            </Form.Item>
          </Form>
        </div>
      </Content>
    </Layout>
  );
};

export default Page;
