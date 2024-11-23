import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { auth, db } from '../firebase';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { collection, getDocs, query, where } from 'firebase/firestore';

const Login = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const [error, setError] = useState('');

  //create a dummy object to store the user
  let userData = {
    data: 'authority',
    role: 'authority',
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      
      const formData = {
        username: email,
        password: password,
      };

      const response = await fetch('http://localhost:8081/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if(response.ok){
        console.log("Loginn successful");
        
        console.log(response.data);
      }

      const token = await response.text();
      console.log(token);
      sessionStorage.setItem('token', token); // Store the token received from the backend


      //end of token and new login





      // Sign in with Firebase authentication
      // const userCredential = await signInWithEmailAndPassword(auth, email, password);
      // const user = userCredential.user;

      //const user;
      // Query all role collections
      // const businessQuery = query(
      //   collection(db, 'business'),
      //   where('email', '==', user.email)
      // );
      // const householderQuery = query(
      //   collection(db, 'householder'),
      //   where('email', '==', user.email)
      // );
      // const collectorQuery = query(
      //   collection(db, 'collector'),
      //   where('email', '==', user.email)
      // );
      // const authorityQuery = query(
      //   collection(db, 'authority'),
      //   where('email', '==', user.email)
      // );
      console.log("No user found");

      // const [businessSnapshot, householderSnapshot, collectorSnapshot, authoritySnapshot] = 
      //   await Promise.all([
      //     getDocs(businessQuery),
      //     getDocs(householderQuery),
      //     getDocs(collectorQuery),
      //     getDocs(authorityQuery)
      //   ]);

     // let userData = null;
      let userRole = 'authority';

      // if (!businessSnapshot.empty) {
      //   userData = businessSnapshot.docs[0].data();
      //   userRole = 'business';
      // } else if (!householderSnapshot.empty) {
      //   userData = householderSnapshot.docs[0].data();
      //   userRole = 'householder';
      // } else if (!collectorSnapshot.empty) {
      //   userData = collectorSnapshot.docs[0].data();
      //   userRole = 'collector';
      // } else if (!authoritySnapshot.empty) {
      //   userData = authoritySnapshot.docs[0].data();
      //   userRole = 'authority';
      // } //setting default role to authority
     
        console.log("No user found");
      //}

      if (userData && userRole) {
        userData.role = userRole;
        onLogin(userData);
        if (userRole === 'business' || userRole === 'householder') {
          navigate('/user');
        } else {
          navigate(`/${userRole}`);
        }
      } else {
        console.log(userRole);
        console.log(userData);
        setError('Account not found. Please check your creeedentials.',);
      }

    } catch (error) {
      console.error('Authentication failed:', error.message);
      setError('Invalid email or password');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-md w-96">
        <h2 className="text-2xl font-bold mb-4 text-center text-green-800">WasteWise Login</h2>
        {error && (
          <div className="mb-4 p-2 bg-red-100 text-red-700 rounded">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">Email</label>
            <input
              type="text"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">Password</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50"
          >
            Log In
          </button>
          <p className="text-center text-sm text-gray-600 mt-4">
            Create a new account?{' '}
            <br/>
            <a href="/signup" className="text-green-600 hover:text-green-800">
              Sign up as Business
            </a>
            <br/>
            <a href="/signup" className="text-green-600 hover:text-green-800">
              Sign up as HouseHolder
            </a>

          </p>
        </form>
      </div>
    </div>
  );
};

export default Login;