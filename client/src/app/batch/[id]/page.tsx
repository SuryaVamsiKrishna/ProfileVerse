"use client";
import React, { useState, useEffect } from "react";
import {
  Layout,
  Form,
  Upload,
  Button,
  Typography,
  Modal,
  Progress,
  message,
  Divider,
} from "antd";
import { UploadOutlined } from "@ant-design/icons";
import axios from "axios";
import { useParams } from "next/navigation";

const { Content } = Layout;
const { Title, Text } = Typography;

const ResumesUploadPage = () => {
  const [fileList, setFileList] = useState<any[]>([]);
  const [uploading, setUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState(0);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedFileCount, setSelectedFileCount] = useState(0);

  // Extract batch ID from the URL
  const params = useParams();
  const batchId = params?.id;

  // Display an error if batch ID is missing
  useEffect(() => {
    if (!batchId) {
      message.error("Batch ID not found in URL.");
    }
  }, [batchId]);

  // Handle file selection
  const handleUploadChange = ({ fileList }: any) => {
    setFileList(fileList);
    setSelectedFileCount(fileList.length);
  };

  // Convert file to base64 string
  const convertFileToBase64 = (file: File) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const base64String = (reader.result as string).split(",")[1]; // Extract base64 content
        resolve(base64String);
      };
      reader.onerror = (error) => reject(error);
    });
  };

  // Handle form submission
  const handleFormSubmit = async () => {
    if (!batchId) {
      message.error("Batch ID is missing.");
      return;
    }

    if (!fileList.length) {
      message.error("Please upload at least one resume.");
      return;
    }

    setIsModalVisible(true);
    setUploading(true);

    try {
      // Convert files to base64
      const filesBase64 = await Promise.all(
        fileList.map((file) => convertFileToBase64(file.originFileObj))
      );

      // Prepare payload
      const payload = {
        batchId: parseInt(batchId),
        files: filesBase64,
      };

      // Make the POST request
      const response = await axios.post(
        "http://localhost:8080/api/resumes/upload",
        payload,
        {
          headers: {
            "Content-Type": "application/json",
          },
          onUploadProgress: (progressEvent) => {
            const percentCompleted = Math.round(
              (progressEvent.loaded * 100) / progressEvent.total
            );
            setUploadProgress(percentCompleted);
          },
        }
      );

      // Handle successful upload
      setUploading(false);
      setIsModalVisible(false);
      setFileList([]);
      setSelectedFileCount(0);
      setUploadProgress(0);

      message.success("Resumes uploaded successfully!");
      console.log(response.data);
    } catch (error) {
      console.error("Upload error:", error);
      message.error("Failed to upload resumes. Please try again.");
      setUploading(false);
      setIsModalVisible(false);
    }
  };

  return (
    <Layout style={{ minHeight: "100vh", padding: "40px" }}>
      <Content>
        <div
          style={{
            maxWidth: 800,
            margin: "0 auto",
            padding: "40px",
            backgroundColor: "#f9f9f9",
            borderRadius: "12px",
            boxShadow: "0 6px 20px rgba(0,0,0,0.1)",
          }}
        >
          <Title level={2} style={{ textAlign: "center", marginBottom: 30 }}>
            Upload Resumes for Batch {batchId}
          </Title>
          <Divider />
          <Text
            type="secondary"
            style={{ textAlign: "center", display: "block", marginBottom: 20 }}
          >
            Upload resumes in bulk for analysis. Supported formats: PDF, DOC,
            DOCX.
          </Text>

          <Form layout="vertical" onFinish={handleFormSubmit}>
            <Form.Item label="Upload Resumes" valuePropName="fileList">
              <Upload
                multiple
                fileList={fileList}
                onChange={handleUploadChange}
                beforeUpload={() => false}
                accept=".pdf,.doc,.docx"
              >
                <Button icon={<UploadOutlined />}>Select Files</Button>
              </Upload>
              <div style={{ marginTop: 10 }}>
                <Text>{selectedFileCount} resumes selected</Text>
              </div>
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                block
                disabled={!selectedFileCount}
              >
                Submit Resumes
              </Button>
            </Form.Item>
          </Form>
        </div>

        <Modal
          title="Uploading Resumes"
          visible={isModalVisible}
          footer={null}
          closable={false}
        >
          <Progress percent={uploadProgress} status="active" />
          <Text>{`Uploading ${uploadProgress}% completed`}</Text>
        </Modal>
      </Content>
    </Layout>
  );
};

export default ResumesUploadPage;
