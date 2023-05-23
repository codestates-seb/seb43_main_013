// css
import "@/css/globals.css";
import "@/css/viewer.css";

// provider
import Provider from "@/provider";

// layout
import Layout from "@/Layout";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC",
  description: "CC",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="ko">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta httpEquiv="Content-Type" content="text/html; charset=utf-8" />

        <link rel="icon" href="/logo/favicon.ico" />
      </head>
      <body className="text-[16px] md:text-[14px] bg-bg">
        <Provider>
          <Layout>{children}</Layout>
        </Provider>
      </body>
    </html>
  );
}
