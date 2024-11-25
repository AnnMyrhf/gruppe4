import Registrieren from './pages/registrierenBuerger.js'
import Dashboard from './pages/mitarbeiterDashboard.js'
import './App.css';
import './style/styles.css';
import { BrowserRouter, Routes, Route} from "react-router-dom";
import MitarbeiterDashboard from "./pages/mitarbeiterDashboard.js";
import BeschwerdeDetail from "./pages/beschwerdeDetail";

function App() {
  return (
    <div className="App">
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Registrieren />}/>
                <Route path="/mitarbeiter/dashboard" element={<MitarbeiterDashboard />} />
                <Route path="/mitarbeiter/dashboard/:id" element={<BeschwerdeDetail />} />
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
