import { initializeApp } from "firebase/app";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import { getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyDcGEkPBU5CeVPyvkomUrG6bnHctSSz7D4",
  authDomain: "csse-waste-management-system.firebaseapp.com",
  projectId: "csse-waste-management-system",
  storageBucket: "csse-waste-management-system.appspot.com",
  messagingSenderId: "670178303605",
  appId: "1:670178303605:web:4ff222f76780ba8fc6a23c",
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const db = getFirestore(app);

export { auth, db };