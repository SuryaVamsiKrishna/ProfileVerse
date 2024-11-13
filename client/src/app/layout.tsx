"use client";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import MyCustomLayout from "./MyCustomLayout";
import { Provider } from "react-redux";
import store from "@/store/store";

const inter = Inter({ subsets: ["latin"] });

// export const metadata: Metadata = {
//   title: "Create Next App",
//   description: "Generated by create next app",
// };

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <Provider store={store}>
        <body className={inter.className}>
          <MyCustomLayout>{children}</MyCustomLayout>
        </body>
      </Provider>
    </html>
  );
}