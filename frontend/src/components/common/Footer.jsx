import React from 'react';

export default function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gray-800 text-white mt-12">
      <div className="container mx-auto px-4 py-8 text-center">
        <p>&copy; {currentYear} Virtual Portfolio. All rights reserved.</p>
      </div>
    </footer>
  );
}
